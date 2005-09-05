package com.traclabs.biosim.server.simulation.food;

import junit.framework.TestCase;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentPOATie;
import com.traclabs.biosim.idl.simulation.food.BiomassRS;
import com.traclabs.biosim.idl.simulation.food.BiomassRSPOATie;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.BiomassStorePOATie;
import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.power.PowerStorePOATie;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStorePOATie;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStorePOATie;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStorePOATie;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.power.PowerStoreImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStoreImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterStoreImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterStoreImpl;
import com.traclabs.biosim.util.OrbUtils;

public class PlantImplTest extends TestCase {
	private BiomassRS myBiomassRS;
	
	//consumers
	private SimEnvironment mySimEnvironment;
	private PotableWaterStore myPotableWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private PowerStore myPowerStore;
	
	//producers
	private DirtyWaterStore myDirtyWaterStore;
	private BiomassStore myBiomassStore;
	
	private BioModule[] myModules = {myBiomassRS, mySimEnvironment, myPotableWaterStore, myBiomassStore, myGreyWaterStore, myDirtyWaterStore, myPowerStore};
	
	protected void setUp() throws Exception {
		super.setUp();
		OrbUtils.initializeLog();
		OrbUtils.startDebugNameServer();
		OrbUtils.initializeServerForDebug();
		BiomassRSImpl biomassRSImpl = new BiomassRSImpl();
		
		myBiomassRS = (new BiomassRSPOATie(biomassRSImpl))._this(OrbUtils.getORB());
		myBiomassRS.createNewShelf(PlantType.WHEAT, 15, 0);
		//initialize stores
		mySimEnvironment = (new SimEnvironmentPOATie(new SimEnvironmentImpl()))._this(OrbUtils.getORB());
		myPotableWaterStore = (new PotableWaterStorePOATie(new PotableWaterStoreImpl()))._this(OrbUtils.getORB());
		myPotableWaterStore.setInitialCapacity(10000f);
		myPotableWaterStore.setInitialLevel(10000f);
		myGreyWaterStore = (new GreyWaterStorePOATie(new GreyWaterStoreImpl()))._this(OrbUtils.getORB());
		myBiomassStore = (new BiomassStorePOATie(new BiomassStoreImpl()))._this(OrbUtils.getORB());
		myDirtyWaterStore = (new DirtyWaterStorePOATie(new DirtyWaterStoreImpl()))._this(OrbUtils.getORB());
		myPowerStore = (new PowerStorePOATie(new PowerStoreImpl()))._this(OrbUtils.getORB());
		myPowerStore.setInitialCapacity(1000000000f);
		myPowerStore.setInitialLevel(1000000000f);
		
		
		myBiomassRS.getAirConsumerDefinition().setAirInputs(new SimEnvironment[] {mySimEnvironment}, new float[] {1000f}, new float[] {1000f});
		myBiomassRS.getAirProducerDefinition().setAirOutputs(new SimEnvironment[] {mySimEnvironment}, new float[] {1000f}, new float[] {1000f});

		myBiomassRS.getPotableWaterConsumerDefinition().setPotableWaterInputs(new PotableWaterStore[] {myPotableWaterStore}, new float[] {1000f}, new float[] {1000f});
		myBiomassRS.getBiomassProducerDefinition().setBiomassOutputs(new BiomassStore[] {myBiomassStore}, new float[] {1000f}, new float[] {1000f});
		myBiomassRS.getDirtyWaterProducerDefinition().setDirtyWaterOutputs(new DirtyWaterStore[] {myDirtyWaterStore}, new float[] {1000f}, new float[] {1000f});
		myBiomassRS.getPowerConsumerDefinition().setPowerInputs(new PowerStore[] {myPowerStore}, new float[] {1000f}, new float[] {1000f});
		
		myModules = new BioModule[7];
		myModules[0] = myBiomassRS;
		myModules[1] = mySimEnvironment;
		myModules[2] = myPotableWaterStore;
		myModules[3] = myGreyWaterStore;
		myModules[4] = myBiomassStore;
		myModules[5] = myDirtyWaterStore;
		myModules[6] = myPowerStore;
		
		for (BioModule currentModule : myModules)
			currentModule.setTickLength(1);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testHighCO2Death(){
		float oldInitialLevel = mySimEnvironment.getCO2Store().getInitialLevel();
		mySimEnvironment.getCO2Store().setInitialLevel(100000000000000000f);
		float ticksWithHighCO2 = 4f * myBiomassRS.getTickLength();
		assertEquals(ticksWithHighCO2, getAverageTillDead(100), 1.5);
		mySimEnvironment.getCO2Store().setInitialLevel(oldInitialLevel);
	}
	
	public void testLowCO2Death(){
		float oldInitialLevel = mySimEnvironment.getCO2Store().getInitialLevel();
		mySimEnvironment.getCO2Store().setInitialLevel(0f);
		float ticksWithLowCO2 = 27f * myBiomassRS.getTickLength();
		assertEquals(ticksWithLowCO2, getAverageTillDead(100), 2);
		mySimEnvironment.getCO2Store().setInitialLevel(oldInitialLevel);
	}
	
	public void testLowWaterDeath(){
		float oldInitialLevel = myPotableWaterStore.getInitialLevel();
		myPotableWaterStore.setInitialLevel(0f);
		float ticksWithLowWater = 412f * myBiomassRS.getTickLength();
		assertEquals(ticksWithLowWater, getAverageTillDead(20), 2);
		myPotableWaterStore.setInitialLevel(oldInitialLevel);
	}
	
	public void testLowPowerDeath(){
		float oldInitialLevel = myPowerStore.getInitialLevel();
		myPowerStore.setInitialLevel(0f);
		float ticksWithLowPower = 408f * myBiomassRS.getTickLength();
		assertEquals(ticksWithLowPower, getAverageTillDead(20), 20);
		myPowerStore.setInitialLevel(oldInitialLevel);
	}
	
	private float getAverageTillDead(int trialsToRun){
		if (trialsToRun <= 0)
			return 0f;
		float totalTicksTillDead = 0;
		for (int i = 0; i < trialsToRun; i++){
			totalTicksTillDead += ticksUntilDead();
		}
		return totalTicksTillDead / trialsToRun;
	}
	
	private int ticksUntilDead(){
		reset();
		int totalTicks;
		for (totalTicks = 0; !myBiomassRS.isAnyPlantDead(); totalTicks++){
			myBiomassRS.tick();
		}
		return totalTicks;
	}
	
	private void reset(){
		for (BioModule currentModule : myModules)
			currentModule.reset();
	}

}
