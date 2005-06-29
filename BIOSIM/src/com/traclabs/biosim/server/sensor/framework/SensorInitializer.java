package com.traclabs.biosim.server.sensor.framework;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.MethaneInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.MethaneInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.MethaneInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.MethaneOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.MethaneOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.MethaneOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensorPOATie;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensorPOATie;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.HarvestSensor;
import com.traclabs.biosim.idl.sensor.food.HarvestSensorHelper;
import com.traclabs.biosim.idl.sensor.food.HarvestSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensor;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensorPOATie;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensorPOATie;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerHelper;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.H2ProducerHelper;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.MethaneProducerHelper;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerHelper;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.O2ProducerHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerHelper;
import com.traclabs.biosim.idl.simulation.environment.AirProducerHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassRSHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerHelper;
import com.traclabs.biosim.idl.simulation.food.FoodProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.StoreHelper;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerHelper;
import com.traclabs.biosim.idl.simulation.power.PowerProducerHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerHelper;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.water.WaterProducerHelper;
import com.traclabs.biosim.server.framework.BioInitializer;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.server.sensor.air.CO2InFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.CO2OutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.H2InFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.H2OutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.MethaneInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.MethaneOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.NitrogenInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.NitrogenOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.O2InFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.O2OutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.crew.CrewGroupAnyDeadSensorImpl;
import com.traclabs.biosim.server.sensor.crew.CrewGroupDeathSensorImpl;
import com.traclabs.biosim.server.sensor.crew.CrewGroupProductivitySensorImpl;
import com.traclabs.biosim.server.sensor.environment.AirInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.AirOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassStoreWaterContentSensorImpl;
import com.traclabs.biosim.server.sensor.food.FoodInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.FoodOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.HarvestSensorImpl;
import com.traclabs.biosim.server.sensor.food.PlantDeathSensorImpl;
import com.traclabs.biosim.server.sensor.power.PowerInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.power.PowerOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.waste.DryWasteInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.waste.DryWasteOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.DirtyWaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.DirtyWaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.GreyWaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.GreyWaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.PotableWaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.PotableWaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.WaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.WaterOutFlowRateSensorImpl;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class SensorInitializer {
    private int myID = 0;

    private List<GenericSensor> mySensors;

    private Logger myLogger;

    /** Default constructor. */
    public SensorInitializer(int pID) {
        myID = pID;
        mySensors = new Vector<GenericSensor>();
        myLogger = Logger.getLogger(this.getClass());
    }

    //Sensors
    public void crawlSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("air")) {
                crawlAirSensors(child, firstPass);

            } else if (childName.equals("crew")) {
                crawlCrewSensors(child, firstPass);

            } else if (childName.equals("environment")) {
                crawlEnvironmentSensors(child, firstPass);

            } else if (childName.equals("food")) {
                crawlFoodSensors(child, firstPass);

            } else if (childName.equals("framework")) {
                crawlFrameworkSensors(child, firstPass);

            } else if (childName.equals("power")) {
                crawlPowerSensors(child, firstPass);

            } else if (childName.equals("water")) {
                crawlWaterSensors(child, firstPass);

            } else if (childName.equals("waste")) {
                crawlWasteSensors(child, firstPass);

            }
            child = child.getNextSibling();
        }
    }

    private static String getInputName(Node pNode) {
        return pNode.getAttributes().getNamedItem("input").getNodeValue();
    }

    private static int getFlowRateIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes().getNamedItem("index")
                .getNodeValue());
    }

    //Air
    private void createCO2InFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CO2InFlowRateSensor with moduleName: "
                    + moduleName);
            CO2InFlowRateSensorImpl myCO2InFlowRateSensorImpl = new CO2InFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2InFlowRateSensorImpl, node);
            BiosimServer.registerServer(new CO2InFlowRateSensorPOATie(
                    myCO2InFlowRateSensorImpl), myCO2InFlowRateSensorImpl
                    .getModuleName(), myCO2InFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2InFlowRateSensor(Node node) {
        CO2InFlowRateSensor myCO2InFlowRateSensor = CO2InFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2InFlowRateSensor.setInput(CO2ConsumerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myCO2InFlowRateSensor);
    }

    private void createCO2OutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CO2OutFlowRateSensor with moduleName: "
                    + moduleName);
            CO2OutFlowRateSensorImpl myCO2OutFlowRateSensorImpl = new CO2OutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2OutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new CO2OutFlowRateSensorPOATie(
                    myCO2OutFlowRateSensorImpl), myCO2OutFlowRateSensorImpl
                    .getModuleName(), myCO2OutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCO2OutFlowRateSensor(Node node) {
        CO2OutFlowRateSensor myCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCO2OutFlowRateSensor.setInput(CO2ProducerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myCO2OutFlowRateSensor);
    }

    private void createO2InFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating O2InFlowRateSensor with moduleName: "
                    + moduleName);
            O2InFlowRateSensorImpl myO2InFlowRateSensorImpl = new O2InFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2InFlowRateSensorImpl, node);
            BiosimServer.registerServer(new O2InFlowRateSensorPOATie(
                    myO2InFlowRateSensorImpl), myO2InFlowRateSensorImpl
                    .getModuleName(), myO2InFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2InFlowRateSensor(Node node) {
        O2InFlowRateSensor myO2InFlowRateSensor = O2InFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2InFlowRateSensor.setInput(O2ConsumerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myO2InFlowRateSensor);
    }

    private void createO2OutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating O2OutFlowRateSensor with moduleName: "
                    + moduleName);
            O2OutFlowRateSensorImpl myO2OutFlowRateSensorImpl = new O2OutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2OutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new O2OutFlowRateSensorPOATie(
                    myO2OutFlowRateSensorImpl), myO2OutFlowRateSensorImpl
                    .getModuleName(), myO2OutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureO2OutFlowRateSensor(Node node) {
        O2OutFlowRateSensor myO2OutFlowRateSensor = O2OutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myO2OutFlowRateSensor.setInput(O2ProducerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myO2OutFlowRateSensor);
    }

    private void createH2InFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating H2InFlowRateSensor with moduleName: "
                    + moduleName);
            H2InFlowRateSensorImpl myH2InFlowRateSensorImpl = new H2InFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myH2InFlowRateSensorImpl, node);
            BiosimServer.registerServer(new H2InFlowRateSensorPOATie(
                    myH2InFlowRateSensorImpl), myH2InFlowRateSensorImpl
                    .getModuleName(), myH2InFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureH2InFlowRateSensor(Node node) {
        H2InFlowRateSensor myH2InFlowRateSensor = H2InFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myH2InFlowRateSensor.setInput(H2ConsumerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myH2InFlowRateSensor);
    }

    private void createH2OutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating H2OutFlowRateSensor with moduleName: "
                    + moduleName);
            H2OutFlowRateSensorImpl myH2OutFlowRateSensorImpl = new H2OutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myH2OutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new H2OutFlowRateSensorPOATie(
                    myH2OutFlowRateSensorImpl), myH2OutFlowRateSensorImpl
                    .getModuleName(), myH2OutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureH2OutFlowRateSensor(Node node) {
        H2OutFlowRateSensor myH2OutFlowRateSensor = H2OutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myH2OutFlowRateSensor.setInput(H2ProducerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myH2OutFlowRateSensor);
    }

    private void createNitrogenInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenInFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenInFlowRateSensorImpl myNitrogenInFlowRateSensorImpl = new NitrogenInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new NitrogenInFlowRateSensorPOATie(
                    myNitrogenInFlowRateSensorImpl),
                    myNitrogenInFlowRateSensorImpl.getModuleName(),
                    myNitrogenInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenInFlowRateSensor(Node node) {
        NitrogenInFlowRateSensor myNitrogenInFlowRateSensor = NitrogenInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenInFlowRateSensor.setInput(NitrogenConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myNitrogenInFlowRateSensor);
    }

    private void createNitrogenOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenOutFlowRateSensor with moduleName: "
                            + moduleName);
            NitrogenOutFlowRateSensorImpl myNitrogenOutFlowRateSensorImpl = new NitrogenOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myNitrogenOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new NitrogenOutFlowRateSensorPOATie(
                    myNitrogenOutFlowRateSensorImpl),
                    myNitrogenOutFlowRateSensorImpl.getModuleName(),
                    myNitrogenOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureNitrogenOutFlowRateSensor(Node node) {
        NitrogenOutFlowRateSensor myNitrogenOutFlowRateSensor = NitrogenOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myNitrogenOutFlowRateSensor.setInput(NitrogenProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myNitrogenOutFlowRateSensor);
    }

    private void createMethaneInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating MethaneInFlowRateSensor with moduleName: "
                            + moduleName);
            MethaneInFlowRateSensorImpl myMethaneInFlowRateSensorImpl = new MethaneInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myMethaneInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new MethaneInFlowRateSensorPOATie(
                    myMethaneInFlowRateSensorImpl),
                    myMethaneInFlowRateSensorImpl.getModuleName(),
                    myMethaneInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureMethaneInFlowRateSensor(Node node) {
        MethaneInFlowRateSensor myMethaneInFlowRateSensor = MethaneInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myMethaneInFlowRateSensor.setInput(MethaneConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myMethaneInFlowRateSensor);
    }

    private void createMethaneOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating MethaneOutFlowRateSensor with moduleName: "
                            + moduleName);
            MethaneOutFlowRateSensorImpl myMethaneOutFlowRateSensorImpl = new MethaneOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myMethaneOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new MethaneOutFlowRateSensorPOATie(
                    myMethaneOutFlowRateSensorImpl),
                    myMethaneOutFlowRateSensorImpl.getModuleName(),
                    myMethaneOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureMethaneOutFlowRateSensor(Node node) {
        MethaneOutFlowRateSensor myMethaneOutFlowRateSensor = MethaneOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myMethaneOutFlowRateSensor.setInput(MethaneProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myMethaneOutFlowRateSensor);
    }

    private void crawlAirSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CO2InFlowRateSensor")) {
                if (firstPass)
                    createCO2InFlowRateSensor(child);
                else
                    configureCO2InFlowRateSensor(child);
            } else if (childName.equals("CO2OutFlowRateSensor")) {
                if (firstPass)
                    createCO2OutFlowRateSensor(child);
                else
                    configureCO2OutFlowRateSensor(child);
            }else if (childName.equals("O2InFlowRateSensor")) {
                if (firstPass)
                    createO2InFlowRateSensor(child);
                else
                    configureO2InFlowRateSensor(child);
            } else if (childName.equals("O2OutFlowRateSensor")) {
                if (firstPass)
                    createO2OutFlowRateSensor(child);
                else
                    configureO2OutFlowRateSensor(child);
            }else if (childName.equals("H2InFlowRateSensor")) {
                if (firstPass)
                    createH2InFlowRateSensor(child);
                else
                    configureH2InFlowRateSensor(child);
            } else if (childName.equals("H2OutFlowRateSensor")) {
                if (firstPass)
                    createH2OutFlowRateSensor(child);
                else
                    configureH2OutFlowRateSensor(child);
            }else if (childName.equals("NitrogenInFlowRateSensor")) {
                if (firstPass)
                    createNitrogenInFlowRateSensor(child);
                else
                    configureNitrogenInFlowRateSensor(child);
            } else if (childName.equals("NitrogenOutFlowRateSensor")) {
                if (firstPass)
                    createNitrogenOutFlowRateSensor(child);
                else
                    configureNitrogenOutFlowRateSensor(child);
            }else if (childName.equals("MethaneInFlowRateSensor")) {
                if (firstPass)
                    createMethaneInFlowRateSensor(child);
                else
                    configureMethaneInFlowRateSensor(child);
            } else if (childName.equals("MethaneOutFlowRateSensor")) {
                if (firstPass)
                    createMethaneOutFlowRateSensor(child);
                else
                    configureMethaneOutFlowRateSensor(child);
            } 
            child = child.getNextSibling();
        }
    }

    //Crew
    private void createCrewGroupDeathSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CrewGroupDeathSensor with moduleName: "
                    + moduleName);
            CrewGroupDeathSensorImpl myCrewGroupDeathSensorImpl = new CrewGroupDeathSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCrewGroupDeathSensorImpl, node);
            BiosimServer.registerServer(new CrewGroupDeathSensorPOATie(
                    myCrewGroupDeathSensorImpl), myCrewGroupDeathSensorImpl
                    .getModuleName(), myCrewGroupDeathSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroupDeathSensor(Node node) {
        CrewGroupDeathSensor myCrewGroupDeathSensor = CrewGroupDeathSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCrewGroupDeathSensor.setInput(CrewGroupHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myCrewGroupDeathSensor);
    }

    private void createCrewGroupAnyDeadSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CrewGroupAnyDeadSensor with moduleName: "
                    + moduleName);
            CrewGroupAnyDeadSensorImpl myCrewGroupAnyDeadSensorImpl = new CrewGroupAnyDeadSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCrewGroupAnyDeadSensorImpl, node);
            BiosimServer.registerServer(new CrewGroupAnyDeadSensorPOATie(
                    myCrewGroupAnyDeadSensorImpl), myCrewGroupAnyDeadSensorImpl
                    .getModuleName(), myCrewGroupAnyDeadSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroupAnyDeadSensor(Node node) {
        CrewGroupAnyDeadSensor myCrewGroupAnyDeadSensor = CrewGroupAnyDeadSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCrewGroupAnyDeadSensor.setInput(CrewGroupHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myCrewGroupAnyDeadSensor);
    }

    private void createCrewGroupProductivitySensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CrewGroupProductivitySensor with moduleName: "
                            + moduleName);
            CrewGroupProductivitySensorImpl myCrewGroupProductivitySensorImpl = new CrewGroupProductivitySensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCrewGroupProductivitySensorImpl,
                    node);
            BiosimServer.registerServer(new CrewGroupProductivitySensorPOATie(
                    myCrewGroupProductivitySensorImpl),
                    myCrewGroupProductivitySensorImpl.getModuleName(),
                    myCrewGroupProductivitySensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroupProductivitySensor(Node node) {
        CrewGroupProductivitySensor myCrewGroupProductivitySensor = CrewGroupProductivitySensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myCrewGroupProductivitySensor.setInput(CrewGroupHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myCrewGroupProductivitySensor);
    }

    private void crawlCrewSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CrewGroupDeathSensor")) {
                if (firstPass)
                    createCrewGroupDeathSensor(child);
                else
                    configureCrewGroupDeathSensor(child);
            } else if (childName.equals("CrewGroupAnyDeadSensor")) {
                if (firstPass)
                    createCrewGroupAnyDeadSensor(child);
                else
                    configureCrewGroupAnyDeadSensor(child);
            } else if (childName.equals("CrewGroupProductivitySensor")) {
                if (firstPass)
                    createCrewGroupProductivitySensor(child);
                else
                    configureCrewGroupProductivitySensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Environment
    private void createAirInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating AirInFlowRateSensor with moduleName: "
                    + moduleName);
            AirInFlowRateSensorImpl myAirInFlowRateSensorImpl = new AirInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myAirInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new AirInFlowRateSensorPOATie(
                    myAirInFlowRateSensorImpl), myAirInFlowRateSensorImpl
                    .getModuleName(), myAirInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureAirInFlowRateSensor(Node node) {
        AirInFlowRateSensor myAirInFlowRateSensor = AirInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myAirInFlowRateSensor.setInput(AirConsumerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myAirInFlowRateSensor);
    }

    private void createAirOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating AirOutFlowRateSensor with moduleName: "
                    + moduleName);
            AirOutFlowRateSensorImpl myAirOutFlowRateSensorImpl = new AirOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myAirOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new AirOutFlowRateSensorPOATie(
                    myAirOutFlowRateSensorImpl), myAirOutFlowRateSensorImpl
                    .getModuleName(), myAirOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureAirOutFlowRateSensor(Node node) {
        AirOutFlowRateSensor myAirOutFlowRateSensor = AirOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myAirOutFlowRateSensor.setInput(AirProducerHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))), getFlowRateIndex(node));
        mySensors.add(myAirOutFlowRateSensor);
    }

    private void createGasConcentrationSensor(Node child) {
		// TODO Auto-generated method stub
		
	}

	private void configureGasConcentrationSensor(Node child) {
		// TODO Auto-generated method stub
		
	}

	private void createGasPressureSensor(Node child) {
		// TODO Auto-generated method stub
		
	}

	private void configureGasPressureSensor(Node child) {
		// TODO Auto-generated method stub
		
	}

    private void crawlEnvironmentSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("AirInFlowRateSensor")) {
                if (firstPass)
                    createAirInFlowRateSensor(child);
                else
                    configureAirInFlowRateSensor(child);
            } else if (childName.equals("AirOutFlowRateSensor")) {
                if (firstPass)
                    createAirOutFlowRateSensor(child);
                else
                    configureAirOutFlowRateSensor(child);
            } else if (childName.equals("GasPressureSensor")) {
                if (firstPass)
                    createGasPressureSensor(child);
                else
                    configureGasPressureSensor(child);
            } else if (childName.equals("GasConcentrationSensor")) {
                if (firstPass)
                	createGasConcentrationSensor(child);
                else
                	configureGasConcentrationSensor(child);
            }
            child = child.getNextSibling();
        }
    }

	//Food
    private void createBiomassInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating BiomassInFlowRateSensor with moduleName: "
                    + moduleName);
            BiomassInFlowRateSensorImpl myBiomassInFlowRateSensorImpl = new BiomassInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myBiomassInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new BiomassInFlowRateSensorPOATie(
                    myBiomassInFlowRateSensorImpl),
                    myBiomassInFlowRateSensorImpl.getModuleName(),
                    myBiomassInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassInFlowRateSensor(Node node) {
        BiomassInFlowRateSensor myBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myBiomassInFlowRateSensor.setInput(BiomassConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myBiomassInFlowRateSensor);
    }

    private void createBiomassOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating BiomassOutFlowRateSensor with moduleName: "
                            + moduleName);
            BiomassOutFlowRateSensorImpl myBiomassOutFlowRateSensorImpl = new BiomassOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myBiomassOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new BiomassOutFlowRateSensorPOATie(
                    myBiomassOutFlowRateSensorImpl),
                    myBiomassOutFlowRateSensorImpl.getModuleName(),
                    myBiomassOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassOutFlowRateSensor(Node node) {
        BiomassOutFlowRateSensor myBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myBiomassOutFlowRateSensor.setInput(BiomassProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myBiomassOutFlowRateSensor);
    }

    private void createFoodInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating FoodInFlowRateSensor with moduleName: "
                    + moduleName);
            FoodInFlowRateSensorImpl myFoodInFlowRateSensorImpl = new FoodInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myFoodInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new FoodInFlowRateSensorPOATie(
                    myFoodInFlowRateSensorImpl), myFoodInFlowRateSensorImpl
                    .getModuleName(), myFoodInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureFoodInFlowRateSensor(Node node) {
        FoodInFlowRateSensor myFoodInFlowRateSensor = FoodInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myFoodInFlowRateSensor.setInput(FoodConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myFoodInFlowRateSensor);
    }

    private void createFoodOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating FoodOutFlowRateSensor with moduleName: "
                    + moduleName);
            FoodOutFlowRateSensorImpl myFoodOutFlowRateSensorImpl = new FoodOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myFoodOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new FoodOutFlowRateSensorPOATie(
                    myFoodOutFlowRateSensorImpl), myFoodOutFlowRateSensorImpl
                    .getModuleName(), myFoodOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureFoodOutFlowRateSensor(Node node) {
        FoodOutFlowRateSensor myFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myFoodOutFlowRateSensor.setInput(FoodProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myFoodOutFlowRateSensor);
    }

    private void configureBiomassStoreWaterContentSensor(Node node) {
        BiomassStoreWaterContentSensor myBiomassStoreWaterContentSensor = BiomassStoreWaterContentSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myBiomassStoreWaterContentSensor.setInput(BiomassStoreHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))));
        mySensors.add(myBiomassStoreWaterContentSensor);
    }

    private void createBiomassStoreWaterContentSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating BiomassStoreWaterContentSensor with moduleName: "
                            + moduleName);
            BiomassStoreWaterContentSensorImpl myBiomassStoreWaterContentSensorImpl = new BiomassStoreWaterContentSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myBiomassStoreWaterContentSensorImpl,
                    node);
            BiosimServer.registerServer(
                    new BiomassStoreWaterContentSensorPOATie(
                            myBiomassStoreWaterContentSensorImpl),
                    myBiomassStoreWaterContentSensorImpl.getModuleName(),
                    myBiomassStoreWaterContentSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }
    private void createHarvestSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating HarvestSensor with moduleName: "
                    + moduleName);
            HarvestSensorImpl myHarvestSensorImpl = new HarvestSensorImpl(myID,
                    moduleName);
            BioInitializer.setupBioModule(myHarvestSensorImpl, node);
            BiosimServer.registerServer(new HarvestSensorPOATie(
                    myHarvestSensorImpl), myHarvestSensorImpl.getModuleName(),
                    myHarvestSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureHarvestSensor(Node node) {
        try {
            int index = Integer.parseInt(node.getAttributes().getNamedItem(
                    "shelfIndex").getNodeValue());
            HarvestSensor myHarvestSensor = HarvestSensorHelper
                    .narrow(BioInitializer.grabModule(myID, BioInitializer
                            .getModuleName(node)));
            myHarvestSensor.setInput(BiomassRSHelper.narrow(BioInitializer
                    .grabModule(myID, getInputName(node))), index);
            mySensors.add(myHarvestSensor);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void createPlantDeathSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PlantDeathSensor with moduleName: "
                    + moduleName);
            PlantDeathSensorImpl myPlantDeathSensorImpl = new PlantDeathSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPlantDeathSensorImpl, node);
            BiosimServer.registerServer(new PlantDeathSensorPOATie(
                    myPlantDeathSensorImpl), myPlantDeathSensorImpl
                    .getModuleName(), myPlantDeathSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePlantDeathSensor(Node node) {
        try {
            int index = Integer.parseInt(node.getAttributes().getNamedItem(
                    "shelfIndex").getNodeValue());
            PlantDeathSensor myPlantDeathSensor = PlantDeathSensorHelper
                    .narrow(BioInitializer.grabModule(myID, BioInitializer
                            .getModuleName(node)));
            myPlantDeathSensor.setInput(BiomassRSHelper.narrow(BioInitializer
                    .grabModule(myID, getInputName(node))), index);
            mySensors.add(myPlantDeathSensor);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void crawlFoodSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("BiomassInFlowRateSensor")) {
                if (firstPass)
                    createBiomassInFlowRateSensor(child);
                else
                    configureBiomassInFlowRateSensor(child);
            } else if (childName.equals("BiomassOutFlowRateSensor")) {
                if (firstPass)
                    createBiomassOutFlowRateSensor(child);
                else
                    configureBiomassOutFlowRateSensor(child);
            }else if (childName.equals("BiomassStoreWaterContentSensor")) {
                if (firstPass)
                    createBiomassStoreWaterContentSensor(child);
                else
                    configureBiomassStoreWaterContentSensor(child);
            } else if (childName.equals("FoodInFlowRateSensor")) {
                if (firstPass)
                    createFoodInFlowRateSensor(child);
                else
                    configureFoodInFlowRateSensor(child);
            } else if (childName.equals("FoodOutFlowRateSensor")) {
                if (firstPass)
                    createFoodOutFlowRateSensor(child);
                else
                    configureFoodOutFlowRateSensor(child);
            }else if (childName.equals("HarvestSensor")) {
                if (firstPass)
                    createHarvestSensor(child);
                else
                    configureHarvestSensor(child);
            } else if (childName.equals("PlantDeathSensor")) {
                if (firstPass)
                    createPlantDeathSensor(child);
                else
                    configurePlantDeathSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Framework
    private void createStoreLevelSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating StoreLevelSensor with moduleName: "
                    + moduleName);
            StoreLevelSensorImpl myStoreLevelSensorImpl = new StoreLevelSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myStoreLevelSensorImpl, node);
            BiosimServer.registerServer(new StoreLevelSensorPOATie(
                    myStoreLevelSensorImpl), myStoreLevelSensorImpl
                    .getModuleName(), myStoreLevelSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureStoreLevelSensor(Node node) {
        StoreLevelSensor myStoreLevelSensor = StoreLevelSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myStoreLevelSensor.setInput(StoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myStoreLevelSensor);
    }

    private void createStoreOverflowSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating StoreOverflowSensor with moduleName: "
                    + moduleName);
            StoreOverflowSensorImpl myStoreOverflowSensorImpl = new StoreOverflowSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myStoreOverflowSensorImpl, node);
            BiosimServer.registerServer(new StoreOverflowSensorPOATie(
                    myStoreOverflowSensorImpl), myStoreOverflowSensorImpl
                    .getModuleName(), myStoreOverflowSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureStoreOverflowSensor(Node node) {
        StoreOverflowSensor myStoreOverflowSensor = StoreOverflowSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myStoreOverflowSensor.setInput(StoreHelper.narrow(BioInitializer
                .grabModule(myID, getInputName(node))));
        mySensors.add(myStoreOverflowSensor);
    }

    private void crawlFrameworkSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("StoreLevelSensor")) {
                if (firstPass)
                    createStoreLevelSensor(child);
                else
                    configureStoreLevelSensor(child);
            } else if (childName.equals("StoreOverflowSensor")) {
                if (firstPass)
                    createStoreOverflowSensor(child);
                else
                    configureStoreOverflowSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Power
    private void createPowerInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PowerInFlowRateSensor with moduleName: "
                    + moduleName);
            PowerInFlowRateSensorImpl myPowerInFlowRateSensorImpl = new PowerInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPowerInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new PowerInFlowRateSensorPOATie(
                    myPowerInFlowRateSensorImpl), myPowerInFlowRateSensorImpl
                    .getModuleName(), myPowerInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePowerInFlowRateSensor(Node node) {
        PowerInFlowRateSensor myPowerInFlowRateSensor = PowerInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPowerInFlowRateSensor.setInput(PowerConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myPowerInFlowRateSensor);
    }

    private void createPowerOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PowerOutFlowRateSensor with moduleName: "
                    + moduleName);
            PowerOutFlowRateSensorImpl myPowerOutFlowRateSensorImpl = new PowerOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPowerOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new PowerOutFlowRateSensorPOATie(
                    myPowerOutFlowRateSensorImpl), myPowerOutFlowRateSensorImpl
                    .getModuleName(), myPowerOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePowerOutFlowRateSensor(Node node) {
        PowerOutFlowRateSensor myPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPowerOutFlowRateSensor.setInput(PowerProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myPowerOutFlowRateSensor);
    }
    
    private void crawlPowerSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PowerInFlowRateSensor")) {
                if (firstPass)
                    createPowerInFlowRateSensor(child);
                else
                    configurePowerInFlowRateSensor(child);
            } else if (childName.equals("PowerOutFlowRateSensor")) {
                if (firstPass)
                    createPowerOutFlowRateSensor(child);
                else
                    configurePowerOutFlowRateSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Water
    private void createPotableWaterInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterInFlowRateSensor with moduleName: "
                            + moduleName);
            PotableWaterInFlowRateSensorImpl myPotableWaterInFlowRateSensorImpl = new PotableWaterInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPotableWaterInFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new PotableWaterInFlowRateSensorPOATie(
                    myPotableWaterInFlowRateSensorImpl),
                    myPotableWaterInFlowRateSensorImpl.getModuleName(),
                    myPotableWaterInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePotableWaterInFlowRateSensor(Node node) {
        PotableWaterInFlowRateSensor myPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPotableWaterInFlowRateSensor.setInput(PotableWaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myPotableWaterInFlowRateSensor);
    }

    private void createPotableWaterOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterOutFlowRateSensor with moduleName: "
                            + moduleName);
            PotableWaterOutFlowRateSensorImpl myPotableWaterOutFlowRateSensorImpl = new PotableWaterOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPotableWaterOutFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(
                    new PotableWaterOutFlowRateSensorPOATie(
                            myPotableWaterOutFlowRateSensorImpl),
                    myPotableWaterOutFlowRateSensorImpl.getModuleName(),
                    myPotableWaterOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePotableWaterOutFlowRateSensor(Node node) {
        PotableWaterOutFlowRateSensor myPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myPotableWaterOutFlowRateSensor.setInput(PotableWaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myPotableWaterOutFlowRateSensor);
    }

    private void createGreyWaterInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterInFlowRateSensor with moduleName: "
                            + moduleName);
            GreyWaterInFlowRateSensorImpl myGreyWaterInFlowRateSensorImpl = new GreyWaterInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myGreyWaterInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new GreyWaterInFlowRateSensorPOATie(
                    myGreyWaterInFlowRateSensorImpl),
                    myGreyWaterInFlowRateSensorImpl.getModuleName(),
                    myGreyWaterInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureGreyWaterInFlowRateSensor(Node node) {
        GreyWaterInFlowRateSensor myGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myGreyWaterInFlowRateSensor.setInput(GreyWaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myGreyWaterInFlowRateSensor);
    }

    private void createGreyWaterOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterOutFlowRateSensor with moduleName: "
                            + moduleName);
            GreyWaterOutFlowRateSensorImpl myGreyWaterOutFlowRateSensorImpl = new GreyWaterOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myGreyWaterOutFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new GreyWaterOutFlowRateSensorPOATie(
                    myGreyWaterOutFlowRateSensorImpl),
                    myGreyWaterOutFlowRateSensorImpl.getModuleName(),
                    myGreyWaterOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureGreyWaterOutFlowRateSensor(Node node) {
        GreyWaterOutFlowRateSensor myGreyWaterOutFlowRateSensor = GreyWaterOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myGreyWaterOutFlowRateSensor.setInput(GreyWaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myGreyWaterOutFlowRateSensor);
    }

    private void createDirtyWaterInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterInFlowRateSensor with moduleName: "
                            + moduleName);
            DirtyWaterInFlowRateSensorImpl myDirtyWaterInFlowRateSensorImpl = new DirtyWaterInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDirtyWaterInFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new DirtyWaterInFlowRateSensorPOATie(
                    myDirtyWaterInFlowRateSensorImpl),
                    myDirtyWaterInFlowRateSensorImpl.getModuleName(),
                    myDirtyWaterInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDirtyWaterInFlowRateSensor(Node node) {
        DirtyWaterInFlowRateSensor myDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDirtyWaterInFlowRateSensor.setInput(DirtyWaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myDirtyWaterInFlowRateSensor);
    }

    private void createDirtyWaterOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterOutFlowRateSensor with moduleName: "
                            + moduleName);
            DirtyWaterOutFlowRateSensorImpl myDirtyWaterOutFlowRateSensorImpl = new DirtyWaterOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDirtyWaterOutFlowRateSensorImpl,
                    node);
            BiosimServer.registerServer(new DirtyWaterOutFlowRateSensorPOATie(
                    myDirtyWaterOutFlowRateSensorImpl),
                    myDirtyWaterOutFlowRateSensorImpl.getModuleName(),
                    myDirtyWaterOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDirtyWaterOutFlowRateSensor(Node node) {
        DirtyWaterOutFlowRateSensor myDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDirtyWaterOutFlowRateSensor.setInput(DirtyWaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myDirtyWaterOutFlowRateSensor);
    }

    private void createWaterInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating WaterInFlowRateSensor with moduleName: "
                    + moduleName);
            WaterInFlowRateSensorImpl myWaterInFlowRateSensorImpl = new WaterInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new WaterInFlowRateSensorPOATie(
                    myWaterInFlowRateSensorImpl), myWaterInFlowRateSensorImpl
                    .getModuleName(), myWaterInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterInFlowRateSensor(Node node) {
        WaterInFlowRateSensor myWaterInFlowRateSensor = WaterInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterInFlowRateSensor.setInput(WaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myWaterInFlowRateSensor);
    }

    private void createWaterOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating WaterOutFlowRateSensor with moduleName: "
                    + moduleName);
            WaterOutFlowRateSensorImpl myWaterOutFlowRateSensorImpl = new WaterOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new WaterOutFlowRateSensorPOATie(
                    myWaterOutFlowRateSensorImpl), myWaterOutFlowRateSensorImpl
                    .getModuleName(), myWaterOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterOutFlowRateSensor(Node node) {
        WaterOutFlowRateSensor myWaterOutFlowRateSensor = WaterOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myWaterOutFlowRateSensor.setInput(WaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myWaterOutFlowRateSensor);
    }

    private void crawlWaterSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PotableWaterInFlowRateSensor")) {
                if (firstPass)
                    createPotableWaterInFlowRateSensor(child);
                else
                    configurePotableWaterInFlowRateSensor(child);
            } else if (childName.equals("PotableWaterOutFlowRateSensor")) {
                if (firstPass)
                    createPotableWaterOutFlowRateSensor(child);
                else
                    configurePotableWaterOutFlowRateSensor(child);
            }else if (childName.equals("GreyWaterInFlowRateSensor")) {
                if (firstPass)
                    createGreyWaterInFlowRateSensor(child);
                else
                    configureGreyWaterInFlowRateSensor(child);
            } else if (childName.equals("GreyWaterOutFlowRateSensor")) {
                if (firstPass)
                    createGreyWaterOutFlowRateSensor(child);
                else
                    configureGreyWaterOutFlowRateSensor(child);
            } else if (childName.equals("DirtyWaterInFlowRateSensor")) {
                if (firstPass)
                    createDirtyWaterInFlowRateSensor(child);
                else
                    configureDirtyWaterInFlowRateSensor(child);
            } else if (childName.equals("DirtyWaterOutFlowRateSensor")) {
                if (firstPass)
                    createDirtyWaterOutFlowRateSensor(child);
                else
                    configureDirtyWaterOutFlowRateSensor(child);
            }else if (childName.equals("WaterInFlowRateSensor")) {
                if (firstPass)
                    createWaterInFlowRateSensor(child);
                else
                    configureWaterInFlowRateSensor(child);
            } else if (childName.equals("WaterOutFlowRateSensor")) {
                if (firstPass)
                    createWaterOutFlowRateSensor(child);
                else
                    configureWaterOutFlowRateSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    //Waste
    private void createDryWasteInFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteInFlowRateSensor with moduleName: "
                            + moduleName);
            DryWasteInFlowRateSensorImpl myDryWasteInFlowRateSensorImpl = new DryWasteInFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDryWasteInFlowRateSensorImpl, node);
            BiosimServer.registerServer(new DryWasteInFlowRateSensorPOATie(
                    myDryWasteInFlowRateSensorImpl),
                    myDryWasteInFlowRateSensorImpl.getModuleName(),
                    myDryWasteInFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDryWasteInFlowRateSensor(Node node) {
        DryWasteInFlowRateSensor myDryWasteInFlowRateSensor = DryWasteInFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDryWasteInFlowRateSensor.setInput(DryWasteConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myDryWasteInFlowRateSensor);
    }

    private void createDryWasteOutFlowRateSensor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteOutFlowRateSensor with moduleName: "
                            + moduleName);
            DryWasteOutFlowRateSensorImpl myDryWasteOutFlowRateSensorImpl = new DryWasteOutFlowRateSensorImpl(
                    myID, moduleName);
            BioInitializer
                    .setupBioModule(myDryWasteOutFlowRateSensorImpl, node);
            BiosimServer.registerServer(new DryWasteOutFlowRateSensorPOATie(
                    myDryWasteOutFlowRateSensorImpl),
                    myDryWasteOutFlowRateSensorImpl.getModuleName(),
                    myDryWasteOutFlowRateSensorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDryWasteOutFlowRateSensor(Node node) {
        DryWasteOutFlowRateSensor myDryWasteOutFlowRateSensor = DryWasteOutFlowRateSensorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        myDryWasteOutFlowRateSensor.setInput(DryWasteProducerHelper
                .narrow(BioInitializer.grabModule(myID, getInputName(node))),
                getFlowRateIndex(node));
        mySensors.add(myDryWasteOutFlowRateSensor);
    }

    private void crawlWasteSensors(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("DryWasteInFlowRateSensor")) {
                if (firstPass)
                    createDryWasteInFlowRateSensor(child);
                else
                    configureDryWasteInFlowRateSensor(child);
            } else if (childName.equals("DryWasteOutFlowRateSensor")) {
                if (firstPass)
                    createDryWasteOutFlowRateSensor(child);
                else
                    configureDryWasteOutFlowRateSensor(child);
            }
            child = child.getNextSibling();
        }
    }

    /**
     * @return Returns the mySensors.
     */
    public List<GenericSensor> getSensors() {
        return mySensors;
    }
}