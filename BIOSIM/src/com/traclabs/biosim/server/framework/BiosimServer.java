package biosim.server.framework;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.framework.*;
import biosim.server.simulation.air.*;
import biosim.server.simulation.crew.*;
import biosim.server.simulation.food.*;
import biosim.server.simulation.water.*;
import biosim.server.simulation.power.*;
import biosim.server.simulation.environment.*;
import biosim.server.simulation.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.water.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.framework.*;
import biosim.server.sensor.air.*;
import biosim.server.sensor.food.*;
import biosim.server.sensor.water.*;
import biosim.server.sensor.power.*;
import biosim.server.sensor.environment.*;
import biosim.server.sensor.framework.*;
import biosim.idl.util.log.*;
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

		//
		//Framework
		//
		LoggerImpl myLoggerImpl = new LoggerImpl(id);
		BioDriverImpl myBioDriverImpl = new BioDriverImpl(id);
		registerServer(myLoggerImpl, myLoggerImpl.getName());
		registerServer(myBioDriverImpl, myBioDriverImpl.getName());

		//
		//Simulation
		//
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
		registerServer(new InjectorPOATie(myInjectorImpl), myInjectorImpl.getModuleName());
		registerServer(new AccumulatorPOATie(myAccumulatorImpl), myAccumulatorImpl.getModuleName());

		//
		//Sensors
		//

		//Air Sesnors
		{
			//AirRS
			{
				PowerInFlowRateSensorImpl myPowerInFlowRateSensorImpl = new PowerInFlowRateSensorImpl(id);
				AirInFlowRateSensorImpl myAirInFlowRateSensorImpl = new AirInFlowRateSensorImpl(id);
				AirOutFlowRateSensorImpl myAirOutFlowRateSensorImpl = new AirOutFlowRateSensorImpl(id);
				O2OutFlowRateSensorImpl myO2OutFlowRateSensorImpl = new O2OutFlowRateSensorImpl(id);
				CO2OutFlowRateSensorImpl myCO2OutFlowRateSensorImpl = new CO2OutFlowRateSensorImpl(id);
				CO2InFlowRateSensorImpl myCO2InFlowRateSensorImpl = new CO2InFlowRateSensorImpl(id);
				registerServer(new PowerInFlowRateSensorPOATie(myPowerInFlowRateSensorImpl), "AirRS" + myPowerInFlowRateSensorImpl.getModuleName());
				registerServer(new AirInFlowRateSensorPOATie(myAirInFlowRateSensorImpl), "AirRS" + myAirInFlowRateSensorImpl.getModuleName());
				registerServer(new AirOutFlowRateSensorPOATie(myAirOutFlowRateSensorImpl), "AirRS" + myAirOutFlowRateSensorImpl.getModuleName());
				registerServer(new O2OutFlowRateSensorPOATie(myO2OutFlowRateSensorImpl), "AirRS" + myO2OutFlowRateSensorImpl.getModuleName());
				registerServer(new CO2InFlowRateSensorPOATie(myCO2InFlowRateSensorImpl), "AirRS" + myCO2InFlowRateSensorImpl.getModuleName());
				registerServer(new CO2OutFlowRateSensorPOATie(myCO2OutFlowRateSensorImpl), "AirRS" + myCO2OutFlowRateSensorImpl.getModuleName());
			}
			//Stores
			{
				O2StoreLevelSensorImpl myO2StoreLevelSensorImpl = new O2StoreLevelSensorImpl(id);
				CO2StoreLevelSensorImpl myCO2StoreLevelSensorImpl = new CO2StoreLevelSensorImpl(id);
				registerServer(new O2StoreLevelSensorPOATie(myO2StoreLevelSensorImpl), myO2StoreLevelSensorImpl.getModuleName());
				registerServer(new CO2StoreLevelSensorPOATie(myCO2StoreLevelSensorImpl), myCO2StoreLevelSensorImpl.getModuleName());
			}
		}

		//Power Sensors
		{
			//Power PS
			{
				PowerOutFlowRateSensorImpl myPowerOutFlowRateSensorImpl = new PowerOutFlowRateSensorImpl(id);
				registerServer(new PowerOutFlowRateSensorPOATie(myPowerOutFlowRateSensorImpl), "PowerPS" + myPowerOutFlowRateSensorImpl.getModuleName());
			}
			//Stores
			{
				PowerStoreLevelSensorImpl myPowerStoreLevelSensorImpl = new PowerStoreLevelSensorImpl(id);
				registerServer(new PowerStoreLevelSensorPOATie(myPowerStoreLevelSensorImpl), myPowerStoreLevelSensorImpl.getModuleName());
			}
		}

		//Food Sensors
		{
			//Food Processor
			{
				PowerInFlowRateSensorImpl myPowerInFlowRateSensorImpl = new PowerInFlowRateSensorImpl(id);
				BiomassInFlowRateSensorImpl myBiomassInFlowRateSensorImpl = new BiomassInFlowRateSensorImpl(id);
				FoodOutFlowRateSensorImpl myFoodOutFlowRateSensorImpl = new FoodOutFlowRateSensorImpl(id);
				registerServer(new PowerInFlowRateSensorPOATie(myPowerInFlowRateSensorImpl), "FoodProcessor" + myPowerInFlowRateSensorImpl.getModuleName());
				registerServer(new BiomassInFlowRateSensorPOATie(myBiomassInFlowRateSensorImpl), "FoodProcessor" + myBiomassInFlowRateSensorImpl.getModuleName());
				registerServer(new FoodOutFlowRateSensorPOATie(myFoodOutFlowRateSensorImpl), "FoodProcessor" + myFoodOutFlowRateSensorImpl.getModuleName());
			}
			//BiomassRS
			{
				PowerInFlowRateSensorImpl myPowerInFlowRateSensorImpl = new PowerInFlowRateSensorImpl(id);
				BiomassOutFlowRateSensorImpl myBiomassOutFlowRateSensorImpl = new BiomassOutFlowRateSensorImpl(id);
				FoodOutFlowRateSensorImpl myFoodOutFlowRateSensorImpl = new FoodOutFlowRateSensorImpl(id);
				AirInFlowRateSensorImpl myAirInFlowRateSensorImpl = new AirInFlowRateSensorImpl(id);
				AirOutFlowRateSensorImpl myAirOutFlowRateSensorImpl = new AirOutFlowRateSensorImpl(id);
				PotableWaterInFlowRateSensorImpl myPotableWaterInFlowRateSensorImpl = new PotableWaterInFlowRateSensorImpl(id);
				GreyWaterInFlowRateSensorImpl myGreyWaterInFlowRateSensorImpl = new GreyWaterInFlowRateSensorImpl(id);
				registerServer(new PowerInFlowRateSensorPOATie(myPowerInFlowRateSensorImpl), "BiomassRS" + myPowerInFlowRateSensorImpl.getModuleName());
				registerServer(new BiomassOutFlowRateSensorPOATie(myBiomassOutFlowRateSensorImpl), "BiomassRS" + myBiomassOutFlowRateSensorImpl.getModuleName());
				registerServer(new FoodOutFlowRateSensorPOATie(myFoodOutFlowRateSensorImpl), "BiomassRS" + myFoodOutFlowRateSensorImpl.getModuleName());
				registerServer(new AirInFlowRateSensorPOATie(myAirInFlowRateSensorImpl), "BiomassRS" + myAirInFlowRateSensorImpl.getModuleName());
				registerServer(new AirOutFlowRateSensorPOATie(myAirOutFlowRateSensorImpl), "BiomassRS" + myAirOutFlowRateSensorImpl.getModuleName());
				registerServer(new PotableWaterInFlowRateSensorPOATie(myPotableWaterInFlowRateSensorImpl), "BiomassRS" + myPotableWaterInFlowRateSensorImpl.getModuleName());
				registerServer(new GreyWaterInFlowRateSensorPOATie(myGreyWaterInFlowRateSensorImpl), "BiomassRS" + myGreyWaterInFlowRateSensorImpl.getModuleName());
			}
			//Stores
			{
				BiomassStoreLevelSensorImpl myBiomassStoreLevelSensorImpl = new BiomassStoreLevelSensorImpl(id);
				FoodStoreLevelSensorImpl myFoodStoreLevelSensorImpl = new FoodStoreLevelSensorImpl(id);
				registerServer(new BiomassStoreLevelSensorPOATie(myBiomassStoreLevelSensorImpl), myBiomassStoreLevelSensorImpl.getModuleName());
				registerServer(new FoodStoreLevelSensorPOATie(myFoodStoreLevelSensorImpl), myFoodStoreLevelSensorImpl.getModuleName());
			}
		}

		//
		//Water Sensors
		//
		{
			//Water RS
			{
				PowerInFlowRateSensorImpl myPowerInFlowRateSensorImpl = new PowerInFlowRateSensorImpl(id);
				GreyWaterInFlowRateSensorImpl myGreyWaterInFlowRateSensorImpl = new GreyWaterInFlowRateSensorImpl(id);
				DirtyWaterInFlowRateSensorImpl myDirtyWaterInFlowRateSensorImpl = new DirtyWaterInFlowRateSensorImpl(id);
				PotableWaterOutFlowRateSensorImpl myPotableWaterOutFlowRateSensorImpl = new PotableWaterOutFlowRateSensorImpl(id);
				registerServer(new PowerInFlowRateSensorPOATie(myPowerInFlowRateSensorImpl), "WaterRS" + myPowerInFlowRateSensorImpl.getModuleName());
				registerServer(new GreyWaterInFlowRateSensorPOATie(myGreyWaterInFlowRateSensorImpl), "WaterRS" + myGreyWaterInFlowRateSensorImpl.getModuleName());
				registerServer(new DirtyWaterInFlowRateSensorPOATie(myDirtyWaterInFlowRateSensorImpl), "WaterRS" + myDirtyWaterInFlowRateSensorImpl.getModuleName());
				registerServer(new PotableWaterOutFlowRateSensorPOATie(myPotableWaterOutFlowRateSensorImpl), "WaterRS" + myPotableWaterOutFlowRateSensorImpl.getModuleName());
			}
			//Stores
			{
				PotableWaterStoreLevelSensorImpl myPotableWaterStoreLevelSensorImpl = new PotableWaterStoreLevelSensorImpl(id);
				GreyWaterStoreLevelSensorImpl myGreyWaterStoreLevelSensorImpl = new GreyWaterStoreLevelSensorImpl(id);
				DirtyWaterStoreLevelSensorImpl myDirtyWaterStoreLevelSensorImpl = new DirtyWaterStoreLevelSensorImpl(id);
				registerServer(new PotableWaterStoreLevelSensorPOATie(myPotableWaterStoreLevelSensorImpl), myPotableWaterStoreLevelSensorImpl.getModuleName());
				registerServer(new GreyWaterStoreLevelSensorPOATie(myGreyWaterStoreLevelSensorImpl), myGreyWaterStoreLevelSensorImpl.getModuleName());
				registerServer(new DirtyWaterStoreLevelSensorPOATie(myDirtyWaterStoreLevelSensorImpl), myDirtyWaterStoreLevelSensorImpl.getModuleName());
			}
		}

		//
		//Environment Sensors
		//
		{
			//CrewEnvironment
			{
				O2AirLevelSensorImpl myO2AirLevelSensorImpl = new O2AirLevelSensorImpl(id);
				CO2AirLevelSensorImpl myCO2AirLevelSensorImpl = new CO2AirLevelSensorImpl(id);
				OtherAirLevelSensorImpl myOtherAirLevelSensorImpl = new OtherAirLevelSensorImpl(id);
				registerServer(new O2AirLevelSensorPOATie(myO2AirLevelSensorImpl), "CrewEnvironment" + myO2AirLevelSensorImpl.getModuleName());
				registerServer(new CO2AirLevelSensorPOATie(myCO2AirLevelSensorImpl), "CrewEnvironment" + myCO2AirLevelSensorImpl.getModuleName());
				registerServer(new OtherAirLevelSensorPOATie(myOtherAirLevelSensorImpl), "CrewEnvironment" + myOtherAirLevelSensorImpl.getModuleName());
			}
			//Plant Evnironment
			{
				O2AirLevelSensorImpl myO2AirLevelSensorImpl = new O2AirLevelSensorImpl(id);
				CO2AirLevelSensorImpl myCO2AirLevelSensorImpl = new CO2AirLevelSensorImpl(id);
				OtherAirLevelSensorImpl myOtherAirLevelSensorImpl = new OtherAirLevelSensorImpl(id);
				registerServer(new O2AirLevelSensorPOATie(myO2AirLevelSensorImpl), "PlantEnvironment" + myO2AirLevelSensorImpl.getModuleName());
				registerServer(new CO2AirLevelSensorPOATie(myCO2AirLevelSensorImpl), "PlantEnvironment" + myCO2AirLevelSensorImpl.getModuleName());
				registerServer(new OtherAirLevelSensorPOATie(myOtherAirLevelSensorImpl), "PlantEnvironment" + myOtherAirLevelSensorImpl.getModuleName());
			}
			
		}

		//
		//Framework Sensors
		//
		{
			//Accumulator
			{
				CO2AirEnvironmentInFlowRateSensorImpl myCO2AirEnvironmentInFlowRateSensorImpl = new CO2AirEnvironmentInFlowRateSensorImpl(id);
				CO2AirStoreOutFlowRateSensorImpl myCO2AirStoreOutFlowRateSensorImpl = new CO2AirStoreOutFlowRateSensorImpl(id);
				O2AirEnvironmentInFlowRateSensorImpl myO2AirEnvironmentInFlowRateSensorImpl = new O2AirEnvironmentInFlowRateSensorImpl(id);
				O2AirStoreOutFlowRateSensorImpl myO2AirStoreOutFlowRateSensorImpl = new O2AirStoreOutFlowRateSensorImpl(id);
				registerServer(new CO2AirStoreOutFlowRateSensorPOATie(myCO2AirStoreOutFlowRateSensorImpl), "Accumulator" + myCO2AirStoreOutFlowRateSensorImpl.getModuleName());
				registerServer(new CO2AirEnvironmentInFlowRateSensorPOATie(myCO2AirEnvironmentInFlowRateSensorImpl), "Accumulator" + myCO2AirEnvironmentInFlowRateSensorImpl.getModuleName());
				registerServer(new O2AirStoreOutFlowRateSensorPOATie(myO2AirStoreOutFlowRateSensorImpl), "Accumulator" + myO2AirStoreOutFlowRateSensorImpl.getModuleName());
				registerServer(new O2AirEnvironmentInFlowRateSensorPOATie(myO2AirEnvironmentInFlowRateSensorImpl), "Accumulator" + myO2AirEnvironmentInFlowRateSensorImpl.getModuleName());
			}
			//Injector
			{
				CO2AirEnvironmentOutFlowRateSensorImpl myCO2AirEnvironmentOutFlowRateSensorImpl = new CO2AirEnvironmentOutFlowRateSensorImpl(id);
				CO2AirStoreInFlowRateSensorImpl myCO2AirStoreInFlowRateSensorImpl = new CO2AirStoreInFlowRateSensorImpl(id);
				O2AirEnvironmentOutFlowRateSensorImpl myO2AirEnvironmentOutFlowRateSensorImpl = new O2AirEnvironmentOutFlowRateSensorImpl(id);
				O2AirStoreInFlowRateSensorImpl myO2AirStoreInFlowRateSensorImpl = new O2AirStoreInFlowRateSensorImpl(id);
				registerServer(new CO2AirStoreInFlowRateSensorPOATie(myCO2AirStoreInFlowRateSensorImpl), "Injector" + myCO2AirStoreInFlowRateSensorImpl.getModuleName());
				registerServer(new CO2AirEnvironmentOutFlowRateSensorPOATie(myCO2AirEnvironmentOutFlowRateSensorImpl), "Injector" + myCO2AirEnvironmentOutFlowRateSensorImpl.getModuleName());
				registerServer(new O2AirStoreInFlowRateSensorPOATie(myO2AirStoreInFlowRateSensorImpl), "Injector" + myO2AirStoreInFlowRateSensorImpl.getModuleName());
				registerServer(new O2AirEnvironmentOutFlowRateSensorPOATie(myO2AirEnvironmentOutFlowRateSensorImpl), "Injector" + myO2AirEnvironmentOutFlowRateSensorImpl.getModuleName());
			}
		}
	}
}

