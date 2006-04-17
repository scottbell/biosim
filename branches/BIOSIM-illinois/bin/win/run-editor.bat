@echo off
@echo starting biosim editor
set mainClass=com.traclabs.biosim.editor.BiosimEditorMain
set buildDir="%BIOSIM_HOME%\build"
set jacoOrbClass=org.omg.CORBA.ORBClass=org.jacorb.orb.ORB
set jacoSingletonOrbClass=org.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
set machineType=MACHINE_TYPE=CYGWIN
set jacoNameIOR="ORBInitRef.NameService=file:%BIOSIM_HOME%\tmp\ns\ior.txt"
set resourceDir="%BIOSIM_HOME%\resources"
set jacoClasspath="%BIOSIM_HOME%\lib\jacorb/jacorb.jar;%BIOSIM_HOME%\lib\jacorb;%BIOSIM_HOME%\lib\jacorb/avalon-framework.jar;%BIOSIM_HOME%\lib\jacorb/logkit.jar"
set logCLasspath="%BIOSIM_HOME%\lib\log4j\log4j.jar;%BIOSIM_HOME%\lib\log4j\commons-logging.jar"
set gefClasspath="%BIOSIM_HOME%\lib\gef\gef.jar"
set xmlClasspath="%BIOSIM_HOME%\lib\xerces\xercesImpl.jar;%BIOSIM_HOME%\lib\xerces\xml-apis.jar;%BIOSIM_HOME%\lib\xerces\xmlParserAPIs.jar"
java -classpath %buildDir%;%resourceDir%;%gefClasspath%;%logCLasspath%;%jacoClasspath%;%xmlClasspath% %machineTypeEnv% -D%jacoOrbClass% -D%jacoSingletonOrbClass% -D%jacoNameIOR% "%mainClass%" %*
