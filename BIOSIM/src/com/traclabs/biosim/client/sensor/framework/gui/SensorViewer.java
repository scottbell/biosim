package biosim.client.sensor.framework.gui;

/**
 * @author    Scott Bell
 */	
import javax.swing.*;
import biosim.client.framework.gui.*;
import biosim.client.util.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.water.*;

public class SensorViewer extends BioFrame
{
	private JTable myTable;

	//Air
	//AirRs
	private PowerInFlowRateSensor myAirRSPowerInFlowRateSensor;
	private AirInFlowRateSensor myAirRSAirInFlowRateSensor;
	private AirOutFlowRateSensor myAirRSAirOutFlowRateSensor;
	private O2OutFlowRateSensor myAirRSO2OutFlowRateSensor;
	private CO2InFlowRateSensor myAirRSCO2InFlowRateSensor;
	private CO2OutFlowRateSensor myAirRSCO2OutFlowRateSensor;
	//Stores
	private O2StoreLevelSensor myO2StoreLevelSensor;
	private CO2StoreLevelSensor myCO2StoreLevelSensor;
	//Power
	//PowerPS
	private PowerOutFlowRateSensor myPowerPSPowerOutFlowRateSensor;
	//Stores
	private PowerStoreLevelSensor myPowerStoreLevelSensor;
	//Environment
	//Crew
	private OtherAirLevelSensor myCrewEnvironmentOtherAirLevelSensor;
	private CO2AirLevelSensor myCrewEnvironmentCO2AirLevelSensor;
	private O2AirLevelSensor myCrewEnvironmentO2AirLevelSensor;
	//Plant
	private OtherAirLevelSensor myPlantEnvironmentOtherAirLevelSensor;
	private CO2AirLevelSensor myPlantEnvironmentCO2AirLevelSensor;
	private O2AirLevelSensor myPlantEnvironmentO2AirLevelSensor;
	//Water
	//WaterRS
	private DirtyWaterInFlowRateSensor myWaterRSDirtyWaterInFlowRateSensor;
	private GreyWaterInFlowRateSensor myWaterRSGreyWaterInFlowRateSensor;
	private PowerInFlowRateSensor myWaterRSPowerInFlowRateSensor;
	private PotableWaterOutFlowRateSensor myWaterRSPotableWaterOutFlowRateSensor;
	//Stores
	private PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor;
	private GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor;
	private DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor;
	//Food
	//BiomassRS
	private AirInFlowRateSensor myBiomassRSAirInFlowRateSensor;
	private AirOutFlowRateSensor myBiomassRSAirOutFlowRateSensor;
	private PotableWaterInFlowRateSensor myBiomassRSPotableWaterInFlowRateSensor;
	private GreyWaterInFlowRateSensor myBiomassRSGreyWaterInFlowRateSensor;
	private BiomassOutFlowRateSensor myBiomassRSBiomassOutFlowRateSensor;
	//Food Processor
	private PowerInFlowRateSensor myFoodProcessorPowerInFlowRateSensor;
	private BiomassInFlowRateSensor myFoodProcessorBiomassInFlowRateSensor;
	private FoodOutFlowRateSensor myFoodProcessorFoodOutFlowRateSensor;
	//Stores
	private BiomassStoreLevelSensor myBiomassStoreLevelSensor;
	private FoodStoreLevelSensor myFoodStoreLevelSensor;
	//Framework
	//Accumulator
	private CO2AirEnvironmentInFlowRateSensor myAccumulatorCO2AirEnvironmentInFlowRateSensor;
	private O2AirEnvironmentInFlowRateSensor myAccumulatorO2AirEnvironmentInFlowRateSensor;
	private CO2AirStoreOutFlowRateSensor myAccumulatorCO2AirStoreOutFlowRateSensor;
	private O2AirStoreOutFlowRateSensor myAccumulatorO2AirStoreOutFlowRateSensor;
	//Injector
	private CO2AirStoreInFlowRateSensor myInjectorCO2AirStoreInFlowRateSensor;
	private O2AirStoreInFlowRateSensor myInjectorO2AirStoreInFlowRateSensor;
	private CO2AirEnvironmentOutFlowRateSensor myInjectorCO2AirEnvironmentOutFlowRateSensor;
	private O2AirEnvironmentOutFlowRateSensor myInjectorO2AirEnvironmentOutFlowRateSensor;
	
