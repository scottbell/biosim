@echo off
call setENV.bat
set PATH=%JAVA_HOME%\bin;%PATH
set machineType=MACHINE_TYPE=CYGWIN
java -D%machineType% -classpath biosim.jar BiosimStandalone
pause
