package biosim.client.framework;

import biosim.client.framework.gui.*;
import java.io.*;
/**
 * @author    Scott Bell
 */	

public class BiosimMain
{
	/**
	* The method to start the BIOSIM client.
	* @param args can start TestDriver with -gui or -nogui
	*/
	public static void main(String args[]) throws java.lang.InterruptedException
	{
		
		BiosimMain myMain = new BiosimMain();
		if (args.length > 0){
			if (args[0].equals("gui")){
				myMain.runGUI();
			}
			else if (args[0].equals("console")){
				myMain.runCommandLine();
				
			}
			else{
				System.out.println("Unknown option!  Starting with GUI...");
				myMain.runGUI();
			}
		}
		else{
			System.out.println("No args.  Starting with GUI.");
			myMain.runGUI();
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
		SimCommandLine newCommandLine = new SimCommandLine();
		newCommandLine.runCommandLine();
	}
	
}

