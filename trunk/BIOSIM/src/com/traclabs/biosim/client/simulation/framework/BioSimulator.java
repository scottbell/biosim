package biosim.client.control;

import ALSS.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.*;

public class BioSimulator implements Runnable
{
 //Module Names
	private final static String crewName = "Crew";
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

	private Hashtable modules;
	private NamingContextExt ncRef;
	private Thread myThread;

	public BioSimulator(){
		initializeORB();
		collectReferences();
	}
	
	public void spawnSimulation(){
		myThread = new Thread(this);
		myThread.start();
	}

	public void run(){
		DirtyWaterStore myDirtyWater = (DirtyWaterStore)(getBioModule("DirtyWaterStore"));
		System.out.println("Trying to add "+2f+" liters of dirty water");
		float actuallyAdded = myDirtyWater.addWater(2f);
		System.out.println("Actually added "+actuallyAdded+" liters of dirty water");
		System.out.println("Dirty water levels at: "+myDirtyWater.getWaterLevel());
		System.out.println("Trying to add "+12f+" liters of dirty water");
		actuallyAdded = myDirtyWater.addWater(12f);
		System.out.println("Actually added "+actuallyAdded+" liters of dirty water");
		System.out.println("Dirty water levels at: "+myDirtyWater.getWaterLevel());
		Crew myCrew = (Crew)(getBioModule("Crew"));
		Activity sleeping = ActivityHelper.narrow(myCrew.getScheduledActivityByName("sleeping"));
		System.out.println("This activity is: "+sleeping.getName()+" for "+sleeping.getTimeLength());
		for (int i = 0; i < 10; i ++){
			tick();
		}
	}
	
	public BioModule getBioModule(String type){
		return (BioModule)(modules.get(type));
	}
	
	private void initializeORB(){
		//create null array
		String nullArgs[] = null;
		// create and initialize the ORB
		ORB orb = ORB.init(nullArgs, null);
		// get the root naming context
		org.omg.CORBA.Object objRef = null;
		try{
			objRef = orb.resolve_initial_references("NameService");
		}
		catch (org.omg.CORBA.ORBPackage.InvalidName e){
			System.out.println("Couldn't find nameserver! "+e);
		}
		// Use NamingContextExt instead of NamingContext. This is part of the Interoperable naming Service.
		ncRef = NamingContextExtHelper.narrow(objRef);
	}

 private void collectReferences(){

		// resolve the Objects Reference in Naming
		modules = new Hashtable();
		System.out.println("Collecting references to modules...");
		try{
			Crew myCrew = CrewHelper.narrow(ncRef.resolve_str(crewName));
			modules.put(crewName , myCrew);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate Crew, skipping...");
		}
		try{
			PowerPS myPowerPS = PowerPSHelper.narrow(ncRef.resolve_str(powerPSName));
			modules.put(powerPSName , myPowerPS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate PowerPS, skipping...");
		}
		try{
			PowerStore myPowerStore = PowerStoreHelper.narrow(ncRef.resolve_str(powerStoreName));
			modules.put(powerStoreName , myPowerStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate EnergyStore, skipping...");
		}
		try{
			AirRS myAirRS = AirRSHelper.narrow(ncRef.resolve_str(airRSName));
			modules.put(airRSName , myAirRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate AirRS, skipping...");
		}
		try{
			SimEnvironment mySimEnvironment = SimEnvironmentHelper.narrow(ncRef.resolve_str(simEnvironmentName));
			modules.put(simEnvironmentName , mySimEnvironment);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate SimEnvironment, skipping...");
		}
		try{
			GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(ncRef.resolve_str(greyWaterStoreName));
			modules.put(greyWaterStoreName , myGreyWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate GreyWaterStore, skipping...");
		}
		try{
			PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(ncRef.resolve_str(potableWaterStoreName));
			modules.put(potableWaterStoreName , myPotableWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate PotableWaterStore, skipping...");
		}
		try{
			DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(ncRef.resolve_str(dirtyWaterStoreName));
			modules.put(dirtyWaterStoreName , myDirtyWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate DirtyWaterStore, skipping...");
		}
		try{
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(ncRef.resolve_str(foodProcessorName));
			modules.put(foodProcessorName , myFoodProcessor);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate FoodProcessor, skipping...");
		}
		try{
			FoodStore myFoodStore= FoodStoreHelper.narrow(ncRef.resolve_str(foodStoreName));
			modules.put(foodStoreName , myFoodStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate FoodStore, skipping...");
		}
		try{
			CO2Store myCO2Store = CO2StoreHelper.narrow(ncRef.resolve_str(co2StoreName));
			modules.put(co2StoreName , myCO2Store);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate CO2Store, skipping...");
		}
		try{
			O2Store myO2Store = O2StoreHelper.narrow(ncRef.resolve_str(o2StoreName));
			modules.put(o2StoreName , myO2Store);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate CO2Store, skipping...");
		}
		try{
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(ncRef.resolve_str(biomassRSName));
			modules.put(biomassRSName , myBiomassRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate BiomassRS, skipping...");
		}
		try{
			BiomassStore myBiomassStore = BiomassStoreHelper.narrow(ncRef.resolve_str(biomassStoreName));
			modules.put(biomassStoreName, myBiomassStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate BiomassStore, skipping...");
		}
		try{
			WaterRS myWaterRS = WaterRSHelper.narrow(ncRef.resolve_str(waterRSName));
			modules.put(waterRSName , myWaterRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate WaterRS, skipping...");
		}
	}

	public void tick(){
		//Iterate through modules and tick them
		System.out.println("Ticking modules...");
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			currentBioModule.tick();
		}
	}
}

