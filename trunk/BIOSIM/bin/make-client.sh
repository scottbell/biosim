#!/bin/bash

echo "*building biosim"
echo "	-initializing biosim build...";
devRootDir=$BIOSIM_HOME
if [ -z "$devRootDir" ]
then
	devRootDir=".."
	echo "		-assuming BIOSIM_HOME is $devRootDir"
fi
JACORB_HOME="$devRootDir/lib/jacorb"
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
idlInvocation="java -classpath $JACORB_HOME/lib/idl.jar org.jacorb.idl.parser"
$idlInvocation  -noskel -d $stubDir $fullIDLDir
#######################
#		Client COMPILATION	#
#######################
echo "		-compiling client";
simString="SIMULATION"
simStubDir="$stubDir/$simString"
clientDir="$devRootDir/src/biosim/client"
compilationInvocation="javac -d $clientClassesDir -classpath $stubDir$separator$clientClassesDir$separator$CLASSPATH"
echo "			-compiling stubs"
$compilationInvocation $simStubDir/*.java
echo "			-compiling control"
$compilationInvocation $clientDir/control/*.java
echo "*done building biosim"



