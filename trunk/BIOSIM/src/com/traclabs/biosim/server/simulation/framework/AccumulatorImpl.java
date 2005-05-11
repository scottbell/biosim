package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.CO2AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.CO2AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.CO2AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.CO2AirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.O2AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.O2AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.O2AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.O2AirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.WaterAirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.WaterAirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.WaterAirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.WaterAirProducerOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerDefinition;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerDefinition;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerOperations;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinition;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerOperations;
import com.traclabs.biosim.idl.simulation.food.FoodProducerDefinition;
import com.traclabs.biosim.idl.simulation.food.FoodProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.AccumulatorOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerProducerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerProducerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterProducerOperations;
import com.traclabs.biosim.server.simulation.air.CO2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.H2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.H2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.NitrogenConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.NitrogenProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.O2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.O2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.CO2AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.CO2AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.NitrogenAirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.NitrogenAirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.O2AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.O2AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.WaterAirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.WaterAirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.BiomassConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.BiomassProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.FoodConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.FoodProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.power.PowerProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.WaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.WaterProducerDefinitionImpl;

/**
 * The basic Accumulator Implementation. Can be configured to take any modules
 * as input, and any modules as output. It takes as much as it can (max taken
 * set by maxFlowRates) from one module and pushes it into another module.
 * Functionally equivalent to an Accumulator at this point.
 * 
 * @author Scott Bell
 */

