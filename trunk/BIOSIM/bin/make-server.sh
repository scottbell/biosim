#!/bin/bash

echo "*building biosim"
echo "	-initializing biosim build...";
userSelect="$@"
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
relativeIDLDir="/src/biosim/idl/biosim.idl"
fullIDLDir=$devRootDir$relativeIDLDir
idlInvocation="$java_command -classpath $JACORB_HOME/lib/idl.jar org.jacorb.idl.parser"
if [ "$userSelect" = "all" ]
then
	echo "			-generating skeletons/stubs"
	$idlInvocation -d $skeletonDir $fullIDLDir
fi
#######################
#		SERVER COMPILATION	#
#######################
echo "		-compiling server";
simString="biosim"
simSkeletonDir="$skeletonDir/$simString"
serverDir="$devRootDir/src/biosim/server"
sourceDir="$devRootDir/src"
jacoClasspath="$JACORB_HOME/lib/jacorb.jar$separator$JRE_HOME/lib/rt.jar$separator$JACORB_HOME/lib"
compilationInvocation="$javac_command -d $serverClassesDir -classpath $skeletonDir$separator$serverClassesDir$separator$sourceDir$separator$jacoClasspath"
if [ "$userSelect" = "all" ]
then
	echo "			-compiling skeletons/stubs"
	echo "				-compiling air skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/air/*.java
	echo "				-compiling water skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/water/*.java
	echo "				-compiling power skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/power/*.java
	echo "				-compiling crew skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/crew/*.java
	echo "				-compiling food skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/food/*.java
	echo "				-compiling environment skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/environment/*.java
	echo "				-compiling framework skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/framework/*.java
fi
echo "			-compiling implementations"
echo "				-compiling air implementation"
$compilationInvocation $serverDir/air/*.java
echo "				-compiling water implementation"
$compilationInvocation $serverDir/water/*.java
echo "				-compiling power implementation"
$compilationInvocation $serverDir/power/*.java
echo "				-compiling crew implementation"
$compilationInvocation $serverDir/crew/*.java
echo "				-compiling food implementation"
$compilationInvocation $serverDir/food/*.java
echo "				-compiling environment implementation"
$compilationInvocation $serverDir/environment/*.java
echo "				-compiling framework implementation"
$compilationInvocation $serverDir/framework/*.java
echo "				-compiling util implementation"
$compilationInvocation $serverDir/util/*.java
echo "*done building biosim"



