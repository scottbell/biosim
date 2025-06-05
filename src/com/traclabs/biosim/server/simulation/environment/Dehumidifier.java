package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducer;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinition;

/**
 * The basic Dehumidifier implementation.
 *
 * @author Scott Bell
 */
public class Dehumidifier extends SimBioModule implements AirConsumer, DirtyWaterProducer {

    public static final float OPTIMAL_MOISTURE_CONCENTRATION = 0.52f;

    // Consumers, Producers
    private final AirConsumerDefinition myAirConsumerDefinition;
    private final DirtyWaterProducerDefinition myDirtyWaterProducerDefinition;

    // 1.0 = fully operational, 0.0 = completely non-operational
    private float operationalEfficiency = 1.0f;

    public Dehumidifier(int pID, String pName) {
        super(pID, pName);
        myAirConsumerDefinition = new AirConsumerDefinition(this);
        myDirtyWaterProducerDefinition = new DirtyWaterProducerDefinition(this);
    }

    public Dehumidifier() {
        this(0, "Unnamed Dehumidifier");
    }

    private static float calculateMolesNeededToRemove(SimEnvironment pEnvironment) {
        float currentWaterMolesInEnvironment = pEnvironment.getVaporStore().getCurrentLevel();
        float totalMolesInEnvironment = pEnvironment.getTotalMoles();
        if ((currentWaterMolesInEnvironment / totalMolesInEnvironment) > OPTIMAL_MOISTURE_CONCENTRATION) {
            float waterMolesAtOptimalConcentration =
                    ((totalMolesInEnvironment - currentWaterMolesInEnvironment) * OPTIMAL_MOISTURE_CONCENTRATION)
                    / (1 - OPTIMAL_MOISTURE_CONCENTRATION);
            return currentWaterMolesInEnvironment - waterMolesAtOptimalConcentration;
        }
        return 0f;
    }

    private static float waterMolesToLiters(float pMoles) {
        return (pMoles * 18.01524f) / 1000f; // 1000g/liter, 18.01524g/mole
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinition;
    }

    public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
        return myDirtyWaterProducerDefinition;
    }

    @Override
    public void tick() {
        super.tick();
        dehumidifyEnvironments();
    }

    private void dehumidifyEnvironments() {
        if (myLogger.isDebugEnabled()) {
            float beforeWater = myAirConsumerDefinition.getEnvironments()[0].getVaporStore().getCurrentLevel();
            float beforeTotal = myAirConsumerDefinition.getEnvironments()[0].getTotalMoles();
            myLogger.debug("Before: Water concentration " + (beforeWater / beforeTotal));
        }

        float molesOfWaterGathered = 0f;
        for (int i = 0; i < myAirConsumerDefinition.getEnvironments().length; i++) {
            float molesNeededToRemove = calculateMolesNeededToRemove(myAirConsumerDefinition.getEnvironments()[i]);
            if (molesNeededToRemove > 0) {
                float resourceToGatherFirst = Math.min(molesNeededToRemove, myAirConsumerDefinition.getMaxFlowRate(i));
                float desired = myAirConsumerDefinition.getDesiredFlowRate(i) * operationalEfficiency;
                float resourceToGatherFinal = Math.min(resourceToGatherFirst, desired);

                float taken = myAirConsumerDefinition.getEnvironments()[i]
                        .getVaporStore().take(resourceToGatherFinal);
                myAirConsumerDefinition.getActualFlowRates()[i] = taken;
                molesOfWaterGathered += taken;
            }
        }

        float waterPushedToStore = myDirtyWaterProducerDefinition
                .pushResourceToStores(waterMolesToLiters(molesOfWaterGathered));

        if (myLogger.isDebugEnabled()) {
            float afterWater = myAirConsumerDefinition.getEnvironments()[0].getVaporStore().getCurrentLevel();
            float afterTotal = myAirConsumerDefinition.getEnvironments()[0].getTotalMoles();
            myLogger.debug("After: Pushed " + waterPushedToStore
                    + " liters (gathered " + molesOfWaterGathered
                    + " moles), concentration now " + (afterWater / afterTotal));
        }
    }

    @Override
    protected void performMalfunctions() {
        if (!myMalfunctions.isEmpty()) {
            // Default to fully operational
            operationalEfficiency = 1.0f;

            // Get the most severe malfunction
            MalfunctionIntensity worstIntensity = MalfunctionIntensity.LOW_MALF;
            for (Malfunction malfunction : myMalfunctions.values()) {
                if (malfunction.getIntensity().ordinal() > worstIntensity.ordinal()) {
                    worstIntensity = malfunction.getIntensity();
                }
                if (!malfunction.hasPerformed()) {
                    myLogger.debug("Performing malfunction: " + malfunction);
                    malfunction.setPerformed(true);
                }
            }

            // Apply reduction based on intensity
            if (worstIntensity == MalfunctionIntensity.LOW_MALF) {
                operationalEfficiency = 0.75f;
            } else if (worstIntensity == MalfunctionIntensity.MEDIUM_MALF) {
                operationalEfficiency = 0.25f;
            } else if (worstIntensity == MalfunctionIntensity.SEVERE_MALF) {
                operationalEfficiency = 0.0f;
            }
        } else {
            // No malfunctions, fully operational
            operationalEfficiency = 1.0f;
        }
    }

    @Override
    public void reset() {
        super.reset();
        myAirConsumerDefinition.reset();
        myDirtyWaterProducerDefinition.reset();
        operationalEfficiency = 1.0f;
    }

    @Override
    public void log() {
        myLogger.debug("operational_efficiency=" + operationalEfficiency);
    }
}
