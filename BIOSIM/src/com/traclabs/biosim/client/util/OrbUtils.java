package biosim.client.util;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
/**
 * The OrbUtils class provides basic CORBA utilities to client components
 *
 * @author    Scott Bell
 */

public class OrbUtils{
	//Flag to make sure OrbUtils only runs initialize once  
	private static boolean runOnce = false;
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
	
	public static void sleepAwhile(){
		try{
			Thread.currentThread().sleep(2000);
		}
		catch (InterruptedException e){}
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
			org.omg.CORBA.Object objRef = myOrb.resolve_initial_references("NameService");
			ncRef = NamingContextExtHelper.narrow(objRef);
			runOnce = true;
		}
		catch (Exception e){
			System.out.println("OrbUtils: nameserver not found, polling again");
			sleepAwhile();
			initialize();
		}
	}
}
