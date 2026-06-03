package com.traclabs.biosim.integration;

import com.traclabs.biosim.server.framework.BioDriver;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.simulation.water.VCDWaterRS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end exercise of the shipped VCD water-recovery sample configuration: a VCD WaterRS
 * consuming power, dirty (urine) water, and grey water while producing potable water at an
 * 85% recovery ratio. Verifies the scenario runs over many ticks without driving any store
 * negative, that waste water is consumed while potable water accumulates, and that the
 * configured recovery ratio means less potable water is produced than waste water consumed.
 */
class VCDWaterRecoveryLoopIT {

    private static final int SIM_ID = 82;
    private static final int N_TICKS = 20;
    private static final Path XML_PATH = Path.of("configuration/test/VCDWaterInit.xml");

    @AfterEach
    void tearDown() {
        BiosimInitializer.deleteInstance(SIM_ID);
    }

    @Test
    void recoveryLoopRunsWithoutNegativeStoresAndHonorsRatio() throws Exception {
        BiosimInitializer.deleteInstance(SIM_ID);
        BiosimInitializer initializer = BiosimInitializer.getInstance(SIM_ID);
        initializer.parseXmlConfiguration(Files.readString(XML_PATH));

        BioDriver bioDriver = initializer.getBioDriver();
        assertNotNull(bioDriver, "BioDriver was not configured");
        bioDriver.reset();

        VCDWaterRS vcd = (VCDWaterRS) BiosimInitializer.getModule(SIM_ID, "VCDWaterRS");
        DirtyWaterStore dirtyStore = (DirtyWaterStore) BiosimInitializer.getModule(SIM_ID, "DirtyWaterStore");
        GreyWaterStore greyStore = (GreyWaterStore) BiosimInitializer.getModule(SIM_ID, "GreyWaterStore");
        PotableWaterStore potableStore = (PotableWaterStore) BiosimInitializer.getModule(SIM_ID, "PotableWaterStore");
        assertNotNull(vcd, "VCDWaterRS missing after parse");
        assertNotNull(dirtyStore, "DirtyWaterStore missing after parse");
        assertNotNull(greyStore, "GreyWaterStore missing after parse");
        assertNotNull(potableStore, "PotableWaterStore missing after parse");

        float dirtyStart = dirtyStore.getCurrentLevel();
        float potableStart = potableStore.getCurrentLevel();

        boolean producedPotable = false;
        float totalWasteConsumed = 0f;
        float totalPotableProduced = 0f;

        for (int step = 1; step <= N_TICKS; step++) {
            bioDriver.advanceOneTick();

            assertTrue(dirtyStore.getCurrentLevel() >= 0f, "DirtyWaterStore negative at step " + step);
            assertTrue(greyStore.getCurrentLevel() >= 0f, "GreyWaterStore negative at step " + step);
            assertTrue(potableStore.getCurrentLevel() >= 0f, "PotableWaterStore negative at step " + step);

            totalWasteConsumed += vcd.getWaterProcessed();
            totalPotableProduced += vcd.getPotableWaterProduced();
            if (vcd.getPotableWaterProduced() > 0f) {
                producedPotable = true;
            }
        }

        assertTrue(producedPotable, "VCD system never produced potable water during the run");
        assertTrue(dirtyStore.getCurrentLevel() < dirtyStart, "Dirty water was not consumed over the run");
        assertTrue(potableStore.getCurrentLevel() > potableStart, "Potable water did not accumulate over the run");
        assertTrue(totalPotableProduced < totalWasteConsumed,
                "Recovery ratio < 1 must yield less potable water than waste water processed");
        assertTrue(totalPotableProduced > 0.5f * totalWasteConsumed,
                "Potable production unexpectedly low for the configured recovery ratio");
    }
}
