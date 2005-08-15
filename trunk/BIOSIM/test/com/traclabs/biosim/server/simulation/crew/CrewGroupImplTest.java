package com.traclabs.biosim.server.simulation.crew;

import junit.framework.TestCase;

import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.food.FoodStoreImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteStoreImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStoreImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterStoreImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterStoreImpl;

public class CrewGroupImplTest extends TestCase {
	private CrewGroupImpl myCrewGroup;
	
	//consumers
	private SimEnvironmentImpl mySimEnvironment;
	private PotableWaterStoreImpl myPotableWaterStore;
	private FoodStoreImpl myFoodStore;
	
	//producers
	private GreyWaterStoreImpl myGreyWaterStore;
	private DirtyWaterStoreImpl myDirtyWaterStore;
	private DryWasteStoreImpl myDryWasteStore;
	

	protected void setUp() throws Exception {
		super.setUp();
		myCrewGroup = new CrewGroupImpl(0, "TestCrewGroup");
		for (int i = 0; i < 4; i++)
			myCrewGroup.createCrewPerson();
		//initialize stores
		mySimEnvironment = new SimEnvironmentImpl();
		myPotableWaterStore = new PotableWaterStoreImpl();
		myFoodStore = new FoodStoreImpl();
		myGreyWaterStore = new GreyWaterStoreImpl();
		myDirtyWaterStore = new DirtyWaterStoreImpl();
		myDryWasteStore = new DryWasteStoreImpl();
		
		//myCrewGroup.getAirConsumerDefinition().setAirInputs(mySimEnvironment, 1000f, 1000f);
		
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.crew.CrewGroupImpl.tick()'
	 */
	public void testTick() {
		
	}

}
