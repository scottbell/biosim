#!/bin/bash

echo "*building biosim"
echo "	-initializing biosim build...";
# see if the biosim directory exists, if it doesn't, assume it's one directory back (i.e., user is in bin directory)
devRootDir=$BIOSIM_HOME
if [ -z "$devRootDir" ]
then
	devRootDir=".."
	echo "		-assuming BIOSIM_HOME is $devRootDir"
fi
JACORB_HOME="$devRootDir/lib/jacorb"
java_command=$JAVA_HOME/bin/java
javac_command=$JAVA_HOME/bin/javac
if [ -z "$JAVA_HOME" ]
then
	echo "		-JAVA_HOME not set, assuming java and javac are in path..."
	java_command="java"
	javac_command="javac"
fi
genString="/generated"
genDir=$devRootDir$genString
if [ ! -e "$genDir" ]
then
	mkdir $genDir
	echo "		-creating generated directory"
fi
separator=":"
machineType=`uname`
winName="CYGWIN"
case $machineType in
	*$winName*) separator=";";echo "		-machine type is $winName";;
	*)separator=":";echo "		-assuming Unix machine type";;
esac
#######################
#		CLIENT COMPILATION	#
#######################
echo "	-building client"
echo "		-initializing client build"
clientString="/client"
clientGenDir=$genDir$clientString
if [ ! -e  "$clientGenDir" ]
then
	mkdir $clientGenDir
	echo "			-creating client directory"
fi
stubString="/stubs"
stubDir=$clientGenDir$stubString
if [ ! -e  "$stubDir" ]
then
	mkdir $stubDir
	echo "			-creating stubs directory"
fi
clientClassesString="/classes"
clientClassesDir=$clientGenDir$clientClassesString
if [ ! -e  "$clientClassesDir" ]
then
	mkdir $clientClassesDir
	echo "			-creating classes directory"
fi
relativeIDLDir="/src/biosim/idl/SIMULATION.idl"
fullIDLDir=$devRootDir$relativeIDLDir
echo "			-generating stubs"
idlInvocation="$java_command -classpath $JACORB_HOME/lib/idl.jar org.jacorb.idl.parser"
$idlInvocation  -noskel -d $stubDir $fullIDLDir
#######################
#		Client COMPILATION	#
#######################
echo "		-compiling client";
simString="SIMULATION"
simStubDir="$stubDir/$simString"
clientDir="$devRootDir/src/biosim/client"
compilationInvocation="$javac_command -d $clientClassesDir -classpath $stubDir$separator$clientClassesDir$separator$CLASSPATH"
echo "			-compiling stubs"
$compilationInvocation $simStubDir/*.java
echo "			-compiling control"
$compilationInvocation $clientDir/control/*.java
echo "*done building biosim"



