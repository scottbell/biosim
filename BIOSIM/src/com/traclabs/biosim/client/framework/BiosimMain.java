package com.traclabs.biosim.client.framework;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jacorb.util.Environment;

import com.traclabs.biosim.client.control.HandController;
import com.traclabs.biosim.client.control.SimCommandLine;
import com.traclabs.biosim.client.simulation.framework.gui.SimDesktop;
import com.traclabs.biosim.client.unrealCom.UnrealCom;
import com.traclabs.biosim.client.util.BioHolderInitializer;

/**
 * @author Scott Bell
 */

public class BiosimMain {

    private static final int NAMESERVER_PORT = 16309;

    private static final int CLIENT_OA_PORT = 16311;

    private Logger myLogger;

    public BiosimMain() {
        Properties logProps = new Properties();
        logProps.setProperty("log4j.rootLogger", "INFO, rootAppender");
        logProps.setProperty("log4j.appender.rootAppender",
                "org.apache.log4j.ConsoleAppender");
        logProps.setProperty("log4j.appender.rootAppender.layout",
                "org.apache.log4j.PatternLayout");
        logProps.setProperty(
                "log4j.appender.rootAppender.layout.ConversionPattern",
                "%5p [%c] - %m%n");
        /*
         * logProps.setProperty(
         * "log4j.logger.com.traclabs.biosim.client.control.HandController",
         * "DEBUG");
         */
        PropertyConfigurator.configure(logProps);
        myLogger = Logger.getLogger(this.getClass());
    }

    /**
     * The method to start the BIOSIM client.
     * 
     * @param args
     *            can start TestDriver with -gui or -nogui (optional id=X where
     *            X is an integer)
     */
    public static void main(String args[]) {
        BiosimMain myMain = new BiosimMain();
        myMain.checkArgs(args);
    }

    private void checkArgs(String[] myArgs) {
        myLogger.debug("arg length = " + myArgs.length);
        int myID = 0;
        String xmlFile = null;
        boolean wantsToRunCommandLine = false;
        boolean wantsToRunGUI = false;
        boolean wantsToRunController = false;
        boolean wantsToRunUnreal = false;
        boolean unrealServerGiven = false;
        String unrealServer = "";
        for (int i = 0; i < myArgs.length; i++) {
            if (myArgs[i].equals("gui")) {
                wantsToRunGUI = true;
            } else if (myArgs[i].equals("console")) {
                wantsToRunCommandLine = true;
            } else if (myArgs[i].equals("controller")) {
                wantsToRunController = true;
            } else if (myArgs[i].equals("unreal")) {
                wantsToRunUnreal = true;
            } else if (myArgs[i].equals("-debug")) {
                Environment.setProperty("ORBInitRef.NameService",
                        "corbaloc::localhost:" + NAMESERVER_PORT
                                + "/NameService");
                Environment.setProperty("OAPort", Integer
                        .toString(CLIENT_OA_PORT));
            } else if (myArgs[i].startsWith("-xml=")) {
                try {
                    xmlFile = myArgs[i].split("=")[1];
                    myLogger.info("Using xml=" + xmlFile);
                    BioHolderInitializer.setFile(xmlFile);
                } catch (Exception e) {
                    myLogger.error("Problem parsing arguments on arg "
                            + myArgs[i]);
                    e.printStackTrace();
                }
            } else if (myArgs[i].startsWith("-id=")) {
                try {
                    myID = Integer.parseInt(myArgs[i].split("=")[1]);
                } catch (Exception e) {
                    myLogger.error("Problem parsing arguments on arg "
                            + myArgs[i]);
                    e.printStackTrace();
                }
            } else if (myArgs[i].startsWith("-utServer=")) {
                try {
                    unrealServerGiven = true;
                    unrealServer = myArgs[i].split("=")[1];
                } catch (Exception e) {
                    myLogger.warn("Problem parsing arguments on arg "
                            + myArgs[i]);
                    e.printStackTrace();
                }
            }
        }
        if (xmlFile != null)
            BioHolderInitializer.setFileAndID(myID, xmlFile);
        else
            BioHolderInitializer.setID(myID);

        if (wantsToRunCommandLine)
            runCommandLine(myID);
        else if (wantsToRunGUI)
            runGUI();
        else if (wantsToRunController)
            runHandController();
        else if (wantsToRunUnreal) {
            if (unrealServerGiven) {
                runUnreal2(unrealServer);
            } else {
                runUnreal();
            }
        } else {
            myLogger.info("Using default, starting GUI with server ID=" + myID);
            runGUI();
        }
    }

    /**
     * Runs the SimDesktop front end for the simulation.
     */
    private void runGUI() {
        SimDesktop newDesktop = new SimDesktop();
        newDesktop.setSize(1024, 768);
        newDesktop.setVisible(true);
    }

    /**
     * Runs the commandline front end for the simulation.
     */
    private void runCommandLine(int myID) {
        SimCommandLine newCommandLine = new SimCommandLine(myID);
        newCommandLine.runCommandLine();
    }

    public void runHandController() {
        HandController myController = new HandController();
        myController.runSim();
    }

    /**
     * Runs the UnrealCom interface to communicate with UnrealTournament 2004
     */
    public void runUnreal() {
        UnrealCom myUnrealCom = new UnrealCom();
        myUnrealCom.initUnrealComm();
    }

    /**
     * Runs the UnrealCom interface to communicate with UnrealTournament 2004
     * 
     * @param unServer
     *            The name of the unreal Server to connect to.
     */
    public void runUnreal2(String unServer) {
        UnrealCom myUnrealCom = new UnrealCom(unServer);
        myUnrealCom.initUnrealComm();
    }
}

