package com.traclabs.biosim.server.simulation.framework;

import junit.framework.TestCase;

import com.traclabs.biosim.util.OrbUtils;

public class StoreImplTest extends TestCase {
	private StoreImpl myStoreImpl;

	protected void setUp() throws Exception {
		super.setUp();
		OrbUtils.initializeLog();
		myStoreImpl = new StoreImpl(0, "TestStore");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		myStoreImpl = null;
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.framework.StoreImpl.tick()'
	 */
	public void testTick() {
		myStoreImpl.setCurrentCapacity(200);
		myStoreImpl.setCurrentLevel(100);
		myStoreImpl.tick();
		assertEquals(myStoreImpl.getCurrentLevel(), 100, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
		myStoreImpl.tick();
		assertEquals(myStoreImpl.getCurrentLevel(), 100, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
		myStoreImpl.add(20);
		assertEquals(myStoreImpl.getCurrentLevel(), 100, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
		myStoreImpl.tick();
		assertEquals(myStoreImpl.getCurrentLevel(), 120, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
		myStoreImpl.add(20);
		myStoreImpl.take(50);
		assertEquals(myStoreImpl.getCurrentLevel(), 120, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
		myStoreImpl.tick();
		assertEquals(myStoreImpl.getCurrentLevel(), 90, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.framework.StoreImpl.reset()'
	 */
	public void testReset() {
		myStoreImpl.setInitialCapacity(200);
		myStoreImpl.setInitialLevel(100);
		assertEquals(myStoreImpl.getCurrentLevel(), 100, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
		myStoreImpl.add(20);
		myStoreImpl.take(50);
		assertEquals(myStoreImpl.getCurrentLevel(), 100, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
		myStoreImpl.tick();
		assertEquals(myStoreImpl.getCurrentLevel(), 70, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
		myStoreImpl.setCurrentCapacity(80);
		assertEquals(myStoreImpl.getCurrentCapacity(), 80, 0);
		myStoreImpl.tick();
		assertEquals(myStoreImpl.getCurrentLevel(), 70, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 80, 0);
		myStoreImpl.reset();
		assertEquals(myStoreImpl.getCurrentLevel(), 100, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.framework.StoreImpl.setResupply(int, float)'
	 */
	public void testSetResupply() {
		myStoreImpl.setInitialCapacity(200);
		myStoreImpl.setInitialLevel(100);
		assertEquals(myStoreImpl.getCurrentLevel(), 100, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
		myStoreImpl.setResupply(2, 1.5f);
		for (int i = 0; i < 54; i++)
			myStoreImpl.tick();
		assertEquals(myStoreImpl.getCurrentLevel(), 139f, 0);
		assertEquals(myStoreImpl.getCurrentCapacity(), 200, 0);
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.framework.StoreImpl.add(float)'
	 */
	public void testAdd() {
		myStoreImpl.setInitialCapacity(200);
		myStoreImpl.setInitialLevel(100);
		assertEquals(100, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.add(1);
		myStoreImpl.add(1);
		assertEquals(100, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.tick();
		assertEquals(102, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.add(21.3f);
		assertEquals(102, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.tick();
		assertEquals(123.3, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.add(0f);
		assertEquals(123.3, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.tick();
		assertEquals(123.3, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.add(30000f);
		assertEquals(123.3, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.tick();
		assertEquals(200, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.framework.StoreImpl.take(float)'
	 */
	public void testTake() {
		myStoreImpl.setInitialCapacity(200);
		myStoreImpl.setInitialLevel(100);
		assertEquals(100, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.take(1);
		myStoreImpl.take(1);
		assertEquals(100, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.tick();
		assertEquals(98, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.take(1.9f);
		assertEquals(98, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.tick();
		assertEquals(96.1, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.take(0f);
		assertEquals(96.1, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.tick();
		assertEquals(96.1, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.take(30000f);
		assertEquals(96.1, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
		myStoreImpl.tick();
		assertEquals(0, myStoreImpl.getCurrentLevel(), 1);
		assertEquals(200, myStoreImpl.getCurrentCapacity(), 1);
	}

	/*
	 * Test method for 'com.traclabs.biosim.server.simulation.framework.StoreImpl.getOverflow()'
	 */
	public void testGetOverflow() {
		myStoreImpl.setInitialCapacity(200);
		myStoreImpl.setInitialLevel(100);
		myStoreImpl.add(5);
		assertEquals(0, myStoreImpl.getOverflow(), 1);
		myStoreImpl.add(200);
		assertEquals(0, myStoreImpl.getOverflow(), 1);
		myStoreImpl.tick();
		assertEquals(105, myStoreImpl.getOverflow(), 1);
		myStoreImpl.tick();
		assertEquals(105, myStoreImpl.getOverflow(), 1);
		myStoreImpl.add(200);
		myStoreImpl.tick();
		assertEquals(305, myStoreImpl.getOverflow(), 1);
	}

}
