package biosim.server.air;

import biosim.idl.air.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import biosim.server.util.*;

public class AirRSServer {

	public static void main(String args[]) {
		try{
			// create and initialize the ORB
			ORB orb = OrbUtils.getORB();
			NamingContextExt ncRef = OrbUtils.getNCRef();
			POA rootpoa = OrbUtils.getRootPOA();
			rootpoa.the_POAManager().activate();
			// create servant and register it with  ORB
			AirRSImpl myAirRSImpl = new AirRSImpl();

			org.omg.CORBA.Object ref =rootpoa.servant_to_reference( new AirRSPOATie(myAirRSImpl));
			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name("AirRS");
			ncRef.rebind(path, ref);

			System.out.println("AirRS Server ready and waiting ...");
			// wait for invocations from clients
			orb.run();
		}
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("AirRS Server Exiting ...");
	}
}

