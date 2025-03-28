package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;

/**
 * @author Scott Bell
 */

public class AbstractWaterRS extends SimBioModule implements PowerConsumer, GreyWaterConsumer, DirtyWaterConsumer, PotableWaterProducer {
    //Consumers, Producers
    protected final PowerConsumerDefinition myPowerConsumerDefinition;

    protected final GreyWaterConsumerDefinition myGreyWaterConsumerDefinition;

    protected final DirtyWaterConsumerDefinition myDirtyWaterConsumerDefinition;

    protected final PotableWaterProducerDefinition myPotableWaterProducerDefinition;

    public AbstractWaterRS(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myGreyWaterConsumerDefinition = new GreyWaterConsumerDefinition(this);
        myDirtyWaterConsumerDefinition = new DirtyWaterConsumerDefinition(this);
        myPotableWaterProducerDefinition = new PotableWaterProducerDefinition(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition;
    }

    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return myGreyWaterConsumerDefinition;
    }

    public DirtyWaterConsumerDefinition getDirtyWaterConsumerDefinition() {
        return myDirtyWaterConsumerDefinition;
    }

    public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
        return myPotableWaterProducerDefinition;
    }

    /**
     * Resets production/consumption levels
     */
    public void reset() {
        super.reset();
        myPowerConsumerDefinition.reset();
        myGreyWaterConsumerDefinition.reset();
        myDirtyWaterConsumerDefinition.reset();
        myPotableWaterProducerDefinition.reset();
    }
}