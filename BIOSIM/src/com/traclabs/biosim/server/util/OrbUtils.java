package biosim.server.util;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
/**
 * The OrbUtils class provides basic CORBA utilities to server components
 *
 * @author    Scott Bell
 */

public class OrbUtils{
	//Flag to make sure OrbUtils only runs initialize once  
	private static boolean runOnce = false;
	//The root POA for transformation methods and other things
	private static POA rootPOA = null;
	//The server ORB used resolving references
	private static ORB myOrb = null;
	//The naming context reference
	private static NamingContextExt ncRef = null;
	
	/**
	* Returns the ORB
	* @return the ORB
	*/
	public static ORB getORB(){
		initialize();
		return myOrb;
	}
	
	/**
	* Returns the root POA
	* @return the root POA
	*/
	public static POA getRootPOA(){
		initialize();
		return rootPOA;
	}
	
	/**
	* Returns the naming context
	* @return the naming context
	*/
	public static NamingContextExt getNCRef(){
		initialize();
		return ncRef;
	}
	
	public static void resetInit(){
		runOnce = false;
	}
	
	/**
	* Done only once, this method initializes the ORB, resolves the root POA, and grabs the naming context.
	*/
	private static void initialize(){
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
			System.out.println("OrbUtils: nameserver not found, polling again");
			sleepAwhile();
			initialize();
		}
		catch(org.omg.PortableServer.POAManagerPackage.AdapterInactive e){
			System.out.println("OrbUtils: nameserver not found, polling again");
			sleepAwhile();
			initialize();
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("OrbUtils: nameserver not found, polling again");
			sleepAwhile();
			initialize();
		}
	}
	
	public static void sleepAwhile(){
		try{
			Thread.currentThread().sleep(2000);
		}
		catch (InterruptedException e){}
	}
	
	/**
	* This method take a POA servant object and transforms it into a CORBA object
	* @param poa the POA object to transform
	* @return the transformed CORBA object
	*/
	public static org.omg.CORBA.Object poaToCorbaObj(org.omg.PortableServer.Servant poa){
		org.omg.CORBA.Object newObject = null;
		try{
			initialize();
			newObject = rootPOA.servant_to_reference( poa );
		}
		catch(org.omg.PortableServer.POAPackage.ServantNotActive e){
			e.printStackTrace();
		}
		catch(org.omg.PortableServer.POAPackage.WrongPolicy e){
			e.printStackTrace();
		}
		return newObject;
	}
	
	/**
	* This method take a CORBA object and transforms it into a POA servant object
	* @param pObject the CORBA object to transform
	* @return the transformed POA servant object
	*/
	public static org.omg.PortableServer.Servant corbaObjToPoa(org.omg.CORBA.Object pObject){
		org.omg.PortableServer.Servant newPoa = null;
		try{
			initialize();
			newPoa = rootPOA.reference_to_servant( pObject );
		}
		catch (org.omg.PortableServer.POAPackage.ObjectNotActive e){
			e.printStackTrace();
		}
		catch(org.omg.PortableServer.POAPackage.WrongPolicy e){
			e.printStackTrace();
		}
		catch(org.omg.PortableServer.POAPackage.WrongAdapter e){
			e.printStackTrace();
		}
		return newPoa;
	}
}
