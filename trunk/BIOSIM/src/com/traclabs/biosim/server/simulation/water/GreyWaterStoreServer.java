package biosim.server.water;

import biosim.idl.water.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
/**
 * The Grey Water Store Server.  Creates an instance of the GreyWaterStore and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class GreyWaterStoreServer {
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		try{
			// create and initialize the ORB
			ORB orb = OrbUtils.getORB();
			NamingContextExt ncRef = OrbUtils.getNCRef();
			POA rootpoa = OrbUtils.getRootPOA();
			rootpoa.the_POAManager().activate();
			// create servant and register it with  ORB
			GreyWaterStoreImpl myGreyWaterStoreImpl = new GreyWaterStoreImpl();
			// get object reference from the servant
			org.omg.CORBA.Object ref =rootpoa.servant_to_reference(new GreyWaterStorePOATie(myGreyWaterStoreImpl));
			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name("GreyWaterStore");
			ncRef.rebind(path, ref);
			System.out.println("GreyWaterStore Server ready and waiting ...");
			// wait for invocations from clients
			orb.run();
		}
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace();
		}
		System.out.println("GreyWaterStore Server Exiting ...");
	}
}

