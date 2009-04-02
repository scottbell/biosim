package com.traclabs.biosim.client.unrealCom;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;

/**
 * Main class for communication between BioSim and the BioSim 3D mod done for
 * UnrealTournament 2004. Allows 3D graphical display of a BioSim controlled
 * habitat.
 * 
 * @author Travis R. Fischer
 */
public class UnrealCom {

    /**
     * @param myH2Owatch
     *            WaterMonitor to communicate water stores to UnrealTournament
     */
    private WaterMonitor myH2Owatch;

    /**
     * @param myCrewEnvWatch
     *            EnvironAirMonitor to send crew zone environment info to
     *            UnrealTournamet
     */
    private EnvironAirMonitor myCrewEnvWatch;

    /**
     * @param myPlantEnvWatch
     *            EnvironAirMonitor to send BioMass zone environment info to
     *            UnrealTournamet
     */
    private EnvironAirMonitor myPlantEnvWatch;

    /**
     * @param myARSwatch
     *            ARSMonitor that sends air stores info to UnrealTournament
     */
    private ARSMonitor myARSwatch;

    /**
     * @param myServer
     *            String name of computer running UnrealTournament server.
     */
    private String myServer;

    /**
     * @param bGivenServer
     *            Boolean true if a server name was given.
     */
    private boolean bGivenServer;

    private Logger myLogger;

    /**
     * Default UnrealCom constructor. Creates connection with UnrealTournament
     * server running on same computer.
     */
    public UnrealCom() {
        myLogger = Logger.getLogger(this.getClass());
        bGivenServer = false;

    }

    /**
     * Overloaded UnrealCom constructor that provides a UnrealTournament server
     * name.
     * 
     * @param name
     *            Name of BioSim3D server to connect to.
     */
    public UnrealCom(String name) {
        myLogger = Logger.getLogger(this.getClass());
        myServer = name;
        bGivenServer = true;
    }

    /**
     * Creates a socket connected to myServer on port 7775. Then creates Monitor
     * objects to relay BioSim information to UnrealTournament over given
     * socket. Only does 1-way comunication. Has no idea whether anything's
     * listening on the other side.
     */
    public void initUnrealComm() {

        Socket unrealSocket = null;
        InetAddress mySelf;

        BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
        BioDriver myBioDriver = myBioHolder.theBioDriver;
        myBioDriver.setDriverStutterLength(10000);
        myBioDriver.startSimulation();
        myBioDriver.setPauseSimulation(false);

        try {
            if (!bGivenServer) {

                mySelf = InetAddress.getLocalHost();
                myServer = mySelf.getHostName();

            }

            unrealSocket = new Socket(myServer, 7775);
        } catch (UnknownHostException e) {

            myLogger.error("Error: Could not contact host");
            System.exit(1);
        } catch (IOException e) {
            myLogger.error("Error: Failed to establish socket I/O");
            System.exit(1);
        }

        myH2Owatch = new WaterMonitor(unrealSocket, myBioHolder);
        myCrewEnvWatch = new EnvironAirMonitor(unrealSocket, myBioHolder, true);
        myPlantEnvWatch = new EnvironAirMonitor(unrealSocket, myBioHolder,
                false);
        myARSwatch = new ARSMonitor(unrealSocket, myBioHolder);
        myH2Owatch.start();
        myCrewEnvWatch.start();
        myPlantEnvWatch.start();
        myARSwatch.start();
        myLogger.info("Connected and watching UT Server");
    }

}