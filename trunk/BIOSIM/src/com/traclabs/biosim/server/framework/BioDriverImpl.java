package biosim.server.framework;

import biosim.idl.framework.*;
import biosim.idl.util.log.*;
import biosim.idl.simulation.crew.*;
import biosim.server.util.*;
import java.util.*;

/*
 *
 * @author    Scott Bell
 */

public class BioDriverImpl extends BioDriverPOA{
	//The thread to run the simulation
	private Thread myTickThread;
	//Flag to see whether the BioDriverImpl is paused (started but not ticking)
	private boolean simulationIsPaused = false;
	//Flag to see whether the BioDriverImpl is started at all
	private boolean simulationStarted = false;
	//If <runTillN == true, this is the number of ticks to run for.
	private int nTicks = 0;
	//The number of ticks gone by
	private int ticksGoneBy = 0;
	//Tells whether simulation runs until crew death
	private boolean runTillDead = false;
	//Tells whether simulation runs till a fixed number of ticks
	private boolean runTillN = false;
	//If <runTillN == true, this is the number of ticks to run for.
	//The logger module
	private Logger myLogger;
	//How long BioDriver should pause between ticks
	private int driverPauseLength = 0;
	//The ID of this instance of BioSim
	private int myID = 0;
	//If we loop after end conditions of a simulation run have been met (crew death or n-ticks)
	private boolean looping = false;
	private CrewGroup[] crews;
	private BioModule[] modules;
	private BioModule[] sensors;
	private BioModule[] actuators;

	/**
	* Constructs the BioDriver
	* @param pID The ID of this instance of the BioSim (must be the same for all modules in the instance)
	*/
	public BioDriverImpl(int pID){
		myID = pID;
		modules = new BioModule[0];
		sensors = new BioModule[0];
		actuators = new BioModule[0];
		crews = new CrewGroup[0];
	}

	/**
	* Starts the simulation
	*/
	public void startSimulation(){
		myTickThread = new Thread(new Ticker());
		myTickThread.start();
	}

	/**
	* Returns the name of this instance of BioDriver
	* @return The name of this instance (BioDriver + ID)
	*/
	public String getName(){
		return "BioDriver";
	}

	/**
	* Checks to see if the simulation is paused.
	* @return <code>true</code> if paused, <code>false</code> if not
	*/
	public boolean isPaused(){
		return simulationIsPaused;
	}

	/**
	* Checks to see if the simulation has started.
	* @return <code>true</code> if started, <code>false</code> if not
	*/
	public boolean isStarted(){
		return simulationStarted;
	}

	/**
	* Tells The ID of this module.  Should be the same as every other module in this BioSim instance
	* @return The ID of this module.  Should be the same as every other module in this BioSim instance
	*/
	public int getID(){
		return myID;
	}

	/**
	* Simulation runs till all the crew dies if true.
	*/
	public synchronized void setRunTillDead(boolean pRunTillDead){
		runTillDead = pRunTillDead;
	}

	public void setModules(BioModule[] pModules){
		modules = pModules;
	}

	public void setSensors(BioModule[] pSensors){
		sensors = pSensors;
	}

	public void setActuators(BioModule[] pActuators){
		actuators = pActuators;
	}

	public void setCrewsToWatch(CrewGroup[] pCrewGroups){
		crews = pCrewGroups;
	}

	/**
	* Simulation runs till n ticks.
	* @param pTicks ticks to run simulation
	*/
	public synchronized void setRunTillN(int pTicks){
		nTicks = pTicks;
		if (nTicks > 0)
			runTillN = true;
	}

	/**
	* If n-ticks have been reached or the crew is dead, the simulation restarts
	*/
	public synchronized void setLoopSimulation(boolean pLooping){
		looping = pLooping;
	}

	public synchronized void setPauseSimulation(boolean pPaused){
		simulationIsPaused = pPaused;
		if (!simulationIsPaused)
			notify();
	}

	public void setLogger(Logger pLogger){
		myLogger = pLogger;
	}

	/**
	* Sets how long BioDriver should pause between full simulation ticks (e.g., tick all modules, wait, tick all modules, wait, etc.)
	* @param pDriverPauseLength the length (in milliseconds) for the driver to pause between ticks
	*/
	public synchronized void setDriverStutterLength(int pDriverPauseLength){
		if (pDriverPauseLength > 0)
			System.out.println("BioDriverImpl:"+myID+" driver pause of "+pDriverPauseLength+" milliseconds");
		driverPauseLength = pDriverPauseLength;
	}

	/**
	* Tells how long the simulation pauses between full simulation ticks.
	* @return How long the simulation pauses between full simulation ticks.
	*/
	public int getDriverStutterLength(){
		return driverPauseLength;
	}

	/**
	* Tells whether BioDriver is looping the simulation after end conditions have been met.
	* @return Whether BioDriver is looping the simulation after end conditions have been met.
	*/
	public boolean isLooping(){
		return looping;
	}

	/**
	* Tells whether BioDriver should loop the simulation after end conditions have been met.
	* @param pLoop Whether BioDriver should loop the simulation after end conditions have been met.
	*/
	public synchronized void setLooping(boolean pLoop){
		looping = pLoop;
	}

