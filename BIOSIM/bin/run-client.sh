#!/bin/bash

echo "*running client"
echo "	-initializing"
case $1 in
	"-ga") userSelect=$2;iorHome=$GA_HOME;;
	*) userSelect=$1;iorHome=$BIOSIM_HOME;;
esac
devRootDir=$BIOSIM_HOME
jacoOrbClass="-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB"
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
jacoNameIOR="-DORBInitRef.NameService=file:$iorHome/generated/ns/ior.txt"
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
malfunctionName="biosim.client.framework.gui.MalfunctionPanel"
stochasticName="biosim.client.framework.gui.StochasticPanel"
resourceString="/resources"
resourceDir=$devRootDir$resourceString
plotClasspath="$devRootDir/lib/jfreechart/jcommon.jar$separator$devRootDir/lib/jfreechart/jfreechart.jar"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JACORB_HOME"
jacoInvocation="$java_command -classpath $plotClasspath$separator$clientClassesDir$separator$jacoClasspath$separator$resourceDir $biosimHome $jacoOrbClass $jacoNameIOR"
echo "	-starting client"
console="console"
gui="gui"
help="-?"
logviewer="logviewer"
malfunction="malfunction"
stochastic="stochastic"
case $userSelect in
	$console) echo "			 -starting $userSelect";$jacoInvocation $driverName $console;;
	$gui) echo "			 -starting $userSelect";$jacoInvocation $driverName $gui;;
	$logviewer) echo "			 -starting $userSelect";$jacoInvocation $logviewerName;;
	$malfunction) echo "			 -starting $userSelect";$jacoInvocation $malfunctionName;;
	$stochastic) echo "			 -starting $userSelect";$jacoInvocation $stochasticName;;
	$help) echo "Usage: make-client.sh (-ga) [console, gui, logviewer, malfunction, stochastic]";;
	*) echo "			 -starting default";$jacoInvocation $driverName;;
esac
echo "*done invoking clients"



