package com.traclabs.biosim.server.util.stochastic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RelativeFilterTest {

    @Test
    void desiredZeroStaysZero() {
        RelativeFilter filter = new RelativeFilter(0.01);
        filter.setEnabled(true);
        assertEquals(0f, filter.randomFilter(0f));
        assertEquals(0f, filter.randomFilter(-0f));
    }

    @Test
    void sigmaOnFiftyWattsStaysNearFifty() {
        RelativeFilter filter = new RelativeFilter(0.01);
        filter.setEnabled(true);
        double sum = 0;
        int n = 2000;
        for (int i = 0; i < n; i++) {
            float sample = filter.randomFilter(50f);
            assertTrue(sample >= 0f, "rectified or negative: " + sample);
            sum += sample;
        }
        double mean = sum / n;
        assertEquals(50.0, mean, 1.0);
    }

    @Test
    void disabledIsIdentity() {
        RelativeFilter filter = new RelativeFilter(0.01);
        filter.setEnabled(false);
        assertEquals(50f, filter.randomFilter(50f));
    }

    @Test
    void doesNotGoThroughNormalFilter() {
        assertFalse(NormalFilter.class.isAssignableFrom(RelativeFilter.class));
        assertTrue(StochasticFilter.class.isAssignableFrom(RelativeFilter.class));
    }
}
