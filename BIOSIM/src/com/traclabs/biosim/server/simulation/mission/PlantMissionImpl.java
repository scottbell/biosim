package com.traclabs.biosim.server.simulation.mission;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.mission.PlantMissionOperations;
import com.traclabs.biosim.server.simulation.framework.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PotableWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * The basic PlantMission Implementation.
 * 
 * @author Scott Bell
 */

public class PlantMissionImpl extends MissionModuleImpl implements
        PlantMissionOperations, PowerConsumerOperations, AirConsumerOperations,
        PotableWaterConsumerOperations {
    //  Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;

    public PlantMissionImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl();
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return (PowerConsumerDefinition) (OrbUtils
                .poaToCorbaObj(myPowerConsumerDefinitionImpl));
    }

    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return (PotableWaterConsumerDefinition) (OrbUtils
                .poaToCorbaObj(myPotableWaterConsumerDefinitionImpl));
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return (AirConsumerDefinition) (OrbUtils
                .poaToCorbaObj(myAirConsumerDefinitionImpl));
    }

    public void tick() {
        super.tick();
        getAndPushResources();
    }

    private void getAndPushResources() {
        float powerGathered = myPowerConsumerDefinitionImpl
                .getMostResourceFromStore();
        float potableWaterGathered = myPotableWaterConsumerDefinitionImpl
                .getMostResourceFromStore();
        Breath gatheredAir = myAirConsumerDefinitionImpl
                .getMostAirFromEnvironment();
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
}