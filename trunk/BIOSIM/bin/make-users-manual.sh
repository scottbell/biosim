#!/bin/bash

if [ "$1" == "-?" ]; then
	echo -e "usage: make-users-manual.sh [-debug] [-show] [-?]"
	exit
fi
echo "*building users manual"
# see if the biosim directory exists, if it doesn't, assume it's one directory back (i.e. user is in bin directory)
devRootDir=$BIOSIM_HOME
currentDir=`pwd`
show="false"
textOutput=/dev/null
if [ -z "$devRootDir" ]
then
	cd ..
	devRootDir=`pwd`
	cd $currentDir
	echo "	-assuming BIOSIM_HOME is $devRootDir"
fi
if [ "$1" == "-debug" ]; then
	echo "	-debug mode on"
	textOutput=/dev/tty
	if [ "$2" == "-show" ]; then
		show="true"
	fi
fi
if [ "$1" == "-show" ]; then
	show="true"
	if [ "$2" == "-debug" ]; then
		echo "	-debug mode on"
		textOutput=/dev/tty
	fi
fi
userManDir="$devRootDir/doc/users_manual_files"
cd $userManDir
echo "	-creating empty index file"
touch users_manual.ind > $textOutput
echo "	-first latex pass"
latex users_manual.tex > $textOutput
echo "	-making index file"
makeindex users_manual.idx 2> $textOutput
echo "	-making bibliography"
bibtex users_manual.tex > $textOutput
echo "	-second latex pass"
latex users_manual.tex > $textOutput
echo "	-creating ps file"
dvips -o users_manual.ps users_manual.dvi 2> $textOutput
echo "	-creating pdf file"
ps2pdf users_manual.ps > $textOutput
if [ "$show" == "true" ]; then
	echo "	-launching kghostview"
	kghostview users_manual.pdf &  > $textOutput
fi
cd $currentDir
echo "*done users manual"



