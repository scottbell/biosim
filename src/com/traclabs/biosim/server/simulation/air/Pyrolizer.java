package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinition;


public class Pyrolizer extends SimBioModule {

    //Consumers, Producers
    private PowerConsumerDefinition myPowerConsumerDefinition;

    private MethaneConsumerDefinition myMethaneConsumerDefinition;

    private DryWasteProducerDefinition myDryWasteProducerDefinition;

    private H2ProducerDefinition myH2ProducerDefinition;

    public Pyrolizer(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myMethaneConsumerDefinition = new MethaneConsumerDefinition(this);
        myDryWasteProducerDefinition = new DryWasteProducerDefinition(this);
        myH2ProducerDefinition = new H2ProducerDefinition(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition;
    }

    public MethaneConsumerDefinition getMethaneConsumerDefinition() {
        return myMethaneConsumerDefinition;
    }

    public DryWasteProducerDefinition getDryWasteProducerDefinition() {
        return myDryWasteProducerDefinition;
    }

    public H2ProducerDefinition getH2ProducerDefinition() {
        return myH2ProducerDefinition;
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        myPowerConsumerDefinition.reset();
        myMethaneConsumerDefinition.reset();
        myDryWasteProducerDefinition.reset();
        myH2ProducerDefinition.reset();
    }

    public void tick() {
        super.tick();
    }
}