#!/bin/bash

echo "*running client"
echo "	-initializing"
case $1 in
	"-ga") userSelect=$2;idSelect=$3;iorHome=$GA_HOME;;
	*) userSelect=$1;idSelect="$2";iorHome=$BIOSIM_HOME;;
esac
devRootDir=$BIOSIM_HOME
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
jacoNameIOR="-DORBInitRef.NameService=file:$iorHome/generated/ns/ior.txt"
separator=":"
machineType=`uname -s`
winName="CYGWIN"
linuxName="Linux"
macName="Darwin"
sim3DLibDir="$devRootDir/lib/sim3D"
case $machineType in
	*$winName*) separator=";";joglDir="$sim3DLibDir/win32";echo "		-machine type is $winName(windows)";;
	*$linuxName*) separator=":";joglDir="$sim3DLibDir/linux";echo "		-machine type is $linuxName";;
	*$macName*) separator=":";joglDir="$sim3DLibDir/mac";echo "		-machine type is $macName(macintosh)";;
	*)separator=":";echo "		-assuming Unix machine type";;
esac
machineTypeEnv="-DMACHINE_TYPE=$machineType"
native3DDir="-Djava.library.path=$joglDir"
####################
# CLIENTS START	   #
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
sensorName="biosim.client.sensor.framework.gui.SensorViewer"
resourceString="/resources"
resourceDir=$devRootDir$resourceString
plotClasspath="$devRootDir/lib/jfreechart/jcommon.jar$separator$devRootDir/lib/jfreechart/jfreechart.jar"
xmlClasspath="$devRootDir/lib/xerces/xercesImpl.jar$separator$devRootDir/lib/xerces/xml-apis.jar$separator$devRootDir/lib/xerces/xmlParserAPIs.jar"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JACORB_HOME"
sim3DClasspath="$sim3DLibDir/joal.jar$separator$joglDir/jogl.jar$separator$sim3DLibDir/junit.jar$separator$sim3DLibDir/log4j.jar$separator$sim3DLibDir/vecmath.jar$separator$sim3DLibDir/vorbis.jar$separator$sim3DLibDir/xith3d.jar"
jacoInvocation="$java_command -classpath $plotClasspath$separator$clientClassesDir$separator$jacoClasspath$separator$resourceDir$separator$xmlClasspath$separator$sim3DClasspath $biosimHome $jacoSingletonOrbClass $native3DDir $jacoOrbClass $jacoNameIOR"
echo "	-starting client"
console="console"
gui="gui"
help="-?"
logviewer="logviewer"
malfunction="malfunction"
stochastic="stochastic"
controller="controller"
sim3D="3D"
sensor="sensor"
case $userSelect in
	$console) echo "			 -starting $userSelect";$jacoInvocation $driverName $console;;
	$controller) echo "			 -starting $userSelect";$jacoInvocation $driverName $controller;;
	$gui) echo "			 -starting $userSelect";$jacoInvocation $driverName $gui;;
	$logviewer) echo "			 -starting $userSelect";$jacoInvocation $logviewerName;;
	$malfunction) echo "			 -starting $userSelect";$jacoInvocation $malfunctionName;;
	$stochastic) echo "			 -starting $userSelect";$jacoInvocation $stochasticName;;
	$sensor) echo "			 -starting $userSelect";$jacoInvocation $sensorName;;
	$sim3D) echo "			 -starting $userSelect";$jacoInvocation $driverName $sim3D;;
	$help) echo "Usage: run-client.sh (-ga) [console, gui, logviewer, malfunction, stochastic, controller, 3D]";;
	"-id"*) echo "			-assuming all (id user specified)";$jacoInvocation $driverName $1;;
	"-xml"*) echo "			-assuming all (xml init user specified)";$jacoInvocation $driverName $1 $2;;
	*) echo "			 -starting default";$jacoInvocation $driverName;;
esac
echo "*done invoking clients"



