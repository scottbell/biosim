#!/bin/bash

userSelect="$@"
devRootDir=$BIOSIM_HOME
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
####################
# CLIENTS START	   #
####################
testClasspath="$devRootDir/lib/log4j/log4j.jar:$devRootDir/build"
javaInvocation="$java_command -classpath $testClasspath"
backendProgram="com.traclabs.biosim.server.util.MatlabTestBackend"
$javaInvocation $backendProgram $1 $2



