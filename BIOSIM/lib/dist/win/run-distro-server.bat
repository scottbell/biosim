@echo off
call setENV.bat
set PATH=%JAVA_HOME%\bin;%PATH
set jacoOrbClass=org.omg.CORBA.ORBClass=org.jacorb.orb.ORB
set jacoSingletonOrbClass=org.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
set jacoNameIOR=ORBInitRef.NameService=file:%TEMP%\ior.txt
java -D%jacoOrbClass% -D%jacoSingletonOrbClass% -D%jacoNameIOR% -cp biosim.jar biosim.server.framework.BiosimServer
echo CLOSE THIS WINDOW TO QUIT
