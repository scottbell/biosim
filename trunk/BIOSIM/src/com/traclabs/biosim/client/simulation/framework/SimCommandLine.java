package biosim.client.simulation.framework;

import biosim.idl.framework.*;
import biosim.client.util.*;
import java.io.*;

import biosim.idl.sensor.water.*;

/**
 * A simple driver that creates a SimDesktop, sets the size and visibility, then lets it run.
 *
 * @author    Scott Bell
 */	

public class SimCommandLine
{
	private BioDriver myDriver;
	private int myID = 0;
	public SimCommandLine(int pID){
		myID = pID;
	}
	
	public void runCommandLine(){
		BioHolder.setID(myID);
		myDriver = BioHolder.getBioDriver();
		BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
		while (true){
			try{
				System.out.print("> ");
				String userCommand = userInputReader.readLine();
				processCommand(userCommand);
			}
			catch (IOException e){
				System.out.println("Had problem reading your command, try again...");
			}
		}
	}
	
	/**
	* Takes a command given by the user and performs and action.
	* @param userCommand the user command taken from input
	*/
	private void processCommand(String userCommand){
		if (userCommand.equals("quit")){
			System.exit(0);
		}
		else if (userCommand.equals("start")){
			if (!myDriver.isStarted())
				myDriver.spawnSimulationAndRunTillDead();
			else
				System.out.println("simulation has already started");
		}
		else if (userCommand.equals("startFullLog")){
			if (!myDriver.isFullLogging())
				myDriver.setFullLogging(true);
			else
				System.out.println("simulation is already logging");
		}
		else if (userCommand.equals("stopFullLog")){
			if (myDriver.isFullLogging())
				myDriver.setFullLogging(false);
			else
				System.out.println("simulation isn't logging");
		}
		else if (userCommand.equals("startSensorLog")){
			if (!myDriver.isSensorLogging())
				myDriver.setSensorLogging(true);
			else
				System.out.println("simulation is already logging");
		}
		else if (userCommand.equals("stopSensorLog")){
			if (myDriver.isSensorLogging())
				myDriver.setSensorLogging(false);
			else
				System.out.println("simulation isn't logging");
		}
		else if (userCommand.equals("startActuatorLog")){
			if (!myDriver.isActuatorLogging())
				myDriver.setActuatorLogging(true);
			else
				System.out.println("simulation is already logging");
		}
		else if (userCommand.equals("stopActuatorLog")){
			if (myDriver.isActuatorLogging())
				myDriver.setActuatorLogging(false);
			else
				System.out.println("simulation isn't logging");
		}
		else if (userCommand.equals("pause")){
			if (!myDriver.isPaused())
				myDriver.pauseSimulation();
			else
				System.out.println("simulation is already paused");
		}
		else if (userCommand.equals("resume")){
			if (myDriver.isPaused())
				myDriver.resumeSimulation();
			else
				System.out.println("simulation isn't paused");
		}
		else if (userCommand.equals("stop")){
			if (myDriver.isStarted())
				myDriver.endSimulation();
			else
				System.out.println("simulation isn't running");
		}
		else if (userCommand.equals("advance")){
			if (myDriver.isPaused())
				myDriver.advanceOneTick();
			else
				System.out.println("simulation needs to be paused frist");
		}
		else if (userCommand.equals("test")){
			runTest();
		}
		else if (userCommand.equals("status")){
			StringBuffer statusBuffer = new StringBuffer("simulation is: ");
			if (myDriver.isStarted())
				statusBuffer.append("running, ");
			else
				statusBuffer.append("stopped, ");
			if (myDriver.isPaused())
				statusBuffer.append("paused, ");
			else
				statusBuffer.append("not paused, ");
			if (myDriver.isFullLogging())
				statusBuffer.append("full logging, ");
			else
				statusBuffer.append("not full logging, ");
			if (myDriver.isSensorLogging())
				statusBuffer.append("sensor logging, ");
			else
				statusBuffer.append("not sensor logging, ");
			if (myDriver.isActuatorLogging())
				statusBuffer.append("actuator logging, ");
			else
				statusBuffer.append("not actuator logging, ");
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			System.out.println(statusBuffer.toString());
		}
		else if (userCommand.equals("?") || userCommand.equals("help")){
			System.out.println("commands: start, stop, pause, resume, status, quit, startFullLog, stopFullLog, startSensorLog, stopSensorLog, startActuatorLog, stopActuatorLog, help");
		}
		else{
			System.out.println("unrecognized command: "+userCommand);
			System.out.println("type ? for help");
		}
	}
	
	private void runTest(){
		BioHolder.getBioDriver().configureSimulation();
		PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = (PotableWaterStoreLevelSensor)(BioHolder.getBioModule(BioHolder.myPotableWaterStoreLevelSensorName));
		System.out.println("getMax returns: "+myPotableWaterStoreLevelSensor.getMax());
	}
}

