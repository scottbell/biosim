/**
 * Used for the unrealComm protocol. Routinely checks the water level(s) of the
 * BioSim simulation and then sends that data through a socket to the appropriate
 * UnrealScript NetTrigger call.
 *
 * WaterMonitor will communicate with the JavaGateway using the following text cmd:
 *
 *		netWRS <eventName> <potableWaterAmount> <greyWaterAmount> <dirtyWaterAmount>
 *
 * This text string will trigger the appropriate netTrigger and activate <eventName>
 * with the given water amounts.
 *
 * @author: Travis R. Fischer
 */
 
package biosim.client.unrealCom;

import java.net.*;
import java.io.*;
import biosim.client.util.*;
import biosim.idl.simulation.water.*;
import biosim.idl.framework.*;

public class WaterMonitor extends Thread {
	
	/**
	 * @param mySocket - WaterMonitor instance of the open socket to the Unreal JavaGateway
	 */
	private Socket mySocket;
	
	/**
	 * @param cmdPrefix - String instructions sent to Unreal JavaGateway
	 */
	private String cmdPrefix = "netWRS ";
	
	/**
	 * @param myBioHolder - BioHold for the simulation information.
	 */
	private BioHolder myBioHolder;
	
	/**
	 * @param unrealStream - The output stream used to send commands to the Unreal JavaGateway
	 */
	private PrintWriter unrealStream;
	
	/**
	 * @param myPotableWaterStore - The PotableWater storage information.
	 */ 
	private PotableWaterStore myPotableWaterStore;
	
	/**
	 * @param myGreyWaterStore - The grey water storage information.
	 */ 
	private GreyWaterStore myGreyWaterStore;
	
	/**
	 * @param myDirtyWaterStore - The dirty Water storage information.
	 */ 
	private DirtyWaterStore myDirtyWaterStore;
	
	/**
	 * Constructor to create a new WaterMonitor object.
	 * @param unrealSocket - open socket to the BioSim3D server JavaGateway
	 * @param eventName - used to communicate with the BioSim3D server based on pre-established BioSim3D Protocol
	 * @param bioHolder - BioHolder used to get the simulation's water information.
	 */
	WaterMonitor(Socket unrealSocket, String eventName, BioHolder bioHolder) {
		
		mySocket = unrealSocket;
		cmdPrefix.concat(eventName);		
		myBioHolder = bioHolder;
		
		try {
		
			unrealStream = new PrintWriter(mySocket.getOutputStream(),true);

		} catch(IOException e) {
		
			System.err.println("WaterMonitor: Failed to establish socket I/O.");
			System.exit(1);
		}
		
		myPotableWaterStore = (PotableWaterStore)myBioHolder.thePotableWaterStores.get(0);
		myGreyWaterStore = (GreyWaterStore)myBioHolder.theGreyWaterStores.get(0);
		myDirtyWaterStore = (DirtyWaterStore)myBioHolder.theDirtyWaterStores.get(0);
	}
	
	 
	public void run() {
		
		float potableLevel;
		float dirtyLevel;
		float greyLevel;
		
		System.out.println("Starting WaterMonitor thread");
		
		while(true) {
			
			potableLevel = myPotableWaterStore.getLevel();
			greyLevel = myGreyWaterStore.getLevel();
			dirtyLevel = myDirtyWaterStore.getLevel();
			
			unrealStream.println(cmdPrefix+potableLevel+" "+greyLevel+" "+dirtyLevel);
			try {
				sleep(1000);
			} catch(InterruptedException e) { }
		}
	}
}
			
		
		
	
	
	