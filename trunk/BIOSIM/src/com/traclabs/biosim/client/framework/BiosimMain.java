package biosim.client.framework;

import java.util.StringTokenizer;

import biosim.client.simulation.framework.SimCommandLine;
import biosim.client.simulation.framework.gui.SimDesktop;
import biosim.client.unrealCom.UnrealCom;
import biosim.client.util.BioHolderInitializer;
/**
 * @author    Scott Bell
 */	

public class BiosimMain
{

	private static final int NAMESERVER_PORT = 16309;
	private static final int CLIENT_OA_PORT = 16311;
	
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
		boolean wantsToRunController = false;
		boolean wantsToRunUnreal = false;
		boolean unrealServerGiven = false;
		boolean wantsToRunDebug = false;
		String unrealServer = "";
		for (int i = 0; i < myArgs.length; i++){
			if (myArgs[i].equals("gui")){
				wantsToRunGUI = true;
			}
			else if (myArgs[i].equals("console")){
				wantsToRunCommandLine = true;
			}
			else if (myArgs[i].equals("controller")){
				wantsToRunController = true;
			}
			else if (myArgs[i].equals("unreal")){
				wantsToRunUnreal = true;
			}
			else if (myArgs[i].equals("debug")){
				wantsToRunDebug = true;
			}
			else if (myArgs[i].equals("-xml=")){
				try{
					StringTokenizer st = new StringTokenizer(myArgs[i],"=");
					st.nextToken();
					BioHolderInitializer.setFile(st.nextToken());
				}
				catch (Exception e){
					System.err.println("Problem parsing arguments on arg "+myArgs[i]);
					e.printStackTrace();
				}
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
			else if(myArgs[i].startsWith("-utServer=")) {
				try{
					StringTokenizer st = new StringTokenizer(myArgs[i],"=");
					st.nextToken();
					unrealServerGiven = true;
					unrealServer = st.nextToken();
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
		else if (wantsToRunController)
			runHandController(myID);
		else if (wantsToRunUnreal) {
			if(unrealServerGiven) {
				runUnreal2(myID,unrealServer);
			} else {
				runUnreal(myID);
			}
		}
		else if (wantsToRunDebug)
			runDebug(myID);
		else{
			System.out.println("Using default, starting GUI with server ID="+myID);
			runGUI(myID);
		}
	}

	/**
	* Runs the SimDesktop front end for the simulation.
	*/
	private void runGUI(int myID){
		BioHolderInitializer.setID(myID);
		SimDesktop newDesktop = new SimDesktop(myID);
		newDesktop.setSize(1024, 768);
		newDesktop.setVisible(true);
	}

	/**
	* Runs the commandline front end for the simulation.
	*/
	private void runCommandLine(int myID){
		SimCommandLine newCommandLine = new SimCommandLine(myID);
		newCommandLine.runCommandLine();
	}
	
	/**
	* Runs the debug commandLine front end for the simulation.
	*/
	private void runDebug(int myID){
		org.jacorb.util.Environment.setProperty("ORBInitRef.NameService", "corbaloc::localhost:"+NAMESERVER_PORT+"/NameService");
		org.jacorb.util.Environment.setProperty("OAPort", Integer.toString(CLIENT_OA_PORT));
		SimCommandLine newCommandLine = new SimCommandLine(myID);
		newCommandLine.runCommandLine();
	}
	
	public void runHandController(int myID){
		BioHolderInitializer.setID(myID);
		HandController myController = new HandController();
		myController.runSim();
	}
	
	/**
	 * Runs the UnrealCom interface to communicate with UnrealTournament 2004
	 */
	public void runUnreal(int myID){
		BioHolderInitializer.setID(myID);
		
		UnrealCom myUnrealCom = new UnrealCom();
		
		myUnrealCom.initUnrealComm();
		
	}
	
	/**
	 * Runs the UnrealCom interface to communicate with UnrealTournament 2004
	 * @param unServer The name of the unreal Server to connect to.
	 */
	public void runUnreal2(int myID, String unServer){
		BioHolderInitializer.setID(myID);
		
		UnrealCom myUnrealCom = new UnrealCom(unServer);
		
		myUnrealCom.initUnrealComm();
		
	}
}

