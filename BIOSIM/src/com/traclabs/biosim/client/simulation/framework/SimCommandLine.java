package biosim.client.simulation.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import biosim.client.util.BioHolder;
import biosim.client.util.BioHolderInitializer;
import biosim.client.util.OrbUtils;
import biosim.idl.framework.BioDriver;
import biosim.idl.framework.BioDriverHelper;
import biosim.idl.sensor.food.BiomassStoreLevelSensor;
import biosim.idl.sensor.food.BiomassStoreLevelSensorHelper;
import biosim.idl.simulation.food.BiomassStore;

/**
 * Runs a CLI interface to the simulation.
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
		myDriver = grabDriver();
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
	
	private BioDriver grabDriver(){
		BioDriver driverToReturn = null;
		while (driverToReturn == null){
			try{
				driverToReturn = BioDriverHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str("BioDriver"));
			}
			catch (org.omg.CORBA.UserException e){
				System.err.println("SimCommandLine: Couldn't find BioDriver, polling again...");
				OrbUtils.sleepAwhile();
			}
			catch (Exception e){
				System.err.println("SimCommandLine: Had problems contacting nameserver for BioDriver polling again...");
				OrbUtils.resetInit();
				OrbUtils.sleepAwhile();
			}
		}
		return driverToReturn;
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
			if (!myDriver.isStarted()){
				System.out.println("simulation needs to be started first");
				return;
			}
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
			System.out.println("commands: start, stop, pause, resume, status, quit, tick, help");
		}
		else{
			System.out.println("unrecognized command: "+userCommand);
			System.out.println("type ? for help");
		}
	}

	private void runTest(){
		BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
		
		BiomassStore myBiomassStore = (BiomassStore)myBioHolder.theBiomassStores.get(0);
		BiomassStoreLevelSensor currentSensor = BiomassStoreLevelSensorHelper.narrow((myBioHolder.getSensorAttachedTo(myBioHolder.theBiomassStoreLevelSensors, myBiomassStore)));
		//replant 100 meters squared of rice
		System.out.println("Value of sensor is: "+currentSensor.getValue());
	}
	
}

