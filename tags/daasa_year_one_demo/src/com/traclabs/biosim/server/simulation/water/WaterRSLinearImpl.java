package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterRSOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;

//import java.lang.*;

/**
 * The Water Recovery System takes grey/dirty water and refines it to potable
 * water for the crew members and grey water for the crops.. Class modeled after
 * the paper:. "Intelligent Control of a Water Recovery System: Three Years in
 * the Trenches" by Bonasso, Kortenkamp, and Thronesbery
 * 
 * @author Scott Bell
 */

public class WaterRSLinearImpl extends SimBioModuleImpl implements
        WaterRSOperations, PowerConsumerOperations,
        DirtyWaterConsumerOperations, GreyWaterConsumerOperations,
        PotableWaterProducerOperations {
    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private GreyWaterConsumerDefinitionImpl myGreyWaterConsumerDefinitionImpl;

    private DirtyWaterConsumerDefinitionImpl myDirtyWaterConsumerDefinitionImpl;

    private PotableWaterProducerDefinitionImpl myPotableWaterProducerDefinitionImpl;

    private float currentPowerConsumed = 0f;

    private float currentWaterConsumed = 0f;

    /**
     * Creates the Water RS and it's subsystems
     */
    public WaterRSLinearImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myGreyWaterConsumerDefinitionImpl = new GreyWaterConsumerDefinitionImpl(this);
        myDirtyWaterConsumerDefinitionImpl = new DirtyWaterConsumerDefinitionImpl(this);
        myPotableWaterProducerDefinitionImpl = new PotableWaterProducerDefinitionImpl(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return myGreyWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public DirtyWaterConsumerDefinition getDirtyWaterConsumerDefinition() {
        return myDirtyWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
        return myPotableWaterProducerDefinitionImpl.getCorbaObject();
    }

    /**
     * Resets production/consumption levels and resets all the subsystems
     */
    public void reset() {
        super.reset();
        myPowerConsumerDefinitionImpl.reset();
        myGreyWaterConsumerDefinitionImpl.reset();
        myDirtyWaterConsumerDefinitionImpl.reset();
        myPotableWaterProducerDefinitionImpl.reset();
    }

    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinitionImpl
                .getMostResourceFromStores();
    }

    private void gatherWater() {
        //1540 Watts -> 4.26 liters of water
        float waterNeeded = (currentPowerConsumed / 1540f) * 4.26f * getTickLength();
        float currentDirtyWaterConsumed = myDirtyWaterConsumerDefinitionImpl
                .getResourceFromStores(waterNeeded);
        float currentGreyWaterConsumed = myGreyWaterConsumerDefinitionImpl
                .getResourceFromStores(waterNeeded - currentDirtyWaterConsumed);
        currentWaterConsumed = currentDirtyWaterConsumed
                + currentGreyWaterConsumed;
    }

    /**
     * Flushes the water from this subsystem (via the WaterRS) to the Potable
     * Water Store
     */
    private void pushWater() {
        myPotableWaterProducerDefinitionImpl
                .pushResourceToStores(currentWaterConsumed);
    }

    /**
     * When ticked, the Water RS: 1) gets as much water as it can in relation to
     * power
     */
    public void tick() {
        super.tick();
        gatherPower();
        gatherWater();
        pushWater();
    }
    
    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        return "NoName";
    }
    
    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
    }
    

	
	@Override
    protected void performMalfunctions() {
		for (Malfunction malfunction : myMalfunctions.values()) {
			malfunction.setPerformed(true);
		}
		if (myMalfunctions.values().size() > 0) {
			myPowerConsumerDefinitionImpl.malfunction();
	        myGreyWaterConsumerDefinitionImpl.malfunction();
	        myDirtyWaterConsumerDefinitionImpl.malfunction();
	        myPotableWaterProducerDefinitionImpl.malfunction();
		}
    }

}