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
javaVersionString=`$java_command -version 2>&1 | grep IBM`
case $javaVersionString in
	*"IBM"*)echo "	-VM is IBM";;
	*)java_command="$java_command";echo "		-assuming Sun VM";;
esac
JACORB_HOME="$devRootDir/lib/jacorb"
jacoNameIOR="-DORBInitRef.NameService=file:$devRootDir/tmp/ns/ior.txt"
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
buildDir="$devRootDir/build"
airRSName="com.traclabs.biosim.server.simulation.air.AirRSServer"
airStoreName="com.traclabs.biosim.server.simulation.air.AirStoreServer"
co2StoreName="com.traclabs.biosim.server.simulation.air.CO2StoreServer"
o2StoreName="com.traclabs.biosim.server.simulation.air.O2StoreServer"
simEnvironmentName="com.traclabs.biosim.server.simulation.environment.SimEnvironmentServer"
biomassRSName="com.traclabs.biosim.server.simulation.food.BiomassRSServer"
biomassStoreName="com.traclabs.biosim.server.simulation.food.BiomassStoreServer"
foodProcessorName="com.traclabs.biosim.server.simulation.food.FoodProcessorServer"
foodStoreName="com.traclabs.biosim.server.simulation.food.FoodStoreServer"
powerPSName="com.traclabs.biosim.server.simulation.power.PowerPSServer"
powerStoreName="com.traclabs.biosim.server.simulation.power.PowerStoreServer"
crewName="com.traclabs.biosim.server.simulation.crew.CrewGroupServer"
waterRSName="com.traclabs.biosim.server.simulation.water.WaterRSServer"
waterRSMatlabName="com.traclabs.biosim.server.simulation.water.WaterRSMatlabServer"
dirtyWaterStoreName="com.traclabs.biosim.server.simulation.water.DirtyWaterStoreServer"
potableWaterStoreName="com.traclabs.biosim.server.simulation.water.PotableWaterStoreServer"
greyWaterStoreName="com.traclabs.biosim.server.simulation.water.GreyWaterStoreServer"
frameworkName="com.traclabs.biosim.server.framework.BiosimServer"
initializerName="com.traclabs.biosim.server.framework.BioInitializer"
loggerName="com.traclabs.biosim.server.util.LoggerServer"
resourceDir="$devRootDir/resources"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JACORB_HOME$separator$JACORB_HOME/avalon-framework.jar$separator$JACORB_HOME/logkit.jar"
logCLasspath="$devRootDir/lib/log4j/log4j.jar"
xmlClasspath="$devRootDir/lib/xerces/xercesImpl.jar$separator$devRootDir/lib/xerces/xml-apis.jar$separator$devRootDir/lib/xerces/xmlParserAPIs.jar"
jacoInvocation="$java_command -classpath $buildDir$separator$resourceDir$separator$logCLasspath$separator$jacoClasspath$separator$xmlClasspath $machineTypeEnv $biosimHome $jacoOrbClass $jacoSingletonOrbClass $jacoNameIOR"
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



