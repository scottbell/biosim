package biosim.server.framework;

import org.apache.log4j.Logger;
import biosim.idl.framework.BioDriverPOA;
import biosim.idl.framework.BioModule;
import biosim.idl.framework.MalfunctionIntensity;
import biosim.idl.framework.MalfunctionLength;
import biosim.idl.framework.StochasticIntensity;
import biosim.idl.simulation.crew.CrewGroup;
import biosim.idl.simulation.food.BiomassRS;
import biosim.idl.simulation.food.Shelf;

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
	private boolean runTillCrewDeath = false;
	//Tells whether simulation runs until plant death
	private boolean runTillPlantDeath = false;
	//Tells whether simulation runs till a fixed number of ticks
	//If <runTillN == true, this is the number of ticks to run for.
	private boolean runTillN = false;
	//How long BioDriver should pause between ticks
	private int driverStutterLength = 0;
	//The ID of this instance of BioSim
	private int myID = 0;
	private Logger myLogger;
	//If we loop after end conditions of a simulation run have been met (crew death or n-ticks)
	private boolean looping = false;
	private CrewGroup[] crewsToWatch;
	private BiomassRS[] plantsToWatch;
	private BioModule[] modules;
	private BioModule[] activeSimModules;
	private BioModule[] passiveSimModules;
	private BioModule[] prioritySimModules;
	private BioModule[] sensors;
	private BioModule[] actuators;

	/**
	* Constructs the BioDriver
	* @param pID The ID of this instance of the BioSim (must be the same for all modules in the instance)
	*/
	public BioDriverImpl(int pID){
		myID = pID;
		modules = new BioModule[0];
		activeSimModules = new BioModule[0];
		passiveSimModules = new BioModule[0];
		prioritySimModules = new BioModule[0];
		sensors = new BioModule[0];
		actuators = new BioModule[0];
		crewsToWatch = new CrewGroup[0];
		plantsToWatch = new BiomassRS[0];
		myLogger = Logger.getLogger(this.getClass());
	}

	/**
	* Starts the simulation
	*/
	public void startSimulation(){
		reset();
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
	public synchronized void setRunTillCrewDeath(boolean pRunTillDead){
		runTillCrewDeath = pRunTillDead;
	}
	
	/**
	* Simulation runs till all the crew dies if true.
	*/
	public synchronized void setRunTillPlantDeath(boolean pRunTillDead){
		runTillPlantDeath = pRunTillDead;
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

	public void setActiveSimModules(BioModule[] pSimModules){
		activeSimModules = pSimModules;
	}
	
	public void setPassiveSimModules(BioModule[] pSimModules){
		passiveSimModules = pSimModules;
	}
	
	public void setPrioritySimModules(BioModule[] pSimModules){
		prioritySimModules = pSimModules;
	}
	
	public BioModule[] getModules(){
		return modules;
	}

	public BioModule[] getSensors(){
		return sensors;
	}

	public BioModule[] getActiveSimModules(){
		return activeSimModules;
	}
	
	public BioModule[] getPassiveSimModules(){
		return passiveSimModules;
	}
	
	public BioModule[] getPrioritySimModules(){
		return prioritySimModules;
	}

	public BioModule[] getActuators(){
		return actuators;
	}

	public String[] getModuleNames(){
		String[] moduleNameArray = new String[modules.length];
		for (int i = 0; i < moduleNameArray.length; i++)
			moduleNameArray[i] = modules[i].getModuleName();
		return moduleNameArray;
	}

	public String[] getSensorNames(){
		String[] sensorNameArray = new String[sensors.length];
		for (int i = 0; i < sensorNameArray.length; i++)
			sensorNameArray[i] = sensors[i].getModuleName();
		return sensorNameArray;
	}

	public String[] getActuatorNames(){
		String[] actuatorNameArray = new String[actuators.length];
		for (int i = 0; i < actuatorNameArray.length; i++)
			actuatorNameArray[i] = actuators[i].getModuleName();
		return actuatorNameArray;
	}

	public String[] getActiveSimModuleNames(){
		String[] simModuleNameArray = new String[activeSimModules.length];
		for (int i = 0; i < simModuleNameArray.length; i++)
			simModuleNameArray[i] = activeSimModules[i].getModuleName();
		return simModuleNameArray;
	}
	
	public String[] getPassiveSimModuleNames(){
		String[] simModuleNameArray = new String[passiveSimModules.length];
		for (int i = 0; i < simModuleNameArray.length; i++)
			simModuleNameArray[i] = passiveSimModules[i].getModuleName();
		return simModuleNameArray;
	}
	
	public String[] getPrioritySimModuleNames(){
		String[] simModuleNameArray = new String[prioritySimModules.length];
		for (int i = 0; i < simModuleNameArray.length; i++)
			simModuleNameArray[i] = prioritySimModules[i].getModuleName();
		return simModuleNameArray;
	}

	public void setCrewsToWatch(CrewGroup[] pCrewGroups){
		crewsToWatch = pCrewGroups;
	}

	public void setPlantsToWatch(BiomassRS[] pPlants){
		plantsToWatch = pPlants;
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

	/**
	* Sets how long BioDriver should pause between full simulation ticks (e.g., tick all modules, wait, tick all modules, wait, etc.)
	* @param pDriverStutterLength the length (in milliseconds) for the driver to pause between ticks
	*/
	public synchronized void setDriverStutterLength(int pDriverStutterLength){
		if (pDriverStutterLength > 0)
			myLogger.info("BioDriverImpl"+myID+": driver pause of "+pDriverStutterLength+" milliseconds");
		driverStutterLength = pDriverStutterLength;
	}

	/**
	* Tells how long the simulation pauses between full simulation ticks.
	* @return How long the simulation pauses between full simulation ticks.
	*/
	public int getDriverStutterLength(){
		return driverStutterLength;
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
				myLogger.info("BioDriverImpl"+myID+": Reached user defined tick limit of "+nTicks);
				//System.exit(0);
				return true;
			}
		}
		if (runTillCrewDeath){
			for (int i = 0; i < crewsToWatch.length; i++){
				if (crewsToWatch[i].isDead()){
					myLogger.debug("BioDriverImpl"+myID+": simulation ended due to crew death at "+nTicks);
					return true;
				}
			}
		}
		if(runTillPlantDeath){
			for (int i = 0; i < plantsToWatch.length; i++){
				Shelf[] shelves = plantsToWatch[i].getShelves();
				for (int j = 0; j < shelves.length; j++){
					if (shelves[j].isDead()){
						myLogger.debug("BioDriverImpl"+myID+": simulation ended due to plant death at "+nTicks);
						return true;
					}
				}
			}
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
		myLogger.info("BioDriverImpl"+myID+": simulation ended on tick "+ticksGoneBy);
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
		myLogger.info("BioDriverImpl"+myID+": Resetting simulation");
		ticksGoneBy = 0;
		for (int i = 0; i < modules.length; i++){
			BioModule currentBioModule = (BioModule)(modules[i]);
			currentBioModule.reset();
		}
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
				Thread.sleep(driverStutterLength);
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

	/**
	* Ticks every server.  The SimEnvironment is ticked first as it keeps track of time for the rest of the server.
	* The other server are ticked in no particular order by enumerating through the module hashtable.
	* When every server has been ticked, BioDriverImpl notifies all it's listeners that this has happened.
	*/
	private void tick(){
		if (!isStarted()){
			myLogger.debug("BioDriverImpl"+myID+": Tick called when simulation wasn't started!");
			return;
		}
		myLogger.debug("BioDriveImpl: begin Tick");
		//Iterate through the actuators and tick them
		for (int i = 0; i < actuators.length; i++){
			BioModule currentBioModule = (BioModule)(actuators[i]);
			currentBioModule.tick();
		}
		//Iterate through the active sim modules and tick them
		for (int i = 0; i < activeSimModules.length; i++){
			BioModule currentBioModule = (BioModule)(activeSimModules[i]);
			currentBioModule.tick();
		}
		//Iterate through the passive sim modules and tick them
		for (int i = 0; i < passiveSimModules.length; i++){
			BioModule currentBioModule = (BioModule)(passiveSimModules[i]);
			currentBioModule.tick();
		}
		//Iterate through the priority sim modules and tick them
		for (int i = 0; i < prioritySimModules.length; i++){
			BioModule currentBioModule = (BioModule)(prioritySimModules[i]);
			currentBioModule.tick();
		}
		//Iterate through the passive sim modules and tick them
		for (int i = 0; i < passiveSimModules.length; i++){
			BioModule currentBioModule = (BioModule)(passiveSimModules[i]);
			currentBioModule.tick();
		}
		//Iterate through the sensors and tick them
		for (int i = 0; i < sensors.length; i++){
			BioModule currentBioModule = (BioModule)(sensors[i]);
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
			myLogger.info("BioDriverImpl"+myID+": Running simulation...");
			simulationStarted = true;
			runSimulation();
		}
	}
}

