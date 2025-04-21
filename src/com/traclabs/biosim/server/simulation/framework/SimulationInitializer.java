package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.air.*;
import com.traclabs.biosim.server.simulation.air.cdrs.CDRSModule;
import com.traclabs.biosim.server.simulation.crew.*;
import com.traclabs.biosim.server.simulation.environment.*;
import com.traclabs.biosim.server.simulation.food.*;
import com.traclabs.biosim.server.simulation.power.*;
import com.traclabs.biosim.server.simulation.thermal.IATCS;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumer;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducer;
import com.traclabs.biosim.server.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.simulation.waste.Incinerator;
import com.traclabs.biosim.server.simulation.water.*;
import com.traclabs.biosim.util.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * Reads BioSim configuration from XML file.
 *
 * @author Scott Bell
 */
public class SimulationInitializer {
    private final Set<SimBioModule> myActiveSimModules;
    private final Set<PassiveModule> myPassiveSimModules;
    private final Set<SimBioModule> myPrioritySimModules;
    private final Logger myLogger;
    private int myID = 0;

    /**
     * Default constructor.
     */
    public SimulationInitializer(int pID) {
        myID = pID;
        myLogger = LoggerFactory.getLogger(this.getClass());
        myPassiveSimModules = new HashSet<PassiveModule>();
        myActiveSimModules = new HashSet<SimBioModule>();
        myPrioritySimModules = new HashSet<SimBioModule>();
    }

