/**
 * Used for the unrealComm protocol. Routinely checks the water level(s) of the
 * BioSim simulation and then sends that data through a socket to the
 * appropriate UnrealScript NetTrigger call.
 * 
 * WaterMonitor will communicate with the JavaGateway using the following text
 * cmd(s):
 * 
 * netWRS <potableWaterAmount><greyWaterAmount><dirtyWaterAmount>
 * <potableWaterCapacity><greyWaterCapacity><dirtyWaterCapacity>
 * 
 * This text string will trigger any/all UnrealScript netWRSTrigger, giving it
 * the new WRS info. This can be used for environments with a single WRS system
 * or multiple, connected WRS systems.
 * 
 * netWRS <triggerTag><potableWaterAmount><greyWaterAmount><dirtyWaterAmount>
 * <potableWaterCapacity><greyWaterCapacity><dirtyWaterCapacity>
 * 
 * This text string will trigger the UnrealScript netWRSTrigger with the given
 * triggerTag, giving it the new WRS info. The triggerTag allows for multiple,
 * unconnected WRS systems in a given environment.
 * 
 * @author: Travis R. Fischer
 */

package com.traclabs.biosim.client.unrealCom;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;

public class WaterMonitor extends Thread {

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
    private String cmdPrefix = "netWRS ";

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
     * @param myPotableWaterStore -
     *            The PotableWater storage information.
     */
    private PotableWaterStore myPotableWaterStore;

    /**
     * @param myGreyWaterStore -
     *            The grey water storage information.
     */
    private GreyWaterStore myGreyWaterStore;

    /**
     * @param myDirtyWaterStore -
     *            The dirty Water storage information.
     */
    private DirtyWaterStore myDirtyWaterStore;

    private Logger myLogger;

    /**
     * Constructor to create a new WaterMonitor object.
     * 
     * @param unrealSocket -
     *            open socket to the BioSim3D server JavaGateway
     * @param bioHolder -
     *            BioHolder used to get the simulation's water information.
     */
    WaterMonitor(Socket unrealSocket, BioHolder bioHolder) {
        myLogger = Logger.getLogger(this.getClass());
        mySocket = unrealSocket;
        myBioHolder = bioHolder;

        try {

            unrealStream = new PrintWriter(mySocket.getOutputStream(), true);

        } catch (IOException e) {

            myLogger.error("WaterMonitor: Failed to establish socket I/O.");
            System.exit(1);
        }

        myPotableWaterStore = myBioHolder.thePotableWaterStores
                .get(0);
        myGreyWaterStore = myBioHolder.theGreyWaterStores
                .get(0);
        myDirtyWaterStore = myBioHolder.theDirtyWaterStores
                .get(0);
    }

    /**
     * Constructor to create a new WaterMonitor object.
     * 
     * @param unrealSocket -
     *            open socket to the BioSim3D server JavaGateway
     * @param bioHolder -
     *            BioHolder used to get the simulation's water information.
     * @param matchTag -
     *            String used to only trigger certain NetWRSTriggers if more
     *            than one exist in a level.
     */
    WaterMonitor(Socket unrealSocket, BioHolder bioHolder, String matchTag) {
        myLogger.info("matchTag: " + matchTag);
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

        myPotableWaterStore = myBioHolder.thePotableWaterStores
                .get(0);
        myGreyWaterStore = myBioHolder.theGreyWaterStores
                .get(0);
        myDirtyWaterStore = myBioHolder.theDirtyWaterStores
                .get(0);
    }

    public void run() {

        float potableLevel;
        float dirtyLevel;
        float greyLevel;
        float potableCap;
        float dirtyCap;
        float greyCap;

        myLogger.info("Starting WaterMonitor thread");

        while (true) {

            potableLevel = myPotableWaterStore.getCurrentLevel();
            greyLevel = myGreyWaterStore.getCurrentLevel();
            dirtyLevel = myDirtyWaterStore.getCurrentLevel();
            potableCap = myPotableWaterStore.getCurrentCapacity();
            greyCap = myGreyWaterStore.getCurrentCapacity();
            dirtyCap = myGreyWaterStore.getCurrentCapacity();

            unrealStream.println(cmdPrefix + potableLevel + " " + greyLevel
                    + " " + dirtyLevel + " " + potableCap + " " + greyCap + " "
                    + dirtyCap);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
            }
        }
    }
}

