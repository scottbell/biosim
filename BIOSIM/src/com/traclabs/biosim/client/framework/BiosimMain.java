package biosim.client.framework;

import biosim.client.simulation.framework.gui.*;
import biosim.client.simulation.framework.*;
import biosim.client.framework.gui.*;
import java.io.*;
import java.util.*;
/**
 * @author    Scott Bell
 */	

public class BiosimMain
{
	/**
	* The method to start the BIOSIM client.
	* @param args can start TestDriver with -gui or -nogui (optional id=X where X is an integer)
	*/
	public static void main(String args[])
	{
		BiosimMain myMain = new BiosimMain();
		myMain.checkArgs(args);
	}

	private void checkArgs(String[] myArgs){
		int myID = 0;
		boolean wantsToRunCommandLine = false;
		boolean wantsToRunGUI = false;
		for (int i = 0; i < myArgs.length; i++){
			if (myArgs[i].equals("gui")){
				wantsToRunGUI = true;
			}
			else if (myArgs[i].equals("console")){
				wantsToRunCommandLine = true;
			}
			else if (myArgs[i].startsWith("-id=")){
				try{
					StringTokenizer st = new StringTokenizer(myArgs[i],"=");
					st.nextToken();
					myID = Integer.parseInt(st.nextToken());
				}
				catch (Exception e){
					System.err.println("Problem parsing arguments on arg "+myArgs[i]);
					e.printStackTrace();
				}
			}
		}
		if (wantsToRunCommandLine)
			runCommandLine(myID);
		else if (wantsToRunGUI)
			runGUI(myID);
		else{
			System.out.println("Unknown option, starting GUI with server ID="+myID);
			runGUI(myID);
		}
	}

	/**
	* Runs the SimDesktop front end for the simulation.
	*/
	public void runGUI(int myID){
		SimDesktop newDesktop = new SimDesktop(myID);
		newDesktop.setSize(1024, 768);
		newDesktop.setVisible(true);
	}

	/**
	* Runs the commandline front end for the simulation.
	*/
	public void runCommandLine(int myID){
		SimCommandLine newCommandLine = new SimCommandLine(myID);
		newCommandLine.runCommandLine();
	}
}