	//Table information
	String[] columnNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};
	String[] rowNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};

	public SensorViewer(){
		super("Sensor Viewer", false);
		collectReferences();
	}

	public static void main(String args[])
	{
		SensorViewer myViewer = new SensorViewer();
		myViewer.setSize(320, 130);
		myViewer.setVisible(true);
	}

	private void collectReferences(){
		PowerInFlowRateSensor myAirRSPowerInFlowRateSensor = (PowerInFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSPowerInFlowRateSensorName);
		AirInFlowRateSensor myAirRSAirInFlowRateSensor = (AirInFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSAirInFlowRateSensorName);
		AirOutFlowRateSensor myAirRSAirOutFlowRateSensor = (AirOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSAirOutFlowRateSensorName);
		O2OutFlowRateSensor myAirRSO2OutFlowRateSensor = (O2OutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSO2OutFlowRateSensorName);
		CO2InFlowRateSensor myAirRSCO2InFlowRateSensor = (CO2InFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSCO2InFlowRateSensorName);
		CO2OutFlowRateSensor myAirRSCO2OutFlowRateSensor = (CO2OutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSCO2OutFlowRateSensorName);
		//Stores
		O2StoreLevelSensor myO2StoreLevelSensor = (O2StoreLevelSensor)BioHolder.getBioModule(BioHolder.myO2StoreLevelSensorName);
		CO2StoreLevelSensor myCO2StoreLevelSensor = (CO2StoreLevelSensor)BioHolder.getBioModule(BioHolder.myCO2StoreLevelSensorName);
		//Power
		//PowerPS
		PowerOutFlowRateSensor myPowerPSPowerOutFlowRateSensor = (PowerOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myPowerPSPowerOutFlowRateSensorName);
		//Stores
		PowerStoreLevelSensor myPowerStoreLevelSensor = (PowerStoreLevelSensor)BioHolder.getBioModule(BioHolder.myPowerStoreLevelSensorName);
		//Environment
		//Crew
		OtherAirLevelSensor myCrewEnvironmentOtherAirLevelSensor = (OtherAirLevelSensor)BioHolder.getBioModule(BioHolder.myCrewEnvironmentOtherAirLevelSensorName);
		CO2AirLevelSensor myCrewEnvironmentCO2AirLevelSensor = (CO2AirLevelSensor)BioHolder.getBioModule(BioHolder.myCrewEnvironmentCO2AirLevelSensorName);
		O2AirLevelSensor myCrewEnvironmentO2AirLevelSensor = (O2AirLevelSensor)BioHolder.getBioModule(BioHolder.myCrewEnvironmentO2AirLevelSensorName);
		//Plant
		OtherAirLevelSensor myPlantEnvironmentOtherAirLevelSensor = (OtherAirLevelSensor)BioHolder.getBioModule(BioHolder.myPlantEnvironmentOtherAirLevelSensorName);
		CO2AirLevelSensor myPlantEnvironmentCO2AirLevelSensor = (CO2AirLevelSensor)BioHolder.getBioModule(BioHolder.myPlantEnvironmentCO2AirLevelSensorName);
		O2AirLevelSensor myPlantEnvironmentO2AirLevelSensor = (O2AirLevelSensor)BioHolder.getBioModule(BioHolder.myPlantEnvironmentO2AirLevelSensorName);
		//Water
		//WaterRS
		DirtyWaterInFlowRateSensor myWaterRSDirtyWaterInFlowRateSensor = (DirtyWaterInFlowRateSensor)BioHolder.getBioModule(BioHolder.myWaterRSDirtyWaterInFlowRateSensorName);
		GreyWaterInFlowRateSensor myWaterRSGreyWaterInFlowRateSensor = (GreyWaterInFlowRateSensor)BioHolder.getBioModule(BioHolder.myWaterRSGreyWaterInFlowRateSensorName);
		PowerInFlowRateSensor myWaterRSPowerInFlowRateSensor = (PowerInFlowRateSensor)BioHolder.getBioModule(BioHolder.myWaterRSPowerInFlowRateSensorName);
		PotableWaterOutFlowRateSensor myWaterRSPotableWaterOutFlowRateSensor = (PotableWaterOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myWaterRSPotableWaterOutFlowRateSensorName);
		//Stores
		PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = (PotableWaterStoreLevelSensor)BioHolder.getBioModule(BioHolder.myPotableWaterStoreLevelSensorName);
		GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor = (GreyWaterStoreLevelSensor)BioHolder.getBioModule(BioHolder.myGreyWaterStoreLevelSensorName);
		DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor = (DirtyWaterStoreLevelSensor)BioHolder.getBioModule(BioHolder.myDirtyWaterStoreLevelSensorName);
		//Food
		//BiomassRS
		AirInFlowRateSensor myBiomassRSAirInFlowRateSensor = (AirInFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSAirInFlowRateSensorName);
		AirOutFlowRateSensor myBiomassRSAirOutFlowRateSensor = (AirOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSAirOutFlowRateSensorName);
		PotableWaterInFlowRateSensor myBiomassRSPotableWaterInFlowRateSensor = (PotableWaterInFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSPotableWaterInFlowRateSensorName);
		GreyWaterInFlowRateSensor myBiomassRSGreyWaterInFlowRateSensor = (GreyWaterInFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSGreyWaterInFlowRateSensorName);
		BiomassOutFlowRateSensor myBiomassRSBiomassOutFlowRateSensor = (BiomassOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSBiomassOutFlowRateSensorName);
		//Food Processor
		PowerInFlowRateSensor myFoodProcessorPowerInFlowRateSensor = (PowerInFlowRateSensor)BioHolder.getBioModule(BioHolder.myFoodProcessorPowerInFlowRateSensorName);
		BiomassInFlowRateSensor myFoodProcessorBiomassInFlowRateSensor = (BiomassInFlowRateSensor)BioHolder.getBioModule(BioHolder.myFoodProcessorBiomassInFlowRateSensorName);
		FoodOutFlowRateSensor myFoodProcessorFoodOutFlowRateSensor = (FoodOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myFoodProcessorFoodOutFlowRateSensorName);
		//Stores
		BiomassStoreLevelSensor myBiomassStoreLevelSensor = (BiomassStoreLevelSensor)BioHolder.getBioModule(BioHolder.myBiomassStoreLevelSensorName);
		FoodStoreLevelSensor myFoodStoreLevelSensor = (FoodStoreLevelSensor)BioHolder.getBioModule(BioHolder.myFoodStoreLevelSensorName);
		//Framework
		//Accumulator
		CO2AirEnvironmentInFlowRateSensor myAccumulatorCO2AirEnvironmentInFlowRateSensor = (CO2AirEnvironmentInFlowRateSensor)BioHolder.getBioModule(BioHolder.myAccumulatorCO2AirEnvironmentInFlowRateSensorName);
		O2AirEnvironmentInFlowRateSensor myAccumulatorO2AirEnvironmentInFlowRateSensor = (O2AirEnvironmentInFlowRateSensor)BioHolder.getBioModule(BioHolder.myAccumulatorO2AirEnvironmentInFlowRateSensorName);
		CO2AirStoreOutFlowRateSensor myAccumulatorCO2AirStoreOutFlowRateSensor = (CO2AirStoreOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAccumulatorCO2AirStoreOutFlowRateSensorName);
		O2AirStoreOutFlowRateSensor myAccumulatorO2AirStoreOutFlowRateSensor = (O2AirStoreOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAccumulatorO2AirStoreOutFlowRateSensorName);
		//Injector
		CO2AirStoreInFlowRateSensor myInjectorCO2AirStoreInFlowRateSensor = (CO2AirStoreInFlowRateSensor)BioHolder.getBioModule(BioHolder.myInjectorCO2AirStoreInFlowRateSensorName);
		O2AirStoreInFlowRateSensor myInjectorO2AirStoreInFlowRateSensor = (O2AirStoreInFlowRateSensor)BioHolder.getBioModule(BioHolder.myInjectorO2AirStoreInFlowRateSensorName);
		CO2AirEnvironmentOutFlowRateSensor myInjectorCO2AirEnvironmentOutFlowRateSensor = (CO2AirEnvironmentOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myInjectorCO2AirEnvironmentOutFlowRateSensorName);
		O2AirEnvironmentOutFlowRateSensor myInjectorO2AirEnvironmentOutFlowRateSensor = (O2AirEnvironmentOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myInjectorO2AirEnvironmentOutFlowRateSensorName);
	}

}

