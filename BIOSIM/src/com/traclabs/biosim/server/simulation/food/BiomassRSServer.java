package biosim.server.food;

import biosim.idl.food.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class BiomassRSServer {

public static void main(String args[]) {
	try{
		// create and initialize the ORB
		ORB orb = OrbUtils.getORB();
		NamingContextExt ncRef = OrbUtils.getNCRef();
		POA rootpoa = OrbUtils.getRootPOA();

		 // create servant and register it with  ORB
		 BiomassRSImpl myBiomassRSImpl = new BiomassRSImpl();

		 // get object reference from the servant
		 org.omg.CORBA.Object ref = rootpoa.servant_to_reference(myBiomassRSImpl);
		 // bind the Object Reference in Naming
		 NameComponent path[] = ncRef.to_name("BiomassRS");
		 ncRef.rebind(path, ref);
		 
		 System.out.println("BiomassRS Server ready and waiting ...");
		 // wait for invocations from clients
		 orb.run();
	}
	catch (Exception e) {
		System.err.println("ERROR: " + e);
		e.printStackTrace(System.out);
	}
	System.out.println("BiomassRS Server Exiting ...");
	}
}

