package com.traclabs.biosim.client.util;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
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
		initialize();
		NamingContextExt idContext = null;
		try{
			idContext = NamingContextExtHelper.narrow(myBiosimNamingContext.resolve_str(""+pID));
		}
		catch (Exception e){
		}
		return idContext;
	}
	
	/**
	* Forces OrbUtils to retrieve the RootPoa and Naming Service again on next request.
	*/
	public static void resetInit(){
		initializeOrbRunOnce = false;  
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
			NamingContextExt myRootContext = NamingContextExtHelper.narrow(myOrb.resolve_initial_references("NameService"));
			myBiosimNamingContext = NamingContextExtHelper.narrow(myRootContext.resolve_str("biosim"));
			initializeOrbRunOnce = true;
		}
		catch (Exception e){
			System.out.println("OrbUtils: nameserver not found, polling again: "+e);
			sleepAwhile();
			initialize();
			return;
		}
	}
	
	/**
	* Sleeps for a few seconds.  Used when we can't find the naming service and need to poll again after a few seconds.
	*/
	public static void sleepAwhile(){
		try{
			Thread.sleep(2000);
		}
		catch (InterruptedException e){}
	}
	
}
