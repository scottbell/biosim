package biosim.server.framework;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.*;


/**
 * BioDriverImpl exists as the driver for the simulation.  It gathers references to all the various servers, initializes them, then ticks them.<br>
 * This is all done multithreaded through the use of the spawnSimulation methods.<br>
 * BioDriverImpl also can notify listeners when it has sucessfully ticked all the servers.  The listener needs only to implement BioDriverImplListener and<br>
 * register with BioDriverImpl.  Note that any configuration of the simulation other than the one supplied will need to use <code>BioDriverInit.NONE_INIT</code>
 *
 * @author    Scott Bell
 */

public class BioDriverImpl extends BioDriverPOA implements Runnable
{
	//Module Names, only used for initialization
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
	private String crewEnvironmentName;
	private String plantEnvironmentName;
	private String accumulatorName;
	private String injectorName;
	private String loggerName;
	//A hastable containing the server references
	private Map modules;
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
	//Tells whether simulation runs until crew death
	private boolean runTillDead = false;
	//Tells whether simulation runs till a fixed number of ticks
	private boolean runTillN = false;
	//If <runTillN == true, this is the number of ticks to run for.
	private int nTicks = 0;
	//The number of ticks gone by
	private int ticksGoneBy = 0;
	//Tells whether all the modules known are logging or not (only checked when logging turned on/off)
	private boolean logging = false;
	//The logger module
	private Logger myLogger;
	//Tells whether BioDriver has collected references to modules it needs from the nameserver (used for initializations)
	private boolean hasCollectedReferences = false;
	//How long BioDriver should pause between ticks
	private int driverPauseLength = 0;
	//The ID of this instance of BioSim
	private int myID = 0;
	//If the initialization has already created the crew or not.
	private boolean createdCrew = false;
	//If we loop after end conditions of a simulation run have been met (crew death or n-ticks)
	private boolean looping = false;
	
