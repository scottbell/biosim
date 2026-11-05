package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.BioDriver;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.simulation.framework.Store;
import com.traclabs.biosim.server.util.MathUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HumidityAndTranspirationTest {

    private static final int SIM_ID = 43;

    private static String config(String dehumidifierAttrs, String waterFraction, boolean withCrop) {
        String nitrogen = String.valueOf(1.0 - 0.21 - 0.0012 - Double.parseDouble(waterFraction));
        String crop = withCrop
                ? "<food>"
                + "<BiomassStore moduleName=\"Biomass_Store\" capacity=\"1000\" level=\"0\"/>"
                + "<BiomassPS moduleName=\"Bed\" autoHarvestAndReplant=\"true\">"
                + "<shelf cropType=\"LETTUCE\" cropArea=\"10\"/>"
                + "<powerConsumer inputs=\"Power_Store\" desiredFlowRates=\"10\" maxFlowRates=\"10\"/>"
                + "<potableWaterConsumer inputs=\"Potable_Water_Store\" desiredFlowRates=\"0\" maxFlowRates=\"0\"/>"
                + "<greyWaterConsumer inputs=\"Grey_Water_Store\" desiredFlowRates=\"50\" maxFlowRates=\"50\"/>"
                + "<airConsumer inputs=\"Env\" desiredFlowRates=\"0\" maxFlowRates=\"0\"/>"
                + "<dirtyWaterProducer outputs=\"Dirty_Water_Store\" desiredFlowRates=\"100\" maxFlowRates=\"100\"/>"
                + "<biomassProducer outputs=\"Biomass_Store\" desiredFlowRates=\"100\" maxFlowRates=\"100\"/>"
                + "<airProducer outputs=\"Env\" desiredFlowRates=\"0\" maxFlowRates=\"0\"/>"
                + "</BiomassPS></food>"
                : "";
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<biosim xmlns=\"http://www.traclabs.com/biosim\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<Globals tickLength=\"1\" runTillN=\"0\" runTillCrewDeath=\"false\""
                + " runTillPlantDeath=\"false\" startPaused=\"true\" driverStutterLength=\"0\"/>"
                + "<SimBioModules>"
                + "<environment>"
                + "<SimEnvironment moduleName=\"Env\" initialVolume=\"500000\">"
                + "<percentageInitialization totalPressure=\"101\" o2Percentage=\"0.21\""
                + " co2Percentage=\"0.0012\" nitrogenPercentage=\"" + nitrogen + "\""
                + " waterPercentage=\"" + waterFraction + "\" otherPercentage=\"0\"/>"
                + "</SimEnvironment>"
                + "<Dehumidifier moduleName=\"Dehumidifier\" " + dehumidifierAttrs + ">"
                + "<airConsumer inputs=\"Env\" desiredFlowRates=\"1000\" maxFlowRates=\"1000\"/>"
                + "<dirtyWaterProducer outputs=\"Dirty_Water_Store\" desiredFlowRates=\"1000\" maxFlowRates=\"1000\"/>"
                + "</Dehumidifier>"
                + "</environment>"
                + "<water>"
                + "<PotableWaterStore moduleName=\"Potable_Water_Store\" capacity=\"1000\" level=\"1000\"/>"
                + "<GreyWaterStore moduleName=\"Grey_Water_Store\" capacity=\"5000\" level=\"5000\"/>"
                + "<DirtyWaterStore moduleName=\"Dirty_Water_Store\" capacity=\"100000\" level=\"0\"/>"
                + "</water>"
                + "<power><PowerStore moduleName=\"Power_Store\" capacity=\"1000000\" level=\"1000000\"/></power>"
                + crop
                + "</SimBioModules></biosim>";
    }

    private BioDriver start(String xml) {
        BiosimInitializer.deleteInstance(SIM_ID);
        BiosimInitializer initializer = BiosimInitializer.getInstance(SIM_ID);
        initializer.parseXmlConfiguration(xml);
        BioDriver driver = initializer.getBioDriver();
        driver.reset();
        return driver;
    }

    @AfterEach
    void tearDown() {
        BiosimInitializer.deleteInstance(SIM_ID);
    }

    @Test
    void expIsTheExponential() {
        assertEquals(1.0f, MathUtils.exp(0f), 1e-6f);
        assertEquals((float) Math.E, MathUtils.exp(1f), 1e-5f);
        assertEquals((float) Math.exp(-2.5), MathUtils.exp(-2.5f), 1e-6f);
    }

    @Test
    void saturationPressureMatchesPlantCurve() {
        // 0.611 * exp(17.4 * 23 / (23 + 239))
        assertEquals(2.8143f, SimEnvironment.saturatedVaporPressure(23f), 1e-3f);
    }

    @Test
    void dehumidifierHoldsTargetRelativeHumidity() {
        BioDriver driver = start(config("targetRelativeHumidity=\"0.7\"", "0.025", false));
        SimEnvironment env = (SimEnvironment) BiosimInitializer.getModule(SIM_ID, "Env");
        Store dirty = (Store) BiosimInitializer.getModule(SIM_ID, "Dirty_Water_Store");
        assertTrue(env.getRelativeHumidity() > 0.85f, "start above target");
        for (int i = 0; i < 3; i++)
            driver.advanceOneTick();
        assertEquals(0.7f, env.getRelativeHumidity(), 1e-3f);
        assertTrue(dirty.getCurrentLevel() > 0f, "removed vapor becomes dirty water");
    }

    @Test
    void dehumidifierWithoutTargetKeepsOriginalBehaviour() {
        BioDriver driver = start(config("", "0.025", false));
        SimEnvironment env = (SimEnvironment) BiosimInitializer.getModule(SIM_ID, "Env");
        float before = env.getVaporStore().getCurrentLevel();
        for (int i = 0; i < 3; i++)
            driver.advanceOneTick();
        assertEquals(before, env.getVaporStore().getCurrentLevel(), 1e-3f);
    }

    @Test
    void lettuceTranspiresIntoTheAir() {
        // Before the MathUtils.exp fix the vapor pressure deficit was always clamped to 0,
        // so vapor never increased however long the crop grew.
        BioDriver driver = start(config("", "0.01", true));
        SimEnvironment env = (SimEnvironment) BiosimInitializer.getModule(SIM_ID, "Env");
        float before = env.getVaporStore().getCurrentLevel();
        for (int i = 0; i < 300; i++)
            driver.advanceOneTick();
        assertTrue(env.getVaporStore().getCurrentLevel() > before + 1f,
                "vapor " + before + " -> " + env.getVaporStore().getCurrentLevel());
    }
}
