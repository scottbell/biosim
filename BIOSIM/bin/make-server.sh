#!/bin/bash

echo "*building biosim"
echo "	-configuring environment";
userSelect="$@"
devRootDir=$BIOSIM_HOME
currentDir=`pwd`
if [ -z "$devRootDir" ]
then
	cd ..
	devRootDir=`pwd`
	cd $currentDir
	echo "		-assuming BIOSIM_HOME is $devRootDir"
fi
JACORB_HOME="$devRootDir/lib/jacorb"
java_command=$JAVA_HOME/bin/java
jikesCommand="jikes"
type $jikesCommand 2> /dev/null >/dev/null
if [ $? != 0 ]; then
	javac_command=javac
	echo "		-using javac compiler (assuming it's in the path)"
else
	javac_command=jikes
	echo "		-using jikes compiler"
fi
if [ -z "$JAVA_HOME" ]; then
	echo "		-JAVA_HOME not set, assuming java is in path..."
	java_command="java"
fi
JRE_HOME="$JAVA_HOME/jre"
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
IBM_libs="$JRE_HOME/lib/core.jar$separator$JRE_HOME/lib/charsets.jar$separator$JRE_HOME/lib/graphics.jar$separator$JRE_HOME/lib/security.jar$separator$JRE_HOME/lib/server.jar$separator$JRE_HOME/lib/xml.jar"
Sun_libs="$JRE_HOME/lib/rt.jar"
javaVersionString=`$java_command -version 2>&1 | grep IBM`
case $javaVersionString in
	*"IBM"*) JRE_libs=$IBM_libs;echo "		-VM is IBM";;
	*)JRE_libs=$Sun_libs;echo "		-assuming Sun VM";;
esac
####################
#		SERVER INIT    #
####################
echo "	-initializing biosim server build"
serverGenString="/server"
serverGenDir=$genDir$serverGenString
if [ ! -e  "$serverGenDir" ]
then
	mkdir $serverGenDir
	echo "		-creating server directory"
fi
skeletonString="/skeletons"
skeletonDir=$serverGenDir$skeletonString
if [ ! -e  "$skeletonDir" ]
then
	mkdir $skeletonDir
	echo "		-creating skeletons directory"
	echo "			-no skeletons (switching to make all)"
	userSelect="all"
fi
serverClassesString="/classes"
serverClassesDir=$serverGenDir$serverClassesString
if [ ! -e  "$serverClassesDir" ]
then
	mkdir $serverClassesDir
	echo "		-creating classes directory"
fi
relativeIDLDir="/src/biosim/idl/biosim.idl"
fullIDLDir=$devRootDir$relativeIDLDir
idlInvocation="$java_command -classpath $JACORB_HOME/idl.jar org.jacorb.idl.parser"
if [ "$userSelect" == "all" ]
then
	echo "		-generating skeletons/stubs"
	$idlInvocation -d $skeletonDir $fullIDLDir
fi
#######################
#		SERVER COMPILATION	#
#######################
echo "	-compiling server";
simString="biosim"
simSkeletonDir="$skeletonDir/$simString"
serverDir="$devRootDir/src/biosim/server"
sourceDir="$devRootDir/src"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JRE_libs$separator$JACORB_HOME"
compilationInvocation="$javac_command -d $serverClassesDir -classpath $skeletonDir$separator$serverClassesDir$separator$sourceDir$separator$jacoClasspath"
if [ "$userSelect" == "all" ]
then
	echo "		-compiling skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/*.java
	echo "			-compiling framework skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/framework/*.java
	echo "			-compiling util skeletons/stubs"
	echo "				-compiling log skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/util/log/*.java
	echo "			-compiling simulation skeletons/stubs"
	echo "				-compiling air skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/simulation/air/*.java
	echo "				-compiling water skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/simulation/water/*.java
	echo "				-compiling power skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/simulation/power/*.java
	echo "				-compiling crew skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/simulation/crew/*.java
	echo "				-compiling food skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/simulation/food/*.java
	echo "				-compiling environment skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/simulation/environment/*.java
	echo "				-compiling framework skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/simulation/framework/*.java
	echo "			-compiling sensor skeletons/stubs"
	echo "				-compiling framework skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/sensor/framework/*.java
	echo "				-compiling air skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/sensor/air/*.java
	echo "				-compiling environment skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/sensor/environment/*.java
	echo "				-compiling food skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/sensor/food/*.java
	echo "				-compiling power skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/sensor/power/*.java
	echo "				-compiling water skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/sensor/water/*.java
	echo "			-compiling actuator skeletons/stubs"
	echo "				-compiling framework skeletons/stubs"
	$compilationInvocation $simSkeletonDir/idl/actuator/framework/*.java
fi
echo "		-compiling implementations"
echo "			-compiling framework implementation"
$compilationInvocation $serverDir/framework/*.java
echo "			-compiling util implementation"
$compilationInvocation $serverDir/util/*.java
echo "				-compiling log implementation"
$compilationInvocation $serverDir/util/log/*.java
echo "			-compiling simulation implementation"
echo "				-compiling framework implementation"
$compilationInvocation $serverDir/simulation/framework/*.java
echo "				-compiling air implementation"
$compilationInvocation $serverDir/simulation/air/*.java
echo "				-compiling water implementation"
$compilationInvocation $serverDir/simulation/water/*.java
echo "				-compiling power implementation"
$compilationInvocation $serverDir/simulation/power/*.java
echo "				-compiling crew implementation"
$compilationInvocation $serverDir/simulation/crew/*.java
echo "				-compiling food implementation"
$compilationInvocation $serverDir/simulation/food/*.java
echo "				-compiling environment implementation"
$compilationInvocation $serverDir/simulation/environment/*.java
echo "			-compiling actuator implementation"
echo "				-compiling framework implementation"
$compilationInvocation $serverDir/actuator/framework/*.java
echo "			-compiling sensor implementation"
echo "				-compiling framework implementation"
$compilationInvocation $serverDir/sensor/framework/*.java
echo "				-compiling air implementation"
$compilationInvocation $serverDir/sensor/air/*.java
echo "*done building biosim"



