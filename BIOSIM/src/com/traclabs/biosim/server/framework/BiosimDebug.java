package biosim.server.framework;

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
	private static final String XML_INIT_FILENAME="biosim/server/framework/DefaultInitialization.xml";
	private static final int NAMESERVER_PORT = 16309;
	private static final int SERVER_OA_PORT = 16310;

	public static void main(String args[]){
		BiosimDebug myBiosimStandalone = new BiosimDebug();
		myBiosimStandalone.beginSimulation();
	}
	
	public BiosimDebug(){
		myNamingServiceThread = new Thread(new NamingServiceThread());
	}
	
	public void beginSimulation(){
		myNamingServiceThread.start();
		try {
			System.out.println("Sleeping until nameserver comes online...");
			Thread.sleep(5000);
        }
		catch (Exception e){}
		org.jacorb.util.Environment.setProperty("OAPort", Integer.toString(SERVER_OA_PORT));
		org.jacorb.util.Environment.setProperty("ORBInitRef.NameService", "corbaloc::localhost:"+NAMESERVER_PORT+"/NameService");
		System.out.println("Server awake...");
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
