/*
 * Created on Jun 16, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import junit.framework.TestCase;

/**
 * @author scott
 *
 * TODO
 */
public class ChemicalTest extends TestCase {
    public void testTake() {
        Chemical testChemical = new Chemical(10);
        assertEquals(testChemical.getQuantity(), 10f, 0);
        testChemical.take(3);
        testChemical.tick();
        assertEquals(testChemical.getQuantity(), 7f, 0);
    }

    public void testAdd() {
        Chemical testChemical = new Chemical(10);
        assertEquals(testChemical.getQuantity(), 10f, 0);
        testChemical.add(2);
        testChemical.tick();
        assertEquals(testChemical.getQuantity(), 12f, 0);
    }

    public void testUpdate() {
        Chemical testChemical = new Chemical(10);
        assertEquals(testChemical.getQuantity(), 10f, 0);
        testChemical.take(5);
        assertEquals(testChemical.getQuantity(), 5f, 0);
        testChemical.tick();
        assertEquals(testChemical.getQuantity(), 5f, 0);
    }

    public void testGetQuantity() {
        Chemical testChemical = new Chemical(10);
        assertEquals(testChemical.getQuantity(), 10f, 0);
        testChemical.take(5);
        assertEquals(testChemical.getQuantity(), 5f, 0);
    }

}
