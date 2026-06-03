package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.BioDriver;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VCDWaterRSTest {

    private static final int SIM_ID = 81;

    private static final float RECOVERY_RATIO = 0.85f;
    private static final float ENERGY_PER_LITER = 361.5f;
    private static final float NOMINAL_POWER = 1540.0f;
    // power / processingEnergyPerLiter * tickLength = 1540 / 361.5 ~= 4.26 L per tick.
    private static final float NOMINAL_PROCESSED = NOMINAL_POWER / ENERGY_PER_LITER;

    private BioDriver bioDriver;

    @AfterEach
    void tearDown() {
        BiosimInitializer.deleteInstance(SIM_ID);
    }

    private VCDWaterRS bootstrap(float dirtyLevel, float greyLevel, float powerFlowCap) {
        BiosimInitializer.deleteInstance(SIM_ID);
        BiosimInitializer initializer = BiosimInitializer.getInstance(SIM_ID);
        initializer.parseXmlConfiguration(buildConfig(dirtyLevel, greyLevel, powerFlowCap));
        bioDriver = initializer.getBioDriver();
        assertNotNull(bioDriver, "BioDriver was not configured");
        bioDriver.reset();
        return (VCDWaterRS) BiosimInitializer.getModule(SIM_ID, "VCDWaterRS");
    }

    private static String buildConfig(float dirtyLevel, float greyLevel, float powerFlowCap) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<biosim xmlns=\"http://www.traclabs.com/biosim\"" +
                "        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                "        xsi:schemaLocation=\"http://www.traclabs.com/biosim schema/BiosimInitSchema.xsd\">" +
                "    <Globals tickLength=\"1\" runTillN=\"0\" runTillCrewDeath=\"false\"" +
                "             runTillPlantDeath=\"false\" startPaused=\"true\"" +
                "             exitWhenFinished=\"false\" isLooping=\"false\"" +
                "             driverStutterLength=\"0\"/>" +
                "    <SimBioModules>" +
                "        <power>" +
                "            <PowerStore moduleName=\"PowerStore\" capacity=\"100000000.0\" level=\"10000000.0\"/>" +
                "        </power>" +
                "        <water>" +
                "            <DirtyWaterStore moduleName=\"DirtyWaterStore\" capacity=\"1000000.0\" level=\"" + dirtyLevel + "\"/>" +
                "            <GreyWaterStore moduleName=\"GreyWaterStore\" capacity=\"1000000.0\" level=\"" + greyLevel + "\"/>" +
                "            <PotableWaterStore moduleName=\"PotableWaterStore\" capacity=\"1000000.0\" level=\"0.0\"/>" +
                "            <WaterRS moduleName=\"VCDWaterRS\" implementation=\"VCD\"" +
                "                     waterRecoveryRatio=\"0.85\" processingEnergyPerLiter=\"361.5\">" +
                "                <powerConsumer desiredFlowRates=\"" + powerFlowCap + "\" maxFlowRates=\"" + powerFlowCap + "\" inputs=\"PowerStore\"/>" +
                "                <dirtyWaterConsumer desiredFlowRates=\"1000.0\" maxFlowRates=\"1000.0\" inputs=\"DirtyWaterStore\"/>" +
                "                <greyWaterConsumer desiredFlowRates=\"1000.0\" maxFlowRates=\"1000.0\" inputs=\"GreyWaterStore\"/>" +
                "                <potableWaterProducer desiredFlowRates=\"1000.0\" maxFlowRates=\"1000.0\" outputs=\"PotableWaterStore\"/>" +
                "            </WaterRS>" +
                "        </water>" +
                "    </SimBioModules>" +
                "</biosim>";
    }

    private com.traclabs.biosim.server.simulation.framework.Store dirtyStore() {
        return (com.traclabs.biosim.server.simulation.framework.Store)
                BiosimInitializer.getModule(SIM_ID, "DirtyWaterStore");
    }

    private com.traclabs.biosim.server.simulation.framework.Store greyStore() {
        return (com.traclabs.biosim.server.simulation.framework.Store)
                BiosimInitializer.getModule(SIM_ID, "GreyWaterStore");
    }

    private com.traclabs.biosim.server.simulation.framework.Store potableStore() {
        return (com.traclabs.biosim.server.simulation.framework.Store)
                BiosimInitializer.getModule(SIM_ID, "PotableWaterStore");
    }

    @Test
    void moduleStartsAndIsRegisteredWithConfiguredParameters() {
        VCDWaterRS vcd = bootstrap(100000f, 100000f, NOMINAL_POWER);
        assertNotNull(vcd, "VCDWaterRS module missing after parse");
        assertInstanceOf(VCDWaterRS.class, BiosimInitializer.getModule(SIM_ID, "VCDWaterRS"));
        assertEquals(RECOVERY_RATIO, vcd.getRecoveryRatio(), 1e-6);
        assertEquals(ENERGY_PER_LITER, vcd.getProcessingEnergyPerLiter(), 1e-4);
    }

    @Test
    void nominalTickConsumesDirtyWaterAndProducesPotable() {
        VCDWaterRS vcd = bootstrap(100000f, 100000f, NOMINAL_POWER);
        float dirtyBefore = dirtyStore().getCurrentLevel();
        float potableBefore = potableStore().getCurrentLevel();

        bioDriver.advanceOneTick();

        assertTrue(dirtyStore().getCurrentLevel() < dirtyBefore, "Dirty water store did not decrease");
        assertTrue(potableStore().getCurrentLevel() > potableBefore, "Potable water store did not increase");

        assertEquals(NOMINAL_PROCESSED, vcd.getWaterProcessed(), 0.05f,
                "Throughput not energy-limited as expected");
        assertEquals(NOMINAL_POWER, vcd.getPowerConsumed(), 1e-2f);
        assertEquals(RECOVERY_RATIO * vcd.getWaterProcessed(), vcd.getPotableWaterProduced(), 1e-3f,
                "Potable output is not recoveryRatio * processed");
    }

    @Test
    void recoveryRatioRejectsRemainderAsBrine() {
        VCDWaterRS vcd = bootstrap(100000f, 100000f, NOMINAL_POWER);
        bioDriver.advanceOneTick();

        float processed = vcd.getWaterProcessed();
        float potable = vcd.getPotableWaterProduced();
        float brine = vcd.getBrineProduced();

        assertTrue(potable < processed, "Recovery should be less than 100% (potable < processed)");
        assertEquals((1f - RECOVERY_RATIO) * processed, brine, 1e-3f, "Brine is not the rejected remainder");
        assertEquals(processed, potable + brine, 1e-3f, "Potable + brine must equal processed");
    }

    @Test
    void throughputIsEnergyLimited() {
        // Power flow capped at exactly processingEnergyPerLiter -> ~1 liter processed per tick.
        VCDWaterRS limited = bootstrap(100000f, 100000f, ENERGY_PER_LITER);
        bioDriver.advanceOneTick();
        assertEquals(1.0f, limited.getWaterProcessed(), 0.02f,
                "Processed water should equal power / processingEnergyPerLiter");

        // Doubling the power flow cap should roughly double the throughput.
        VCDWaterRS doubled = bootstrap(100000f, 100000f, 2f * ENERGY_PER_LITER);
        bioDriver.advanceOneTick();
        assertEquals(2.0f, doubled.getWaterProcessed(), 0.04f,
                "Throughput should scale with available power");
    }

    @Test
    void noWasteWaterProducesNothingAndNoNegativeStores() {
        VCDWaterRS vcd = bootstrap(0f, 0f, NOMINAL_POWER);
        float potableBefore = potableStore().getCurrentLevel();

        bioDriver.advanceOneTick();

        assertEquals(0f, vcd.getWaterProcessed(), 1e-6f);
        assertEquals(0f, vcd.getPotableWaterProduced(), 1e-6f);
        assertEquals(0f, vcd.getBrineProduced(), 1e-6f);
        assertEquals(potableBefore, potableStore().getCurrentLevel(), 1e-3f);
        assertTrue(dirtyStore().getCurrentLevel() >= 0f, "Dirty store driven negative");
        assertTrue(greyStore().getCurrentLevel() >= 0f, "Grey store driven negative");
    }

    @Test
    void malfunctionReducesThroughputThenRecoversWhenCleared() {
        VCDWaterRS vcd = bootstrap(500000f, 500000f, NOMINAL_POWER);

        bioDriver.advanceOneTick();
        float nominalProcessed = vcd.getWaterProcessed();
        assertEquals(NOMINAL_PROCESSED, nominalProcessed, 0.05f);

        Malfunction malfunction = vcd.startMalfunction(
                MalfunctionIntensity.MEDIUM_MALF, MalfunctionLength.TEMPORARY_MALF);
        bioDriver.advanceOneTick();
        assertEquals(0.5f * nominalProcessed, vcd.getWaterProcessed(), 0.05f,
                "Medium malfunction did not halve throughput");
        assertEquals(RECOVERY_RATIO * vcd.getWaterProcessed(), vcd.getPotableWaterProduced(), 1e-3f,
                "Recovery ratio not honored during malfunction");

        vcd.clearMalfunction(malfunction.getID());
        bioDriver.advanceOneTick();
        assertEquals(nominalProcessed, vcd.getWaterProcessed(), 0.05f,
                "Throughput did not recover after malfunction cleared");
    }
}
