#!/bin/bash

echo "*building biosim distro"
echo "	-initializing biosim distro build...";
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
	echo "		-building biosim"
	$devRootDir/bin/make-server.sh
	$devRootDir/bin/make-client.sh
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
cd $distroTmp
jarExpand="$jarCommand -xf"
echo "			-expanding jcommon"
jcommonPath="$devRootDir/lib/jfreechart/jcommon.jar"
$jarExpand $jcommonPath
echo "			-expanding jfreechart"
jfreechartPath="$devRootDir/lib/jfreechart/jfreechart.jar"
$jarExpand $jfreechartPath
echo "			-expanding jacorb"
jacorbPath="$devRootDir/lib/jacorb/jacorb.jar"
$jarExpand $jacorbPath
echo "			-expanding xerces"
xercesImplPath="$devRootDir/lib/xerces/xercesImpl.jar"
xercesApisPath="$devRootDir/lib/xerces/xml-apis.jar"
xercesParserPath="$devRootDir/lib/xerces/xmlParserAPIs.jar"
$jarExpand $xercesImplPath
$jarExpand $xercesApisPath
$jarExpand $xercesParserPath
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
$jarCompress biosim.jar * > /dev/null
echo "		-copying biosim jar to distro directory"
cp -f biosim.jar ..
echo "		-changing to distro dir"
cd ..
echo "		-removing distro tmp"
rm -Rf $distroTmp
echo "		-copying invocation files over"
cp -f ../../lib/distro/win/biosim.nsi . 2> /dev/null
cp -f ../../lib/distro/win/setENV.bat . 2> /dev/null
cp -f ../../lib/distro/win/run-biosim.bat . 2> /dev/null
cp -f ../../lib/distro/win/run-biosim-debug.bat . 2> /dev/null
cp -f ../../lib/distro/win/biosim.ico . 2> /dev/null
cp -f ../../lib/distro/win/LICENSE.txt . 2> /dev/null
cp -f ../../lib/distro/win/BiosimStandalone.java . 2> /dev/null
cp -f ../../lib/distro/win/mars.gif . 2> /dev/null
######################
# BUILD STANDALONE   #
######################
echo "	-building standalone"
jikesCommand="jikes"
type $jikesCommand 2> /dev/null >/dev/null
if [ $? != 0 ]; then
	javac_command=javac
	echo "		-using javac compiler (assuming it's in the path)"
else
	javac_command=jikes
	echo "		-using jikes compiler"
fi
if [ -z "$JAVA_HOME" ]; then
	echo "		-JAVA_HOME not set! assuming java is in path..."
fi
JRE_HOME="$JAVA_HOME/jre"
java_command=$JAVA_HOME/bin/java
biosimJar="$distroDir/biosim.jar"
separator=":"
machineType=`uname`
winName="CYGWIN"
case $machineType in
	*$winName*) separator=";";echo "	-machine type is $winName";;
	*)separator=":";echo "		-assuming Unix machine type";;
esac
IBM_libs="$JRE_HOME/lib/core.jar$separator$JRE_HOME/lib/charsets.jar$separator$JRE_HOME/lib/graphics.jar$separator$JRE_HOME/lib/security.jar$separator$JRE_HOME/lib/server.jar$separator$JRE_HOME/lib/xml.jar"
Sun_libs="$JRE_HOME/lib/rt.jar"
javaVersionString=`$java_command -version 2>&1 | grep IBM`
case $javaVersionString in
	*"IBM"*) JRE_libs=$IBM_libs;echo "		-VM is IBM";;
	*)JRE_libs=$Sun_libs;echo "		-assuming Sun VM";;
esac
echo "		-compiling standalone";
$javac_command -classpath $distroDir$separator$biosimJar$separator$JRE_libs $distroDir/BiosimStandalone.java
echo "	-done building standalone"
echo "	-adding to existing jar"
$jarCommand -uf biosim.jar BiosimStandalone*.class
$jarCommand -uf biosim.jar mars.gif
echo "	-removing standalone file"
rm -f BiosimStandalone*
rm -f mars.gif
echo "	-changing to back to invocation dir"
cd $currentDir
echo "*done creating biosim distro"




