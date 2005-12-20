package com.traclabs.biosim.client.control;

import java.util.Map;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.simulation.air.OGS;
import com.traclabs.biosim.idl.simulation.water.WaterRS;

public class ActionMap {
    private BioHolder myBioHolder;

    private Logger myLogger;

    private boolean myWaterRSConsumeDirtyWater = false;

    private boolean myWaterRSConsumeGreyWater = false;

    private boolean myOGSProduceO2 = false;

    private GenericActuator myOGSPotableWaterInFlowRateActuator;

    private GenericActuator myOGSPowerInFlowRateActuator;

    private GenericActuator myWaterRSDirtyWaterInFlowRateActuator;

    private GenericActuator myWaterRSGreyWaterInFlowRateActuator;

    private GenericActuator myWaterRSPowerInFlowRateActuator;

    public ActionMap() {
        collectReferences();
        myLogger = Logger.getLogger(this.getClass());
    }

    private void collectReferences() {
        myBioHolder = BioHolderInitializer.getBioHolder();

        WaterRS myWaterRS = myBioHolder.theWaterRSModules.get(0);
        OGS myOGS = myBioHolder.theOGSModules.get(0);

        myOGSPotableWaterInFlowRateActuator = myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.thePotableWaterInFlowRateActuators, myOGS);
        myOGSPowerInFlowRateActuator = myBioHolder.getActuatorAttachedTo(
                myBioHolder.thePowerInFlowRateActuators, myOGS);

        myWaterRSDirtyWaterInFlowRateActuator = myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theDirtyWaterInFlowRateActuators, myWaterRS);
        myWaterRSGreyWaterInFlowRateActuator = myBioHolder
                .getActuatorAttachedTo(
                        myBioHolder.theGreyWaterInFlowRateActuators, myWaterRS);
        myWaterRSPowerInFlowRateActuator = myBioHolder.getActuatorAttachedTo(
                myBioHolder.thePowerInFlowRateActuators, myWaterRS);
    }

    /**
     * @param classifiedState
     */
    public void performAction(Map classifiedState) {
        if (classifiedState.get("potablewater") == HandController.LOW) {
            myLogger.debug("potablewater is LOW! ");
            myWaterRSConsumeDirtyWater = true;
        } else if (classifiedState.get("dirtywater") == HandController.HIGH
                && classifiedState.get("potablewater") != HandController.HIGH) {
            myWaterRSConsumeDirtyWater = true;
        } else {
            myWaterRSConsumeDirtyWater = false;
        }
        if (classifiedState.get("potablewater") == HandController.LOW
                && classifiedState.get("greywater") != HandController.LOW) {
            myWaterRSConsumeGreyWater = true;
        } else if (classifiedState.get("greywater") == HandController.HIGH
                && classifiedState.get("potablewater") != HandController.HIGH) {
            myWaterRSConsumeGreyWater = true;
        } else {
            myWaterRSConsumeGreyWater = false;
        }
        if (classifiedState.get("oxygen") == HandController.LOW) {
            myLogger.debug("oxygen is LOW! ");
            myOGSProduceO2 = true;
        }
        if (classifiedState.get("oxygen") == HandController.HIGH) {
            myOGSProduceO2 = false;
        }

        myLogger.debug("myWaterRSConsumeDirtyWater "
                + myWaterRSConsumeDirtyWater);
        myLogger
                .debug("myWaterRSConsumeGreyWater " + myWaterRSConsumeGreyWater);
        myLogger.debug("myOGSProduceO2 " + myOGSProduceO2);

        //OGS
        if (myOGSProduceO2) {
            myOGSPotableWaterInFlowRateActuator
                    .setValue(myOGSPotableWaterInFlowRateActuator.getMax());
            myOGSPowerInFlowRateActuator.setValue(myOGSPowerInFlowRateActuator
                    .getMax());
        } else {
            myOGSPotableWaterInFlowRateActuator
                    .setValue(myOGSPotableWaterInFlowRateActuator.getMin());
            myOGSPowerInFlowRateActuator.setValue(myOGSPowerInFlowRateActuator
                    .getMin());
        }

        //WaterRS
        if (myWaterRSConsumeDirtyWater || myWaterRSConsumeGreyWater)
            myWaterRSPowerInFlowRateActuator
                    .setValue(myWaterRSPowerInFlowRateActuator.getMax());
        else
            myWaterRSPowerInFlowRateActuator
                    .setValue(myWaterRSPowerInFlowRateActuator.getMin());
        if (myWaterRSConsumeDirtyWater)
            myWaterRSDirtyWaterInFlowRateActuator
                    .setValue(myWaterRSDirtyWaterInFlowRateActuator.getMax());
        else
            myWaterRSDirtyWaterInFlowRateActuator
                    .setValue(myWaterRSDirtyWaterInFlowRateActuator.getMin());
        if (myWaterRSConsumeGreyWater)
            myWaterRSGreyWaterInFlowRateActuator
                    .setValue(myWaterRSGreyWaterInFlowRateActuator.getMax());
        else
            myWaterRSGreyWaterInFlowRateActuator
                    .setValue(myWaterRSGreyWaterInFlowRateActuator.getMin());
    }
}