    private static float getAttributeFloat(Node node, String attributeName) {
        float number = 0f;
        try {
            number = Float.parseFloat(node.getAttributes().getNamedItem(
                    attributeName).getNodeValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return number;
    }

    private static boolean[] getSwitchValues(Node node) {
        if (node == null)
            return new boolean[0];
        Node switchValueNode = node.getAttributes()
                .getNamedItem("switchValues");
        if (switchValueNode == null)
            return new boolean[0];
        String arrayString = switchValueNode.getNodeValue();
        String[] tokens = arrayString.split("\\s");
        boolean[] switchValues = new boolean[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            try {
                switchValues[i] = tokens[i].equals("1");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return switchValues;
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
        String[] tokens = arrayString.split("\\s");
        float[] maxFlowRates = new float[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            try {
                maxFlowRates[i] = Float.parseFloat(tokens[i]);
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
        String[] tokens = arrayString.split("\\s");
        float[] desiredFlowRates = new float[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            try {
                desiredFlowRates[i] = Float.parseFloat(tokens[i]);
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        }
        return desiredFlowRates;
    }

    private static Node getEnvironmentNode(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.startsWith("environment"))
                    return child;
            }
            child = child.getNextSibling();
        }
        return null;
    }

    private static Node getStoreNode(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.startsWith("store"))
                    return child;
            }
            child = child.getNextSibling();
        }
        return null;
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

    public void crawlSimModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
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
                } else if (childName.equals("thermal")) {
                    crawlThermalModules(child, firstPass);
                }
            }
            child = child.getNextSibling();
        }
    }

    private void configureSimBioModule(SimBioModule pModule, Node node) {
        if (pModule == null) {
            throw new IllegalArgumentException("Module passed for configuration is null!");
        }
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                switch (childName) {
                    case "powerConsumer" -> {
                        PowerConsumer myPowerConsumer = (PowerConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        PowerStore[] inputs = new PowerStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = (PowerStore) modules[i];
                        myPowerConsumer.getPowerConsumerDefinition()
                                .setPowerInputs(inputs, getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "potableWaterConsumer" -> {
                        PotableWaterConsumer myPotableWaterConsumer = (PotableWaterConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        PotableWaterStore[] inputs = new PotableWaterStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = (PotableWaterStore) modules[i];
                        myPotableWaterConsumer.getPotableWaterConsumerDefinition()
                                .setPotableWaterInputs(inputs,
                                        getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "greyWaterConsumer" -> {
                        GreyWaterConsumer myGreyWaterConsumer = (GreyWaterConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        GreyWaterStore[] inputs = new GreyWaterStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = (GreyWaterStore) (modules[i]);
                        myGreyWaterConsumer.getGreyWaterConsumerDefinition()
                                .setGreyWaterInputs(inputs, getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "dirtyWaterConsumer" -> {
                        DirtyWaterConsumer myDirtyWaterConsumer = (DirtyWaterConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        DirtyWaterStore[] inputs = new DirtyWaterStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = ((DirtyWaterStore) modules[i]);
                        myDirtyWaterConsumer.getDirtyWaterConsumerDefinition()
                                .setDirtyWaterInputs(inputs,
                                        getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "airConsumer" -> {
                        AirConsumer myAirConsumer = (AirConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        SimEnvironment[] inputs = new SimEnvironment[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = ((SimEnvironment) modules[i]);
                        myAirConsumer.getAirConsumerDefinition().setAirInputs(
                                inputs, getMaxFlowRates(child),
                                getDesiredFlowRates(child));
                    }
                    case "H2Consumer" -> {
                        H2Consumer myH2Consumer = (H2Consumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        H2Store[] inputs = new H2Store[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = ((H2Store) modules[i]);
                        myH2Consumer.getH2ConsumerDefinition().setH2Inputs(inputs,
                                getMaxFlowRates(child), getDesiredFlowRates(child));
                    }
                    case "nitrogenConsumer" -> {
                        NitrogenConsumer myNitrogenConsumer = (NitrogenConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        INitrogenStore[] inputs = new INitrogenStore[modules.length];
                        for (int i = 0; i < modules.length; i++) {
                            if (modules[i] instanceof SimEnvironment) {
                                SimEnvironment currentEnvironment = (SimEnvironment) (modules[i]);
                                inputs[i] = currentEnvironment.getNitrogenStore();
                            } else
                                inputs[i] = ((INitrogenStore) modules[i]);
                        }
                        myNitrogenConsumer.getNitrogenConsumerDefinition()
                                .setNitrogenInputs(inputs, getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "methaneConsumer" -> {
                        MethaneConsumer myMethaneConsumer = (MethaneConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        MethaneStore[] inputs = new MethaneStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = ((MethaneStore) modules[i]);
                        myMethaneConsumer.getMethaneConsumerDefinition()
                                .setMethaneInputs(inputs, getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "O2Consumer" -> {
                        O2Consumer myO2Consumer = (O2Consumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        IO2Store[] inputs = new IO2Store[modules.length];
                        for (int i = 0; i < modules.length; i++) {
                            if (modules[i] instanceof SimEnvironment) {
                                SimEnvironment currentEnvironment = (SimEnvironment) (modules[i]);
                                inputs[i] = currentEnvironment.getO2Store();
                            } else
                                inputs[i] = ((O2Store) modules[i]);
                        }
                        myO2Consumer.getO2ConsumerDefinition().setO2Inputs(inputs,
                                getMaxFlowRates(child), getDesiredFlowRates(child));
                    }
                    case "CO2Consumer" -> {
                        CO2Consumer myCO2Consumer = (CO2Consumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        ICO2Store[] inputs = new ICO2Store[modules.length];
                        for (int i = 0; i < modules.length; i++) {
                            if (modules[i] instanceof SimEnvironment) {
                                SimEnvironment currentEnvironment = (SimEnvironment) (modules[i]);
                                inputs[i] = currentEnvironment.getCO2Store();
                            } else
                                inputs[i] = ((CO2Store) modules[i]);
                        }
                        myCO2Consumer.getCO2ConsumerDefinition().setCO2Inputs(
                                inputs, getMaxFlowRates(child),
                                getDesiredFlowRates(child));
                    }
                    case "lightConsumer" -> {
                        LightConsumer myLightConsumer = (LightConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        SimEnvironment[] inputs = new SimEnvironment[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = ((SimEnvironment) modules[i]);
                        myLightConsumer.getLightConsumerDefinition().setLightInput(
                                inputs[0]);
                    }
                    case "biomassConsumer" -> {
                        BiomassConsumer myBiomassConsumer = (BiomassConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        BiomassStore[] inputs = new BiomassStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = ((BiomassStore) modules[i]);
                        myBiomassConsumer.getBiomassConsumerDefinition()
                                .setBiomassInputs(inputs, getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "dryWasteConsumer" -> {
                        DryWasteConsumer myDryWasteConsumer = (DryWasteConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        DryWasteStore[] inputs = new DryWasteStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = ((DryWasteStore) modules[i]);
                        myDryWasteConsumer.getDryWasteConsumerDefinition()
                                .setDryWasteInputs(inputs, getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "foodConsumer" -> {
                        FoodConsumer myFoodConsumer = (FoodConsumer) (pModule);
                        IBioModule[] modules = getInputs(child);
                        FoodStore[] inputs = new FoodStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            inputs[i] = ((FoodStore) modules[i]);
                        myFoodConsumer.getFoodConsumerDefinition().setFoodInputs(
                                inputs, getMaxFlowRates(child),
                                getDesiredFlowRates(child));
                    }
                    case "powerProducer" -> {
                        PowerProducer myPowerProducer = (PowerProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        PowerStore[] outputs = new PowerStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((PowerStore) modules[i]);
                        myPowerProducer.getPowerProducerDefinition()
                                .setPowerOutputs(outputs, getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "potableWaterProducer" -> {
                        PotableWaterProducer myPotableWaterProducer = (PotableWaterProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        PotableWaterStore[] outputs = new PotableWaterStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((PotableWaterStore) modules[i]);
                        myPotableWaterProducer.getPotableWaterProducerDefinition()
                                .setPotableWaterOutputs(outputs,
                                        getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "greyWaterProducer" -> {
                        GreyWaterProducer myGreyWaterProducer = (GreyWaterProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        GreyWaterStore[] outputs = new GreyWaterStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((GreyWaterStore) modules[i]);
                        myGreyWaterProducer.getGreyWaterProducerDefinition()
                                .setGreyWaterOutputs(outputs,
                                        getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "dirtyWaterProducer" -> {
                        DirtyWaterProducer myDirtyWaterProducer = (DirtyWaterProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        DirtyWaterStore[] outputs = new DirtyWaterStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((DirtyWaterStore) modules[i]);
                        myDirtyWaterProducer.getDirtyWaterProducerDefinition()
                                .setDirtyWaterOutputs(outputs,
                                        getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "airProducer" -> {
                        AirProducer myAirProducer = (AirProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        SimEnvironment[] outputs = new SimEnvironment[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((SimEnvironment) modules[i]);
                        myAirProducer.getAirProducerDefinition().setAirOutputs(
                                outputs, getMaxFlowRates(child),
                                getDesiredFlowRates(child));
                    }
                    case "H2Producer" -> {
                        H2Producer myH2Producer = (H2Producer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        H2Store[] outputs = new H2Store[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((H2Store) modules[i]);
                        myH2Producer.getH2ProducerDefinition().setH2Outputs(
                                outputs, getMaxFlowRates(child),
                                getDesiredFlowRates(child));
                    }
                    case "nitrogenProducer" -> {
                        NitrogenProducer myNitrogenProducer = (NitrogenProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        INitrogenStore[] outputs = new INitrogenStore[modules.length];
                        for (int i = 0; i < modules.length; i++) {
                            if (modules[i] instanceof SimEnvironment) {
                                SimEnvironment currentEnvironment = (SimEnvironment) (modules[i]);
                                outputs[i] = currentEnvironment.getNitrogenStore();
                            } else
                                outputs[i] = ((INitrogenStore) modules[i]);
                        }
                        myNitrogenProducer.getNitrogenProducerDefinition()
                                .setNitrogenOutputs(outputs,
                                        getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "methaneProducer" -> {
                        MethaneProducer myMethaneProducer = (MethaneProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        MethaneStore[] outputs = new MethaneStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((MethaneStore) modules[i]);
                        myMethaneProducer.getMethaneProducerDefinition()
                                .setMethaneOutputs(outputs, getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "O2Producer" -> {
                        O2Producer myO2Producer = (O2Producer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        IO2Store[] outputs = new IO2Store[modules.length];
                        for (int i = 0; i < modules.length; i++) {
                            if (modules[i] instanceof SimEnvironment) {
                                SimEnvironment currentEnvironment = (SimEnvironment) (modules[i]);
                                outputs[i] = currentEnvironment.getO2Store();
                            } else
                                outputs[i] = ((IO2Store) modules[i]);
                        }
                        myO2Producer.getO2ProducerDefinition().setO2Outputs(
                                outputs, getMaxFlowRates(child),
                                getDesiredFlowRates(child));
                    }
                    case "CO2Producer" -> {
                        CO2Producer myCO2Producer = (CO2Producer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        ICO2Store[] outputs = new ICO2Store[modules.length];
                        for (int i = 0; i < modules.length; i++) {
                            if (modules[i] instanceof SimEnvironment) {
                                SimEnvironment currentEnvironment = (SimEnvironment) (modules[i]);
                                outputs[i] = currentEnvironment.getCO2Store();
                            } else
                                outputs[i] = ((ICO2Store) modules[i]);
                        }
                        myCO2Producer.getCO2ProducerDefinition().setCO2Outputs(
                                outputs, getMaxFlowRates(child),
                                getDesiredFlowRates(child));
                    }
                    case "biomassProducer" -> {
                        BiomassProducer myBiomassProducer = (BiomassProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        BiomassStore[] outputs = new BiomassStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((BiomassStore) modules[i]);
                        myBiomassProducer.getBiomassProducerDefinition()
                                .setBiomassOutputs(outputs, getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "dryWasteProducer" -> {
                        DryWasteProducer myDryWasteProducer = (DryWasteProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        DryWasteStore[] outputs = new DryWasteStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((DryWasteStore) modules[i]);
                        myDryWasteProducer.getDryWasteProducerDefinition()
                                .setDryWasteOutputs(outputs,
                                        getMaxFlowRates(child),
                                        getDesiredFlowRates(child));
                    }
                    case "foodProducer" -> {
                        FoodProducer myFoodProducer = (FoodProducer) (pModule);
                        IBioModule[] modules = getOutputs(child);
                        FoodStore[] outputs = new FoodStore[modules.length];
                        for (int i = 0; i < modules.length; i++)
                            outputs[i] = ((FoodStore) modules[i]);
                        myFoodProducer.getFoodProducerDefinition().setFoodOutputs(
                                outputs, getMaxFlowRates(child),
                                getDesiredFlowRates(child));
                    }
                }
            }
            child = child.getNextSibling();
        }
    }

    private IBioModule[] getInputs(Node node) {
        if (node == null)
            return new BioModule[0];
        String arrayString = node.getAttributes().getNamedItem("inputs")
                .getNodeValue();
        String[] inputNames = arrayString.split("\\s");
        IBioModule[] inputs = new IBioModule[inputNames.length];
        for (int i = 0; i < inputs.length; i++) {
            String inputName = inputNames[i];
            inputs[i] = BiosimInitializer.getModule(myID, inputName);
            if (inputs[i] == null) {
                throw new IllegalArgumentException("🛑 Module " + inputName + " not found in the simulation.");
            }
        }
        return inputs;
    }

    private IBioModule[] getOutputs(Node node) {
        if (node == null)
            return new BioModule[0];
        String arrayString = node.getAttributes().getNamedItem("outputs")
                .getNodeValue();
        String[] outputNames = arrayString.split("\\s");
        IBioModule[] outputs = new IBioModule[outputNames.length];
        for (int i = 0; i < outputs.length; i++) {
            String outputName = outputNames[i];
            outputs[i] = BiosimInitializer.getModule(myID, outputName);
        }
        return outputs;
    }

    private void setupStore(Store pStore, Node pNode) {
        pStore.setInitialCapacity(getStoreCapacity(pNode));
        pStore.setInitialLevel(getStoreLevel(pNode));
        pStore.setResupply(getStoreResupplyFrequency(pNode),
                getStoreResupplyAmount(pNode));
        BiosimInitializer.setupBioModule(myID, pStore, pNode);
        myPassiveSimModules.add(pStore);
    }

    /**
     * @param child
     */
    private void createVCCR(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        myLogger.debug("Creating VCCR with moduleName: " + moduleName);
        String implementationString = node.getAttributes().getNamedItem(
                "implementation").getNodeValue();
        if (implementationString.equals("LINEAR")) {
            myLogger.debug("created linear VCCR...");
            VCCRLinear myVCCR = new VCCRLinear(myID, moduleName);
            BiosimInitializer.setupBioModule(myID, myVCCR, node);
        } else {
            VCCR myVCCR = new VCCR(myID, moduleName);
            BiosimInitializer.setupBioModule(myID, myVCCR, node);
        }
    }

    /**
     * @param child
     */
    private void configureVCCR(Node node) {
        AbstractVCCR myVCCR = (AbstractVCCR) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(myVCCR, node);
        myActiveSimModules.add(myVCCR);

    }

    /**
     * @param child
     */
    private void createCRS(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CRS myCRS = new CRS(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, myCRS, node);
    }

    /**
     * @param child
     */
    private void configureCRS(Node node) {
        CRS myCRS = (CRS) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(myCRS, node);
        myActiveSimModules.add(myCRS);

    }

    /**
     * @param child
     */
    private void createCDRS(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CDRSModule cdrs = new CDRSModule(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, cdrs, node);
    }

    /**
     * @param child
     */
    private void configureCDRS(Node node) {
        CDRSModule cdrs = (CDRSModule) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(cdrs, node);
        myActiveSimModules.add(cdrs);

    }

    private void createOGS(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        OGS ogs = new OGS(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, ogs, node);
    }

    private void configureOGS(Node node) {
        OGS ogs = (OGS) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(ogs, node);
        myLogger.debug("Configuring OGS");
        myActiveSimModules.add(ogs);
    }

    private void createPyrolizer(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        Pyrolizer pyrolizer = new Pyrolizer(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, pyrolizer, node);
    }

    private void configurePyrolizer(Node node) {
        Pyrolizer pyrolizer = (Pyrolizer) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(pyrolizer, node);
        myLogger.debug("Configuring Pyrolizer");
        myActiveSimModules.add(pyrolizer);
    }

    private void createO2Store(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        O2Store o2Store = new O2Store(myID, moduleName);
        setupStore(o2Store, node);
    }

    private void createCO2Store(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CO2Store co2Store = new CO2Store(myID, moduleName);
        setupStore(co2Store, node);
    }

    private void createH2Store(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        H2Store h2Store = new H2Store(myID, moduleName);
        setupStore(h2Store, node);
    }

    private void createNitrogenStore(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        NitrogenStore nitrogenStore = new NitrogenStore(myID, moduleName);
        setupStore(nitrogenStore, node);
    }

    private void createMethaneStore(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        MethaneStore methaneStore = new MethaneStore(myID, moduleName);
        setupStore(methaneStore, node);
    }

    private void crawlAirModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("OGS")) {
                    if (firstPass)
                        createOGS(child);
                    else
                        configureOGS(child);
                } else if (childName.equals("CRS")) {
                    if (firstPass)
                        createCRS(child);
                    else
                        configureCRS(child);
                } else if (childName.equals("Pyrolizer")) {
                    if (firstPass)
                        createPyrolizer(child);
                    else
                        configurePyrolizer(child);
                } else if (childName.equals("VCCR")) {
                    if (firstPass)
                        createVCCR(child);
                    else
                        configureVCCR(child);
                } else if (childName.equals("CDRS")) {
                    if (firstPass)
                        createCDRS(child);
                    else
                        configureCDRS(child);
                } else if (childName.equals("O2Store")) {
                    if (firstPass)
                        createO2Store(child);
                } else if (childName.equals("CO2Store")) {
                    if (firstPass)
                        createCO2Store(child);
                } else if (childName.equals("H2Store")) {
                    if (firstPass)
                        createH2Store(child);
                } else if (childName.equals("NitrogenStore")) {
                    if (firstPass)
                        createNitrogenStore(child);
                } else if (childName.equals("MethaneStore")) {
                    if (firstPass)
                        createMethaneStore(child);
                }
            }
            child = child.getNextSibling();
        }
    }

    private Activity createActivity(Node node, CrewGroup crew) {
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
                EVAActivity newEVAActivity = new EVAActivity(name,
                        length, intensity, crew.getModuleName(),
                        evaCrewGroupName);
                return newEVAActivity;
            }
            myLogger
                    .error("Activity not of expected type even though it was explicitly declared! (can only be EVA type right now)");
            myLogger.error("type was: " + activityTypeNode.getNodeValue()
                    + ", should be: EVAActivityType");
            return null;
        }
        Activity newActivity = new Activity(name, length, intensity);
        return newActivity;
    }

    private Schedule createSchedule(Node node, CrewGroup crew) {
        Schedule newSchedule = new Schedule(crew);
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (child.getLocalName().equals("activity")) {
                    Activity newActivity = createActivity(child, crew);
                    newSchedule.insertActivityInSchedule(newActivity);
                }
            }
            child = child.getNextSibling();
        }
        return newSchedule;
    }

    private void createCrewPerson(Node node, CrewGroup crewGroup) {
        Node child = node.getFirstChild();
        Schedule schedule = null;
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("schedule"))
                    schedule = createSchedule(node.getFirstChild()
                            .getNextSibling(), crewGroup);
            }
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
        crewGroup.createCrewPerson(name, age, weight, sex, arrivalDate,
                departureDate, schedule);
        crewGroup.getCrewPerson(name).setLogLevel(
                BiosimInitializer.getLogLevel(node));
    }

    private void createCrewGroup(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        myLogger.debug("Creating CrewGroup with moduleName: " + moduleName);
        CrewGroup myCrewGroup = new CrewGroup(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, myCrewGroup, node);
        boolean deathEnabled = XMLUtils.getBooelanAttribute(node,
                "isDeathEnabled");
        myCrewGroup.setDeathEnabled(deathEnabled);
        // Create crew members
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("crewPerson"))
                    createCrewPerson(child, myCrewGroup);
            }
            child = child.getNextSibling();
        }
    }

    private void configureCrewGroup(Node node) {
        CrewGroup crewGroup = (CrewGroup) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(crewGroup, node);
        myActiveSimModules.add(crewGroup);
    }

    private void crawlCrewModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("CrewGroup")) {
                    if (firstPass)
                        createCrewGroup(child);
                    else
                        configureCrewGroup(child);
                }
            }
            child = child.getNextSibling();
        }
    }

    private void createSimEnvironment(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        myLogger.debug("Creating SimEnvironment with moduleName: " + moduleName);
        float volume = Float.parseFloat(node.getAttributes().getNamedItem("initialVolume").getNodeValue());
        float leakRate = Float.parseFloat(node.getAttributes().getNamedItem("leakRate").getNodeValue());
        float dayLength = Float.parseFloat(node.getAttributes().getNamedItem("dayLength").getNodeValue());
        float hourOfDayStart = Float.parseFloat(node.getAttributes().getNamedItem("hourOfDayStart").getNodeValue());
        float maxLumens = Float.parseFloat(node.getAttributes().getNamedItem("maxLumens").getNodeValue());
        float airlockVolume = Float.parseFloat(node.getAttributes().getNamedItem("airlockVolume").getNodeValue());
        float dangerousOxygenThreshold = Float.parseFloat(node.getAttributes().getNamedItem("dangerousOxygenThreshold").getNodeValue());

        SimEnvironment env;
        if (creatingEnvironmentWithMoles(node)) {
            myLogger.debug("Creating environment with moles");
            env = createEnvironmentWithMoles(node, myID, volume, moduleName);
        } else if (creatingEnvironmentWithPercentages(node)) {
            myLogger.debug("Creating environment with percentages");
            env = createEnvironmentWithPercentages(node, myID, volume, moduleName);
        } else {
            myLogger.debug("Creating environment with defaults");
            env = new SimEnvironment(myID, volume, moduleName);
        }
        env.setLeakRate(leakRate);
        env.setDayLength(dayLength);
        env.setHourOfDayStart(hourOfDayStart);
        env.setMaxLumens(maxLumens);
        env.setAirlockVolume(airlockVolume);
        env.setDangerousOxygenThreshold(dangerousOxygenThreshold);
        BiosimInitializer.setupBioModule(myID, env, node);
    }

    private boolean creatingEnvironmentWithPercentages(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("percentageInitialization")) {
                    return true;
                }
            }
            child = child.getNextSibling();
        }
        return false;
    }

    private boolean creatingEnvironmentWithMoles(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("moleInitialization")) {
                    return true;
                }
            }
            child = child.getNextSibling();
        }
        return false;
    }

    private SimEnvironment createEnvironmentWithPercentages(Node node, int pID, float pVolume, String pName) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("percentageInitialization")) {
                    float co2Percentage = Float.parseFloat(child.getAttributes().getNamedItem("co2Percentage").getNodeValue());
                    float o2Percentage = Float.parseFloat(child.getAttributes().getNamedItem("o2Percentage").getNodeValue());
                    float waterPercentage = Float.parseFloat(child.getAttributes().getNamedItem("waterPercentage").getNodeValue());
                    float otherPercentage = Float.parseFloat(child.getAttributes().getNamedItem("otherPercentage").getNodeValue());
                    float nitrogenPercentage = Float.parseFloat(child.getAttributes().getNamedItem("nitrogenPercentage").getNodeValue());
                    float totalPressure = Float.parseFloat(child.getAttributes().getNamedItem("totalPressure").getNodeValue());
                    SimEnvironment newSimEnvironment = new SimEnvironment(pID, pName, pVolume, totalPressure, o2Percentage, co2Percentage, otherPercentage, waterPercentage, nitrogenPercentage);
                    return newSimEnvironment;
                }
            }
            child = child.getNextSibling();
        }
        return null;
    }

    private SimEnvironment createEnvironmentWithMoles(Node node, int pID, float pVolume, String pName) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("moleInitialization")) {
                    float CO2Moles = Float.parseFloat(child.getAttributes().getNamedItem("initialCO2Moles").getNodeValue());
                    float O2Moles = Float.parseFloat(child.getAttributes().getNamedItem("initialO2Moles").getNodeValue());
                    float waterMoles = Float.parseFloat(child.getAttributes().getNamedItem("initialWaterMoles").getNodeValue());
                    float otherMoles = Float.parseFloat(child.getAttributes().getNamedItem("initialOtherMoles").getNodeValue());
                    float nitrogenMoles = Float.parseFloat(child.getAttributes().getNamedItem("initialNitrogenMoles").getNodeValue());
                    SimEnvironment newSimEnvironment = new SimEnvironment(O2Moles, CO2Moles, otherMoles, waterMoles, nitrogenMoles, pVolume, pName, pID);
                    return newSimEnvironment;
                }
            }
            child = child.getNextSibling();
        }
        return null;
    }

    private void createDehumidifier(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        myLogger.debug("Creating Dehumidifier with moduleName: " + moduleName);
        Dehumidifier dehumidifier = new Dehumidifier(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, dehumidifier, node);
    }

    private void configureDehumidifier(Node node) {
        Dehumidifier dehumidifier = (Dehumidifier) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(dehumidifier, node);
        myPrioritySimModules.add(dehumidifier);
    }

    private void createFan(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        myLogger.debug("Creating Fan with moduleName: " + moduleName);
        Fan fan = new Fan(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, fan, node);
    }

    private void configureFan(Node node) {
        Fan fan = (Fan) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(fan, node);
        myPrioritySimModules.add(fan);
    }

    private void crawlEnvironmentModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("SimEnvironment")) {
                    if (firstPass)
                        createSimEnvironment(child);
                    else {
                        SimEnvironment environment = (SimEnvironment) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(child));
                        myPassiveSimModules.add(environment);
                    }
                }
                if (childName.equals("Dehumidifier")) {
                    if (firstPass)
                        createDehumidifier(child);
                    else
                        configureDehumidifier(child);
                }
                if (childName.equals("Fan")) {
                    if (firstPass)
                        createFan(child);
                    else
                        configureFan(child);
                }
            }
            child = child.getNextSibling();
        }
    }

    private void createAccumulator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        Accumulator accumulator = new Accumulator(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, accumulator, node);
    }

    private void configureAccumulator(Node node) {
        Accumulator accumulator = (Accumulator) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(accumulator, node);
        myActiveSimModules.add(accumulator);
    }

    private void createInjector(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        Injector injector = new Injector(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, injector, node);
    }

    private void configureInjector(Node node) {
        Injector injector = (Injector) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(injector, node);
        myActiveSimModules.add(injector);
    }

    private void createInfluentValve(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        InfluentValve influentValve = new InfluentValve(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, influentValve, node);
    }

    private void configureInfluentValve(Node node) {
        InfluentValve influentValve = (InfluentValve) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(influentValve, node);
        myActiveSimModules.add(influentValve);
    }

    private void createEffluentValve(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        EffluentValve effluentValve = new EffluentValve(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, effluentValve, node);
    }

    private void configureEffluentValve(Node node) {
        EffluentValve effluentValve = (EffluentValve) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(effluentValve, node);
        myActiveSimModules.add(effluentValve);
    }

    private void crawlFrameworkModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
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
                } else if (childName.equals("InfluentValve")) {
                    if (firstPass)
                        createInfluentValve(child);
                    else
                        configureInfluentValve(child);
                } else if (childName.equals("EffluentValve")) {
                    if (firstPass)
                        createEffluentValve(child);
                    else
                        configureEffluentValve(child);
                }
            }
            child = child.getNextSibling();
        }
    }

    // Transformed Food Modules methods

    private void createBiomassPS(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        BiomassPS biomassPS = new BiomassPS(myID, moduleName);
        boolean autoHarvestAndReplant = XMLUtils.getBooelanAttribute(node, "autoHarvestAndReplant");
        boolean deathEnabled = XMLUtils.getBooelanAttribute(node, "isDeathEnabled");
        biomassPS.setAutoHarvestAndReplantEnabled(autoHarvestAndReplant);
        biomassPS.setDeathEnabled(deathEnabled);
        BiosimInitializer.setupBioModule(myID, biomassPS, node);
        Node child = node.getFirstChild();
        while (child != null) {
            if ("shelf".equals(child.getLocalName())) {
                biomassPS.createNewShelf(getCropType(child), getCropArea(child), getCropStartDay(child));
            }
            child = child.getNextSibling();
        }
    }

    private void configureBiomassPS(Node node) {
        BiomassPS biomassPS = (BiomassPS) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(biomassPS, node);
        myActiveSimModules.add(biomassPS);
    }

    private void createFoodProcessor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        FoodProcessor foodProcessor = new FoodProcessor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, foodProcessor, node);
    }

    private void configureFoodProcessor(Node node) {
        FoodProcessor foodProcessor = (FoodProcessor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(foodProcessor, node);
        myActiveSimModules.add(foodProcessor);
    }

    private void createBiomassStore(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        BiomassStore biomassStore = new BiomassStore(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, biomassStore, node);
        float inedibleFraction = Float.parseFloat(node.getAttributes().getNamedItem("inedibleFraction").getNodeValue());
        float edibleWaterContent = Float.parseFloat(node.getAttributes().getNamedItem("edibleWaterContent").getNodeValue());
        float inedibleWaterContent = Float.parseFloat(node.getAttributes().getNamedItem("inedibleWaterContent").getNodeValue());
        PlantType cropType = getCropType(node);
        BioMatter bioMatter = new BioMatter(getStoreLevel(node), inedibleFraction, edibleWaterContent, inedibleWaterContent, cropType);
        biomassStore.setInitialCapacity(getStoreCapacity(node));
        biomassStore.setInitialBioMatterLevel(bioMatter);
        biomassStore.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
    }

    private void createFoodStore(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        FoodStore foodStore = new FoodStore(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, foodStore, node);
        float waterContent = Float.parseFloat(node.getAttributes().getNamedItem("waterContent").getNodeValue());
        PlantType cropType = getCropType(node);
        FoodMatter foodMatter = new FoodMatter(getStoreLevel(node), waterContent, cropType);
        foodStore.setInitialCapacity(getStoreCapacity(node));
        foodStore.setInitialFoodMatterLevel(foodMatter);
        foodStore.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
    }

    private void crawlFoodModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("BiomassPS")) {
                    if (firstPass)
                        createBiomassPS(child);
                    else
                        configureBiomassPS(child);
                } else if (childName.equals("FoodProcessor")) {
                    if (firstPass)
                        createFoodProcessor(child);
                    else
                        configureFoodProcessor(child);
                } else if (childName.equals("BiomassStore")) {
                    if (firstPass)
                        createBiomassStore(child);
                } else if (childName.equals("FoodStore")) {
                    if (firstPass)
                        createFoodStore(child);
                }
            }
            child = child.getNextSibling();
        }
    }

    // Transformed Power Modules methods

    private void createPowerPS(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        String generationType = node.getAttributes().getNamedItem("generationType").getNodeValue();
        PowerPS powerPS;
        if (generationType.equals("SOLAR")) {
            powerPS = new SolarPowerPS(myID, moduleName);
        } else if (generationType.equals("STATE_MACHINE")) {
            powerPS = new StateMachinePowerPS(myID, moduleName);
        } else {
            powerPS = new NuclearPowerPS(myID, moduleName);
        }
        float upperPowerGeneration = Float.parseFloat(node.getAttributes().getNamedItem("upperPowerGeneration").getNodeValue());
        powerPS.setInitialUpperPowerGeneration(upperPowerGeneration);
        BiosimInitializer.setupBioModule(myID, powerPS, node);
    }

    private void configurePowerPS(Node node) {
        PowerPS powerPS = (PowerPS) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(powerPS, node);
        myActiveSimModules.add(powerPS);
    }

    private void createGenericPowerConsumer(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        GenericPowerConsumer consumer = new GenericPowerConsumer(myID, moduleName);
        consumer.setPowerRequired(getAttributeFloat(node, "powerRequired"));
        BiosimInitializer.setupBioModule(myID, consumer, node);
    }

    private void configureGenericPowerConsumer(Node node) {
        GenericPowerConsumer consumer = (GenericPowerConsumer) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(consumer, node);
        myActiveSimModules.add(consumer);
    }

    private void createRPCM(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        RPCM rpcm = new RPCM(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, rpcm, node);
    }

    private void configureRPCM(Node node) {
        RPCM rpcm = (RPCM) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(rpcm, node);
        myActiveSimModules.add(rpcm);
    }

    private void createPowerStore(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PowerStore powerStore = new PowerStore(myID, moduleName);
        setupStore(powerStore, node);
    }

    private void crawlPowerModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("PowerPS")) {
                    if (firstPass)
                        createPowerPS(child);
                    else
                        configurePowerPS(child);
                } else if (childName.equals("GenericPowerConsumer")) {
                    if (firstPass)
                        createGenericPowerConsumer(child);
                    else
                        configureGenericPowerConsumer(child);
                } else if (childName.equals("RPCM")) {
                    if (firstPass)
                        createRPCM(child);
                    else
                        configureRPCM(child);
                } else if (childName.equals("PowerStore")) {
                    if (firstPass)
                        createPowerStore(child);
                }
            }
            child = child.getNextSibling();
        }
    }

    // Transformed Water Modules methods

    private void createWaterRS(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        String implementationString = node.getAttributes().getNamedItem("implementation").getNodeValue();
        if (implementationString.equals("LINEAR")) {
            myLogger.debug("Creating linear WaterRS with moduleName: " + moduleName);
            WaterRSLinear waterRS = new WaterRSLinear(myID, moduleName);
            BiosimInitializer.setupBioModule(myID, waterRS, node);
        } else {
            WaterRS waterRS = new WaterRS(myID, moduleName);
            BiosimInitializer.setupBioModule(myID, waterRS, node);
        }
    }

    private void configureWaterRS(Node node) {
        AbstractWaterRS waterRS = (AbstractWaterRS) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(waterRS, node);
        myActiveSimModules.add(waterRS);
    }

    private void createPotableWaterStore(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PotableWaterStore potableWaterStore = new PotableWaterStore(myID, moduleName);
        setupStore(potableWaterStore, node);
    }

    private void createDirtyWaterStore(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DirtyWaterStore dirtyWaterStore = new DirtyWaterStore(myID, moduleName);
        setupStore(dirtyWaterStore, node);
    }

    private void createGreyWaterStore(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        GreyWaterStore greyWaterStore = new GreyWaterStore(myID, moduleName);
        setupStore(greyWaterStore, node);
    }

    private void crawlWaterModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("WaterRS")) {
                    if (firstPass)
                        createWaterRS(child);
                    else
                        configureWaterRS(child);
                } else if (childName.equals("PotableWaterStore")) {
                    if (firstPass)
                        createPotableWaterStore(child);
                } else if (childName.equals("GreyWaterStore")) {
                    if (firstPass)
                        createGreyWaterStore(child);
                } else if (childName.equals("DirtyWaterStore")) {
                    if (firstPass)
                        createDirtyWaterStore(child);
                }
            }
            child = child.getNextSibling();
        }
    }

    // Transformed Waste Modules methods

    private void createIncinerator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        Incinerator incinerator = new Incinerator(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, incinerator, node);
    }

    private void configureIncinerator(Node node) {
        Incinerator incinerator = (Incinerator) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(incinerator, node);
        myActiveSimModules.add(incinerator);
    }

    private void createDryWasteStore(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DryWasteStore dryWasteStore = new DryWasteStore(myID, moduleName);
        setupStore(dryWasteStore, node);
    }

    // Transformed Thermal Modules methods

    private void createIATCS(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        IATCS iatcs = new IATCS(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, iatcs, node);
    }

    private void configureIATCS(Node node) {
        IATCS iatcs = (IATCS) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSimBioModule(iatcs, node);
        myActiveSimModules.add(iatcs);
    }

    private void crawlWasteModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("Incinerator")) {
                    if (firstPass)
                        createIncinerator(child);
                    else
                        configureIncinerator(child);
                } else if (childName.equals("DryWasteStore")) {
                    if (firstPass)
                        createDryWasteStore(child);
                }
            }
            child = child.getNextSibling();
        }
    }

    private void crawlThermalModules(Node node, boolean firstPass) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                if (childName.equals("IATCS")) {
                    if (firstPass)
                        createIATCS(child);
                    else
                        configureIATCS(child);
                }
            }
            child = child.getNextSibling();
        }
    }

    /**
     * @return Returns the myActiveSimModules.
     */
    public Set<SimBioModule> getActiveSimModules() {
        return myActiveSimModules;
    }

    /**
     * @return Returns the myPassiveSimModules.
     */
    public Set<PassiveModule> getPassiveSimModules() {
        return myPassiveSimModules;
    }

    /**
     * @return Returns the myPrioritySimModules.
     */
    public Set<SimBioModule> getPrioritySimModules() {
        return myPrioritySimModules;
    }
}