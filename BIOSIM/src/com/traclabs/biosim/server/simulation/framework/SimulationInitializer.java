package com.traclabs.biosim.server.simulation.framework;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.BioModuleHelper;
import com.traclabs.biosim.idl.simulation.air.AirRS;
import com.traclabs.biosim.idl.simulation.air.AirRSHelper;
import com.traclabs.biosim.idl.simulation.air.AirRSPOATie;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.CO2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.CO2StorePOATie;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.air.H2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.H2StorePOATie;
import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.air.NitrogenStoreHelper;
import com.traclabs.biosim.idl.simulation.air.NitrogenStorePOATie;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.air.O2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.O2StorePOATie;
import com.traclabs.biosim.idl.simulation.crew.Activity;
import com.traclabs.biosim.idl.simulation.crew.ActivityHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupPOATie;
import com.traclabs.biosim.idl.simulation.crew.EVAActivityHelper;
import com.traclabs.biosim.idl.simulation.crew.Sex;
import com.traclabs.biosim.idl.simulation.environment.Dehumidifier;
import com.traclabs.biosim.idl.simulation.environment.DehumidifierHelper;
import com.traclabs.biosim.idl.simulation.environment.DehumidifierPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentPOATie;
import com.traclabs.biosim.idl.simulation.food.BioMatter;
import com.traclabs.biosim.idl.simulation.food.BiomassRS;
import com.traclabs.biosim.idl.simulation.food.BiomassRSHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassRSPOATie;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassStorePOATie;
import com.traclabs.biosim.idl.simulation.food.FoodMatter;
import com.traclabs.biosim.idl.simulation.food.FoodProcessor;
import com.traclabs.biosim.idl.simulation.food.FoodProcessorHelper;
import com.traclabs.biosim.idl.simulation.food.FoodProcessorPOATie;
import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.food.FoodStoreHelper;
import com.traclabs.biosim.idl.simulation.food.FoodStorePOATie;
import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.idl.simulation.framework.Accumulator;
import com.traclabs.biosim.idl.simulation.framework.AccumulatorHelper;
import com.traclabs.biosim.idl.simulation.framework.AccumulatorPOATie;
import com.traclabs.biosim.idl.simulation.framework.AirConsumer;
import com.traclabs.biosim.idl.simulation.framework.AirProducer;
import com.traclabs.biosim.idl.simulation.framework.BiomassConsumer;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducer;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumer;
import com.traclabs.biosim.idl.simulation.framework.CO2AirProducer;
import com.traclabs.biosim.idl.simulation.framework.CO2Consumer;
import com.traclabs.biosim.idl.simulation.framework.CO2Producer;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumer;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducer;
import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumer;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducer;
import com.traclabs.biosim.idl.simulation.framework.FoodConsumer;
import com.traclabs.biosim.idl.simulation.framework.FoodProducer;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumer;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducer;
import com.traclabs.biosim.idl.simulation.framework.H2Consumer;
import com.traclabs.biosim.idl.simulation.framework.H2Producer;
import com.traclabs.biosim.idl.simulation.framework.Injector;
import com.traclabs.biosim.idl.simulation.framework.InjectorHelper;
import com.traclabs.biosim.idl.simulation.framework.InjectorPOATie;
import com.traclabs.biosim.idl.simulation.framework.LightConsumer;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumer;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirProducer;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumer;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducer;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumer;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducer;
import com.traclabs.biosim.idl.simulation.framework.O2Consumer;
import com.traclabs.biosim.idl.simulation.framework.O2Producer;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumer;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducer;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumer;
import com.traclabs.biosim.idl.simulation.framework.PowerProducer;
import com.traclabs.biosim.idl.simulation.framework.SimBioModule;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumer;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducer;
import com.traclabs.biosim.idl.simulation.framework.WaterConsumer;
import com.traclabs.biosim.idl.simulation.framework.WaterProducer;
import com.traclabs.biosim.idl.simulation.mission.EVAMission;
import com.traclabs.biosim.idl.simulation.mission.EVAMissionHelper;
import com.traclabs.biosim.idl.simulation.mission.EVAMissionPOATie;
import com.traclabs.biosim.idl.simulation.power.PowerPS;
import com.traclabs.biosim.idl.simulation.power.PowerPSHelper;
import com.traclabs.biosim.idl.simulation.power.PowerPSPOATie;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.power.PowerStoreHelper;
import com.traclabs.biosim.idl.simulation.power.PowerStorePOATie;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStoreHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStorePOATie;
import com.traclabs.biosim.idl.simulation.waste.Incinerator;
import com.traclabs.biosim.idl.simulation.waste.IncineratorHelper;
import com.traclabs.biosim.idl.simulation.waste.IncineratorPOATie;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStorePOATie;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStorePOATie;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStorePOATie;
import com.traclabs.biosim.idl.simulation.water.WaterRS;
import com.traclabs.biosim.idl.simulation.water.WaterRSHelper;
import com.traclabs.biosim.idl.simulation.water.WaterRSPOATie;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.idl.simulation.water.WaterStoreHelper;
import com.traclabs.biosim.server.framework.BioInitializer;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.server.simulation.air.AirRSImpl;
import com.traclabs.biosim.server.simulation.air.AirRSLinearImpl;
import com.traclabs.biosim.server.simulation.air.CO2StoreImpl;
import com.traclabs.biosim.server.simulation.air.H2StoreImpl;
import com.traclabs.biosim.server.simulation.air.NitrogenStoreImpl;
import com.traclabs.biosim.server.simulation.air.O2StoreImpl;
import com.traclabs.biosim.server.simulation.crew.ActivityImpl;
import com.traclabs.biosim.server.simulation.crew.CrewGroupImpl;
import com.traclabs.biosim.server.simulation.crew.EVAActivityImpl;
import com.traclabs.biosim.server.simulation.crew.Schedule;
import com.traclabs.biosim.server.simulation.environment.DehumidifierImpl;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.food.BiomassRSImpl;
import com.traclabs.biosim.server.simulation.food.BiomassStoreImpl;
import com.traclabs.biosim.server.simulation.food.FoodProcessorImpl;
import com.traclabs.biosim.server.simulation.food.FoodStoreImpl;
import com.traclabs.biosim.server.simulation.mission.EVAMissionImpl;
import com.traclabs.biosim.server.simulation.power.NuclearPowerPS;
import com.traclabs.biosim.server.simulation.power.PowerPSImpl;
import com.traclabs.biosim.server.simulation.power.PowerStoreImpl;
import com.traclabs.biosim.server.simulation.power.SolarPowerPS;
import com.traclabs.biosim.server.simulation.waste.DryWasteStoreImpl;
import com.traclabs.biosim.server.simulation.waste.IncineratorImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStoreImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterStoreImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterStoreImpl;
import com.traclabs.biosim.server.simulation.water.WaterRSImpl;
import com.traclabs.biosim.server.simulation.water.WaterRSLinearImpl;
import com.traclabs.biosim.server.simulation.water.WaterRSMatlabImpl;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class SimulationInitializer {
    private int myID = 0;

    private List myActiveSimModules;

    private List myPassiveSimModules;

    private List myPrioritySimModules;

    private Logger myLogger;

    /** Default constructor. */
    public SimulationInitializer(int pID) {
        myID = pID;
        myLogger = Logger.getLogger(this.getClass());
        myPassiveSimModules = new Vector();
        myActiveSimModules = new Vector();
        myPrioritySimModules = new Vector();
    }

    public void crawlSimModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("air")) {
                crawlAirModules(child, firstPass);
            } else if (childName.equals("crew")) {
                crawlCrewModules(child, firstPass);
            } else if (childName.equals("environment")) {
                crawlEnvironmentModules(child, firstPass);
            } else if (childName.equals("food")) {
                crawlFoodModules(child, firstPass);
            } else if (childName.equals("framework")) {
                crawlFrameworkModules(child, firstPass);
            } else if (childName.equals("power")) {
                crawlPowerModules(child, firstPass);
            } else if (childName.equals("water")) {
                crawlWaterModules(child, firstPass);
            } else if (childName.equals("waste")) {
                crawlWasteModules(child, firstPass);
            } else if (childName.equals("mission")) {
                crawlMissionModules(child, firstPass);
            }
            child = child.getNextSibling();
        }
    }

    private static float getStoreLevel(Node node) {
        float level = 0f;
        try {
            level = Float.parseFloat(node.getAttributes().getNamedItem("level")
                    .getNodeValue());
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        return level;
    }

    private static float getStoreCapacity(Node node) {
        float capacity = 0f;
        try {
            capacity = Float.parseFloat(node.getAttributes().getNamedItem(
                    "capacity").getNodeValue());
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        return capacity;
    }

    private static int getStoreResupplyFrequency(Node node) {
        int frequency = 0;
        try {
            frequency = Integer.parseInt(node.getAttributes().getNamedItem(
                    "resupplyFrequency").getNodeValue());
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        return frequency;
    }

    private static float getStoreResupplyAmount(Node node) {
        float amount = 0f;
        try {
            amount = Float.parseFloat(node.getAttributes().getNamedItem(
                    "resupplyAmount").getNodeValue());
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        return amount;
    }

    private static float[] getMaxFlowRates(Node node) {
        if (node == null)
            return new float[0];
        String arrayString = node.getAttributes().getNamedItem("maxFlowRates")
                .getNodeValue();
        StringTokenizer tokenizer = new StringTokenizer(arrayString);
        float[] maxFlowRates = new float[tokenizer.countTokens()];

        for (int i = 0; tokenizer.hasMoreTokens(); i++) {
            try {
                maxFlowRates[i] = Float.parseFloat(tokenizer.nextToken());
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        }
        return maxFlowRates;
    }

    private static float[] getDesiredFlowRates(Node node) {
        if (node == null)
            return new float[0];
        String arrayString = node.getAttributes().getNamedItem(
                "desiredFlowRates").getNodeValue();
        StringTokenizer tokenizer = new StringTokenizer(arrayString);
        float[] desiredFlowRates = new float[tokenizer.countTokens()];

        for (int i = 0; tokenizer.hasMoreTokens(); i++) {
            try {
                desiredFlowRates[i] = Float.parseFloat(tokenizer.nextToken());
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        }
        return desiredFlowRates;
    }

    private void configureSimBioModule(SimBioModule pModule, Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("powerConsumer")) {
                PowerConsumer myPowerConsumer = (PowerConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                PowerStore[] inputs = new PowerStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = PowerStoreHelper.narrow(modules[i]);
                myPowerConsumer.setPowerInputs(inputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("potableWaterConsumer")) {
                PotableWaterConsumer myPotableWaterConsumer = (PotableWaterConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                PotableWaterStore[] inputs = new PotableWaterStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = PotableWaterStoreHelper.narrow(modules[i]);
                myPotableWaterConsumer.setPotableWaterInputs(inputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("greyWaterConsumer")) {
                GreyWaterConsumer myGreyWaterConsumer = (GreyWaterConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                GreyWaterStore[] inputs = new GreyWaterStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = GreyWaterStoreHelper.narrow(modules[i]);
                myGreyWaterConsumer.setGreyWaterInputs(inputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("dirtyWaterConsumer")) {
                DirtyWaterConsumer myDirtyWaterConsumer = (DirtyWaterConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                DirtyWaterStore[] inputs = new DirtyWaterStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = DirtyWaterStoreHelper.narrow(modules[i]);
                myDirtyWaterConsumer.setDirtyWaterInputs(inputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("waterConsumer")) {
                WaterConsumer myWaterConsumer = (WaterConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                WaterStore[] inputs = new WaterStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = WaterStoreHelper.narrow(modules[i]);
                myWaterConsumer.setWaterInputs(inputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("airConsumer")) {
                AirConsumer myAirConsumer = (AirConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                SimEnvironment[] inputs = new SimEnvironment[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = SimEnvironmentHelper.narrow(modules[i]);
                myAirConsumer.setAirInputs(inputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("H2Consumer")) {
                H2Consumer myH2Consumer = (H2Consumer) (pModule);
                BioModule[] modules = getInputs(child);
                H2Store[] inputs = new H2Store[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = H2StoreHelper.narrow(modules[i]);
                myH2Consumer.setH2Inputs(inputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("nitrogenConsumer")) {
                NitrogenConsumer myNitrogenConsumer = (NitrogenConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                NitrogenStore[] inputs = new NitrogenStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = NitrogenStoreHelper.narrow(modules[i]);
                myNitrogenConsumer.setNitrogenInputs(inputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("O2Consumer")) {
                O2Consumer myO2Consumer = (O2Consumer) (pModule);
                BioModule[] modules = getInputs(child);
                O2Store[] inputs = new O2Store[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = O2StoreHelper.narrow(modules[i]);
                myO2Consumer.setO2Inputs(inputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("CO2Consumer")) {
                CO2Consumer myCO2Consumer = (CO2Consumer) (pModule);
                BioModule[] modules = getInputs(child);
                CO2Store[] inputs = new CO2Store[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = CO2StoreHelper.narrow(modules[i]);
                myCO2Consumer.setCO2Inputs(inputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("lightConsumer")) {
                LightConsumer myLightConsumer = (LightConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                SimEnvironment[] inputs = new SimEnvironment[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = SimEnvironmentHelper.narrow(modules[i]);
                myLightConsumer.setLightInput(inputs[0]);
            } else if (childName.equals("biomassConsumer")) {
                BiomassConsumer myBiomassConsumer = (BiomassConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                BiomassStore[] inputs = new BiomassStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = BiomassStoreHelper.narrow(modules[i]);
                myBiomassConsumer.setBiomassInputs(inputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("dryWasteConsumer")) {
                DryWasteConsumer myDryWasteConsumer = (DryWasteConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                DryWasteStore[] inputs = new DryWasteStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = DryWasteStoreHelper.narrow(modules[i]);
                myDryWasteConsumer.setDryWasteInputs(inputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("foodConsumer")) {
                FoodConsumer myFoodConsumer = (FoodConsumer) (pModule);
                BioModule[] modules = getInputs(child);
                FoodStore[] inputs = new FoodStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    inputs[i] = FoodStoreHelper.narrow(modules[i]);
                myFoodConsumer.setFoodInputs(inputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("O2AirConsumer")) {
                O2AirConsumer myO2AirConsumer = (O2AirConsumer) (pModule);
                Node environmentNode = getEnvironmentNode(child);
                Node storeNode = getStoreNode(child);
                BioModule[] environmentModules = getInputs(environmentNode);
                BioModule[] storeModules = getInputs(storeNode);
                SimEnvironment[] environmentInputs = new SimEnvironment[environmentModules.length];
                O2Store[] storeInputs = new O2Store[storeModules.length];
                for (int i = 0; i < environmentModules.length; i++)
                    environmentInputs[i] = SimEnvironmentHelper
                            .narrow(environmentModules[i]);
                for (int i = 0; i < storeModules.length; i++)
                    storeInputs[i] = O2StoreHelper.narrow(storeModules[i]);
                myO2AirConsumer.setO2AirEnvironmentInputs(environmentInputs,
                        getMaxFlowRates(environmentNode),
                        getDesiredFlowRates(environmentNode));
                myO2AirConsumer.setO2AirStoreInputs(storeInputs,
                        getMaxFlowRates(storeNode),
                        getDesiredFlowRates(storeNode));
            } else if (childName.equals("CO2AirConsumer")) {
                CO2AirConsumer myCO2AirConsumer = (CO2AirConsumer) (pModule);
                Node environmentNode = getEnvironmentNode(child);
                Node storeNode = getStoreNode(child);
                BioModule[] environmentModules = getInputs(environmentNode);
                BioModule[] storeModules = getInputs(storeNode);
                SimEnvironment[] environmentInputs = new SimEnvironment[environmentModules.length];
                CO2Store[] storeInputs = new CO2Store[storeModules.length];
                for (int i = 0; i < environmentModules.length; i++)
                    environmentInputs[i] = SimEnvironmentHelper
                            .narrow(environmentModules[i]);
                for (int i = 0; i < storeModules.length; i++)
                    storeInputs[i] = CO2StoreHelper.narrow(storeModules[i]);
                myCO2AirConsumer.setCO2AirEnvironmentInputs(environmentInputs,
                        getMaxFlowRates(environmentNode),
                        getDesiredFlowRates(environmentNode));
                myCO2AirConsumer.setCO2AirStoreInputs(storeInputs,
                        getMaxFlowRates(storeNode),
                        getDesiredFlowRates(storeNode));
            } else if (childName.equals("nitrogenAirConsumer")) {
                NitrogenAirConsumer myNitrogenAirConsumer = (NitrogenAirConsumer) (pModule);
                Node environmentNode = getEnvironmentNode(child);
                Node storeNode = getStoreNode(child);
                BioModule[] environmentModules = getInputs(environmentNode);
                BioModule[] storeModules = getInputs(storeNode);
                SimEnvironment[] environmentInputs = new SimEnvironment[environmentModules.length];
                NitrogenStore[] storeInputs = new NitrogenStore[storeModules.length];
                for (int i = 0; i < environmentModules.length; i++)
                    environmentInputs[i] = SimEnvironmentHelper
                            .narrow(environmentModules[i]);
                for (int i = 0; i < storeModules.length; i++)
                    storeInputs[i] = NitrogenStoreHelper
                            .narrow(storeModules[i]);
                myNitrogenAirConsumer.setNitrogenAirEnvironmentInputs(
                        environmentInputs, getMaxFlowRates(environmentNode),
                        getDesiredFlowRates(environmentNode));
                myNitrogenAirConsumer.setNitrogenAirStoreInputs(storeInputs,
                        getMaxFlowRates(storeNode),
                        getDesiredFlowRates(storeNode));
            } else if (childName.equals("waterAirConsumer")) {
                WaterAirConsumer myWaterAirConsumer = (WaterAirConsumer) (pModule);
                Node environmentNode = getEnvironmentNode(child);
                Node storeNode = getStoreNode(child);
                BioModule[] environmentModules = getInputs(environmentNode);
                BioModule[] storeModules = getInputs(storeNode);
                SimEnvironment[] environmentInputs = new SimEnvironment[environmentModules.length];
                WaterStore[] storeInputs = new WaterStore[storeModules.length];
                for (int i = 0; i < environmentModules.length; i++)
                    environmentInputs[i] = SimEnvironmentHelper
                            .narrow(environmentModules[i]);
                for (int i = 0; i < storeModules.length; i++)
                    storeInputs[i] = PotableWaterStoreHelper
                            .narrow(storeModules[i]);
                myWaterAirConsumer.setWaterAirEnvironmentInputs(
                        environmentInputs, getMaxFlowRates(environmentNode),
                        getDesiredFlowRates(environmentNode));
                myWaterAirConsumer.setWaterAirStoreInputs(storeInputs,
                        getMaxFlowRates(storeNode),
                        getDesiredFlowRates(storeNode));
            } else if (childName.equals("powerProducer")) {
                PowerProducer myPowerProducer = (PowerProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                PowerStore[] outputs = new PowerStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = PowerStoreHelper.narrow(modules[i]);
                myPowerProducer.setPowerOutputs(outputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("potableWaterProducer")) {
                PotableWaterProducer myPotableWaterProducer = (PotableWaterProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                PotableWaterStore[] outputs = new PotableWaterStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = PotableWaterStoreHelper.narrow(modules[i]);
                myPotableWaterProducer.setPotableWaterOutputs(outputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("greyWaterProducer")) {
                GreyWaterProducer myGreyWaterProducer = (GreyWaterProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                GreyWaterStore[] outputs = new GreyWaterStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = GreyWaterStoreHelper.narrow(modules[i]);
                myGreyWaterProducer.setGreyWaterOutputs(outputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("dirtyWaterProducer")) {
                DirtyWaterProducer myDirtyWaterProducer = (DirtyWaterProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                DirtyWaterStore[] outputs = new DirtyWaterStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = DirtyWaterStoreHelper.narrow(modules[i]);
                myDirtyWaterProducer.setDirtyWaterOutputs(outputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("waterProducer")) {
                WaterProducer myWaterProducer = (WaterProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                WaterStore[] outputs = new WaterStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = WaterStoreHelper.narrow(modules[i]);
                myWaterProducer.setWaterOutputs(outputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("airProducer")) {
                AirProducer myAirProducer = (AirProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                SimEnvironment[] outputs = new SimEnvironment[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = SimEnvironmentHelper.narrow(modules[i]);
                myAirProducer.setAirOutputs(outputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("H2Producer")) {
                H2Producer myH2Producer = (H2Producer) (pModule);
                BioModule[] modules = getOutputs(child);
                H2Store[] outputs = new H2Store[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = H2StoreHelper.narrow(modules[i]);
                myH2Producer.setH2Outputs(outputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("nitrogenProducer")) {
                NitrogenProducer myNitrogenProducer = (NitrogenProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                NitrogenStore[] outputs = new NitrogenStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = NitrogenStoreHelper.narrow(modules[i]);
                myNitrogenProducer.setNitrogenOutputs(outputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("O2Producer")) {
                O2Producer myO2Producer = (O2Producer) (pModule);
                BioModule[] modules = getOutputs(child);
                O2Store[] outputs = new O2Store[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = O2StoreHelper.narrow(modules[i]);
                myO2Producer.setO2Outputs(outputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("CO2Producer")) {
                CO2Producer myCO2Producer = (CO2Producer) (pModule);
                BioModule[] modules = getOutputs(child);
                CO2Store[] outputs = new CO2Store[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = CO2StoreHelper.narrow(modules[i]);
                myCO2Producer.setCO2Outputs(outputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("biomassProducer")) {
                BiomassProducer myBiomassProducer = (BiomassProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                BiomassStore[] outputs = new BiomassStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = BiomassStoreHelper.narrow(modules[i]);
                myBiomassProducer.setBiomassOutputs(outputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("dryWasteProducer")) {
                DryWasteProducer myDryWasteProducer = (DryWasteProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                DryWasteStore[] outputs = new DryWasteStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = DryWasteStoreHelper.narrow(modules[i]);
                myDryWasteProducer.setDryWasteOutputs(outputs,
                        getMaxFlowRates(child), getDesiredFlowRates(child));
            } else if (childName.equals("foodProducer")) {
                FoodProducer myFoodProducer = (FoodProducer) (pModule);
                BioModule[] modules = getOutputs(child);
                FoodStore[] outputs = new FoodStore[modules.length];
                for (int i = 0; i < modules.length; i++)
                    outputs[i] = FoodStoreHelper.narrow(modules[i]);
                myFoodProducer.setFoodOutputs(outputs, getMaxFlowRates(child),
                        getDesiredFlowRates(child));
            } else if (childName.equals("O2AirProducer")) {
                O2AirProducer myO2AirProducer = (O2AirProducer) (pModule);
                Node environmentNode = getEnvironmentNode(child);
                Node storeNode = getStoreNode(child);
                BioModule[] environmentModules = getOutputs(environmentNode);
                BioModule[] storeModules = getOutputs(storeNode);
                SimEnvironment[] environmentOutputs = new SimEnvironment[environmentModules.length];
                O2Store[] storeOutputs = new O2Store[storeModules.length];
                for (int i = 0; i < environmentModules.length; i++)
                    environmentOutputs[i] = SimEnvironmentHelper
                            .narrow(environmentModules[i]);
                for (int i = 0; i < storeModules.length; i++)
                    storeOutputs[i] = O2StoreHelper.narrow(storeModules[i]);
                myO2AirProducer.setO2AirEnvironmentOutputs(environmentOutputs,
                        getMaxFlowRates(environmentNode),
                        getDesiredFlowRates(environmentNode));
                myO2AirProducer.setO2AirStoreOutputs(storeOutputs,
                        getMaxFlowRates(storeNode),
                        getDesiredFlowRates(storeNode));
            } else if (childName.equals("CO2AirProducer")) {
                CO2AirProducer myCO2AirProducer = (CO2AirProducer) (pModule);
                Node environmentNode = getEnvironmentNode(child);
                Node storeNode = getStoreNode(child);
                BioModule[] environmentModules = getOutputs(environmentNode);
                BioModule[] storeModules = getOutputs(storeNode);
                SimEnvironment[] environmentOutputs = new SimEnvironment[environmentModules.length];
                CO2Store[] storeOutputs = new CO2Store[storeModules.length];
                for (int i = 0; i < environmentModules.length; i++)
                    environmentOutputs[i] = SimEnvironmentHelper
                            .narrow(environmentModules[i]);
                for (int i = 0; i < storeModules.length; i++)
                    storeOutputs[i] = CO2StoreHelper.narrow(storeModules[i]);
                myCO2AirProducer.setCO2AirEnvironmentOutputs(
                        environmentOutputs, getMaxFlowRates(environmentNode),
                        getDesiredFlowRates(environmentNode));
                myCO2AirProducer.setCO2AirStoreOutputs(storeOutputs,
                        getMaxFlowRates(storeNode),
                        getDesiredFlowRates(storeNode));
            } else if (childName.equals("nitrogenAirProducer")) {
                NitrogenAirProducer myNitrogenAirProducer = (NitrogenAirProducer) (pModule);
                Node environmentNode = getEnvironmentNode(child);
                Node storeNode = getStoreNode(child);
                BioModule[] environmentModules = getOutputs(environmentNode);
                BioModule[] storeModules = getOutputs(storeNode);
                SimEnvironment[] environmentOutputs = new SimEnvironment[environmentModules.length];
                NitrogenStore[] storeOutputs = new NitrogenStore[storeModules.length];
                for (int i = 0; i < environmentModules.length; i++)
                    environmentOutputs[i] = SimEnvironmentHelper
                            .narrow(environmentModules[i]);
                for (int i = 0; i < storeModules.length; i++)
                    storeOutputs[i] = NitrogenStoreHelper
                            .narrow(storeModules[i]);
                myNitrogenAirProducer.setNitrogenAirEnvironmentOutputs(
                        environmentOutputs, getMaxFlowRates(environmentNode),
                        getDesiredFlowRates(environmentNode));
                myNitrogenAirProducer.setNitrogenAirStoreOutputs(storeOutputs,
                        getMaxFlowRates(storeNode),
                        getDesiredFlowRates(storeNode));
            } else if (childName.equals("waterAirProducer")) {
                WaterAirProducer myWaterAirProducer = (WaterAirProducer) (pModule);
                Node environmentNode = getEnvironmentNode(child);
                Node storeNode = getStoreNode(child);
                BioModule[] environmentModules = getOutputs(environmentNode);
                BioModule[] storeModules = getOutputs(storeNode);
                SimEnvironment[] environmentOutputs = new SimEnvironment[environmentModules.length];
                WaterStore[] storeOutputs = new WaterStore[storeModules.length];
                for (int i = 0; i < environmentModules.length; i++)
                    environmentOutputs[i] = SimEnvironmentHelper
                            .narrow(environmentModules[i]);
                for (int i = 0; i < storeModules.length; i++)
                    storeOutputs[i] = WaterStoreHelper.narrow(storeModules[i]);
                myWaterAirProducer.setWaterAirEnvironmentOutputs(
                        environmentOutputs, getMaxFlowRates(environmentNode),
                        getDesiredFlowRates(environmentNode));
                myWaterAirProducer.setWaterAirStoreOutputs(storeOutputs,
                        getMaxFlowRates(storeNode),
                        getDesiredFlowRates(storeNode));
            }
            child = child.getNextSibling();
        }
    }

    private static Node getEnvironmentNode(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            if (child.getNodeName().startsWith("environment"))
                return child;
            child = child.getNextSibling();
        }
        return null;
    }

    private static Node getStoreNode(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            if (child.getNodeName().startsWith("store"))
                return child;
            child = child.getNextSibling();
        }
        return null;
    }

    private BioModule[] getInputs(Node node) {
        if (node == null)
            return new BioModule[0];
        String arrayString = node.getAttributes().getNamedItem("inputs")
                .getNodeValue();
        StringTokenizer tokenizer = new StringTokenizer(arrayString);
        BioModule[] inputs = new BioModule[tokenizer.countTokens()];
        for (int i = 0; tokenizer.hasMoreTokens(); i++) {
            try {
                inputs[i] = BioModuleHelper.narrow(OrbUtils.getNamingContext(
                        myID).resolve_str(tokenizer.nextToken()));
                myLogger.debug("Fetched " + inputs[i].getModuleName());
            } catch (org.omg.CORBA.UserException e) {
                e.printStackTrace();
            }
        }
        return inputs;
    }

    private BioModule[] getOutputs(Node node) {
        if (node == null)
            return new BioModule[0];
        String arrayString = node.getAttributes().getNamedItem("outputs")
                .getNodeValue();
        StringTokenizer tokenizer = new StringTokenizer(arrayString);
        BioModule[] outputs = new BioModule[tokenizer.countTokens()];
        for (int i = 0; tokenizer.hasMoreTokens(); i++) {
            try {
                outputs[i] = BioModuleHelper.narrow(OrbUtils.getNamingContext(
                        myID).resolve_str(tokenizer.nextToken()));
                myLogger.debug("Fetched " + outputs[i].getModuleName());
            } catch (org.omg.CORBA.UserException e) {
                e.printStackTrace();
            }
        }
        return outputs;
    }

    private void setupStore(StoreImpl pStore, Node pNode) {
        pStore.setInitialCapacity(getStoreCapacity(pNode));
        pStore.setInitialLevel(getStoreLevel(pNode));
        pStore.setResupply(getStoreResupplyFrequency(pNode),
                getStoreResupplyAmount(pNode));
        BioInitializer.setupBioModule(pStore, pNode);
    }

    private void createAirRS(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating AirRS with moduleName: " + moduleName);
            String implementationString = node.getAttributes().getNamedItem(
                    "implementation").getNodeValue();
            if (implementationString.equals("LINEAR")) {
                myLogger.debug("created linear WaterRS...");
                AirRSLinearImpl myAirRSImpl = new AirRSLinearImpl(myID,
                        moduleName);
                BioInitializer.setupBioModule(myAirRSImpl, node);
                BiosimServer.registerServer(new AirRSPOATie(myAirRSImpl),
                        myAirRSImpl.getModuleName(), myAirRSImpl.getID());
            } else {
                AirRSImpl myAirRSImpl = new AirRSImpl(myID, moduleName);
                BioInitializer.setupBioModule(myAirRSImpl, node);
                BiosimServer.registerServer(new AirRSPOATie(myAirRSImpl),
                        myAirRSImpl.getModuleName(), myAirRSImpl.getID());
            }
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureAirRS(Node node) {
        AirRS myAirRS = AirRSHelper.narrow(BioInitializer.grabModule(myID,
                BioInitializer.getModuleName(node)));
        configureSimBioModule(myAirRS, node);
        /*
         * String operationModeString = node.getAttributes().getNamedItem(
         * "operationMode").getNodeValue(); if
         * (operationModeString.equals("FULL"))
         * myAirRS.setOperationMode(AirRSOperationMode.FULL); else if
         * (operationModeString.equals("MOST"))
         * myAirRS.setOperationMode(AirRSOperationMode.MOST); else if
         * (operationModeString.equals("LESS"))
         * myAirRS.setOperationMode(AirRSOperationMode.LESS); else if
         * (operationModeString.equals("OFF"))
         * myAirRS.setOperationMode(AirRSOperationMode.OFF); else
         * myLogger.error("AirRSOperationMode not found!");
         */
        myActiveSimModules.add(myAirRS);
    }

    private void createO2Store(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            O2StoreImpl myO2StoreImpl = new O2StoreImpl(myID, moduleName);
            setupStore(myO2StoreImpl, node);
            BiosimServer.registerServer(new O2StorePOATie(myO2StoreImpl),
                    myO2StoreImpl.getModuleName(), myO2StoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void createCO2Store(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            CO2StoreImpl myCO2StoreImpl = new CO2StoreImpl(myID, moduleName);
            setupStore(myCO2StoreImpl, node);
            BiosimServer.registerServer(new CO2StorePOATie(myCO2StoreImpl),
                    myCO2StoreImpl.getModuleName(), myCO2StoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void createH2Store(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            H2StoreImpl myH2StoreImpl = new H2StoreImpl(myID, moduleName);
            setupStore(myH2StoreImpl, node);
            BiosimServer.registerServer(new H2StorePOATie(myH2StoreImpl),
                    myH2StoreImpl.getModuleName(), myH2StoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void createNitrogenStore(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            NitrogenStoreImpl myNitrogenStoreImpl = new NitrogenStoreImpl(myID,
                    moduleName);
            setupStore(myNitrogenStoreImpl, node);
            BiosimServer.registerServer(new NitrogenStorePOATie(
                    myNitrogenStoreImpl), myNitrogenStoreImpl.getModuleName(),
                    myNitrogenStoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void crawlAirModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("AirRS")) {
                if (firstPass)
                    createAirRS(child);
                else
                    configureAirRS(child);

            } else if (childName.equals("O2Store")) {
                if (firstPass)
                    createO2Store(child);
                else
                    myPassiveSimModules.add(O2StoreHelper.narrow(BioInitializer
                            .grabModule(myID, BioInitializer
                                    .getModuleName(child))));
            } else if (childName.equals("CO2Store")) {
                if (firstPass)
                    createCO2Store(child);
                else
                    myPassiveSimModules.add(CO2StoreHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));

            } else if (childName.equals("H2Store")) {
                if (firstPass)
                    createH2Store(child);
                else
                    myPassiveSimModules.add(H2StoreHelper.narrow(BioInitializer
                            .grabModule(myID, BioInitializer
                                    .getModuleName(child))));

            } else if (childName.equals("NitrogenStore")) {
                if (firstPass)
                    createNitrogenStore(child);
                else
                    myPassiveSimModules.add(NitrogenStoreHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));

            }
            child = child.getNextSibling();
        }
    }

    private Activity createActivity(Node node, CrewGroupImpl crew) {
        int length = 0;
        int intensity = 0;
        try {
            length = Integer.parseInt(node.getAttributes().getNamedItem(
                    "length").getNodeValue());
            intensity = Integer.parseInt(node.getAttributes().getNamedItem(
                    "intensity").getNodeValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String name = node.getAttributes().getNamedItem("name").getNodeValue();
        Node activityTypeNode = node.getAttributes().getNamedItem("xsi:type");
        if (activityTypeNode != null) {
            if (activityTypeNode.getNodeValue().equals("EVAActivityType")) {
                myLogger.debug("Type is " + activityTypeNode.getNodeValue());
                String evaCrewGroupName = node.getAttributes().getNamedItem(
                        "evaCrewGroup").getNodeValue();
                EVAActivityImpl newEVAActivityImpl = new EVAActivityImpl(name,
                        length, intensity, crew.getModuleName(),
                        evaCrewGroupName);
                return EVAActivityHelper.narrow(ActivityHelper.narrow(OrbUtils
                        .poaToCorbaObj(newEVAActivityImpl)));
            } else {
                myLogger
                        .error("Activity not of expected type even though it was explicitly declared! (can only be EVA type right now)");
                myLogger.error("type was: " + activityTypeNode.getNodeValue()
                        + ", should be: EVAActivityType");
                return null;
            }
        } else {
            ActivityImpl newActivityImpl = new ActivityImpl(name, length,
                    intensity);
            return ActivityHelper.narrow(OrbUtils
                    .poaToCorbaObj(newActivityImpl));
        }
    }

    private Schedule createSchedule(Node node, CrewGroupImpl crew) {
        Schedule newSchedule = new Schedule();
        Node child = node.getFirstChild();
        while (child != null) {
            if (child.getNodeName().equals("activity")) {
                Activity newActivity = createActivity(child, crew);
                newSchedule.insertActivityInSchedule(newActivity);
            }
            child = child.getNextSibling();
        }
        return newSchedule;
    }

    private void createCrewPerson(Node node, CrewGroupImpl pCrewGroupImpl,
            CrewGroup pCrewGroup) {
        Node child = node.getFirstChild();
        Schedule schedule = null;
        while (child != null) {
            if (child.getNodeName().equals("schedule"))
                schedule = createSchedule(
                        node.getFirstChild().getNextSibling(), pCrewGroupImpl);
            child = child.getNextSibling();
        }
        String name = node.getAttributes().getNamedItem("name").getNodeValue();
        Sex sex;
        if (node.getAttributes().getNamedItem("sex").getNodeValue().equals(
                "FEMALE"))
            sex = Sex.female;
        else
            sex = Sex.male;
        float age = 0f;
        float weight = 0f;
        int arrivalDate = 0;
        int departureDate = 0;
        try {
            age = Float.parseFloat(node.getAttributes().getNamedItem("age")
                    .getNodeValue());
            weight = Float.parseFloat(node.getAttributes().getNamedItem(
                    "weight").getNodeValue());
            arrivalDate = Integer.parseInt(node.getAttributes().getNamedItem(
                    "arrivalDate").getNodeValue());
            departureDate = Integer.parseInt(node.getAttributes().getNamedItem(
                    "departureDate").getNodeValue());
            if (departureDate < 0)
                departureDate = Integer.MAX_VALUE;
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        pCrewGroupImpl.createCrewPerson(name, age, weight, sex, arrivalDate,
                departureDate, schedule, pCrewGroup);
    }

    private void createCrewGroup(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating CrewGroup with moduleName: " + moduleName);
            CrewGroupImpl myCrewGroupImpl = new CrewGroupImpl(myID, moduleName);
            BioInitializer.setupBioModule(myCrewGroupImpl, node);
            BiosimServer.registerServer(new CrewGroupPOATie(myCrewGroupImpl),
                    myCrewGroupImpl.getModuleName(), myCrewGroupImpl.getID());

            //Create crew members

            CrewGroup myCrewGroup = CrewGroupHelper.narrow(BioInitializer
                    .grabModule(myID, myCrewGroupImpl.getModuleName()));
            Node child = node.getFirstChild();
            while (child != null) {
                if (child.getNodeName().equals("crewPerson"))
                    createCrewPerson(child, myCrewGroupImpl, myCrewGroup);
                child = child.getNextSibling();
            }
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureCrewGroup(Node node) {
        CrewGroup myCrewGroup = CrewGroupHelper.narrow(BioInitializer
                .grabModule(myID, BioInitializer.getModuleName(node)));
        configureSimBioModule(myCrewGroup, node);
        myActiveSimModules.add(myCrewGroup);
    }

    private void crawlCrewModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CrewGroup")) {
                if (firstPass)
                    createCrewGroup(child);
                else
                    configureCrewGroup(child);

            }
            child = child.getNextSibling();
        }
    }

    private void createSimEnvironment(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating SimEnvironment with moduleName: "
                    + moduleName);
            SimEnvironmentImpl mySimEnvironmentImpl = null;
            float CO2Moles = 0f;
            float O2Moles = 0f;
            float waterMoles = 0f;
            float otherMoles = 0f;
            float nitrogenMoles = 0f;
            float volume = 0f;
            float leakRate = 0f;
            float dayLength = 0f;
            float hourOfDayStart = 0f;
            float maxLumens = 0f;
            float airlockVolume = 0f;
            Node CO2MolesNode = null;
            Node O2MolesNode = null;
            Node waterMolesNode = null;
            Node otherMolesNode = null;
            Node nitrogenMolesNode = null;
            try {
                volume = Float.parseFloat(node.getAttributes().getNamedItem(
                        "initialVolume").getNodeValue());
                CO2MolesNode = node.getAttributes().getNamedItem(
                        "initialCO2Moles");
                O2MolesNode = node.getAttributes().getNamedItem(
                        "initialO2Moles");
                waterMolesNode = node.getAttributes().getNamedItem(
                        "initialWaterMoles");
                otherMolesNode = node.getAttributes().getNamedItem(
                        "initialOtherMoles");
                otherMolesNode = node.getAttributes().getNamedItem(
                        "initialNitrogenMoles");
                if (CO2MolesNode != null)
                    CO2Moles = Float.parseFloat(CO2MolesNode.getNodeValue());
                if (O2MolesNode != null)
                    O2Moles = Float.parseFloat(O2MolesNode.getNodeValue());
                if (waterMolesNode != null)
                    waterMoles = Float
                            .parseFloat(waterMolesNode.getNodeValue());
                if (otherMolesNode != null)
                    otherMoles = Float
                            .parseFloat(otherMolesNode.getNodeValue());
                if (nitrogenMolesNode != null)
                    nitrogenMoles = Float.parseFloat(nitrogenMolesNode
                            .getNodeValue());
                leakRate = Float.parseFloat(node.getAttributes().getNamedItem(
                        "leakRate").getNodeValue());
                dayLength = Float.parseFloat(node.getAttributes().getNamedItem(
                        "dayLength").getNodeValue());
                hourOfDayStart = Float.parseFloat(node.getAttributes()
                        .getNamedItem("hourOfDayStart").getNodeValue());
                maxLumens = Float.parseFloat(node.getAttributes().getNamedItem(
                        "maxLumens").getNodeValue());
                airlockVolume = Float.parseFloat(node.getAttributes()
                        .getNamedItem("airlockVolume").getNodeValue());
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
            if ((CO2MolesNode != null) || (O2MolesNode != null)
                    || (waterMolesNode != null) || (otherMolesNode != null)
                    || (nitrogenMolesNode != null))
                mySimEnvironmentImpl = new SimEnvironmentImpl(CO2Moles,
                        O2Moles, otherMoles, waterMoles, nitrogenMoles, volume,
                        moduleName, myID);
            else
                mySimEnvironmentImpl = new SimEnvironmentImpl(myID, volume,
                        moduleName);
            mySimEnvironmentImpl.setLeakRate(leakRate);
            mySimEnvironmentImpl.setDayLength(dayLength);
            mySimEnvironmentImpl.setHourOfDayStart(hourOfDayStart);
            mySimEnvironmentImpl.setMaxLumens(maxLumens);
            mySimEnvironmentImpl.setAirlockVolume(airlockVolume);
            BioInitializer.setupBioModule(mySimEnvironmentImpl, node);
            BiosimServer.registerServer(new SimEnvironmentPOATie(
                    mySimEnvironmentImpl),
                    mySimEnvironmentImpl.getModuleName(), mySimEnvironmentImpl
                            .getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void createDehumidifier(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating Dehumidifier with moduleName: "
                    + moduleName);
            DehumidifierImpl myDehumidifierImpl = new DehumidifierImpl(myID,
                    moduleName);
            BioInitializer.setupBioModule(myDehumidifierImpl, node);
            BiosimServer.registerServer(new DehumidifierPOATie(
                    myDehumidifierImpl), myDehumidifierImpl.getModuleName(),
                    myDehumidifierImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureDehumidifier(Node node) {
        Dehumidifier myDehumidifier = DehumidifierHelper.narrow(BioInitializer
                .grabModule(myID, BioInitializer.getModuleName(node)));
        configureSimBioModule(myDehumidifier, node);
        myPrioritySimModules.add(myDehumidifier);
    }

    private void crawlEnvironmentModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("SimEnvironment")) {
                if (firstPass)
                    createSimEnvironment(child);
                else
                    myPassiveSimModules.add(SimEnvironmentHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));
            }
            if (childName.equals("Dehumidifier")) {
                if (firstPass)
                    createDehumidifier(child);
                else
                    configureDehumidifier(child);
            }
            child = child.getNextSibling();
        }
    }

    private void createAccumulator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating Accumulator with moduleName: "
                    + moduleName);
            AccumulatorImpl myAccumulatorImpl = new AccumulatorImpl(myID,
                    moduleName);
            BioInitializer.setupBioModule(myAccumulatorImpl, node);
            BiosimServer.registerServer(
                    new AccumulatorPOATie(myAccumulatorImpl), myAccumulatorImpl
                            .getModuleName(), myAccumulatorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureAccumulator(Node node) {
        Accumulator myAccumulator = AccumulatorHelper.narrow(BioInitializer
                .grabModule(myID, BioInitializer.getModuleName(node)));
        configureSimBioModule(myAccumulator, node);
        myActiveSimModules.add(myAccumulator);
    }

    private void createInjector(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating Injector with moduleName: " + moduleName);
            InjectorImpl myInjectorImpl = new InjectorImpl(myID, moduleName);
            BioInitializer.setupBioModule(myInjectorImpl, node);
            BiosimServer.registerServer(new InjectorPOATie(myInjectorImpl),
                    myInjectorImpl.getModuleName(), myInjectorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureInjector(Node node) {
        Injector myInjector = InjectorHelper.narrow(BioInitializer.grabModule(
                myID, BioInitializer.getModuleName(node)));
        configureSimBioModule(myInjector, node);
        myActiveSimModules.add(myInjector);
    }

    private void crawlFrameworkModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("Accumulator")) {
                if (firstPass)
                    createAccumulator(child);
                else
                    configureAccumulator(child);
            } else if (childName.equals("Injector")) {
                if (firstPass)
                    createInjector(child);
                else
                    configureInjector(child);
            }
            child = child.getNextSibling();
        }
    }

    private static PlantType getCropType(Node node) {
        String cropString = node.getAttributes().getNamedItem("cropType")
                .getNodeValue();
        if (cropString.equals("DRY_BEAN"))
            return PlantType.DRY_BEAN;
        else if (cropString.equals("LETTUCE"))
            return PlantType.LETTUCE;
        else if (cropString.equals("PEANUT"))
            return PlantType.PEANUT;
        else if (cropString.equals("RICE"))
            return PlantType.RICE;
        else if (cropString.equals("SOYBEAN"))
            return PlantType.SOYBEAN;
        else if (cropString.equals("SWEET_POTATO"))
            return PlantType.SWEET_POTATO;
        else if (cropString.equals("TOMATO"))
            return PlantType.TOMATO;
        else if (cropString.equals("WHEAT"))
            return PlantType.WHEAT;
        else if (cropString.equals("WHITE_POTATO"))
            return PlantType.WHITE_POTATO;
        else
            return PlantType.UNKNOWN_PLANT;
    }

    private static float getCropArea(Node node) {
        float area = 0f;
        try {
            area = Float.parseFloat(node.getAttributes().getNamedItem(
                    "cropArea").getNodeValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return area;
    }

    private static int getCropStartDay(Node node) {
        int startDay = 0;
        try {
            startDay = Integer.parseInt(node.getAttributes().getNamedItem(
                    "startTick").getNodeValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return startDay;
    }

    private void createBiomassRS(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating BiomassRS with moduleName: " + moduleName);
            BiomassRSImpl myBiomassRSImpl = new BiomassRSImpl(myID, moduleName);
            boolean autoHarvestAndReplant = node.getAttributes().getNamedItem(
                    "autoHarvestAndReplant").getNodeValue().equals("true");
            myBiomassRSImpl
                    .setAutoHarvestAndReplantEnabled(autoHarvestAndReplant);
            BioInitializer.setupBioModule(myBiomassRSImpl, node);
            Node child = node.getFirstChild();
            while (child != null) {
                if (child.getNodeName().equals("shelf"))
                    myBiomassRSImpl.createNewShelf(getCropType(child),
                            getCropArea(child), getCropStartDay(child));
                child = child.getNextSibling();
            }
            BiosimServer.registerServer(new BiomassRSPOATie(myBiomassRSImpl),
                    myBiomassRSImpl.getModuleName(), myBiomassRSImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureBiomassRS(Node node) {
        BiomassRS myBiomassRS = BiomassRSHelper.narrow(BioInitializer
                .grabModule(myID, BioInitializer.getModuleName(node)));
        configureSimBioModule(myBiomassRS, node);
        myActiveSimModules.add(myBiomassRS);
    }

    private void createFoodProcessor(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating FoodProcessor with moduleName: "
                    + moduleName);
            FoodProcessorImpl myFoodProcessorImpl = new FoodProcessorImpl(myID,
                    moduleName);
            BioInitializer.setupBioModule(myFoodProcessorImpl, node);
            BiosimServer.registerServer(new FoodProcessorPOATie(
                    myFoodProcessorImpl), myFoodProcessorImpl.getModuleName(),
                    myFoodProcessorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureFoodProcessor(Node node) {
        FoodProcessor myFoodProcessor = FoodProcessorHelper
                .narrow(BioInitializer.grabModule(myID, BioInitializer
                        .getModuleName(node)));
        configureSimBioModule(myFoodProcessor, node);
        myActiveSimModules.add(myFoodProcessor);
    }

    private void createBiomassStore(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating BiomassStore with moduleName: "
                    + moduleName);
            BiomassStoreImpl myBiomassStoreImpl = new BiomassStoreImpl(myID,
                    moduleName);
            BioInitializer.setupBioModule(myBiomassStoreImpl, node);
            float inedibleFraction = Float.parseFloat(node.getAttributes().getNamedItem(
            "inedibleFraction").getNodeValue());
            float edibleWaterContent = Float.parseFloat(node.getAttributes().getNamedItem(
            "edibleWaterContent").getNodeValue());
            float inedibleWaterContent = Float.parseFloat(node.getAttributes().getNamedItem(
            "inedibleWaterContent").getNodeValue());
            PlantType theCropType = getCropType(node);
            BioMatter newBioMatter = new BioMatter(getStoreLevel(node), inedibleFraction, edibleWaterContent, inedibleWaterContent, theCropType);
            myBiomassStoreImpl.setInitialCapacity(getStoreCapacity(node));
            myBiomassStoreImpl.setInitialBioMatterLevel(newBioMatter);
            myBiomassStoreImpl.setResupply(getStoreResupplyFrequency(node),
                    getStoreResupplyAmount(node));
            BiosimServer.registerServer(new BiomassStorePOATie(
                    myBiomassStoreImpl), myBiomassStoreImpl.getModuleName(),
                    myBiomassStoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void createFoodStore(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating FoodStore with moduleName: " + moduleName);
            FoodStoreImpl myFoodStoreImpl = new FoodStoreImpl(myID, moduleName);
            BioInitializer.setupBioModule(myFoodStoreImpl, node);
            float waterContent = Float.parseFloat(node.getAttributes().getNamedItem(
            "waterContent").getNodeValue());
            PlantType theCropType = getCropType(node);
            FoodMatter newFoodMatter = new FoodMatter(getStoreLevel(node), waterContent, theCropType);
            myFoodStoreImpl.setInitialCapacity(getStoreCapacity(node));
            myFoodStoreImpl.setInitialFoodMatterLevel(newFoodMatter);
            myFoodStoreImpl.setResupply(getStoreResupplyFrequency(node),
                    getStoreResupplyAmount(node));
            BiosimServer.registerServer(new FoodStorePOATie(myFoodStoreImpl),
                    myFoodStoreImpl.getModuleName(), myFoodStoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void crawlFoodModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("BiomassRS")) {
                if (firstPass)
                    createBiomassRS(child);
                else
                    configureBiomassRS(child);
            } else if (childName.equals("FoodProcessor")) {
                if (firstPass)
                    createFoodProcessor(child);
                else
                    configureFoodProcessor(child);
            } else if (childName.equals("BiomassStore")) {
                if (firstPass)
                    createBiomassStore(child);
                else
                    myPassiveSimModules.add(BiomassStoreHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));
            } else if (childName.equals("FoodStore")) {
                if (firstPass)
                    createFoodStore(child);
                else
                    myPassiveSimModules.add(FoodStoreHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));
            }
            child = child.getNextSibling();
        }
    }

    private void createPowerPS(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PowerPS with moduleName: " + moduleName);
            PowerPSImpl myPowerPSImpl = null;
            if (node.getAttributes().getNamedItem("generationType").getNodeValue()
                    .equals("SOLAR"))
                myPowerPSImpl = new SolarPowerPS(myID, moduleName);
            else
                myPowerPSImpl = new NuclearPowerPS(myID, moduleName);
            float upperPowerGeneration = Float.parseFloat(node.getAttributes().getNamedItem(
            "upperPowerGeneration").getNodeValue());
            myPowerPSImpl.setInitialUpperPowerGeneration(upperPowerGeneration);
            BioInitializer.setupBioModule(myPowerPSImpl, node);
            BiosimServer.registerServer(new PowerPSPOATie(myPowerPSImpl),
                    myPowerPSImpl.getModuleName(), myPowerPSImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configurePowerPS(Node node) {
        PowerPS myPowerPS = PowerPSHelper.narrow(BioInitializer.grabModule(
                myID, BioInitializer.getModuleName(node)));
        configureSimBioModule(myPowerPS, node);
        myActiveSimModules.add(myPowerPS);
    }

    private void createPowerStore(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating PowerStore with moduleName: " + moduleName);
            PowerStoreImpl myPowerStoreImpl = new PowerStoreImpl(myID,
                    moduleName);
            setupStore(myPowerStoreImpl, node);
            BiosimServer.registerServer(new PowerStorePOATie(myPowerStoreImpl),
                    myPowerStoreImpl.getModuleName(), myPowerStoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void crawlPowerModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PowerPS")) {
                if (firstPass)
                    createPowerPS(child);
                else
                    configurePowerPS(child);
            } else if (childName.equals("PowerStore")) {
                if (firstPass)
                    createPowerStore(child);
                else
                    myPassiveSimModules.add(PowerStoreHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));
            }
            child = child.getNextSibling();
        }
    }

    private void createWaterRS(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating WaterRS with moduleName: " + moduleName);
            String implementationString = node.getAttributes().getNamedItem(
                    "implementation").getNodeValue();
            if (implementationString.equals("MATLAB")) {
                myLogger.debug("created Matlab WaterRS...");
                WaterRSMatlabImpl myWaterRSImpl = new WaterRSMatlabImpl(myID,
                        moduleName);
                BioInitializer.setupBioModule(myWaterRSImpl, node);
                BiosimServer.registerServer(new WaterRSPOATie(myWaterRSImpl),
                        myWaterRSImpl.getModuleName(), myWaterRSImpl.getID());
            } else if (implementationString.equals("LINEAR")) {
                myLogger.debug("created linear WaterRS...");
                WaterRSLinearImpl myWaterRSImpl = new WaterRSLinearImpl(myID,
                        moduleName);
                BioInitializer.setupBioModule(myWaterRSImpl, node);
                BiosimServer.registerServer(new WaterRSPOATie(myWaterRSImpl),
                        myWaterRSImpl.getModuleName(), myWaterRSImpl.getID());
            } else {
                WaterRSImpl myWaterRSImpl = new WaterRSImpl(myID, moduleName);
                BioInitializer.setupBioModule(myWaterRSImpl, node);
                BiosimServer.registerServer(new WaterRSPOATie(myWaterRSImpl),
                        myWaterRSImpl.getModuleName(), myWaterRSImpl.getID());
            }
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureWaterRS(Node node) {
        WaterRS myWaterRS = WaterRSHelper.narrow(BioInitializer.grabModule(
                myID, BioInitializer.getModuleName(node)));
        configureSimBioModule(myWaterRS, node);
        /*
         * String operationModeString = node.getAttributes().getNamedItem(
         * "operationMode").getNodeValue(); if
         * (operationModeString.equals("FULL"))
         * myWaterRS.setOperationMode(WaterRSOperationMode.FULL); else if
         * (operationModeString.equals("PARTIAL"))
         * myWaterRS.setOperationMode(WaterRSOperationMode.PARTIAL); else if
         * (operationModeString.equals("GREY_WATER_ONLY"))
         * myWaterRS.setOperationMode(WaterRSOperationMode.GREY_WATER_ONLY);
         * else if (operationModeString.equals("OFF"))
         * myWaterRS.setOperationMode(WaterRSOperationMode.OFF); else
         * myLogger.error("WaterRSOperationMode not found!");
         */
        myActiveSimModules.add(myWaterRS);
    }

    private void createPotableWaterStore(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating PotableWaterStore with moduleName: "
                    + moduleName);
            PotableWaterStoreImpl myPotableWaterStoreImpl = new PotableWaterStoreImpl(
                    myID, moduleName);
            setupStore(myPotableWaterStoreImpl, node);
            BiosimServer.registerServer(new PotableWaterStorePOATie(
                    myPotableWaterStoreImpl), myPotableWaterStoreImpl
                    .getModuleName(), myPotableWaterStoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void createDirtyWaterStore(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating DirtyWaterStore with moduleName: "
                    + moduleName);
            DirtyWaterStoreImpl myDirtyWaterStoreImpl = new DirtyWaterStoreImpl(
                    myID, moduleName);
            setupStore(myDirtyWaterStoreImpl, node);
            BiosimServer.registerServer(new DirtyWaterStorePOATie(
                    myDirtyWaterStoreImpl), myDirtyWaterStoreImpl
                    .getModuleName(), myDirtyWaterStoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void createGreyWaterStore(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating GreyWaterStore with moduleName: "
                    + moduleName);
            GreyWaterStoreImpl myGreyWaterStoreImpl = new GreyWaterStoreImpl(
                    myID, moduleName);
            setupStore(myGreyWaterStoreImpl, node);
            BiosimServer.registerServer(new GreyWaterStorePOATie(
                    myGreyWaterStoreImpl),
                    myGreyWaterStoreImpl.getModuleName(), myGreyWaterStoreImpl
                            .getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void crawlWaterModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("WaterRS")) {
                if (firstPass)
                    createWaterRS(child);
                else
                    configureWaterRS(child);
            } else if (childName.equals("PotableWaterStore")) {
                if (firstPass)
                    createPotableWaterStore(child);
                else
                    myPassiveSimModules.add(PotableWaterStoreHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));
            } else if (childName.equals("GreyWaterStore")) {
                if (firstPass)
                    createGreyWaterStore(child);
                else
                    myPassiveSimModules.add(GreyWaterStoreHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));
            } else if (childName.equals("DirtyWaterStore")) {
                if (firstPass)
                    createDirtyWaterStore(child);
                else
                    myPassiveSimModules.add(DirtyWaterStoreHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));
            }
            child = child.getNextSibling();
        }
    }

    private void createIncinerator(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating Incinerator with moduleName: "
                    + moduleName);
            IncineratorImpl myIncineratorImpl = new IncineratorImpl(myID,
                    moduleName);
            BioInitializer.setupBioModule(myIncineratorImpl, node);
            BiosimServer.registerServer(
                    new IncineratorPOATie(myIncineratorImpl), myIncineratorImpl
                            .getModuleName(), myIncineratorImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureIncinerator(Node node) {
        Incinerator myIncinerator = IncineratorHelper.narrow(BioInitializer
                .grabModule(myID, BioInitializer.getModuleName(node)));
        configureSimBioModule(myIncinerator, node);
        myActiveSimModules.add(myIncinerator);
    }

    private void createDryWasteStore(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger.debug("Creating DryWasteStore with moduleName: "
                    + moduleName);
            DryWasteStoreImpl myDryWasteStoreImpl = new DryWasteStoreImpl(myID,
                    moduleName);
            setupStore(myDryWasteStoreImpl, node);
            BiosimServer.registerServer(new DryWasteStorePOATie(
                    myDryWasteStoreImpl), myDryWasteStoreImpl.getModuleName(),
                    myDryWasteStoreImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void crawlWasteModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("Incinerator")) {
                if (firstPass)
                    createIncinerator(child);
                else
                    configureIncinerator(child);
            } else if (childName.equals("DryWasteStore")) {
                if (firstPass)
                    createDryWasteStore(child);
                else
                    myPassiveSimModules.add(DryWasteStoreHelper
                            .narrow(BioInitializer.grabModule(myID,
                                    BioInitializer.getModuleName(child))));
            }
            child = child.getNextSibling();
        }
    }

    private void createEVAMission(Node node) {
        String moduleName = BioInitializer.getModuleName(node);
        if (BioInitializer.isCreatedLocally(node)) {
            myLogger
                    .debug("Creating EVAMission with moduleName: " + moduleName);
            EVAMissionImpl myEVAMissionImpl = new EVAMissionImpl(myID,
                    moduleName);
            BioInitializer.setupBioModule(myEVAMissionImpl, node);
            BiosimServer.registerServer(new EVAMissionPOATie(myEVAMissionImpl),
                    myEVAMissionImpl.getModuleName(), myEVAMissionImpl.getID());
        } else
            BioInitializer.printRemoteWarningMessage(moduleName);
    }

    private void configureEVAMission(Node node) {
        EVAMission myEVAMission = EVAMissionHelper.narrow(BioInitializer
                .grabModule(myID, BioInitializer.getModuleName(node)));
        configureSimBioModule(myEVAMission, node);
        myActiveSimModules.add(myEVAMission);
    }

    private void crawlMissionModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("EVAMission")) {
                if (firstPass)
                    createEVAMission(child);
                else
                    configureEVAMission(child);
            }
            child = child.getNextSibling();
        }
    }

    /**
     * @return Returns the myActiveSimModules.
     */
    public List getActiveSimModules() {
        return myActiveSimModules;
    }

    /**
     * @return Returns the myPassiveSimModules.
     */
    public List getPassiveSimModules() {
        return myPassiveSimModules;
    }

    /**
     * @return Returns the myPrioritySimModules.
     */
    public List getPrioritySimModules() {
        return myPrioritySimModules;
    }
}