package com.traclabs.biosim.framework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.traclabs.biosim.client.framework.BiosimMain;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.util.OrbUtils;

/**
 * A standalone BioSim instance (server, nameserver, client in one)
 * 
 * @author Scott Bell
 */

public class BiosimStandalone {

    private Thread myServerThread;

    private Thread myClientThread;

    private ReadyListener myReadyListener;

    private JFrame myFrame;

    private JProgressBar myProgressBar;
    
    private String myXmlFilename;
    
    private int myDriverPause;

    public static void main(String args[]) {
        ImageIcon marsIcon = new ImageIcon(BiosimStandalone.class
                .getClassLoader().getResource(
                        "com/traclabs/biosim/framework/mars.png"));
        BiosimStandalone myBiosimStandalone = new BiosimStandalone(marsIcon, "BioSim: Advanced Life Support Simulation", "com/traclabs/biosim/server/framework/DefaultInit.xml", 500);
        myBiosimStandalone.beginSimulation();
    }
    
    public BiosimStandalone(ImageIcon splashIcon, String splashText, String xmlFilename, int driverPause){
    	myDriverPause = driverPause;
    	myXmlFilename = xmlFilename;
        myServerThread = new Thread(new ServerThread());
        myClientThread = new Thread(new ClientThread());
        myProgressBar = new JProgressBar();
        myProgressBar.setIndeterminate(true);
        myFrame = new JFrame("BioSim Loader");
        myFrame.getContentPane().setLayout(new BorderLayout());
        ImageIcon biosimIcon = new ImageIcon(BiosimStandalone.class
                .getClassLoader().getResource(
                        "com/traclabs/biosim/client/framework/biosim.png"));

        JLabel waitLabel = new JLabel(
        		splashText, splashIcon,
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
        myFrame.getContentPane().setCursor(
                Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        myFrame.setVisible(true);
        OrbUtils.startDebugNameServer();
        OrbUtils.sleepAwhile(5000);
        OrbUtils.initializeServerForDebug();
        myServerThread.start();
        OrbUtils.sleepAwhile(10000);
        OrbUtils.initializeClientForDebug();
        myClientThread.start();
    }
    
    protected void runClient(){
        String[] emptyArgs = new String[0];
        BiosimMain.main(emptyArgs);
    }

    private class ServerThread implements Runnable {
		public void run() {
            BiosimServer myBiosimServer = new BiosimServer(0, myDriverPause,
                    myXmlFilename);
            myBiosimServer.addReadyListener(myReadyListener);
            myBiosimServer.runServer("BiosimServer (id=0)");
        }
    }

    private class ClientThread implements Runnable {
        public void run() {
        	runClient();
        }
    }

    public class ReadyListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            myFrame.getContentPane().setCursor(Cursor.getDefaultCursor());
            myFrame.dispose();
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