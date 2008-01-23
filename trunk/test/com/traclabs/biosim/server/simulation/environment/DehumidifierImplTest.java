package com.traclabs.biosim.server.simulation.environment;

import junit.framework.TestCase;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.Dehumidifier;
import com.traclabs.biosim.idl.simulation.environment.DehumidifierPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentPOATie;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStorePOATie;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStoreImpl;
import com.traclabs.biosim.util.OrbUtils;

public class DehumidifierImplTest extends TestCase {
	private Dehumidifier myDehumidifier;
	
	//consumers
	private SimEnvironment mySimEnvironment;
	
	//producers
	private DirtyWaterStore myDirtyWaterStore;
	
	private BioModule[] myModules;
	
	protected void setUp() throws Exception {
		super.setUp();
		OrbUtils.initializeLog(false);
		OrbUtils.startStandaloneNameServer();
		OrbUtils.initializeServerForStandalone();
		DehumidifierImpl dehumidifierImpl = new DehumidifierImpl();
		myDehumidifier = (new DehumidifierPOATie(dehumidifierImpl))._this(OrbUtils.getORB());
		//initialize stores
		mySimEnvironment = (new SimEnvironmentPOATie(new SimEnvironmentImpl()))._this(OrbUtils.getORB());
		myDirtyWaterStore = (new DirtyWaterStorePOATie(new DirtyWaterStoreImpl()))._this(OrbUtils.getORB());
		
		mySimEnvironment.setInitialVolumeAtSeaLevel(10000);
		myDirtyWaterStore.setInitialCapacity(4000);
		myDirtyWaterStore.setInitialLevel(0);
		
		myDehumidifier.getAirConsumerDefinition().setAirInputs(new SimEnvironment[] {mySimEnvironment}, new float[] {5f}, new float[] {5f});
		myDehumidifier.getDirtyWaterProducerDefinition().setDirtyWaterOutputs(new DirtyWaterStore[] {myDirtyWaterStore}, new float[] {1000f}, new float[] {1000f});
		
		
		myModules = new BioModule[3];
		myModules[0] = myDehumidifier;
		myModules[1] = mySimEnvironment;
		myModules[2] = myDirtyWaterStore;
		
		for (BioModule currentModule : myModules)
			currentModule.setTickLength(1);
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
	
	private void tick(){
		for (BioModule currentModule : myModules)
			currentModule.tick();
	}

	public void testTick() {
		float environmentVaporMolesBefore = 0;
		float dirtyWaterLevelBefore = 0;
		float environmentVaporMolesAfter = 0;
		float dirtyWaterLevelAfter = 0;
		float vaporConcentration = 0;
		//first make sure if vapor is below optimal, we don't extract anything
		reset();
		mySimEnvironment.getVaporStore().setCurrentLevel(DehumidifierImpl.OPTIMAL_MOISTURE_CONCENTRATION * mySimEnvironment.getTotalMoles() / 2f);
		mySimEnvironment.getVaporStore().tick();
		environmentVaporMolesBefore = mySimEnvironment.getVaporStore().getCurrentLevel();
		dirtyWaterLevelBefore = myDirtyWaterStore.getCurrentLevel();
		tick();
		environmentVaporMolesAfter = mySimEnvironment.getVaporStore().getCurrentLevel();
		dirtyWaterLevelAfter = myDirtyWaterStore.getCurrentLevel();
		tick();
		assertEquals(environmentVaporMolesBefore, environmentVaporMolesAfter);
		assertEquals(dirtyWaterLevelBefore, dirtyWaterLevelAfter);
		//now make sure if vapor is above optimal, we extract water
		reset();
		mySimEnvironment.getVaporStore().setCurrentLevel(DehumidifierImpl.OPTIMAL_MOISTURE_CONCENTRATION * mySimEnvironment.getTotalMoles() * 4f);
		mySimEnvironment.getVaporStore().tick();
		vaporConcentration = mySimEnvironment.getVaporStore().getCurrentLevel() / mySimEnvironment.getTotalMoles();
		assertTrue(vaporConcentration != DehumidifierImpl.OPTIMAL_MOISTURE_CONCENTRATION);
		environmentVaporMolesBefore = mySimEnvironment.getVaporStore().getCurrentLevel();
		dirtyWaterLevelBefore = myDirtyWaterStore.getCurrentLevel();
		tick();
		environmentVaporMolesAfter = mySimEnvironment.getVaporStore().getCurrentLevel();
		dirtyWaterLevelAfter = myDirtyWaterStore.getCurrentLevel();
		tick();
		assertTrue(environmentVaporMolesBefore > environmentVaporMolesAfter);
		assertTrue(dirtyWaterLevelBefore < dirtyWaterLevelAfter);
		for (int i = 0; i < 10000; i++)
			tick();
		vaporConcentration = mySimEnvironment.getVaporStore().getCurrentLevel() / mySimEnvironment.getTotalMoles();
		assertEquals(vaporConcentration, DehumidifierImpl.OPTIMAL_MOISTURE_CONCENTRATION);
	}
	

}
