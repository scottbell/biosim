#!/bin/bash

echo "*building biosim client"
echo "	-initializing biosim build...";
userSelect="$@"
# see if the biosim directory exists, if it doesn't, assume it's one directory back (i.e., user is in bin directory)
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
	echo "		-JAVA_HOME not set! assuming java is in path..."
	java_command="java"
fi
JRE_HOME="$JAVA_HOME/jre"
IBM_libs="$JRE_HOME/lib/core.jar$separator$JRE_HOME/lib/charsets.jar$separator$JRE_HOME/lib/graphics.jar$separator$JRE_HOME/lib/security.jar$separator$JRE_HOME/lib/server.jar$separator$JRE_HOME/lib/xml.jar"
Sun_libs="$JRE_HOME/lib/rt.jar"
javaVersionString=`$java_command -version 2>&1 | grep IBM`
case $javaVersionString in
	*"IBM"*) JRE_libs=$IBM_libs;echo "		-VM is IBM";;
	*)JRE_lib=$Sun_libs;echo "		-assuming Sun VM";;
esac
genString="/generated"
genDir=$devRootDir$genString
if [ ! -e "$genDir" ]; then
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
#	CLIENT INIT             #
####################
echo "	-building client"
echo "		-initializing client build"
clientString="/client"
clientGenDir=$genDir$clientString
if [ ! -e  "$clientGenDir" ]; then
	mkdir $clientGenDir
	echo "			-creating client directory"
fi
stubString="/stubs"
stubDir=$clientGenDir$stubString
if [ ! -e  "$stubDir" ]; then
	mkdir $stubDir
	echo "			-creating stubs directory"
	echo "				-no stubs (switching to make all)"
	userSelect="all"
fi
clientClassesString="/classes"
clientClassesDir=$clientGenDir$clientClassesString
if [ ! -e  "$clientClassesDir" ]; then
	mkdir $clientClassesDir
	echo "			-creating classes directory"
fi
relativeIDLDir="/src/biosim/idl/biosim.idl"
fullIDLDir=$devRootDir$relativeIDLDir
idlInvocation="$java_command -classpath $JACORB_HOME/idl.jar org.jacorb.idl.parser"
if [ "$userSelect" == "all" ]; then
	echo "			-generating stubs"
	$idlInvocation  -noskel -d $stubDir $fullIDLDir
fi
#######################
#		Client COMPILATION	#
#######################
echo "		-compiling client";
simString="biosim"
simStubDir="$stubDir/$simString"
clientDir="$devRootDir/src/biosim/client"
sourceDir="$devRootDir/src"
plotClasspath="$devRootDir/lib/jfreechart/jcommon.jar$separator$devRootDir/lib/jfreechart/jfreechart.jar"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JRE_libs$separator$JACORB_HOME"
compilationInvocation="$javac_command -d $clientClassesDir -classpath $plotClasspath$separator$stubDir$separator$clientClassesDir$separator$sourceDir$separator$jacoClasspath"
if [ "$userSelect" == "all" ]; then
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
	echo "					-compiling log stubs"
	$compilationInvocation $simStubDir/idl/util/log/*.java
fi
echo "			-compiling util"
$compilationInvocation $clientDir/util/*.java
echo "				-compiling log"
$compilationInvocation $clientDir/util/log/*.java
echo "			-compiling air"
echo "				-compiling air.gui"
$compilationInvocation $clientDir/air/gui/*.java
echo "			-compiling water"
echo "				-compiling water.gui"
$compilationInvocation $clientDir/water/gui/*.java
echo "			-compiling power"
echo "				-compiling power.gui"
$compilationInvocation $clientDir/power/gui/*.java
echo "			-compiling environment"
echo "				-compiling environment.gui"
$compilationInvocation $clientDir/environment/gui/*.java
echo "			-compiling crew"
echo "				-compiling crew.gui"
$compilationInvocation $clientDir/crew/gui/*.java
echo "			-compiling food"
echo "				-compiling food.gui"
$compilationInvocation $clientDir/food/gui/*.java
echo "			-compiling framework"
$compilationInvocation $clientDir/framework/*.java
echo "				-compiling framework.gui"
$compilationInvocation $clientDir/framework/gui/*.java
echo "*done building biosim"



