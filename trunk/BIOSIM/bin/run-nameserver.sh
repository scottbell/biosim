#!/bin/bash

echo "*running nameserver"
echo "  -initializing"
devRootDir=$BIOSIM_HOME
jacoOrbClass="-Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB"
jacoSingletonOrbClass="-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton"
nameServer="org.jacorb.naming.NameServer"
currentDir=`pwd`
if [ -z "$devRootDir" ]
then
        cd ..
        devRootDir=`pwd`
        cd $currentDir
        echo "          -assuming BIOSIM_HOME is $devRootDir"
fi
java_command=$JAVA_HOME/bin/java
if [ -z "$JAVA_HOME" ]
then
        echo "          -JAVA_HOME not set, assuming java and javac are in path..."
        java_command="java"
fi
separator=":"
machineType=`uname`
winName="CYGWIN"
case $machineType in
        *$winName*) separator=";";echo "                -machine type is $winName";;
        *)separator=":";echo "          -assuming Unix machine type";;
esac
JACORB_HOME="$devRootDir/lib/jacorb"
genString="/generated"
genDir=$devRootDir$genString
if [ ! -e "$genDir" ]
then
        mkdir $genDir
        echo "          -creating generated directory"
fi
nsString="/ns"
nsDir=$genDir$nsString
if [ ! -e "$nsDir" ]
then
        mkdir $nsDir
        echo "          -creating ns directory"
fi
echo "  -invoking nameserver"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JACORB_HOME"
$java_command -classpath $jacoClasspath $jacoOrbClass $jacoSingletonOrbClass $nameServer $nsDir/ior.txt
