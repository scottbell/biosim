package biosim.server.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.omg.PortableServer.Servant;

import biosim.server.util.OrbUtils;
/**
 * The Generic Server.  Provides basic functionality for BioSim servers
 *
 * @author    Scott Bell
 */

public class GenericServer{
	private List readyListeners;
	
	/**
	* Grabs ID parameter from an array of string
	* @param myArgs an array of strings to parse for the ID server switch, "-id".  Used for setting ID of
	* this instance of the server.  example, java myServer -id=2
	*/
	protected static int getIDfromArgs(String[] myArgs){
		int myID = 0;
		for (int i = 0; i < myArgs.length; i++){
			if (myArgs[i].startsWith("-id=")){
				try{
					StringTokenizer st = new StringTokenizer(myArgs[i],"=");
					st.nextToken();
					myID = Integer.parseInt(st.nextToken());
				}
				catch (Exception e){
					System.err.println("Problem parsing arguments on arg "+myArgs[i]);
					e.printStackTrace();
				}
			}
		}
		return myID;
	}
	
	/**
	* Grabs name parameter from an array of string
	* @param myArgs an array of strings to parse for the name server switch, "-name".  Used for setting name of
	* this instance of the server.  example, java myServer -name=MyServer
	*/
	protected static String getNamefromArgs(String[] myArgs){
		String myName = "NoName";
		for (int i = 0; i < myArgs.length; i++){
			if (myArgs[i].startsWith("-name=")){
				StringTokenizer st = new StringTokenizer(myArgs[i],"=");
				st.nextToken();
				myName = st.nextToken();
			}
		}
		return myName;
	}
	
	/**
	* Grabs xml parameter from an array of string
	* @param myArgs an array of strings to parse for the name server switch, "-xml".  Used for setting xml init of
	* this instance of the server.  example, java myServer -xml=/home/bob/init.xml
	*/
	protected static String getXMLfromArgs(String[] myArgs){
		String myName = "biosim/server/framework/DefaultInitialization.xml";
		for (int i = 0; i < myArgs.length; i++){
			if (myArgs[i].startsWith("-xml=")){
				StringTokenizer st = new StringTokenizer(myArgs[i],"=");
				st.nextToken();
				myName = st.nextToken();
			}
		}
		return myName;
	}
	
	/**
	* Registers this server with the CORBA naming service
	* @param pPOA the object to register
	* @param pServerName the name that will be associated with this server in the naming service
	* @param pID the subcontext which to bind the name
	*/
	public static void registerServer(Servant pPOA, String pServerName, int pID){
		//Try to register the ID name context
		try{
			// bind the Object Reference in Naming
			OrbUtils.getNamingContext(pID).rebind(OrbUtils.getNamingContext(pID).to_name(pServerName), OrbUtils.poaToCorbaObj(pPOA));
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println(pServerName+" had problems registering with nameservice, trying again..");
			e.printStackTrace();
			OrbUtils.sleepAwhile();
			registerServer(pPOA, pServerName, pID);
		}
	}
	
	/**
	* Registers this server with the CORBA naming service and starts the server
	* @param pPOA the object to register
	* @param pServerName the name that will be associated with this server in the naming service
	* @param pID the id of the server
	*/
	public void registerServerAndRun(Servant pPOA, String pServerName, int pID){
		registerServer(pPOA,pServerName, pID);
		runServer(pServerName);
	}
	
	public void addReadyListener(ActionListener newListener){
		if (readyListeners == null)
			readyListeners = new Vector();
		readyListeners.add(newListener);
	}
	
	private void notfiyListeners(){
		if (readyListeners == null)
			return;
		for (Iterator iter = readyListeners.iterator(); iter.hasNext();){
			ActionListener currentListener = (ActionListener)(iter.next());
			currentListener.actionPerformed(new ActionEvent(this, 0, " server ready"));
		}
	}
	
	/**
	* Starts the server by calling ORB.run()
	* @param serverName the name associated with this server (for debug purposes only)
	*/
	public void runServer(String serverName){
		try{
			notfiyListeners();
			System.out.println(serverName+" ready and waiting");
			// wait for invocations from clients
			OrbUtils.getORB().run();
		}
		catch (Exception e) {
			System.err.println(serverName+" ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println(serverName+" exiting");
	}
}

