import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import biosim.idl.framework.BioDriver;
import biosim.idl.framework.BioDriverHelper;

/*
 To compile:
 1) run make-client.sh
 2) javac -classpath .:$BIOSIM_HOME\lib\jacorb.jar:$BIOSIM_HOME\generated\client\classes TestBiosim.java
 
 javac - the compiler
 jacorb.jar - the library that has the ORB and various CORBA utilities
 generated\client\classes - the generated client stubs
 TestBiosim - this file

 
 To run:
 1)run run-nameserver.sh
 2)run run-server.sh
 3)java -classpath .:$BIOSIM_HOME\lib\jacorb\jacorb.jar:$BIOSIM_HOME\lib\jacorb:$BIOSIM_HOME\generated\client\classes 
 -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB
 -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton
 -DORBInitRef.NameService=file:$BIOSIM_HOME/generated/ns/ior.txt TestBiosim
 (all the above on one line)
 
 -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB - overriding Sun's default ORB (using Jacorb instead)
 -Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton - overriding Sun's default ORB (using Jacorb instead)
 -DORBInitRef.NameService=file:$BIOSIM_HOME/generated/ns/ior.txt - telling the client where to look for the ior (serialized nameservice object, produced by run-nameserver.sh)
 
 Good Luck!  If you have any questions, email me at:
 scott@traclabs.com

*/


public class TestBiosim{
	public static void main(String[] args){
		try{
			System.out.println("TestBiosim begin");
			//The ORB
			ORB myOrb = null;
			//The naming context reference
			NamingContextExt myNamingContextExt = null;
			// create and initialize the ORB (with no arguments)
		String[] nullArgs = null;
			System.out.println("Initializing ORB");
		myOrb = ORB.init(nullArgs, null);
		System.out.println("Getting a reference to the naming service");
		org.omg.CORBA.Object nameServiceObject = myOrb.resolve_initial_references("NameService");
		System.out.println("Narrowing name service");
		myNamingContextExt = NamingContextExtHelper.narrow(nameServiceObject);
		
		//Let's get the BioDriver
		System.out.println("Getting BioDriver");
		BioDriver myBioDriver = BioDriverHelper.narrow(myNamingContextExt.resolve_str("BioDriver0"));
		System.out.println("Invoking method on BioDriver");
		//Now let's call a method on BioDriver
		myBioDriver.spawnSimulation();
		System.out.println("Invoking another method on BioDriver");
		//Now let's call another method on BioDriver, this time with a result
		int numberOfTicks = myBioDriver.getTicks();
		System.out.println("Result was: "+numberOfTicks);
		//All done!
		System.out.println("TestBiosim end");
		}
		catch (Exception e){
			System.out.println("Something went wrong!");
			e.printStackTrace();
		}
	}

}
