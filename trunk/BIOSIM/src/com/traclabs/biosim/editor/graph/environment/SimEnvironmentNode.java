package com.traclabs.biosim.editor.graph.environment;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.PassiveNode;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.CO2AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.CO2AirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.O2AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.O2AirProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.WaterAirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.WaterAirProducerOperations;
import com.traclabs.biosim.server.sensor.environment.CO2AirPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.EnvironmentSensorImpl;
import com.traclabs.biosim.server.sensor.environment.NitrogenAirPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.O2AirPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.OtherAirPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.WaterAirPressureSensorImpl;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class SimEnvironmentNode extends PassiveNode{
    private SimEnvironmentImpl mySimEnvironmentImpl;
    private static int nameID = 0;

    private EnvironmentSensorImpl myO2AirPressureSensorImpl;
    private EnvironmentSensorImpl myCO2AirPressureSensorImpl;
    private EnvironmentSensorImpl myNitrogenAirPressureSensorImpl;
    private EnvironmentSensorImpl myOtherAirPressureSensorImpl;
    private EnvironmentSensorImpl myWaterAirPressureSensorImpl;
    
    private final static Class[] myProducersAllowed = {AirProducerOperations.class, CO2AirProducerOperations.class, O2AirProducerOperations.class, NitrogenAirProducerOperations.class, WaterAirProducerOperations.class};
    private final static Class[] myConsumersAllowed = {AirConsumerOperations.class, CO2AirConsumerOperations.class, O2AirConsumerOperations.class, NitrogenAirConsumerOperations.class, WaterAirConsumerOperations.class};
    
    public SimEnvironmentNode() {
        mySimEnvironmentImpl = new SimEnvironmentImpl(0, "SimEnvironment"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigSimEnvironmentNode node = new FigSimEnvironmentNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return mySimEnvironmentImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.PassiveNode#getProducersAllowed()
     */
    public Class[] getProducersAllowed() {
        return myProducersAllowed;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.PassiveNode#getConsumersAllowed()
     */
    public Class[] getConsumersAllowed() {
        return myConsumersAllowed;
    }
    
    public void addSensor() {
        myO2AirPressureSensorImpl =  new O2AirPressureSensorImpl(0, getSimBioModuleImpl().getModuleName() + "O2AirPressureSensor");
        myCO2AirPressureSensorImpl =  new CO2AirPressureSensorImpl(0, getSimBioModuleImpl().getModuleName() + "CO2AirPressureSensor");
        myNitrogenAirPressureSensorImpl =  new NitrogenAirPressureSensorImpl(0, getSimBioModuleImpl().getModuleName() + "NitrogenAirPressureSensor");
        myOtherAirPressureSensorImpl =  new OtherAirPressureSensorImpl(0, getSimBioModuleImpl().getModuleName() + "OtherAirPressureSensor");
        myWaterAirPressureSensorImpl =  new WaterAirPressureSensorImpl(0, getSimBioModuleImpl().getModuleName() + "WaterAirPressureSensor");
    }

    /**
     * 
     */
    public void removeSensor() {
        myO2AirPressureSensorImpl =  null;
        myCO2AirPressureSensorImpl =  null;
        myNitrogenAirPressureSensorImpl = null;
        myOtherAirPressureSensorImpl =  null;
        myWaterAirPressureSensorImpl= null;;
    
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "SimEnvironment";
    }
}
