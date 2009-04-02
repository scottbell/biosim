package com.traclabs.biosim.server.simulation.framework;

import junit.framework.TestCase;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentPOATie;
import com.traclabs.biosim.idl.simulation.framework.Accumulator;
import com.traclabs.biosim.idl.simulation.framework.AccumulatorPOATie;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.util.OrbUtils;

public class AccumulatorImplTest extends TestCase {
	private Accumulator myAccumulator;
	private SimEnvironment myInputSimEnvironment;
	private SimEnvironment myOutputSimEnvironment;
	
	private static final float MOLES_TO_EXCHANGE = 10;
	
	private BioModule[] myModules;
	
	protected void setUp() throws Exception {
		super.setUp();
		OrbUtils.initializeLog(false);
		OrbUtils.startStandaloneNameServer();
		OrbUtils.initializeServerForStandalone();
		
		myAccumulator = (new AccumulatorPOATie(new AccumulatorImpl()))._this(OrbUtils.getORB());
		myInputSimEnvironment = (new SimEnvironmentPOATie(new SimEnvironmentImpl()))._this(OrbUtils.getORB());
		myOutputSimEnvironment = (new SimEnvironmentPOATie(new SimEnvironmentImpl()))._this(OrbUtils.getORB());
		myAccumulator.getAirConsumerDefinition().setAirInputs(new SimEnvironment[] {myInputSimEnvironment}, new float[] {MOLES_TO_EXCHANGE}, new float[] {MOLES_TO_EXCHANGE});
		myAccumulator.getAirProducerDefinition().setAirOutputs(new SimEnvironment[] {myOutputSimEnvironment}, new float[] {MOLES_TO_EXCHANGE}, new float[] {MOLES_TO_EXCHANGE});
		
		myModules = new BioModule[3];
		myModules[0] = myAccumulator;
		myModules[1] = myInputSimEnvironment;
		myModules[2] = myOutputSimEnvironment;
		
		for (BioModule currentModule : myModules)
			currentModule.setTickLength(1);
	}
	
	public void testAirConsumption(){
		float totalInputMolesBefore = myInputSimEnvironment.getTotalMoles();
		float totalOutputMolesBefore = myOutputSimEnvironment.getTotalMoles();
		tick();
		float totalInputMolesAfter = myInputSimEnvironment.getTotalMoles();
		float totalOutputMolesAfter = myOutputSimEnvironment.getTotalMoles();
		float inputDifference = totalInputMolesBefore - totalInputMolesAfter;
		float outputDifference = totalInputMolesBefore - totalInputMolesAfter;
		assertEquals(inputDifference, MOLES_TO_EXCHANGE, 1);
		assertEquals(outputDifference, MOLES_TO_EXCHANGE, 1);
		
		totalInputMolesBefore = myInputSimEnvironment.getTotalMoles();
		totalOutputMolesBefore = myOutputSimEnvironment.getTotalMoles();
		myAccumulator.getAirConsumerDefinition().setDesiredFlowRate(0, 0);
		tick();
		totalInputMolesAfter = myInputSimEnvironment.getTotalMoles();
		totalOutputMolesAfter = myOutputSimEnvironment.getTotalMoles();
		assertEquals(totalInputMolesBefore, totalInputMolesAfter);
		assertEquals(totalOutputMolesAfter, totalOutputMolesAfter);

		totalInputMolesBefore = myInputSimEnvironment.getTotalMoles();
		totalOutputMolesBefore = myOutputSimEnvironment.getTotalMoles();
		myAccumulator.getAirConsumerDefinition().setDesiredFlowRate(MOLES_TO_EXCHANGE, 0);
		tick();
		totalInputMolesAfter = myInputSimEnvironment.getTotalMoles();totalOutputMolesAfter = myOutputSimEnvironment.getTotalMoles();
		inputDifference = totalInputMolesBefore - totalInputMolesAfter;
		outputDifference = totalInputMolesBefore - totalInputMolesAfter;
		assertEquals(inputDifference, MOLES_TO_EXCHANGE, 1);
		assertEquals(outputDifference, MOLES_TO_EXCHANGE, 1);
		
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
