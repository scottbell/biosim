#!/bin/bash

echo "*running nameserver"
echo "	-initializing"
devRootDir=$BIOSIM_HOME
JRE_HOME="$JAVA_HOME/jre"
jacoOrbClass="-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB"
jacoSingletonOrbClass="-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton"
nameServer="org.jacorb.naming.NameServer"
if [ -z "$devRootDir" ]
then
	devRootDir=".."
	echo "		-assuming BIOSIM_HOME is $devRootDir"
fi
JACORB_HOME="$devRootDir/lib/jacorb"
genString="/generated"
genDir=$devRootDir$genString
if [ ! -e "$genDir" ]
then
	mkdir $genDir
	echo "		-creating generated directory"
fi
nsString="/ns"
nsDir=$genDir$nsString
if [ ! -e "$nsDir" ]
then
	mkdir $nsDir
	echo "		-creating ns directory"
fi
echo "	-invoking nameserver"
separator=":"
serverType=`uname`
if [ "$serverType" == "CYGWIN_NT-5.1" ]
then
	separator=";"
fi
jacoClasspath="$JACORB_HOME/lib/jacorb.jar$separator$JRE_HOME/lib/rt.jar"
java -classpath $jacoClasspath $jacoOrbClass $jacoSingletonOrbClass  $nameServer $nsDir/ior.txt
echo "*done with nameserver"
