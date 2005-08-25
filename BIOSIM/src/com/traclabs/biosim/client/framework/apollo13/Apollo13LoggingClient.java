package com.traclabs.biosim.client.framework.apollo13;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;

public class Apollo13LoggingClient {
	private Logger myLogger;
	private BioHolder myBioHolder;
	
	public Apollo13LoggingClient(){
		myLogger = Logger.getLogger(this.getClass());
		myBioHolder = BioHolderInitializer.getBioHolder();
	}
	
	public void startRun(){
		myBioHolder.theBioDriver.setPauseSimulation(true);
		myBioHolder.theBioDriver.startSimulation();
		while (!myBioHolder.theBioDriver.isDone()){
			log();
			myBioHolder.theBioDriver.advanceOneTick();
		}
	}

	private void log() {
		myLogger.info("valve state = "+ myBioHolder.theInfluentValveStateSensors.get(0).getValue());
	}

	public static void main(String[] strings) {
		Apollo13LoggingClient theLoggingClient = new Apollo13LoggingClient();
		theLoggingClient.startRun();
	}

}
