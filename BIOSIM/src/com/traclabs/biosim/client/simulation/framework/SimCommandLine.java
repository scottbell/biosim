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
		myDriver.setPauseSimulation(false);
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
				myDriver.startSimulation();
			else
				System.out.println("simulation has already started");
		}
		else if (userCommand.equals("startFullLog")){
			myDriver.setFullLogging(true);
		}
		else if (userCommand.equals("stopFullLog")){
			myDriver.setFullLogging(false);
		}
		else if (userCommand.equals("startSensorLog")){
			myDriver.setSensorLogging(true);
		}
		else if (userCommand.equals("stopSensorLog")){
			myDriver.setSensorLogging(false);
		}
		else if (userCommand.equals("startActuatorLog")){
			myDriver.setActuatorLogging(true);
		}
		else if (userCommand.equals("stopActuatorLog")){
			myDriver.setActuatorLogging(false);
		}
		else if (userCommand.equals("pause")){
			if (!myDriver.isPaused())
				myDriver.setPauseSimulation(true);
			else
				System.out.println("simulation is already paused");
		}
		else if (userCommand.equals("resume")){
			if (myDriver.isPaused())
				myDriver.setPauseSimulation(false);
			else
				System.out.println("simulation isn't paused");
		}
		else if (userCommand.equals("stop")){
			if (myDriver.isStarted())
				myDriver.endSimulation();
			else
				System.out.println("simulation isn't running");
		}
		else if (userCommand.equals("tick")){
			if (myDriver.isPaused())
				myDriver.advanceOneTick();
			else
				System.out.println("simulation needs to be paused first");
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
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			statusBuffer.append(" with "+myDriver.getTicks()+" tick(s)");
			System.out.println(statusBuffer.toString());
		}
		else if (userCommand.equals("?") || userCommand.equals("help")){
			System.out.println("commands: start, stop, pause, resume, status, quit, tick, startFullLog, stopFullLog, startSensorLog, stopSensorLog, startActuatorLog, stopActuatorLog, help");
		}
		else{
			System.out.println("unrecognized command: "+userCommand);
			System.out.println("type ? for help");
		}
	}
	
	private void runTest(){
		float molesOfCO2 = 40f;
		float molesOfH2 = 20f;
		
		//CO2 + 4H2 => CH4 + 2H20
		float limitingReactant = Math.min(molesOfCO2, molesOfH2 / 4f);
		if (limitingReactant == molesOfH2){
			
		}
		else{
		}
	}
}

