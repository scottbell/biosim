#!/bin/bash

echo "*running biosim name manager"
echo "	-initializing"
userSelect="$@"
devRootDir=$BIOSIM_HOME
jacoOrbClass="-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB"
jacoSingletonOrbClass="-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton"
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
tmpDir="$devRootDir/tmp"
jacoNameIOR="-DORBInitRef.NameService=file:$tmpDir/ns/ior.txt"
separator=":"
machineType=`uname`
winName="CYGWIN"
case $machineType in
	*$winName*) separator=";";echo "		-machine type is $winName";;
	*)separator=":";echo "		-assuming Unix machine type";;
esac
####################
# CLIENTS START	   #
####################
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JACORB_HOME$separator$JACORB_HOME/avalon-framework.jar$separator$JACORB_HOME/logkit.jar"
jacoInvocation="$java_command -classpath $jacoClasspath $jacoOrbClass $jacoSingletonOrbClass $jacoNameIOR"
nameManager="org.jacorb.naming.namemanager.NameManager"
echo "	-starting biosim name manager"
$jacoInvocation $nameManager
echo "*done invoking biosim name manager"


