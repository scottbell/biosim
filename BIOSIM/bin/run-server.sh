#!/bin/bash

echo "*running server"
echo "	-initializing"
userSelect="$1"
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
#		SERVERS START	#
####################
genString="/generated"
genDir=$devRootDir$genString
serverGenString="/server"
serverGenDir=$genDir$serverGenString
serverClassesString="/classes"
serverClassesDir=$serverGenDir$serverClassesString
resourceString="/resources"
resourceDir=$devRootDir$resourceString
skeletonClassesDir="$serverGenDir/skeletons"
serverDir="$devRootDir/biosim/server"
airRSName="biosim.server.simulation.air.AirRSServer"
airStoreName="biosim.server.simulation.air.AirStoreServer"
co2StoreName="biosim.server.simulation.air.CO2StoreServer"
o2StoreName="biosim.server.simulation.air.O2StoreServer"
simEnvironmentName="biosim.server.simulation.environment.SimEnvironmentServer"
biomassRSName="biosim.server.simulation.food.BiomassRSServer"
biomassStoreName="biosim.server.simulation.food.BiomassStoreServer"
foodProcessorName="biosim.server.simulation.food.FoodProcessorServer"
foodStoreName="biosim.server.simulation.food.FoodStoreServer"
powerPSName="biosim.server.simulation.power.PowerPSServer"
powerStoreName="biosim.server.simulation.power.PowerStoreServer"
crewName="biosim.server.simulation.crew.CrewGroupServer"
waterRSName="biosim.server.simulation.water.WaterRSServer"
dirtyWaterStoreName="biosim.server.simulation.water.DirtyWaterStoreServer"
potableWaterStoreName="biosim.server.simulation.water.PotableWaterStoreServer"
greyWaterStoreName="biosim.server.simulation.water.GreyWaterStoreServer"
frameworkName="biosim.server.framework.BiosimServer"
loggerName="biosim.server.util.LoggerServer"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JACORB_HOME"
jacoInvocation="$java_command -classpath $serverClassesDir$separator$resourceDir$separator$jacoClasspath $machineTypeEnv $biosimHome $jacoOrbClass $jacoSingletonOrbClass $jacoNameIOR"
nojitOption="-Djava.compiler=none"
case $userSelect in
	"-nojit") echo "		-starting with no JIT";jacoInvocation="$jacoInvocation $nojitOption";userSelect=$2;;
esac
echo "	-starting servers"
case $userSelect in
	airRS) echo "			 -starting $userSelect";$jacoInvocation $airRSName;;
	co2Store) echo "			 -starting $userSelect";$jacoInvocation $co2StoreName;;
	o2Store) echo "			 -starting $userSelect";$jacoInvocation $o2StoreName;;
	biomassRS) echo "			 -starting $userSelect";$jacoInvocation $biomassRSName;;
	biomassStore) echo "			 -starting $userSelect";$jacoInvocation $biomassStoreName;;
	foodProcessor) echo "			 -starting $userSelect";$jacoInvocation $foodProcessorName;;
	foodStore) echo "			 -starting $userSelect";$jacoInvocation $foodStoreName;;
	powerPS) echo "			 -starting $userSelect";$jacoInvocation $powerPSName;;
	powerStore) echo "			 -starting $userSelect";$jacoInvocation $powerStoreName;;
	crew) echo "			 -starting $userSelect";$jacoInvocation $crewName;;
	waterRS) echo "			 -starting $userSelect";$jacoInvocation $waterRSName;;
	dirtyWaterStore) echo "			 -starting $userSelect";$jacoInvocation $dirtyWaterStoreName;;
	potableWaterStore) echo "			 -starting $userSelect";$jacoInvocation $potableWaterStoreName;;
	greyWaterStore) echo "			 -starting $userSelect";$jacoInvocation $greyWaterStoreName;;
	simEnvironment) echo "			 -starting $userSelect";$jacoInvocation $simEnvironmentName;;
	logger) echo "			 -starting $userSelect";$jacoInvocation $loggerName;;
	all) echo "			-starting $userSelect";$jacoInvocation $frameworkName;;
	"-?") echo "choose from: [all, logger, greyWaterStore, potableWaterStore, dirtyWaterStore, powerStore, powerPS, simEnvironment, foodStore, foodProcessor, airRS, o2Store, co2Store, biomassRS, biomassStore, crew, waterRS]";;
	*) echo "			-assuming all";$jacoInvocation $frameworkName;;
esac
echo "*done invoking servers"



