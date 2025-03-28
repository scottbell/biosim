/**
 *
 */
package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;

/**
 * @author scott
 */
public class RPCM extends SimBioModule {
    //Consumers, Producers
    private final PowerProducerDefinition myPowerProducerDefinition;
    private final PowerConsumerDefinition myPowerConsumerDefinition;
    private RPCMSwitchState mySwitchState = RPCMSwitchState.closed;
    private RPCMArmedStatus myArmedStatus = RPCMArmedStatus.enabled;


    public RPCM(int pID, String pName) {
        super(pID, pName);
        myPowerProducerDefinition = new PowerProducerDefinition(this);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
    }


    public void reset() {
        super.reset();
        myPowerProducerDefinition.reset();
        myPowerConsumerDefinition.reset();
    }


    public void tick() {
        super.tick();
        float powerGathered;
        if (getSwitchState() == RPCMSwitchState.closed)
            powerGathered = myPowerConsumerDefinition.getMostResourceFromStores();
        else
            powerGathered = myPowerConsumerDefinition.getResourceFromStores(0);
        myPowerProducerDefinition.pushResourceToStores(powerGathered);
    }

    public RPCMSwitchState getSwitchState() {
        return mySwitchState;
    }

    public void setSwitchState(RPCMSwitchState state) {
        if (getArmedStatus() == RPCMArmedStatus.enabled)
            this.mySwitchState = state;
        else
            myLogger.info(getModuleName() + ": attempted to change switch state when not armed");
    }

    public RPCMArmedStatus getArmedStatus() {
        return myArmedStatus;
    }

    public void setArmedStatus(RPCMArmedStatus state) {
        this.myArmedStatus = state;
    }

    @Override
    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
                                        MalfunctionLength pLength) {
        return "Broken";
    }

    @Override
    protected void performMalfunctions() {
        for (Malfunction malfunction : myMalfunctions.values()) {
            malfunction.setPerformed(true);
        }
        if (myMalfunctions.size() > 0) {
            myPowerConsumerDefinition.malfunction();
            myPowerProducerDefinition.malfunction();
        }
    }

}
