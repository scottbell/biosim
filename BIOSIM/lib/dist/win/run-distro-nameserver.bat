@echo off
call setENV.bat
set PATH=%JAVA_HOME%\bin;%PATH
set jacoOrbClass=org.omg.CORBA.ORBClass=org.jacorb.orb.ORB
set jacoSingletonOrbClass=org.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
java -D%jacoOrbClass% -D%jacoSingletonOrbClass% -cp biosim.jar org.jacorb.naming.NameServer %TEMP%\ior.txt
echo CLOSE THIS WINDOW TO QUIT
