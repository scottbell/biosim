#!/bin/bash

echo "*running client"
echo "	-initializing"
userSelect="$@"
devRootDir=$BIOSIM_HOME
JRE_HOME="$JAVA_HOME/jre"
jacoOrbClass="-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB"
jacoSingletonOrbClass="-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton"
biosimHome="-DBIOSIM_HOME=$BIOSIM_HOME"
currentDir=`pwd`
if [ -z "$devRootDir" ]
then
	cd ..
	devRootDir=`pwd`
	cd $currentDir
	echo "		-assuming BIOSIM_HOME is $devRootDir"
fi
java_command=$JAVA_HOME/bin/java
if [ -z "$JAVA_HOME" ]
then
	echo "		-JAVA_HOME not set, assuming java and javac are in path..."
	java_command="java"
fi
JACORB_HOME="$devRootDir/lib/jacorb"
#jacoNameIOR="-DORBInitRef.NameService=file:$devRootDir/generated/ns/ior.txt"
jacoNameIOR="-DORBInitRef.NameService=file:$devRootDir/generated/ns/ior.txt"
separator=":"
machineType=`uname`
winName="CYGWIN"
case $machineType in
	*$winName*) separator=";";echo "		-machine type is $winName";;
	*)separator=":";echo "		-assuming Unix machine type";;
esac
machineTypeEnv="-DMACHINE_TYPE=$machineType"
####################
#		CLIENTS START	#
####################
genString="/generated"
genDir=$devRootDir$genString
clientGenString="/client"
clientGenDir=$genDir$clientGenString
clientClassesString="/classes"
clientClassesDir=$clientGenDir$clientClassesString
stubsClassesDir="$clientGenDir/stubs"
clientDir="$devRootDir/src/biosim/client"
driverName="biosim.client.framework.BiosimMain"
logviewerName="biosim.client.util.log.LogViewer"
resourceString="/resources"
resourceDir=$devRootDir$resourceString
plotClasspath="$devRootDir/lib/jfreechart/jcommon.jar$separator$devRootDir/lib/jfreechart/junit.jar$separator$devRootDir/lib/jfreechart/jfreechart.jar"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JRE_HOME/lib/rt.jar$separator$JACORB_HOME"
jacoInvocation="$java_command -client -classpath $plotClasspath$separator$clientClassesDir$separator$jacoClasspath$separator$resourceDir $biosimHome $jacoOrbClass $jacoSingletonOrbClass $jacoNameIOR"
echo "	-starting client"
console="console"
gui="gui"
logviewer="logviewer"
case $userSelect in
	$console) echo "			 -starting $userSelect";$jacoInvocation $driverName $console;;
	$gui) echo "			 -starting $userSelect";$jacoInvocation $driverName $gui;;
	$logviewer) echo "			 -starting $userSelect";$jacoInvocation $logviewerName;;
	*) echo "			 -starting default (available options are: console, gui, logviewer)";$jacoInvocation $driverName;;
esac
echo "*done invoking clients"



