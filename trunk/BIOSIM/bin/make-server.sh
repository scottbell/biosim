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
####################
#		SERVER INIT		#
####################
echo "	-building server"
echo "		-initializing server build"
serverGenString="/server"
serverGenDir=$genDir$serverGenString
if [ ! -e  "$serverGenDir" ]
then
	mkdir $serverGenDir
	echo "			-creating server directory"
fi
skeletonString="/skeletons"
skeletonDir=$serverGenDir$skeletonString
if [ ! -e  "$skeletonDir" ]
then
	mkdir $skeletonDir
	echo "			-creating skeletons directory"
fi
serverClassesString="/classes"
serverClassesDir=$serverGenDir$serverClassesString
if [ ! -e  "$serverClassesDir" ]
then
	mkdir $serverClassesDir
	echo "			-creating classes directory"
fi
relativeIDLDir="/src/biosim/idl/ALSS.idl"
fullIDLDir=$devRootDir$relativeIDLDir
echo "			-generating skeletons"
idlInvocation="$java_command -classpath $JACORB_HOME/lib/idl.jar org.jacorb.idl.parser"
$idlInvocation  -nostub -d $skeletonDir $fullIDLDir
#######################
#		SERVER COMPILATION	#
#######################
echo "		-compiling server";
simString="ALSS"
simSkeletonDir="$skeletonDir/$simString"
serverDir="$devRootDir/src/biosim/server"
sourceDir="$devRootDir/src"
compilationInvocation="$javac_command -d $serverClassesDir -classpath $skeletonDir$separator$serverClassesDir$separator$sourceDir"
echo "			-compiling skeletons"
$compilationInvocation $simSkeletonDir/*.java
echo "			-compiling air"
$compilationInvocation $serverDir/air/*.java
echo "			-compiling water"
$compilationInvocation $serverDir/water/*.java
echo "			-compiling power"
$compilationInvocation $serverDir/power/*.java
echo "			-compiling crew"
$compilationInvocation $serverDir/crew/*.java
echo "			-compiling food"
$compilationInvocation $serverDir/food/*.java
echo "			-compiling environment"
$compilationInvocation $serverDir/environment/*.java
echo "			-compiling framework"
$compilationInvocation $serverDir/framework/*.java
echo "*done building biosim"



