package com.traclabs.biosim.server.actuator.environment;

import junit.framework.TestCase;

import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentPOATie;
import com.traclabs.biosim.idl.simulation.framework.Accumulator;
import com.traclabs.biosim.idl.simulation.framework.AccumulatorPOATie;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.framework.AccumulatorImpl;
import com.traclabs.biosim.util.OrbUtils;

public class AirActuatorImplTest extends TestCase {
	private AirInFlowRateActuator myAirInFlowRateActuator;
	private AirOutFlowRateActuator myAirOutFlowRateActuator;
	private Accumulator myAccumulator;
	private SimEnvironment myInputSimEnvironment;
	private SimEnvironment myOutputSimEnvironment;
	
	private static final float MOLES_TO_EXCHANGE = 10;
	
	private BioModule[] myModules;
	
	protected void setUp() throws Exception {
		super.setUp();
		OrbUtils.initializeLog();
		OrbUtils.startStandaloneNameServer();
		OrbUtils.initializeServerForStandalone();
		
		myAirInFlowRateActuator = (new AirInFlowRateActuatorPOATie(new AirInFlowRateActuatorImpl()))._this(OrbUtils.getORB());
		myAirOutFlowRateActuator = (new AirOutFlowRateActuatorPOATie(new AirOutFlowRateActuatorImpl()))._this(OrbUtils.getORB());
		myAccumulator = (new AccumulatorPOATie(new AccumulatorImpl()))._this(OrbUtils.getORB());
		myInputSimEnvironment = (new SimEnvironmentPOATie(new SimEnvironmentImpl()))._this(OrbUtils.getORB());
		myOutputSimEnvironment = (new SimEnvironmentPOATie(new SimEnvironmentImpl()))._this(OrbUtils.getORB());
		myAccumulator.getAirConsumerDefinition().setAirInputs(new SimEnvironment[] {myInputSimEnvironment}, new float[] {Float.MAX_VALUE}, new float[] {Float.MAX_VALUE});
		myAccumulator.getAirProducerDefinition().setAirOutputs(new SimEnvironment[] {myOutputSimEnvironment}, new float[] {Float.MAX_VALUE}, new float[] {Float.MAX_VALUE});
		myAirInFlowRateActuator.setOutput(myAccumulator, 0);
		myAirOutFlowRateActuator.setOutput(myAccumulator, 0);
		
		myModules = new BioModule[5];
		myModules[0] = myAirInFlowRateActuator;
		myModules[1] = myAirOutFlowRateActuator;
		myModules[2] = myAccumulator;
		myModules[3] = myInputSimEnvironment;
		myModules[4] = myOutputSimEnvironment;
		
		for (BioModule currentModule : myModules)
			currentModule.setTickLength(1);
	}
	
	public void testAirConsumption(){
		myAirInFlowRateActuator.setValue(0);
		myAirOutFlowRateActuator.setValue(0);
		float totalInputMolesBefore = myInputSimEnvironment.getTotalMoles();
		float totalOutputMolesBefore = myOutputSimEnvironment.getTotalMoles();
		tick();
		float totalInputMolesAfter = myInputSimEnvironment.getTotalMoles();
		float totalOutputMolesAfter = myOutputSimEnvironment.getTotalMoles();
		float inputDifference = totalInputMolesBefore - totalInputMolesAfter;
		float outputDifference = totalInputMolesBefore - totalInputMolesAfter;
		assertEquals(inputDifference, 0, 1);
		assertEquals(outputDifference, 0, 1);
		
		myAirInFlowRateActuator.setValue(MOLES_TO_EXCHANGE);
		myAirOutFlowRateActuator.setValue(MOLES_TO_EXCHANGE);
		totalInputMolesBefore = myInputSimEnvironment.getTotalMoles();
		totalOutputMolesBefore = myOutputSimEnvironment.getTotalMoles();
		tick();
		totalInputMolesAfter = myInputSimEnvironment.getTotalMoles();
		totalOutputMolesAfter = myOutputSimEnvironment.getTotalMoles();
		inputDifference = totalInputMolesBefore - totalInputMolesAfter;
		outputDifference = totalInputMolesBefore - totalInputMolesAfter;
		assertEquals(MOLES_TO_EXCHANGE, inputDifference, 1);
		assertEquals(MOLES_TO_EXCHANGE, outputDifference, 1);
	}
	
	private void tick(){
		for (BioModule currentModule : myModules)
			currentModule.tick();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		for (BioModule currentModule : myModules)
			currentModule = null;
		myModules = null;
	}
	
	private void reset(){
		for (BioModule currentModule : myModules)
			currentModule.reset();
	}

}
