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
echo "		-creating empty index file"
touch users_manual.ind > /dev/null
echo "		-first latex pass"
latex users_manual.tex > /dev/null
echo "		-making index file"
makeindex users_manual.idx 2> /dev/null
echo "		-making bibliography"
bibtex users_manual.tex > /dev/null
echo "		-second latex pass"
latex users_manual.tex > /dev/null
echo "		-creating ps file"
dvips -o users_manual.ps users_manual.dvi 2> /dev/null
echo "		-creating pdf file"
ps2pdf users_manual.ps > /dev/null
if [ "$1" == "show" ]; then
	echo "		-launching kghostview"
	kghostview users_manual.pdf &  > /dev/null
fi
cd $currentDir
echo "*done users manual"



