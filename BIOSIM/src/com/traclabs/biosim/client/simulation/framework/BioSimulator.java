package biosim.client.framework;

import biosim.idl.air.*;
import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.idl.food.*;
import biosim.idl.power.*;
import biosim.idl.water.*;
import biosim.idl.framework.*;
import biosim.client.framework.gui.*;
import biosim.client.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.*;

public class BioSimulator implements Runnable
{
	//Module Names
	public final static  String crewName = "CrewGroup";
	public final static  String powerPSName = "PowerPS";
	public final static  String powerStoreName = "PowerStore";
	public final static  String airRSName = "AirRS";
	public final static  String CO2StoreName = "CO2Store";
	public final static  String O2StoreName = "O2Store";
	public final static  String biomassRSName = "BiomassRS";
	public final static  String biomassStoreName = "BiomassStore";
	public final static  String foodProcessorName = "FoodProcessor";
	public final static  String foodStoreName = "FoodStore";
	public final static  String waterRSName = "WaterRS";
	public final static  String dirtyWaterStoreName = "DirtyWaterStore";
	public final static  String potableWaterStoreName = "PotableWaterStore";
	public final static  String greyWaterStoreName = "GreyWaterStore";
	public final static  String simEnvironmentName = "SimEnvironment";
	public final static  String bioModuleHarnessName = "BioModuleHarness";

	private Hashtable modules;
	private NamingContextExt ncRef;
	private Thread myThread;
	private boolean simulationIsPaused = false;
	private boolean simulationStarted = false;
	private Vector listeners;

	public BioSimulator() {
	        listeners = new Vector();
		System.out.println("BioSimulator: Getting server references...");
		collectReferences();
	}

	public void spawnSimulation(){
		myThread = new Thread(this);
		myThread.start();
	}

	public void run(){
		simulationStarted = true;
		System.out.println("BioSimulator: Initializing simulation...");
		initializeSimulation();
		System.out.println("BioSimulator: Running simulation...");
		runSimulation();
	}

