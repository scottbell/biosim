package biosim.client.sensor.framework.gui;

/**
 * @author    Scott Bell
 */	
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.gui.spreadsheet.*;
import biosim.client.util.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.water.*;
import java.text.*;

public class SensorViewer extends SpreadSheet
{
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
	private OtherAirConcentrationSensor myCrewEnvironmentOtherAirConcentrationSensor;
	private CO2AirConcentrationSensor myCrewEnvironmentCO2AirConcentrationSensor;
	private O2AirConcentrationSensor myCrewEnvironmentO2AirConcentrationSensor;
	//Plant
	private OtherAirConcentrationSensor myPlantEnvironmentOtherAirConcentrationSensor;
	private CO2AirConcentrationSensor myPlantEnvironmentCO2AirConcentrationSensor;
	private O2AirConcentrationSensor myPlantEnvironmentO2AirConcentrationSensor;
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
	private PowerInFlowRateSensor myBiomassRSPowerInFlowRateSensor;
	private PotableWaterInFlowRateSensor myBiomassRSPotableWaterInFlowRateSensor;
	private GreyWaterInFlowRateSensor myBiomassRSGreyWaterInFlowRateSensor;
	private DirtyWaterOutFlowRateSensor myBiomassRSDirtyWaterOutFlowRateSensor;
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
	
	private Timer refreshTimer;
	private final static int DEFAULT_TIMER_DELAY=500;
	private RefreshAction myRefreshAction;
	
	//For formatting floats
	private DecimalFormat numFormat;
	
	private static final String[] myResourceTypeNames = {"O2", "CO2", "Food", "Biomass", "Power", "Air", "Grey Water", "Dirty Water", "Potable Water"};
	private static final String[] mySimModuleNames = {"Air RS", "Biomass RS", "Food Processor", "Water RS"};
	
	//Table information
	public SensorViewer(){
		super(myResourceTypeNames, mySimModuleNames);
		numFormat = new DecimalFormat("#,##0.00;(#)");
		collectReferences();
		myRefreshAction = new RefreshAction("Refresh");
		refreshTimer = new Timer (DEFAULT_TIMER_DELAY, myRefreshAction);
		refreshTimer.start();
	}

