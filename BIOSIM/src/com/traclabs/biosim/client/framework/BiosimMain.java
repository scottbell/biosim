package biosim.client.framework;

import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import javax.swing.*;
import java.io.*;
/**
 * A simple driver that creates a SimDesktop, sets the size and visibility, then lets it run.
 *
 * @author    Scott Bell
 */	

public class TestDriver
{
	//Used only for the command line interface
	private BioSimulator myBiosimulator;
	
	/**
	* The method to start the BIOSIM client.
	* @param args can start TestDriver with -gui or -nogui
	*/
	public static void main(String args[]) throws java.lang.InterruptedException
	{
		TestDriver myDriver = new TestDriver();
		if (args.length > 0){
			if (args[0].equals("-gui"))
				myDriver.runGUI();
			else if (args[0].equals("-nogui"))
				myDriver.runCommandLine();
			else{
				System.out.println("Unknown option!  Starting with GUI...");
				myDriver.runGUI();
			}
		}
		else{
			myDriver.runGUI();
		}
		
	}
	
	/**
	* Runs the SimDesktop front end for the simulation.
	*/
	public void runGUI(){
		SimDesktop newDesktop = new SimDesktop();
		newDesktop.setSize(1024, 768);
		newDesktop.setVisible(true);
	}
	
	/**
	* Runs the commandline front end for the simulation.
	*/
	public void runCommandLine(){
		BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
		myBiosimulator = new BioSimulator();
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
			if (!myBiosimulator.isStarted())
				myBiosimulator.spawnSimulation(true);
			else
				System.out.println("Simulation has already started!");
		}
		else if (userCommand.equals("pause")){
			if (!myBiosimulator.isPaused())
				myBiosimulator.pauseSimulation();
			else
				System.out.println("Simulation has already been paused!");
		}
		else if (userCommand.equals("resume")){
			if (myBiosimulator.isPaused())
				myBiosimulator.resumeSimulation();
			else
				System.out.println("Simulation isn't paused!");
		}
		else if (userCommand.equals("stop")){
			if (myBiosimulator.isStarted())
				myBiosimulator.endSimulation();
			else
				System.out.println("Simulation hasn't been started!");
		}
		else if (userCommand.equals("advance")){
			if (myBiosimulator.isPaused())
				myBiosimulator.advanceOneTick();
			else
				System.out.println("Simulation needs to be paused!!");
		}
		else if (userCommand.equals("?")){
			System.out.println("commands: start, stop, pause, resume, quit");
		}
		else{
			System.out.println("Unrecognized command: "+userCommand);
			System.out.println("Type ? for help");
		}
	}
}

