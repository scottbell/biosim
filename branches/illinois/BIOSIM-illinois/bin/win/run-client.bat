@echo off
set mainClass=com.traclabs.biosim.client.framework.BiosimMain
set buildDir=%BIOSIM_HOME%\build
set jacoOrbClass=org.omg.CORBA.ORBClass=org.jacorb.orb.ORB
set jacoSingletonOrbClass=org.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
set machineType=MACHINE_TYPE=CYGWIN
set jacoNameIOR=ORBInitRef.NameService=file:%BIOSIM_HOME%\tmp\ns\ior.txt
set resourceDir=%BIOSIM_HOME%\resources
set plotClasspath=%BIOSIM_HOME%\lib\jfreechart\jcommon.jar;%BIOSIM_HOME%\lib\jfreechart\jfreechart.jar
set jacoClasspath=%BIOSIM_HOME%\lib\jacorb/jacorb.jar;%BIOSIM_HOME%\lib\jacorb;%BIOSIM_HOME%\lib\jacorb/avalon-framework.jar;%BIOSIM_HOME%\lib\jacorb/logkit.jar
set logCLasspath=%BIOSIM_HOME%\lib\log4j\log4j.jar
set xmlClasspath=%BIOSIM_HOME%\lib\xerces\xercesImpl.jar;%BIOSIM_HOME%\lib\xerces\xml-apis.jar;%BIOSIM_HOME%\lib\xerces\xmlParserAPIs.jar
java -classpath %buildDir%;%resourceDir%;%logCLasspath%;%jacoClasspath%;%xmlClasspath%;%plotClasspath% %machineTypeEnv% -D%jacoOrbClass% -D%jacoSingletonOrbClass% -D%jacoNameIOR% %mainClass% %1 %2
pause