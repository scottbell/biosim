#!/bin/bash

echo "*running client"
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
controlName="biosim.client.control.BioSimulator"
separator=":"
serverType=`uname`
if [ "$serverType" == "CYGWIN_NT-5.1" ]
then
	separator=";"
fi
jacoClasspath="$JACORB_HOME/lib/jacorb.jar$separator$JRE_HOME/lib/rt.jar"
jacoInvocation="java -classpath $clientClassesDir$separator$jacoClasspath$separator$CLASSPATH $jacoOrbClass $jacoSingletonOrbClass"
echo "	-starting client"
case $userSelect in
	control) echo "			 -starting $userSelect";$jacoInvocation $controlName;;
	*) echo "!!!! unkown client: $userSelect";echo "please choose from: [control]";;
esac
echo "*done invoking clients"