public class AccumulatorImpl extends SimBioModuleImpl implements
        AccumulatorOperations, PowerConsumerOperations,
        PotableWaterConsumerOperations, GreyWaterConsumerOperations,
        WaterConsumerOperations, DirtyWaterConsumerOperations,
        O2ConsumerOperations, CO2ConsumerOperations, AirConsumerOperations,
        BiomassConsumerOperations, FoodConsumerOperations,
        CO2AirConsumerOperations, O2AirConsumerOperations,
        WaterAirConsumerOperations, NitrogenAirConsumerOperations,
        DryWasteConsumerOperations, H2ConsumerOperations,
        NitrogenConsumerOperations, PowerProducerOperations,
        PotableWaterProducerOperations, GreyWaterProducerOperations,
        WaterProducerOperations, DirtyWaterProducerOperations,
        O2ProducerOperations, CO2ProducerOperations, AirProducerOperations,
        BiomassProducerOperations, FoodProducerOperations,
        CO2AirProducerOperations, O2AirProducerOperations,
        WaterAirProducerOperations, NitrogenAirProducerOperations,
        DryWasteProducerOperations, H2ProducerOperations,
        NitrogenProducerOperations {

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;

    private GreyWaterConsumerDefinitionImpl myGreyWaterConsumerDefinitionImpl;

    private DirtyWaterConsumerDefinitionImpl myDirtyWaterConsumerDefinitionImpl;

    private O2ConsumerDefinitionImpl myO2ConsumerDefinitionImpl;

    private CO2ConsumerDefinitionImpl myCO2ConsumerDefinitionImpl;

    private H2ConsumerDefinitionImpl myH2ConsumerDefinitionImpl;

    private NitrogenConsumerDefinitionImpl myNitrogenConsumerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private BiomassConsumerDefinitionImpl myBiomassConsumerDefinitionImpl;

    private FoodConsumerDefinitionImpl myFoodConsumerDefinitionImpl;

    private O2AirConsumerDefinitionImpl myO2AirConsumerDefinitionImpl;

    private CO2AirConsumerDefinitionImpl myCO2AirConsumerDefinitionImpl;

    private NitrogenAirConsumerDefinitionImpl myNitrogenAirConsumerDefinitionImpl;

    private WaterAirConsumerDefinitionImpl myWaterAirConsumerDefinitionImpl;

    private DryWasteConsumerDefinitionImpl myDryWasteConsumerDefinitionImpl;

    private WaterConsumerDefinitionImpl myWaterConsumerDefinitionImpl;

    private PowerProducerDefinitionImpl myPowerProducerDefinitionImpl;

    private PotableWaterProducerDefinitionImpl myPotableWaterProducerDefinitionImpl;

    private GreyWaterProducerDefinitionImpl myGreyWaterProducerDefinitionImpl;

    private DirtyWaterProducerDefinitionImpl myDirtyWaterProducerDefinitionImpl;

    private O2ProducerDefinitionImpl myO2ProducerDefinitionImpl;

    private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;

    private H2ProducerDefinitionImpl myH2ProducerDefinitionImpl;

    private NitrogenProducerDefinitionImpl myNitrogenProducerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

    private BiomassProducerDefinitionImpl myBiomassProducerDefinitionImpl;

    private FoodProducerDefinitionImpl myFoodProducerDefinitionImpl;

    private O2AirProducerDefinitionImpl myO2AirProducerDefinitionImpl;

    private CO2AirProducerDefinitionImpl myCO2AirProducerDefinitionImpl;

    private NitrogenAirProducerDefinitionImpl myNitrogenAirProducerDefinitionImpl;

    private WaterAirProducerDefinitionImpl myWaterAirProducerDefinitionImpl;

    private DryWasteProducerDefinitionImpl myDryWasteProducerDefinitionImpl;

    private WaterProducerDefinitionImpl myWaterProducerDefinitionImpl;

    public AccumulatorImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl();
        myGreyWaterConsumerDefinitionImpl = new GreyWaterConsumerDefinitionImpl();
        myDirtyWaterConsumerDefinitionImpl = new DirtyWaterConsumerDefinitionImpl();
        myO2ConsumerDefinitionImpl = new O2ConsumerDefinitionImpl();
        myCO2ConsumerDefinitionImpl = new CO2ConsumerDefinitionImpl();
        myH2ConsumerDefinitionImpl = new H2ConsumerDefinitionImpl();
        myNitrogenConsumerDefinitionImpl = new NitrogenConsumerDefinitionImpl();
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl();
        myBiomassConsumerDefinitionImpl = new BiomassConsumerDefinitionImpl();
        myFoodConsumerDefinitionImpl = new FoodConsumerDefinitionImpl();
        myO2AirConsumerDefinitionImpl = new O2AirConsumerDefinitionImpl();
        myCO2AirConsumerDefinitionImpl = new CO2AirConsumerDefinitionImpl();
        myNitrogenAirConsumerDefinitionImpl = new NitrogenAirConsumerDefinitionImpl();
        myWaterAirConsumerDefinitionImpl = new WaterAirConsumerDefinitionImpl();
        myDryWasteConsumerDefinitionImpl = new DryWasteConsumerDefinitionImpl();
        myWaterConsumerDefinitionImpl = new WaterConsumerDefinitionImpl();

        myPowerProducerDefinitionImpl = new PowerProducerDefinitionImpl();
        myPotableWaterProducerDefinitionImpl = new PotableWaterProducerDefinitionImpl();
        myGreyWaterProducerDefinitionImpl = new GreyWaterProducerDefinitionImpl();
        myDirtyWaterProducerDefinitionImpl = new DirtyWaterProducerDefinitionImpl();
        myO2ProducerDefinitionImpl = new O2ProducerDefinitionImpl();
        myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl();
        myH2ProducerDefinitionImpl = new H2ProducerDefinitionImpl();
        myNitrogenProducerDefinitionImpl = new NitrogenProducerDefinitionImpl();
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl();
        myBiomassProducerDefinitionImpl = new BiomassProducerDefinitionImpl();
        myFoodProducerDefinitionImpl = new FoodProducerDefinitionImpl();
        myO2AirProducerDefinitionImpl = new O2AirProducerDefinitionImpl();
        myCO2AirProducerDefinitionImpl = new CO2AirProducerDefinitionImpl();
        myNitrogenAirProducerDefinitionImpl = new NitrogenAirProducerDefinitionImpl();
        myWaterAirProducerDefinitionImpl = new WaterAirProducerDefinitionImpl();
        myDryWasteProducerDefinitionImpl = new DryWasteProducerDefinitionImpl();
        myWaterProducerDefinitionImpl = new WaterProducerDefinitionImpl();
    }

    public void tick() {
        super.tick();
        getAndPushResources();
    }

    private void getAndPushResources() {
        float powerGathered = myPowerConsumerDefinitionImpl
                .getMostResourceFromStore();
        float powerPushed = myPowerProducerDefinitionImpl
                .pushResourceToStore(powerGathered);

        float potableWaterGathered = myPotableWaterConsumerDefinitionImpl
                .getMostResourceFromStore();
        float potableWaterPushed = myPotableWaterProducerDefinitionImpl
                .pushResourceToStore(potableWaterGathered);

        float greyWaterGathered = myGreyWaterConsumerDefinitionImpl
                .getMostResourceFromStore();
        float greyWaterPushed = myGreyWaterProducerDefinitionImpl
                .pushResourceToStore(greyWaterGathered);

        float dirtyWaterGathered = myDirtyWaterConsumerDefinitionImpl
                .getMostResourceFromStore();
        float dirtyWaterPushed = myDirtyWaterProducerDefinitionImpl
                .pushResourceToStore(dirtyWaterGathered);

        float biomassGathered = myBiomassConsumerDefinitionImpl
                .getMostResourceFromStore();
        float biomassPushed = myBiomassProducerDefinitionImpl
                .pushResourceToStore(biomassGathered);

        float foodGathered = myFoodConsumerDefinitionImpl
                .getMostResourceFromStore();
        float foodPushed = myFoodProducerDefinitionImpl
                .pushResourceToStore(foodGathered);

        float dryWasteGathered = myDryWasteConsumerDefinitionImpl
                .getMostResourceFromStore();
        float dryWastePushed = myDryWasteProducerDefinitionImpl
                .pushResourceToStore(dryWasteGathered);

        float O2Gathered = myO2ConsumerDefinitionImpl
                .getMostResourceFromStore();
        float O2Pushed = myO2ProducerDefinitionImpl
                .pushResourceToStore(O2Gathered);

        float CO2Gathered = myCO2ConsumerDefinitionImpl
                .getMostResourceFromStore();
        float CO2Pushed = myCO2ProducerDefinitionImpl
                .pushResourceToStore(CO2Gathered);

        float nitrogenGathered = myNitrogenConsumerDefinitionImpl
                .getMostResourceFromStore();
        float nitrogenPushed = myNitrogenProducerDefinitionImpl
                .pushResourceToStore(nitrogenGathered);

        float H2Gathered = myH2ConsumerDefinitionImpl
                .getMostResourceFromStore();
        float H2Pushed = myH2ProducerDefinitionImpl
                .pushResourceToStore(nitrogenGathered);

        Breath gatheredAir = myAirConsumerDefinitionImpl
                .getMostAirFromEnvironment();

        Breath distributedAir = myAirProducerDefinitionImpl
                .pushAirToEnvironments(gatheredAir);

        //Get CO2 from stores/environment
        float gatheredCO2Air = myCO2AirConsumerDefinitionImpl
                .getMostResourceFromStore();
        gatheredCO2Air += myCO2AirConsumerDefinitionImpl
                .getMostCO2FromEnvironment();
        //Push CO2 to stores/environment
        float CO2AirPushed = myCO2AirProducerDefinitionImpl
                .pushResourceToStore(gatheredCO2Air);
        float CO2AirLeft = gatheredCO2Air - CO2AirPushed;
        CO2AirLeft += myCO2AirProducerDefinitionImpl
                .pushCO2ToEnvironment(CO2AirLeft);
        CO2AirPushed = gatheredCO2Air - CO2AirLeft;

        //Get O2 from stores/environment
        float gatheredO2Air = myO2AirConsumerDefinitionImpl
                .getMostResourceFromStore();
        gatheredO2Air += myO2AirConsumerDefinitionImpl
                .getMostO2FromEnvironment();
        //Push O2 to stores/environment
        float O2AirPushed = myO2AirProducerDefinitionImpl
                .pushResourceToStore(gatheredO2Air);
        float O2AirLeft = gatheredO2Air - O2AirPushed;
        O2AirLeft += myO2AirProducerDefinitionImpl
                .pushO2ToEnvironment(O2AirLeft);
        O2AirPushed = gatheredO2Air - O2AirLeft;

        //Get Nitrogen from stores/environment
        float gatheredNitrogenAir = myNitrogenAirConsumerDefinitionImpl
                .getMostResourceFromStore();
        gatheredNitrogenAir += myNitrogenAirConsumerDefinitionImpl
                .getMostNitrogenFromEnvironment();
        //Push Nitrogen to stores/environment
        float nitrogenAirPushed = myNitrogenAirProducerDefinitionImpl
                .pushResourceToStore(gatheredNitrogenAir);
        float nitrogenAirLeft = gatheredNitrogenAir - nitrogenAirPushed;
        nitrogenAirLeft -= myNitrogenAirProducerDefinitionImpl
                .pushNitrogenToEnvironment(nitrogenAirLeft);
        nitrogenAirPushed = gatheredNitrogenAir - nitrogenAirLeft;

        //Get Water from stores/environment
        float gatheredWaterAirLiters = myWaterAirConsumerDefinitionImpl
                .getMostResourceFromStore();
        gatheredWaterAirLiters += waterMolesToLiters(myWaterAirConsumerDefinitionImpl
                .getMostWaterFromEnvironment());

        //Push water to stores/environment
        float waterAirPushedLiters = myWaterAirProducerDefinitionImpl
                .pushResourceToStore(gatheredWaterAirLiters);

        float waterAirMolesLeft = waterLitersToMoles(gatheredWaterAirLiters
                - waterAirPushedLiters);
        waterAirMolesLeft -= myWaterAirProducerDefinitionImpl
                .pushWaterToEnvironment(waterAirMolesLeft);
        float waterAirPushedMoles = waterLitersToMoles(gatheredWaterAirLiters)
                - waterAirMolesLeft;
    }

    private static float waterLitersToMoles(float pLiters) {
        return (pLiters * 1000f) / 18.01524f; // 1000g/liter, 18.01524g/mole
    }

    private static float waterMolesToLiters(float pMoles) {
        return (pMoles * 18.01524f) / 1000f; // 1000g/liter, 18.01524g/mole
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        StringBuffer returnBuffer = new StringBuffer();
        if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
            returnBuffer.append("Severe ");
        else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
            returnBuffer.append("Medium ");
        else if (pIntensity == MalfunctionIntensity.LOW_MALF)
            returnBuffer.append("Low ");
        if (pLength == MalfunctionLength.TEMPORARY_MALF)
            returnBuffer.append("Temporary Production Reduction");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append("Permanent Production Reduction");
        return returnBuffer.toString();
    }

    protected void performMalfunctions() {
    }

    public void reset() {
        super.reset();
    }

    public void log() {
    }

    //Consumers
    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return myGreyWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public DirtyWaterConsumerDefinition getDirtyWaterConsumerDefinition() {
        return myDirtyWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public O2ConsumerDefinition getO2ConsumerDefinition() {
        return myO2ConsumerDefinitionImpl.getCorbaObject();
    }

    public CO2ConsumerDefinition getCO2ConsumerDefinition() {
        return myCO2ConsumerDefinitionImpl.getCorbaObject();
    }

    public NitrogenConsumerDefinition getNitrogenConsumerDefinition() {
        return myNitrogenConsumerDefinitionImpl.getCorbaObject();
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }

    public BiomassConsumerDefinition getBiomassConsumerDefinition() {
        return myBiomassConsumerDefinitionImpl.getCorbaObject();
    }

    public FoodConsumerDefinition getFoodConsumerDefinition() {
        return myFoodConsumerDefinitionImpl.getCorbaObject();
    }

    public O2AirConsumerDefinition getO2AirConsumerDefinition() {
        return myO2AirConsumerDefinitionImpl.getCorbaObject();
    }

    public CO2AirConsumerDefinition getCO2AirConsumerDefinition() {
        return myCO2AirConsumerDefinitionImpl.getCorbaObject();
    }

    public NitrogenAirConsumerDefinition getNitrogenAirConsumerDefinition() {
        return myNitrogenAirConsumerDefinitionImpl.getCorbaObject();
    }

    public DryWasteConsumerDefinition getDryWasteConsumerDefinition() {
        return myDryWasteConsumerDefinitionImpl.getCorbaObject();
    }

    public WaterAirConsumerDefinition getWaterAirConsumerDefinition() {
        return myWaterAirConsumerDefinitionImpl.getCorbaObject();
    }

    public H2ConsumerDefinition getH2ConsumerDefinition() {
        return myH2ConsumerDefinitionImpl.getCorbaObject();
    }

    public WaterConsumerDefinition getWaterConsumerDefinition() {
        return myWaterConsumerDefinitionImpl.getCorbaObject();
    }

    //Producers
    public PowerProducerDefinition getPowerProducerDefinition() {
        return myPowerProducerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
        return myPotableWaterProducerDefinitionImpl.getCorbaObject();
    }

    public GreyWaterProducerDefinition getGreyWaterProducerDefinition() {
        return myGreyWaterProducerDefinitionImpl.getCorbaObject();
    }

    public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
        return myDirtyWaterProducerDefinitionImpl.getCorbaObject();
    }

    public O2ProducerDefinition getO2ProducerDefinition() {
        return myO2ProducerDefinitionImpl.getCorbaObject();
    }

    public CO2ProducerDefinition getCO2ProducerDefinition() {
        return myCO2ProducerDefinitionImpl.getCorbaObject();
    }

    public NitrogenProducerDefinition getNitrogenProducerDefinition() {
        return myNitrogenProducerDefinitionImpl.getCorbaObject();
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinitionImpl.getCorbaObject();
    }

    public BiomassProducerDefinition getBiomassProducerDefinition() {
        return myBiomassProducerDefinitionImpl.getCorbaObject();
    }

    public FoodProducerDefinition getFoodProducerDefinition() {
        return myFoodProducerDefinitionImpl.getCorbaObject();
    }

    public O2AirProducerDefinition getO2AirProducerDefinition() {
        return myO2AirProducerDefinitionImpl.getCorbaObject();
    }

    public CO2AirProducerDefinition getCO2AirProducerDefinition() {
        return myCO2AirProducerDefinitionImpl.getCorbaObject();
    }

    public NitrogenAirProducerDefinition getNitrogenAirProducerDefinition() {
        return myNitrogenAirProducerDefinitionImpl.getCorbaObject();
    }

    public DryWasteProducerDefinition getDryWasteProducerDefinition() {
        return myDryWasteProducerDefinitionImpl.getCorbaObject();
    }

    public WaterAirProducerDefinition getWaterAirProducerDefinition() {
        return myWaterAirProducerDefinitionImpl.getCorbaObject();
    }

    public H2ProducerDefinition getH2ProducerDefinition() {
        return myH2ProducerDefinitionImpl.getCorbaObject();
    }

    public WaterProducerDefinition getWaterProducerDefinition() {
        return myWaterProducerDefinitionImpl.getCorbaObject();
    }
}