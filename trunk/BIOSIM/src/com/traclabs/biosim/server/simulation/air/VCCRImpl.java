package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.VCCROperations;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.air.vccr.Pump;
import com.traclabs.biosim.server.simulation.air.vccr.CO2Bed;
import com.traclabs.biosim.server.simulation.air.vccr.DesiccantBed;
import com.traclabs.biosim.server.simulation.air.vccr.HeatExchanger;
import com.traclabs.biosim.server.simulation.air.vccr.Valve;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;

/**
 * Produces air with less CO2.
 * The pumps create pressure changes in each subsystem.  The first subsystem without fluid
 * goes looking in its attachments for fluid.  Each attachment looks for fluid in turn (being
 * blocked by valves) until some is found and sent back through the system.   This continues until
 * pressure is equalized.
 * 
 * @author Scott Bell
 */

public class VCCRImpl extends SimBioModuleImpl implements VCCROperations,
        PowerConsumerOperations, AirConsumerOperations, AirProducerOperations,
        CO2ProducerOperations {

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

    private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;

    private float currentCO2Produced = 0f;

    private float currentPowerConsumed = 0;
    
    private float gatheredCO2 = 0;
    
    private DesiccantBed myDesciccantBed1 = new DesiccantBed();
    private DesiccantBed myDesciccantBed2 = new DesiccantBed();
    private CO2Bed myCO2Bed3 = new CO2Bed();
    private CO2Bed myCO2Bed4 = new CO2Bed();
    private Pump myPump1 = new Pump();
    private Pump myPump2 = new Pump();
    private HeatExchanger myHeatExchanger = new HeatExchanger();
    private Valve myValve1 = new Valve();
    private Valve myValve2 = new Valve();
    private Valve myValve3 = new Valve();
    private Valve myValve4 = new Valve();
    private Valve myValve5 = new Valve();
    private Valve myValve6 = new Valve();
    private Valve myValve7 = new Valve();
    private Valve myValve8 = new Valve();

    public VCCRImpl(int pID, String pName) {
        super(pID, pName);
        //initialize consumers/producers
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl();
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl();
        myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl();
        
        //Hook up various pieces of VCCR
        myValve1.attach(myDesciccantBed1);
        myValve1.attach(myDesciccantBed2);
        
        myDesciccantBed1.attach(myAirConsumerDefinitionImpl);
        myDesciccantBed1.attach(myAirProducerDefinitionImpl);
        myDesciccantBed1.attach(myValve1);
        myDesciccantBed1.attach(myValve3);
        
        myDesciccantBed2.attach(myAirConsumerDefinitionImpl);
        myDesciccantBed2.attach(myAirProducerDefinitionImpl);
        myDesciccantBed2.attach(myValve1);
        myDesciccantBed1.attach(myValve3);
        
        myValve3.attach(myPump1);
        
        
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinitionImpl.getCorbaObject();
    }

    public CO2ProducerDefinition getCO2ProducerDefinition() {
        return myCO2ProducerDefinitionImpl.getCorbaObject();
    }

    /**
     * Processes a tick by collecting referernces (if needed), resources, and
     * pushing the new air out.
     */
    public void tick() {
        super.tick();
        gatherPower();
        gatherCO2();
        pushCO2();
        pushAir();
    }

    private void pushAir() {
		// TODO Auto-generated method stub
		
	}

	/**
     * Adds power for this tick
     */
    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinitionImpl
                .getMostResourceFromStores();
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        currentPowerConsumed = 0;
        currentCO2Produced = 0f;
    }

    private void gatherCO2() {
        //25.625 watts -> 1.2125 moles of Air
        float molesAirNeeded = (currentPowerConsumed / 25.625f) * 1.2125f  * getTickLength();
        SimEnvironment theEnvironment = myAirConsumerDefinitionImpl.getEnvironments()[0];
        float theCO2Percentage = theEnvironment.getCO2Store().getCurrentLevel() / theEnvironment.getTotalMoles();
        //gather CO2
        gatheredCO2 = theEnvironment.getCO2Store().take(molesAirNeeded * theCO2Percentage);
        myLogger.debug("gatheredCO2 = "+gatheredCO2);
        myLogger.debug("currentPowerConsumed = "+currentPowerConsumed);
    }

    private void pushCO2() {
        //distrbute CO2
        currentCO2Produced = gatheredCO2;
        myCO2ProducerDefinitionImpl
                .pushResourceToStores(currentCO2Produced);
        myLogger.debug("currentCO2Produced = " + currentCO2Produced);
    }
}