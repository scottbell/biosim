package biosim.server.util;

import biosim.idl.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class BioSimUtilsImpl extends BioUtilsPOA {
	private int ticks = 0;
	
	private static boolean runOnce = false;
	private static POA rootpoa = null;
	private static ORB orb = null;
	
	public void addTick(){
		ticks++;
	}

	public int getTicks(){
		return ticks;
	}
	
	private static void initialize(){
		try{
			String[] nullArgs = null;
			// create and initialize the ORB
			orb = ORB.init(nullArgs, null);
			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
		}
		catch (org.omg.CORBA.ORBPackage.InvalidName e){
			e.printStackTrace(System.out);
		}
		catch (org.omg.PortableServer.POAManagerPackage.AdapterInactive e){
			e.printStackTrace(System.out);
		}
	}

	public static org.omg.CORBA.Object poaToCorbaObj(org.omg.PortableServer.Servant poa){
		org.omg.CORBA.Object newObject = null;
		try{
			if (runOnce == false)
				initialize();
			rootpoa = org.omg.PortableServer.POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			newObject = rootpoa.servant_to_reference( poa );
		}
		catch (org.omg.CORBA.ORBPackage.InvalidName e){
			e.printStackTrace(System.out);
		}
		catch (org.omg.PortableServer.POAManagerPackage.AdapterInactive e){
			e.printStackTrace(System.out);
		}
		catch(org.omg.PortableServer.POAPackage.ServantNotActive e){
			e.printStackTrace(System.out);
		}
		catch(org.omg.PortableServer.POAPackage.WrongPolicy e){
			e.printStackTrace(System.out);
		}
		return newObject;
	}
}
