package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducer;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinition;


public class Pyrolizer extends SimBioModule implements PowerConsumer, MethaneConsumer, DryWasteProducer, H2Producer {

    //Consumers, Producers
    private final PowerConsumerDefinition myPowerConsumerDefinition;

    private final MethaneConsumerDefinition myMethaneConsumerDefinition;

    private final DryWasteProducerDefinition myDryWasteProducerDefinition;

    private final H2ProducerDefinition myH2ProducerDefinition;

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