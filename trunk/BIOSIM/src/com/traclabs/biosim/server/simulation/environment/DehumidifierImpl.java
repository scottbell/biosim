package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.DehumidifierOperations;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinitionImpl;

/**
 * The basic Dehimidifier Implementation.
 * 
 * @author Scott Bell
 */

public class DehumidifierImpl extends SimBioModuleImpl implements
        DehumidifierOperations, AirConsumerOperations,
        DirtyWaterProducerOperations {

    //Consumers, Producers
    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private DirtyWaterProducerDefinitionImpl myDirtyWaterProducerDefinitionImpl;
    
    public static final float OPTIMAL_MOISTURE_CONCENTRATION = 0.0218910f;
    //  in kPA assuming 101 kPa total pressure and air temperature of 23C and relative humidity of 80%

    public DehumidifierImpl(int pID, String pName) {
        super(pID, pName);
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl(this);
        myDirtyWaterProducerDefinitionImpl = new DirtyWaterProducerDefinitionImpl(this);
    }
    
    public DehumidifierImpl() {
        this(0, "Unnamed Dehumidifier");
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }

    public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
        return myDirtyWaterProducerDefinitionImpl.getCorbaObject();
    }

    public void tick() {
        super.tick();
        dehumidifyEnvironments();
        //myLogger.debug(getModuleName() + " ticked");
    }

    private void dehumidifyEnvironments() {
        if (myLogger.isDebugEnabled()) {
            float currentWaterMolesInEnvironment = myAirConsumerDefinitionImpl
                    .getEnvironments()[0].getVaporStore().getCurrentLevel();
            float totalMolesInEnvironment = myAirConsumerDefinitionImpl
                    .getEnvironments()[0].getTotalMoles();
            //myAirInputs[0].printCachedEnvironment();
            //myLogger.debug("Before: Water concentration"
             //       + currentWaterMolesInEnvironment / totalMolesInEnvironment);
        }
        float molesOfWaterGathered = 0f;
        for (int i = 0; i < myAirConsumerDefinitionImpl.getEnvironments().length; i++) {
            float molesNeededToRemove = calculateMolesNeededToRemove(myAirConsumerDefinitionImpl
                    .getEnvironments()[i]);
            if (molesNeededToRemove > 0) {
                //cycle through and take molesNeededToRemove (or less if
                // desired is less)
                float resourceToGatherFirst = Math.min(molesNeededToRemove,
                        myAirConsumerDefinitionImpl.getMaxFlowRate(i));
                float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                        myAirConsumerDefinitionImpl.getDesiredFlowRate(i));
                myAirConsumerDefinitionImpl.getActualFlowRates()[i] = myAirConsumerDefinitionImpl
                        .getEnvironments()[i].getVaporStore().take(resourceToGatherFinal);
               // myLogger.debug("Going to remove " + resourceToGatherFinal
                //        + " moles of water");
                molesOfWaterGathered += myAirConsumerDefinitionImpl
                        .getActualFlowRate(i);
            }
        }
        float waterPushedToStore = myDirtyWaterProducerDefinitionImpl
                .pushResourceToStores(waterMolesToLiters(molesOfWaterGathered));

        if (myLogger.isDebugEnabled()) {
            float currentWaterMolesInEnvironment = myAirConsumerDefinitionImpl
                    .getEnvironments()[0].getVaporStore().getCurrentLevel();
            float totalMolesInEnvironment = myAirConsumerDefinitionImpl
                    .getEnvironments()[0].getTotalMoles();
            myLogger.debug("After: Pushed " + waterPushedToStore
                    + " liters of water to the store (gathered "
                    + molesOfWaterGathered
                    + " moles), water concentration now "
                    + currentWaterMolesInEnvironment / totalMolesInEnvironment);
        }
    }

    private static float calculateMolesNeededToRemove(
            SimEnvironment pEnvironment) {
        float currentWaterMolesInEnvironment = pEnvironment.getVaporStore().getCurrentLevel();
        float totalMolesInEnvironment = pEnvironment.getTotalMoles();
        if ((currentWaterMolesInEnvironment / totalMolesInEnvironment) > OPTIMAL_MOISTURE_CONCENTRATION) {
            float waterMolesAtOptimalConcentration = ((totalMolesInEnvironment - currentWaterMolesInEnvironment) * OPTIMAL_MOISTURE_CONCENTRATION)
                    / (1 - OPTIMAL_MOISTURE_CONCENTRATION);
            return currentWaterMolesInEnvironment
                    - waterMolesAtOptimalConcentration;
        }
		return 0f;
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
        myAirConsumerDefinitionImpl.reset();
        myDirtyWaterProducerDefinitionImpl.reset();
    }

    public void log() {
    }
}