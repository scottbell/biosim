package biosim.server.framework;

import biosim.idl.air.*;
import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.idl.food.*;
import biosim.idl.power.*;
import biosim.idl.water.*;
import biosim.idl.framework.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;


/**
 * BioDriverImpl exists as the driver for the simulation.  It gathers references to all the various servers, initializes them, then ticks them.
 * This is all done multithreaded through the use of the spawnSimulation method.
 * BioDriverImpl also can notify listeners when it has sucessfully ticked all the servers.  The listener needs only to implement BioDriverImplListener and
 * register with BioDriverImpl.
 *
 * @author    Scott Bell
 */

public class BioDriverImpl extends BioDriverPOA implements Runnable
{
	//Module Names
	private String crewName;
	private String powerPSName;
	private String powerStoreName;
	private String airRSName;
	private String CO2StoreName;
	private String O2StoreName;
	private String biomassRSName;
	private String biomassStoreName;
	private String foodProcessorName;
	private String foodStoreName;
	private String waterRSName;
	private String dirtyWaterStoreName;
	private String potableWaterStoreName;
	private String greyWaterStoreName;
	private String simEnvironmentName;
	private String loggerName;
	//A hastable containing the server references
	private Hashtable modules;
	//A reference to the naming service
	private NamingContextExt ncRef;
	//The thread to run the simulation
	private Thread myTickThread;
	//Flag to see whether the BioDriverImpl is paused (started but not ticking)
	private boolean simulationIsPaused = false;
	//Flag to see whether the BioDriverImpl is started at all
	private boolean simulationStarted = false;
	//Flag to see if user wants to use default intialization (i.e., fill tanks with x amount gas, generate crew memebers, etc)
	private BioDriverInit initializationToUse = BioDriverInit.DEFAULT_INIT;
	private boolean runTillDead = false;
	private boolean runTillN = false;
	private int nTicks = 0;
	private int ticksGoneBy = 0;
	private boolean logging = false;
	private SimEnvironment mySimEnvironment;
	private Logger myLogger;
	private boolean hasCollectedReferences = false;
	private int driverPauseLength = 0;
	private int myID = 0;

	public BioDriverImpl(int pID){
		myID = pID;
		crewName = "CrewGroup"+myID;
		powerPSName = "PowerPS"+myID;
		powerStoreName = "PowerStore"+myID;
		airRSName = "AirRS"+myID;
		CO2StoreName = "CO2Store"+myID;
		O2StoreName = "O2Store"+myID;
		biomassRSName = "BiomassRS"+myID;
		biomassStoreName = "BiomassStore"+myID;
		foodProcessorName = "FoodProcessor"+myID;
		foodStoreName = "FoodStore"+myID;
		waterRSName = "WaterRS"+myID;
		dirtyWaterStoreName = "DirtyWaterStore"+myID;
		potableWaterStoreName = "PotableWaterStore"+myID;
		greyWaterStoreName = "GreyWaterStore"+myID;
		simEnvironmentName = "SimEnvironment"+myID;
		loggerName = "Logger"+myID;
		checkMachineType();
	}

	public String getName(){
		return "BioDriver"+myID;
	}

