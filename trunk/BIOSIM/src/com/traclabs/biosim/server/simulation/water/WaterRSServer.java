package biosim.server.water;

import SIMULATION.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class WaterRSServer {

public static void main(String args[]) {
	try{
		// create and initialize the ORB
		ORB orb = ORB.init(args, null);

		 // get reference to rootpoa & activate the POAManager
		 POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		 rootpoa.the_POAManager().activate();

		 // create servant and register it with  ORB
		 WaterRSImpl myWaterRSImpl = new WaterRSImpl();

		 // get object reference from the servant
		 org.omg.CORBA.Object ref = rootpoa.servant_to_reference(myWaterRSImpl);
		 // get the root naming context
		 org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
		 // Use NamingContextExt which is part of the Interoperable Naming Service (INS) specification.
		 NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		 // bind the Object Reference in Naming
		 NameComponent path[] = ncRef.to_name("WaterRS");
		 ncRef.rebind(path, ref);
		 
		 System.out.println("WaterRS Server ready and waiting ...");
		 // wait for invocations from clients
		 orb.run();
	}
	catch (Exception e) {
		System.err.println("ERROR: " + e);
		e.printStackTrace(System.out);
	}
	System.out.println("WaterRS Server Exiting ...");
	}
}

