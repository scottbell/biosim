package biosim.client.unrealCom;

import java.io.*;
import java.net.*;
import biosim.client.util.*;
import biosim.idl.simulation.water.*;
import biosim.idl.framework.*;

/**
 * Main class for communication between BioSim and the BioSim 3D mod done for UnrealTournament 2004.  
 * Allows 3D graphical display of a BioSim controlled habitat.
 * 
 * @author Travis R. Fischer
 */
public class UnrealCom {
	
	private WaterMonitor myH2Owatch;
	
	private EnvironAirMonitor myCrewEnvWatch;
	
	private EnvironAirMonitor myPlantEnvWatch;
	
	private ARSMonitor myARSwatch;
	
	public void initUnrealComm() {
		
		Socket unrealSocket = null;
		
		BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
		BioDriver myBioDriver = myBioHolder.theBioDriver;
		myBioDriver.setDriverStutterLength(10000);
		myBioDriver.startSimulation();
		myBioDriver.setPauseSimulation(false);
		
		try {
			
			unrealSocket = new Socket("acuratl",7775);
		} catch(UnknownHostException e) {
		
			System.err.println("Error: Could not contact host");
			System.exit(1);	
		} catch(IOException e) {
		
			System.err.println("Error: Failed to establish socket I/O");
			System.exit(1);
		}
		
		myH2Owatch = new WaterMonitor(unrealSocket,myBioHolder);
		myCrewEnvWatch = new EnvironAirMonitor(unrealSocket,myBioHolder,true);
		myPlantEnvWatch = new EnvironAirMonitor(unrealSocket,myBioHolder,false);
		myARSwatch = new ARSMonitor(unrealSocket,myBioHolder);
		myH2Owatch.start();
		myCrewEnvWatch.start();
		myPlantEnvWatch.start();
		myARSwatch.start();
		
		
	}


}