/**
 * Used for the unrealComm protocol. Routinely checks the ARS level(s) of the
 * BioSim simulation and then sends that data through a socket to the appropriate
 * UnrealScript NetTrigger call.
 *
 * ARSMonitor will communicate with the UnrealScript JavaGateway using the following text cmd(s):
 *
 *		netARS <O2Amount> <CO2Amount> <H2Amount> <NAmount> <O2Capacity> <CO2Capacity> <H2Capacity> <NCapacity>
 *
 * This text string will trigger any/all UnrealScript netARSTrigger, giving it the new ARS info.
 * This can be used for environments with a single ARS system or multiple, connected ARS systems.
 *
 *		netARS <triggerTag> <O2Amount> <CO2Amount> <H2Amount> <NAmount> <O2Capacity> <CO2Capacity> <H2Capacity> <NCapacity>
 *
 * This text string will trigger the UnrealScript netARSTrigger with the given triggerTag, giving it the new ARS info.
 * The triggerTag allows for multiple, unconnected ARS systems in a given environment.
 *
 * @author: Travis R. Fischer
 */
 
package biosim.client.unrealCom;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import biosim.client.util.BioHolder;
import biosim.idl.simulation.air.CO2Store;
import biosim.idl.simulation.air.H2Store;
import biosim.idl.simulation.air.NitrogenStore;
import biosim.idl.simulation.air.O2Store;

public class ARSMonitor extends Thread {
	
	/**
	 * @param mySocket - WaterMonitor instance of the open socket to the Unreal JavaGateway
	 */
	private Socket mySocket;
	
	/**
	 * @param cmdPrefix - String instructions sent to Unreal JavaGateway
	 */
	private String cmdPrefix = "netARS ";
	
	/**
	 * @param myBioHolder - BioHold for the simulation information.
	 */
	private BioHolder myBioHolder;
	
	/**
	 * @param unrealStream - The output stream used to send commands to the Unreal JavaGateway
	 */
	private PrintWriter unrealStream;
	
	/**
	 * @param myCO2Store - The CO2Store storage information.
	 */ 
	private CO2Store myCO2Store;
	
	/**
	 * @param myH2Store - The hydrogen store storage information.
	 */ 
	private H2Store myH2Store;
	
	/**
	 * @param myNitrogenStore - The nitrogen store storage information.
	 */ 
	private NitrogenStore myNitrogenStore;
	
	/**
	 * @param myO2Store - The oxygen store storage information.
	 */ 
	private O2Store myO2Store;
	
	/**
	 * Constructor to create a new ARSMonitor object.
	 * @param unrealSocket - open socket to the BioSim3D server JavaGateway
	 * @param bioHolder - BioHolder used to get the simulation's water information.
	 */
	ARSMonitor(Socket unrealSocket, BioHolder bioHolder) {
		
		mySocket = unrealSocket;
		myBioHolder = bioHolder;
		
		try {
		
			unrealStream = new PrintWriter(mySocket.getOutputStream(),true);

		} catch(IOException e) {
		
			System.err.println("ARSMonitor: Failed to establish socket I/O.");
			System.exit(1);
		}
		
		myCO2Store = (CO2Store)myBioHolder.theCO2Stores.get(0);
		myH2Store = (H2Store)myBioHolder.theH2Stores.get(0);
		myNitrogenStore = (NitrogenStore)myBioHolder.theNitrogenStores.get(0);
		myO2Store = (O2Store)myBioHolder.theO2Stores.get(0);
		
	}
	
	/**
	 * Constructor to create a new WaterMonitor object.
	 * @param unrealSocket - open socket to the BioSim3D server JavaGateway
	 * @param bioHolder - BioHolder used to get the simulation's water information.
	 * @param matchTag - String used to only trigger certain NetARSTriggers if more than one exist in a level.
	 */
	ARSMonitor(Socket unrealSocket, BioHolder bioHolder, String matchTag) {
	
		mySocket = unrealSocket;
		cmdPrefix = cmdPrefix.concat(matchTag);	
		cmdPrefix = cmdPrefix.concat(" ");			
		myBioHolder = bioHolder;
		
		try {
		
			unrealStream = new PrintWriter(mySocket.getOutputStream(),true);

		} catch(IOException e) {
		
			System.err.println("WaterMonitor: Failed to establish socket I/O.");
			System.exit(1);
		}
		
		myCO2Store = (CO2Store)myBioHolder.theCO2Stores.get(0);
		myH2Store = (H2Store)myBioHolder.theH2Stores.get(0);
		myNitrogenStore = (NitrogenStore)myBioHolder.theNitrogenStores.get(0);
		myO2Store = (O2Store)myBioHolder.theO2Stores.get(0);
		
		
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
		
		System.out.println("Starting ARSMonitor thread");
		
		while(true) {
			
			h2Level = myH2Store.getLevel();
			o2Level = myO2Store.getLevel();
			co2Level = myCO2Store.getLevel();
			nLevel = myNitrogenStore.getLevel();
			h2Cap = myH2Store.getCapacity();
			o2Cap = myO2Store.getCapacity();
			co2Cap = myCO2Store.getCapacity();
			nCap = myNitrogenStore.getCapacity();
			netRequest = cmdPrefix+o2Level+" "+co2Level+" "+h2Level+" "+nLevel+" "+o2Cap+" "+co2Cap+" "+h2Cap+" "+nCap;
	
			unrealStream.println(netRequest);
			try {
				sleep(2000);
			} catch(InterruptedException e) { }
		}
	}
}
			
		
		
	
	
	