	/**
	* Checks to see if the simulation is paused.
	* @param pID The ID of this instance of the BioSim (must be the same for all modules in the instance)
	*/
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
		crewEnvironmentName = "CrewEnvironment"+myID;
		plantEnvironmentName = "PlantEnvironment"+myID;
		loggerName = "Logger"+myID;
		accumulatorName = "Accumulator"+myID;
		injectorName = "Injector"+myID;
		checkMachineType();
	}
	
	/**
	* Returns the name of this instance of BioDriver
	* @return The name of this instance (BioDriver + ID)
	*/
	public String getName(){
		return "BioDriver"+myID;
	}
	
	/**
	* Attempts to discover the machine type we're running on.  If it's windows, set a driver pause between ticks
	* to keep from starving windows GUI.  Checked by looking at the Java System Property "MACHINE_TYPE"
	*/
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
		else{
			setDriverPauseLength(5);
		}
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
	* Run a simulation on a different thread and runs continuously (ticks till signalled to end)
	*/
	public void spawnSimulation(){
		runTillDead = false;
		runTillN = false;
		collectReferences();
		myTickThread = new Thread(this);
		myTickThread.start();
	}
	
	/**
	* Run a simulation on a different thread and runs continously till the crew dies
	*/
	public void spawnSimulationTillDead(){
		runTillDead = true;
		collectReferences();
		myTickThread = new Thread(this);
		myTickThread.start();
	}
	
	/**
	* Run a simulation on a different thread and runs continously till n-Ticks
	* @param pTicks The number of ticks to run the simulation
	*/
	public void spawnSimulationTillN(int pTicks){
		nTicks = pTicks;
		runTillN = true;
		runTillDead = false;
		collectReferences();
		myTickThread = new Thread(this);
		myTickThread.start();
	}
	
	/**
	* If n-ticks have been reached or the crew is dead, this method restarts the simulation
	*/
	private void loopSimulation(){
		collectReferences();
		myTickThread = new Thread(this);
		myTickThread.start();
	}
	
	/**
	* Tells BioDriver which initialization to use
	* @param pInitializationToUse the initialization to use:<br>
	* &nbsp;&nbsp;&nbsp;<code>BioDriverInit.DEFAULT_INIT</code> - default initialization (sets resource flows, initializes buffers, adds crew, etc)<br>
	* &nbsp;&nbsp;&nbsp;<code>BioDriverInit.OPTIMAL_INIT</code> - optimal initialization, same as default, but with bigger buffers, smaller crew, etc<br>
	* &nbsp;&nbsp;&nbsp;<code>BioDriverInit.NONE_INIT</code> - no initialization.  everything must be set up manually. use this if you've reconfigured the simulation
	*/
	public void setInitialization(BioDriverInit pInitializationToUse){
		initializationToUse = pInitializationToUse;
	}

	/**
	* Invoked by the myTickThread.start() method call and necessary to implement Runnable.
	* Sets flag that simulation is running, intializes servers (if applicable), then begins ticking them.
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
		else if (initializationToUse == BioDriverInit.FLOWS_ONLY_INIT){
			System.out.println("BioDriverImpl:"+myID+" Initializing flows only simulation...");
			configureFlows();
		}
		System.out.println("BioDriverImpl:"+myID+" Running simulation...");
		runSimulation();
	}

	/**
	* Initializes the various servers with various dummy data.
	* Crew is created, stores are filled, etc.
	*/
	private void defaultInitialization(){
		//reset servers
		reset();
		//Make some crew members
		CrewGroup myCrew = (CrewGroup)(getBioModule(crewName));
		myCrew.setStochasticIntensity(StochasticIntensity.MEDIUM_STOCH);
		if (!createdCrew){
			CrewPerson myCrewPerson1 = myCrew.createCrewPerson("Bob Roberts", 43, 170, Sex.male);
			CrewPerson myCrewPerson2 = myCrew.createCrewPerson("Stephanie Stevens", 25, 125, Sex.female);
			CrewPerson myCrewPerson3 = myCrew.createCrewPerson("Bill Williams", 30, 165, Sex.male);
			CrewPerson myCrewPerson4 = myCrew.createCrewPerson("Janet Janey", 22, 130, Sex.female);
			createdCrew = true;
		}
		//stagger actvities
		CrewPerson[] myCrewPeople = myCrew.getCrewPeople();
		for (int i = 0; i < myCrewPeople.length; i++)
			myCrewPeople[i].setCurrentActivity(myCrewPeople[i].getScheduledActivityByOrder(i));
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

		//Put some air in the crew cabin
		SimEnvironment myCrewEnvironment = (SimEnvironment)(getBioModule(crewEnvironmentName));
		double environmentCapacity = 1.54893 * Math.pow(10, 6);
		//for dave, make bigger
		environmentCapacity *= 10;
		Double environmentCapacityObj = new Double(environmentCapacity);
		myCrewEnvironment.setCapacity(environmentCapacityObj.floatValue());

		//Put some air in the plant cabin
		SimEnvironment myPlantEnvironment = (SimEnvironment)(getBioModule(plantEnvironmentName));
		myPlantEnvironment.setCapacity(environmentCapacityObj.floatValue());

		//Add some crops and food
		BiomassStore myBiomassStore = (BiomassStore)(getBioModule(biomassStoreName));
		FoodStore myFoodStore = (FoodStore)(getBioModule(foodStoreName));
		BiomassRS myBiomassRS = (BiomassRS)(getBioModule(biomassRSName));
		myBiomassStore.setCapacity(500f);
		myFoodStore.setCapacity(2000f);
		myBiomassStore.setLevel(300f);
		myFoodStore.setLevel(2000f);

		//Add some power
		PowerStore myPowerStore = (PowerStore)(getBioModule(powerStoreName));
		myPowerStore.setCapacity(10000f);
		myPowerStore.setLevel(10000f);

		configureFlows();
	}
	
	/**
	* Configures the simulation.  By default, 2 environments are used along with one module of everything else.
	*/
	private void configureFlows(){
		BiomassStore myBiomassStore = (BiomassStore)(getBioModule(biomassStoreName));
		PowerStore myPowerStore = (PowerStore)(getBioModule(powerStoreName));
		FoodStore myFoodStore = (FoodStore)(getBioModule(foodStoreName));
		PotableWaterStore myPotableWaterStore = (PotableWaterStore)(getBioModule(potableWaterStoreName));
		DirtyWaterStore myDirtyWaterStore = (DirtyWaterStore)(getBioModule(dirtyWaterStoreName));
		GreyWaterStore myGreyWaterStore = (GreyWaterStore)(getBioModule(greyWaterStoreName));
		SimEnvironment myCrewEnvironment = (SimEnvironment)(getBioModule(crewEnvironmentName));
		SimEnvironment myPlantEnvironment = (SimEnvironment)(getBioModule(plantEnvironmentName));
		O2Store myO2Store = (O2Store)(getBioModule(O2StoreName));
		CO2Store myCO2Store = (CO2Store)(getBioModule(CO2StoreName));
		
		//Hook up Food Processor to other modules
		{
			BiomassStore[] biomassStoreInput = {myBiomassStore};
			PowerStore[] powerStoreInput = {myPowerStore};
			FoodStore[] foodStoreOutput = {myFoodStore};
			float[] biomassFlowRates = {10000f};
			float[] powerFlowRates = {10000f};
			float[] foodFlowRates = {10000f};
			FoodProcessor myFoodProcessor = (FoodProcessor)(getBioModule(foodProcessorName));
			myFoodProcessor.setBiomassInputs(biomassStoreInput, biomassFlowRates);
			myFoodProcessor.setPowerInputs(powerStoreInput, powerFlowRates);
			myFoodProcessor.setFoodOutputs(foodStoreOutput, foodFlowRates);
		}

		//Hook up Biomass RS to other modules
		{
			PowerStore[] powerStoreInput = {myPowerStore};
			PotableWaterStore[] potableWaterStoreInput = {myPotableWaterStore};
			GreyWaterStore[] greyWaterStoreInput = {myGreyWaterStore};
			BiomassStore[] biomassStoreOutput = {myBiomassStore};
			SimEnvironment[] simEnvironmentInput = {myPlantEnvironment};
			SimEnvironment[] simEnvironmentOutput = {myPlantEnvironment};
			float[] powerFlowRates = {10000f};
			float[] potableWaterFlowRates = {10000f};
			float[] greyWaterFlowRates = {10000f};
			float[] biomassFlowRates = {10000f};
			float[] simEnvironmentInputFlowRates = {10000f};
			float[] simEnvironmentOutputFlowRates = {10000f};
			BiomassRS myBiomassRS = (BiomassRS)(getBioModule(biomassRSName));
			myBiomassRS.setPowerInputs(powerStoreInput, powerFlowRates);
			myBiomassRS.setPotableWaterInputs(potableWaterStoreInput, potableWaterFlowRates);
			myBiomassRS.setGreyWaterInputs(greyWaterStoreInput, greyWaterFlowRates);
			myBiomassRS.setBiomassOutputs(biomassStoreOutput, biomassFlowRates);
			myBiomassRS.setAirInputs(simEnvironmentInput, simEnvironmentInputFlowRates);
			myBiomassRS.setAirOutputs(simEnvironmentOutput, simEnvironmentOutputFlowRates);
		}

		//Hook up Air RS to other modules
		{
			PowerStore[] powerStoreInput = {myPowerStore};
			SimEnvironment[] simEnvironmentInput = {myPlantEnvironment};
			SimEnvironment[] simEnvironmentOutput = {myCrewEnvironment};
			O2Store[] O2StoreOutput = {myO2Store};
			CO2Store[] CO2StoreOutput = {myCO2Store};
			CO2Store[] CO2StoreInput = {myCO2Store};
			float[] powerFlowRates = {10000f};
			float[] simEnvironmentInputFlowRates = {10000f};
			float[] simEnvironmentOutputFlowRates = {10000f};
			float[] O2StoreFlowRates = {10000f};
			float[] CO2StoreInputFlowRates = {10000f};
			float[] CO2StoreOutputFlowRates = {10000f};
			AirRS myAirRS = (AirRS)(getBioModule(airRSName));
			myAirRS.setPowerInputs(powerStoreInput, powerFlowRates);
			myAirRS.setAirInputs(simEnvironmentInput, simEnvironmentInputFlowRates);
			myAirRS.setAirOutputs(simEnvironmentOutput, simEnvironmentOutputFlowRates);
			myAirRS.setO2Outputs(O2StoreOutput, O2StoreFlowRates);
			myAirRS.setCO2Outputs(CO2StoreOutput, CO2StoreOutputFlowRates);
			myAirRS.setCO2Inputs(CO2StoreInput, CO2StoreInputFlowRates);
		}

		//Hook up Crew to other modules
		{
			SimEnvironment[] airInputs = {myCrewEnvironment};
			SimEnvironment[] airOutputs = {myCrewEnvironment};
			PotableWaterStore[] potableWaterInput = {myPotableWaterStore};
			FoodStore[] foodInputs = {myFoodStore};
			GreyWaterStore[] greyWaterOutputs = {myGreyWaterStore};
			DirtyWaterStore[] dirtyWaterOutputs = {myDirtyWaterStore};
			float[] airInputFlowRates = {10000f};
			float[] airOutputFlowRates = {10000f};
			float[] foodInputFlowRates = {10000f};
			float[] potableWaterInputFlowRates = {10000f};
			float[] dirtyWaterOutputFlowRates = {10000f};
			float[] greyWaterOutputFlowRates = {10000f};
			CrewGroup myCrew = (CrewGroup)(getBioModule(crewName));
			myCrew.setAirInputs(airInputs, airInputFlowRates);
			myCrew.setAirOutputs(airOutputs, airOutputFlowRates);
			myCrew.setFoodInputs(foodInputs, foodInputFlowRates);
			myCrew.setPotableWaterInputs(potableWaterInput, potableWaterInputFlowRates);
			myCrew.setDirtyWaterOutputs(dirtyWaterOutputs, dirtyWaterOutputFlowRates);
			myCrew.setGreyWaterOutputs(greyWaterOutputs, greyWaterOutputFlowRates);
		}

		//Hook up Water to other modules
		{
			PotableWaterStore[] potableWaterOutput = {myPotableWaterStore};
			GreyWaterStore[] greyWaterInputs = {myGreyWaterStore};
			DirtyWaterStore[] dirtyWaterInputs = {myDirtyWaterStore};
			PowerStore[] powerInput = {myPowerStore};
			float[] potableWaterOutputFlowRates = {10000f};
			float[] greyWaterInputFlowRates = {10000f};
			float[] dirtyWaterInputFlowRates = {10000f};
			float[] powerInputFlowRates = {10000f};
			WaterRS myWaterRS = (WaterRS)(getBioModule(waterRSName));
			myWaterRS.setPotableWaterOutputs(potableWaterOutput, potableWaterOutputFlowRates);
			myWaterRS.setGreyWaterInputs(greyWaterInputs, greyWaterInputFlowRates);
			myWaterRS.setDirtyWaterInputs(dirtyWaterInputs, dirtyWaterInputFlowRates);
			myWaterRS.setPowerInputs(powerInput, powerInputFlowRates);
		}

		//Hook up Power to other modules
		{
			PowerStore[] powerOutput = {myPowerStore};
			SimEnvironment lightInput = myCrewEnvironment;
			float[] powerOuputsFlowRates = {10000f};
			PowerPS myPowerPS = (PowerPS)(getBioModule(powerPSName));
			myPowerPS.setPowerOutputs(powerOutput, powerOuputsFlowRates);
			myPowerPS.setLightInput(lightInput);
		}

		//Hook up Accumulator to other modules
		{
			//Accumulate O2 from plant environment, put it in O2 store
			SimEnvironment[] O2AirInput = {myPlantEnvironment};
			O2Store[] O2AirOutput = {myO2Store};
			float[] O2AirInputFlowRates = {20f};
			float[] O2AirOutputFlowRates = {20f};
			Accumulator myAccumulator = (Accumulator)(getBioModule(accumulatorName));
			myAccumulator.setO2AirEnvironmentInputs(O2AirInput, O2AirInputFlowRates);
			myAccumulator.setO2AirStoreOutputs(O2AirOutput, O2AirOutputFlowRates);
			
			//Accumulate CO2 from crew environment, put it in CO2 store
			SimEnvironment[] CO2AirInput = {myCrewEnvironment};
			CO2Store[] CO2AirOutput = {myCO2Store};
			float[] CO2AirInputFlowRates = {100f};
			float[] CO2AirOutputFlowRates = {100f};
			myAccumulator.setCO2AirEnvironmentInputs(CO2AirInput, CO2AirInputFlowRates);
			myAccumulator.setCO2AirStoreOutputs(CO2AirOutput, CO2AirOutputFlowRates);
		}

		//Hook up Injector to other modules
		{
			//Take O2 from store, inject it into crew environment
			O2Store[] O2AirInput = {myO2Store};
			SimEnvironment[] O2AirOutput = {myCrewEnvironment};
			float[] O2AirInputFlowRates = {100f};
			float[] O2AirOutputFlowRates = {100f};
			Injector myInjector = (Injector)(getBioModule(injectorName));
			myInjector.setO2AirStoreInputs(O2AirInput, O2AirInputFlowRates);
			myInjector.setO2AirEnvironmentOutputs(O2AirOutput, O2AirOutputFlowRates);
			
			//Take CO2 from store, inject it into plant environment
			CO2Store[] CO2AirInput = {myCO2Store};
			SimEnvironment[] CO2AirOutput = {myPlantEnvironment};
			float[] CO2AirInputFlowRates = {100f};
			float[] CO2AirOutputFlowRates = {100f};
			myInjector.setCO2AirStoreInputs(CO2AirInput, CO2AirInputFlowRates);
			myInjector.setCO2AirEnvironmentOutputs(CO2AirOutput, CO2AirOutputFlowRates);
		}
	}

	/**
	* Initializes the various servers with various optimal data.
	* One crew member created, stores filled high, etc.
	*/
	private void optimalInitialization(){
		//reset servers
		reset();

		//Make some crew members
		CrewGroup myCrew = (CrewGroup)(getBioModule(crewName));
		if (!createdCrew){
			CrewPerson myCrewPerson1 = myCrew.createCrewPerson("Alice Optimal", 50, 80, Sex.female);
			createdCrew = true;
		}

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
		SimEnvironment myCrewEnvironment = (SimEnvironment)(getBioModule(crewEnvironmentName));
		myCrewEnvironment.setCapacity(10000000f);
		SimEnvironment myPlantEnvironment = (SimEnvironment)(getBioModule(plantEnvironmentName));
		myPlantEnvironment.setCapacity(10000000f);

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
		configureFlows();
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
					loopSimulation();
			}
		}
	}
	
	/**
	* Sets how long BioDriver should pause between full simulation ticks (e.g., tick all modules, wait, tick all modules, wait, etc.)
	* @param pDriverPauseLength the length (in milliseconds) for the driver to pause between ticks
	*/
	public void setDriverPauseLength(int pDriverPauseLength){
		if (pDriverPauseLength > 0)
			System.out.println("BioDriverImpl:"+myID+" driver pause of "+pDriverPauseLength+" milliseconds");
		driverPauseLength = pDriverPauseLength;
	}
	
	/**
	* Tells how long the simulation pauses between full simulation ticks.
	* @return How long the simulation pauses between full simulation ticks.
	*/
	public int getDriverPauseLength(){
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
	public void setLooping(boolean pLoop){
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
			CrewGroup myCrew = (CrewGroup)(getBioModule(crewName));
			if (myCrew.isDead()){
				System.out.println("BioDriverImpl"+myID+": Crew's dead @"+ticksGoneBy+" ticks");
				return true;
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
	
	/**
	* Iterates through the modules setting their logs
	* @param pLogSim Whether or not modules should log.
	*/
	public synchronized void setLogging(boolean pLogSim){
		collectReferences();
		logging = pLogSim;
		if (logging){
			System.out.println("BioDriverImpl:"+myID+" Enabling logging");
		}
		else{
			System.out.println("BioDriverImpl:"+myID+" Disabling logging");
		}
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.setLogging(pLogSim);
		}
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
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
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
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.startMalfunction(pIntensity, pLength);
		}
	}
	
	/**
	* Tells whether the modules are logging or not
	* @return Whether the modules are logging or not
	*/
	public boolean isLogging(){
		return logging;
	}

	/**
	* Tries to collect references to all the servers and adds them to a hashtable than can be accessed by outside classes.
	*/
	private void collectReferences(){
		if (hasCollectedReferences)
			return;
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
			SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(crewEnvironmentName));
			modules.put(crewEnvironmentName , myCrewEnvironment);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+crewEnvironmentName+", ending!");
			System.exit(0);
		}
		try{
			SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(plantEnvironmentName));
			modules.put(plantEnvironmentName , myPlantEnvironment);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+plantEnvironmentName+", ending!");
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
		try{
			Accumulator myAccumulator = AccumulatorHelper.narrow(OrbUtils.getNCRef().resolve_str(accumulatorName));
			modules.put(accumulatorName , myAccumulator);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+accumulatorName+", skipping...");
		}
		try{
			Injector myInjector = InjectorHelper.narrow(OrbUtils.getNCRef().resolve_str(injectorName));
			modules.put(injectorName , myInjector);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+injectorName+", skipping...");
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
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
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
		for (Iterator iter = modules.values().iterator(); iter.hasNext();){
			BioModule currentBioModule = (BioModule)(iter.next());
			currentBioModule.tick();
		}
		ticksGoneBy++;
	}

}

