package biosim.client.util;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class OrbUtils{
	private static boolean runOnce = false;
	private static POA rootPOA = null;
	private static ORB myOrb = null;
	private static NamingContextExt ncRef = null;

	public static ORB getORB(){
		if (!runOnce)
			initializeORB();
		return myOrb;
	}

	public static POA getRootPOA(){
		if (!runOnce)
			initializeORB();
		return rootPOA;
	}

	public static NamingContextExt getNCRef(){
		if (!runOnce)
			initializeORB();
		return ncRef;
	}

	private static void initializeORB(){
		if (runOnce)
			return;
		try{
			String[] nullArgs = null;
			// create and initialize the ORB
			myOrb = ORB.init(nullArgs, null);
			// get reference to rootpoa & activate the POAManager
			rootPOA = POAHelper.narrow(myOrb.resolve_initial_references("RootPOA"));
			rootPOA.the_POAManager().activate();
			org.omg.CORBA.Object objRef = myOrb.resolve_initial_references("NameService");
			ncRef = NamingContextExtHelper.narrow(objRef);
			runOnce = true;
		}
		catch (org.omg.CORBA.ORBPackage.InvalidName e){
			e.printStackTrace(System.out);
		}
		catch(org.omg.PortableServer.POAManagerPackage.AdapterInactive e){
			e.printStackTrace(System.out);
		}
	}
}
