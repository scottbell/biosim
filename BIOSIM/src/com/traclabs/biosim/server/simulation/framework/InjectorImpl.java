package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.framework.InjectorOperations;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.BiomassConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.BiomassConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.CO2AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DryWasteConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.FoodConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.FoodConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.FoodProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.FoodProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.NitrogenConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.NitrogenProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.WaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.WaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.WaterProducerOperations;

/**
 * The basic Injector Implementation. Can be configured to take any modules
 * as input, and any modules as output. It takes as much as it can (max taken
 * set by maxFlowRates) from one module and pushes it into another module.
 * Functionally equivalent to an Injector at this point.
 * 
 * @author Scott Bell
 */

public class InjectorImpl extends SimBioModuleImpl implements
        InjectorOperations, PowerConsumerOperations,
        PotableWaterConsumerOperations, GreyWaterConsumerOperations, WaterConsumerOperations,
        DirtyWaterConsumerOperations, O2ConsumerOperations,
        CO2ConsumerOperations, AirConsumerOperations,
        BiomassConsumerOperations, FoodConsumerOperations, 
        CO2AirConsumerOperations, O2AirConsumerOperations,
        WaterAirConsumerOperations, NitrogenAirConsumerOperations,
        DryWasteConsumerOperations, H2ConsumerOperations, 
        NitrogenConsumerOperations,
        PowerProducerOperations,
        PotableWaterProducerOperations, GreyWaterProducerOperations, WaterProducerOperations,
        DirtyWaterProducerOperations, O2ProducerOperations,
        CO2ProducerOperations, AirProducerOperations,
        BiomassProducerOperations, FoodProducerOperations, 
        CO2AirProducerOperations, O2AirProducerOperations,
        WaterAirProducerOperations, NitrogenAirProducerOperations,
        DryWasteProducerOperations, H2ProducerOperations, 
        NitrogenProducerOperations
        {
    
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

    public InjectorImpl(int pID, String pName) {
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
        float powerGathered = myPowerConsumerDefinitionImpl.getMostResourceFromStore();
        float powerPushed = myPowerProducerDefinitionImpl.pushResourceToStore(powerGathered);
        
        float potableWaterGathered = myPotableWaterConsumerDefinitionImpl.getMostResourceFromStore();
        float potableWaterPushed = myPotableWaterProducerDefinitionImpl.pushResourceToStore(potableWaterGathered);

        float greyWaterGathered = myGreyWaterConsumerDefinitionImpl.getMostResourceFromStore();
        float greyWaterPushed = myGreyWaterProducerDefinitionImpl.pushResourceToStore(greyWaterGathered);

        float dirtyWaterGathered = myDirtyWaterConsumerDefinitionImpl.getMostResourceFromStore();
        float dirtyWaterPushed = myDirtyWaterProducerDefinitionImpl.pushResourceToStore(dirtyWaterGathered);

        float biomassGathered = myBiomassConsumerDefinitionImpl.getMostResourceFromStore();
        float biomassPushed = myBiomassProducerDefinitionImpl.pushResourceToStore(biomassGathered);

        float foodGathered = myFoodConsumerDefinitionImpl.getMostResourceFromStore();
        float foodPushed = myFoodProducerDefinitionImpl.pushResourceToStore(foodGathered);

        float dryWasteGathered = myDryWasteConsumerDefinitionImpl.getMostResourceFromStore();
        float dryWastePushed = myDryWasteProducerDefinitionImpl.pushResourceToStore(dryWasteGathered);

        float O2Gathered = myO2ConsumerDefinitionImpl.getMostResourceFromStore();
        float O2Pushed = myO2ProducerDefinitionImpl.pushResourceToStore(O2Gathered);

        float CO2Gathered = myCO2ConsumerDefinitionImpl.getMostResourceFromStore();
        float CO2Pushed = myCO2ProducerDefinitionImpl.pushResourceToStore(CO2Gathered);

        float nitrogenGathered = myNitrogenConsumerDefinitionImpl.getMostResourceFromStore();
        float nitrogenPushed = myNitrogenProducerDefinitionImpl.pushResourceToStore(nitrogenGathered);

        float H2Gathered = myH2ConsumerDefinitionImpl.getMostResourceFromStore();
        float H2Pushed = myH2ProducerDefinitionImpl.pushResourceToStore(nitrogenGathered);
        
        Breath gatheredAir = myAirConsumerDefinitionImpl.getMostAirFromEnvironment();
       

        Breath distributedAir = myAirProducerDefinitionImpl.pushAirToEnvironments(gatheredAir);

        //Get CO2 from stores/environment
        float gatheredCO2Air = myCO2AirConsumerDefinitionImpl.getMostResourceFromStore();
        gatheredCO2Air += myCO2AirConsumerDefinitionImpl.getMostCO2FromEnvironment();
        //Push CO2 to stores/environment
        float CO2AirPushed = myCO2AirProducerDefinitionImpl.pushResourceToStore(gatheredCO2Air);
        float CO2AirLeft = gatheredCO2Air - CO2AirPushed;
        CO2AirLeft -= myCO2AirProducerDefinitionImpl.pushCO2ToEnvironment(CO2AirLeft);
        CO2AirPushed = gatheredCO2Air - CO2AirLeft;

        //Get O2 from stores/environment
        float gatheredO2Air = myO2AirConsumerDefinitionImpl.getMostResourceFromStore();
        gatheredO2Air += myO2AirConsumerDefinitionImpl.getMostO2FromEnvironment();
        //Push O2 to stores/environment
        float O2AirPushed = myO2AirProducerDefinitionImpl.pushResourceToStore(gatheredO2Air);
        float O2AirLeft = gatheredO2Air - O2AirPushed;
        O2AirLeft -= myO2AirProducerDefinitionImpl.pushO2ToEnvironment(O2AirLeft);
        O2AirPushed = gatheredO2Air - O2AirLeft;

        //Get Nitrogen from stores/environment
        float gatheredNitrogenAir = myNitrogenAirConsumerDefinitionImpl.getMostResourceFromStore();
        gatheredNitrogenAir += myNitrogenAirConsumerDefinitionImpl.getMostNitrogenFromEnvironment();
        //Push Nitrogen to stores/environment
        float nitrogenAirPushed = myNitrogenAirProducerDefinitionImpl.pushResourceToStore(gatheredNitrogenAir);
        float nitrogenAirLeft = gatheredNitrogenAir - nitrogenAirPushed;
        nitrogenAirLeft -= myNitrogenAirProducerDefinitionImpl.pushNitrogenToEnvironment(nitrogenAirLeft);
        nitrogenAirPushed = gatheredNitrogenAir - nitrogenAirLeft;
        
        //Get Water from stores/environment
        float gatheredWaterAirLiters = myWaterAirConsumerDefinitionImpl.getMostResourceFromStore();
        gatheredWaterAirLiters += waterMolesToLiters(myWaterAirConsumerDefinitionImpl.getMostWaterFromEnvironment());
        
        //Push water to stores/environment
        float waterAirPushedLiters = myWaterAirProducerDefinitionImpl.pushResourceToStore(gatheredWaterAirLiters);

        float waterAirMolesLeft = waterLitersToMoles(gatheredWaterAirLiters - waterAirPushedLiters);
        waterAirMolesLeft -= myWaterAirProducerDefinitionImpl.pushWaterToEnvironment(waterAirMolesLeft);
        float waterAirPushedMoles = waterLitersToMoles(gatheredWaterAirLiters) - waterAirMolesLeft;
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