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
javaVersionString=`$java_command -version 2>&1 | grep IBM`
case $javaVersionString in
	*"IBM"*)echo "	-VM is IBM";;
	*)java_command="$java_command -client";echo "		-assuming Sun VM";;
esac
JACORB_HOME="$devRootDir/lib/jacorb"
jacoNameIOR="-DORBInitRef.NameService=file:$iorHome/tmp/ns/ior.txt"
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
buildDir="$devRootDir/build"
mainName="com.traclabs.biosim.client.framework.BiosimMain"
logviewerName="com.traclabs.biosim.client.util.log.LogViewer"
malfunctionName="com.traclabs.biosim.client.framework.gui.MalfunctionPanel"
stochasticName="com.traclabs.biosim.client.framework.gui.StochasticPanel"
sensorName="com.traclabs.biosim.client.sensor.framework.gui.SensorViewer"
testName="TestBiosim"
resourceDir="$devRootDir/resources"
plotClasspath="$devRootDir/lib/jfreechart/jcommon.jar$separator$devRootDir/lib/jfreechart/jfreechart.jar"
xmlClasspath="$devRootDir/lib/xerces/xercesImpl.jar$separator$devRootDir/lib/xerces/xml-apis.jar$separator$devRootDir/lib/xerces/xmlParserAPIs.jar"
logCLasspath="$devRootDir/lib/log4j/log4j.jar"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JACORB_HOME$separator$JACORB_HOME/avalon-framework.jar$separator$JACORB_HOME/logkit.jar"
jacoInvocation="$java_command -classpath $buildDir$separator$plotClasspath$separator$logCLasspath$separator$jacoClasspath$separator$resourceDir$separator$xmlClasspath $biosimHome $jacoSingletonOrbClass $native3DDir $jacoOrbClass $jacoNameIOR"
echo "	-starting client"
console="console"
gui="gui"
help="-?"
logviewer="logviewer"
malfunction="malfunction"
stochastic="stochastic"
case $userSelect in
	$console) echo "			 -starting $userSelect";$jacoInvocation $mainName $1 $2 $3 $4;;
	$controller) echo "			 -starting $userSelect";$jacoInvocation $mainName $1 $2 $3 $4;;
	$gui) echo "			 -starting $userSelect";$jacoInvocation $mainName $1 $2 $3 $4;;
	$logviewer) echo "			 -starting $userSelect";$jacoInvocation $logviewerName $1 $2 $3 $4;;
	$malfunction) echo "			 -starting $userSelect";$jacoInvocation $malfunctionName $1 $2 $3 $4;;
	$stochastic) echo "			 -starting $userSelect";$jacoInvocation $stochasticName $1 $2 $3 $4;;
	$sensor) echo "			 -starting $userSelect";$jacoInvocation $sensorName;;
	$debug) echo "			 -starting $userSelect";$jacoInvocation $mainName $1 $2 $3 $4;;
	$unreal) echo "			 -starting $userSelect";$jacoInvocation $mainName $1 $2 $3 $4;;
	$test) echo "			 -starting $userSelect";$jacoInvocation $testName $1 $2 $3 $4;;
	$help) echo "Usage: run-client.sh (-ga) [console, gui, logviewer, malfunction, stochastic, controller, 3D]";;
	"-id"*) echo "			-assuming all (id user specified)";$jacoInvocation $mainName $1 $2 $3 $4;;
	"-xml"*) echo "			-assuming all (xml init user specified)";$jacoInvocation $mainName $1 $2 $3 $4;;
	*) echo "			 -starting $userSelect";$jacoInvocation $mainName $1 $2 $3 $4;;
esac
echo "*done invoking clients"