	private void checkMachineType(){
		String machineType = null;
		machineType = System.getProperty("MACHINE_TYPE");
		if (machineType != null){
			if (machineType.indexOf("CYGWIN") != -1){
				setDriverPauseLength(5);
			}
			else{
				setDriverPauseLength(0);
			}
		}
		else
			setDriverPauseLength(5);
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
	* Run a simulation on a different thread and runs continously (ticks till signalled to end)
	* param pUseDefaultInitialization set to <code>true</code> if user wants to use default intialization 
	* (i.e., fill tanks with x amount gas, generate crew memebers, etc)
	*/
	public void spawnSimulation(){
		runTillDead = false;
		runTillN = false;
		myTickThread = new Thread(this);
		myTickThread.start();
	}

	public void spawnSimulationTillDead(){
		runTillDead = true;
		collectReferences();
		myTickThread = new Thread(this);
		myTickThread.start();
	}

	public void spawnSimulationTillN(int pTicks){
		nTicks = pTicks;
		runTillN = true;
		runTillDead = false;
		collectReferences();
		myTickThread = new Thread(this);
		myTickThread.start();
	}
	
	public void setInitialization(BioDriverInit pInitializationToUse){
		initializationToUse = pInitializationToUse;
	}

	/**
	* Invoked by the myTickThread.start() method call and necessary to implement Runnable.
	* Sets flag that simulation is running, intializes servers, then begins ticking them.
	*/
	public void run(){
		simulationStarted = true;
		ticksGoneBy = 0;
		if (initializationToUse == BioDriverInit.DEFAULT_INIT){
			System.out.println("BioDriverImpl:"+myID+" Initializing default simulation...");
			defaultInitialization();
		}
		else if (initializationToUse == BioDriverInit.OPTIMAL_INIT){
			System.out.println("BioDriverImpl:"+myID+" Initializing optimal simulation...");
			optimalInitialization();
		}
		System.out.println("BioDriverImpl:"+myID+" Running simulation...");
		runSimulation();
	}

	/**
	* Initializes the various servers with various dummy data
	*/
	private void defaultInitialization(){
		//reset servers
		reset();
		//Make some crew members
		CrewGroup myCrew = (CrewGroup)(getBioModule(crewName));
		myCrew.setStochasticIntensity(StochasticIntensity.MEDIUM_STOCH);
		CrewPerson myCrewPerson1 = myCrew.createCrewPerson("Bob Roberts", 43, 170, Sex.male);
		CrewPerson myCrewPerson2 = myCrew.createCrewPerson("Stephanie Stevens", 25, 125, Sex.female);
		CrewPerson myCrewPerson3 = myCrew.createCrewPerson("Bill Williams", 30, 165, Sex.male);
		CrewPerson myCrewPerson4 = myCrew.createCrewPerson("Janet Janey", 22, 130, Sex.female);
		//CrewPerson myCrewPerson5 = myCrew.createCrewPerson("Sarah Sanchez", 44, 135, Sex.female);
		//CrewPerson myCrewPerson6 = myCrew.createCrewPerson("Marvin Metsky", 33, 145, Sex.male);

		//stagger actvities
		myCrewPerson1.setCurrentActivity(myCrew.getScheduledActivityByOrder(0));
		myCrewPerson2.setCurrentActivity(myCrew.getScheduledActivityByOrder(1));
		myCrewPerson3.setCurrentActivity(myCrew.getScheduledActivityByOrder(2));
		myCrewPerson4.setCurrentActivity(myCrew.getScheduledActivityByOrder(3));
		//myCrewPerson5.setCurrentActivity(myCrew.getScheduledActivityByOrder(4));
		//myCrewPerson6.setCurrentActivity(myCrew.getScheduledActivityByOrder(5));

		//Fill the clean water stores to the brim (20 liters), and all stores' capacities
		DirtyWaterStore myDirtyWaterStore = (DirtyWaterStore)(getBioModule(dirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = (PotableWaterStore)(getBioModule(potableWaterStoreName));
		GreyWaterStore myGreyWaterStore = (GreyWaterStore)(getBioModule(greyWaterStoreName));
		myDirtyWaterStore.setCapacity(10000f);
		myDirtyWaterStore.setLevel(0f);
		myPotableWaterStore.setCapacity(10000f);
		myPotableWaterStore.setLevel(10000f);
		myGreyWaterStore.setCapacity(10000f);
		myGreyWaterStore.setLevel(10000f);

		//Fill the air tanks
		CO2Store myCO2Store = (CO2Store)(getBioModule(CO2StoreName));
		O2Store myO2Store = (O2Store)(getBioModule(O2StoreName));
		myCO2Store.setCapacity(1000f);
		myO2Store.setCapacity(1000f);
		myCO2Store.setLevel(500f);
		myO2Store.setLevel(250f);

		//Put some air in the cabin
		SimEnvironment mySimEnvironment = (SimEnvironment)(getBioModule(simEnvironmentName));
		double environmentCapacity = 1.54893 * Math.pow(10, 6);
		//for dave, make bigger
		environmentCapacity *= 10;
		Double environmentCapacityObj = new Double(environmentCapacity);
		mySimEnvironment.setCapacity(environmentCapacityObj.floatValue());

		//Add some crops and food
		BiomassStore myBiomassStore = (BiomassStore)(getBioModule(biomassStoreName));
		FoodStore myFoodStore = (FoodStore)(getBioModule(foodStoreName));
		BiomassRS myBiomassRS = (BiomassRS)(getBioModule(biomassRSName));
		myBiomassRS.setStochasticIntensity(StochasticIntensity.MEDIUM_STOCH);
		myBiomassStore.setCapacity(100f);
		myFoodStore.setCapacity(500f);
		myBiomassStore.setLevel(0f);
		myFoodStore.setLevel(500f);

		//Add some power
		PowerStore myPowerStore = (PowerStore)(getBioModule(powerStoreName));
		myPowerStore.setCapacity(7500f);
		myPowerStore.setLevel(0f);
	}
	
	/**
	* Initializes the various servers with various optimal data
	*/
	private void optimalInitialization(){
		//reset servers
		reset();

		//Make some crew members
		CrewGroup myCrew = (CrewGroup)(getBioModule(crewName));
		CrewPerson myCrewPerson1 = myCrew.createCrewPerson("Alice Optimal", 50, 80, Sex.female);

		//Fill the clean water stores to the brim (20 liters), and all stores' capacities
		DirtyWaterStore myDirtyWaterStore = (DirtyWaterStore)(getBioModule(dirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = (PotableWaterStore)(getBioModule(potableWaterStoreName));
		GreyWaterStore myGreyWaterStore = (GreyWaterStore)(getBioModule(greyWaterStoreName));
		myDirtyWaterStore.setCapacity(100f);
		myDirtyWaterStore.setLevel(100f);
		myPotableWaterStore.setCapacity(100f);
		myPotableWaterStore.setLevel(100f);
		myGreyWaterStore.setCapacity(100f);
		myGreyWaterStore.setLevel(100f);

		//Fill the air tanks
		CO2Store myCO2Store = (CO2Store)(getBioModule(CO2StoreName));
		O2Store myO2Store = (O2Store)(getBioModule(O2StoreName));
		myCO2Store.setCapacity(100f);
		myO2Store.setCapacity(100f);
		myCO2Store.setLevel(100f);
		myO2Store.setLevel(100f);

		//Put some air in the cabin
		SimEnvironment mySimEnvironment = (SimEnvironment)(getBioModule(simEnvironmentName));
		mySimEnvironment.setCapacity(10000000f);

		//Add some crops and food
		BiomassStore myBiomassStore = (BiomassStore)(getBioModule(biomassStoreName));
		FoodStore myFoodStore = (FoodStore)(getBioModule(foodStoreName));
		myBiomassStore.setCapacity(100f);
		myFoodStore.setCapacity(100f);
		myBiomassStore.setLevel(100f);
		myFoodStore.setLevel(100f);

		//Add some power
		PowerStore myPowerStore = (PowerStore)(getBioModule(powerStoreName));
		myPowerStore.setCapacity(100f);
		myPowerStore.setLevel(100f);
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
			if (isDone())
				endSimulation();
		}
	}

	public void setDriverPauseLength(int pDriverPauseLength){
		if (pDriverPauseLength > 0)
			System.out.println("BioDriverImpl:"+myID+" driver pause of "+pDriverPauseLength+" milliseconds");
		driverPauseLength = pDriverPauseLength;
	}

	public int getDriverPauseLength(){
		return driverPauseLength;
	}
	
	public boolean isDone(){
		if (runTillN){
			if (ticksGoneBy >= nTicks){
				System.out.println("BioDriverImpl:"+myID+"Too many ticks?");
				return true;
			}
		}
		else if (runTillDead){
			CrewGroup myCrew = (CrewGroup)(getBioModule(crewName));
			if (myCrew.isDead()){
				System.out.println("BioDriverImpl"+myID+": Crew's dead @"+ticksGoneBy+" ticks");
				return true;
			}
		}
		return false;
	}
	
	public int getTicks(){
		return ticksGoneBy;
	}

	/**
	* Fetches a BioModule (e.g. AirRS, FoodProcessor, PotableWaterStore) that has been collected by the BioDriverImpl
	* @return the BioModule requested, null if not found
	*/
	private BioModule getBioModule(String type){
		return (BioModule)(modules.get(type));
	}

	/**
	* Pauses the simulation.  Does nothing if already paused.
	*/
	public synchronized void pauseSimulation(){
		simulationIsPaused = true;
		System.out.println("BioDriverImpl:"+myID+" simulation paused");
	}

	/**
	* Ends the simulation entirely. 
	*/
	public synchronized void endSimulation(){
		myTickThread = null;
		notify();
		simulationStarted = false;
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
		collectReferences();
		System.out.println("BioDriverImpl:"+myID+" ticking simulation once");
		tick();
	}

	/**
	* Resumes the simulation.  Does nothing if already running.
	*/
	public synchronized void resumeSimulation(){
		simulationIsPaused = false;
		notify();
		System.out.println("BioDriverImpl:"+myID+" simulation resumed");
	}

	public synchronized void setLogging(boolean pLogSim){
		collectReferences();
		logging = pLogSim;
		if (logging){
			System.out.println("BioDriverImpl:"+myID+" Enabling logging");
		}
		else{
			System.out.println("BioDriverImpl:"+myID+" Disabling logging");
		}
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			currentBioModule.setLogging(pLogSim);
		}
		myLogger.setProcessingLogs(pLogSim);
	}
	
	public void setStochasticIntensity(StochasticIntensity pValue){
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			currentBioModule.setStochasticIntensity(pValue);
		}
	}
	
	public  void startMalfunction(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			currentBioModule.startMalfunction(pIntensity, pLength);
		}
	}

	public boolean isLogging(){
		return logging;
	}

	/**
	* Tries to collect references to all the servers and adds them to a hashtable than can be accessed by outside classes.
	*/
	private void collectReferences(){
		if (hasCollectedReferences)
			return;
		System.out.println("BioDriverImpl:"+myID+" Getting server references...");
		// resolve the Objects Reference in Naming
		modules = new Hashtable();
		System.out.println("BioDriverImpl:"+myID+" Collecting references to modules...");
		try{
			CrewGroup myCrew = CrewGroupHelper.narrow(OrbUtils.getNCRef().resolve_str(crewName));
			modules.put(crewName , myCrew);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+crewName+", skipping...");
		}
		try{
			PowerPS myPowerPS = PowerPSHelper.narrow(OrbUtils.getNCRef().resolve_str(powerPSName));
			modules.put(powerPSName , myPowerPS);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+powerPSName+", skipping...");
		}
		try{
			PowerStore myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(powerStoreName));
			modules.put(powerStoreName , myPowerStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+powerStoreName+", skipping...");
		}
		try{
			AirRS myAirRS = AirRSHelper.narrow(OrbUtils.getNCRef().resolve_str(airRSName));
			modules.put(airRSName , myAirRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+airRSName+", skipping...");
		}
		try{
			mySimEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(simEnvironmentName));
			modules.put(simEnvironmentName , mySimEnvironment);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+simEnvironmentName+", ending!");
			System.exit(0);
		}
		try{
			GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(greyWaterStoreName));
			modules.put(greyWaterStoreName , myGreyWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+greyWaterStoreName+", skipping...");
		}
		try{
			PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(potableWaterStoreName));
			modules.put(potableWaterStoreName , myPotableWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+potableWaterStoreName+", skipping...");
		}
		try{
			DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(dirtyWaterStoreName));
			modules.put(dirtyWaterStoreName , myDirtyWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+dirtyWaterStoreName+", skipping...");
		}
		try{
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(OrbUtils.getNCRef().resolve_str(foodProcessorName));
			modules.put(foodProcessorName , myFoodProcessor);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+foodProcessorName+", skipping...");
		}
		try{
			FoodStore myFoodStore= FoodStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(foodStoreName));
			modules.put(foodStoreName , myFoodStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+foodStoreName+", skipping...");
		}
		try{
			CO2Store myCO2Store = CO2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(CO2StoreName));
			modules.put(CO2StoreName , myCO2Store);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+CO2StoreName+", skipping...");
		}
		try{
			O2Store myO2Store = O2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(O2StoreName));
			modules.put(O2StoreName , myO2Store);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+O2StoreName+", skipping...");
		}
		try{
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassRSName));
			modules.put(biomassRSName , myBiomassRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+biomassRSName+", skipping...");
		}
		try{
			BiomassStore myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassStoreName));
			modules.put(biomassStoreName, myBiomassStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+biomassStoreName+", skipping...");
		}
		try{
			WaterRS myWaterRS = WaterRSHelper.narrow(OrbUtils.getNCRef().resolve_str(waterRSName));
			modules.put(waterRSName , myWaterRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+waterRSName+", skipping...");
		}
		try{
			myLogger = LoggerHelper.narrow(OrbUtils.getNCRef().resolve_str(loggerName));
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+loggerName+", skipping...");
		}
		hasCollectedReferences = true;
	}

	/**
	* Resets the simulation by calling every known server's reset method.
	* Typically this means resetting the various gas levels, crew people, water levels, etc.
	*/
	public void reset(){
		collectReferences();
		System.out.println("BioDriverImpl:"+myID+" Resetting simulation");
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			currentBioModule.reset();
		}
	}

	/**
	* Ticks every server.  The SimEnvironment is ticked first as it keeps track of time for the rest of the server.
	* The other server are ticked in no particular order by enumerating through the module hashtable.
	* When every server has been ticked, BioDriverImpl notifies all it's listeners that this has happened.
	*/
	private void tick(){
		//first tick SimEnvironment
		mySimEnvironment.tick();
		//Iterate through the rest of the modules and tick them
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			if (!(currentBioModule.getModuleName().equals(simEnvironmentName))){
				currentBioModule.tick();
			}
		}
		ticksGoneBy++;
	}

}

