package biosim.server.framework;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.power.*;
import biosim.idl.util.log.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.framework.*;
import biosim.server.simulation.framework.*;
import biosim.server.simulation.air.*;
import biosim.server.simulation.crew.*;
import biosim.server.simulation.food.*;
import biosim.server.simulation.water.*;
import biosim.server.simulation.power.*;
import biosim.server.simulation.environment.*;
import biosim.server.util.*;
import biosim.server.util.log.*;
/**
 * The Biosim Server.  Creates an instance of each module (AirRS, FoodProcessor, WaterRS, etc..) and binds them to the nameserver.
 *
 * @author    Scott Bell
 */

public class BiosimServer extends GenericServer{
	
	public BiosimServer(){
	}
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param args first element can be an ID to assign to this instance
	*/
	public static void main(String args[]) {
		BiosimServer myServer = new BiosimServer();
		int id = myServer.getIDfromArgs(args);
		myServer.createServers(id);
		myServer.runServer("BiosimServer"+id);
	}
	
	public void startBiosimServer(int id){
		createServers(id);
		runServer("BiosimServer"+id);
	}
	
	/**
	* Instantiates the server and binds it to the name server.
	* @param id The ID this instance of BioSim will use.
	*/
	public void createServers(int id){
		// create servants and register them with ORB
		
		//Simulation
		SimEnvironmentImpl myCrewEnvironmentImpl = new SimEnvironmentImpl(id, "CrewEnvironment");
		SimEnvironmentImpl myPlantEnvironmentImpl = new SimEnvironmentImpl(id, "PlantEnvironment");
		AirRSImpl myAirRSImpl = new AirRSImpl(id);
		AccumulatorImpl myAccumulatorImpl = new AccumulatorImpl(id);
		InjectorImpl myInjectorImpl = new InjectorImpl(id);
		CO2StoreImpl myCO2StoreImpl = new CO2StoreImpl(id);
		O2StoreImpl myO2StoreImpl = new O2StoreImpl(id);
		CrewGroupImpl myCrewImpl = new CrewGroupImpl(id);
		BiomassRSImpl myBiomassRSImpl = new BiomassRSImpl(id);
		BiomassStoreImpl myBiomassStoreImpl = new BiomassStoreImpl(id);
		FoodProcessorImpl myFoodProcessorImpl = new FoodProcessorImpl(id);
		FoodStoreImpl myFoodStoreImpl = new FoodStoreImpl(id);
		PowerPSImpl myPowerPSImpl = new SolarPowerPS(id);
		PowerStoreImpl myPowerStoreImpl = new PowerStoreImpl(id);
		WaterRSImpl myWaterRSImpl = new WaterRSImpl(id);
		GreyWaterStoreImpl myGreyWaterStoreImpl = new GreyWaterStoreImpl(id);
		PotableWaterStoreImpl myPotableWaterStoreImpl = new PotableWaterStoreImpl(id);
		DirtyWaterStoreImpl myDirtyWaterStoreImpl = new DirtyWaterStoreImpl(id);
		LoggerImpl myLoggerImpl = new LoggerImpl(id);
		BioDriverImpl myBioDriverImpl = new BioDriverImpl(id);
		registerServer(new SimEnvironmentPOATie(myCrewEnvironmentImpl), myCrewEnvironmentImpl.getModuleName());
		registerServer(new SimEnvironmentPOATie(myPlantEnvironmentImpl), myPlantEnvironmentImpl.getModuleName());
		registerServer(new AirRSPOATie(myAirRSImpl), myAirRSImpl.getModuleName());
		registerServer(new CO2StorePOATie(myCO2StoreImpl), myCO2StoreImpl.getModuleName());
		registerServer(new O2StorePOATie(myO2StoreImpl), myO2StoreImpl.getModuleName());
		registerServer(new CrewGroupPOATie(myCrewImpl), myCrewImpl.getModuleName());
		registerServer(new BiomassRSPOATie(myBiomassRSImpl), myBiomassRSImpl.getModuleName());
		registerServer(new BiomassStorePOATie(myBiomassStoreImpl), myBiomassStoreImpl.getModuleName());
		registerServer(new FoodProcessorPOATie(myFoodProcessorImpl), myFoodProcessorImpl.getModuleName());
		registerServer(new FoodStorePOATie(myFoodStoreImpl), myFoodStoreImpl.getModuleName());
		registerServer(new PowerPSPOATie(myPowerPSImpl), myPowerPSImpl.getModuleName());
		registerServer(new PowerStorePOATie(myPowerStoreImpl), myPowerStoreImpl.getModuleName());
		registerServer(new WaterRSPOATie(myWaterRSImpl), myWaterRSImpl.getModuleName());
		registerServer(new GreyWaterStorePOATie(myGreyWaterStoreImpl), myGreyWaterStoreImpl.getModuleName());
		registerServer(new PotableWaterStorePOATie(myPotableWaterStoreImpl), myPotableWaterStoreImpl.getModuleName());
		registerServer(new DirtyWaterStorePOATie(myDirtyWaterStoreImpl), myDirtyWaterStoreImpl.getModuleName());
		registerServer(myLoggerImpl, myLoggerImpl.getName());
		registerServer(myBioDriverImpl, myBioDriverImpl.getName());
		registerServer(new InjectorPOATie(myInjectorImpl), myInjectorImpl.getModuleName());
		registerServer(new AccumulatorPOATie(myAccumulatorImpl), myAccumulatorImpl.getModuleName());
		
		//Sensors
	}                          
}