	private void initializeSimulation(){
		//reset servers
		reset();
		
		//Make some crew members
		CrewGroup myCrew = (CrewGroup)(getBioModule(crewName));
		CrewPerson myCrewPerson1 = myCrew.createCrewPerson("Bob Roberts", 43, 170, Sex.male);
		CrewPerson myCrewPerson2 = myCrew.createCrewPerson("Stephanie Stevens", 25, 125, Sex.female);
		CrewPerson myCrewPerson3 = myCrew.createCrewPerson("Bill Williams", 30, 165, Sex.male);
		CrewPerson myCrewPerson4 = myCrew.createCrewPerson("Janet Janey", 22, 130, Sex.female);
		
		//stagger actvities
		myCrewPerson1.setCurrentActivity(myCrew.getScheduledActivityByOrder(0));
		myCrewPerson2.setCurrentActivity(myCrew.getScheduledActivityByOrder(1));
		myCrewPerson3.setCurrentActivity(myCrew.getScheduledActivityByOrder(2));
		myCrewPerson4.setCurrentActivity(myCrew.getScheduledActivityByOrder(3));

		//Fill the clean water stores to the brim (20 liters), and all stores' capacities
		DirtyWaterStore myDirtyWaterStore = (DirtyWaterStore)(getBioModule(dirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = (PotableWaterStore)(getBioModule(potableWaterStoreName));
		GreyWaterStore myGreyWaterStore = (GreyWaterStore)(getBioModule(greyWaterStoreName));
		myDirtyWaterStore.setCapacity(2000f);
		myDirtyWaterStore.setLevel(0f);
		myPotableWaterStore.setCapacity(200f);
		myPotableWaterStore.setLevel(200f);
		myGreyWaterStore.setCapacity(2000f);
		myGreyWaterStore.setLevel(0f);

		//Fill the air tanks
		CO2Store myCO2Store = (CO2Store)(getBioModule(CO2StoreName));
		O2Store myO2Store = (O2Store)(getBioModule(O2StoreName));
		myCO2Store.setCapacity(1000f);
		myO2Store.setCapacity(1000f);
		myCO2Store.setLevel(0f);
		myO2Store.setLevel(0f);

		//Put some air in the cabin
		SimEnvironment mySimEnvironment = (SimEnvironment)(getBioModule(simEnvironmentName));
		Double environmentCapacity = new Double(1.54893 * Math.pow(10, 6));
		mySimEnvironment.setCapacity(environmentCapacity.floatValue());

		//Add some crops and food
		BiomassStore myBiomassStore = (BiomassStore)(getBioModule(biomassStoreName));
		FoodStore myFoodStore = (FoodStore)(getBioModule(foodStoreName));
		myBiomassStore.setCapacity(100f);
		myFoodStore.setCapacity(500f);
		myBiomassStore.setLevel(0f);
		myFoodStore.setLevel(500f);

		//Add some power
		PowerStore myPowerStore = (PowerStore)(getBioModule(powerStoreName));
		myPowerStore.setCapacity(30000f);
		myPowerStore.setLevel(30000f);
	}

	private void runSimulation(){
		if (!simulationStarted)
			reset();
		for (int i = 0; true; i ++){
			Thread theCurrentThread = Thread.currentThread();
			while (myThread == theCurrentThread) {
				tick();
				if (simulationIsPaused && (myThread==theCurrentThread)){
					try {
						synchronized(this) {
							while (simulationIsPaused && (myThread==theCurrentThread)){
								wait();
							}
						}
					}
					catch (InterruptedException e){}
				}
			}
		}
	}

	public BioModule getBioModule(String type){
		return (BioModule)(modules.get(type));
	}

	public synchronized void pauseSimulation(){
		simulationIsPaused = true;
		System.out.println("BioSimulator: simulation paused");
	}

	public synchronized void endSimulation(){
		myThread = null;
		notify();
		simulationStarted = false;
		System.out.println("BioSimulator: simulation ended");
	}

	public boolean simulationHasStarted(){
		return simulationStarted;
	}

	//Pause simulation before you use this function
	public synchronized void advanceOneTick(){
		System.out.println("BioSimulator: ticking simulation once");
		tick();
	}

	public synchronized void resumeSimulation(){
		simulationIsPaused = false;
		notify();
		System.out.println("BioSimulator: simulation resumed");
	}

	public synchronized void registerListener(BioSimulatorListener newListener){
		listeners.add(newListener);
	}

	private void collectReferences(){
		// resolve the Objects Reference in Naming
		modules = new Hashtable();
		System.out.println("BioSimulator: Collecting references to modules...");
		try{
			CrewGroup myCrew = CrewGroupHelper.narrow(OrbUtils.getNCRef().resolve_str(crewName));
			modules.put(crewName , myCrew);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate CrewGroup, skipping...");
		}
		try{
			PowerPS myPowerPS = PowerPSHelper.narrow(OrbUtils.getNCRef().resolve_str(powerPSName));
			modules.put(powerPSName , myPowerPS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate PowerPS, skipping...");
		}
		try{
			PowerStore myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(powerStoreName));
			modules.put(powerStoreName , myPowerStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate EnergyStore, skipping...");
		}
		try{
			AirRS myAirRS = AirRSHelper.narrow(OrbUtils.getNCRef().resolve_str(airRSName));
			modules.put(airRSName , myAirRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate AirRS, skipping...");
		}
		try{
			SimEnvironment mySimEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(simEnvironmentName));
			modules.put(simEnvironmentName , mySimEnvironment);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate SimEnvironment, ending!");
			System.exit(0);
		}
		try{
			GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(greyWaterStoreName));
			modules.put(greyWaterStoreName , myGreyWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate GreyWaterStore, skipping...");
		}
		try{
			PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(potableWaterStoreName));
			modules.put(potableWaterStoreName , myPotableWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate PotableWaterStore, skipping...");
		}
		try{
			DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(dirtyWaterStoreName));
			modules.put(dirtyWaterStoreName , myDirtyWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate DirtyWaterStore, skipping...");
		}
		try{
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(OrbUtils.getNCRef().resolve_str(foodProcessorName));
			modules.put(foodProcessorName , myFoodProcessor);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate FoodProcessor, skipping...");
		}
		try{
			FoodStore myFoodStore= FoodStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(foodStoreName));
			modules.put(foodStoreName , myFoodStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate FoodStore, skipping...");
		}
		try{
			CO2Store myCO2Store = CO2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(CO2StoreName));
			modules.put(CO2StoreName , myCO2Store);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate CO2Store, skipping...");
		}
		try{
			O2Store myO2Store = O2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(O2StoreName));
			modules.put(O2StoreName , myO2Store);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate CO2Store, skipping...");
		}
		try{
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassRSName));
			modules.put(biomassRSName , myBiomassRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate BiomassRS, skipping...");
		}
		try{
			BiomassStore myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassStoreName));
			modules.put(biomassStoreName, myBiomassStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate BiomassStore, skipping...");
		}
		try{
			WaterRS myWaterRS = WaterRSHelper.narrow(OrbUtils.getNCRef().resolve_str(waterRSName));
			modules.put(waterRSName , myWaterRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate WaterRS, skipping...");
		}
	}

	private void notifyListeners(){
		for (Enumeration e = listeners.elements(); e.hasMoreElements();){
			BioSimulatorListener currentListener = (BioSimulatorListener)(e.nextElement());
			currentListener.processTick();
		}
	}
	
	private void reset(){
		System.out.println("Resetting simulation");
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			currentBioModule.reset();
		}
	}

	private void tick(){
		//first tick SimEnvironment
		SimEnvironment mySimEnvironment =(SimEnvironment)(getBioModule(simEnvironmentName));
		mySimEnvironment.tick();
		//Iterate through the rest of the modules and tick them
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			if (!(currentBioModule.getModuleName().equals(simEnvironmentName))){
				currentBioModule.tick();
			}
		}
		notifyListeners();
	}
}

