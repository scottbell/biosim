package biosim.server.power;

import biosim.idl.power.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class PowerPSServer {

public static void main(String args[]) {
	try{
		// create and initialize the ORB
		ORB orb = OrbUtils.getORB();
		NamingContextExt ncRef = OrbUtils.getNCRef();
		POA rootpoa = OrbUtils.getRootPOA();

		 // create servant and register it with  ORB
		 PowerPSImpl myPowerPSImpl = new PowerPSImpl();

		 // get object reference from the servant
		 org.omg.CORBA.Object ref = rootpoa.servant_to_reference(myPowerPSImpl);
		 // bind the Object Reference in Naming
		 NameComponent path[] = ncRef.to_name("PowerPS");
		 ncRef.rebind(path, ref);
		 
		 System.out.println("PowerPS Server ready and waiting ...");
		 // wait for invocations from clients
		 orb.run();
	}
	catch (Exception e) {
		System.err.println("ERROR: " + e);
		e.printStackTrace(System.out);
	}
	System.out.println("PowerPS Server Exiting ...");
	}
}

