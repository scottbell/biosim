@echo off
call setENV.bat
set PATH=%JAVA_HOME%\bin;%PATH
set machineType=MACHINE_TYPE=CYGWIN
start /B javaw -D%machineType% -classpath biosim.jar BiosimStandalone
