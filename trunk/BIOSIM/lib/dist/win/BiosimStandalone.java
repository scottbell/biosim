import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.jacorb.naming.NameServer;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.client.framework.BiosimMain;

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
	private Thread waitThread;
	private ReadyListener myReadyListener;
	private static final String XML_INIT_FILENAME="biosim/server/framework/DefaultInitialization.xml";
	private static final int NAMESERVER_PORT = 16309;
	private static final int SERVER_OA_PORT = 16310;
	private static final int CLIENT_OA_PORT = 16311;
	
	private JFrame myFrame;
	private JProgressBar myProgressBar;

	public static void main(String args[]){
		BiosimStandalone myBiosimStandalone = new BiosimStandalone();
		myBiosimStandalone.beginSimulation();
	}
	
	public BiosimStandalone(){
		myNamingServiceThread = new Thread(new NamingServiceThread());
		myServerThread = new Thread(new ServerThread());
		myClientThread = new Thread(new ClientThread());
		myProgressBar = new JProgressBar();
		myProgressBar.setIndeterminate(true);
		myFrame = new JFrame("BioSim Loader");
		myFrame.getContentPane().setLayout(new BorderLayout());
		ImageIcon marsIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("mars.gif"));
		JLabel waitLabel = new JLabel("BioSim: Advanced Life Support Simulation", marsIcon, SwingConstants.CENTER);
		myFrame.setUndecorated(true);
		myFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		myFrame.getContentPane().add(waitLabel, BorderLayout.CENTER);
		myFrame.getContentPane().add(myProgressBar, BorderLayout.SOUTH);
		Dimension winsize = myFrame.getSize(), screensize = Toolkit.getDefaultToolkit().getScreenSize();
		myFrame.pack();
		myFrame.setLocation((screensize.width - winsize.width - 320) / 2,(screensize.height - winsize.height - 200) / 2);
		myReadyListener = new ReadyListener();
	}
	
	public void beginSimulation(){
		myFrame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		myFrame.setVisible(true);
		myNamingServiceThread.start();
		try {
			System.out.println("Sleeping until nameserver comes online...");
			Thread.sleep(5000);
        	}catch (Exception e){}
		org.jacorb.util.Environment.setProperty("OAPort", Integer.toString(SERVER_OA_PORT));
		org.jacorb.util.Environment.setProperty("ORBInitRef.NameService", "corbaloc::localhost:"+NAMESERVER_PORT+"/NameService");
		myServerThread.start();
		try {
			System.out.println("Sleeping until servers comes online...");
			Thread.sleep(10000);
        	}catch (Exception e){}
		org.jacorb.util.Environment.setProperty("OAPort", Integer.toString(CLIENT_OA_PORT));
		myClientThread.start();
	}

	private class NamingServiceThread implements Runnable{
	        public void run(){
			String[] portArgs = {"-p", Integer.toString(NAMESERVER_PORT)};
			NameServer.main(portArgs);
	        }
	}
	
	private class ServerThread implements Runnable{
	        public void run(){
			BiosimServer myBiosimServer = new BiosimServer(0, 500, XML_INIT_FILENAME);
			myBiosimServer.addReadyListener(myReadyListener);
			myBiosimServer.runServer("BiosimServer (id=0)");
	        }
	}
	
	private class ClientThread implements Runnable{
	        public void run(){
			String[] emptyArgs = new String[0];
			BiosimMain.main(emptyArgs);
	        }
	}
	
	public class ReadyListener implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			myFrame.getContentPane().setCursor(Cursor.getDefaultCursor());
			myFrame.dispose();
		}
	}
}
