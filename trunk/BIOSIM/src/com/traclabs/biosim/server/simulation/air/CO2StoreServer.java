package biosim.server.air;

import biosim.idl.air.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class CO2StoreServer {

public static void main(String args[]) {
	try{
		// create and initialize the ORB
		ORB orb = BioSimUtilsImpl.getORB();
		NamingContextExt ncRef = BioSimUtilsImpl.getNCRef();
		POA rootpoa = BioSimUtilsImpl.getRootPOA();

		 // create servant and register it with  ORB
		 CO2StoreImpl myCO2Impl = new CO2StoreImpl();

		 // get object reference from the servant
		 org.omg.CORBA.Object ref = rootpoa.servant_to_reference(myCO2Impl);
		 // bind the Object Reference in Naming
		 NameComponent path[] = ncRef.to_name("CO2");
		 ncRef.rebind(path, ref);
		 
		 System.out.println("CO2 Server ready and waiting ...");
		 // wait for invocations from clients
		 orb.run();
	}
	catch (Exception e) {
		System.err.println("ERROR: " + e);
		e.printStackTrace(System.out);
	}
	System.out.println("CO2 Server Exiting ...");
	}
}

