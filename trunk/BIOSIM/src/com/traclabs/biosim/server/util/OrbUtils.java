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
	private static boolean initializeOrbRunOnce = false;
	//The root POA for transformation methods and other things
	private static POA myRootPOA = null;
	//The server ORB used resolving references
	private static ORB myOrb = null;
	//The root biosim naming context reference
	private static NamingContextExt myBiosimNamingContext = null;
	private static NamingContextExt myRootContext = null;
	
	/**
	* Shouldn't be called (everything static!)
	*/
	private OrbUtils(){
	}
	
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
		return myRootPOA;
	}
	
	/**
	* Returns the naming context associated with this ID
	* @return the naming context
	*/
	public static NamingContextExt getNamingContext(int pID){
		initializeNamingContexts(pID);
		NamingContextExt idContext = null;
		try{
			idContext = NamingContextExtHelper.narrow(myBiosimNamingContext.resolve_str(""+pID));
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return idContext;
	}
	
	/**
	* Forces OrbUtils to retrieve the RootPoa and Naming Service again on next request.
	*/
	public static void resetInit(){
		initializeOrbRunOnce = false;
	}
	
	private static void initializeNamingContexts(int pID){
		initialize();
		try{
			//Attempt to create id context, if already there, don't bother
			NameComponent idComponent = new NameComponent(pID+"", "");
			NameComponent[] idComponents = {idComponent};
			NamingContext IDContext = myBiosimNamingContext.bind_new_context(idComponents);
		}
		catch (org.omg.CosNaming.NamingContextPackage.AlreadyBound e){
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	* Done only once, this method initializes the ORB, resolves the root POA, and grabs the naming context.
	*/
	private static void initialize(){
		if (initializeOrbRunOnce)
			return;
		try{
			String[] nullArgs = null;
			// create and initialize the ORB
			myOrb = ORB.init(nullArgs, null);
			// get reference to rootpoa & activate the POAManager
			myRootPOA = POAHelper.narrow(myOrb.resolve_initial_references("RootPOA"));
			myRootPOA.the_POAManager().activate();
			myRootContext = NamingContextExtHelper.narrow(myOrb.resolve_initial_references("NameService"));
			//Attempt to create biosim context, if already there, don't bother
			NameComponent biosimComponent = new NameComponent("biosim", "");
			NameComponent[] biosimComponentArray = {biosimComponent};
			myRootContext.bind_new_context(biosimComponentArray);
		}
		catch (org.omg.CosNaming.NamingContextPackage.AlreadyBound e){
		}
		catch (Exception e){
			System.out.println("OrbUtils: nameserver not found, polling again: "+e);
			sleepAwhile();
			initialize();
			return;
		}
		try{
			myBiosimNamingContext = NamingContextExtHelper.narrow(myRootContext.resolve_str("biosim"));
			initializeOrbRunOnce = true;
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	* Sleeps for a few seconds.  Used when we can't find the naming service and need to poll again after a few seconds.
	*/
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
			newObject = myRootPOA.servant_to_reference(poa);
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
			newPoa = myRootPOA.reference_to_servant( pObject );
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