package com.traclabs.biosim.server.simulation.crew;

import junit.framework.TestCase;

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
	

	protected void setUp() throws Exception {
		super.setUp();
		CrewGroupImpl crewGroupImpl = new CrewGroupImpl();
		for (int i = 0; i < 4; i++)
			crewGroupImpl.createCrewPerson();
		myCrewGroup = (new CrewGroupPOATie(crewGroupImpl))._this(OrbUtils.getORB());
		//initialize stores
		mySimEnvironment = (new SimEnvironmentPOATie(new SimEnvironmentImpl()))._this(OrbUtils.getORB());
		myPotableWaterStore = (new PotableWaterStorePOATie(new PotableWaterStoreImpl()))._this(OrbUtils.getORB());
		myFoodStore = (new FoodStorePOATie(new FoodStoreImpl()))._this(OrbUtils.getORB());
		myGreyWaterStore = (new GreyWaterStorePOATie(new GreyWaterStoreImpl()))._this(OrbUtils.getORB());
		myDirtyWaterStore = (new DirtyWaterStorePOATie(new DirtyWaterStoreImpl()))._this(OrbUtils.getORB());
		myDryWasteStore = (new DryWasteStorePOATie(new DryWasteStoreImpl()))._this(OrbUtils.getORB());
		
		myCrewGroup.getAirConsumerDefinition().setAirInputs(new SimEnvironment[] {mySimEnvironment}, new float[] {1000f}, new float[] {1000f});
		myCrewGroup.getPotableWaterConsumerDefinition().setPotableWaterInputs(new PotableWaterStore[] {myPotableWaterStore}, new float[] {1000f}, new float[] {1000f});
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.crew.CrewGroup.tick()'
	 */
	public void testTick() {
		
	}

}
