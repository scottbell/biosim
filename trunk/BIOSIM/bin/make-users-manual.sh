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
userManDir="$devRootDir/doc/users_manual_files"
cd $userManDir
echo `pwd`
touch users_manual.ind
latex users_manual.tex
makeindex users_manual.idx
bibtex users_manual.tex
latex users_manual.tex
dvips -o users_manual.ps users_manual.dvi
ps2pdf users_manual.ps
if [ "$1" == "show" ]; then
	echo "		-launching kghostview"
	kghostview users_manual.pdf &
fi
cd $currentDir
echo "*done users manual"



