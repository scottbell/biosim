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
/*
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
	private boolean usedDefaultModules = true;
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
	private String[] myModuleNames;

	/**
	* Constructs the BioDriver
	* @param pID The ID of this instance of the BioSim (must be the same for all modules in the instance)
	*/
	public BioDriverImpl(int pID){
		myID = pID;
		myModuleNames = new String[18];
		myModuleNames[0] = crewName = "CrewGroup"+myID;
		myModuleNames[1] = powerPSName = "PowerPS"+myID;
		myModuleNames[2] = powerStoreName = "PowerStore"+myID;
		myModuleNames[3] = airRSName = "AirRS"+myID;
		myModuleNames[4] = CO2StoreName = "CO2Store"+myID;
		myModuleNames[5] = O2StoreName = "O2Store"+myID;
		myModuleNames[6] = biomassRSName = "BiomassRS"+myID;
		myModuleNames[7] = biomassStoreName = "BiomassStore"+myID;
		myModuleNames[8] = foodProcessorName = "FoodProcessor"+myID;
		myModuleNames[9] = foodStoreName = "FoodStore"+myID;
		myModuleNames[10] = waterRSName = "WaterRS"+myID;
		myModuleNames[11] = dirtyWaterStoreName = "DirtyWaterStore"+myID;
		myModuleNames[12] = potableWaterStoreName = "PotableWaterStore"+myID;
		myModuleNames[13] = greyWaterStoreName = "GreyWaterStore"+myID;
		myModuleNames[14] = crewEnvironmentName = "CrewEnvironment"+myID;
		myModuleNames[15] = plantEnvironmentName = "PlantEnvironment"+myID;
		myModuleNames[16] = accumulatorName = "Accumulator"+myID;
		myModuleNames[17] = injectorName = "Injector"+myID;
		usedDefaultModules = true;
		checkMachineType();
	}

	/**
	* Constructs the BioDriver
	* @param pModuleNames The module names this BioDriver should tick.
	* @param pID The ID of this instance of the BioSim (must be the same for all modules in the instance)
	*/
	public BioDriverImpl(String[] pModuleNames, int pID){
		myID = pID;
		myModuleNames = pModuleNames;
		loggerName = "Logger"+myID;
		usedDefaultModules = false;
		initializationToUse = BioDriverInit.NO_INIT;
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
		if (!usedDefaultModules){
			System.err.println("BioDriverImpl:"+myID+" Attempting to change initialization after not using default modules, not allowing!");
			return;
		}
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
		CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(crewName));
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
		DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(dirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(potableWaterStoreName));
		GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(greyWaterStoreName));
		myDirtyWaterStore.setCapacity(10000f);
		myDirtyWaterStore.setLevel(0f);
		myPotableWaterStore.setCapacity(10000f);
		myPotableWaterStore.setLevel(10000f);
		myGreyWaterStore.setCapacity(10000f);
		myGreyWaterStore.setLevel(10000f);

		//Fill the air tanks
		CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(CO2StoreName));
		O2Store myO2Store = O2StoreHelper.narrow(getBioModule(O2StoreName));
		myCO2Store.setCapacity(1000f);
		myO2Store.setCapacity(1000f);
		myCO2Store.setLevel(500f);
		myO2Store.setLevel(250f);

		//Put some air in the crew cabin
		SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(crewEnvironmentName));
		double environmentCapacity = 1.54893 * Math.pow(10, 6);
		//for dave, make bigger
		environmentCapacity *= 10;
		Double environmentCapacityObj = new Double(environmentCapacity);
		myCrewEnvironment.setCapacity(environmentCapacityObj.floatValue());

		//Put some air in the plant cabin
		SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(plantEnvironmentName));
		myPlantEnvironment.setCapacity(environmentCapacityObj.floatValue());

		//Add some crops and food
		BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(biomassStoreName));
		FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(foodStoreName));
		BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(biomassRSName));
		myBiomassStore.setCapacity(500f);
		myFoodStore.setCapacity(2000f);
		myBiomassStore.setLevel(300f);
		myFoodStore.setLevel(2000f);

		//Add some power
		PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(powerStoreName));
		myPowerStore.setCapacity(10000f);
		myPowerStore.setLevel(10000f);

		configureFlows();
	}

	/**
	* Configures the simulation.  By default, 2 environments are used along with one module of everything else.
	*/
	private void configureFlows(){
		BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(biomassStoreName));
		PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(powerStoreName));
		FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(foodStoreName));
		PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(potableWaterStoreName));
		DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(dirtyWaterStoreName));
		GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(greyWaterStoreName));
		SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(crewEnvironmentName));
		SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(plantEnvironmentName));
		O2Store myO2Store = O2StoreHelper.narrow(getBioModule(O2StoreName));
		CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(CO2StoreName));

		//Hook up Food Processor to other modules
		{
			BiomassStore[] biomassStoreInput = {myBiomassStore};
			PowerStore[] powerStoreInput = {myPowerStore};
			FoodStore[] foodStoreOutput = {myFoodStore};
			float[] biomassMaxFlowRates = {10000f};
			float[] powerMaxFlowRates = {10000f};
			float[] foodMaxFlowRates = {10000f};
			float[] biomassActualFlowRates = {10000f};
			float[] powerActualFlowRates = {10000f};
			float[] foodActualFlowRates = {10000f};
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(getBioModule(foodProcessorName));
			myFoodProcessor.setBiomassInputs(biomassStoreInput, biomassMaxFlowRates, biomassActualFlowRates);
			myFoodProcessor.setPowerInputs(powerStoreInput, powerMaxFlowRates, powerActualFlowRates);
			myFoodProcessor.setFoodOutputs(foodStoreOutput, foodMaxFlowRates, foodActualFlowRates);
		}

		//Hook up Biomass RS to other modules
		{
			PowerStore[] powerStoreInput = {myPowerStore};
			PotableWaterStore[] potableWaterStoreInput = {myPotableWaterStore};
			GreyWaterStore[] greyWaterStoreInput = {myGreyWaterStore};
			BiomassStore[] biomassStoreOutput = {myBiomassStore};
			SimEnvironment[] simEnvironmentInput = {myPlantEnvironment};
			SimEnvironment[] simEnvironmentOutput = {myPlantEnvironment};
			float[] powerMaxFlowRates = {10000f};
			float[] potableWaterMaxFlowRates = {10000f};
			float[] greyWaterMaxFlowRates = {10000f};
			float[] biomassMaxFlowRates = {10000f};
			float[] simEnvironmentInputMaxFlowRates = {10000f};
			float[] simEnvironmentOutputMaxFlowRates = {10000f};
			float[] powerActualFlowRates = {10000f};
			float[] potableWaterActualFlowRates = {10000f};
			float[] greyWaterActualFlowRates = {10000f};
			float[] biomassActualFlowRates = {10000f};
			float[] simEnvironmentInputActualFlowRates = {10000f};
			float[] simEnvironmentOutputActualFlowRates = {10000f};
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(biomassRSName));
			myBiomassRS.setPowerInputs(powerStoreInput, powerMaxFlowRates, powerActualFlowRates);
			myBiomassRS.setPotableWaterInputs(potableWaterStoreInput, potableWaterMaxFlowRates, potableWaterActualFlowRates);
			myBiomassRS.setGreyWaterInputs(greyWaterStoreInput, greyWaterMaxFlowRates, greyWaterActualFlowRates);
			myBiomassRS.setBiomassOutputs(biomassStoreOutput, biomassMaxFlowRates, biomassActualFlowRates);
			myBiomassRS.setAirInputs(simEnvironmentInput, simEnvironmentInputMaxFlowRates, simEnvironmentInputActualFlowRates);
			myBiomassRS.setAirOutputs(simEnvironmentOutput, simEnvironmentOutputMaxFlowRates, simEnvironmentOutputActualFlowRates);
		}

		//Hook up Air RS to other modules
		{
			PowerStore[] powerStoreInput = {myPowerStore};
			SimEnvironment[] simEnvironmentInput = {myPlantEnvironment};
			SimEnvironment[] simEnvironmentOutput = {myCrewEnvironment};
			O2Store[] O2StoreOutput = {myO2Store};
			CO2Store[] CO2StoreOutput = {myCO2Store};
			CO2Store[] CO2StoreInput = {myCO2Store};
			float[] powerMaxFlowRates = {10000f};
			float[] simEnvironmentInputMaxFlowRates = {10000f};
			float[] simEnvironmentOutputMaxFlowRates = {10000f};
			float[] O2StoreMaxFlowRates = {10000f};
			float[] CO2StoreInputMaxFlowRates = {10000f};
			float[] CO2StoreOutputMaxFlowRates = {10000f};
			float[] powerActualFlowRates = {10000f};
			float[] simEnvironmentInputActualFlowRates = {10000f};
			float[] simEnvironmentOutputActualFlowRates = {10000f};
			float[] O2StoreActualFlowRates = {10000f};
			float[] CO2StoreInputActualFlowRates = {10000f};
			float[] CO2StoreOutputActualFlowRates = {10000f};
			AirRS myAirRS = AirRSHelper.narrow(getBioModule(airRSName));
			myAirRS.setPowerInputs(powerStoreInput, powerMaxFlowRates, powerActualFlowRates);
			myAirRS.setAirInputs(simEnvironmentInput, simEnvironmentInputMaxFlowRates, simEnvironmentInputActualFlowRates);
			myAirRS.setAirOutputs(simEnvironmentOutput, simEnvironmentOutputMaxFlowRates, simEnvironmentOutputActualFlowRates);
			myAirRS.setO2Outputs(O2StoreOutput, O2StoreMaxFlowRates, O2StoreActualFlowRates);
			myAirRS.setCO2Outputs(CO2StoreOutput, CO2StoreOutputMaxFlowRates, CO2StoreInputActualFlowRates);
			myAirRS.setCO2Inputs(CO2StoreInput, CO2StoreInputMaxFlowRates, CO2StoreOutputActualFlowRates);
		}

		//Hook up Crew to other modules
		{
			SimEnvironment[] airInputs = {myCrewEnvironment};
			SimEnvironment[] airOutputs = {myCrewEnvironment};
			PotableWaterStore[] potableWaterInput = {myPotableWaterStore};
			FoodStore[] foodInputs = {myFoodStore};
			GreyWaterStore[] greyWaterOutputs = {myGreyWaterStore};
			DirtyWaterStore[] dirtyWaterOutputs = {myDirtyWaterStore};
			float[] airInputMaxFlowRates = {10000f};
			float[] airOutputMaxFlowRates = {10000f};
			float[] foodInputMaxFlowRates = {10000f};
			float[] potableWaterInputMaxFlowRates = {10000f};
			float[] dirtyWaterOutputMaxFlowRates = {10000f};
			float[] greyWaterOutputMaxFlowRates = {10000f};
			float[] airInputActualFlowRates = {10000f};
			float[] airOutputActualFlowRates = {10000f};
			float[] foodInputActualFlowRates = {10000f};
			float[] potableWaterInputActualFlowRates = {10000f};
			float[] dirtyWaterOutputActualFlowRates = {10000f};
			float[] greyWaterOutputActualFlowRates = {10000f};
			CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(crewName));
			myCrew.setAirInputs(airInputs, airInputMaxFlowRates, airInputActualFlowRates);
			myCrew.setAirOutputs(airOutputs, airOutputMaxFlowRates, airOutputActualFlowRates);
			myCrew.setFoodInputs(foodInputs, foodInputMaxFlowRates, foodInputActualFlowRates);
			myCrew.setPotableWaterInputs(potableWaterInput, potableWaterInputMaxFlowRates, potableWaterInputActualFlowRates);
			myCrew.setDirtyWaterOutputs(dirtyWaterOutputs, dirtyWaterOutputMaxFlowRates, dirtyWaterOutputActualFlowRates);
			myCrew.setGreyWaterOutputs(greyWaterOutputs, greyWaterOutputMaxFlowRates, greyWaterOutputActualFlowRates);
		}

		//Hook up Water to other modules
		{
			PotableWaterStore[] potableWaterOutput = {myPotableWaterStore};
			GreyWaterStore[] greyWaterInputs = {myGreyWaterStore};
			DirtyWaterStore[] dirtyWaterInputs = {myDirtyWaterStore};
			PowerStore[] powerInput = {myPowerStore};
			float[] potableWaterOutputMaxFlowRates = {10000f};
			float[] greyWaterInputMaxFlowRates = {10000f};
			float[] dirtyWaterInputMaxFlowRates = {10000f};
			float[] powerInputMaxFlowRates = {10000f};
			float[] potableWaterOutputActualFlowRates = {10000f};
			float[] greyWaterInputActualFlowRates = {10000f};
			float[] dirtyWaterInputActualFlowRates = {10000f};
			float[] powerInputActualFlowRates = {10000f};
			WaterRS myWaterRS = WaterRSHelper.narrow(getBioModule(waterRSName));
			myWaterRS.setPotableWaterOutputs(potableWaterOutput, potableWaterOutputMaxFlowRates, potableWaterOutputActualFlowRates);
			myWaterRS.setGreyWaterInputs(greyWaterInputs, greyWaterInputMaxFlowRates, greyWaterInputActualFlowRates);
			myWaterRS.setDirtyWaterInputs(dirtyWaterInputs, dirtyWaterInputMaxFlowRates, dirtyWaterInputActualFlowRates);
			myWaterRS.setPowerInputs(powerInput, powerInputMaxFlowRates, powerInputActualFlowRates);
		}

		//Hook up Power to other modules
		{
			PowerStore[] powerOutput = {myPowerStore};
			SimEnvironment lightInput = myCrewEnvironment;
			float[] powerOuputsMaxFlowRates = {10000f};
			float[] powerOuputsActualFlowRates = {10000f};
			PowerPS myPowerPS = PowerPSHelper.narrow(getBioModule(powerPSName));
			myPowerPS.setPowerOutputs(powerOutput, powerOuputsMaxFlowRates, powerOuputsActualFlowRates);
			myPowerPS.setLightInput(lightInput);
		}

		//Hook up Accumulator to other modules
		{
			//Accumulate O2 from plant environment, put it in O2 store
			SimEnvironment[] O2AirInput = {myPlantEnvironment};
			O2Store[] O2AirOutput = {myO2Store};
			float[] O2AirInputMaxFlowRates = {500f};
			float[] O2AirOutputMaxFlowRates = {500f};
			float[] O2AirInputActualFlowRates = {500f};
			float[] O2AirOutputActualFlowRates = {500f};
			Accumulator myAccumulator = AccumulatorHelper.narrow(getBioModule(accumulatorName));
			myAccumulator.setO2AirEnvironmentInputs(O2AirInput, O2AirInputMaxFlowRates, O2AirInputActualFlowRates);
			myAccumulator.setO2AirStoreOutputs(O2AirOutput, O2AirOutputMaxFlowRates, O2AirOutputActualFlowRates);

			//Accumulate CO2 from crew environment, put it in CO2 store
			SimEnvironment[] CO2AirInput = {myCrewEnvironment};
			CO2Store[] CO2AirOutput = {myCO2Store};
			float[] CO2AirInputMaxFlowRates = {500f};
			float[] CO2AirOutputMaxFlowRates = {500f};
			float[] CO2AirInputActualFlowRates = {500f};
			float[] CO2AirOutputActualFlowRates = {500f};
			myAccumulator.setCO2AirEnvironmentInputs(CO2AirInput, CO2AirInputMaxFlowRates, CO2AirInputActualFlowRates);
			myAccumulator.setCO2AirStoreOutputs(CO2AirOutput, CO2AirOutputMaxFlowRates, CO2AirOutputActualFlowRates);
		}

		//Hook up Injector to other modules
		{
			//Take O2 from store, inject it into crew environment
			O2Store[] O2AirInput = {myO2Store};
			SimEnvironment[] O2AirOutput = {myCrewEnvironment};
			float[] O2AirInputMaxFlowRates = {500f};
			float[] O2AirOutputMaxFlowRates = {500f};
			float[] O2AirInputActualFlowRates = {500f};
			float[] O2AirOutputActualFlowRates = {500f};
			Injector myInjector = InjectorHelper.narrow(getBioModule(injectorName));
			myInjector.setO2AirStoreInputs(O2AirInput, O2AirInputMaxFlowRates, O2AirInputActualFlowRates);
			myInjector.setO2AirEnvironmentOutputs(O2AirOutput, O2AirOutputMaxFlowRates, O2AirOutputActualFlowRates);

			//Take CO2 from store, inject it into plant environment
			CO2Store[] CO2AirInput = {myCO2Store};
			SimEnvironment[] CO2AirOutput = {myPlantEnvironment};
			float[] CO2AirInputMaxFlowRates = {500f};
			float[] CO2AirOutputMaxFlowRates = {500f};
			float[] CO2AirInputActualFlowRates = {500f};
			float[] CO2AirOutputActualFlowRates = {500f};
			myInjector.setCO2AirStoreInputs(CO2AirInput, CO2AirInputMaxFlowRates, CO2AirInputActualFlowRates);
			myInjector.setCO2AirEnvironmentOutputs(CO2AirOutput, CO2AirOutputMaxFlowRates, CO2AirOutputActualFlowRates);
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
		CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(crewName));
		if (!createdCrew){
			CrewPerson myCrewPerson1 = myCrew.createCrewPerson("Alice Optimal", 50, 80, Sex.female);
			createdCrew = true;
		}

		//Fill the clean water stores to the brim (20 liters), and all stores' capacities
		DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(dirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(potableWaterStoreName));
		GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(greyWaterStoreName));
		myDirtyWaterStore.setCapacity(100f);
		myDirtyWaterStore.setLevel(100f);
		myPotableWaterStore.setCapacity(100f);
		myPotableWaterStore.setLevel(100f);
		myGreyWaterStore.setCapacity(100f);
		myGreyWaterStore.setLevel(100f);

		//Fill the air tanks
		CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(CO2StoreName));
		O2Store myO2Store = O2StoreHelper.narrow(getBioModule(O2StoreName));
		myCO2Store.setCapacity(100f);
		myO2Store.setCapacity(100f);
		myCO2Store.setLevel(100f);
		myO2Store.setLevel(100f);

		//Put some air in the cabin
		SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(crewEnvironmentName));
		myCrewEnvironment.setCapacity(10000000f);
		SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(plantEnvironmentName));
		myPlantEnvironment.setCapacity(10000000f);

		//Add some crops and food
		BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(biomassStoreName));
		FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(foodStoreName));
		myBiomassStore.setCapacity(100f);
		myFoodStore.setCapacity(100f);
		myBiomassStore.setLevel(100f);
		myFoodStore.setLevel(100f);

		//Add some power
		PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(powerStoreName));
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
			CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(crewName));
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
		for (int i =0; i < myModuleNames.length; i++){
			try{
				BioModule currentModule = BioModuleHelper.narrow(OrbUtils.getNCRef().resolve_str(myModuleNames[i]));
				modules.put(myModuleNames[i] , currentModule);
			}
			catch (Exception e){
				System.err.println("BioDriverImpl:"+myID+" Couldn't locate "+myModuleNames[i]+", skipping...");
			}
		}
		try{
			myLogger = LoggerHelper.narrow(OrbUtils.getNCRef().resolve_str(loggerName));
		}
		catch (Exception e){
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

