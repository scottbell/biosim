#!/bin/bash

echo "*building biosim distro"
echo "	-initializing biosim distro build...";
userSelect="$@"
# see if the biosim directory exists, if it doesn't, assume it's one directory back (i.e., user is in bin directory)
devRootDir=$BIOSIM_HOME
if [ -z "$devRootDir" ]
then
	devRootDir=".."
	echo "		-assuming BIOSIM_HOME is $devRootDir"
fi
JACORB_HOME="$devRootDir/lib/jacorb"
jarCommand="jar"
type $jarCommand 2> /dev/null >/dev/null
if [ $? != 0 ]; then
	echo "		-couldn't find jar command!"
	#should quit here
fi
genString="/generated"
genDir=$devRootDir$genString
if [ ! -e "$genDir" ]; then
	mkdir $genDir
	echo "		-creating generated directory"
fi
genString="/distro"
distroDir=$genDir$genString
if [ ! -e "$distroDir" ]; then
	mkdir $distroDir
	echo "		-creating distro directory"
fi
clientString="/client"
clientGenDir=$genDir$clientString
serverString="/server"
serverGenDir=$genDir$serverString
serverClassesString="/classes/biosim"
serverClassesDir=$serverGenDir$serverClassesString
clientClassesString="/classes/biosim"
clientClassesDir=$clientGenDir$clientClassesString
echo "		-expanding library jars"
distroTmp="$distroDir/tmp"
if [ ! -e "$distroTmp" ]; then
	mkdir $distroTmp
	echo "		-creating distro tmp directory"
fi
echo "			-changing dir to distro tmp"
currentDir=`pwd`
cd $distroTmp
jarExpand="$jarCommand -xf"
echo "			-expanding jcommon"
jcommonPath="$devRootDir/lib/jfreechart/jcommon.jar"
$jarExpand $jcommonPath
echo "			-expanding junit"
junitPath="$devRootDir/lib/jfreechart/junit.jar"
$jarExpand $junitPath
echo "			-expanding jfreechart"
jfreechartPath="$devRootDir/lib/jfreechart/jfreechart.jar"
$jarExpand $jfreechartPath
echo "			-expanding jacorb"
jacorbPath="$devRootDir/lib/jacorb/jacorb.jar"
$jarExpand $jacorbPath
echo "			-removing manifest"
rm -Rf $distroTmp/META-INF
echo "			-copying jacorb.properties"
cp $devRootDir/lib/jacorb/jacorb.properties .
echo "			-copying resources"
cp -R $devRootDir/resources/biosim .
echo "			-copying server classes"
cp -f -R $serverClassesDir .
echo "			-copying client classes"
cp -f -R $clientClassesDir .
####################
# DISTRO BUILD     #
####################
jarCompress="$jarCommand -cvf"
echo "	-creating jars"
echo "		-creating biosim jar"
$jarCompress biosim.jar * > distroOutput.txt
echo "		-copying biosim jar to distro directory"
cp -f biosim.jar ..
echo "		-changing to distro dir"
cd ..
echo "		-removing distro tmp"
rm -Rf $distroTmp
echo "		-changing to back to invocation dir"
cd $currentDir
echo "*done creating biosim distro"



