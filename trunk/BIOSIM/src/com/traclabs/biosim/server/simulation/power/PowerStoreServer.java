package biosim.server.power;

import biosim.idl.power.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
/**
 * The Power Store Server.  Creates an instance of the PowerStore and registers it with the nameserver.
 *
 * @author    Scott Bell
 */

public class PowerStoreServer {
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
			PowerStoreImpl myPowerStoreImpl = new PowerStoreImpl(0);
			// get object reference from the servant
			org.omg.CORBA.Object ref =rootpoa.servant_to_reference(new PowerStorePOATie(myPowerStoreImpl));
			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name("PowerStore");
			ncRef.rebind(path, ref);
			System.out.println("PowerStore Server ready and waiting ...");
			// wait for invocations from clients
			orb.run();
		}
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("PowerStore Server Exiting ...");
	}
}

