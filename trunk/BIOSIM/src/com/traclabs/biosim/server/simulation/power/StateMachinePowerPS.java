package com.traclabs.biosim.server.simulation.power;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * State Machine Power Production System
 * 
 * @author Kenneth McMurtrie
 */

public class StateMachinePowerPS extends PowerPSImpl {
	
	private static final int m_port = 31215;

	private static final String m_serverName = "localhost";
	
	private Logger myLogger = Logger.getLogger(StateMachinePowerPS.class);
	
    public StateMachinePowerPS(int pID, String pName) {
        super(pID, pName);
   }

    protected float calculatePowerProduced() {
        float power = getTickLength() * getPowerFromStateMachine();
        myLogger.debug(getModuleName() + " produced " + power + " watts.");
        return power;
    }
    
    private float getPowerFromStateMachine() {
    	float availablePower = 0f;
    	String loadName = getPowerProducerDefinition().getStores()[0].getModuleName();
		try {
			// connect to server
			final Socket ios = new Socket(m_serverName, m_port);
			myLogger.debug("Client connecting to server at " + m_serverName + ":" + m_port);
			
			// send request (load name) to server
			final PrintWriter os = new PrintWriter(new OutputStreamWriter(ios.getOutputStream()));
			os.println(loadName);
			os.flush();
			myLogger.debug("Client requesting available power for load " + loadName);

			// read response (available power) from server
			final BufferedReader is = new BufferedReader(new InputStreamReader(ios
					.getInputStream()));
			final String power = is.readLine();
			myLogger.debug("Server responded with available power " + power);
			availablePower = Float.parseFloat(power);
		} catch (final IOException e) {
			myLogger.error("Error connecting to server at " + m_serverName + ":" + m_port + " - " + e);
		}
    	
    	return availablePower;
    }
}