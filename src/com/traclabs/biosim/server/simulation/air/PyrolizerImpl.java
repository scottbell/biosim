package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.PyrolizerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinitionImpl;


public class PyrolizerImpl extends SimBioModuleImpl implements PyrolizerOperations,
        PowerConsumerOperations, MethaneConsumerOperations,
        DryWasteProducerOperations, H2ProducerOperations {

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private MethaneConsumerDefinitionImpl myMethaneConsumerDefinitionImpl;

    private DryWasteProducerDefinitionImpl myDryWasteProducerDefinitionImpl;

    private H2ProducerDefinitionImpl myH2ProducerDefinitionImpl;

    public PyrolizerImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myMethaneConsumerDefinitionImpl = new MethaneConsumerDefinitionImpl(this);
        myDryWasteProducerDefinitionImpl = new DryWasteProducerDefinitionImpl(this);
        myH2ProducerDefinitionImpl = new H2ProducerDefinitionImpl(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public MethaneConsumerDefinition getMethaneConsumerDefinition() {
        return myMethaneConsumerDefinitionImpl.getCorbaObject();
    }

    public DryWasteProducerDefinition getDryWasteProducerDefinition() {
        return myDryWasteProducerDefinitionImpl.getCorbaObject();
    }

    public H2ProducerDefinition getH2ProducerDefinition() {
        return myH2ProducerDefinitionImpl.getCorbaObject();
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        myPowerConsumerDefinitionImpl.reset();
        myMethaneConsumerDefinitionImpl.reset();
        myDryWasteProducerDefinitionImpl.reset();
        myH2ProducerDefinitionImpl.reset();
    }

    public void tick() {
        super.tick();
    }
}