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
public class ElectronCarryingEnzymeTest extends TestCase {

    public void testTick() {
        ElectronCarryingEnzyme tempElectronCarryingEnzyme = new ElectronCarryingEnzyme();
        assertEquals(tempElectronCarryingEnzyme.hasElectron(), false);
        tempElectronCarryingEnzyme.reduce();
        assertEquals(tempElectronCarryingEnzyme.hasElectron(), false);
        tempElectronCarryingEnzyme.tick();
        assertEquals(tempElectronCarryingEnzyme.hasElectron(), true);
    }

    public void testHasElectron() {
        testTick();
        
    }

    public void testReduce() {
        testTick();
    }

    public void testOxidize() {
        ElectronCarryingEnzyme tempElectronCarryingEnzyme = new ElectronCarryingEnzyme();
        tempElectronCarryingEnzyme.reduce();
        tempElectronCarryingEnzyme.tick();
        assertEquals(tempElectronCarryingEnzyme.hasElectron(), true);
        tempElectronCarryingEnzyme.oxidize();
        assertEquals(tempElectronCarryingEnzyme.hasElectron(), true);
        tempElectronCarryingEnzyme.tick();
        assertEquals(tempElectronCarryingEnzyme.hasElectron(), false);
    }

}
