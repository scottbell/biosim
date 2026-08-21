package com.traclabs.biosim.integration;

import com.traclabs.biosim.server.framework.BioDriver;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.simulation.environment.Fan;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.util.stochastic.RelativeFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FanRelativeNoiseIT {

    private static final int SIM_ID = 43;

    private static final String XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<biosim xmlns=\"http://www.traclabs.com/biosim\"" +
                    "        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                    "        xsi:schemaLocation=\"http://www.traclabs.com/biosim schema/BiosimInitSchema.xsd\">" +
                    "    <Globals tickLength=\"0.016666667\" runTillN=\"0\" runTillCrewDeath=\"false\"" +
                    "             runTillPlantDeath=\"false\" startPaused=\"true\"" +
                    "             exitWhenFinished=\"false\" isLooping=\"false\"" +
                    "             driverStutterLength=\"0\"/>" +
                    "    <SimBioModules>" +
                    "        <power>" +
                    "            <PowerStore moduleName=\"FanPower\" level=\"100000\" capacity=\"100000\"/>" +
                    "        </power>" +
                    "        <environment>" +
                    "            <SimEnvironment moduleName=\"RoomA\" initialVolume=\"18000\">" +
                    "                <percentageInitialization waterPercentage=\"0.01\" nitrogenPercentage=\"0.659\"" +
                    "                    otherPercentage=\"0.001\" o2Percentage=\"0.33\" totalPressure=\"55\" co2Percentage=\"0\"/>" +
                    "            </SimEnvironment>" +
                    "            <SimEnvironment moduleName=\"RoomB\" initialVolume=\"18000\">" +
                    "                <percentageInitialization waterPercentage=\"0.01\" nitrogenPercentage=\"0.659\"" +
                    "                    otherPercentage=\"0.001\" o2Percentage=\"0.33\" totalPressure=\"55\" co2Percentage=\"0\"/>" +
                    "            </SimEnvironment>" +
                    "            <Fan moduleName=\"TestFan\">" +
                    "                <relativeStochasticFilter sigma=\"0.01\" isFilterEnabled=\"true\"/>" +
                    "                <airConsumer inputs=\"RoomA\" desiredFlowRates=\"804\" maxFlowRates=\"804\"/>" +
                    "                <powerConsumer inputs=\"FanPower\" desiredFlowRates=\"50\" maxFlowRates=\"50\"/>" +
                    "                <airProducer desiredFlowRates=\"804\" outputs=\"RoomB\" maxFlowRates=\"804\"/>" +
                    "            </Fan>" +
                    "        </environment>" +
                    "    </SimBioModules>" +
                    "</biosim>";

    @AfterEach
    void tearDown() {
        BiosimInitializer.deleteInstance(SIM_ID);
    }

    @Test
    void fanTickConservesMassWithRelativeNoiseOn() {
        BiosimInitializer.deleteInstance(SIM_ID);
        BiosimInitializer initializer = BiosimInitializer.getInstance(SIM_ID);
        initializer.parseXmlConfiguration(XML);
        BioDriver driver = initializer.getBioDriver();
        driver.reset();

        Fan fan = (Fan) BiosimInitializer.getModule(SIM_ID, "TestFan");
        assertTrue(fan.getStochasticFilter() instanceof RelativeFilter);
        assertTrue(fan.getStochasticFilter().getEnabled());

        SimEnvironment a = (SimEnvironment) BiosimInitializer.getModule(SIM_ID, "RoomA");
        SimEnvironment b = (SimEnvironment) BiosimInitializer.getModule(SIM_ID, "RoomB");
        double before = a.getTotalMoles() + b.getTotalMoles();
        driver.advanceOneTick();
        double after = a.getTotalMoles() + b.getTotalMoles();
        assertEquals(before, after, 1.0e-3);

        double moved = before / 2.0 - a.getTotalMoles();
        assertTrue(moved > 1.0, "fan did not move air: " + moved);
        double hourly = moved / (1.0 / 60.0);
        assertEquals(804.0, hourly, 80.0, "hourly mol/h=" + hourly);
    }
}
