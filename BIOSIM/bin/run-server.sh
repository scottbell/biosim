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
waterRSMatlabName="biosim.server.simulation.water.WaterRSMatlabServer"
dirtyWaterStoreName="biosim.server.simulation.water.DirtyWaterStoreServer"
potableWaterStoreName="biosim.server.simulation.water.PotableWaterStoreServer"
greyWaterStoreName="biosim.server.simulation.water.GreyWaterStoreServer"
frameworkName="biosim.server.framework.BiosimServer"
initializerName="biosim.server.framework.BioInitializer"
loggerName="biosim.server.util.LoggerServer"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JACORB_HOME$separator$JACORB_HOME/avalon-framework.jar$separator$JACORB_HOME/logkit.jar"
xmlClasspath="$devRootDir/lib/xerces/xercesImpl.jar$separator$devRootDir/lib/xerces/xml-apis.jar$separator$devRootDir/lib/xerces/xmlParserAPIs.jar"
jacoInvocation="$java_command -classpath $serverClassesDir$separator$resourceDir$separator$jacoClasspath$separator$xmlClasspath $machineTypeEnv $biosimHome $jacoOrbClass $jacoSingletonOrbClass $jacoNameIOR"
nojitOption="-Djava.compiler=none"
case $userSelect in
	"-nojit") echo "		-starting with no JIT";jacoInvocation="$jacoInvocation $nojitOption";userSelect=$2;;
esac
echo "	-starting servers"
case $userSelect in
	initializer) echo "			 -starting $userSelect";$jacoInvocation $initializerName $1 $2 $3;;
	airRS) echo "			 -starting $userSelect";$jacoInvocation $airRSName $1 $2 $3;;
	co2Store) echo "			 -starting $userSelect";$jacoInvocation $co2StoreName $1 $2 $3;;
	o2Store) echo "			 -starting $userSelect";$jacoInvocation $o2StoreName $1 $2 $3;;
	biomassRS) echo "			 -starting $userSelect";$jacoInvocation $biomassRSName $1 $2 $3;;
	biomassStore) echo "			 -starting $userSelect";$jacoInvocation $biomassStoreName $1 $2 $3;;
	foodProcessor) echo "			 -starting $userSelect";$jacoInvocation $foodProcessorName $1 $2 $3;;
	foodStore) echo "			 -starting $userSelect";$jacoInvocation $foodStoreName $1 $2 $3;;
	powerPS) echo "			 -starting $userSelect";$jacoInvocation $powerPSName $1 $2 $3;;
	powerStore) echo "			 -starting $userSelect";$jacoInvocation $powerStoreName $1 $2 $3;;
	crew) echo "			 -starting $userSelect";$jacoInvocation $crewNam $1 $2 $3e;;
	waterRS) echo "			 -starting $userSelect";$jacoInvocation $waterRSName $1 $2 $3;;
	waterRSMatlab) echo "			 -starting $userSelect";$jacoInvocation $waterRSMatlabName WaterRS $1 $2 $3;;
	dirtyWaterStore) echo "			 -starting $userSelect";$jacoInvocation $dirtyWaterStoreName $1 $2 $3;;
	potableWaterStore) echo "			 -starting $userSelect";$jacoInvocation $potableWaterStoreName $1 $2 $3;;
	greyWaterStore) echo "			 -starting $userSelect";$jacoInvocation $greyWaterStoreName $1 $2 $3;;
	simEnvironment) echo "			 -starting $userSelect";$jacoInvocation $simEnvironmentName $1 $2 $3;;
	logger) echo "			 -starting $userSelect";$jacoInvocation $loggerName $1 $2 $3;;
	all) echo "			-starting $userSelect";$jacoInvocation $frameworkName $1 $2 $3;;
	"-?") echo -e "choose from: [all, logger, greyWaterStore, potableWaterStore, dirtyWaterStore, powerStore, powerPS, simEnvironment, foodStore, foodProcessor, airRS, o2Store, co2Store, biomassRS, biomassStore, crew, waterRS]\nOptions include -id=(int) -xml=(string) -name=(string)\r\nExample: run-server.sh airRS -name=AirRS0 -id=3 -xml=/home/scott/init.xml";;
	"-id"*) echo "			-assuming all (id user specified)";$jacoInvocation $frameworkName $1 $2 $3;;
	"-xml"*) echo "			-assuming all (xml init user specified)";$jacoInvocation $frameworkName $1 $2 $3;;
	*) echo "			-assuming all";$jacoInvocation $frameworkName;;
esac
echo "*done invoking servers"



