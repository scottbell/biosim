package com.traclabs.biosim.server.framework;

import org.apache.log4j.Logger;
import org.jacorb.naming.NameServer;

/**
 * A standalone BioSim instance (server, nameserver, client in one) for debugging
 *
 * @author    Scott Bell
 */	

public class BiosimDebug
{
	private Thread myNamingServiceThread;
	private Thread myServerThread;
	private Thread myClientThread;
	private Thread waitThread;
	private static final String XML_INIT_FILENAME="com/traclabs/biosim/server/framework/DefaultInitialization.xml";
	private static final int NAMESERVER_PORT = 16309;
	private static final int SERVER_OA_PORT = 16310;
	private Logger myLogger;	
	
	public BiosimDebug(){
		myLogger = Logger.getLogger(this.getClass());
		myNamingServiceThread = new Thread(new NamingServiceThread());
	}

	public static void main(String args[]){
		BiosimDebug myBiosimStandalone = new BiosimDebug();
		myBiosimStandalone.beginSimulation();
	}
	
	public void beginSimulation(){
		myNamingServiceThread.start();
		try {
			myLogger.info("Sleeping until nameserver comes online...");
			Thread.sleep(5000);
        }
		catch (Exception e){}
		org.jacorb.util.Environment.setProperty("OAPort", Integer.toString(SERVER_OA_PORT));
		org.jacorb.util.Environment.setProperty("ORBInitRef.NameService", "corbaloc::localhost:"+NAMESERVER_PORT+"/NameService");
		myLogger.info("Server awake...");
        BiosimServer myBiosimServer = new BiosimServer(0, 0, XML_INIT_FILENAME);
        myBiosimServer.runServer("BiosimServer (id=0)");
	}

	private class NamingServiceThread implements Runnable{
	        public void run(){
	        	String[] portArgs = {"-p", Integer.toString(NAMESERVER_PORT)};
				NameServer.main(portArgs);
	        }
	}
}
