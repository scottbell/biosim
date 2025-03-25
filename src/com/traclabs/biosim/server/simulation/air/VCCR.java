package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.server.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.server.simulation.air.VCCROperations;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.air.vccr.CO2Bed;
import com.traclabs.biosim.server.simulation.air.vccr.DesiccantBed;
import com.traclabs.biosim.server.simulation.air.vccr.HeatExchanger;
import com.traclabs.biosim.server.simulation.air.vccr.Pump;
import com.traclabs.biosim.server.simulation.air.vccr.VCCRSubsystem;
import com.traclabs.biosim.server.simulation.air.vccr.Valve;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;

/**
 * Produces air with less CO2. The pumps create pressure changes in each
 * subsystem. The first subsystem without fluid goes looking in its attachments
 * for fluid. Each attachment looks for fluid in turn (being blocked by valves)
 * until some is found and sent back through the system. This continues until
 * pressure is equalized.
 * 
 * @author Scott Bell
 */

public class VCCR extends SimBioModule implements VCCROperations,
		PowerConsumerOperations, AirConsumerOperations, AirProducerOperations,
		CO2ProducerOperations {

	// Consumers, Producers
	private PowerConsumerDefinition myPowerConsumerDefinition;

	private AirConsumerDefinition myAirConsumerDefinition;

	private AirProducerDefinition myAirProducerDefinition;

	private CO2ProducerDefinition myCO2ProducerDefinition;

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

	private Valve myValve9 = new Valve();

	private Valve myValve10 = new Valve();

	private VCCRSubsystem[] mySubsystems = { myDesciccantBed1,
			myDesciccantBed2, myCO2Bed3, myCO2Bed4, myPump1, myPump2,
			myHeatExchanger, myValve1, myValve2, myValve3, myValve4, myValve5,
			myValve6, myValve7, myValve8, myValve9, myValve10 };

	public VCCR(int pID, String pName) {
		super(pID, pName);
		// initialize consumers/producers
		myPowerConsumerDefinition = new PowerConsumerDefinition(this);
		myAirConsumerDefinition = new AirConsumerDefinition(this);
		myAirProducerDefinition = new AirProducerDefinition(this);
		myCO2ProducerDefinition = new CO2ProducerDefinition(this);

		// Hook up various pieces of VCCR
		myValve1.attach(myAirProducerDefinition);
		myValve1.attach(myAirConsumerDefinition);
		myValve2.attach(myAirProducerDefinition);
		myValve2.attach(myAirConsumerDefinition);
		attach(myValve1, myDesciccantBed1);
		attach(myValve2, myDesciccantBed2);
		attach(myValve3, myDesciccantBed1);
		attach(myValve3, myDesciccantBed2);
		attach(myValve3, myPump1);
		attach(myPump1, myHeatExchanger);
		attach(myHeatExchanger, myValve6);
		attach(myHeatExchanger, myValve7);
		attach(myValve4, myDesciccantBed1);
		attach(myValve5, myDesciccantBed2);
		attach(myValve6, myDesciccantBed1);
		attach(myValve7, myDesciccantBed2);
		attach(myValve8, myDesciccantBed1);
		attach(myValve8, myDesciccantBed2);
		attach(myValve8, myValve9);
		attach(myValve9, myValve10);
		attach(myValve10, myPump2);
		myPump2.attach(myCO2ProducerDefinition);

		myValve3.attach(myPump1);

	}

	private static void attach(VCCRSubsystem componenet1,
			VCCRSubsystem componenet2) {
		componenet1.attach(componenet2);
		componenet2.attach(componenet1);
	}

	public PowerConsumerDefinition getPowerConsumerDefinition() {
		return myPowerConsumerDefinition.getCorbaObject();
	}

	public AirConsumerDefinition getAirConsumerDefinition() {
		return myAirConsumerDefinition.getCorbaObject();
	}

	public AirProducerDefinition getAirProducerDefinition() {
		return myAirProducerDefinition.getCorbaObject();
	}

	public CO2ProducerDefinition getCO2ProducerDefinition() {
		return myCO2ProducerDefinition.getCorbaObject();
	}
	
	@Override
    protected void performMalfunctions() {
		for (Malfunction malfunction : myMalfunctions.values()) {
			malfunction.setPerformed(true);
		}
		if (myMalfunctions.values().size() > 0) {
			myPowerConsumerDefinition.malfunction();
			myAirConsumerDefinition.malfunction();
			myAirProducerDefinition.malfunction();
			myCO2ProducerDefinition.malfunction();
		}
    }

	/**
	 * Processes a tick by collecting referernces (if needed), resources, and
	 * pushing the new air out.
	 */
	public void tick() {
		super.tick();
		for (VCCRSubsystem subsystem : mySubsystems)
			subsystem.tick();
	}

	/**
	 * Resets production/consumption levels.
	 */
	public void reset() {
		super.reset();
		currentPowerConsumed = 0;
		currentCO2Produced = 0f;
		for (VCCRSubsystem subsystem : mySubsystems)
			subsystem.reset();
		myPowerConsumerDefinition.reset();
		myAirConsumerDefinition.reset();
		myAirProducerDefinition.reset();
		myCO2ProducerDefinition.reset();
	}
}