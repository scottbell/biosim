package com.traclabs.biosim.server.actuator.framework;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.CO2AirEnvironmentInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.CO2AirEnvironmentInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.CO2AirEnvironmentInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.CO2AirEnvironmentOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.CO2AirEnvironmentOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.CO2AirEnvironmentOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirStoreInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirStoreInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirStoreInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirStoreOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirStoreOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirStoreOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.O2AirEnvironmentInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.O2AirEnvironmentInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.O2AirEnvironmentInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.O2AirEnvironmentOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.O2AirEnvironmentOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.O2AirEnvironmentOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.O2AirStoreInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.O2AirStoreInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.O2AirStoreInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.O2AirStoreOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.O2AirStoreOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.O2AirStoreOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.BiomassInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.BiomassInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.BiomassInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuator;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.PlantingActuator;
import com.traclabs.biosim.idl.actuator.food.PlantingActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.PlantingActuatorPOATie;
import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.waste.DryWasteInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.waste.DryWasteInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.waste.DryWasteInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.waste.DryWasteOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.waste.DryWasteOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.waste.DryWasteOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.GreyWaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.GreyWaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.GreyWaterInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.WaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.WaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.WaterInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.WaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.WaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.WaterOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.simulation.food.BiomassRSHelper;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.AirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.BiomassConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.CO2AirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.FoodConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.FoodProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.O2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.PowerProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterProducerHelper;
import com.traclabs.biosim.server.actuator.air.CO2InFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.CO2OutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.H2InFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.H2OutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.NitrogenInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.NitrogenOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.O2InFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.O2OutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.AirInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.AirOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.CO2AirEnvironmentInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.CO2AirEnvironmentOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.CO2AirStoreInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.CO2AirStoreOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.NitrogenAirEnvironmentInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.NitrogenAirEnvironmentOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.NitrogenAirStoreInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.NitrogenAirStoreOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.O2AirEnvironmentInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.O2AirEnvironmentOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.O2AirStoreInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.O2AirStoreOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.WaterAirEnvironmentInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.WaterAirEnvironmentOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.WaterAirStoreInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.WaterAirStoreOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.BiomassInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.BiomassOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.FoodInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.FoodOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.HarvestingActuatorImpl;
import com.traclabs.biosim.server.actuator.food.PlantingActuatorImpl;
import com.traclabs.biosim.server.actuator.power.PowerInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.power.PowerOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.waste.DryWasteInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.waste.DryWasteOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.DirtyWaterInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.DirtyWaterOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.GreyWaterInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.GreyWaterOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.PotableWaterInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.PotableWaterOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.WaterInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.WaterOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.framework.BioInitializer;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class ActuatorInitializer {
    private int myID = 0;

    private List myActuators;

    private Logger myLogger;
    
    /** Default constructor. */
    public ActuatorInitializer(int pID) {
        myID = pID;
        myActuators = new Vector();
        myLogger = Logger.getLogger(this.getClass());
    }

    private static int getShelfIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes()
                .getNamedItem("shelfIndex").getNodeValue());
    }
    
    private static String getOutputName(Node pNode) {
        return pNode.getAttributes().getNamedItem("output").getNodeValue();
    }
    
    private static int getFlowRateIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes().getNamedItem("index")
                .getNodeValue());
    }

    //Air
    private void createCO2InFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CO2InFlowRateActuator with moduleName: "
                    + moduleName);
            CO2InFlowRateActuatorImpl myCO2InFlowRateActuatorImpl = new CO2InFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2InFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new CO2InFlowRateActuatorPOATie(
                    myCO2InFlowRateActuatorImpl), myCO2InFlowRateActuatorImpl
                    .getModuleName(), myCO2InFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureCO2InFlowRateActuator(Node node) {
        CO2InFlowRateActuator myCO2InFlowRateActuator = CO2InFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myCO2InFlowRateActuator.setOutput(CO2ConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myCO2InFlowRateActuator);
    }

    private void createCO2OutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CO2OutFlowRateActuator with moduleName: "
                    + moduleName);
            CO2OutFlowRateActuatorImpl myCO2OutFlowRateActuatorImpl = new CO2OutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2OutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new CO2OutFlowRateActuatorPOATie(
                    myCO2OutFlowRateActuatorImpl), myCO2OutFlowRateActuatorImpl
                    .getModuleName(), myCO2OutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureCO2OutFlowRateActuator(Node node) {
        CO2OutFlowRateActuator myCO2OutFlowRateActuator = CO2OutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myCO2OutFlowRateActuator.setOutput(CO2ProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myCO2OutFlowRateActuator);
    }

    private void createO2InFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating O2InFlowRateActuator with moduleName: "
                    + moduleName);
            O2InFlowRateActuatorImpl myO2InFlowRateActuatorImpl = new O2InFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2InFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new O2InFlowRateActuatorPOATie(
                    myO2InFlowRateActuatorImpl), myO2InFlowRateActuatorImpl
                    .getModuleName(), myO2InFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureO2InFlowRateActuator(Node node) {
        O2InFlowRateActuator myO2InFlowRateActuator = O2InFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myO2InFlowRateActuator.setOutput(O2ConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myO2InFlowRateActuator);
    }

    private void createO2OutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating O2OutFlowRateActuator with moduleName: "
                    + moduleName);
            O2OutFlowRateActuatorImpl myO2OutFlowRateActuatorImpl = new O2OutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2OutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new O2OutFlowRateActuatorPOATie(
                    myO2OutFlowRateActuatorImpl), myO2OutFlowRateActuatorImpl
                    .getModuleName(), myO2OutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureO2OutFlowRateActuator(Node node) {
        O2OutFlowRateActuator myO2OutFlowRateActuator = O2OutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myO2OutFlowRateActuator.setOutput(O2ProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myO2OutFlowRateActuator);
    }

    private void createH2InFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating H2InFlowRateActuator with moduleName: "
                    + moduleName);
            H2InFlowRateActuatorImpl myH2InFlowRateActuatorImpl = new H2InFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myH2InFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new H2InFlowRateActuatorPOATie(
                    myH2InFlowRateActuatorImpl), myH2InFlowRateActuatorImpl
                    .getModuleName(), myH2InFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureH2InFlowRateActuator(Node node) {
        H2InFlowRateActuator myH2InFlowRateActuator = H2InFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myH2InFlowRateActuator.setOutput(H2ConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myH2InFlowRateActuator);
    }

    private void createH2OutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating H2OutFlowRateActuator with moduleName: "
                    + moduleName);
            H2OutFlowRateActuatorImpl myH2OutFlowRateActuatorImpl = new H2OutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myH2OutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new H2OutFlowRateActuatorPOATie(
                    myH2OutFlowRateActuatorImpl), myH2OutFlowRateActuatorImpl
                    .getModuleName(), myH2OutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureH2OutFlowRateActuator(Node node) {
        H2OutFlowRateActuator myH2OutFlowRateActuator = H2OutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myH2OutFlowRateActuator.setOutput(H2ProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myH2OutFlowRateActuator);
    }

    private void createNitrogenInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenInFlowRateActuator with moduleName: "
                            + moduleName);
            NitrogenInFlowRateActuatorImpl myNitrogenInFlowRateActuatorImpl = new NitrogenInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new NitrogenInFlowRateActuatorPOATie(
                    myNitrogenInFlowRateActuatorImpl),
                    myNitrogenInFlowRateActuatorImpl.getModuleName(),
                    myNitrogenInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureNitrogenInFlowRateActuator(Node node) {
        NitrogenInFlowRateActuator myNitrogenInFlowRateActuator = NitrogenInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myNitrogenInFlowRateActuator.setOutput(NitrogenConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myNitrogenInFlowRateActuator);
    }

    private void createNitrogenOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenOutFlowRateActuator with moduleName: "
                            + moduleName);
            NitrogenOutFlowRateActuatorImpl myNitrogenOutFlowRateActuatorImpl = new NitrogenOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new NitrogenOutFlowRateActuatorPOATie(
                    myNitrogenOutFlowRateActuatorImpl),
                    myNitrogenOutFlowRateActuatorImpl.getModuleName(),
                    myNitrogenOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureNitrogenOutFlowRateActuator(Node node) {
        NitrogenOutFlowRateActuator myNitrogenOutFlowRateActuator = NitrogenOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myNitrogenOutFlowRateActuator.setOutput(NitrogenProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myNitrogenOutFlowRateActuator);
    }

    private void crawlAirActuators(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CO2InFlowRateActuator")) {
                if (firstPass)
                    createCO2InFlowRateActuator(child);
                else
                    configureCO2InFlowRateActuator(child);
            } else if (childName.equals("CO2OutFlowRateActuator")) {
                if (firstPass)
                    createCO2OutFlowRateActuator(child);
                else
                    configureCO2OutFlowRateActuator(child);
            } else if (childName.equals("O2InFlowRateActuator")) {
                if (firstPass)
                    createO2InFlowRateActuator(child);
                else
                    configureO2InFlowRateActuator(child);
            } else if (childName.equals("O2OutFlowRateActuator")) {
                if (firstPass)
                    createO2OutFlowRateActuator(child);
                else
                    configureO2OutFlowRateActuator(child);
            } else if (childName.equals("H2InFlowRateActuator")) {
                if (firstPass)
                    createH2InFlowRateActuator(child);
                else
                    configureH2InFlowRateActuator(child);
            } else if (childName.equals("H2OutFlowRateActuator")) {
                if (firstPass)
                    createH2OutFlowRateActuator(child);
                else
                    configureH2OutFlowRateActuator(child);
            } else if (childName.equals("NitrogenInFlowRateActuator")) {
                if (firstPass)
                    createNitrogenInFlowRateActuator(child);
                else
                    configureNitrogenInFlowRateActuator(child);
            } else if (childName.equals("NitrogenOutFlowRateActuator")) {
                if (firstPass)
                    createNitrogenOutFlowRateActuator(child);
                else
                    configureNitrogenOutFlowRateActuator(child);
            }
            child = child.getNextSibling();
        }
    }

    //Environment
    private void createAirInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating AirInFlowRateActuator with moduleName: "
                    + moduleName);
            AirInFlowRateActuatorImpl myAirInFlowRateActuatorImpl = new AirInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myAirInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new AirInFlowRateActuatorPOATie(
                    myAirInFlowRateActuatorImpl), myAirInFlowRateActuatorImpl
                    .getModuleName(), myAirInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureAirInFlowRateActuator(Node node) {
        AirInFlowRateActuator myAirInFlowRateActuator = AirInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myAirInFlowRateActuator.setOutput(AirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myAirInFlowRateActuator);
    }

    private void createAirOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating AirOutFlowRateActuator with moduleName: "
                    + moduleName);
            AirOutFlowRateActuatorImpl myAirOutFlowRateActuatorImpl = new AirOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myAirOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new AirOutFlowRateActuatorPOATie(
                    myAirOutFlowRateActuatorImpl), myAirOutFlowRateActuatorImpl
                    .getModuleName(), myAirOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureAirOutFlowRateActuator(Node node) {
        AirOutFlowRateActuator myAirOutFlowRateActuator = AirOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myAirOutFlowRateActuator.setOutput(AirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myAirOutFlowRateActuator);
    }

    private void createCO2AirEnvironmentInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirEnvironmentInFlowRateActuator with moduleName: "
                            + moduleName);
            CO2AirEnvironmentInFlowRateActuatorImpl myCO2AirEnvironmentInFlowRateActuatorImpl = new CO2AirEnvironmentInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2AirEnvironmentInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new CO2AirEnvironmentInFlowRateActuatorPOATie(
                            myCO2AirEnvironmentInFlowRateActuatorImpl),
                    myCO2AirEnvironmentInFlowRateActuatorImpl.getModuleName(),
                    myCO2AirEnvironmentInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureCO2AirEnvironmentInFlowRateActuator(Node node) {
        CO2AirEnvironmentInFlowRateActuator myCO2AirEnvironmentInFlowRateActuator = CO2AirEnvironmentInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myCO2AirEnvironmentInFlowRateActuator.setOutput(CO2AirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myCO2AirEnvironmentInFlowRateActuator);
    }

    private void createCO2AirEnvironmentOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirEnvironmentOutFlowRateActuator with moduleName: "
                            + moduleName);
            CO2AirEnvironmentOutFlowRateActuatorImpl myCO2AirEnvironmentOutFlowRateActuatorImpl = new CO2AirEnvironmentOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2AirEnvironmentOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new CO2AirEnvironmentOutFlowRateActuatorPOATie(
                            myCO2AirEnvironmentOutFlowRateActuatorImpl),
                    myCO2AirEnvironmentOutFlowRateActuatorImpl.getModuleName(),
                    myCO2AirEnvironmentOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureCO2AirEnvironmentOutFlowRateActuator(Node node) {
        CO2AirEnvironmentOutFlowRateActuator myCO2AirEnvironmentOutFlowRateActuator = CO2AirEnvironmentOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myCO2AirEnvironmentOutFlowRateActuator.setOutput(CO2AirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myCO2AirEnvironmentOutFlowRateActuator);
    }

    private void createCO2AirStoreInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirStoreInFlowRateActuator with moduleName: "
                            + moduleName);
            CO2AirStoreInFlowRateActuatorImpl myCO2AirStoreInFlowRateActuatorImpl = new CO2AirStoreInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2AirStoreInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new CO2AirStoreInFlowRateActuatorPOATie(
                            myCO2AirStoreInFlowRateActuatorImpl),
                    myCO2AirStoreInFlowRateActuatorImpl.getModuleName(),
                    myCO2AirStoreInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureCO2AirStoreInFlowRateActuator(Node node) {
        CO2AirStoreInFlowRateActuator myCO2AirStoreInFlowRateActuator = CO2AirStoreInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myCO2AirStoreInFlowRateActuator.setOutput(CO2AirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myCO2AirStoreInFlowRateActuator);
    }

    private void createCO2AirStoreOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating CO2AirStoreOutFlowRateActuator with moduleName: "
                            + moduleName);
            CO2AirStoreOutFlowRateActuatorImpl myCO2AirStoreOutFlowRateActuatorImpl = new CO2AirStoreOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myCO2AirStoreOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new CO2AirStoreOutFlowRateActuatorPOATie(
                            myCO2AirStoreOutFlowRateActuatorImpl),
                    myCO2AirStoreOutFlowRateActuatorImpl.getModuleName(),
                    myCO2AirStoreOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureCO2AirStoreOutFlowRateActuator(Node node) {
        CO2AirStoreOutFlowRateActuator myCO2AirStoreOutFlowRateActuator = CO2AirStoreOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myCO2AirStoreOutFlowRateActuator.setOutput(CO2AirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myCO2AirStoreOutFlowRateActuator);
    }

    private void createO2AirEnvironmentInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirEnvironmentInFlowRateActuator with moduleName: "
                            + moduleName);
            O2AirEnvironmentInFlowRateActuatorImpl myO2AirEnvironmentInFlowRateActuatorImpl = new O2AirEnvironmentInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2AirEnvironmentInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new O2AirEnvironmentInFlowRateActuatorPOATie(
                            myO2AirEnvironmentInFlowRateActuatorImpl),
                    myO2AirEnvironmentInFlowRateActuatorImpl.getModuleName(),
                    myO2AirEnvironmentInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureO2AirEnvironmentInFlowRateActuator(Node node) {
        O2AirEnvironmentInFlowRateActuator myO2AirEnvironmentInFlowRateActuator = O2AirEnvironmentInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myO2AirEnvironmentInFlowRateActuator.setOutput(O2AirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myO2AirEnvironmentInFlowRateActuator);
    }

    private void createO2AirEnvironmentOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirEnvironmentOutFlowRateActuator with moduleName: "
                            + moduleName);
            O2AirEnvironmentOutFlowRateActuatorImpl myO2AirEnvironmentOutFlowRateActuatorImpl = new O2AirEnvironmentOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2AirEnvironmentOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new O2AirEnvironmentOutFlowRateActuatorPOATie(
                            myO2AirEnvironmentOutFlowRateActuatorImpl),
                    myO2AirEnvironmentOutFlowRateActuatorImpl.getModuleName(),
                    myO2AirEnvironmentOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureO2AirEnvironmentOutFlowRateActuator(Node node) {
        O2AirEnvironmentOutFlowRateActuator myO2AirEnvironmentOutFlowRateActuator = O2AirEnvironmentOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myO2AirEnvironmentOutFlowRateActuator.setOutput(O2AirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myO2AirEnvironmentOutFlowRateActuator);
    }

    private void createO2AirStoreInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirStoreInFlowRateActuator with moduleName: "
                            + moduleName);
            O2AirStoreInFlowRateActuatorImpl myO2AirStoreInFlowRateActuatorImpl = new O2AirStoreInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2AirStoreInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new O2AirStoreInFlowRateActuatorPOATie(
                    myO2AirStoreInFlowRateActuatorImpl),
                    myO2AirStoreInFlowRateActuatorImpl.getModuleName(),
                    myO2AirStoreInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureO2AirStoreInFlowRateActuator(Node node) {
        O2AirStoreInFlowRateActuator myO2AirStoreInFlowRateActuator = O2AirStoreInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myO2AirStoreInFlowRateActuator.setOutput(O2AirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myO2AirStoreInFlowRateActuator);
    }

    private void createO2AirStoreOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating O2AirStoreOutFlowRateActuator with moduleName: "
                            + moduleName);
            O2AirStoreOutFlowRateActuatorImpl myO2AirStoreOutFlowRateActuatorImpl = new O2AirStoreOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myO2AirStoreOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new O2AirStoreOutFlowRateActuatorPOATie(
                            myO2AirStoreOutFlowRateActuatorImpl),
                    myO2AirStoreOutFlowRateActuatorImpl.getModuleName(),
                    myO2AirStoreOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureO2AirStoreOutFlowRateActuator(Node node) {
        O2AirStoreOutFlowRateActuator myO2AirStoreOutFlowRateActuator = O2AirStoreOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myO2AirStoreOutFlowRateActuator.setOutput(O2AirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myO2AirStoreOutFlowRateActuator);
    }

    private void createWaterAirEnvironmentInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirEnvironmentInFlowRateActuator with moduleName: "
                            + moduleName);
            WaterAirEnvironmentInFlowRateActuatorImpl myWaterAirEnvironmentInFlowRateActuatorImpl = new WaterAirEnvironmentInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterAirEnvironmentInFlowRateActuatorImpl, node);
            BiosimServer
                    .registerServer(
                            new WaterAirEnvironmentInFlowRateActuatorPOATie(
                                    myWaterAirEnvironmentInFlowRateActuatorImpl),
                            myWaterAirEnvironmentInFlowRateActuatorImpl
                                    .getModuleName(),
                            myWaterAirEnvironmentInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureWaterAirEnvironmentInFlowRateActuator(Node node) {
        WaterAirEnvironmentInFlowRateActuator myWaterAirEnvironmentInFlowRateActuator = WaterAirEnvironmentInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myWaterAirEnvironmentInFlowRateActuator.setOutput(
                WaterAirConsumerHelper.narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myWaterAirEnvironmentInFlowRateActuator);
    }

    private void createWaterAirEnvironmentOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirEnvironmentOutFlowRateActuator with moduleName: "
                            + moduleName);
            WaterAirEnvironmentOutFlowRateActuatorImpl myWaterAirEnvironmentOutFlowRateActuatorImpl = new WaterAirEnvironmentOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterAirEnvironmentOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new WaterAirEnvironmentOutFlowRateActuatorPOATie(
                            myWaterAirEnvironmentOutFlowRateActuatorImpl),
                    myWaterAirEnvironmentOutFlowRateActuatorImpl
                            .getModuleName(),
                    myWaterAirEnvironmentOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureWaterAirEnvironmentOutFlowRateActuator(Node node) {
        WaterAirEnvironmentOutFlowRateActuator myWaterAirEnvironmentOutFlowRateActuator = WaterAirEnvironmentOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myWaterAirEnvironmentOutFlowRateActuator.setOutput(
                WaterAirProducerHelper.narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myWaterAirEnvironmentOutFlowRateActuator);
    }

    private void createWaterAirStoreInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirStoreInFlowRateActuator with moduleName: "
                            + moduleName);
            WaterAirStoreInFlowRateActuatorImpl myWaterAirStoreInFlowRateActuatorImpl = new WaterAirStoreInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterAirStoreInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new WaterAirStoreInFlowRateActuatorPOATie(
                            myWaterAirStoreInFlowRateActuatorImpl),
                    myWaterAirStoreInFlowRateActuatorImpl.getModuleName(),
                    myWaterAirStoreInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureWaterAirStoreInFlowRateActuator(Node node) {
        WaterAirStoreInFlowRateActuator myWaterAirStoreInFlowRateActuator = WaterAirStoreInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myWaterAirStoreInFlowRateActuator.setOutput(WaterAirConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myWaterAirStoreInFlowRateActuator);
    }

    private void createWaterAirStoreOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterAirStoreOutFlowRateActuator with moduleName: "
                            + moduleName);
            WaterAirStoreOutFlowRateActuatorImpl myWaterAirStoreOutFlowRateActuatorImpl = new WaterAirStoreOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterAirStoreOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new WaterAirStoreOutFlowRateActuatorPOATie(
                            myWaterAirStoreOutFlowRateActuatorImpl),
                    myWaterAirStoreOutFlowRateActuatorImpl.getModuleName(),
                    myWaterAirStoreOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureWaterAirStoreOutFlowRateActuator(Node node) {
        WaterAirStoreOutFlowRateActuator myWaterAirStoreOutFlowRateActuator = WaterAirStoreOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myWaterAirStoreOutFlowRateActuator.setOutput(WaterAirProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myWaterAirStoreOutFlowRateActuator);
    }

    private void createNitrogenAirEnvironmentInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirEnvironmentInFlowRateActuator with moduleName: "
                            + moduleName);
            NitrogenAirEnvironmentInFlowRateActuatorImpl myNitrogenAirEnvironmentInFlowRateActuatorImpl = new NitrogenAirEnvironmentInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenAirEnvironmentInFlowRateActuatorImpl, node);
            myActuators
                    .add(OrbUtils
                            .poaToCorbaObj(myNitrogenAirEnvironmentInFlowRateActuatorImpl));
            BiosimServer.registerServer(
                    new NitrogenAirEnvironmentInFlowRateActuatorPOATie(
                            myNitrogenAirEnvironmentInFlowRateActuatorImpl),
                    myNitrogenAirEnvironmentInFlowRateActuatorImpl
                            .getModuleName(),
                    myNitrogenAirEnvironmentInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureNitrogenAirEnvironmentInFlowRateActuator(Node node) {
        NitrogenAirEnvironmentInFlowRateActuator myNitrogenAirEnvironmentInFlowRateActuator = NitrogenAirEnvironmentInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myNitrogenAirEnvironmentInFlowRateActuator.setOutput(
                NitrogenAirConsumerHelper
                        .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myNitrogenAirEnvironmentInFlowRateActuator);
    }

    private void createNitrogenAirEnvironmentOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirEnvironmentOutFlowRateActuator with moduleName: "
                            + moduleName);
            NitrogenAirEnvironmentOutFlowRateActuatorImpl myNitrogenAirEnvironmentOutFlowRateActuatorImpl = new NitrogenAirEnvironmentOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenAirEnvironmentOutFlowRateActuatorImpl,
                    node);
            BiosimServer.registerServer(
                    new NitrogenAirEnvironmentOutFlowRateActuatorPOATie(
                            myNitrogenAirEnvironmentOutFlowRateActuatorImpl),
                    myNitrogenAirEnvironmentOutFlowRateActuatorImpl
                            .getModuleName(),
                    myNitrogenAirEnvironmentOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureNitrogenAirEnvironmentOutFlowRateActuator(Node node) {
        NitrogenAirEnvironmentOutFlowRateActuator myNitrogenAirEnvironmentOutFlowRateActuator = NitrogenAirEnvironmentOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myNitrogenAirEnvironmentOutFlowRateActuator.setOutput(
                NitrogenAirProducerHelper
                        .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myNitrogenAirEnvironmentOutFlowRateActuator);
    }

    private void createNitrogenAirStoreInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirStoreInFlowRateActuator with moduleName: "
                            + moduleName);
            NitrogenAirStoreInFlowRateActuatorImpl myNitrogenAirStoreInFlowRateActuatorImpl = new NitrogenAirStoreInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenAirStoreInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirStoreInFlowRateActuatorPOATie(
                            myNitrogenAirStoreInFlowRateActuatorImpl),
                    myNitrogenAirStoreInFlowRateActuatorImpl.getModuleName(),
                    myNitrogenAirStoreInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureNitrogenAirStoreInFlowRateActuator(Node node) {
        NitrogenAirStoreInFlowRateActuator myNitrogenAirStoreInFlowRateActuator = NitrogenAirStoreInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myNitrogenAirStoreInFlowRateActuator.setOutput(
                NitrogenAirConsumerHelper
                        .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myNitrogenAirStoreInFlowRateActuator);
    }

    private void createNitrogenAirStoreOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating NitrogenAirStoreOutFlowRateActuator with moduleName: "
                            + moduleName);
            NitrogenAirStoreOutFlowRateActuatorImpl myNitrogenAirStoreOutFlowRateActuatorImpl = new NitrogenAirStoreOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myNitrogenAirStoreOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new NitrogenAirStoreOutFlowRateActuatorPOATie(
                            myNitrogenAirStoreOutFlowRateActuatorImpl),
                    myNitrogenAirStoreOutFlowRateActuatorImpl.getModuleName(),
                    myNitrogenAirStoreOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureNitrogenAirStoreOutFlowRateActuator(Node node) {
        NitrogenAirStoreOutFlowRateActuator myNitrogenAirStoreOutFlowRateActuator = NitrogenAirStoreOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myNitrogenAirStoreOutFlowRateActuator.setOutput(
                NitrogenAirProducerHelper
                        .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myNitrogenAirStoreOutFlowRateActuator);
    }

    private void crawlEnvironmentActuators(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("AirInFlowRateActuator")) {
                if (firstPass)
                    createAirInFlowRateActuator(child);
                else
                    configureAirInFlowRateActuator(child);
            } else if (childName.equals("AirOutFlowRateActuator")) {
                if (firstPass)
                    createAirOutFlowRateActuator(child);
                else
                    configureAirOutFlowRateActuator(child);
            } else if (childName.equals("CO2AirEnvironmentInFlowRateActuator")) {
                if (firstPass)
                    createCO2AirEnvironmentInFlowRateActuator(child);
                else
                    configureCO2AirEnvironmentInFlowRateActuator(child);
            } else if (childName.equals("CO2AirEnvironmentOutFlowRateActuator")) {
                if (firstPass)
                    createCO2AirEnvironmentOutFlowRateActuator(child);
                else
                    configureCO2AirEnvironmentOutFlowRateActuator(child);
            } else if (childName.equals("CO2AirStoreInFlowRateActuator")) {
                if (firstPass)
                    createCO2AirStoreInFlowRateActuator(child);
                else
                    configureCO2AirStoreInFlowRateActuator(child);
            } else if (childName.equals("CO2AirStoreOutFlowRateActuator")) {
                if (firstPass)
                    createCO2AirStoreOutFlowRateActuator(child);
                else
                    configureCO2AirStoreOutFlowRateActuator(child);
            } else if (childName.equals("O2AirEnvironmentInFlowRateActuator")) {
                if (firstPass)
                    createO2AirEnvironmentInFlowRateActuator(child);
                else
                    configureO2AirEnvironmentInFlowRateActuator(child);
            } else if (childName.equals("O2AirEnvironmentOutFlowRateActuator")) {
                if (firstPass)
                    createO2AirEnvironmentOutFlowRateActuator(child);
                else
                    configureO2AirEnvironmentOutFlowRateActuator(child);
            } else if (childName.equals("O2AirStoreInFlowRateActuator")) {
                if (firstPass)
                    createO2AirStoreInFlowRateActuator(child);
                else
                    configureO2AirStoreInFlowRateActuator(child);
            } else if (childName.equals("O2AirStoreOutFlowRateActuator")) {
                if (firstPass)
                    createO2AirStoreOutFlowRateActuator(child);
                else
                    configureO2AirStoreOutFlowRateActuator(child);
            } else if (childName.equals("WaterAirStoreInFlowRateActuator")) {
                if (firstPass)
                    createWaterAirStoreInFlowRateActuator(child);
                else
                    configureWaterAirStoreInFlowRateActuator(child);
            } else if (childName.equals("WaterAirStoreOutFlowRateActuator")) {
                if (firstPass)
                    createWaterAirStoreOutFlowRateActuator(child);
                else
                    configureWaterAirStoreOutFlowRateActuator(child);
            } else if (childName
                    .equals("WaterAirEnvironmentInFlowRateActuator")) {
                if (firstPass)
                    createWaterAirEnvironmentInFlowRateActuator(child);
                else
                    configureWaterAirEnvironmentInFlowRateActuator(child);
            } else if (childName
                    .equals("WaterAirEnvironmentOutFlowRateActuator")) {
                if (firstPass)
                    createWaterAirEnvironmentOutFlowRateActuator(child);
                else
                    configureWaterAirEnvironmentOutFlowRateActuator(child);
            } else if (childName
                    .equals("NitrogenAirEnvironmentInFlowRateActuator")) {
                if (firstPass)
                    createNitrogenAirEnvironmentInFlowRateActuator(child);
                else
                    configureNitrogenAirEnvironmentInFlowRateActuator(child);
            } else if (childName
                    .equals("NitrogenAirEnvironmentOutFlowRateActuator")) {
                if (firstPass)
                    createNitrogenAirEnvironmentOutFlowRateActuator(child);
                else
                    configureNitrogenAirEnvironmentOutFlowRateActuator(child);
            } else if (childName.equals("NitrogenAirStoreInFlowRateActuator")) {
                if (firstPass)
                    createNitrogenAirStoreInFlowRateActuator(child);
                else
                    configureNitrogenAirStoreInFlowRateActuator(child);
            } else if (childName.equals("NitrogenAirStoreOutFlowRateActuator")) {
                if (firstPass)
                    createNitrogenAirStoreOutFlowRateActuator(child);
                else
                    configureNitrogenAirStoreOutFlowRateActuator(child);
            }
            child = child.getNextSibling();
        }
    }

    //Food
    private void createBiomassInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating BiomassInFlowRateActuator with moduleName: "
                            + moduleName);
            BiomassInFlowRateActuatorImpl myBiomassInFlowRateActuatorImpl = new BiomassInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myBiomassInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new BiomassInFlowRateActuatorPOATie(
                    myBiomassInFlowRateActuatorImpl),
                    myBiomassInFlowRateActuatorImpl.getModuleName(),
                    myBiomassInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureBiomassInFlowRateActuator(Node node) {
        BiomassInFlowRateActuator myBiomassInFlowRateActuator = BiomassInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myBiomassInFlowRateActuator.setOutput(BiomassConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myBiomassInFlowRateActuator);
    }

    private void createBiomassOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating BiomassOutFlowRateActuator with moduleName: "
                            + moduleName);
            BiomassOutFlowRateActuatorImpl myBiomassOutFlowRateActuatorImpl = new BiomassOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myBiomassOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new BiomassOutFlowRateActuatorPOATie(
                    myBiomassOutFlowRateActuatorImpl),
                    myBiomassOutFlowRateActuatorImpl.getModuleName(),
                    myBiomassOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureBiomassOutFlowRateActuator(Node node) {
        BiomassOutFlowRateActuator myBiomassOutFlowRateActuator = BiomassOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myBiomassOutFlowRateActuator.setOutput(BiomassProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myBiomassOutFlowRateActuator);
    }

    private void createFoodInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating FoodInFlowRateActuator with moduleName: "
                    + moduleName);
            FoodInFlowRateActuatorImpl myFoodInFlowRateActuatorImpl = new FoodInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myFoodInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new FoodInFlowRateActuatorPOATie(
                    myFoodInFlowRateActuatorImpl), myFoodInFlowRateActuatorImpl
                    .getModuleName(), myFoodInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureFoodInFlowRateActuator(Node node) {
        FoodInFlowRateActuator myFoodInFlowRateActuator = FoodInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myFoodInFlowRateActuator.setOutput(FoodConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myFoodInFlowRateActuator);
    }

    private void createFoodOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating FoodOutFlowRateActuator with moduleName: "
                    + moduleName);
            FoodOutFlowRateActuatorImpl myFoodOutFlowRateActuatorImpl = new FoodOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myFoodOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new FoodOutFlowRateActuatorPOATie(
                    myFoodOutFlowRateActuatorImpl),
                    myFoodOutFlowRateActuatorImpl.getModuleName(),
                    myFoodOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureFoodOutFlowRateActuator(Node node) {
        FoodOutFlowRateActuator myFoodOutFlowRateActuator = FoodOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myFoodOutFlowRateActuator.setOutput(FoodProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myFoodOutFlowRateActuator);
    }

    private void createPlantingActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PlantingActuator with moduleName: "
                    + moduleName);
            PlantingActuatorImpl myPlantingActuatorImpl = new PlantingActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPlantingActuatorImpl, node);
            BiosimServer.registerServer(new PlantingActuatorPOATie(
                    myPlantingActuatorImpl), myPlantingActuatorImpl
                    .getModuleName(), myPlantingActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configurePlantingActuator(Node node) {
        PlantingActuator myPlantingActuator = PlantingActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myPlantingActuator.setOutput(BiomassRSHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))), getShelfIndex(node));
        myActuators.add(myPlantingActuator);
    }

    private void createHarvestingActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating HarvestingActuator with moduleName: "
                    + moduleName);
            HarvestingActuatorImpl myHarvestingActuatorImpl = new HarvestingActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myHarvestingActuatorImpl, node);
            BiosimServer.registerServer(new HarvestingActuatorPOATie(
                    myHarvestingActuatorImpl), myHarvestingActuatorImpl
                    .getModuleName(), myHarvestingActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureHarvestingActuator(Node node) {
        HarvestingActuator myHarvestingActuator = HarvestingActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myHarvestingActuator.setOutput(BiomassRSHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))), getShelfIndex(node));
        myActuators.add(myHarvestingActuator);
    }

    private void crawlFoodActuators(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("BiomassInFlowRateActuator")) {
                if (firstPass)
                    createBiomassInFlowRateActuator(child);
                else
                    configureBiomassInFlowRateActuator(child);
            } else if (childName.equals("BiomassOutFlowRateActuator")) {
                if (firstPass)
                    createBiomassOutFlowRateActuator(child);
                else
                    configureBiomassOutFlowRateActuator(child);
            } else if (childName.equals("FoodInFlowRateActuator")) {
                if (firstPass)
                    createFoodInFlowRateActuator(child);
                else
                    configureFoodInFlowRateActuator(child);
            } else if (childName.equals("FoodOutFlowRateActuator")) {
                if (firstPass)
                    createFoodOutFlowRateActuator(child);
                else
                    configureFoodOutFlowRateActuator(child);
            } else if (childName.equals("HarvestingActuator")) {
                if (firstPass)
                    createHarvestingActuator(child);
                else
                    configureHarvestingActuator(child);
            } else if (childName.equals("PlantingActuator")) {
                if (firstPass)
                    createPlantingActuator(child);
                else
                    configurePlantingActuator(child);
            }
            child = child.getNextSibling();
        }
    }

    //Power
    private void createPowerInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PowerInFlowRateActuator with moduleName: "
                    + moduleName);
            PowerInFlowRateActuatorImpl myPowerInFlowRateActuatorImpl = new PowerInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPowerInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new PowerInFlowRateActuatorPOATie(
                    myPowerInFlowRateActuatorImpl),
                    myPowerInFlowRateActuatorImpl.getModuleName(),
                    myPowerInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configurePowerInFlowRateActuator(Node node) {
        PowerInFlowRateActuator myPowerInFlowRateActuator = PowerInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myPowerInFlowRateActuator.setOutput(PowerConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myPowerInFlowRateActuator);
    }

    private void createPowerOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PowerOutFlowRateActuator with moduleName: "
                            + moduleName);
            PowerOutFlowRateActuatorImpl myPowerOutFlowRateActuatorImpl = new PowerOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPowerOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new PowerOutFlowRateActuatorPOATie(
                    myPowerOutFlowRateActuatorImpl),
                    myPowerOutFlowRateActuatorImpl.getModuleName(),
                    myPowerOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configurePowerOutFlowRateActuator(Node node) {
        PowerOutFlowRateActuator myPowerOutFlowRateActuator = PowerOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myPowerOutFlowRateActuator.setOutput(PowerProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myPowerOutFlowRateActuator);
    }

    private void crawlPowerActuators(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PowerInFlowRateActuator")) {
                if (firstPass)
                    createPowerInFlowRateActuator(child);
                else
                    configurePowerInFlowRateActuator(child);
            } else if (childName.equals("PowerOutFlowRateActuator")) {
                if (firstPass)
                    createPowerOutFlowRateActuator(child);
                else
                    configurePowerOutFlowRateActuator(child);
            }
            child = child.getNextSibling();
        }
    }

    //Water
    private void createPotableWaterInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterInFlowRateActuator with moduleName: "
                            + moduleName);
            PotableWaterInFlowRateActuatorImpl myPotableWaterInFlowRateActuatorImpl = new PotableWaterInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPotableWaterInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new PotableWaterInFlowRateActuatorPOATie(
                            myPotableWaterInFlowRateActuatorImpl),
                    myPotableWaterInFlowRateActuatorImpl.getModuleName(),
                    myPotableWaterInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configurePotableWaterInFlowRateActuator(Node node) {
        PotableWaterInFlowRateActuator myPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myPotableWaterInFlowRateActuator.setOutput(PotableWaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myPotableWaterInFlowRateActuator);
    }

    private void createPotableWaterOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PotableWaterOutFlowRateActuator with moduleName: "
                            + moduleName);
            PotableWaterOutFlowRateActuatorImpl myPotableWaterOutFlowRateActuatorImpl = new PotableWaterOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myPotableWaterOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new PotableWaterOutFlowRateActuatorPOATie(
                            myPotableWaterOutFlowRateActuatorImpl),
                    myPotableWaterOutFlowRateActuatorImpl.getModuleName(),
                    myPotableWaterOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configurePotableWaterOutFlowRateActuator(Node node) {
        PotableWaterOutFlowRateActuator myPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myPotableWaterOutFlowRateActuator.setOutput(PotableWaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myPotableWaterOutFlowRateActuator);
    }

    private void createGreyWaterInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterInFlowRateActuator with moduleName: "
                            + moduleName);
            GreyWaterInFlowRateActuatorImpl myGreyWaterInFlowRateActuatorImpl = new GreyWaterInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myGreyWaterInFlowRateActuatorImpl, node);
            myActuators.add(OrbUtils
                    .poaToCorbaObj(myGreyWaterInFlowRateActuatorImpl));
            BiosimServer.registerServer(new GreyWaterInFlowRateActuatorPOATie(
                    myGreyWaterInFlowRateActuatorImpl),
                    myGreyWaterInFlowRateActuatorImpl.getModuleName(),
                    myGreyWaterInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureGreyWaterInFlowRateActuator(Node node) {
        GreyWaterInFlowRateActuator myGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myGreyWaterInFlowRateActuator.setOutput(GreyWaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myGreyWaterInFlowRateActuator);
    }

    private void createGreyWaterOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating GreyWaterOutFlowRateActuator with moduleName: "
                            + moduleName);
            GreyWaterOutFlowRateActuatorImpl myGreyWaterOutFlowRateActuatorImpl = new GreyWaterOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myGreyWaterOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new GreyWaterOutFlowRateActuatorPOATie(
                    myGreyWaterOutFlowRateActuatorImpl),
                    myGreyWaterOutFlowRateActuatorImpl.getModuleName(),
                    myGreyWaterOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureGreyWaterOutFlowRateActuator(Node node) {
        GreyWaterOutFlowRateActuator myGreyWaterOutFlowRateActuator = GreyWaterOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myGreyWaterOutFlowRateActuator.setOutput(GreyWaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myGreyWaterOutFlowRateActuator);
    }

    private void createDirtyWaterInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterInFlowRateActuator with moduleName: "
                            + moduleName);
            DirtyWaterInFlowRateActuatorImpl myDirtyWaterInFlowRateActuatorImpl = new DirtyWaterInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDirtyWaterInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new DirtyWaterInFlowRateActuatorPOATie(
                    myDirtyWaterInFlowRateActuatorImpl),
                    myDirtyWaterInFlowRateActuatorImpl.getModuleName(),
                    myDirtyWaterInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureDirtyWaterInFlowRateActuator(Node node) {
        DirtyWaterInFlowRateActuator myDirtyWaterInFlowRateActuator = DirtyWaterInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myDirtyWaterInFlowRateActuator.setOutput(DirtyWaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myDirtyWaterInFlowRateActuator);
    }

    private void createDirtyWaterOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DirtyWaterOutFlowRateActuator with moduleName: "
                            + moduleName);
            DirtyWaterOutFlowRateActuatorImpl myDirtyWaterOutFlowRateActuatorImpl = new DirtyWaterOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDirtyWaterOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(
                    new DirtyWaterOutFlowRateActuatorPOATie(
                            myDirtyWaterOutFlowRateActuatorImpl),
                    myDirtyWaterOutFlowRateActuatorImpl.getModuleName(),
                    myDirtyWaterOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureDirtyWaterOutFlowRateActuator(Node node) {
        DirtyWaterOutFlowRateActuator myDirtyWaterOutFlowRateActuator = DirtyWaterOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myDirtyWaterOutFlowRateActuator.setOutput(DirtyWaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myDirtyWaterOutFlowRateActuator);
    }

    private void createWaterInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating WaterInFlowRateActuator with moduleName: "
                    + moduleName);
            WaterInFlowRateActuatorImpl myWaterInFlowRateActuatorImpl = new WaterInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new WaterInFlowRateActuatorPOATie(
                    myWaterInFlowRateActuatorImpl),
                    myWaterInFlowRateActuatorImpl.getModuleName(),
                    myWaterInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureWaterInFlowRateActuator(Node node) {
        WaterInFlowRateActuator myWaterInFlowRateActuator = WaterInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myWaterInFlowRateActuator.setOutput(WaterConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myWaterInFlowRateActuator);
    }

    private void createWaterOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating WaterOutFlowRateActuator with moduleName: "
                            + moduleName);
            WaterOutFlowRateActuatorImpl myWaterOutFlowRateActuatorImpl = new WaterOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myWaterOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new WaterOutFlowRateActuatorPOATie(
                    myWaterOutFlowRateActuatorImpl),
                    myWaterOutFlowRateActuatorImpl.getModuleName(),
                    myWaterOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureWaterOutFlowRateActuator(Node node) {
        WaterOutFlowRateActuator myWaterOutFlowRateActuator = WaterOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myWaterOutFlowRateActuator.setOutput(WaterProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myWaterOutFlowRateActuator);
    }

    private void crawlWaterActuators(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PotableWaterInFlowRateActuator")) {
                if (firstPass)
                    createPotableWaterInFlowRateActuator(child);
                else
                    configurePotableWaterInFlowRateActuator(child);
            } else if (childName.equals("PotableWaterOutFlowRateActuator")) {
                if (firstPass)
                    createPotableWaterOutFlowRateActuator(child);
                else
                    configurePotableWaterOutFlowRateActuator(child);
            } else if (childName.equals("GreyWaterInFlowRateActuator")) {
                if (firstPass)
                    createGreyWaterInFlowRateActuator(child);
                else
                    configureGreyWaterInFlowRateActuator(child);
            } else if (childName.equals("GreyWaterOutFlowRateActuator")) {
                if (firstPass)
                    createGreyWaterOutFlowRateActuator(child);
                else
                    configureGreyWaterOutFlowRateActuator(child);
            } else if (childName.equals("DirtyWaterInFlowRateActuator")) {
                if (firstPass)
                    createDirtyWaterInFlowRateActuator(child);
                else
                    configureDirtyWaterInFlowRateActuator(child);
            } else if (childName.equals("DirtyWaterOutFlowRateActuator")) {
                if (firstPass)
                    createDirtyWaterOutFlowRateActuator(child);
                else
                    configureDirtyWaterOutFlowRateActuator(child);
            } else if (childName.equals("WaterInFlowRateActuator")) {
                if (firstPass)
                    createWaterInFlowRateActuator(child);
                else
                    configureWaterInFlowRateActuator(child);
            } else if (childName.equals("WaterOutFlowRateActuator")) {
                if (firstPass)
                    createWaterOutFlowRateActuator(child);
                else
                    configureWaterOutFlowRateActuator(child);
            }
            child = child.getNextSibling();
        }
    }

    //Waste
    private void createDryWasteInFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteInFlowRateActuator with moduleName: "
                            + moduleName);
            DryWasteInFlowRateActuatorImpl myDryWasteInFlowRateActuatorImpl = new DryWasteInFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDryWasteInFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new DryWasteInFlowRateActuatorPOATie(
                    myDryWasteInFlowRateActuatorImpl),
                    myDryWasteInFlowRateActuatorImpl.getModuleName(),
                    myDryWasteInFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureDryWasteInFlowRateActuator(Node node) {
        DryWasteInFlowRateActuator myDryWasteInFlowRateActuator = DryWasteInFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myDryWasteInFlowRateActuator.setOutput(DryWasteConsumerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myDryWasteInFlowRateActuator);
    }

    private void createDryWasteOutFlowRateActuator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating DryWasteOutFlowRateActuator with moduleName: "
                            + moduleName);
            DryWasteOutFlowRateActuatorImpl myDryWasteOutFlowRateActuatorImpl = new DryWasteOutFlowRateActuatorImpl(
                    myID, moduleName);
            BioInitializer.setupBioModule(myDryWasteOutFlowRateActuatorImpl, node);
            BiosimServer.registerServer(new DryWasteOutFlowRateActuatorPOATie(
                    myDryWasteOutFlowRateActuatorImpl),
                    myDryWasteOutFlowRateActuatorImpl.getModuleName(),
                    myDryWasteOutFlowRateActuatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage (moduleName);
    }

    private void configureDryWasteOutFlowRateActuator(Node node) {
        DryWasteOutFlowRateActuator myDryWasteOutFlowRateActuator = DryWasteOutFlowRateActuatorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer.getModuleName(node)));
        myDryWasteOutFlowRateActuator.setOutput(DryWasteProducerHelper
                .narrow(BioInitializer.grabModule(myID, getOutputName(node))),
                getFlowRateIndex(node));
        myActuators.add(myDryWasteOutFlowRateActuator);
    }

    private void crawlWasteActuators(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("DryWasteInFlowRateActuator")) {
                if (firstPass)
                    createDryWasteInFlowRateActuator(child);
                else
                    configureDryWasteInFlowRateActuator(child);
            } else if (childName.equals("DryWasteOutFlowRateActuator")) {
                if (firstPass)
                    createDryWasteOutFlowRateActuator(child);
                else
                    configureDryWasteOutFlowRateActuator(child);
            }
            child = child.getNextSibling();
        }
    }

    public void crawlActuators(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("air")) {
                crawlAirActuators(child, firstPass);
            } else if (childName.equals("environment")) {
                crawlEnvironmentActuators(child, firstPass);

            } else if (childName.equals("food")) {
                crawlFoodActuators(child, firstPass);

            } else if (childName.equals("power")) {
                crawlPowerActuators(child, firstPass);

            } else if (childName.equals("water")) {
                crawlWaterActuators(child, firstPass);

            } else if (childName.equals("waste")) {
                crawlWasteActuators(child, firstPass);

            }
            child = child.getNextSibling();
        }
    }
	/**
	 * @return Returns the myActuators.
	 */
	public List getActuators() {
		return myActuators;
	}
}