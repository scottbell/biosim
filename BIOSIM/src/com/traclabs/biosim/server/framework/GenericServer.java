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
 * The Driver Server.  
 *
 * @author    Scott Bell
 */

public class GenericServer{
	
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
	
	protected void registerServer(Servant pPOA, String serverName){
		try{
			NamingContextExt ncRef = OrbUtils.getNCRef();
			// bind the Object Reference in Naming
			NameComponent path[] = ncRef.to_name(serverName);
			ncRef.rebind(path, OrbUtils.poaToCorbaObj(pPOA));
		}
		catch (Exception e) {
			System.err.println(serverName+" ERROR: " + e);
			e.printStackTrace(System.out);
		}
	}

	protected void registerServerAndRun(Servant pPOA, String serverName){
		registerServer(pPOA,serverName);
		runServer(serverName);
	}
	
	

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

