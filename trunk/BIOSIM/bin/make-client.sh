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
#assume javac is in the path
javac_command=javac
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
relativeIDLDir="/src/biosim/idl/biosim.idl"
fullIDLDir=$devRootDir$relativeIDLDir
echo "			-generating stubs"
idlInvocation="$java_command -classpath $JACORB_HOME/lib/idl.jar org.jacorb.idl.parser"
$idlInvocation  -noskel -d $stubDir $fullIDLDir
#######################
#		Client COMPILATION	#
#######################
echo "		-compiling client";
simString="biosim"
simStubDir="$stubDir/$simString"
clientDir="$devRootDir/src/biosim/client"
sourceDir="$devRootDir/src"
jacoClasspath="$JACORB_HOME/lib/jacorb.jar$separator$JRE_HOME/lib/rt.jar$separator$JACORB_HOME/lib"
compilationInvocation="$javac_command -d $clientClassesDir -classpath $stubDir$separator$clientClassesDir$separator$sourceDir$separator$jacoClasspath"
echo "			-compiling stubs"
echo "				-compiling air stubs"
$compilationInvocation $simStubDir/idl/air/*.java
echo "				-compiling water stubs"
$compilationInvocation $simStubDir/idl/water/*.java
echo "				-compiling power stubs"
$compilationInvocation $simStubDir/idl/power/*.java
echo "				-compiling crew stubs"
$compilationInvocation $simStubDir/idl/crew/*.java
echo "				-compiling food stubs"
$compilationInvocation $simStubDir/idl/food/*.java
echo "				-compiling environment stubs"
$compilationInvocation $simStubDir/idl/environment/*.java
echo "				-compiling framework stubs"
$compilationInvocation $simStubDir/idl/framework/*.java
echo "				-compiling util stubs"
$compilationInvocation $simStubDir/idl/util/*.java
echo "			-compiling control"
$compilationInvocation $clientDir/control/*.java
echo "				-compiling gui"
$compilationInvocation $clientDir/control/gui/*.java
echo "*done building biosim"



