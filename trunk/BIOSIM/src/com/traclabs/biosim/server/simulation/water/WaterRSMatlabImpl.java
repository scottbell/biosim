package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.framework.TechSpecificInfoHelper;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterRSOperations;
import com.traclabs.biosim.server.simulation.framework.DirtyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.GreyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PotableWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.util.Engine;
import com.traclabs.biosim.server.util.MatlabAceEngine;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * This class acts as a surrogate for the underlying Matlab WaterRS model and
 * the rest of BioSim. It communicates with BioSim as the rest of the modules do
 * (flowrates, malfunctions, etc.). The Matlab communication is handled by a
 * MatlabAceEngine. This engine takes doubles (the parameters needed for one
 * timestep) and sends doubles back (the results of the timestep). The results
 * are interpreted by this class and the simulation is modified (by changing
 * tank levels for example).
 * 
 * @author Scott Bell
 */

public class WaterRSMatlabImpl extends SimBioModuleImpl implements
        WaterRSOperations, PowerConsumerOperations,
        DirtyWaterConsumerOperations, GreyWaterConsumerOperations,
        PotableWaterProducerOperations {
    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private GreyWaterConsumerDefinitionImpl myGreyWaterConsumerDefinitionImpl;

    private DirtyWaterConsumerDefinitionImpl myDirtyWaterConsumerDefinitionImpl;

    private PotableWaterProducerDefinitionImpl myPotableWaterProducerDefinitionImpl;

    //MatLab specific
    private Engine myEngine;

    private WaterRSMatlabTechInfoImpl myTechSpecificInfoImpl;

    /**
     * Creates the Water RS and it's subsystems
     */
    public WaterRSMatlabImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myGreyWaterConsumerDefinitionImpl = new GreyWaterConsumerDefinitionImpl();
        myDirtyWaterConsumerDefinitionImpl = new DirtyWaterConsumerDefinitionImpl();
        myPotableWaterProducerDefinitionImpl = new PotableWaterProducerDefinitionImpl();
        //use this object to play with
        myTechSpecificInfoImpl = new WaterRSMatlabTechInfoImpl();
        //don't touch this one.
        myTechSpecificInfo = TechSpecificInfoHelper.narrow(OrbUtils
                .poaToCorbaObj(myTechSpecificInfoImpl));

        myTechSpecificInfoImpl.changeString("matlab changed string");
        myEngine = new MatlabAceEngine();
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
        myEngine.reset();
    }

    /**
     * When ticked, the Water RS: 1) ticks each subsystem.
     */
    public void tick() {
        super.tick();
        myLogger.debug("start tick");
        myLogger.debug("attempting to send some doubles");
        double[] testPutDoubleArray = { 7.5d, 3.4d, Math.PI };
        myEngine.put(testPutDoubleArray);
        myLogger.debug("attempting to receive some doubles");
        double[] testGetDoubleArray = myEngine.get();
        if (testGetDoubleArray == null) {
            myLogger.error("double array returned was null");
        } else {
            for (int i = 0; i < testGetDoubleArray.length; i++)
                myLogger.debug("got back [" + i + "]=" + testGetDoubleArray[i]);
        }
        myLogger.debug("end tick");
    }

    protected void performMalfunctions() {
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        return "NoName";
    }

    /**
     * For fast reference to the log tree
     */
    private class LogIndex {
    }
}