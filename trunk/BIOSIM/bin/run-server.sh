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
biomassRSName="biosim.server.biomass.BiomassRSServer"
biomassStoreName="biosim.server.biomass.BiomassStoreServer"
energyPSName="biosim.server.energy.EnergyPSServer"
energyStoreName="biosim.server.energy.EnergyStoreServer"
crewName="biosim.server.crew.CrewServer"
waterRSName="biosim.server.water.WaterRSServer"
waterStoreName="biosim.server.water.WaterStoreServer"
frameworkName="biosim.server.framework.FrameworkServer"
jacoClasspath="$JACORB_HOME/lib/jacorb.jar$separator$JRE_HOME/lib/rt.jar$separator$JACORB_HOME/lib"
jacoInvocation="$java_command -server -classpath $serverClassesDir$separator$resourceDir$separator$jacoClasspath $jacoOrbClass $jacoSingletonOrbClass $jacoNameIOR"
echo "	-starting servers"
case $userSelect in
	airRS) echo "			 -starting $userSelect";$jacoInvocation $airRSName;;
	airStore) echo "			 -starting $userSelect";$jacoInvocation $airStoreName;;
	biomassRS) echo "			 -starting $userSelect";$jacoInvocation $biomassRSName;;
	biomassStore) echo "			 -starting $userSelect";$jacoInvocation $biomassStoreName;;
	energyPS) echo "			 -starting $userSelect";$jacoInvocation $energyPSName;;
	energyStore) echo "			 -starting $userSelect";$jacoInvocation $energyStoreName;;
	crew) echo "			 -starting $userSelect";$jacoInvocation $crewName;;
	waterRS) echo "			 -starting $userSelect";$jacoInvocation $waterRSName;;
	waterStore) echo "			 -starting $userSelect";$jacoInvocation $waterStoreName;;
	framework) echo "			-starting $userSelect";$jacoInvocation $frameworkName;;
	*) echo "!!!! unkown server: $userSelect";echo "please choose from: [airRS, airStore, biomassRS, biomassStore, energyPS, energyStore, crew, waterRS, waterStore]";;
esac
echo "*done invoking servers"



