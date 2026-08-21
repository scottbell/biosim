package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.server.util.stochastic.NormalFilter;
import com.traclabs.biosim.server.util.stochastic.RelativeFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoreTakeTest {

    @Test
    void neverTakesMoreThanLevelAndNeverGoesNegative() {
        Store store = new Store(0, "tank", 10f, 100f, false);
        float taken = store.take(100f);
        assertEquals(10f, taken, 1e-6);
        assertEquals(0f, store.getCurrentLevel(), 1e-6);

        store.setCurrentLevel(5f);
        assertEquals(0f, store.take(0f), 1e-6);
        assertEquals(5f, store.getCurrentLevel(), 1e-6);
        assertEquals(0f, store.take(-3f), 1e-6);
        assertEquals(5f, store.getCurrentLevel(), 1e-6);
    }

    @Test
    void inflatedFilterCannotDriveLevelNegative() {
        Store store = new Store(0, "tank", 1f, 100f, false);
        store.setStochasticFilter(new NormalFilter(20));
        store.getStochasticFilter().setEnabled(true);
        for (int i = 0; i < 50; i++) {
            store.setCurrentLevel(1f);
            float taken = store.take(1f);
            assertTrue(taken >= 0f, "taken " + taken);
            assertTrue(store.getCurrentLevel() >= 0f, "level " + store.getCurrentLevel());
            assertTrue(taken <= 1f + 1e-5, "took more than available: " + taken);
        }
    }

    @Test
    void relativeZeroDesiredTakesZero() {
        Store store = new Store(0, "tank", 10f, 100f, false);
        RelativeFilter filter = new RelativeFilter(0.01);
        filter.setEnabled(true);
        store.setStochasticFilter(filter);
        assertEquals(0f, store.take(0f));
        assertEquals(10f, store.getCurrentLevel(), 1e-6);
    }
}
