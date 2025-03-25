package com.traclabs.biosim.framework;

import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.util.RestUtils;

/**
 * A standalone BioSim instance (server, nameserver, client in one)
 * 
 * @author Scott Bell
 */

public class BiosimStandalone {

	private Thread myServerThread;

	private Thread myClientThread;

	private ReadyListener myReadyListener;

	private String myXmlFilename;

	private int myDriverPause;

	public static void main(String args[]) {
		String filename = "default.biosim";
		if (args.length > 0) {
			filename = BiosimMain.getArgumentValue(args[0]);
		}
    	System.out.println("Class path is: " + System.getProperty("java.class.path"));
    	System.out.println("Using configuration file: " + filename);
		ImageIcon moon = new ImageIcon(BiosimStandalone.class.getClassLoader()
				.getResource("com/traclabs/biosim/framework/moon.png"));
		BiosimStandalone myBiosimStandalone = new BiosimStandalone(moon,
				"BioSim: Advanced Life Support Simulation", filename, 500);
		myBiosimStandalone.beginSimulation();
	}

	public BiosimStandalone(String xmlFilename, int driverPause) {
		this.myDriverPause = driverPause;
		this.myXmlFilename = xmlFilename;
		this.myServerThread = new Thread(new ServerThread());
		this.myClientThread = new Thread(new ClientThread());
	}

	public BiosimStandalone(ImageIcon splashIcon, String splashText,
			String xmlFilename, int driverPause) {
		this(xmlFilename, driverPause);
		startSpash(splashIcon, splashText);
	}

	private void startSpash(ImageIcon splashIcon, String splashText) {
		myProgressBar = new JProgressBar();
		myProgressBar.setIndeterminate(true);
		myFrame = new JFrame("BioSim Loader");
		myFrame.getContentPane().setLayout(new BorderLayout());
		ImageIcon biosimIcon = new ImageIcon(BiosimStandalone.class
				.getClassLoader().getResource(
						"com/traclabs/biosim/client/framework/biosim.png"));

		JLabel waitLabel = new JLabel(splashText, splashIcon,
				SwingConstants.CENTER);
		waitLabel.setForeground(Color.WHITE);
		myFrame.setIconImage(biosimIcon.getImage());
		myFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		WindowCloseListener myWindowCloseListener = new WindowCloseListener();
		myFrame.addWindowListener(myWindowCloseListener);
		myFrame.getContentPane().add(waitLabel, BorderLayout.CENTER);
		myFrame.getContentPane().add(myProgressBar, BorderLayout.SOUTH);
		myFrame.pack();
		myFrame.setLocationRelativeTo(null);
		myFrame.getContentPane().setBackground(Color.BLACK);
		myReadyListener = new ReadyListener();
	}

	public void beginSimulation() {
		if (myFrame != null) {
			myFrame.getContentPane().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myFrame.setVisible(true);
		}
		// Initialize REST API server
		RestUtils.initialize();
		RestUtils.sleepAwhile(1000);
		myServerThread.start();
		RestUtils.sleepAwhile(1000);
		myClientThread.start();
	}

	protected void runClient() {
		String[] args = { "-xml=" + myXmlFilename };
		BiosimMain.main(args);
	}

	private class ServerThread implements Runnable {
		public void run() {
			BiosimServer server = new BiosimServer(8080);
			// Configure server with XML file
			System.out.println("Starting BioSim server with configuration: " + myXmlFilename);
			
			// Notify ready listener
			if (myReadyListener != null) {
				myReadyListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Server Ready"));
			}
			
			// Keep thread alive
			try {
				Thread.currentThread().join();
			} catch (InterruptedException e) {
				System.out.println("Server thread interrupted");
			}
		}
	}

	private class ClientThread implements Runnable {
		public void run() {
			runClient();
		}
	}

	public class ReadyListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (myFrame != null) {
				myFrame.getContentPane().setCursor(Cursor.getDefaultCursor());
				myFrame.dispose();
			}
		}
	}

	/**
	 * The Window Close listener for this Frame
	 */
	private class WindowCloseListener extends java.awt.event.WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent event) {
			System.exit(0);
		}
	}

	public String getXmlFilename() {
		return myXmlFilename;
	}
}
