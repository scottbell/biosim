package biosim.client.framework;

import biosim.idl.framework.*;
import java.io.*;
/**
 * A simple driver that creates a SimDesktop, sets the size and visibility, then lets it run.
 *
 * @author    Scott Bell
 */	

public class SimCommandLine
{
	BioDriver myDriver;
	
	public void runCommandLine(){
		myDriver = BioHolder.getBioDriver();;
		BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
		while (true){
			try{
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
				System.out.println("Simulation has already started!");
		}
		else if (userCommand.equals("startLog")){
			if (!myDriver.isLogging())
				myDriver.setLogging(true);
			else
				System.out.println("Simulation is already logging!");
		}
		else if (userCommand.equals("stopLog")){
			if (myDriver.isLogging())
				myDriver.setLogging(false);
			else
				System.out.println("Simulation isn't logging!");
		}
		else if (userCommand.equals("pause")){
			if (!myDriver.isPaused())
				myDriver.pauseSimulation();
			else
				System.out.println("Simulation has already been paused!");
		}
		else if (userCommand.equals("resume")){
			if (myDriver.isPaused())
				myDriver.resumeSimulation();
			else
				System.out.println("Simulation isn't paused!");
		}
		else if (userCommand.equals("stop")){
			if (myDriver.isStarted())
				myDriver.endSimulation();
			else
				System.out.println("Simulation hasn't been started!");
		}
		else if (userCommand.equals("advance")){
			if (myDriver.isPaused())
				myDriver.advanceOneTick();
			else
				System.out.println("Simulation needs to be paused!!");
		}
		else if (userCommand.equals("?")){
			System.out.println("commands: start, stop, pause, resume, quit, startLog, stopLog");
		}
		else{
			System.out.println("Unrecognized command: "+userCommand);
			System.out.println("Type ? for help");
		}
	}
}