	private void collectReferences(){
		//Air
		//AirRS
		myAirRSPowerInFlowRateSensor = (PowerInFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSPowerInFlowRateSensorName);
		myAirRSAirInFlowRateSensor = (AirInFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSAirInFlowRateSensorName);
		myAirRSAirOutFlowRateSensor = (AirOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSAirOutFlowRateSensorName);
		myAirRSO2OutFlowRateSensor = (O2OutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSO2OutFlowRateSensorName);
		myAirRSCO2InFlowRateSensor = (CO2InFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSCO2InFlowRateSensorName);
		myAirRSCO2OutFlowRateSensor = (CO2OutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAirRSCO2OutFlowRateSensorName);
		//Stores
		myO2StoreLevelSensor = (O2StoreLevelSensor)BioHolder.getBioModule(BioHolder.myO2StoreLevelSensorName);
		myCO2StoreLevelSensor = (CO2StoreLevelSensor)BioHolder.getBioModule(BioHolder.myCO2StoreLevelSensorName);
		//Power
		//PowerPS
		myPowerPSPowerOutFlowRateSensor = (PowerOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myPowerPSPowerOutFlowRateSensorName);
		//Stores
		myPowerStoreLevelSensor = (PowerStoreLevelSensor)BioHolder.getBioModule(BioHolder.myPowerStoreLevelSensorName);
		//Environment
		//Crew
		myCrewEnvironmentOtherAirConcentrationSensor = (OtherAirConcentrationSensor)BioHolder.getBioModule(BioHolder.myCrewEnvironmentOtherAirConcentrationSensorName);
		myCrewEnvironmentCO2AirConcentrationSensor = (CO2AirConcentrationSensor)BioHolder.getBioModule(BioHolder.myCrewEnvironmentCO2AirConcentrationSensorName);
		myCrewEnvironmentO2AirConcentrationSensor = (O2AirConcentrationSensor)BioHolder.getBioModule(BioHolder.myCrewEnvironmentO2AirConcentrationSensorName);
		//Plant
		myPlantEnvironmentOtherAirConcentrationSensor = (OtherAirConcentrationSensor)BioHolder.getBioModule(BioHolder.myPlantEnvironmentOtherAirConcentrationSensorName);
		myPlantEnvironmentCO2AirConcentrationSensor = (CO2AirConcentrationSensor)BioHolder.getBioModule(BioHolder.myPlantEnvironmentCO2AirConcentrationSensorName);
		myPlantEnvironmentO2AirConcentrationSensor = (O2AirConcentrationSensor)BioHolder.getBioModule(BioHolder.myPlantEnvironmentO2AirConcentrationSensorName);
		//Water
		//WaterRS
		myWaterRSDirtyWaterInFlowRateSensor = (DirtyWaterInFlowRateSensor)BioHolder.getBioModule(BioHolder.myWaterRSDirtyWaterInFlowRateSensorName);
		myWaterRSGreyWaterInFlowRateSensor = (GreyWaterInFlowRateSensor)BioHolder.getBioModule(BioHolder.myWaterRSGreyWaterInFlowRateSensorName);
		myWaterRSPowerInFlowRateSensor = (PowerInFlowRateSensor)BioHolder.getBioModule(BioHolder.myWaterRSPowerInFlowRateSensorName);
		myWaterRSPotableWaterOutFlowRateSensor = (PotableWaterOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myWaterRSPotableWaterOutFlowRateSensorName);
		//Stores
		myPotableWaterStoreLevelSensor = (PotableWaterStoreLevelSensor)BioHolder.getBioModule(BioHolder.myPotableWaterStoreLevelSensorName);
		myGreyWaterStoreLevelSensor = (GreyWaterStoreLevelSensor)BioHolder.getBioModule(BioHolder.myGreyWaterStoreLevelSensorName);
		myDirtyWaterStoreLevelSensor = (DirtyWaterStoreLevelSensor)BioHolder.getBioModule(BioHolder.myDirtyWaterStoreLevelSensorName);
		//Food
		//BiomassRS
		myBiomassRSPotableWaterInFlowRateSensor = (PotableWaterInFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSPotableWaterInFlowRateSensorName);
		myBiomassRSGreyWaterInFlowRateSensor = (GreyWaterInFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSGreyWaterInFlowRateSensorName);
		myBiomassRSDirtyWaterOutFlowRateSensor = (DirtyWaterOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSDirtyWaterOutFlowRateSensorName);
		myBiomassRSBiomassOutFlowRateSensor = (BiomassOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSBiomassOutFlowRateSensorName);
		myBiomassRSPowerInFlowRateSensor = (PowerInFlowRateSensor)BioHolder.getBioModule(BioHolder.myBiomassRSPowerInFlowRateSensorName);
		//Food Processor
		myFoodProcessorPowerInFlowRateSensor = (PowerInFlowRateSensor)BioHolder.getBioModule(BioHolder.myFoodProcessorPowerInFlowRateSensorName);
		myFoodProcessorBiomassInFlowRateSensor = (BiomassInFlowRateSensor)BioHolder.getBioModule(BioHolder.myFoodProcessorBiomassInFlowRateSensorName);
		myFoodProcessorFoodOutFlowRateSensor = (FoodOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myFoodProcessorFoodOutFlowRateSensorName);
		//Stores
		myBiomassStoreLevelSensor = (BiomassStoreLevelSensor)BioHolder.getBioModule(BioHolder.myBiomassStoreLevelSensorName);
		myFoodStoreLevelSensor = (FoodStoreLevelSensor)BioHolder.getBioModule(BioHolder.myFoodStoreLevelSensorName);
		//Framework
		//Accumulator
		myAccumulatorCO2AirEnvironmentInFlowRateSensor = (CO2AirEnvironmentInFlowRateSensor)BioHolder.getBioModule(BioHolder.myAccumulatorCO2AirEnvironmentInFlowRateSensorName);
		myAccumulatorO2AirEnvironmentInFlowRateSensor = (O2AirEnvironmentInFlowRateSensor)BioHolder.getBioModule(BioHolder.myAccumulatorO2AirEnvironmentInFlowRateSensorName);
		myAccumulatorCO2AirStoreOutFlowRateSensor = (CO2AirStoreOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAccumulatorCO2AirStoreOutFlowRateSensorName);
		myAccumulatorO2AirStoreOutFlowRateSensor = (O2AirStoreOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myAccumulatorO2AirStoreOutFlowRateSensorName);
		//Injector
		myInjectorCO2AirStoreInFlowRateSensor = (CO2AirStoreInFlowRateSensor)BioHolder.getBioModule(BioHolder.myInjectorCO2AirStoreInFlowRateSensorName);
		myInjectorO2AirStoreInFlowRateSensor = (O2AirStoreInFlowRateSensor)BioHolder.getBioModule(BioHolder.myInjectorO2AirStoreInFlowRateSensorName);
		myInjectorCO2AirEnvironmentOutFlowRateSensor = (CO2AirEnvironmentOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myInjectorCO2AirEnvironmentOutFlowRateSensorName);
		myInjectorO2AirEnvironmentOutFlowRateSensor = (O2AirEnvironmentOutFlowRateSensor)BioHolder.getBioModule(BioHolder.myInjectorO2AirEnvironmentOutFlowRateSensorName);
	}
	