	/**
	* Tells whether the simulation has met an end condition and has stopped.
	* @return Whether the simulation has met an end condition and has stopped. 
	*/
	public boolean isDone(){
		if (runTillN){
			if (ticksGoneBy >= nTicks){
				System.out.println("BioDriverImpl:"+myID+"Too many ticks?");
				return true;
			}
		}
		else if (runTillDead){
			for (int i = 0; i < crews.length; i++){
				if (!crews[i].isDead()){
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	* Tells the number of times BioDriver has ticked the simulation
	* @return The number of times BioDriver has ticked the simulation
	*/
	public int getTicks(){
		return ticksGoneBy;
	}

	/**
	* Ends the simulation entirely. 
	*/
	public synchronized void endSimulation(){
		myTickThread = null;
		notify();
		simulationStarted = false;
		myLogger.endLog();
		System.out.println("BioDriverImpl"+myID+": simulation ended");
	}

	/**
	* Check whether simulation has begun.
	* @return <code>true</code> if simulation has started, <code>false</code> if not
	*/
	public boolean simulationHasStarted(){
		return simulationStarted;
	}

	/**
	* Advances the simulation once tick.
	* NOTICE: not pausing the simulation before using this method can be very risky.  Don't do it.
	*/
	public synchronized void advanceOneTick(){
		if (!simulationIsPaused)
			setPauseSimulation(true);
		tick();
	}

	/**
	* Iterates through the modules setting their logs
	* @param pLogSim Whether or not modules should log.
	*/
	public synchronized void setFullLogging(boolean pLogSim){
		for (int i = 0; i < modules.length; i++){
			BioModule currentBioModule = (BioModule)(modules[i]);
			currentBioModule.setLogging(pLogSim);
		}
		if (myLogger != null)
			myLogger.setProcessingLogs(pLogSim);
	}

	/**
	* Iterates through the sensors setting their logs
	* @param pLogSim Whether or not sensors should log.
	*/
	public synchronized void setSensorLogging(boolean pLogSim){
		for (int i = 0; i < sensors.length; i++){
			BioModule currentBioModule = (BioModule)(sensors[i]);
			currentBioModule.setLogging(pLogSim);
		}
		if (myLogger != null)
			myLogger.setProcessingLogs(pLogSim);
	}

	/**
	* Iterates through the actuators setting their logs
	* @param pLogSim Whether or not actuators should log.
	*/
	public synchronized void setActuatorLogging(boolean pLogSim){
		for (int i = 0; i < actuators.length; i++){
			BioModule currentBioModule = (BioModule)(actuators[i]);
			currentBioModule.setLogging(pLogSim);
		}
		if (myLogger != null)
			myLogger.setProcessingLogs(pLogSim);
	}

	/**
	* Tells the amount of stochastic intensity the modules will undergo.
	* @param pValue The amount of stochastic intensity the modules will undergo.
	* Options are:
	* <code>StochasticIntensity.HIGH_STOCH</code>
	* <code>StochasticIntensity.MEDIUM_STOCH</code>
	* <code>StochasticIntensity.LOW_STOCH</code>
	* <code>StochasticIntensity.NONE_STOCH</code>
	*/
	public void setStochasticIntensity(StochasticIntensity pValue){
		for (int i = 0; i < modules.length; i++){
			BioModule currentBioModule = (BioModule)(modules[i]);
			currentBioModule.setStochasticIntensity(pValue);
		}
	}

	/**
	* Starts a malfunction on every module
	* @param pIntensity The intensity of the malfunction<br>
	* Options are:<br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionIntensity.SEVERE_MALF</code><br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionIntensity.MEDIUM_MALF</code><br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionIntensity.LOW_MALF</code><br>
	* @param pLength The length (time-wise) of the malfunction<br>
	* Options are:<br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionLength.TEMPORARY_MALF</code><br>
	* &nbsp;&nbsp;&nbsp;<code>MalfunctionLength.PERMANENT_MALF</code><br>
	*/
	public void startMalfunction(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		for (int i = 0; i < modules.length; i++){
			BioModule currentBioModule = (BioModule)(modules[i]);
			currentBioModule.startMalfunction(pIntensity, pLength);
		}
	}

	/**
	* Resets the simulation by calling every known server's reset method.
	* Typically this means resetting the various gas levels, crew people, water levels, etc.
	*/
	public void reset(){
		System.out.println("BioDriverImpl:"+myID+" Resetting simulation");
		for (int i = 0; i < modules.length; i++){
			BioModule currentBioModule = (BioModule)(modules[i]);
			currentBioModule.reset();
		}
	}

	/**
	* Ticks every server.  The SimEnvironment is ticked first as it keeps track of time for the rest of the server.
	* The other server are ticked in no particular order by enumerating through the module hashtable.
	* When every server has been ticked, BioDriverImpl notifies all it's listeners that this has happened.
	*/
	private void tick(){
		//Iterate through the rest of the modules and tick them
		for (int i = 0; i < modules.length; i++){
			BioModule currentBioModule = (BioModule)(modules[i]);
			currentBioModule.tick();
		}
		ticksGoneBy++;
	}

	private class Ticker implements Runnable{
		/**
		* Invoked by the myTickThread.start() method call and necessary to implement Runnable.
		* Sets flag that simulation is running, intializes servers (if applicable), then begins ticking them.
		*/
		public void run(){
			System.out.println("BioDriverImpl:"+myID+" Running simulation...");
			simulationStarted = true;
			runSimulation();
		}
		/**
		* The ticking simulation loop.  Uses a variety of semaphores to pause/resume/end without causing deadlock.
		* Essentially runs till told to pause or die.
		*/
		private void runSimulation(){
			Thread theCurrentThread = Thread.currentThread();
			if (!simulationStarted)
				reset();
			while (myTickThread == theCurrentThread) {
				try {
					myTickThread.sleep(driverPauseLength);
					synchronized(this) {
						while (simulationIsPaused && (myTickThread==theCurrentThread)){
							wait();
						}
					}
				}
				catch (InterruptedException e){}
				tick();
				if (isDone()){
					endSimulation();
					if (looping)
						startSimulation();
				}
			}
		}
	}
}

