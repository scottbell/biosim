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
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.framework.*;
import biosim.server.sensor.air.*;
import biosim.server.sensor.food.*;
import biosim.server.sensor.water.*;
import biosim.server.sensor.power.*;
import biosim.server.sensor.crew.*;
import biosim.server.sensor.environment.*;
import biosim.server.sensor.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.water.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.crew.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.framework.*;
import biosim.server.actuator.air.*;
import biosim.server.actuator.food.*;
import biosim.server.actuator.water.*;
import biosim.server.actuator.power.*;
import biosim.server.actuator.environment.*;
import biosim.server.actuator.framework.*;
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

		//Crew Sensors
		{
			CrewGroupDeathSensorImpl myCrewGroupDeathSensorImpl = new CrewGroupDeathSensorImpl(id);
			registerServer(new CrewGroupDeathSensorPOATie(myCrewGroupDeathSensorImpl), myCrewGroupDeathSensorImpl.getModuleName());
			PotableWaterInFlowRateSensorImpl myPotableWaterInFlowRateSensorImpl = new PotableWaterInFlowRateSensorImpl(id);
			registerServer(new PotableWaterInFlowRateSensorPOATie(myPotableWaterInFlowRateSensorImpl), "CrewGroup" + myPotableWaterInFlowRateSensorImpl.getModuleName());
			DirtyWaterOutFlowRateSensorImpl myDirtyWaterOutFlowRateSensorImpl = new DirtyWaterOutFlowRateSensorImpl(id);
			registerServer(new DirtyWaterOutFlowRateSensorPOATie(myDirtyWaterOutFlowRateSensorImpl), "CrewGroup" + myDirtyWaterOutFlowRateSensorImpl.getModuleName());
			GreyWaterOutFlowRateSensorImpl myGreyWaterOutFlowRateSensorImpl = new GreyWaterOutFlowRateSensorImpl(id);
			registerServer(new GreyWaterOutFlowRateSensorPOATie(myGreyWaterOutFlowRateSensorImpl), "CrewGroup" + myGreyWaterOutFlowRateSensorImpl.getModuleName());
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
				DirtyWaterOutFlowRateSensorImpl myDirtyWaterOutFlowRateSensorImpl = new DirtyWaterOutFlowRateSensorImpl(id);
				registerServer(new PowerInFlowRateSensorPOATie(myPowerInFlowRateSensorImpl), "BiomassRS" + myPowerInFlowRateSensorImpl.getModuleName());
				registerServer(new BiomassOutFlowRateSensorPOATie(myBiomassOutFlowRateSensorImpl), "BiomassRS" + myBiomassOutFlowRateSensorImpl.getModuleName());
				registerServer(new FoodOutFlowRateSensorPOATie(myFoodOutFlowRateSensorImpl), "BiomassRS" + myFoodOutFlowRateSensorImpl.getModuleName());
				registerServer(new AirInFlowRateSensorPOATie(myAirInFlowRateSensorImpl), "BiomassRS" + myAirInFlowRateSensorImpl.getModuleName());
				registerServer(new AirOutFlowRateSensorPOATie(myAirOutFlowRateSensorImpl), "BiomassRS" + myAirOutFlowRateSensorImpl.getModuleName());
				registerServer(new PotableWaterInFlowRateSensorPOATie(myPotableWaterInFlowRateSensorImpl), "BiomassRS" + myPotableWaterInFlowRateSensorImpl.getModuleName());
				registerServer(new GreyWaterInFlowRateSensorPOATie(myGreyWaterInFlowRateSensorImpl), "BiomassRS" + myGreyWaterInFlowRateSensorImpl.getModuleName());
				registerServer(new DirtyWaterOutFlowRateSensorPOATie(myDirtyWaterOutFlowRateSensorImpl), "BiomassRS" + myDirtyWaterOutFlowRateSensorImpl.getModuleName());
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
				O2AirConcentrationSensorImpl myO2AirConcentrationSensorImpl = new O2AirConcentrationSensorImpl(id);
				CO2AirConcentrationSensorImpl myCO2AirConcentrationSensorImpl = new CO2AirConcentrationSensorImpl(id);
				OtherAirConcentrationSensorImpl myOtherAirConcentrationSensorImpl = new OtherAirConcentrationSensorImpl(id);
				registerServer(new O2AirConcentrationSensorPOATie(myO2AirConcentrationSensorImpl), "CrewEnvironment" + myO2AirConcentrationSensorImpl.getModuleName());
				registerServer(new CO2AirConcentrationSensorPOATie(myCO2AirConcentrationSensorImpl), "CrewEnvironment" + myCO2AirConcentrationSensorImpl.getModuleName());
				registerServer(new OtherAirConcentrationSensorPOATie(myOtherAirConcentrationSensorImpl), "CrewEnvironment" + myOtherAirConcentrationSensorImpl.getModuleName());
			}
			//Plant Evnironment
			{
				O2AirConcentrationSensorImpl myO2AirConcentrationSensorImpl = new O2AirConcentrationSensorImpl(id);
				CO2AirConcentrationSensorImpl myCO2AirConcentrationSensorImpl = new CO2AirConcentrationSensorImpl(id);
				OtherAirConcentrationSensorImpl myOtherAirConcentrationSensorImpl = new OtherAirConcentrationSensorImpl(id);
				registerServer(new O2AirConcentrationSensorPOATie(myO2AirConcentrationSensorImpl), "PlantEnvironment" + myO2AirConcentrationSensorImpl.getModuleName());
				registerServer(new CO2AirConcentrationSensorPOATie(myCO2AirConcentrationSensorImpl), "PlantEnvironment" + myCO2AirConcentrationSensorImpl.getModuleName());
				registerServer(new OtherAirConcentrationSensorPOATie(myOtherAirConcentrationSensorImpl), "PlantEnvironment" + myOtherAirConcentrationSensorImpl.getModuleName());
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

		//
		//Actuator
		//

		//Air Sesnors
		{
			//AirRS
			{
				PowerInFlowRateActuatorImpl myPowerInFlowRateActuatorImpl = new PowerInFlowRateActuatorImpl(id);
				AirInFlowRateActuatorImpl myAirInFlowRateActuatorImpl = new AirInFlowRateActuatorImpl(id);
				AirOutFlowRateActuatorImpl myAirOutFlowRateActuatorImpl = new AirOutFlowRateActuatorImpl(id);
				O2OutFlowRateActuatorImpl myO2OutFlowRateActuatorImpl = new O2OutFlowRateActuatorImpl(id);
				CO2OutFlowRateActuatorImpl myCO2OutFlowRateActuatorImpl = new CO2OutFlowRateActuatorImpl(id);
				CO2InFlowRateActuatorImpl myCO2InFlowRateActuatorImpl = new CO2InFlowRateActuatorImpl(id);
				registerServer(new PowerInFlowRateActuatorPOATie(myPowerInFlowRateActuatorImpl), "AirRS" + myPowerInFlowRateActuatorImpl.getModuleName());
				registerServer(new AirInFlowRateActuatorPOATie(myAirInFlowRateActuatorImpl), "AirRS" + myAirInFlowRateActuatorImpl.getModuleName());
				registerServer(new AirOutFlowRateActuatorPOATie(myAirOutFlowRateActuatorImpl), "AirRS" + myAirOutFlowRateActuatorImpl.getModuleName());
				registerServer(new O2OutFlowRateActuatorPOATie(myO2OutFlowRateActuatorImpl), "AirRS" + myO2OutFlowRateActuatorImpl.getModuleName());
				registerServer(new CO2InFlowRateActuatorPOATie(myCO2InFlowRateActuatorImpl), "AirRS" + myCO2InFlowRateActuatorImpl.getModuleName());
				registerServer(new CO2OutFlowRateActuatorPOATie(myCO2OutFlowRateActuatorImpl), "AirRS" + myCO2OutFlowRateActuatorImpl.getModuleName());
			}
		}

		//Power Actuator
		{
			//Power PS
			{
				PowerOutFlowRateActuatorImpl myPowerOutFlowRateActuatorImpl = new PowerOutFlowRateActuatorImpl(id);
				registerServer(new PowerOutFlowRateActuatorPOATie(myPowerOutFlowRateActuatorImpl), "PowerPS" + myPowerOutFlowRateActuatorImpl.getModuleName());
			}
		}

		//Food Actuator
		{
			//Food Processor
			{
				PowerInFlowRateActuatorImpl myPowerInFlowRateActuatorImpl = new PowerInFlowRateActuatorImpl(id);
				BiomassInFlowRateActuatorImpl myBiomassInFlowRateActuatorImpl = new BiomassInFlowRateActuatorImpl(id);
				FoodOutFlowRateActuatorImpl myFoodOutFlowRateActuatorImpl = new FoodOutFlowRateActuatorImpl(id);
				registerServer(new PowerInFlowRateActuatorPOATie(myPowerInFlowRateActuatorImpl), "FoodProcessor" + myPowerInFlowRateActuatorImpl.getModuleName());
				registerServer(new BiomassInFlowRateActuatorPOATie(myBiomassInFlowRateActuatorImpl), "FoodProcessor" + myBiomassInFlowRateActuatorImpl.getModuleName());
				registerServer(new FoodOutFlowRateActuatorPOATie(myFoodOutFlowRateActuatorImpl), "FoodProcessor" + myFoodOutFlowRateActuatorImpl.getModuleName());
			}
			//BiomassRS
			{
				PowerInFlowRateActuatorImpl myPowerInFlowRateActuatorImpl = new PowerInFlowRateActuatorImpl(id);
				BiomassOutFlowRateActuatorImpl myBiomassOutFlowRateActuatorImpl = new BiomassOutFlowRateActuatorImpl(id);
				FoodOutFlowRateActuatorImpl myFoodOutFlowRateActuatorImpl = new FoodOutFlowRateActuatorImpl(id);
				AirInFlowRateActuatorImpl myAirInFlowRateActuatorImpl = new AirInFlowRateActuatorImpl(id);
				AirOutFlowRateActuatorImpl myAirOutFlowRateActuatorImpl = new AirOutFlowRateActuatorImpl(id);
				PotableWaterInFlowRateActuatorImpl myPotableWaterInFlowRateActuatorImpl = new PotableWaterInFlowRateActuatorImpl(id);
				GreyWaterInFlowRateActuatorImpl myGreyWaterInFlowRateActuatorImpl = new GreyWaterInFlowRateActuatorImpl(id);
				registerServer(new PowerInFlowRateActuatorPOATie(myPowerInFlowRateActuatorImpl), "BiomassRS" + myPowerInFlowRateActuatorImpl.getModuleName());
				registerServer(new BiomassOutFlowRateActuatorPOATie(myBiomassOutFlowRateActuatorImpl), "BiomassRS" + myBiomassOutFlowRateActuatorImpl.getModuleName());
				registerServer(new FoodOutFlowRateActuatorPOATie(myFoodOutFlowRateActuatorImpl), "BiomassRS" + myFoodOutFlowRateActuatorImpl.getModuleName());
				registerServer(new AirInFlowRateActuatorPOATie(myAirInFlowRateActuatorImpl), "BiomassRS" + myAirInFlowRateActuatorImpl.getModuleName());
				registerServer(new AirOutFlowRateActuatorPOATie(myAirOutFlowRateActuatorImpl), "BiomassRS" + myAirOutFlowRateActuatorImpl.getModuleName());
				registerServer(new PotableWaterInFlowRateActuatorPOATie(myPotableWaterInFlowRateActuatorImpl), "BiomassRS" + myPotableWaterInFlowRateActuatorImpl.getModuleName());
				registerServer(new GreyWaterInFlowRateActuatorPOATie(myGreyWaterInFlowRateActuatorImpl), "BiomassRS" + myGreyWaterInFlowRateActuatorImpl.getModuleName());
			}
		}
		
		//Crew Actuators
		{
			PotableWaterInFlowRateActuatorImpl myPotableWaterInFlowRateActuatorImpl = new PotableWaterInFlowRateActuatorImpl(id);
			registerServer(new PotableWaterInFlowRateActuatorPOATie(myPotableWaterInFlowRateActuatorImpl), "CrewGroup" + myPotableWaterInFlowRateActuatorImpl.getModuleName());
			DirtyWaterOutFlowRateActuatorImpl myDirtyWaterOutFlowRateActuatorImpl = new DirtyWaterOutFlowRateActuatorImpl(id);
			registerServer(new DirtyWaterOutFlowRateActuatorPOATie(myDirtyWaterOutFlowRateActuatorImpl), "CrewGroup" + myDirtyWaterOutFlowRateActuatorImpl.getModuleName());
			GreyWaterOutFlowRateActuatorImpl myGreyWaterOutFlowRateActuatorImpl = new GreyWaterOutFlowRateActuatorImpl(id);
			registerServer(new GreyWaterOutFlowRateActuatorPOATie(myGreyWaterOutFlowRateActuatorImpl), "CrewGroup" + myGreyWaterOutFlowRateActuatorImpl.getModuleName());
		}

		//
		//Water Actuator
		//
		{
			//Water RS
			{
				PowerInFlowRateActuatorImpl myPowerInFlowRateActuatorImpl = new PowerInFlowRateActuatorImpl(id);
				GreyWaterInFlowRateActuatorImpl myGreyWaterInFlowRateActuatorImpl = new GreyWaterInFlowRateActuatorImpl(id);
				DirtyWaterInFlowRateActuatorImpl myDirtyWaterInFlowRateActuatorImpl = new DirtyWaterInFlowRateActuatorImpl(id);
				PotableWaterOutFlowRateActuatorImpl myPotableWaterOutFlowRateActuatorImpl = new PotableWaterOutFlowRateActuatorImpl(id);
				registerServer(new PowerInFlowRateActuatorPOATie(myPowerInFlowRateActuatorImpl), "WaterRS" + myPowerInFlowRateActuatorImpl.getModuleName());
				registerServer(new GreyWaterInFlowRateActuatorPOATie(myGreyWaterInFlowRateActuatorImpl), "WaterRS" + myGreyWaterInFlowRateActuatorImpl.getModuleName());
				registerServer(new DirtyWaterInFlowRateActuatorPOATie(myDirtyWaterInFlowRateActuatorImpl), "WaterRS" + myDirtyWaterInFlowRateActuatorImpl.getModuleName());
				registerServer(new PotableWaterOutFlowRateActuatorPOATie(myPotableWaterOutFlowRateActuatorImpl), "WaterRS" + myPotableWaterOutFlowRateActuatorImpl.getModuleName());
			}
		}

		//
		//Framework Actuator
		//
		{
			//Accumulator
			{
				CO2AirEnvironmentInFlowRateActuatorImpl myCO2AirEnvironmentInFlowRateActuatorImpl = new CO2AirEnvironmentInFlowRateActuatorImpl(id);
				CO2AirStoreOutFlowRateActuatorImpl myCO2AirStoreOutFlowRateActuatorImpl = new CO2AirStoreOutFlowRateActuatorImpl(id);
				O2AirEnvironmentInFlowRateActuatorImpl myO2AirEnvironmentInFlowRateActuatorImpl = new O2AirEnvironmentInFlowRateActuatorImpl(id);
				O2AirStoreOutFlowRateActuatorImpl myO2AirStoreOutFlowRateActuatorImpl = new O2AirStoreOutFlowRateActuatorImpl(id);
				registerServer(new CO2AirStoreOutFlowRateActuatorPOATie(myCO2AirStoreOutFlowRateActuatorImpl), "Accumulator" + myCO2AirStoreOutFlowRateActuatorImpl.getModuleName());
				registerServer(new CO2AirEnvironmentInFlowRateActuatorPOATie(myCO2AirEnvironmentInFlowRateActuatorImpl), "Accumulator" + myCO2AirEnvironmentInFlowRateActuatorImpl.getModuleName());
				registerServer(new O2AirStoreOutFlowRateActuatorPOATie(myO2AirStoreOutFlowRateActuatorImpl), "Accumulator" + myO2AirStoreOutFlowRateActuatorImpl.getModuleName());
				registerServer(new O2AirEnvironmentInFlowRateActuatorPOATie(myO2AirEnvironmentInFlowRateActuatorImpl), "Accumulator" + myO2AirEnvironmentInFlowRateActuatorImpl.getModuleName());
			}
			//Injector
			{
				CO2AirEnvironmentOutFlowRateActuatorImpl myCO2AirEnvironmentOutFlowRateActuatorImpl = new CO2AirEnvironmentOutFlowRateActuatorImpl(id);
				CO2AirStoreInFlowRateActuatorImpl myCO2AirStoreInFlowRateActuatorImpl = new CO2AirStoreInFlowRateActuatorImpl(id);
				O2AirEnvironmentOutFlowRateActuatorImpl myO2AirEnvironmentOutFlowRateActuatorImpl = new O2AirEnvironmentOutFlowRateActuatorImpl(id);
				O2AirStoreInFlowRateActuatorImpl myO2AirStoreInFlowRateActuatorImpl = new O2AirStoreInFlowRateActuatorImpl(id);
				registerServer(new CO2AirStoreInFlowRateActuatorPOATie(myCO2AirStoreInFlowRateActuatorImpl), "Injector" + myCO2AirStoreInFlowRateActuatorImpl.getModuleName());
				registerServer(new CO2AirEnvironmentOutFlowRateActuatorPOATie(myCO2AirEnvironmentOutFlowRateActuatorImpl), "Injector" + myCO2AirEnvironmentOutFlowRateActuatorImpl.getModuleName());
				registerServer(new O2AirStoreInFlowRateActuatorPOATie(myO2AirStoreInFlowRateActuatorImpl), "Injector" + myO2AirStoreInFlowRateActuatorImpl.getModuleName());
				registerServer(new O2AirEnvironmentOutFlowRateActuatorPOATie(myO2AirEnvironmentOutFlowRateActuatorImpl), "Injector" + myO2AirEnvironmentOutFlowRateActuatorImpl.getModuleName());
			}
		}
	}
}

