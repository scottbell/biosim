package biosim.client.control;

import biosim.idl.air.*;
import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.idl.food.*;
import biosim.idl.power.*;
import biosim.idl.water.*;
import biosim.idl.framework.*;
import biosim.client.control.gui.*;
import biosim.client.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.*;

public class BioSimulator implements Runnable
{
	//Module Names
	private final static  String crewName = "CrewGroup";
	private final static  String powerPSName = "PowerPS";
	private final static  String powerStoreName = "PowerStore";
	private final static  String airRSName = "AirRS";
	private final static  String co2StoreName = "CO2Store";
	private final static  String o2StoreName = "O2Store";
	private final static  String biomassRSName = "BiomassRS";
	private final static  String biomassStoreName = "BiomassStore";
	private final static  String foodProcessorName = "FoodProcessor";
	private final static  String foodStoreName = "FoodStore";
	private final static  String waterRSName = "WaterRS";
	private final static  String dirtyWaterStoreName = "DirtyWaterStore";
	private final static  String potableWaterStoreName = "PotableWaterStore";
	private final static  String greyWaterStoreName = "GreyWaterStore";
	private final static  String simEnvironmentName = "SimEnvironment";
	private final static  String bioModuleHarnessName = "BioModuleHarness";

	private Hashtable modules;
	private NamingContextExt ncRef;
	private Thread myThread;

	public void spawnSimulation(){
		myThread = new Thread(this);
		myThread.start();
	}

	public void run(){
		System.out.println("Getting server references...");
		collectReferences();
		System.out.println("Initializing simulation...");
		initializeSimulation();
		System.out.println("Starting simulation...");
		runSimulation(75);
	}

	private void initializeSimulation(){
		//Make some crew members
		CrewGroup myCrew = (CrewGroup)(getBioModule(crewName));
		CrewPerson myCrewPerson1 = CrewPersonHelper.narrow(myCrew.createCrewPerson("Bob Roberts", 43, 170, Sex.male));
		CrewPerson myCrewPerson2 = CrewPersonHelper.narrow(myCrew.createCrewPerson("Stephanie Stevens", 25, 125, Sex.female));
		CrewPerson myCrewPerson3 = CrewPersonHelper.narrow(myCrew.createCrewPerson("Bill Williams", 30, 165, Sex.male));
		CrewPerson myCrewPerson4 = CrewPersonHelper.narrow(myCrew.createCrewPerson("Janet Janey", 22, 130, Sex.female));

		//Fill the clean water stores to the brim (20 liters), and all stores' capacities
		DirtyWaterStore myDirtyWaterStore = (DirtyWaterStore)(getBioModule(dirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = (PotableWaterStore)(getBioModule(potableWaterStoreName));
		GreyWaterStore myGreyWaterStore = (GreyWaterStore)(getBioModule(greyWaterStoreName));
		myDirtyWaterStore.setWaterCapacity(2000f);
		myPotableWaterStore.setWaterCapacity(2000f);
		myGreyWaterStore.setWaterCapacity(2000f);
		myPotableWaterStore.setWaterLevel(2000f);
		myGreyWaterStore.setWaterLevel(0f);
		myDirtyWaterStore.setWaterLevel(0f);

		//Fill the air tanks
		CO2Store myCO2Store = (CO2Store)(getBioModule(co2StoreName));
		O2Store myO2Store = (O2Store)(getBioModule(o2StoreName));
		myCO2Store.setCO2Capacity(1000f);
		myO2Store.setO2Capacity(1000f);
		myCO2Store.setCO2Level(0f);
		myO2Store.setO2Level(0f);

		//Put some air in the cabin
		SimEnvironment mySimEnvironment = (SimEnvironment)(getBioModule(simEnvironmentName));
		Double environmentCapacity = new Double(1.54893 * Math.pow(10, 6));
		mySimEnvironment.setCapacity(environmentCapacity.floatValue());
		mySimEnvironment.resetLevels();

		//Add some crops and food
		BiomassStore myBiomassStore = (BiomassStore)(getBioModule(biomassStoreName));
		FoodStore myFoodStore = (FoodStore)(getBioModule(foodStoreName));
		myBiomassStore.setBiomassCapacity(100f);
		myFoodStore.setFoodCapacity(50f);
		myBiomassStore.setBiomassLevel(0f);
		myFoodStore.setFoodLevel(50f);

		//Add some power
		PowerStore myPowerStore = (PowerStore)(getBioModule(powerStoreName));
		myPowerStore.setPowerCapacity(300f);
		myPowerStore.setPowerLevel(300f);
	}

	private void runSimulation(int numTicks){
		for (int i = 0; i < numTicks; i ++){
			tick();
		}
	}

	public BioModule getBioModule(String type){
		return (BioModule)(modules.get(type));
	}

	private void collectReferences(){

		// resolve the Objects Reference in Naming
		modules = new Hashtable();
		System.out.println("Collecting references to modules...");
		try{
			CrewGroup myCrew = CrewGroupHelper.narrow(OrbUtils.getNCRef().resolve_str(crewName));
			modules.put(crewName , myCrew);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate CrewGroup, skipping...");
		}
		try{
			PowerPS myPowerPS = PowerPSHelper.narrow(OrbUtils.getNCRef().resolve_str(powerPSName));
			modules.put(powerPSName , myPowerPS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate PowerPS, skipping...");
		}
		try{
			PowerStore myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(powerStoreName));
			modules.put(powerStoreName , myPowerStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate EnergyStore, skipping...");
		}
		try{
			AirRS myAirRS = AirRSHelper.narrow(OrbUtils.getNCRef().resolve_str(airRSName));
			modules.put(airRSName , myAirRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate AirRS, skipping...");
		}
		try{
			SimEnvironment mySimEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(simEnvironmentName));
			modules.put(simEnvironmentName , mySimEnvironment);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate SimEnvironment, skipping...");
		}
		try{
			GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(greyWaterStoreName));
			modules.put(greyWaterStoreName , myGreyWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate GreyWaterStore, skipping...");
		}
		try{
			PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(potableWaterStoreName));
			modules.put(potableWaterStoreName , myPotableWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate PotableWaterStore, skipping...");
		}
		try{
			DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(dirtyWaterStoreName));
			modules.put(dirtyWaterStoreName , myDirtyWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate DirtyWaterStore, skipping...");
		}
		try{
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(OrbUtils.getNCRef().resolve_str(foodProcessorName));
			modules.put(foodProcessorName , myFoodProcessor);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate FoodProcessor, skipping...");
		}
		try{
			FoodStore myFoodStore= FoodStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(foodStoreName));
			modules.put(foodStoreName , myFoodStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate FoodStore, skipping...");
		}
		try{
			CO2Store myCO2Store = CO2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(co2StoreName));
			modules.put(co2StoreName , myCO2Store);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate CO2Store, skipping...");
		}
		try{
			O2Store myO2Store = O2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(o2StoreName));
			modules.put(o2StoreName , myO2Store);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate CO2Store, skipping...");
		}
		try{
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassRSName));
			modules.put(biomassRSName , myBiomassRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate BiomassRS, skipping...");
		}
		try{
			BiomassStore myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassStoreName));
			modules.put(biomassStoreName, myBiomassStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate BiomassStore, skipping...");
		}
		try{
			WaterRS myWaterRS = WaterRSHelper.narrow(OrbUtils.getNCRef().resolve_str(waterRSName));
			modules.put(waterRSName , myWaterRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate WaterRS, skipping...");
		}
	}

	public void tick(){
		//Iterate through modules and tick them
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			currentBioModule.tick();
		}
	}
}

