package biosim.server.framework;

import biosim.server.framework.*;
import biosim.server.util.*;
import java.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
/**
 * The Generic Server.  Provides basic functionality for BioSim servers
 *
 * @author    Scott Bell
 */

public class GenericServer{
	
	/**
	* Grabs ID parameter from an array of string
	* @param myArgs an array of strings to parse for the ID server switch, "-id".  Used for setting ID of
	* this instance of the server.  example, java myServer -id=2
	*/
	protected int getIDfromArgs(String[] myArgs){
		int myID = 0;
		if (myArgs.length > 1){
			if (myArgs[0].startsWith("-id=")){
				try{
					StringTokenizer st = new StringTokenizer(myArgs[0],"=");
					st.nextToken();
					myID = Integer.parseInt(st.nextToken());
				}
				catch (Exception e){
					System.err.println("Problem parsing arguments on arg "+myArgs[0]);
					e.printStackTrace();
				}
			}
		}
		return myID;
	}
	
	/**
	* Registers this server with the CORBA naming service
	* @param pPOA the object to register
	* @param servername the name that will be associated with this server in the naming service
	*/
	protected void registerServer(Servant pPOA, String serverName){
		try{
			NamingContextExt ncRef = OrbUtils.getNCRef();
			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name(serverName);
			ncRef.rebind(path, OrbUtils.poaToCorbaObj(pPOA));
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println(serverName+" had problems registering with nameservice, trying again..");
			OrbUtils.sleepAwhile();
			registerServer(pPOA, serverName);
		}
		catch (Exception e) {
			System.err.println(serverName+" had problems registering with nameservice, trying again..");
			OrbUtils.sleepAwhile();
			OrbUtils.resetInit();
			registerServer(pPOA, serverName);
		}
	}
	
	/**
	* Registers this server with the CORBA naming service and starts the server
	* @param pPOA the object to register
	* @param servername the name that will be associated with this server in the naming service
	*/
	protected void registerServerAndRun(Servant pPOA, String serverName){
		registerServer(pPOA,serverName);
		runServer(serverName);
	}
	
	/**
	* Starts the server by calling ORB.run()
	* @param servername the name associated with this server (for debug purposes only)
	*/
	protected void runServer(String serverName){
		try{
			System.out.println(serverName+ "Server ready and waiting ...");
			// wait for invocations from clients
			OrbUtils.getORB().run();
		}
		catch (Exception e) {
			System.err.println(serverName+" ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println(serverName+" Server Exiting ...");
	}
}

