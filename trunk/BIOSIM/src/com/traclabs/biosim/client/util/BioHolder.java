package biosim.client.simulation.framework;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
import java.util.*;
import biosim.client.simulation.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
/**
 * @author    Scott Bell
 */	

public class BioHolder
{
	//Module Names
	public final static String crewName = "CrewGroup";
	public final static String powerPSName = "PowerPS";
	public final static String powerStoreName = "PowerStore";
	public final static String airRSName = "AirRS";
	public final static String CO2StoreName = "CO2Store";
	public final static String O2StoreName = "O2Store";
	public final static String biomassRSName = "BiomassRS";
	public final static String biomassStoreName = "BiomassStore";
	public final static String foodProcessorName = "FoodProcessor";
	public final static String foodStoreName = "FoodStore";
	public final static String waterRSName = "WaterRS";
	public final static String dirtyWaterStoreName = "DirtyWaterStore";
	public final static String potableWaterStoreName = "PotableWaterStore";
	public final static String greyWaterStoreName = "GreyWaterStore";
	public final static String simEnvironmentName = "CrewEnvironment";
	public final static String crewEnvironmentName = "CrewEnvironment";
	public final static String plantEnvironmentName = "PlantEnvironment";

	//A hastable containing the server references
	private static Map modules;
	private static BioDriver myBioDriver;
	private static boolean hasCollectedReferences = false;
	private static int myID = 0;

	/**
	* Fetches a BioModule (e.g. AirRS, FoodProcessor, PotableWaterStore) that has been collected by the BioSimulator
	* @return the BioModule requested, null if not found
	*/
	public static BioModule getBioModule(String type){
		collectReferences();
		if (type == null){
			System.err.println("BioHolder: Passed null string....");
			return null;
		}
		BioModule returnModule = (BioModule)(modules.get(type));
		if (returnModule == null){
			System.err.println("BioHolder: Couldn't find module: "+type);
			return null;
		}
		return returnModule;
	}
	
	public static BioModule[] getBioModules(){
		collectReferences();
		BioModule[] arrayModules = new BioModule[modules.size()];
		return (BioModule[])(modules.values().toArray(arrayModules));
	}
	
	public static String[] getBioModuleNames(){
		collectReferences();
		String[] arrayModuleNames = new String[modules.size()];
		if (modules.size() == 0)
			return arrayModuleNames;
		return (String[])(modules.keySet().toArray(arrayModuleNames));
	}

	public static BioDriver getBioDriver(){
		collectReferences();
		return myBioDriver;
	}

	public static void setID(int pID){
		myID = pID;
	}

	/**
	* Tries to collect references to all the servers and adds them to a hashtable than can be accessed by outside classes.
	*/
	private static void collectReferences(){
		if (hasCollectedReferences)
			return;
		// resolve the Objects Reference in Naming
		try{
			if (modules == null)
				modules = new Hashtable();
			System.err.println("BioHolder: Collecting references to modules...");
			CrewGroup myCrew = CrewGroupHelper.narrow(OrbUtils.getNCRef().resolve_str(crewName+myID));
			modules.put(crewName , myCrew);
			PowerPS myPowerPS = PowerPSHelper.narrow(OrbUtils.getNCRef().resolve_str(powerPSName+myID));
			modules.put(powerPSName , myPowerPS);
			PowerStore myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(powerStoreName+myID));
			modules.put(powerStoreName , myPowerStore);
			AirRS myAirRS = AirRSHelper.narrow(OrbUtils.getNCRef().resolve_str(airRSName+myID));
			modules.put(airRSName , myAirRS);
			SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(crewEnvironmentName+myID));
			modules.put(crewEnvironmentName , myCrewEnvironment);
			SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(plantEnvironmentName+myID));
			modules.put(plantEnvironmentName , myPlantEnvironment);
			GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(greyWaterStoreName+myID));
			modules.put(greyWaterStoreName , myGreyWaterStore);
			PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(potableWaterStoreName+myID));
			modules.put(potableWaterStoreName , myPotableWaterStore);
			DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(dirtyWaterStoreName+myID));
			modules.put(dirtyWaterStoreName , myDirtyWaterStore);
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(OrbUtils.getNCRef().resolve_str(foodProcessorName+myID));
			modules.put(foodProcessorName , myFoodProcessor);
			FoodStore myFoodStore= FoodStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(foodStoreName+myID));
			modules.put(foodStoreName , myFoodStore);
			CO2Store myCO2Store = CO2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(CO2StoreName+myID));
			modules.put(CO2StoreName , myCO2Store);
			O2Store myO2Store = O2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(O2StoreName+myID));
			modules.put(O2StoreName , myO2Store);
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassRSName+myID));
			modules.put(biomassRSName , myBiomassRS);
			BiomassStore myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassStoreName+myID));
			modules.put(biomassStoreName, myBiomassStore);
			WaterRS myWaterRS = WaterRSHelper.narrow(OrbUtils.getNCRef().resolve_str(waterRSName+myID));
			modules.put(waterRSName , myWaterRS);
			myBioDriver = BioDriverHelper.narrow(OrbUtils.getNCRef().resolve_str("BioDriver"+myID));
			hasCollectedReferences = true;
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			OrbUtils.sleepAwhile();
			collectReferences();
		}
		catch (Exception e){
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			OrbUtils.resetInit();
			OrbUtils.sleepAwhile();
			collectReferences();
		}
	}
}

