package biosim.client.framework;

import biosim.idl.air.*;
import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.idl.food.*;
import biosim.idl.power.*;
import biosim.idl.water.*;
import biosim.idl.framework.*;
import java.util.*;
import biosim.client.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
/**
 * @author    Scott Bell
 */	

public class BioHolder
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
	
	//A hastable containing the server references
	private static Hashtable modules;
	private static BioDriver myBioDriver;
	private static boolean hasCollectedReferences = false;
	
	/**
	* Fetches a BioModule (e.g. AirRS, FoodProcessor, PotableWaterStore) that has been collected by the BioSimulator
	* @return the BioModule requested, null if not found
	*/
	public static BioModule getBioModule(String type){
		collectReferences();
		return (BioModule)(modules.get(type));
	}
	
	public static BioDriver getBioDriver(){
		collectReferences();
		return myBioDriver;
	}
	
	/**
	* Tries to collect references to all the servers and adds them to a hashtable than can be accessed by outside classes.
	*/
	private static void collectReferences(){
		if (hasCollectedReferences)
			return;
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
		try{
			myBioDriver = BioDriverHelper.narrow(OrbUtils.getNCRef().resolve_str("BioDriver"));
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("BioSimulator: Couldn't locate BioDriver, skipping...");
		}
		hasCollectedReferences = true;
	}
}

