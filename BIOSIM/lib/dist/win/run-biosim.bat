@echo off
call setENV.bat
set PATH=%JAVA_HOME%\bin;%PATH
set jacoOrbClass=org.omg.CORBA.ORBClass=org.jacorb.orb.ORB
set jacoNameIOR=ORBInitRef.NameService=file:%TEMP%\ior.txt
set jacoSingletonOrbClass=org.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
set machineType=MACHINE_TYPE=CYGWIN
start /B javaw -D%jacoOrbClass% -D%jacoSingletonOrbClass% -D%jacoNameIOR% -D%machineType% -classpath biosim.jar BiosimStandalone %TEMP%\ior.txt
