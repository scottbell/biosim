package com.traclabs.biosim.client.framework.apollo13;

import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.BorderFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.traclabs.biosim.client.simulation.framework.SimulationPanel;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;

public class Apollo13Viewer extends SimulationPanel{
	private Logger myLogger;

	private BioHolder myBioHolder;
	
	private float o2Usage = 0f;

	private ApolloGraphPanel myGraphPanel;

	public Apollo13Viewer() {
		configureLogger();
		myBioHolder = BioHolderInitializer.getBioHolder();
		myGraphPanel = new ApolloGraphPanel();
		myGraphPanel.setBorder(BorderFactory.createTitledBorder("Data"));
		add(myGraphPanel, BorderLayout.CENTER);
		myBioHolder.theBioDriver.reset();
		myBioHolder.theBioDriver.setPauseSimulation(true);
		myBioHolder.theBioDriver.startSimulation();
	}
	
	protected void refresh(){
		myGraphPanel.refresh();
	}
	
	protected void reset(){
		myGraphPanel.reset();
	}
	
	private void configureLogger(){
		myLogger = Logger.getLogger(this.getClass());
		Properties logProps = new Properties();
		logProps.setProperty("log4j.rootLogger", "INFO, rootAppender");
		logProps.setProperty("log4j.appender.rootAppender",
				"org.apache.log4j.ConsoleAppender");
		logProps.setProperty("log4j.appender.rootAppender.layout",
				"org.apache.log4j.PatternLayout");
		logProps.setProperty(
				"log4j.appender.rootAppender.layout.ConversionPattern", "%m%n");
		PropertyConfigurator.configure(logProps);
	}

	public void startRun() {
		myLogger.info("timetag,O2 flow rate,Valve command,Valve state,O2 concentration in cabin,CO2 concentration in cabin,O2 tank pressure,O2 usage over timestep");
		while (!myBioHolder.theBioDriver.isDone()) {
			log();
			myBioHolder.theBioDriver.advanceOneTick();
		}
	}

	private void log() {
		StringBuilder logString = new StringBuilder();
		logString.append(myBioHolder.theBioDriver.getTicks());
		logString.append(",");
		float o2FlowRate = convertO2ToPounds(myBioHolder.theO2OutFlowRateSensors.get(0).getValue());
		logString.append(o2FlowRate);
		logString.append(",");
		float o2ValveCommand = myBioHolder.theInfluentValveActuators.get(0).getValue();
		logString.append(processValveCommand(o2ValveCommand));
		logString.append(",");
		float o2ValveState = myBioHolder.theInfluentValveStateSensors.get(0).getValue();
		logString.append(processValveState(o2ValveState));
		logString.append(",");
		logString.append(myBioHolder.theGasConcentrationSensors.get(0).getValue());
		logString.append(",");
		logString.append(myBioHolder.theGasConcentrationSensors.get(1).getValue());
		logString.append(",");
		logString.append(myBioHolder.theStoreLevelSensors.get(0).getValue());
		logString.append(",");
		o2Usage += o2FlowRate;
		logString.append(o2Usage);
		myLogger.info(logString);
	}

	private String processValveState(float valveSensorValue) {
		if (valveSensorValue == 0)
			return "1";
		if (valveSensorValue == 1)
			return "2";
		else
			return "0";
	}
	
	private String processValveCommand(float valveActuatorValue) {
		if (valveActuatorValue == 0)
			return "1";
		if (valveActuatorValue == 1)
			return "2";
		else
			return "0";
	}

	public static void main(String[] strings) {
		Apollo13Viewer theLoggingClient = new Apollo13Viewer();
		theLoggingClient.startRun();
	}
	
	private float convertO2ToPounds(float o2InMoles){
		float molecularWeightOfO2 = 31.998f;
		float o2InGrams = o2InMoles * molecularWeightOfO2;
		float o2InPounds = 0.00220462262f * o2InGrams;
		return o2InPounds;
	}

}
