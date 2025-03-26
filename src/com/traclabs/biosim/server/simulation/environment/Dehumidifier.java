package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.server.simulation.environment.DehumidifierOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerOperations;

/**
 * The basic Dehimidifier implementation.
 * 
 * @author Scott Bell
 */

public class Dehumidifier extends SimBioModule implements
        DehumidifierOperations, AirConsumerOperations,
        DirtyWaterProducerOperations {

    //Consumers, Producers
    private AirConsumerDefinition myAirConsumerDefinition;

    private DirtyWaterProducerDefinition myDirtyWaterProducerDefinition;
    
    public static final float OPTIMAL_MOISTURE_CONCENTRATION = 0.0218910f;
    //  in kPA assuming 101 kPa total pressure and air temperature of 23C and relative humidity of 80%

    public Dehumidifier(int pID, String pName) {
        super(pID, pName);
        myAirConsumerDefinition = new AirConsumerDefinition(this);
        myDirtyWaterProducerDefinition = new DirtyWaterProducerDefinition(this);
    }
    
    public Dehumidifier() {
        this(0, "Unnamed Dehumidifier");
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinition.getCorbaObject();
    }

    public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
        return myDirtyWaterProducerDefinition.getCorbaObject();
    }

    public void tick() {
        super.tick();
        dehumidifyEnvironments();
        //myLogger.debug(getModuleName() + " ticked");
    }

    private void dehumidifyEnvironments() {
        if (myLogger.isDebugEnabled()) {
            float currentWaterMolesInEnvironment = myAirConsumerDefinition
                    .getEnvironments()[0].getVaporStore().getCurrentLevel();
            float totalMolesInEnvironment = myAirConsumerDefinition
                    .getEnvironments()[0].getTotalMoles();
            //myAirInputs[0].printCachedEnvironment();
            //myLogger.debug("Before: Water concentration"
             //       + currentWaterMolesInEnvironment / totalMolesInEnvironment);
        }
        float molesOfWaterGathered = 0f;
        for (int i = 0; i < myAirConsumerDefinition.getEnvironments().length; i++) {
            float molesNeededToRemove = calculateMolesNeededToRemove(myAirConsumerDefinition
                    .getEnvironments()[i]);
            if (molesNeededToRemove > 0) {
                //cycle through and take molesNeededToRemove (or less if
                // desired is less)
                float resourceToGatherFirst = Math.min(molesNeededToRemove,
                        myAirConsumerDefinition.getMaxFlowRate(i));
                float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                        myAirConsumerDefinition.getDesiredFlowRate(i));
                myAirConsumerDefinition.getActualFlowRates()[i] = myAirConsumerDefinition
                        .getEnvironments()[i].getVaporStore().take(resourceToGatherFinal);
               // myLogger.debug("Going to remove " + resourceToGatherFinal
                //        + " moles of water");
                molesOfWaterGathered += myAirConsumerDefinition
                        .getActualFlowRate(i);
            }
        }
        float waterPushedToStore = myDirtyWaterProducerDefinition
                .pushResourceToStores(waterMolesToLiters(molesOfWaterGathered));

        if (myLogger.isDebugEnabled()) {
            float currentWaterMolesInEnvironment = myAirConsumerDefinition
                    .getEnvironments()[0].getVaporStore().getCurrentLevel();
            float totalMolesInEnvironment = myAirConsumerDefinition
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
        myAirConsumerDefinition.reset();
        myDirtyWaterProducerDefinition.reset();
    }

    public void log() {
    }
}