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
relativeIDLDir="/src/biosim/idl/SIMULATION.idl"
fullIDLDir=$devRootDir$relativeIDLDir
echo "			-generating skeletons"
idlInvocation="java -classpath $JACORB_HOME/lib/idl.jar org.jacorb.idl.parser"
$idlInvocation  -nostub -d $skeletonDir $fullIDLDir
#######################
#		SERVER COMPILATION	#
#######################
echo "		-compiling server";
simString="SIMULATION"
simSkeletonDir="$skeletonDir/$simString"
serverDir="$devRootDir/src/biosim/server"
separator=":"
serverType=`uname`
if [ "$serverType" == "CYGWIN_NT-5.1" ]
then
	separator=";"
fi
compilationInvocation="javac -d $serverClassesDir -classpath $skeletonDir$separator$serverClassesDir$separator$CLASSPATH"
echo "			-compiling skeletons"
$compilationInvocation $simSkeletonDir/*.java
echo "			-compiling air"
$compilationInvocation $serverDir/air/*.java
echo "			-compiling water"
$compilationInvocation $serverDir/water/*.java
echo "			-compiling energy"
$compilationInvocation $serverDir/energy/*.java
echo "			-compiling crew"
$compilationInvocation $serverDir/crew/*.java
echo "			-compiling biomass"
$compilationInvocation $serverDir/biomass/*.java
echo "*done building biosim"



