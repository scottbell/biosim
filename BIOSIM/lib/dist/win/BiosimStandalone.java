/**
 * A standalone BioSim instance (server, nameserver, client in one)
 *
 * @author    Scott Bell
 */	

public class BiosimStandalone
{
	private Thread myNamingServiceThread;
	private Thread myServerThread;
	private Thread myClientThread;

	public static void main(String args[]){
		if (args.length < 1){
			System.err.println("Must specifify where IOR is located!");
			return;
		}
		BiosimStandalone myBiosimStandalone = new BiosimStandalone(args[0]);
		myBiosimStandalone.beginSimulation();
	}
	
	public BiosimStandalone(String pIOR){
		myNamingServiceThread = new Thread(new NamingServiceThread(pIOR));
		myServerThread = new Thread(new ServerThread(pIOR));
		myClientThread = new Thread(new ClientThread(pIOR));
	}
	
	public void beginSimulation(){
		myNamingServiceThread.start();
		myServerThread.start();
		myClientThread.start();
	}

	private class NamingServiceThread implements Runnable{
		private String myIOR;
		
		public NamingServiceThread(String pIOR){
			myIOR = pIOR;
		}
		
	        public void run(){
			org.jacorb.naming.NameServer myNameserver = new org.jacorb.naming.NameServer();
			String[] iorArgs = {myIOR};
			myNameserver.main(iorArgs);
	        }
	}
	
	private class ServerThread implements Runnable{
		private String myIOR;
		
		public ServerThread(String pIOR){
			myIOR = pIOR;
		}
		
	        public void run(){
			biosim.server.framework.BiosimServer myBiosimServer = new biosim.server.framework.BiosimServer();
			String[] iorArgs = {myIOR};
			myBiosimServer.main(iorArgs);
	        }
	}
	
	private class ClientThread implements Runnable{
		private String myIOR;
		
		public ClientThread(String pIOR){
			myIOR = pIOR;
		}
		
	        public void run(){
			biosim.client.framework.BiosimMain myBiosimClient = new biosim.client.framework.BiosimMain();
			String[] iorArgs = {myIOR};
			myBiosimClient.main(iorArgs);
	        }
	}
}

