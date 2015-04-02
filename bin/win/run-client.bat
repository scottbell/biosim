@echo off
IF [%BIOSIM_HOME%] == [] (set BIOSIM_HOME="..\..")
echo BIOSIM_HOME to set to %BIOSIM_HOME%
set mainClass=com.traclabs.biosim.client.framework.BiosimMain
set buildDir="%BIOSIM_HOME%\build"
set jacoOrbClass=org.omg.CORBA.ORBClass=org.jacorb.orb.ORB
set jacoSingletonOrbClass=org.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
set machineType=MACHINE_TYPE=CYGWIN
set nameServiceHost="localhost"
set nameServicePort="16315"
set jacoNameIOR="ORBInitRef.NameService=corbaloc::%nameServiceHost%:%nameServicePort%/NameService"
set resourceDir="%BIOSIM_HOME%\resources"
set plotClasspath="%BIOSIM_HOME%\lib\jfreechart\jcommon.jar;%BIOSIM_HOME%\lib\jfreechart\jfreechart.jar;%BIOSIM_HOME%\lib\ptolemy\plot.jar"
set jacoClasspath="%BIOSIM_HOME%\lib\jacorb/jacorb.jar;%BIOSIM_HOME%\lib\jacorb;%BIOSIM_HOME%\lib\jacorb/avalon-framework.jar;%BIOSIM_HOME%\lib\jacorb/logkit.jar"
set logCLasspath="%BIOSIM_HOME%\lib\log4j\log4j.jar;%BIOSIM_HOME%\lib\log4j\commons-logging.jar;%BIOSIM_HOME%\lib\mysql\mysql-jdbc.jar"
java -Xmx1g -classpath %buildDir%;%resourceDir%;%logCLasspath%;%jacoClasspath%;%plotClasspath%;%gefClasspath% %machineTypeEnv% -D%jacoOrbClass% -D%jacoSingletonOrbClass% -D%jacoNameIOR% "%mainClass%" %*
pause