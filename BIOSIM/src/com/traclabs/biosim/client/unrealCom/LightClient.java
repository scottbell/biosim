package biosim.client.unrealCom;

import java.io.*;
import java.net.*;
import biosim.client.util.*;
import biosim.idl.simulation.water.*;
import biosim.idl.framework.*;

public class LightClient {
	
	private WaterMonitor myH2Owatch;
	
	private EnvironAirMonitor myCrewEnvWatch;
	
	private EnvironAirMonitor myPlantEnvWatch;
	
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
		
		myH2Owatch = new WaterMonitor(unrealSocket,"waterTest",myBioHolder);
		myCrewEnvWatch = new EnvironAirMonitor(unrealSocket,"changeMain",myBioHolder,true);
		myPlantEnvWatch = new EnvironAirMonitor(unrealSocket,"plantLightChange",myBioHolder,false);
		//myH2Owatch.start();
		myCrewEnvWatch.start();
		myPlantEnvWatch.start();
		
		
	}

/*
	public static void main(String[] args) throws IOException {
	
	
		Socket lightSocket = null;
		PrintWriter out = null;
		char firstChar;
		
		
		BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
		BioDriver myBioDriver = myBioHolder.theBioDriver;
		
		myBioDriver.setDriverStutterLength(10000);
		myBioDriver.startSimulation();
		myBioDriver.setPauseSimulation(false);
		PotableWaterStore myPotableWaterStore = (PotableWaterStore)myBioHolder.thePotableWaterStores.get(0);
		float waterLevel = myPotableWaterStore.getLevel();
		System.out.println("Water Level is "+waterLevel);
		String outgoingCmd = "netTrigger lightChange";
		
		try {
			
			lightSocket = new Socket("acuratl",7775);
			
			
			
		
			out = new PrintWriter(lightSocket.getOutputStream(),true);

		} catch(UnknownHostException e) {
		
			System.err.println("Error: Could not contact host");
			System.exit(1);	
		} catch(IOException e) {
		
			System.err.println("Error: Failed to establish socket I/O");
			System.exit(1);
		}
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput,bioInput;
		
		System.out.println("Press <Enter> to change the light.");
		System.out.println("Type <q> to exit.");
		while((firstChar=(char)stdIn.read())!='q') {
			userInput = stdIn.readLine();
			System.out.println("Changing light...");
			out.println(outgoingCmd);
		}
		
		stdIn.close();
		out.close();
		lightSocket.close();
	}
	
	*/
}