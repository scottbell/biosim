#!/bin/bash

echo "*running server"
echo "	-initializing"
userSelect="$@"
devRootDir=$BIOSIM_HOME
JRE_HOME="$JAVA_HOME/jre"
jacoOrbClass="-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB"
jacoSingletonOrbClass="-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton"
if [ -z "$devRootDir" ]
then
	devRootDir=".."
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
airRSName="biosim.server.air.AirRSServer"
airStoreName="biosim.server.air.AirStoreServer"
co2StoreName="biosim.server.air.CO2StoreServer"
o2StoreName="biosim.server.air.O2StoreServer"
simEnvironmentName="biosim.server.environment.SimEnviornmentServer"
biomassRSName="biosim.server.food.BiomassRSServer"
biomassStoreName="biosim.server.food.BiomassStoreServer"
foodProcessorName="biosim.server.food.FoodProcessorServer"
foodStoreName="biosim.server.food.FoodStoreServer"
powerPSName="biosim.server.power.PowerPSServer"
powerStoreName="biosim.server.power.PowerStoreServer"
crewName="biosim.server.crew.CrewServer"
waterRSName="biosim.server.water.WaterRSServer"
dirtyWaterStoreName="biosim.server.water.DirtyWaterStoreServer"
potableWaterStoreName="biosim.server.water.PotableWaterStoreServer"
greyWaterStoreName="biosim.server.water.GreyWaterStoreServer"
frameworkName="biosim.server.framework.BioModuleServer"
jacoClasspath="$JACORB_HOME/lib/jacorb.jar$separator$JRE_HOME/lib/rt.jar$separator$JACORB_HOME/lib"
jacoInvocation="$java_command -server -classpath $serverClassesDir$separator$resourceDir$separator$jacoClasspath $jacoOrbClass $jacoSingletonOrbClass $jacoNameIOR"
echo "	-starting servers"
case $userSelect in
	airRS) echo "			 -starting $userSelect";$jacoInvocation $airRSName;;
	co2StoreName) echo "			 -starting $userSelect";$jacoInvocation $co2StoreName;;
	o2StoreName) echo "			 -starting $userSelect";$jacoInvocation $o2StoreName;;
	biomassRS) echo "			 -starting $userSelect";$jacoInvocation $biomassRSName;;
	biomassStore) echo "			 -starting $userSelect";$jacoInvocation $biomassStoreName;;
	foodProcessor) echo "			 -starting $userSelect";$jacoInvocation $foodProcessorName;;
	foodStoreName) echo "			 -starting $userSelect";$jacoInvocation $foodStoreName;;
	powerPS) echo "			 -starting $userSelect";$jacoInvocation $powerPSName;;
	powerStore) echo "			 -starting $userSelect";$jacoInvocation $powerStoreName;;
	crew) echo "			 -starting $userSelect";$jacoInvocation $crewName;;
	waterRS) echo "			 -starting $userSelect";$jacoInvocation $waterRSName;;
	dirtyWaterStore) echo "			 -starting $userSelect";$jacoInvocation $dirtyWaterStoreName;;
	potableWaterStore) echo "			 -starting $userSelect";$jacoInvocation $potableWaterStoreName;;
	greyWaterStore) echo "			 -starting $userSelect";$jacoInvocation $greyWaterStoreName;;
	simEnvironment) echo "			 -starting $userSelect";$jacoInvocation $simEnvironmentName;;
	all) echo "			-starting $userSelect";$jacoInvocation $frameworkName;;
	"-?") echo "choose from: [all, greyWaterStore, potableWaterStore, dirtyWaterStore, powerStore, powerPS, simEnvironment, foodStoreName, foodProcessor, airRS, o2StoreName, co2StoreName, biomassRS, biomassStore, crew, waterRS]";;
	*) echo "			-assuming all";$jacoInvocation $frameworkName;;
esac
echo "*done invoking servers"



