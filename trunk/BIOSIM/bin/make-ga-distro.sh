#!/bin/bash

echo "*building biosim ga distro"
echo "	-initializing biosim ga distro build...";
userSelect="$@"
# see if the biosim directory exists, if it doesn't, assume it's one directory back (i.e., user is in bin directory)
devRootDir=$BIOSIM_HOME
currentDir=`pwd`
if [ -z "$devRootDir" ]
then
	cd ..
	devRootDir=`pwd`
	cd $currentDir
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
genString="/ga-distro"
distroDir=$genDir$genString
if [ ! -e "$distroDir" ]; then
	mkdir $distroDir
	echo "		-creating ga-distro directory"
fi
clientString="/client"
clientGenDir=$genDir$clientString
serverString="/server"
serverGenDir=$genDir$serverString
serverClassesString="/classes/biosim"
serverClassesDir=$serverGenDir$serverClassesString
echo "		-expanding library jars"
distroTmp="$distroDir/tmp"
if [ ! -e "$distroTmp" ]; then
	mkdir $distroTmp
	echo "		-creating distro tmp directory"
fi
echo "			-changing dir to distro tmp"
cd $distroTmp
jarExpand="$jarCommand -xf"
echo "			-removing manifest"
rm -Rf $distroTmp/META-INF
echo "			-copying resources"
cp -R $devRootDir/resources/biosim .
echo "			-copying server classes"
cp -f -R $serverClassesDir .
####################
# DISTRO BUILD     #
####################
jarCompress="$jarCommand -cvf"
echo "	-creating jars"
echo "		-creating biosim jar"
$jarCompress biosim.jar * > /dev/null
echo "		-copying biosim jar to distro directory"
cp -f biosim.jar ..
echo "		-changing to distro dir"
cd ..
echo "		-removing distro tmp"
rm -Rf $distroTmp
echo "		-changing to back to invocation dir"
cd $currentDir
echo "*done creating biosim ga-distro"



