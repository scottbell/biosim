package biosim.server.framework;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.water.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.crew.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.water.*;
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

	//
	//framework
	private String myLoggerName;
	//

	//
	//simulation
	//
	private String myCrewName;
	private String myPowerPSName;
	private String myPowerStoreName;
	private String myAirRSName;
	private String myCO2StoreName;
	private String myO2StoreName;
	private String myBiomassRSName;
	private String myBiomassStoreName;
	private String myFoodProcessorName;
	private String myFoodStoreName;
	private String myWaterRSName;
	private String myDirtyWaterStoreName;
	private String myPotableWaterStoreName;
	private String myGreyWaterStoreName;
	private String myCrewEnvironmentName;
	private String myPlantEnvironmentName;
	private String myAccumulatorName;
	private String myInjectorName;

	//
	//sensor
	//
	//Air
	//AirRs
	private String myAirRSPowerInFlowRateSensorName;
	private String myAirRSAirInFlowRateSensorName;
	private String myAirRSAirOutFlowRateSensorName;
	private String myAirRSO2OutFlowRateSensorName;
	private String myAirRSCO2InFlowRateSensorName;
	private String myAirRSCO2OutFlowRateSensorName;
	//Stores
	private String myO2StoreLevelSensorName;
	private String myCO2StoreLevelSensorName;
	//Power
	//PowerPS
	private String myPowerPSPowerOutFlowRateSensorName;
	//Stores
	private String myPowerStoreLevelSensorName;
	//Environment
	//Crew
	private String myCrewEnvironmentOtherAirMolesSensorName;
	private String myCrewEnvironmentCO2AirMolesSensorName;
	private String myCrewEnvironmentO2AirMolesSensorName;
	//Plant
	private String myPlantEnvironmentOtherAirMolesSensorName;
	private String myPlantEnvironmentCO2AirMolesSensorName;
	private String myPlantEnvironmentO2AirMolesSensorName;
	//Water
	//WaterRS
	private String myWaterRSDirtyWaterInFlowRateSensorName;
	private String myWaterRSGreyWaterInFlowRateSensorName;
	private String myWaterRSPowerInFlowRateSensorName;
	private String myWaterRSPotableWaterOutFlowRateSensorName;
	//Stores
	private String myPotableWaterStoreLevelSensorName;
	private String myGreyWaterStoreLevelSensorName;
	private String myDirtyWaterStoreLevelSensorName;
	//Food
	//BiomassRS
	private String myBiomassRSAirInFlowRateSensorName;
	private String myBiomassRSAirOutFlowRateSensorName;
	private String myBiomassRSPotableWaterInFlowRateSensorName;
	private String myBiomassRSGreyWaterInFlowRateSensorName;
	private String myBiomassRSBiomassOutFlowRateSensorName;
	private String myBiomassRSPowerInFlowRateSensorName;
	//Food Processor
	private String myFoodProcessorPowerInFlowRateSensorName;
	private String myFoodProcessorBiomassInFlowRateSensorName;
	private String myFoodProcessorFoodOutFlowRateSensorName;
	//Stores
	private String myBiomassStoreLevelSensorName;
	private String myFoodStoreLevelSensorName;
	//Framework
	//Accumulator
	private String myAccumulatorCO2AirEnvironmentInFlowRateSensorName;
	private String myAccumulatorO2AirEnvironmentInFlowRateSensorName;
	private String myAccumulatorCO2AirStoreOutFlowRateSensorName;
	private String myAccumulatorO2AirStoreOutFlowRateSensorName;
	//Injector
	private String myInjectorCO2AirStoreInFlowRateSensorName;
	private String myInjectorO2AirStoreInFlowRateSensorName;
	private String myInjectorCO2AirEnvironmentOutFlowRateSensorName;
	private String myInjectorO2AirEnvironmentOutFlowRateSensorName;
	
	//
	//actuator
	//
	//Air
	//AirRs
	private String myAirRSPowerInFlowRateActuatorName;
	private String myAirRSAirInFlowRateActuatorName;
	private String myAirRSAirOutFlowRateActuatorName;
	private String myAirRSO2OutFlowRateActuatorName;
	private String myAirRSCO2InFlowRateActuatorName;
	private String myAirRSCO2OutFlowRateActuatorName;
	//Stores
	private String myO2StoreLevelActuatorName;
	private String myCO2StoreLevelActuatorName;
	//Power
	//PowerPS
	private String myPowerPSPowerOutFlowRateActuatorName;
	//Stores
	private String myPowerStoreLevelActuatorName;
	//Environment
	//Crew
	private String myCrewEnvironmentOtherAirMolesActuatorName;
	private String myCrewEnvironmentCO2AirMolesActuatorName;
	private String myCrewEnvironmentO2AirMolesActuatorName;
	//Plant
	private String myPlantEnvironmentOtherAirMolesActuatorName;
	private String myPlantEnvironmentCO2AirMolesActuatorName;
	private String myPlantEnvironmentO2AirMolesActuatorName;
	//Water
	//WaterRS
	private String myWaterRSDirtyWaterInFlowRateActuatorName;
	private String myWaterRSGreyWaterInFlowRateActuatorName;
	private String myWaterRSPowerInFlowRateActuatorName;
	private String myWaterRSPotableWaterOutFlowRateActuatorName;
	//Stores
	private String myPotableWaterStoreLevelActuatorName;
	private String myGreyWaterStoreLevelActuatorName;
	private String myDirtyWaterStoreLevelActuatorName;
	//Food
	//BiomassRS
	private String myBiomassRSAirInFlowRateActuatorName;
	private String myBiomassRSAirOutFlowRateActuatorName;
	private String myBiomassRSPotableWaterInFlowRateActuatorName;
	private String myBiomassRSGreyWaterInFlowRateActuatorName;
	private String myBiomassRSBiomassOutFlowRateActuatorName;
	private String myBiomassRSPowerInFlowRateActuatorName;
	//Food Processor
	private String myFoodProcessorPowerInFlowRateActuatorName;
	private String myFoodProcessorBiomassInFlowRateActuatorName;
	private String myFoodProcessorFoodOutFlowRateActuatorName;
	//Stores
	private String myBiomassStoreLevelActuatorName;
	private String myFoodStoreLevelActuatorName;
	//Framework
	//Accumulator
	private String myAccumulatorCO2AirEnvironmentInFlowRateActuatorName;
	private String myAccumulatorO2AirEnvironmentInFlowRateActuatorName;
	private String myAccumulatorCO2AirStoreOutFlowRateActuatorName;
	private String myAccumulatorO2AirStoreOutFlowRateActuatorName;
	//Injector
	private String myInjectorCO2AirStoreInFlowRateActuatorName;
	private String myInjectorO2AirStoreInFlowRateActuatorName;
	private String myInjectorCO2AirEnvironmentOutFlowRateActuatorName;
	private String myInjectorO2AirEnvironmentOutFlowRateActuatorName;

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
		myModuleNames = new String[102];
		//
		//framework
		//
		myLoggerName = "Logger"+myID;

		//
		//simulation
		//
		myModuleNames[0] = myCrewName = "CrewGroup"+myID;
		myModuleNames[1] = myPowerPSName = "PowerPS"+myID;
		myModuleNames[2] = myPowerStoreName = "PowerStore"+myID;
		myModuleNames[3] = myAirRSName = "AirRS"+myID;
		myModuleNames[4] = myCO2StoreName = "CO2Store"+myID;
		myModuleNames[5] = myO2StoreName = "O2Store"+myID;
		myModuleNames[6] = myBiomassRSName = "BiomassRS"+myID;
		myModuleNames[7] = myBiomassStoreName = "BiomassStore"+myID;
		myModuleNames[8] = myFoodProcessorName = "FoodProcessor"+myID;
		myModuleNames[9] = myFoodStoreName = "FoodStore"+myID;
		myModuleNames[10] = myWaterRSName = "WaterRS"+myID;
		myModuleNames[11] = myDirtyWaterStoreName = "DirtyWaterStore"+myID;
		myModuleNames[12] = myPotableWaterStoreName = "PotableWaterStore"+myID;
		myModuleNames[13] = myGreyWaterStoreName = "GreyWaterStore"+myID;
		myModuleNames[14] = myCrewEnvironmentName = "CrewEnvironment"+myID;
		myModuleNames[15] = myPlantEnvironmentName = "PlantEnvironment"+myID;
		myModuleNames[16] = myAccumulatorName = "Accumulator"+myID;
		myModuleNames[17] = myInjectorName = "Injector"+myID;

		//
		//sensors
		//
		//Air
		//AirRS
		myModuleNames[18] = myAirRSPowerInFlowRateSensorName = "AirRSPowerInFlowRateSensor"+myID;
		myModuleNames[19] = myAirRSAirInFlowRateSensorName = "AirRSAirInFlowRateSensor"+myID;
		myModuleNames[20] = myAirRSAirOutFlowRateSensorName = "AirRSAirOutFlowRateSensor"+myID;
		myModuleNames[21] = myAirRSO2OutFlowRateSensorName = "AirRSO2OutFlowRateSensor"+myID;
		myModuleNames[22] = myAirRSCO2InFlowRateSensorName = "AirRSCO2InFlowRateSensor"+myID;
		myModuleNames[23] = myAirRSCO2OutFlowRateSensorName = "AirRSCO2OutFlowRateSensor"+myID;
		//Stores
		myModuleNames[24] = myO2StoreLevelSensorName = "O2StoreLevelSensor"+myID;
		myModuleNames[25] = myCO2StoreLevelSensorName = "CO2StoreLevelSensor"+myID;
		//Power
		//PowerPS
		myModuleNames[26] = myPowerPSPowerOutFlowRateSensorName = "PowerPSPowerOutFlowRateSensor"+myID;
		//Stores
		myModuleNames[27] = myPowerStoreLevelSensorName = "PowerStoreLevelSensor"+myID;
		//Environment
		//Crew
		myModuleNames[28] = myCrewEnvironmentOtherAirMolesSensorName = "CrewEnvironmentOtherAirMolesSensor"+myID;
		myModuleNames[29] = myCrewEnvironmentCO2AirMolesSensorName = "CrewEnvironmentCO2AirMolesSensor"+myID;
		myModuleNames[30] = myCrewEnvironmentO2AirMolesSensorName = "CrewEnvironmentO2AirMolesSensor"+myID;
		//Plant
		myModuleNames[31] = myPlantEnvironmentOtherAirMolesSensorName = "PlantEnvironmentOtherAirMolesSensor"+myID;
		myModuleNames[32] = myPlantEnvironmentCO2AirMolesSensorName = "PlantEnvironmentCO2AirMolesSensor"+myID;
		myModuleNames[33] = myPlantEnvironmentO2AirMolesSensorName = "PlantEnvironmentO2AirMolesSensor"+myID;
		//Water
		//WaterRS
		myModuleNames[34] = myWaterRSDirtyWaterInFlowRateSensorName = "WaterRSDirtyWaterInFlowRateSensor"+myID;
		myModuleNames[35] = myWaterRSGreyWaterInFlowRateSensorName = "WaterRSGreyWaterInFlowRateSensor"+myID;
		myModuleNames[36] = myWaterRSPowerInFlowRateSensorName = "WaterRSPowerInFlowRateSensor"+myID;
		myModuleNames[37] = myWaterRSPotableWaterOutFlowRateSensorName = "WaterRSPotableWaterOutFlowRateSensor"+myID;
		//Stores
		myModuleNames[38] = myPotableWaterStoreLevelSensorName = "PotableWaterStoreLevelSensor"+myID;
		myModuleNames[39] = myGreyWaterStoreLevelSensorName = "GreyWaterStoreLevelSensor"+myID;
		myModuleNames[40] = myDirtyWaterStoreLevelSensorName = "DirtyWaterStoreLevelSensor"+myID;
		//Food
		//BiomassRS
		myModuleNames[41] = myBiomassRSAirInFlowRateSensorName = "BiomassRSAirInFlowRateSensor"+myID;
		myModuleNames[42] = myBiomassRSPowerInFlowRateSensorName = "BiomassRSPowerInFlowRateSensor"+myID;
		myModuleNames[43] = myBiomassRSAirOutFlowRateSensorName = "BiomassRSAirOutFlowRateSensor"+myID;
		myModuleNames[44] = myBiomassRSPotableWaterInFlowRateSensorName = "BiomassRSPotableWaterInFlowRateSensor"+myID;
		myModuleNames[45] = myBiomassRSGreyWaterInFlowRateSensorName = "BiomassRSGreyWaterInFlowRateSensor"+myID;
		myModuleNames[46] = myBiomassRSBiomassOutFlowRateSensorName = "BiomassRSBiomassOutFlowRateSensor"+myID;
		//Food Processor
		myModuleNames[47] = myFoodProcessorPowerInFlowRateSensorName = "FoodProcessorPowerInFlowRateSensor"+myID;
		myModuleNames[48] = myFoodProcessorBiomassInFlowRateSensorName = "FoodProcessorBiomassInFlowRateSensor"+myID;
		myModuleNames[49] = myFoodProcessorFoodOutFlowRateSensorName = "FoodProcessorFoodOutFlowRateSensor"+myID;
		//Stores
		myModuleNames[50] = myBiomassStoreLevelSensorName = "BiomassStoreLevelSensor"+myID;
		myModuleNames[51] = myFoodStoreLevelSensorName = "FoodStoreLevelSensor"+myID;
		//Framework
		//Accumulator
		myModuleNames[52] = myAccumulatorCO2AirEnvironmentInFlowRateSensorName = "AccumulatorCO2AirEnvironmentInFlowRateSensor"+myID;
		myModuleNames[53] = myAccumulatorO2AirEnvironmentInFlowRateSensorName = "AccumulatorO2AirEnvironmentInFlowRateSensor"+myID;
		myModuleNames[54] = myAccumulatorCO2AirStoreOutFlowRateSensorName = "AccumulatorCO2AirStoreOutFlowRateSensor"+myID;
		myModuleNames[55] = myAccumulatorO2AirStoreOutFlowRateSensorName = "AccumulatorO2AirStoreOutFlowRateSensor"+myID;
		//Injector
		myModuleNames[56] = myInjectorCO2AirStoreInFlowRateSensorName = "InjectorCO2AirStoreInFlowRateSensor"+myID;
		myModuleNames[57] = myInjectorO2AirStoreInFlowRateSensorName = "InjectorO2AirStoreInFlowRateSensor"+myID;
		myModuleNames[58] = myInjectorCO2AirEnvironmentOutFlowRateSensorName = "InjectorCO2AirEnvironmentOutFlowRateSensor"+myID;
		myModuleNames[59] = myInjectorO2AirEnvironmentOutFlowRateSensorName = "InjectorO2AirEnvironmentOutFlowRateSensor"+myID;
		
		//
		//actuators
		//
		//Air
		//AirRS
		myModuleNames[60] = myAirRSPowerInFlowRateActuatorName = "AirRSPowerInFlowRateActuator"+myID;
		myModuleNames[61] = myAirRSAirInFlowRateActuatorName = "AirRSAirInFlowRateActuator"+myID;
		myModuleNames[62] = myAirRSAirOutFlowRateActuatorName = "AirRSAirOutFlowRateActuator"+myID;
		myModuleNames[63] = myAirRSO2OutFlowRateActuatorName = "AirRSO2OutFlowRateActuator"+myID;
		myModuleNames[64] = myAirRSCO2InFlowRateActuatorName = "AirRSCO2InFlowRateActuator"+myID;
		myModuleNames[65] = myAirRSCO2OutFlowRateActuatorName = "AirRSCO2OutFlowRateActuator"+myID;
		//Stores
		myModuleNames[66] = myO2StoreLevelActuatorName = "O2StoreLevelActuator"+myID;
		myModuleNames[67] = myCO2StoreLevelActuatorName = "CO2StoreLevelActuator"+myID;
		//Power
		//PowerPS
		myModuleNames[68] = myPowerPSPowerOutFlowRateActuatorName = "PowerPSPowerOutFlowRateActuator"+myID;
		//Stores
		myModuleNames[69] = myPowerStoreLevelActuatorName = "PowerStoreLevelActuator"+myID;
		//Environment
		//Crew
		myModuleNames[70] = myCrewEnvironmentOtherAirMolesActuatorName = "CrewEnvironmentOtherAirMolesActuator"+myID;
		myModuleNames[71] = myCrewEnvironmentCO2AirMolesActuatorName = "CrewEnvironmentCO2AirMolesActuator"+myID;
		myModuleNames[72] = myCrewEnvironmentO2AirMolesActuatorName = "CrewEnvironmentO2AirMolesActuator"+myID;
		//Plant
		myModuleNames[73] = myPlantEnvironmentOtherAirMolesActuatorName = "PlantEnvironmentOtherAirMolesActuator"+myID;
		myModuleNames[74] = myPlantEnvironmentCO2AirMolesActuatorName = "PlantEnvironmentCO2AirMolesActuator"+myID;
		myModuleNames[75] = myPlantEnvironmentO2AirMolesActuatorName = "PlantEnvironmentO2AirMolesActuator"+myID;
		//Water
		//WaterRS
		myModuleNames[76] = myWaterRSDirtyWaterInFlowRateActuatorName = "WaterRSDirtyWaterInFlowRateActuator"+myID;
		myModuleNames[77] = myWaterRSGreyWaterInFlowRateActuatorName = "WaterRSGreyWaterInFlowRateActuator"+myID;
		myModuleNames[78] = myWaterRSPowerInFlowRateActuatorName = "WaterRSPowerInFlowRateActuator"+myID;
		myModuleNames[79] = myWaterRSPotableWaterOutFlowRateActuatorName = "WaterRSPotableWaterOutFlowRateActuator"+myID;
		//Stores
		myModuleNames[80] = myPotableWaterStoreLevelActuatorName = "PotableWaterStoreLevelActuator"+myID;
		myModuleNames[81] = myGreyWaterStoreLevelActuatorName = "GreyWaterStoreLevelActuator"+myID;
		myModuleNames[82] = myDirtyWaterStoreLevelActuatorName = "DirtyWaterStoreLevelActuator"+myID;
		//Food
		//BiomassRS
		myModuleNames[83] = myBiomassRSAirInFlowRateActuatorName = "BiomassRSAirInFlowRateActuator"+myID;
		myModuleNames[84] = myBiomassRSPowerInFlowRateActuatorName = "BiomassRSPowerInFlowRateActuator"+myID;
		myModuleNames[85] = myBiomassRSAirOutFlowRateActuatorName = "BiomassRSAirOutFlowRateActuator"+myID;
		myModuleNames[86] = myBiomassRSPotableWaterInFlowRateActuatorName = "BiomassRSPotableWaterInFlowRateActuator"+myID;
		myModuleNames[87] = myBiomassRSGreyWaterInFlowRateActuatorName = "BiomassRSGreyWaterInFlowRateActuator"+myID;
		myModuleNames[88] = myBiomassRSBiomassOutFlowRateActuatorName = "BiomassRSBiomassOutFlowRateActuator"+myID;
		//Food Processor
		myModuleNames[89] = myFoodProcessorPowerInFlowRateActuatorName = "FoodProcessorPowerInFlowRateActuator"+myID;
		myModuleNames[90] = myFoodProcessorBiomassInFlowRateActuatorName = "FoodProcessorBiomassInFlowRateActuator"+myID;
		myModuleNames[91] = myFoodProcessorFoodOutFlowRateActuatorName = "FoodProcessorFoodOutFlowRateActuator"+myID;
		//Stores
		myModuleNames[92] = myBiomassStoreLevelActuatorName = "BiomassStoreLevelActuator"+myID;
		myModuleNames[93] = myFoodStoreLevelActuatorName = "FoodStoreLevelActuator"+myID;
		//Framework
		//Accumulator
		myModuleNames[94] = myAccumulatorCO2AirEnvironmentInFlowRateActuatorName = "AccumulatorCO2AirEnvironmentInFlowRateActuator"+myID;
		myModuleNames[95] = myAccumulatorO2AirEnvironmentInFlowRateActuatorName = "AccumulatorO2AirEnvironmentInFlowRateActuator"+myID;
		myModuleNames[96] = myAccumulatorCO2AirStoreOutFlowRateActuatorName = "AccumulatorCO2AirStoreOutFlowRateActuator"+myID;
		myModuleNames[97] = myAccumulatorO2AirStoreOutFlowRateActuatorName = "AccumulatorO2AirStoreOutFlowRateActuator"+myID;
		//Injector
		myModuleNames[98] = myInjectorCO2AirStoreInFlowRateActuatorName = "InjectorCO2AirStoreInFlowRateActuator"+myID;
		myModuleNames[99] = myInjectorO2AirStoreInFlowRateActuatorName = "InjectorO2AirStoreInFlowRateActuator"+myID;
		myModuleNames[100] = myInjectorCO2AirEnvironmentOutFlowRateActuatorName = "InjectorCO2AirEnvironmentOutFlowRateActuator"+myID;
		myModuleNames[101] = myInjectorO2AirEnvironmentOutFlowRateActuatorName = "InjectorO2AirEnvironmentOutFlowRateActuator"+myID;
		
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
		myLoggerName = "Logger"+myID;
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
		}
		configureModuleFlows();
		configureSensorsInputs();
		configureActuatorsOutputs();
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
		CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(myCrewName));
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
		DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(myDirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(myPotableWaterStoreName));
		GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(myGreyWaterStoreName));
		myDirtyWaterStore.setCapacity(10000f);
		myDirtyWaterStore.setLevel(0f);
		myPotableWaterStore.setCapacity(10000f);
		myPotableWaterStore.setLevel(10000f);
		myGreyWaterStore.setCapacity(10000f);
		myGreyWaterStore.setLevel(10000f);

		//Fill the air tanks
		CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(myCO2StoreName));
		O2Store myO2Store = O2StoreHelper.narrow(getBioModule(myO2StoreName));
		myCO2Store.setCapacity(1000f);
		myO2Store.setCapacity(1000f);
		myCO2Store.setLevel(500f);
		myO2Store.setLevel(250f);

		//Put some air in the crew cabin
		SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(myCrewEnvironmentName));
		float environmentCapacity = 1.54893f * pow(10, 6);
		myCrewEnvironment.setInitialVolumeAtSeaLevel(environmentCapacity);

		//Put some air in the plant cabin
		SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(myPlantEnvironmentName));
		myPlantEnvironment.setInitialVolumeAtSeaLevel(environmentCapacity);

		//Add some crops and food
		BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(myBiomassStoreName));
		FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(myFoodStoreName));
		BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(myBiomassRSName));
		myBiomassRS.createNewShelf(PlantType.WHEAT, 10);
		myBiomassStore.setCapacity(500f);
		myFoodStore.setCapacity(2000f);
		myBiomassStore.setLevel(300f);
		myFoodStore.setLevel(2000f);

		//Add some power
		PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(myPowerStoreName));
		myPowerStore.setCapacity(10000f);
		myPowerStore.setLevel(10000f);
	}
	
	private float pow(float a, float b){
		return (new Double(Math.pow(a,b))).floatValue();
	}

	/**
	* Configures the simulation.  By default, 2 environments are used along with one module of everything else.
	*/
	private void configureModuleFlows(){
		BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(myBiomassStoreName));
		PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(myPowerStoreName));
		FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(myFoodStoreName));
		PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(myPotableWaterStoreName));
		DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(myDirtyWaterStoreName));
		GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(myGreyWaterStoreName));
		SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(myCrewEnvironmentName));
		SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(myPlantEnvironmentName));
		O2Store myO2Store = O2StoreHelper.narrow(getBioModule(myO2StoreName));
		CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(myCO2StoreName));

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
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(getBioModule(myFoodProcessorName));
			myFoodProcessor.setBiomassInputs(biomassStoreInput, biomassMaxFlowRates, biomassActualFlowRates);
			myFoodProcessor.setPowerInputs(powerStoreInput, powerMaxFlowRates, powerActualFlowRates);
			myFoodProcessor.setFoodOutputs(foodStoreOutput, foodMaxFlowRates, foodActualFlowRates);
		}

		//Hook up BiomassRS to other modules
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
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(myBiomassRSName));
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
			float[] powerMaxFlowRates = {0f};
			float[] simEnvironmentInputMaxFlowRates = {0f};
			float[] simEnvironmentOutputMaxFlowRates = {0f};
			float[] O2StoreMaxFlowRates = {0f};
			float[] CO2StoreInputMaxFlowRates = {0f};
			float[] CO2StoreOutputMaxFlowRates = {0f};
			float[] powerActualFlowRates = {0f};
			float[] simEnvironmentInputActualFlowRates = {0f};
			float[] simEnvironmentOutputActualFlowRates = {0f};
			float[] O2StoreActualFlowRates = {0f};
			float[] CO2StoreInputActualFlowRates = {0f};
			float[] CO2StoreOutputActualFlowRates = {0f};
			AirRS myAirRS = AirRSHelper.narrow(getBioModule(myAirRSName));
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
			CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(myCrewName));
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
			WaterRS myWaterRS = WaterRSHelper.narrow(getBioModule(myWaterRSName));
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
			PowerPS myPowerPS = PowerPSHelper.narrow(getBioModule(myPowerPSName));
			myPowerPS.setPowerOutputs(powerOutput, powerOuputsMaxFlowRates, powerOuputsActualFlowRates);
			myPowerPS.setLightInput(lightInput);
		}

		//Hook up Accumulator to other modules
		{
			//Accumulate O2 from plant environment, put it in O2 store
			SimEnvironment[] O2AirInput = {myPlantEnvironment};
			O2Store[] O2AirOutput = {myO2Store};
			float[] O2AirInputMaxFlowRates = {0f};
			float[] O2AirOutputMaxFlowRates = {0f};
			float[] O2AirInputActualFlowRates = {0f};
			float[] O2AirOutputActualFlowRates = {0f};
			Accumulator myAccumulator = AccumulatorHelper.narrow(getBioModule(myAccumulatorName));
			myAccumulator.setO2AirEnvironmentInputs(O2AirInput, O2AirInputMaxFlowRates, O2AirInputActualFlowRates);
			myAccumulator.setO2AirStoreOutputs(O2AirOutput, O2AirOutputMaxFlowRates, O2AirOutputActualFlowRates);

			//Accumulate CO2 from crew environment, put it in CO2 store
			SimEnvironment[] CO2AirInput = {myCrewEnvironment};
			CO2Store[] CO2AirOutput = {myCO2Store};
			float[] CO2AirInputMaxFlowRates = {0f};
			float[] CO2AirOutputMaxFlowRates = {0f};
			float[] CO2AirInputActualFlowRates = {0f};
			float[] CO2AirOutputActualFlowRates = {0f};
			myAccumulator.setCO2AirEnvironmentInputs(CO2AirInput, CO2AirInputMaxFlowRates, CO2AirInputActualFlowRates);
			myAccumulator.setCO2AirStoreOutputs(CO2AirOutput, CO2AirOutputMaxFlowRates, CO2AirOutputActualFlowRates);
		}

		//Hook up Injector to other modules
		{
			//Take O2 from store, inject it into crew environment
			O2Store[] O2AirInput = {myO2Store};
			SimEnvironment[] O2AirOutput = {myCrewEnvironment};
			float[] O2AirInputMaxFlowRates = {0f};
			float[] O2AirOutputMaxFlowRates = {0f};
			float[] O2AirInputActualFlowRates = {0f};
			float[] O2AirOutputActualFlowRates = {0f};
			Injector myInjector = InjectorHelper.narrow(getBioModule(myInjectorName));
			myInjector.setO2AirStoreInputs(O2AirInput, O2AirInputMaxFlowRates, O2AirInputActualFlowRates);
			myInjector.setO2AirEnvironmentOutputs(O2AirOutput, O2AirOutputMaxFlowRates, O2AirOutputActualFlowRates);

			//Take CO2 from store, inject it into plant environment
			CO2Store[] CO2AirInput = {myCO2Store};
			SimEnvironment[] CO2AirOutput = {myPlantEnvironment};
			float[] CO2AirInputMaxFlowRates = {0f};
			float[] CO2AirOutputMaxFlowRates = {0f};
			float[] CO2AirInputActualFlowRates = {0f};
			float[] CO2AirOutputActualFlowRates = {0f};
			myInjector.setCO2AirStoreInputs(CO2AirInput, CO2AirInputMaxFlowRates, CO2AirInputActualFlowRates);
			myInjector.setCO2AirEnvironmentOutputs(CO2AirOutput, CO2AirOutputMaxFlowRates, CO2AirOutputActualFlowRates);
		}
	}

	private void configureSensorsInputs(){
		//
		//Air
		//
		{
			//AirRS
			{

				AirRS myAirRS = AirRSHelper.narrow(getBioModule(myAirRSName));
				PowerInFlowRateSensor myAirRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(getBioModule(myAirRSPowerInFlowRateSensorName));
				AirInFlowRateSensor myAirRSAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(getBioModule(myAirRSAirInFlowRateSensorName));
				AirOutFlowRateSensor myAirRSAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(getBioModule(myAirRSAirOutFlowRateSensorName));
				O2OutFlowRateSensor myAirRSO2OutFlowRateSensor = O2OutFlowRateSensorHelper.narrow(getBioModule(myAirRSO2OutFlowRateSensorName));
				CO2InFlowRateSensor myAirRSCO2InFlowRateSensor = CO2InFlowRateSensorHelper.narrow(getBioModule(myAirRSCO2InFlowRateSensorName));
				CO2OutFlowRateSensor myAirRSCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper.narrow(getBioModule(myAirRSCO2OutFlowRateSensorName));
				myAirRSPowerInFlowRateSensor.setInput(myAirRS, 0);
				myAirRSAirInFlowRateSensor.setInput(myAirRS, 0);
				myAirRSAirOutFlowRateSensor.setInput(myAirRS, 0);
				myAirRSO2OutFlowRateSensor.setInput(myAirRS, 0);
				myAirRSCO2InFlowRateSensor.setInput(myAirRS, 0);
				myAirRSCO2OutFlowRateSensor.setInput(myAirRS, 0);
			}
			//Store
			{

				O2Store myO2Store = O2StoreHelper.narrow(getBioModule(myO2StoreName));
				CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(myCO2StoreName));
				O2StoreLevelSensor myO2StoreLevelSensor = O2StoreLevelSensorHelper.narrow(getBioModule(myO2StoreLevelSensorName));
				CO2StoreLevelSensor myCO2StoreLevelSensor = CO2StoreLevelSensorHelper.narrow(getBioModule(myCO2StoreLevelSensorName));
				myO2StoreLevelSensor.setInput(myO2Store);
				myCO2StoreLevelSensor.setInput(myCO2Store);
			}
		}

		//
		//Power
		//
		{
			//PowerPS
			{

				PowerPS myPowerPS = PowerPSHelper.narrow(getBioModule(myPowerPSName));
				PowerOutFlowRateSensor myPowerPSPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper.narrow(getBioModule(myPowerPSPowerOutFlowRateSensorName));
				myPowerPSPowerOutFlowRateSensor.setInput(myPowerPS, 0);
			}
			//Stores
			{
				PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(myPowerStoreName));
				PowerStoreLevelSensor myPowerStoreLevelSensor = PowerStoreLevelSensorHelper.narrow(getBioModule(myPowerStoreLevelSensorName));
				myPowerStoreLevelSensor.setInput(myPowerStore);
			}
		}

		//
		//Environment
		//
		{
			//Crew
			{
				SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(myCrewEnvironmentName));
				O2AirMolesSensor myCrewEnvironmentO2AirMolesSensor = O2AirMolesSensorHelper.narrow(getBioModule(myCrewEnvironmentO2AirMolesSensorName));
				CO2AirMolesSensor myCrewEnvironmentCO2AirMolesSensor = CO2AirMolesSensorHelper.narrow(getBioModule(myCrewEnvironmentCO2AirMolesSensorName));
				OtherAirMolesSensor myCrewEnvironmentOtherAirMolesSensor = OtherAirMolesSensorHelper.narrow(getBioModule(myCrewEnvironmentOtherAirMolesSensorName));
				myCrewEnvironmentO2AirMolesSensor.setInput(myCrewEnvironment);
				myCrewEnvironmentCO2AirMolesSensor.setInput(myCrewEnvironment);
				myCrewEnvironmentOtherAirMolesSensor.setInput(myCrewEnvironment);
			}
			//Plant
			{
				SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(myPlantEnvironmentName));
				O2AirMolesSensor myPlantEnvironmentO2AirMolesSensor = O2AirMolesSensorHelper.narrow(getBioModule(myPlantEnvironmentO2AirMolesSensorName));
				CO2AirMolesSensor myPlantEnvironmentCO2AirMolesSensor = CO2AirMolesSensorHelper.narrow(getBioModule(myPlantEnvironmentCO2AirMolesSensorName));
				OtherAirMolesSensor myPlantEnvironmentOtherAirMolesSensor = OtherAirMolesSensorHelper.narrow(getBioModule(myPlantEnvironmentOtherAirMolesSensorName));
				myPlantEnvironmentO2AirMolesSensor.setInput(myPlantEnvironment);
				myPlantEnvironmentCO2AirMolesSensor.setInput(myPlantEnvironment);
				myPlantEnvironmentOtherAirMolesSensor.setInput(myPlantEnvironment);
			}
		}
		//
		//Food
		//
		{
			//BiomassRS
			{
				BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(myBiomassRSName));
				AirInFlowRateSensor myBiomassRSAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(getBioModule(myBiomassRSAirInFlowRateSensorName));
				AirOutFlowRateSensor myBiomassRSAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(getBioModule(myBiomassRSAirOutFlowRateSensorName));
				PotableWaterInFlowRateSensor myBiomassRSPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(getBioModule(myBiomassRSPotableWaterInFlowRateSensorName));
				GreyWaterInFlowRateSensor myBiomassRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(getBioModule(myBiomassRSGreyWaterInFlowRateSensorName));
				BiomassOutFlowRateSensor myBiomassRSBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper.narrow(getBioModule(myBiomassRSBiomassOutFlowRateSensorName));
				PowerInFlowRateSensor myBiomassRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(getBioModule(myBiomassRSPowerInFlowRateSensorName));
				myBiomassRSGreyWaterInFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSPotableWaterInFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSAirOutFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSAirInFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSBiomassOutFlowRateSensor.setInput(myBiomassRS, 0);
				myBiomassRSPowerInFlowRateSensor.setInput(myBiomassRS, 0);
			}
			//FoodProcessor
			{
				FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(getBioModule(myFoodProcessorName));
				BiomassInFlowRateSensor myFoodProcessorBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper.narrow(getBioModule(myFoodProcessorBiomassInFlowRateSensorName));
				FoodOutFlowRateSensor myFoodProcessorFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper.narrow(getBioModule(myFoodProcessorFoodOutFlowRateSensorName));
				PowerInFlowRateSensor myFoodProcessorPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(getBioModule(myFoodProcessorPowerInFlowRateSensorName));
				myFoodProcessorPowerInFlowRateSensor.setInput(myFoodProcessor, 0);
				myFoodProcessorFoodOutFlowRateSensor.setInput(myFoodProcessor, 0);
				myFoodProcessorBiomassInFlowRateSensor.setInput(myFoodProcessor, 0);
			}
			//Stores
			{
				FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(myFoodStoreName));
				BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(myBiomassStoreName));
				BiomassStoreLevelSensor myBiomassStoreLevelSensor = BiomassStoreLevelSensorHelper.narrow(getBioModule(myBiomassStoreLevelSensorName));
				FoodStoreLevelSensor myFoodStoreLevelSensor = FoodStoreLevelSensorHelper.narrow(getBioModule(myFoodStoreLevelSensorName));
				myFoodStoreLevelSensor.setInput(myFoodStore);
				myBiomassStoreLevelSensor.setInput(myBiomassStore);
			}
		}

		//
		//Water
		//
		{
			//WaterRS
			{
				WaterRS myWaterRS = WaterRSHelper.narrow(getBioModule(myWaterRSName));
				GreyWaterInFlowRateSensor myWaterRSGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(getBioModule(myWaterRSGreyWaterInFlowRateSensorName));
				DirtyWaterInFlowRateSensor myWaterRSDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper.narrow(getBioModule(myWaterRSDirtyWaterInFlowRateSensorName));
				PotableWaterOutFlowRateSensor myWaterRSPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper.narrow(getBioModule(myWaterRSPotableWaterOutFlowRateSensorName));
				PowerInFlowRateSensor myWaterRSPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(getBioModule(myWaterRSPowerInFlowRateSensorName));
				myWaterRSPotableWaterOutFlowRateSensor.setInput(myWaterRS, 0);
				myWaterRSDirtyWaterInFlowRateSensor.setInput(myWaterRS, 0);
				myWaterRSGreyWaterInFlowRateSensor.setInput(myWaterRS, 0);
				myWaterRSPowerInFlowRateSensor.setInput(myWaterRS, 0);
			}
			//Stores
			{
				PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(myPotableWaterStoreName));
				DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(myDirtyWaterStoreName));
				GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(myGreyWaterStoreName));
				PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = PotableWaterStoreLevelSensorHelper.narrow(getBioModule(myPotableWaterStoreLevelSensorName));
				GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor = GreyWaterStoreLevelSensorHelper.narrow(getBioModule(myGreyWaterStoreLevelSensorName));
				DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor = DirtyWaterStoreLevelSensorHelper.narrow(getBioModule(myDirtyWaterStoreLevelSensorName));
				myPotableWaterStoreLevelSensor.setInput(myPotableWaterStore);
				myGreyWaterStoreLevelSensor.setInput(myGreyWaterStore);
				myDirtyWaterStoreLevelSensor.setInput(myDirtyWaterStore);
			}
		}

		//
		//Framework
		//
		{
			//Accumulator
			{

				Accumulator myAccumulator = AccumulatorHelper.narrow(getBioModule(myAccumulatorName));
				CO2AirStoreOutFlowRateSensor myAccumulatorCO2AirStoreOutFlowRateSensor = CO2AirStoreOutFlowRateSensorHelper.narrow(getBioModule(myAccumulatorCO2AirStoreOutFlowRateSensorName));
				CO2AirEnvironmentInFlowRateSensor myAccumulatorCO2AirEnvironmentInFlowRateSensor = CO2AirEnvironmentInFlowRateSensorHelper.narrow(getBioModule(myAccumulatorCO2AirEnvironmentInFlowRateSensorName));
				O2AirEnvironmentInFlowRateSensor myAccumulatorO2AirEnvironmentInFlowRateSensor = O2AirEnvironmentInFlowRateSensorHelper.narrow(getBioModule(myAccumulatorO2AirEnvironmentInFlowRateSensorName));
				O2AirStoreOutFlowRateSensor myAccumulatorO2AirStoreOutFlowRateSensor = O2AirStoreOutFlowRateSensorHelper.narrow(getBioModule(myAccumulatorO2AirStoreOutFlowRateSensorName));
				myAccumulatorCO2AirEnvironmentInFlowRateSensor.setInput(myAccumulator, 0);
				myAccumulatorO2AirEnvironmentInFlowRateSensor.setInput(myAccumulator, 0);
				myAccumulatorO2AirStoreOutFlowRateSensor.setInput(myAccumulator, 0);
				myAccumulatorCO2AirStoreOutFlowRateSensor.setInput(myAccumulator, 0);
			}
			//Injector
			{
				Injector myInjector = InjectorHelper.narrow(getBioModule(myInjectorName));
				CO2AirStoreInFlowRateSensor myInjectorCO2AirStoreInFlowRateSensor = CO2AirStoreInFlowRateSensorHelper.narrow(getBioModule(myInjectorCO2AirStoreInFlowRateSensorName));
				O2AirStoreInFlowRateSensor myInjectorO2AirStoreInFlowRateSensor = O2AirStoreInFlowRateSensorHelper.narrow(getBioModule(myInjectorO2AirStoreInFlowRateSensorName));
				O2AirEnvironmentOutFlowRateSensor myInjectorO2AirEnvironmentOutFlowRateSensor = O2AirEnvironmentOutFlowRateSensorHelper.narrow(getBioModule(myInjectorO2AirEnvironmentOutFlowRateSensorName));
				CO2AirEnvironmentOutFlowRateSensor myInjectorCO2AirEnvironmentOutFlowRateSensor = CO2AirEnvironmentOutFlowRateSensorHelper.narrow(getBioModule(myInjectorCO2AirEnvironmentOutFlowRateSensorName));
				myInjectorCO2AirStoreInFlowRateSensor.setInput(myInjector, 0);
				myInjectorO2AirStoreInFlowRateSensor.setInput(myInjector, 0);
				myInjectorCO2AirEnvironmentOutFlowRateSensor.setInput(myInjector, 0);
				myInjectorO2AirEnvironmentOutFlowRateSensor.setInput(myInjector, 0);
			}
		}
	}
	
	private void configureActuatorsOutputs(){
		//
		//Air
		//
		{
			//AirRS
			{

				AirRS myAirRS = AirRSHelper.narrow(getBioModule(myAirRSName));
				PowerInFlowRateActuator myAirRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(getBioModule(myAirRSPowerInFlowRateActuatorName));
				AirInFlowRateActuator myAirRSAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(getBioModule(myAirRSAirInFlowRateActuatorName));
				AirOutFlowRateActuator myAirRSAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(getBioModule(myAirRSAirOutFlowRateActuatorName));
				O2OutFlowRateActuator myAirRSO2OutFlowRateActuator = O2OutFlowRateActuatorHelper.narrow(getBioModule(myAirRSO2OutFlowRateActuatorName));
				CO2InFlowRateActuator myAirRSCO2InFlowRateActuator = CO2InFlowRateActuatorHelper.narrow(getBioModule(myAirRSCO2InFlowRateActuatorName));
				CO2OutFlowRateActuator myAirRSCO2OutFlowRateActuator = CO2OutFlowRateActuatorHelper.narrow(getBioModule(myAirRSCO2OutFlowRateActuatorName));
				myAirRSPowerInFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSAirInFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSAirOutFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSO2OutFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSCO2InFlowRateActuator.setOutput(myAirRS, 0);
				myAirRSCO2OutFlowRateActuator.setOutput(myAirRS, 0);
			}
			//Store
			{

				O2Store myO2Store = O2StoreHelper.narrow(getBioModule(myO2StoreName));
				CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(myCO2StoreName));
				O2StoreLevelActuator myO2StoreLevelActuator = O2StoreLevelActuatorHelper.narrow(getBioModule(myO2StoreLevelActuatorName));
				CO2StoreLevelActuator myCO2StoreLevelActuator = CO2StoreLevelActuatorHelper.narrow(getBioModule(myCO2StoreLevelActuatorName));
				myO2StoreLevelActuator.setOutput(myO2Store);
				myCO2StoreLevelActuator.setOutput(myCO2Store);
			}
		}

		//
		//Power
		//
		{
			//PowerPS
			{

				PowerPS myPowerPS = PowerPSHelper.narrow(getBioModule(myPowerPSName));
				PowerOutFlowRateActuator myPowerPSPowerOutFlowRateActuator = PowerOutFlowRateActuatorHelper.narrow(getBioModule(myPowerPSPowerOutFlowRateActuatorName));
				myPowerPSPowerOutFlowRateActuator.setOutput(myPowerPS, 0);
			}
			//Stores
			{
				PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(myPowerStoreName));
				PowerStoreLevelActuator myPowerStoreLevelActuator = PowerStoreLevelActuatorHelper.narrow(getBioModule(myPowerStoreLevelActuatorName));
				myPowerStoreLevelActuator.setOutput(myPowerStore);
			}
		}

		//
		//Environment
		//
		{
			//Crew
			{
				SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(myCrewEnvironmentName));
				O2AirMolesActuator myCrewEnvironmentO2AirMolesActuator = O2AirMolesActuatorHelper.narrow(getBioModule(myCrewEnvironmentO2AirMolesActuatorName));
				CO2AirMolesActuator myCrewEnvironmentCO2AirMolesActuator = CO2AirMolesActuatorHelper.narrow(getBioModule(myCrewEnvironmentCO2AirMolesActuatorName));
				OtherAirMolesActuator myCrewEnvironmentOtherAirMolesActuator = OtherAirMolesActuatorHelper.narrow(getBioModule(myCrewEnvironmentOtherAirMolesActuatorName));
				myCrewEnvironmentO2AirMolesActuator.setOutput(myCrewEnvironment);
				myCrewEnvironmentCO2AirMolesActuator.setOutput(myCrewEnvironment);
				myCrewEnvironmentOtherAirMolesActuator.setOutput(myCrewEnvironment);
			}
			//Plant
			{
				SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(myPlantEnvironmentName));
				O2AirMolesActuator myPlantEnvironmentO2AirMolesActuator = O2AirMolesActuatorHelper.narrow(getBioModule(myPlantEnvironmentO2AirMolesActuatorName));
				CO2AirMolesActuator myPlantEnvironmentCO2AirMolesActuator = CO2AirMolesActuatorHelper.narrow(getBioModule(myPlantEnvironmentCO2AirMolesActuatorName));
				OtherAirMolesActuator myPlantEnvironmentOtherAirMolesActuator = OtherAirMolesActuatorHelper.narrow(getBioModule(myPlantEnvironmentOtherAirMolesActuatorName));
				myPlantEnvironmentO2AirMolesActuator.setOutput(myPlantEnvironment);
				myPlantEnvironmentCO2AirMolesActuator.setOutput(myPlantEnvironment);
				myPlantEnvironmentOtherAirMolesActuator.setOutput(myPlantEnvironment);
			}
		}
		//
		//Food
		//
		{
			//BiomassRS
			{
				BiomassRS myBiomassRS = BiomassRSHelper.narrow(getBioModule(myBiomassRSName));
				AirInFlowRateActuator myBiomassRSAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSAirInFlowRateActuatorName));
				AirOutFlowRateActuator myBiomassRSAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSAirOutFlowRateActuatorName));
				PotableWaterInFlowRateActuator myBiomassRSPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSPotableWaterInFlowRateActuatorName));
				GreyWaterInFlowRateActuator myBiomassRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSGreyWaterInFlowRateActuatorName));
				BiomassOutFlowRateActuator myBiomassRSBiomassOutFlowRateActuator = BiomassOutFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSBiomassOutFlowRateActuatorName));
				PowerInFlowRateActuator myBiomassRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(getBioModule(myBiomassRSPowerInFlowRateActuatorName));
				myBiomassRSGreyWaterInFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSPotableWaterInFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSAirOutFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSAirInFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSBiomassOutFlowRateActuator.setOutput(myBiomassRS, 0);
				myBiomassRSPowerInFlowRateActuator.setOutput(myBiomassRS, 0);
			}
			//FoodProcessor
			{
				FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(getBioModule(myFoodProcessorName));
				BiomassInFlowRateActuator myFoodProcessorBiomassInFlowRateActuator = BiomassInFlowRateActuatorHelper.narrow(getBioModule(myFoodProcessorBiomassInFlowRateActuatorName));
				FoodOutFlowRateActuator myFoodProcessorFoodOutFlowRateActuator = FoodOutFlowRateActuatorHelper.narrow(getBioModule(myFoodProcessorFoodOutFlowRateActuatorName));
				PowerInFlowRateActuator myFoodProcessorPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(getBioModule(myFoodProcessorPowerInFlowRateActuatorName));
				myFoodProcessorPowerInFlowRateActuator.setOutput(myFoodProcessor, 0);
				myFoodProcessorFoodOutFlowRateActuator.setOutput(myFoodProcessor, 0);
				myFoodProcessorBiomassInFlowRateActuator.setOutput(myFoodProcessor, 0);
			}
			//Stores
			{
				FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(myFoodStoreName));
				BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(myBiomassStoreName));
				BiomassStoreLevelActuator myBiomassStoreLevelActuator = BiomassStoreLevelActuatorHelper.narrow(getBioModule(myBiomassStoreLevelActuatorName));
				FoodStoreLevelActuator myFoodStoreLevelActuator = FoodStoreLevelActuatorHelper.narrow(getBioModule(myFoodStoreLevelActuatorName));
				myFoodStoreLevelActuator.setOutput(myFoodStore);
				myBiomassStoreLevelActuator.setOutput(myBiomassStore);
			}
		}

		//
		//Water
		//
		{
			//WaterRS
			{
				WaterRS myWaterRS = WaterRSHelper.narrow(getBioModule(myWaterRSName));
				GreyWaterInFlowRateActuator myWaterRSGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(getBioModule(myWaterRSGreyWaterInFlowRateActuatorName));
				DirtyWaterInFlowRateActuator myWaterRSDirtyWaterInFlowRateActuator = DirtyWaterInFlowRateActuatorHelper.narrow(getBioModule(myWaterRSDirtyWaterInFlowRateActuatorName));
				PotableWaterOutFlowRateActuator myWaterRSPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper.narrow(getBioModule(myWaterRSPotableWaterOutFlowRateActuatorName));
				PowerInFlowRateActuator myWaterRSPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(getBioModule(myWaterRSPowerInFlowRateActuatorName));
				myWaterRSPotableWaterOutFlowRateActuator.setOutput(myWaterRS, 0);
				myWaterRSDirtyWaterInFlowRateActuator.setOutput(myWaterRS, 0);
				myWaterRSGreyWaterInFlowRateActuator.setOutput(myWaterRS, 0);
				myWaterRSPowerInFlowRateActuator.setOutput(myWaterRS, 0);
			}
			//Stores
			{
				PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(myPotableWaterStoreName));
				DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(myDirtyWaterStoreName));
				GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(myGreyWaterStoreName));
				PotableWaterStoreLevelActuator myPotableWaterStoreLevelActuator = PotableWaterStoreLevelActuatorHelper.narrow(getBioModule(myPotableWaterStoreLevelActuatorName));
				GreyWaterStoreLevelActuator myGreyWaterStoreLevelActuator = GreyWaterStoreLevelActuatorHelper.narrow(getBioModule(myGreyWaterStoreLevelActuatorName));
				DirtyWaterStoreLevelActuator myDirtyWaterStoreLevelActuator = DirtyWaterStoreLevelActuatorHelper.narrow(getBioModule(myDirtyWaterStoreLevelActuatorName));
				myPotableWaterStoreLevelActuator.setOutput(myPotableWaterStore);
				myGreyWaterStoreLevelActuator.setOutput(myGreyWaterStore);
				myDirtyWaterStoreLevelActuator.setOutput(myDirtyWaterStore);
			}
		}

		//
		//Framework
		//
		{
			//Accumulator
			{

				Accumulator myAccumulator = AccumulatorHelper.narrow(getBioModule(myAccumulatorName));
				CO2AirStoreOutFlowRateActuator myAccumulatorCO2AirStoreOutFlowRateActuator = CO2AirStoreOutFlowRateActuatorHelper.narrow(getBioModule(myAccumulatorCO2AirStoreOutFlowRateActuatorName));
				CO2AirEnvironmentInFlowRateActuator myAccumulatorCO2AirEnvironmentInFlowRateActuator = CO2AirEnvironmentInFlowRateActuatorHelper.narrow(getBioModule(myAccumulatorCO2AirEnvironmentInFlowRateActuatorName));
				O2AirEnvironmentInFlowRateActuator myAccumulatorO2AirEnvironmentInFlowRateActuator = O2AirEnvironmentInFlowRateActuatorHelper.narrow(getBioModule(myAccumulatorO2AirEnvironmentInFlowRateActuatorName));
				O2AirStoreOutFlowRateActuator myAccumulatorO2AirStoreOutFlowRateActuator = O2AirStoreOutFlowRateActuatorHelper.narrow(getBioModule(myAccumulatorO2AirStoreOutFlowRateActuatorName));
				myAccumulatorCO2AirEnvironmentInFlowRateActuator.setOutput(myAccumulator, 0);
				myAccumulatorO2AirEnvironmentInFlowRateActuator.setOutput(myAccumulator, 0);
				myAccumulatorO2AirStoreOutFlowRateActuator.setOutput(myAccumulator, 0);
				myAccumulatorCO2AirStoreOutFlowRateActuator.setOutput(myAccumulator, 0);
			}
			//Injector
			{
				Injector myInjector = InjectorHelper.narrow(getBioModule(myInjectorName));
				CO2AirStoreInFlowRateActuator myInjectorCO2AirStoreInFlowRateActuator = CO2AirStoreInFlowRateActuatorHelper.narrow(getBioModule(myInjectorCO2AirStoreInFlowRateActuatorName));
				O2AirStoreInFlowRateActuator myInjectorO2AirStoreInFlowRateActuator = O2AirStoreInFlowRateActuatorHelper.narrow(getBioModule(myInjectorO2AirStoreInFlowRateActuatorName));
				O2AirEnvironmentOutFlowRateActuator myInjectorO2AirEnvironmentOutFlowRateActuator = O2AirEnvironmentOutFlowRateActuatorHelper.narrow(getBioModule(myInjectorO2AirEnvironmentOutFlowRateActuatorName));
				CO2AirEnvironmentOutFlowRateActuator myInjectorCO2AirEnvironmentOutFlowRateActuator = CO2AirEnvironmentOutFlowRateActuatorHelper.narrow(getBioModule(myInjectorCO2AirEnvironmentOutFlowRateActuatorName));
				myInjectorCO2AirStoreInFlowRateActuator.setOutput(myInjector, 0);
				myInjectorO2AirStoreInFlowRateActuator.setOutput(myInjector, 0);
				myInjectorCO2AirEnvironmentOutFlowRateActuator.setOutput(myInjector, 0);
				myInjectorO2AirEnvironmentOutFlowRateActuator.setOutput(myInjector, 0);
			}
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
		CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(myCrewName));
		if (!createdCrew){
			CrewPerson myCrewPerson1 = myCrew.createCrewPerson("Alice Optimal", 50, 80, Sex.female);
			createdCrew = true;
		}

		//Fill the clean water stores to the brim (20 liters), and all stores' capacities
		DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(getBioModule(myDirtyWaterStoreName));
		PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(getBioModule(myPotableWaterStoreName));
		GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(getBioModule(myGreyWaterStoreName));
		myDirtyWaterStore.setCapacity(100f);
		myDirtyWaterStore.setLevel(100f);
		myPotableWaterStore.setCapacity(100f);
		myPotableWaterStore.setLevel(100f);
		myGreyWaterStore.setCapacity(100f);
		myGreyWaterStore.setLevel(100f);

		//Fill the air tanks
		CO2Store myCO2Store = CO2StoreHelper.narrow(getBioModule(myCO2StoreName));
		O2Store myO2Store = O2StoreHelper.narrow(getBioModule(myO2StoreName));
		myCO2Store.setCapacity(100f);
		myO2Store.setCapacity(100f);
		myCO2Store.setLevel(100f);
		myO2Store.setLevel(100f);

		//Put some air in the cabin
		SimEnvironment myCrewEnvironment = SimEnvironmentHelper.narrow(getBioModule(myCrewEnvironmentName));
		myCrewEnvironment.setInitialVolumeAtSeaLevel(10000000f);
		SimEnvironment myPlantEnvironment = SimEnvironmentHelper.narrow(getBioModule(myPlantEnvironmentName));
		myPlantEnvironment.setInitialVolumeAtSeaLevel(10000000f);

		//Add some crops and food
		BiomassStore myBiomassStore = BiomassStoreHelper.narrow(getBioModule(myBiomassStoreName));
		FoodStore myFoodStore = FoodStoreHelper.narrow(getBioModule(myFoodStoreName));
		myBiomassStore.setCapacity(100f);
		myFoodStore.setCapacity(100f);
		myBiomassStore.setLevel(100f);
		myFoodStore.setLevel(100f);

		//Add some power
		PowerStore myPowerStore = PowerStoreHelper.narrow(getBioModule(myPowerStoreName));
		myPowerStore.setCapacity(100f);
		myPowerStore.setLevel(100f);
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
			CrewGroup myCrew = CrewGroupHelper.narrow(getBioModule(myCrewName));
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
				System.err.println("BioDriverImpl:"+myID+" Couldn't locate ("+i+" element) called "+myModuleNames[i]+", skipping...");
			}
		}
		try{
			myLogger = LoggerHelper.narrow(OrbUtils.getNCRef().resolve_str(myLoggerName));
		}
		catch (Exception e){
			System.err.println("BioDriverImpl:"+myID+" Couldn't locate logger named "+myLoggerName+", skipping...");
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

