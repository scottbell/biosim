package com.traclabs.biosim.server.simulation.crew;

import junit.framework.TestCase;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.crew.ActivityHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentPOATie;
import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.food.FoodStorePOATie;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStorePOATie;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStorePOATie;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStorePOATie;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStorePOATie;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.food.FoodStoreImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteStoreImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStoreImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterStoreImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterStoreImpl;
import com.traclabs.biosim.util.OrbUtils;

public class CrewGroupImplTest extends TestCase {
	private CrewGroup myCrewGroup;
	
	//consumers
	private SimEnvironment mySimEnvironment;
	private PotableWaterStore myPotableWaterStore;
	private FoodStore myFoodStore;
	
	//producers
	private GreyWaterStore myGreyWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private DryWasteStore myDryWasteStore;
	
	private BioModule[] myModules;
	
	protected void setUp() throws Exception {
		super.setUp();
		OrbUtils.initializeLog(false);
		OrbUtils.startStandaloneNameServer();
		OrbUtils.initializeServerForStandalone();
		CrewGroupImpl crewGroupImpl = new CrewGroupImpl();
		myCrewGroup = (new CrewGroupPOATie(crewGroupImpl))._this(OrbUtils.getORB());
		for (int i = 0; i < 1; i++)
			crewGroupImpl.createCrewPerson(createGenericSchedule(myCrewGroup));
		
		//initialize stores
		mySimEnvironment = (new SimEnvironmentPOATie(new SimEnvironmentImpl()))._this(OrbUtils.getORB());
		myPotableWaterStore = (new PotableWaterStorePOATie(new PotableWaterStoreImpl()))._this(OrbUtils.getORB());
		myPotableWaterStore.setInitialCapacity(10000f);
		myPotableWaterStore.setInitialLevel(10000f);
		myFoodStore = (new FoodStorePOATie(new FoodStoreImpl()))._this(OrbUtils.getORB());
		myGreyWaterStore = (new GreyWaterStorePOATie(new GreyWaterStoreImpl()))._this(OrbUtils.getORB());
		myDirtyWaterStore = (new DirtyWaterStorePOATie(new DirtyWaterStoreImpl()))._this(OrbUtils.getORB());
		myDryWasteStore = (new DryWasteStorePOATie(new DryWasteStoreImpl()))._this(OrbUtils.getORB());
		
		myCrewGroup.getAirConsumerDefinition().setAirInputs(new SimEnvironment[] {mySimEnvironment}, new float[] {1000f}, new float[] {1000f});
		myCrewGroup.getPotableWaterConsumerDefinition().setPotableWaterInputs(new PotableWaterStore[] {myPotableWaterStore}, new float[] {1000f}, new float[] {1000f});
		myCrewGroup.getFoodConsumerDefinition().setFoodInputs(new FoodStore[] {myFoodStore}, new float[] {1000f}, new float[] {1000f});
		myCrewGroup.getGreyWaterProducerDefinition().setGreyWaterOutputs(new GreyWaterStore[] {myGreyWaterStore}, new float[] {1000f}, new float[] {1000f});
		myCrewGroup.getDirtyWaterProducerDefinition().setDirtyWaterOutputs(new DirtyWaterStore[] {myDirtyWaterStore}, new float[] {1000f}, new float[] {1000f});
		myCrewGroup.getDryWasteProducerDefinition().setDryWasteOutputs(new DryWasteStore[] {myDryWasteStore}, new float[] {1000f}, new float[] {1000f});
		
		myModules = new BioModule[7];
		myModules[0] = myCrewGroup;
		myModules[1] = mySimEnvironment;
		myModules[2] = myPotableWaterStore;
		myModules[3] = myFoodStore;
		myModules[4] = myGreyWaterStore;
		myModules[5] = myDirtyWaterStore;
		myModules[6] = myDryWasteStore;
		
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

	public void testO2Consumption() {
		reset();
		float averageMolesOfO2NeededInDay = 29f * myCrewGroup.getTickLength();
		float molesOfOxygenConsumed = 0f;
		for (int i = 0; i < 24; i++){
			myCrewGroup.tick();
			molesOfOxygenConsumed += myCrewGroup.getCrewPeople()[0].getO2Consumed();
		}
		assertEquals(averageMolesOfO2NeededInDay, molesOfOxygenConsumed, 1f);
	}
	
	public void testLowO2Death(){
		float oldInitialLevel = mySimEnvironment.getO2Store().getInitialLevel();
		mySimEnvironment.getO2Store().setInitialLevel(0f);
		float ticksWithoutOxygenAverage = 4f * myCrewGroup.getTickLength();
		assertEquals(ticksWithoutOxygenAverage, getAverageTillDead(100), 2);
		mySimEnvironment.getO2Store().setInitialLevel(oldInitialLevel);
	}
	
	public void testWaterDeath(){
		float oldInitialLevel = myPotableWaterStore.getInitialLevel();
		myPotableWaterStore.setInitialLevel(0f);
		float ticksWithoutWaterAverage = 40f * myCrewGroup.getTickLength();
		assertEquals(ticksWithoutWaterAverage, getAverageTillDead(40), 4);
		myPotableWaterStore.setInitialLevel(oldInitialLevel);
	}
	
	public void testFoodDeath(){
		float oldInitialLevel = myFoodStore.getInitialLevel();
		myFoodStore.setInitialLevel(0f);
		float ticksWithoutFoodAverage = 350 * myCrewGroup.getTickLength();
		assertEquals(ticksWithoutFoodAverage, getAverageTillDead(20), 100);
		myFoodStore.setInitialLevel(oldInitialLevel);
	}
	
	public void testHighCO2Death(){
		float oldInitialLevel = mySimEnvironment.getCO2Store().getInitialLevel();
		mySimEnvironment.getCO2Store().setInitialLevel(100000000000000000f);
		float ticksWhileCO2HighAverage = 2f * myCrewGroup.getTickLength();
		assertEquals(ticksWhileCO2HighAverage, getAverageTillDead(200), 2);
		mySimEnvironment.getO2Store().setInitialLevel(oldInitialLevel);
	}
	
	public void testHighO2Death(){
		float oldInitialLevel = mySimEnvironment.getO2Store().getInitialLevel();
		mySimEnvironment.getO2Store().setInitialLevel(100000000000000000f);
		float ticksWhileFlammibleAverage = 800f * myCrewGroup.getTickLength();
		assertEquals(ticksWhileFlammibleAverage, getAverageTillDead(20), 100);
		mySimEnvironment.getO2Store().setInitialLevel(oldInitialLevel);
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
		for (totalTicks = 0; !myCrewGroup.isDead(); totalTicks++)
			myCrewGroup.tick();
		return totalTicks;
	}
	
    public Schedule createGenericSchedule(CrewGroup myCrewGroup){
    	Schedule theSchedule = new Schedule(myCrewGroup);
        ActivityImpl missionActivityImpl = new ActivityImpl("mission", 15, 3);
        ActivityImpl sleepActivityImpl = new ActivityImpl("sleep",8, 1);
        theSchedule.insertActivityInSchedule(ActivityHelper.narrow(OrbUtils.poaToCorbaObj(missionActivityImpl)));
        theSchedule.insertActivityInSchedule(ActivityHelper.narrow(OrbUtils.poaToCorbaObj(sleepActivityImpl)));
        return theSchedule;
    }

}
