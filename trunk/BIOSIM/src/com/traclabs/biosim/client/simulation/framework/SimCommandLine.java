package biosim.client.simulation.framework;

import biosim.idl.framework.*;
import biosim.client.util.*;
import java.io.*;
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
				myDriver.spawnSimulationTillDead();
			else
				System.out.println("simulation has already started");
		}
		else if (userCommand.equals("startLog")){
			if (!myDriver.isLogging())
				myDriver.setLogging(true);
			else
				System.out.println("simulation is already logging");
		}
		else if (userCommand.equals("stopLog")){
			if (myDriver.isLogging())
				myDriver.setLogging(false);
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
				statusBuffer.append("not-paused, ");
			if (myDriver.isLogging())
				statusBuffer.append("logging, ");
			else
				statusBuffer.append("not-logging, ");
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			System.out.println(statusBuffer.toString());
		}
		else if (userCommand.equals("?") || userCommand.equals("help")){
			System.out.println("commands: start, stop, pause, resume, status, quit, startLog, stopLog, help");
		}
		else{
			System.out.println("unrecognized command: "+userCommand);
			System.out.println("type ? for help");
		}
	}
	
	private void runTest(){
	}
}

