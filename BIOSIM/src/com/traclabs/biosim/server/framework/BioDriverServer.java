package biosim.server.framework;

import biosim.idl.framework.*;
import biosim.server.framework.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
/**
 * The Driver Server.  
 *
 * @author    Scott Bell
 */

public class BioDriverServer {
	
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
			BioDriverImpl myBioDriverImpl = new BioDriverImpl();
			// get object reference from the servant
			org.omg.CORBA.Object ref =rootpoa.servant_to_reference(myBioDriverImpl);
			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name("BioDriver");
			ncRef.rebind(path, ref);
			System.out.println("BioDriver Server ready and waiting ...");
			// wait for invocations from clients
			orb.run();
		}
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("BioDriver Server Exiting ...");
	}
}

