**********************************************
BIOSIM - Advanced Life Support Simulation v2.0
	by Scott Bell and Dave Kortenkamp
	copyright 2004
**********************************************

I. SETUP
	1) Make sure you have a JDK >= 1.5 installed (IBM or Sun's).
		If it's not installed, go here: http://java.sun.com
	2) Make sure you have a Ant installed
		If it's not installed, go here: http://ant.apache.org/
	3) Set your JAVA_HOME environment variable to point to where the JSDK is installed.
		Ex: JAVA_HOME=/usr/java/jsdk1.4 on unix systems
		Ex: JAVA_HOME=c:\jsdk1.4 on windows systems
	4) Set your BIOSIM_HOME environment variable.
		Ex: BIOSIM_HOME=/home/joe/BIOSIM on unix systems
		Ex: BIOSIM_HOME=c:\workbench\BIOSIM on windows systems
	5) You can optionally add BIOSIM_HOME's bin directory to your path.
		Ex: $BIOSIM_HOME/bin on unix systems
		Ex: %BIOSIM_HOME%/bin/win on windows
		
II. MAKING BIOSIM
	1) Open a shell (or a command prompt in windows).
	2) cd to the BIOSIM_HOME directory.
	3) Type "ant" without the quotes.
	
III. RUNNING BIOSIM
	Running the BIOSIM may be done 2 ways:
	Make sure you're in the BIOSIM_HOME/bin directory if it's not in your path
		a) Type "run-biosim" (or "/run-biosim" if . is not in your path) without the quotes.
		b) The other method requires you to start 3 different applications (change the .sh extension to .bat on windows)
			1) Type "run-nameserver" (or "./run-nameserver" if . is not in your path) without the quotes.
			2) Type "run-server" (or "./run-server" if it is not in your path) without the quotes.
			3) Type "run-client" (or "./run-client" if it is not in your path) without the quotes.

IV. MAKING YOUR OWN CLIENT
	TestBiosim.java should of been included in this distribution (in the directory called docs).
	It is a nice example of how to create a client for BioSim (controller, viewer, etc).
V. PROBLEMS?
	If you have any problems, please contact me at scott@traclabs.com and I'll try to help.
		
	

