#!/bin/bash

echo "*building users manual"
# see if the biosim directory exists, if it doesn't, assume it's one directory back (i.e. user is in bin directory)
devRootDir=$BIOSIM_HOME
currentDir=`pwd`
if [ -z "$devRootDir" ]
then
	cd ..
	devRootDir=`pwd`
	cd $currentDir
	echo "		-assuming BIOSIM_HOME is $devRootDir"
fi
userManDir="$devRootDir/docs/users_manual_files"
latex $userManDir/users_manual.tex
makeindex $userManDir/users_manual.idx
bibtex $userManDir/users_manual.tex
latex $userManDir/users_manual.tex
latex $userManDir/users_manual.tex
dvips -o $userManDir/users_manual.ps $userManDir/users_manual.dvi
ps2pdf $userManDir/users_manual.ps
if [ "$1" == "show" ]; then
	echo "		-launching kghostview"
	kghostview $userManDir/users_manual.pdf &
fi
echo "*done users manual"



