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
JACORB_HOME="$devRootDir/lib/jacorb"
jacoClasspath="$JACORB_HOME/lib/jacorb.jar:$JRE_HOME/lib/rt.jar"
####################
#		SERVERS START	#
####################
genString="/generated"
genDir=$devRootDir$genString
serverGenString="/server"
serverGenDir=$genDir$serverGenString
serverClassesString="/classes"
serverClassesDir=$serverGenDir$serverClassesString
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
jacoInvocation="java -classpath $serverClassesDir:$jacoClasspath:$CLASSPATH $jacoOrbClass $jacoSingletonOrbClass"
echo "	-starting servers"
case $userSelect in
	AirRS) echo "			 -starting $userSelect";$jacoInvocation $airRSName;;
	AirStore) echo "			 -starting $userSelect";$jacoInvocation $airStoreName;;
	BiomassRS) echo "			 -starting $userSelect";$jacoInvocation $biomassRSName;;
	BiomassStore) echo "			 -starting $userSelect";$jacoInvocation $biomassStoreName;;
	EnergyPS) echo "			 -starting $userSelect";$jacoInvocation $energyPSName;;
	EnergyStore) echo "			 -starting $userSelect";$jacoInvocation $energyStoreName;;
	Crew) echo "			 -starting $userSelect";$jacoInvocation $crewName;;
	WaterRS) echo "			 -starting $userSelect";$jacoInvocation $waterRSName;;
	WaterStore) echo "			 -starting $userSelect";$jacoInvocation $waterStoreName;;
	*) echo "!!!! unkown server: $userSelect";echo "please choose from: [AirRS, AirStore, BiomassRS, BiomassStore, EnergyPS, EnergyStore, Crew, WaterRS, WaterStore]";;
esac
echo "*done invoking servers"



