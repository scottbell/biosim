package com.traclabs.biosim.integration;

import com.traclabs.biosim.server.framework.BioDriver;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.simulation.power.PowerStore;
import com.traclabs.biosim.server.simulation.power.RTGPowerPS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RTGPowerE2ETest {

    private static final int SIM_ID = 77;

    private static final int N_TICKS = 25;

    // Beginning-of-life output (upperPowerGeneration) configured below.
    private static final float BOL = 3000f;

    // Half-life in ticks (tickLength="1"), so "one half-life" == 10 driven calculations.
    private static final int HALF_LIFE = 10;

    private static final float TICK_LENGTH = 1f;

    private static final double REL_TOLERANCE = 0.02; // 2%

    private static final String TEST_CONFIG_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<biosim xmlns=\"http://www.traclabs.com/biosim\"" +
                    "        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    "        xsi:schemaLocation=\"http://www.traclabs.com/biosim schema/BiosimInitSchema.xsd\">" +
                    "    <Globals tickLength=\"1\" runTillN=\"0\" runTillCrewDeath=\"false\"" +
                    "             runTillPlantDeath=\"false\" startPaused=\"true\"" +
                    "             exitWhenFinished=\"false\" isLooping=\"false\"" +
                    "             driverStutterLength=\"0\"/>" +
                    "    <SimBioModules>" +
                    "        <power>" +
                    "            <PowerStore moduleName=\"General_Power_Store\" capacity=\"100000\" level=\"0\"/>" +
                    "            <PowerPS moduleName=\"RTG_Source\" generationType=\"RTG\"" +
                    "                     upperPowerGeneration=\"3000\" halfLife=\"10\">" +
                    "                <powerProducer outputs=\"General_Power_Store\"" +
                    "                               desiredFlowRates=\"3000\" maxFlowRates=\"3000\"/>" +
                    "            </PowerPS>" +
                    "        </power>" +
                    "    </SimBioModules>" +
                    "</biosim>";

    @AfterEach
    void tearDown() {
        BiosimInitializer.deleteInstance(SIM_ID);
    }

    @Test
    void rtgPowerDecaysAndFeedsStore() {
        BiosimInitializer.deleteInstance(SIM_ID);

        BiosimInitializer initializer = BiosimInitializer.getInstance(SIM_ID);
        initializer.parseXmlConfiguration(TEST_CONFIG_XML);

        BioDriver bioDriver = initializer.getBioDriver();
        assertNotNull(bioDriver, "BioDriver was not configured");
        bioDriver.reset();

        RTGPowerPS rtg = (RTGPowerPS) BiosimInitializer.getModule(SIM_ID, "RTG_Source");
        PowerStore store = (PowerStore) BiosimInitializer.getModule(SIM_ID, "General_Power_Store");
        assertNotNull(rtg, "RTG_Source missing after parse (RTG branch not taken)");
        assertNotNull(store, "General_Power_Store missing after parse");

        float initialStoreLevel = store.getCurrentLevel();

        float bolOutput = Float.NaN;
        float halfLifeOutput = Float.NaN;
        float previousOutput = Float.POSITIVE_INFINITY;

        for (int step = 1; step <= N_TICKS; step++) {
            bioDriver.advanceOneTick();

            float produced = rtg.getPowerProduced();
            // calculatePowerProduced() runs before myTicks++, so the value just produced
            // was computed with elapsed ticks = getMyTicks() - 1.
            int elapsedTicksAtComputation = rtg.getMyTicks() - 1;

            assertTrue(produced >= 0f,
                    "RTG output was negative at step " + step + ": " + produced);
            assertTrue(produced <= previousOutput + 1e-3f,
                    "RTG output increased at step " + step + ": " + produced
                            + " > previous " + previousOutput);
            previousOutput = produced;

            if (elapsedTicksAtComputation == 0) {
                bolOutput = produced;
            }
            if (elapsedTicksAtComputation == HALF_LIFE) {
                halfLifeOutput = produced;
            }
        }

        float expectedBol = BOL * TICK_LENGTH;
        assertTrue(!Float.isNaN(bolOutput), "BOL output was never captured");
        assertEquals(expectedBol, bolOutput, expectedBol * REL_TOLERANCE,
                "BOL output did not match upperPowerGeneration * tickLength");

        float expectedHalfLife = 0.5f * BOL * TICK_LENGTH;
        assertTrue(!Float.isNaN(halfLifeOutput),
                "Half-life output was never captured (need > HALF_LIFE ticks)");
        assertEquals(expectedHalfLife, halfLifeOutput, expectedHalfLife * REL_TOLERANCE,
                "Output after one half-life was not ~50% of BOL");

        assertTrue(store.getCurrentLevel() > initialStoreLevel,
                "PowerStore level did not increase; RTG power did not reach the store");
    }
}