	public static void main(String args[])
	{
		JFrame myFrame = new JFrame("Sensor Viewer");
		SensorViewer myViewer = new SensorViewer();
		myFrame.getContentPane().add(myViewer.getScrollPane());
		myFrame.pack();
		myFrame.setVisible(true);
	}
	
	/**
	* Action that displays the power panel in an internal frame on the desktop.
	*/
	private class RefreshAction extends AbstractAction{
		public RefreshAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			refresh();
		}
	}
	
	public void refresh(){
		//myTableModel.setValueAt("Hello", rowsDown, columnsOver);
		TableModel myTableModel = getModel();
		
		//AirRS
		myTableModel.setValueAt("* / "+numFormat.format(myAirRSO2OutFlowRateSensor.getValue()), 0, 0);
		myTableModel.setValueAt(numFormat.format(myAirRSCO2InFlowRateSensor.getValue())+" / "+numFormat.format(myAirRSCO2OutFlowRateSensor.getValue()), 0, 1);
		myTableModel.setValueAt(numFormat.format(myAirRSPowerInFlowRateSensor.getValue())+" / *", 0, 4);
		myTableModel.setValueAt(numFormat.format(myAirRSAirInFlowRateSensor.getValue())+" / "+numFormat.format(myAirRSAirOutFlowRateSensor.getValue()), 0, 5);
		
		//Biomass RS
		myTableModel.setValueAt("* / "+numFormat.format(myBiomassRSBiomassOutFlowRateSensor.getValue()), 1, 3);
		myTableModel.setValueAt(numFormat.format(myBiomassRSPowerInFlowRateSensor.getValue())+" / *", 1, 4);
		myTableModel.setValueAt(numFormat.format(myBiomassRSGreyWaterInFlowRateSensor.getValue())+" / *", 1, 6);
		myTableModel.setValueAt(numFormat.format(myBiomassRSPotableWaterInFlowRateSensor.getValue())+" / *", 1, 8);
		myTableModel.setValueAt("* / "+numFormat.format(myBiomassRSDirtyWaterOutFlowRateSensor.getValue()), 1, 7);
		
		//Food Processor
		myTableModel.setValueAt("* / "+numFormat.format(myFoodProcessorFoodOutFlowRateSensor.getValue()), 2, 2);
		myTableModel.setValueAt(numFormat.format(myFoodProcessorBiomassInFlowRateSensor.getValue())+" / *", 2, 3);
		myTableModel.setValueAt(numFormat.format(myFoodProcessorPowerInFlowRateSensor.getValue())+" / *", 2, 4);
		
		//Water RS
		myTableModel.setValueAt(numFormat.format(myWaterRSPowerInFlowRateSensor.getValue())+" / *", 3, 4);
		myTableModel.setValueAt(numFormat.format(myWaterRSDirtyWaterInFlowRateSensor.getValue())+" / *", 3, 7);
		myTableModel.setValueAt(numFormat.format(myWaterRSGreyWaterInFlowRateSensor.getValue())+" / *", 3, 6);
		myTableModel.setValueAt("* / "+numFormat.format(myWaterRSPotableWaterOutFlowRateSensor.getValue()), 3, 8);	
	}

}

