#!/bin/bash

echo "*building biosim documentation"
echo "	-initializing biosim documentation build...";
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
javadocCommand="javadoc"
type $javadocCommand 2> /dev/null >/dev/null
if [ $? != 0 ]; then
	echo "		-couldn't find javadoc command!"
	#should quit here
fi

docString="/doc"
docDir=$devRootDir$docString
apiString="/api"
apiDir=$docDir$apiString
if [ ! -e "$apiDir" ]; then
	mkdir $apiDir
	echo "		-creating api documents directory"
fi
separator=":"
machineType=`uname`
winName="CYGWIN"
case $machineType in
	*$winName*) separator=";";echo "		-machine type is $winName";;
	*)separator=":";echo "		-assuming Unix machine type";;
esac
genString="/generated"
genDir=$devRootDir$genString
clientString="/client"
clientGenDir=$genDir$clientString
serverString="/server"
serverGenDir=$genDir$serverString
stubString="/stubs"
stubDir=$clientGenDir$stubString
skeletonString="/skeletons"
skeletonDir=$serverGenDir$skeletonString
serverClassesString="/classes"
serverClassesDir=$serverGenDir$serverClassesString
clientClassesString="/classes"
clientClassesDir=$clientGenDir$clientClassesString
relativeIDLDir="/src/biosim/idl/biosim.idl"
fullIDLDir=$devRootDir$relativeIDLDir
simString="biosim"
simStubDir="$stubDir/$simString"
sourceDir="$devRootDir/src"
clientDir="$sourceDir/biosim/client"
plotClasspath="$devRootDir/lib/jfreechart/jcommon.jar$separator$devRootDir/lib/jfreechart/jfreechart.jar"
jacoClasspath="$JACORB_HOME/jacorb.jar$separator$JACORB_HOME$separator$JACORB_HOME/idl.jar"
docSourcepath="$sourceDir$separator$stubDir$separator$skeletonDir$"
docClasspath="$clientClassesDir$separator$serverClassesDir$separator$jacoClasspath$separator$plotClasspath"
####################
#DOC BUILD  #
####################
echo "	-creating docs"
echo "		-creating html documentation"
javadocInvocation="$javadocCommand -breakiterator -d $apiDir -classpath $docClasspath -sourcepath $docSourcepath -subpackages biosim"
$javadocInvocation > /dev/null
echo "*done creating biosim docs"



