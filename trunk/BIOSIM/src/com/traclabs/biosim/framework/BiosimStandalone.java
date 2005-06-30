package com.traclabs.biosim.framework;

import java.awt.BorderLayout;
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

    private static final String XML_INIT_FILENAME = "com/traclabs/biosim/server/framework/DefaultInit.xml";

    private JFrame myFrame;

    private JProgressBar myProgressBar;

    public static void main(String args[]) {

        BiosimStandalone myBiosimStandalone = new BiosimStandalone();
        myBiosimStandalone.beginSimulation();
    }

    public BiosimStandalone() {
        myServerThread = new Thread(new ServerThread());
        myClientThread = new Thread(new ClientThread());
        myProgressBar = new JProgressBar();
        myProgressBar.setIndeterminate(true);
        myFrame = new JFrame("BioSim Loader");
        myFrame.getContentPane().setLayout(new BorderLayout());
        ImageIcon marsIcon = new ImageIcon(BiosimStandalone.class
                .getClassLoader().getResource(
                        "com/traclabs/biosim/framework/mars.png"));
        ImageIcon biosimIcon = new ImageIcon(BiosimStandalone.class
                .getClassLoader().getResource(
                        "com/traclabs/biosim/client/framework/biosim.png"));

        JLabel waitLabel = new JLabel(
                "BioSim: Advanced Life Support Simulation", marsIcon,
                SwingConstants.CENTER);
        myFrame.setIconImage(biosimIcon.getImage());
        myFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        WindowCloseListener myWindowCloseListener = new WindowCloseListener();
        myFrame.addWindowListener(myWindowCloseListener);
        myFrame.getContentPane().add(waitLabel, BorderLayout.CENTER);
        myFrame.getContentPane().add(myProgressBar, BorderLayout.SOUTH);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null); 
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

    private class ServerThread implements Runnable {
        public void run() {
            BiosimServer myBiosimServer = new BiosimServer(0, 500,
                    XML_INIT_FILENAME);
            myBiosimServer.addReadyListener(myReadyListener);
            myBiosimServer.runServer("BiosimServer (id=0)");
        }
    }

    private class ClientThread implements Runnable {
        public void run() {
            String[] emptyArgs = new String[0];
            BiosimMain.main(emptyArgs);
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
}
