package biosim.server.util;

import biosim.idl.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class BioSimUtilsImpl extends BioUtilsPOA {
	private static int ticks = 0;
	
	private static boolean runOnce = false;
	private static POA rootPOA = null;
	private static ORB myOrb = null;
	private static NamingContextExt ncRef = null;
	
	public static void addTick(){
		ticks++;
	}

	public static int getTicks(){
		return ticks;
	}
	
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

	public static org.omg.CORBA.Object poaToCorbaObj(org.omg.PortableServer.Servant poa){
		org.omg.CORBA.Object newObject = null;
		try{
			if (!runOnce)
				initializeORB();
			newObject = rootPOA.servant_to_reference( poa );
		}
		catch(org.omg.PortableServer.POAPackage.ServantNotActive e){
			e.printStackTrace(System.out);
		}
		catch(org.omg.PortableServer.POAPackage.WrongPolicy e){
			e.printStackTrace(System.out);
		}
		return newObject;
	}
	
	public static org.omg.PortableServer.Servant corbaObjToPoa(org.omg.CORBA.Object pObject){
		org.omg.PortableServer.Servant newPoa = null;
		try{
			if (!runOnce)
				initializeORB();
			newPoa = rootPOA.reference_to_servant( pObject );
		}
		catch (org.omg.PortableServer.POAPackage.ObjectNotActive e){
			e.printStackTrace(System.out);
		}
		catch(org.omg.PortableServer.POAPackage.WrongPolicy e){
			e.printStackTrace(System.out);
		}
		catch(org.omg.PortableServer.POAPackage.WrongAdapter e){
			e.printStackTrace(System.out);
		}
		return newPoa;
	}
}
