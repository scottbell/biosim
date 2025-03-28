package com.traclabs.biosim.server.simulation.air.cdrs;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.air.CO2Producer;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.server.simulation.environment.*;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumer;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducer;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinition;

public class CDRSModule extends SimBioModule implements PowerConsumer, GreyWaterConsumer, GreyWaterProducer, AirConsumer, AirProducer, CO2Producer {
    private static final int AIR_INLET_VALVE_POWER_INDEX = 0;
    private static final int AIR_RETURN_VALVE_POWER_INDEX = 1;
    private static final int CO2_ISOLATION_VALVE_POWER_INDEX = 2;
    private static final int CO2_VENT_VALVE_POWER_INDEX = 3;
    private static final int CDRS_POWER_INDEX = 4;
    private static final int PRIMARY_HEATER_POWER_INDEX = 5;
    private static final int SECONDARY_HEATER_POWER_INDEX = 6;
    private static final int WATER_PUMP_POWER_INDEX = 7;
    private static final int BLOWER_POWER_INDEX = 8;
    private static final float MAX_HEATER_PRODUCTION = 200;
    private final static int TICKS_TO_WAIT = 20;
    //Consumers, Producers
    private final PowerConsumerDefinition myPowerConsumerDefinition;
    private final GreyWaterConsumerDefinition myGreyWaterConsumerDefinition;
    private final GreyWaterProducerDefinition myGreyWaterProducerDefinition;
    private final AirConsumerDefinition myAirConsumerDefinition;
    private final AirProducerDefinition myAirProducerDefinition;
    private final CO2ProducerDefinition myCO2ProducerDefinition;
    private CDRSState myState = CDRSState.inactive;
    private CDRSArmedStatus myArmedStatus = CDRSArmedStatus.not_armed;
    private CDRSValveState myAirInletValveState = CDRSValveState.open;
    private CDRSCommandStatus myAirInletValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myAirReturnValveState = CDRSValveState.open;
    private CDRSCommandStatus myAirReturnValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myCO2IsolationValveState = CDRSValveState.open;
    private CDRSCommandStatus myCO2IsolationValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSValveState myCO2VentValveState = CDRSValveState.open;
    private CDRSCommandStatus myCO2VentValveEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSPowerState myWaterPumpState = CDRSPowerState.off;
    private CDRSCommandStatus myWaterPumpEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSPowerState myBlowerState = CDRSPowerState.off;
    private CDRSCommandStatus myBlowerEnabledStatus = CDRSCommandStatus.enabled;
    private CDRSDayNightState myDayNightState = CDRSDayNightState.day;
    private float myPrimaryHeaterProduction = 0;
    private float mySecondaryHeaterProduction = 0;
    private CDRSState myStateToTransition = CDRSState.transitioning;
    private int myTicksWaited = 0;

    public CDRSModule(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myGreyWaterConsumerDefinition = new GreyWaterConsumerDefinition(this);
        myGreyWaterProducerDefinition = new GreyWaterProducerDefinition(this);
        myAirConsumerDefinition = new AirConsumerDefinition(this);
        myAirProducerDefinition = new AirProducerDefinition(this);
        myCO2ProducerDefinition = new CO2ProducerDefinition(this);
    }

    public void tick() {
        super.tick();
        boolean gatheredProperPower = gatherPower();
        if (!gatheredProperPower) {
            transitionToInactive();
            return;
        }
        gatherAir();
        gatherWater();
        if (myStateToTransition != CDRSState.transitioning) {
            myTicksWaited++;
            if (myTicksWaited >= TICKS_TO_WAIT)
                transitionState();
        }
    }

    private void gatherWater() {
        float waterGathered = myGreyWaterConsumerDefinition.getMostResourceFromStores();
        if ((getState() == CDRSState.init) || (getState() == CDRSState.inactive) || (getState() == CDRSState.transitioning)) {
            myGreyWaterProducerDefinition.pushResourceToStores(waterGathered, 22f);
        } else {
            myGreyWaterProducerDefinition.pushResourceToStores(waterGathered, 30f);
        }
    }

