import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.framework.BioDriverHelper;
import com.traclabs.biosim.util.OrbUtils;

/*
 To compile:
 1) build biosim (type ant in BIOSIM_HOME)
 2) javac -classpath .:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/build TestBiosim.java
 
 javac - the compiler
 jacorb.jar - the library that has the ORB and various CORBA utilities
 build - where the biosim classes are
 TestBiosim - this file

 
 To run:
 1)type run-nameserver
 2)type run-server
 3)type java -classpath .:$BIOSIM_HOME/lib/log4j/log4j.jar:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/lib/jacorb/logkit.jar:$BIOSIM_HOME/lib/jacorb/avalon-framework.jar:$BIOSIM_HOME/lib/jacorb:$BIOSIM_HOME/build -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton -DORBInitRef.NameService=file:$BIOSIM_HOME/tmp/ns/ior.txt TestBiosim
 
 
 -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB - overriding Sun's default ORB (using Jacorb instead)
 -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton - overriding Sun's default ORB (using Jacorb instead)
 -DORBInitRef.NameService=file:$BIOSIM_HOME/tmp/ns/ior.txt - telling the client where to look for the ior (serialized nameservice object, produced by run-nameserver)
 
 Good Luck!  If you have any questions, email me at:
 scott@traclabs.com

 */

public class TestBiosim {
	public static void main(String[] args) {
		try {
			System.out.println("TestBiosim begin");
			// Let's get the BioDriver
			System.out.println("Getting BioDriver");
			// BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
			// BioDriver myBioDriver = myBioHolder.theBioDriver;
			BioDriver myBioDriver = BioDriverHelper.narrow(OrbUtils
					.getNamingContext(0).resolve_str("BioDriver"));
			// Now let's call a method on BioDriver
			System.out.println("Invoking method on BioDriver");
			myBioDriver.startSimulation();
			System.out.println("Invoking another method on BioDriver");
			// Now let's call another method on BioDriver, this time with a
			// result
			int numberOfTicks = myBioDriver.getTicks();
			System.out.println("Result was: " + numberOfTicks);
			// All done!
			System.out.println("TestBiosim end");
			myBioDriver.endSimulation();
		} catch (Exception e) {
			System.out
					.println("Something went wrong! (Did you start the nameserver and biosim server?)");
			e.printStackTrace();
		}
	}
}
