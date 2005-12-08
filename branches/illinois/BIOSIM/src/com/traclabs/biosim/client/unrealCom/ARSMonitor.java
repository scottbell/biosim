/**
 * Used for the unrealComm protocol. Routinely checks the ARS level(s) of the
 * BioSim simulation and then sends that data through a socket to the
 * appropriate UnrealScript NetTrigger call.
 * 
 * ARSMonitor will communicate with the UnrealScript JavaGateway using the
 * following text cmd(s):
 * 
 * netARS <O2Amount><CO2Amount><H2Amount><NAmount><O2Capacity><CO2Capacity>
 * <H2Capacity><NCapacity>
 * 
 * This text string will trigger any/all UnrealScript netARSTrigger, giving it
 * the new ARS info. This can be used for environments with a single ARS system
 * or multiple, connected ARS systems.
 * 
 * netARS <triggerTag><O2Amount><CO2Amount><H2Amount><NAmount><O2Capacity>
 * <CO2Capacity><H2Capacity><NCapacity>
 * 
 * This text string will trigger the UnrealScript netARSTrigger with the given
 * triggerTag, giving it the new ARS info. The triggerTag allows for multiple,
 * unconnected ARS systems in a given environment.
 * 
 * @author: Travis R. Fischer
 */

package com.traclabs.biosim.client.unrealCom;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.air.O2Store;

public class ARSMonitor extends Thread {

    /**
     * @param mySocket -
     *            WaterMonitor instance of the open socket to the Unreal
     *            JavaGateway
     */
    private Socket mySocket;

    /**
     * @param cmdPrefix -
     *            String instructions sent to Unreal JavaGateway
     */
    private String cmdPrefix = "netARS ";

    /**
     * @param myBioHolder -
     *            BioHold for the simulation information.
     */
    private BioHolder myBioHolder;

    /**
     * @param unrealStream -
     *            The output stream used to send commands to the Unreal
     *            JavaGateway
     */
    private PrintWriter unrealStream;

    /**
     * @param myCO2Store -
     *            The CO2Store storage information.
     */
    private CO2Store myCO2Store;

    /**
     * @param myH2Store -
     *            The hydrogen store storage information.
     */
    private H2Store myH2Store;

    /**
     * @param myNitrogenStore -
     *            The nitrogen store storage information.
     */
    private NitrogenStore myNitrogenStore;

    /**
     * @param myO2Store -
     *            The oxygen store storage information.
     */
    private O2Store myO2Store;

    private Logger myLogger;

    /**
     * Constructor to create a new ARSMonitor object.
     * 
     * @param unrealSocket -
     *            open socket to the BioSim3D server JavaGateway
     * @param bioHolder -
     *            BioHolder used to get the simulation's water information.
     */

    ARSMonitor(Socket unrealSocket, BioHolder bioHolder) {
        myLogger = Logger.getLogger(this.getClass());
        mySocket = unrealSocket;
        myBioHolder = bioHolder;

        try {

            unrealStream = new PrintWriter(mySocket.getOutputStream(), true);

        } catch (IOException e) {

            myLogger.error("ARSMonitor: Failed to establish socket I/O.");
            System.exit(1);
        }

        myCO2Store = myBioHolder.theCO2Stores.get(0);
        myH2Store = myBioHolder.theH2Stores.get(0);
        myNitrogenStore = myBioHolder.theNitrogenStores.get(0);
        myO2Store = myBioHolder.theO2Stores.get(0);

    }

    /**
     * Constructor to create a new WaterMonitor object.
     * 
     * @param unrealSocket -
     *            open socket to the BioSim3D server JavaGateway
     * @param bioHolder -
     *            BioHolder used to get the simulation's water information.
     * @param matchTag -
     *            String used to only trigger certain NetARSTriggers if more
     *            than one exist in a level.
     */
    ARSMonitor(Socket unrealSocket, BioHolder bioHolder, String matchTag) {

        mySocket = unrealSocket;
        cmdPrefix = cmdPrefix.concat(matchTag);
        cmdPrefix = cmdPrefix.concat(" ");
        myBioHolder = bioHolder;

        try {

            unrealStream = new PrintWriter(mySocket.getOutputStream(), true);

        } catch (IOException e) {

            myLogger.error("WaterMonitor: Failed to establish socket I/O.");
            System.exit(1);
        }

        myCO2Store = myBioHolder.theCO2Stores.get(0);
        myH2Store = myBioHolder.theH2Stores.get(0);
        myNitrogenStore = myBioHolder.theNitrogenStores.get(0);
        myO2Store = myBioHolder.theO2Stores.get(0);

    }

    public void run() {

        float h2Level;
        float o2Level;
        float co2Level;
        float nLevel;
        float h2Cap;
        float o2Cap;
        float co2Cap;
        float nCap;
        String netRequest;

        myLogger.info("Starting ARSMonitor thread");

        while (true) {

            h2Level = myH2Store.getCurrentLevel();
            o2Level = myO2Store.getCurrentLevel();
            co2Level = myCO2Store.getCurrentLevel();
            nLevel = myNitrogenStore.getCurrentLevel();
            h2Cap = myH2Store.getCurrentCapacity();
            o2Cap = myO2Store.getCurrentCapacity();
            co2Cap = myCO2Store.getCurrentCapacity();
            nCap = myNitrogenStore.getCurrentCapacity();
            netRequest = cmdPrefix + o2Level + " " + co2Level + " " + h2Level
                    + " " + nLevel + " " + o2Cap + " " + co2Cap + " " + h2Cap
                    + " " + nCap;

            unrealStream.println(netRequest);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
            }
        }
    }
}