    private void gatherAir() {
        Air airConsumed = myAirConsumerDefinition.getMostAirFromEnvironments();
        float co2Produced = 0;
        if (myState == CDRSState.dual_bed) {
            co2Produced = airConsumed.co2Moles;
            airConsumed.co2Moles = 0;
        } else if (myState == CDRSState.single_bed) {
            co2Produced = airConsumed.co2Moles / 2;
            airConsumed.co2Moles = co2Produced;
        }

        myAirProducerDefinition.pushAirToEnvironment(airConsumed, 0);
        myCO2ProducerDefinition.pushResourceToStores(co2Produced);
    }

    private void transitionState() {
        if (myStateToTransition == CDRSState.init)
            transitionToInit();
        else if (myStateToTransition == CDRSState.standby)
            transitionToStandby();
        else if (myStateToTransition == CDRSState.dual_bed)
            transitionToDualBed();
        else if (myStateToTransition == CDRSState.single_bed)
            transitionToSingleBed();
        else if (myStateToTransition == CDRSState.inactive)
            transitionToInactive();
        myTicksWaited = 0;
        myStateToTransition = CDRSState.transitioning;
    }

    private boolean gatherPower() {
        float cdrsPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStore(CDRS_POWER_INDEX);
        float airInletPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStore(AIR_INLET_VALVE_POWER_INDEX);
        float airReturnPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStore(AIR_RETURN_VALVE_POWER_INDEX);
        float co2IsolationPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStore(CO2_ISOLATION_VALVE_POWER_INDEX);
        float co2VentPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStore(CO2_VENT_VALVE_POWER_INDEX);
        float primaryHeaterPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStore(PRIMARY_HEATER_POWER_INDEX);
        float secondaryHeaterPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStore(SECONDARY_HEATER_POWER_INDEX);
        float waterPumpPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStore(WATER_PUMP_POWER_INDEX);
        float blowerPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStore(BLOWER_POWER_INDEX);
        return !(cdrsPowerConsumed <= 0) && !(airInletPowerConsumed <= 0) && !(airReturnPowerConsumed <= 0) &&
                !(co2IsolationPowerConsumed <= 0) && !(co2VentPowerConsumed <= 0) &&
                !(primaryHeaterPowerConsumed <= 0) && !(secondaryHeaterPowerConsumed <= 0) &&
                !(waterPumpPowerConsumed <= 0) && !(blowerPowerConsumed <= 0);
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        myPowerConsumerDefinition.reset();
        myGreyWaterConsumerDefinition.reset();
        myGreyWaterProducerDefinition.reset();
        myAirConsumerDefinition.reset();
        myAirProducerDefinition.reset();
        myCO2ProducerDefinition.reset();

        myState = CDRSState.inactive;
        myArmedStatus = CDRSArmedStatus.not_armed;
        myAirInletValveState = CDRSValveState.open;
        myAirInletValveEnabledStatus = CDRSCommandStatus.inhibited;
        myAirReturnValveState = CDRSValveState.open;
        myAirReturnValveEnabledStatus = CDRSCommandStatus.inhibited;
        myCO2IsolationValveState = CDRSValveState.open;
        myCO2IsolationValveEnabledStatus = CDRSCommandStatus.inhibited;
        myCO2VentValveState = CDRSValveState.open;
        myCO2VentValveEnabledStatus = CDRSCommandStatus.inhibited;
        myWaterPumpState = CDRSPowerState.off;
        myWaterPumpEnabledStatus = CDRSCommandStatus.inhibited;
        myBlowerState = CDRSPowerState.off;
        myBlowerEnabledStatus = CDRSCommandStatus.inhibited;
        myDayNightState = CDRSDayNightState.day;

        myPrimaryHeaterProduction = 0;
        mySecondaryHeaterProduction = 0;
        transitionToInactive();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition;
    }

    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return myGreyWaterConsumerDefinition;
    }

    public GreyWaterProducerDefinition getGreyWaterProducerDefinition() {
        return myGreyWaterProducerDefinition;
    }

    public CO2ProducerDefinition getCO2ProducerDefinition() {
        return myCO2ProducerDefinition;
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinition;
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinition;
    }

    private boolean transitionAllowed(CDRSState stateToTransition) {
        if (myState == CDRSState.inactive) {
            return (stateToTransition == CDRSState.init);
        } else if (myState == CDRSState.init) {
            return ((stateToTransition == CDRSState.inactive) || (stateToTransition == CDRSState.standby));
        } else if (myState == CDRSState.standby) {
            return ((stateToTransition == CDRSState.inactive) || (stateToTransition == CDRSState.dual_bed) || (stateToTransition == CDRSState.single_bed));
        } else if (myState == CDRSState.single_bed) {
            return (stateToTransition == CDRSState.inactive);
        } else if (myState == CDRSState.dual_bed) {
            return (stateToTransition == CDRSState.inactive);
        }
        return false;
    }

    public CDRSState getState() {
        return myState;
    }

    public void setState(CDRSState state) {
        if (getArmedStatus() == CDRSArmedStatus.armed) {
            if (transitionAllowed(state)) {
                myState = CDRSState.transitioning;
                myStateToTransition = state;
                myArmedStatus = CDRSArmedStatus.not_armed;
            }
        }
    }

    public CDRSArmedStatus getArmedStatus() {
        return myArmedStatus;
    }

    public void setArmedStatus(CDRSArmedStatus status) {
        this.myArmedStatus = status;
    }

    public CDRSValveState getAirInletValveState() {
        return myAirInletValveState;
    }

    public void setAirInletValveState(CDRSValveState state) {
        if (getAirInletValveArmedStatus() == CDRSCommandStatus.enabled)
            this.myAirInletValveState = state;
    }

    public CDRSCommandStatus getAirInletValveArmedStatus() {
        return myAirInletValveEnabledStatus;
    }

    public void setAirInletValveArmedStatus(CDRSCommandStatus status) {
        this.myAirInletValveEnabledStatus = status;
    }

    public CDRSValveState getAirReturnValveState() {
        return myAirReturnValveState;
    }

    public void setAirReturnValveState(CDRSValveState state) {
        if (getAirReturnValveArmedStatus() == CDRSCommandStatus.enabled)
            this.myAirReturnValveState = state;
    }

    public CDRSCommandStatus getAirReturnValveArmedStatus() {
        return myAirReturnValveEnabledStatus;
    }

    public void setAirReturnValveArmedStatus(CDRSCommandStatus status) {
        this.myAirReturnValveEnabledStatus = status;
    }

    public CDRSValveState getCO2IsolationValveState() {
        return myCO2IsolationValveState;
    }

    public void setCO2IsolationValveState(CDRSValveState state) {
        if (getCO2IsolationValveArmedStatus() == CDRSCommandStatus.enabled)
            this.myCO2IsolationValveState = state;
    }

    public CDRSCommandStatus getCO2IsolationValveArmedStatus() {
        return myCO2IsolationValveEnabledStatus;
    }

    public void setCO2IsolationValveArmedStatus(CDRSCommandStatus status) {
        this.myCO2IsolationValveEnabledStatus = status;
    }

    public CDRSValveState getCO2VentValveState() {
        return myCO2VentValveState;
    }

    public void setCO2VentValveState(CDRSValveState state) {
        if (getCO2VentValveArmedStatus() == CDRSCommandStatus.enabled)
            this.myCO2VentValveState = state;
    }

    public CDRSCommandStatus getCO2VentValveArmedStatus() {
        return myCO2VentValveEnabledStatus;
    }

    public void setCO2VentValveArmedStatus(CDRSCommandStatus status) {
        this.myCO2VentValveEnabledStatus = status;
    }

    public CDRSPowerState getWaterPumpState() {
        return myWaterPumpState;
    }

    public void setWaterPumpState(CDRSPowerState state) {
        if (getWaterPumpArmedStatus() == CDRSCommandStatus.enabled)
            this.myWaterPumpState = state;
    }

    public CDRSCommandStatus getWaterPumpArmedStatus() {
        return myWaterPumpEnabledStatus;
    }

    public void setWaterPumpArmedStatus(CDRSCommandStatus status) {
        this.myWaterPumpEnabledStatus = status;
    }

    public CDRSPowerState getBlowerState() {
        return myBlowerState;
    }

    public void setBlowerState(CDRSPowerState state) {
        if (getBlowerArmedStatus() == CDRSCommandStatus.enabled)
            this.myBlowerState = state;
    }

    public CDRSCommandStatus getBlowerArmedStatus() {
        return myBlowerEnabledStatus;
    }

    public void setBlowerArmedStatus(CDRSCommandStatus status) {
        this.myBlowerEnabledStatus = status;
    }

    public CDRSDayNightState getDayNightState() {
        return myDayNightState;
    }

    public void setDayNightState(CDRSDayNightState state) {
        this.myDayNightState = state;
    }

    private void transitionToInit() {
        myBlowerState = CDRSPowerState.on;
        myWaterPumpState = CDRSPowerState.on;
        myAirConsumerDefinition.setDesiredFlowRate(myAirConsumerDefinition.getMaxFlowRate(0) / 2, 0);
        myAirProducerDefinition.setDesiredFlowRate(myAirProducerDefinition.getMaxFlowRate(0) / 2, 0);
        myGreyWaterConsumerDefinition.setDesiredFlowRate(myGreyWaterConsumerDefinition.getMaxFlowRate(0), 0);
        myPrimaryHeaterProduction = 0;
        mySecondaryHeaterProduction = 0;
        myState = CDRSState.init;
    }

    private void transitionToInactive() {
        myBlowerState = CDRSPowerState.off;
        myWaterPumpState = CDRSPowerState.off;
        myAirConsumerDefinition.setDesiredFlowRate(0, 0);
        myAirProducerDefinition.setDesiredFlowRate(0, 0);
        myGreyWaterConsumerDefinition.setDesiredFlowRate(0, 0);
        myPrimaryHeaterProduction = 0;
        mySecondaryHeaterProduction = 0;
        myState = CDRSState.inactive;
    }

    private void transitionToSingleBed() {
        myBlowerState = CDRSPowerState.on;
        myWaterPumpState = CDRSPowerState.on;
        myAirConsumerDefinition.setDesiredFlowRate(myAirConsumerDefinition.getMaxFlowRate(0), 0);
        myAirProducerDefinition.setDesiredFlowRate(myAirProducerDefinition.getMaxFlowRate(0), 0);
        myGreyWaterConsumerDefinition.setDesiredFlowRate(myGreyWaterConsumerDefinition.getMaxFlowRate(0), 0);
        myPrimaryHeaterProduction = MAX_HEATER_PRODUCTION * 0.5f;
        mySecondaryHeaterProduction = 0;
        myState = CDRSState.single_bed;
    }

    private void transitionToDualBed() {
        myBlowerState = CDRSPowerState.on;
        myWaterPumpState = CDRSPowerState.on;
        myState = CDRSState.dual_bed;
        myAirConsumerDefinition.setDesiredFlowRate(myAirConsumerDefinition.getMaxFlowRate(0), 0);
        myAirProducerDefinition.setDesiredFlowRate(myAirProducerDefinition.getMaxFlowRate(0), 0);
        myGreyWaterConsumerDefinition.setDesiredFlowRate(myGreyWaterConsumerDefinition.getMaxFlowRate(0), 0);
        myPrimaryHeaterProduction = MAX_HEATER_PRODUCTION;
        mySecondaryHeaterProduction = 0;
    }

    private void transitionToStandby() {
        myBlowerState = CDRSPowerState.on;
        myWaterPumpState = CDRSPowerState.on;
        myState = CDRSState.standby;
        myAirConsumerDefinition.setDesiredFlowRate(myAirConsumerDefinition.getMaxFlowRate(0), 0);
        myAirProducerDefinition.setDesiredFlowRate(myAirProducerDefinition.getMaxFlowRate(0), 0);
        myPrimaryHeaterProduction = MAX_HEATER_PRODUCTION * 0.25f;
        myGreyWaterConsumerDefinition.setDesiredFlowRate(myGreyWaterConsumerDefinition.getMaxFlowRate(0), 0);
        mySecondaryHeaterProduction = 0;
    }

    public float getPrimaryHeatProduction() {
        return myPrimaryHeaterProduction;
    }

    public float getSecondaryHeatProduction() {
        return mySecondaryHeaterProduction;
    }

    @Override
    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
                                        MalfunctionLength pLength) {
        return "Broken";
    }

    @Override
    protected void performMalfunctions() {
        for (Malfunction malfunction : myMalfunctions.values()) {
            malfunction.setPerformed(true);
        }
        if (myMalfunctions.size() > 0) {
            myPowerConsumerDefinition.malfunction();
            myGreyWaterConsumerDefinition.malfunction();
            myGreyWaterProducerDefinition.malfunction();
            myAirProducerDefinition.malfunction();
            myAirConsumerDefinition.malfunction();
            myCO2ProducerDefinition.malfunction();
            transitionToInactive();
        }
    }

}