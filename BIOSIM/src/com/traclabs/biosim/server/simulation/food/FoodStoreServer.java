package biosim.server.food;

import biosim.idl.food.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class FoodStoreServer {

	public static void main(String args[]) {
		try{
			// create and initialize the ORB
			ORB orb = OrbUtils.getORB();
			NamingContextExt ncRef = OrbUtils.getNCRef();
			POA rootpoa = OrbUtils.getRootPOA();
			rootpoa.the_POAManager().activate();
			// create servant and register it with  ORB
			FoodStoreImpl myFoodStoreImpl = new FoodStoreImpl();
			// get object reference from the servant
			org.omg.CORBA.Object ref =rootpoa.servant_to_reference(new FoodStorePOATie(myFoodStoreImpl));
			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name("FoodStore");
			ncRef.rebind(path, ref);
			System.out.println("FoodStore Server ready and waiting ...");
			// wait for invocations from clients
			orb.run();
		}
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("FoodStore Server Exiting ...");
	}
}

