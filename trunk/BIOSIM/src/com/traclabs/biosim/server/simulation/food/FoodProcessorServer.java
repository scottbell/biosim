package biosim.server.food;

import biosim.idl.food.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class FoodProcessorServer {

public static void main(String args[]) {
	try{
		// create and initialize the ORB
		ORB orb = OrbUtils.getORB();
		NamingContextExt ncRef = OrbUtils.getNCRef();
		POA rootpoa = OrbUtils.getRootPOA();

		 // create servant and register it with  ORB
		 FoodProcessorImpl myFoodProcessorImpl = new FoodProcessorImpl();

		 // get object reference from the servant
		 org.omg.CORBA.Object ref = rootpoa.servant_to_reference(myFoodProcessorImpl);
		 // bind the Object Reference in Naming
		 NameComponent path[] = ncRef.to_name("FoodProcessor");
		 ncRef.rebind(path, ref);
		 
		 System.out.println("FoodProcessor Server ready and waiting ...");
		 // wait for invocations from clients
		 orb.run();
	}
	catch (Exception e) {
		System.err.println("ERROR: " + e);
		e.printStackTrace(System.out);
	}
	System.out.println("FoodProcessor Server Exiting ...");
	}
}

