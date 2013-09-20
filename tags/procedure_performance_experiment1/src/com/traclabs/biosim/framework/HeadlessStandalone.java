package com.traclabs.biosim.framework;

import com.traclabs.biosim.client.control.SimCommandLine;
import com.traclabs.biosim.client.framework.BiosimMain;

/**
 * A standalone BioSim instance (server, nameserver, client in one)
 * 
 * @author Scott Bell
 */

public class HeadlessStandalone extends BiosimStandalone {

	@Override
	protected void runClient() {
		SimCommandLine newCommandLine = new SimCommandLine(0);
		newCommandLine.getDriver().startSimulation();
	}

	public HeadlessStandalone(String xmlFilename, int driverPause) {
		super(xmlFilename, driverPause);
	}
	
	public static void main(String args[]) {
    	String filename = "cdrs/cdrs.biosim";
    	if (args.length > 0){
    		filename = BiosimMain.getArgumentValue(args[0]);
    	}
    	HeadlessStandalone standalone = new HeadlessStandalone(filename, 500);
    	standalone.beginSimulation();
    }

}
