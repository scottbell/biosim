@echo off
set mainClass=org.jacorb.naming.NameServer
set jacoOrbClass=org.omg.CORBA.ORBClass=org.jacorb.orb.ORB
set jacoSingletonOrbClass=org.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
set iorOutput=jacorb.naming.ior_filename="%BIOSIM_HOME%\tmp\ns\ior.txt"
set resourceDir="%BIOSIM_HOME%\resources"
set nameServicePort="16315"
set jacoClasspath="%BIOSIM_HOME%\lib\jacorb\jacorb.jar;%BIOSIM_HOME%\lib\jacorb;%BIOSIM_HOME%\lib\jacorb\avalon-framework.jar;%BIOSIM_HOME%\lib\jacorb\logkit.jar"
mkdir "%BIOSIM_HOME%\tmp\ns"
echo running nameserver
java -classpath %jacoClasspath% -DOAPort=%nameServicePort% -D%jacoOrbClass% -D%jacoSingletonOrbClass% -D%iorOutput% "%mainClass%" %*
pause