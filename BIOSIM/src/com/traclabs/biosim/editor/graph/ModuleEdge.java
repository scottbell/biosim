package com.traclabs.biosim.editor.graph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.presentation.FigEdge;

import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 */

public class ModuleEdge extends NetEdge {
    private final static String GET_STRING = "get";
    private final static String DEFINITION_STRING = "Definition";
    private final static String FLOWRATE_STRING = " Flowrate";
    private String myName = "Unnamed";
    
    private GenericActuatorImpl myActuatorImpl;
    private GenericSensorImpl mySensorImpl;
    private int myIndex;
    private Logger myLogger;
    private SimBioModuleImpl myActiveModule;
    private StoreImpl myStoreImpl;
    private SimEnvironmentImpl mySimEnvironmentImpl;
    private SingleFlowRateControllable myOperations;
    private boolean amProducerEdge = false;
    private Class mySensorClass;
    private Class myActuatorClass;
    
    /** Construct a new SampleEdge. */
    public ModuleEdge() {
        myLogger = Logger.getLogger(ModuleEdge.class);
        myName = "Unnamed";
    } 

    public String getId() {
        return toString();
    }
    
    public String getName() {
        return myName;
    }
    
    public void setIndex(int pIndex){
        myIndex = pIndex;
    }
    
    public int getIndex(){
        return myIndex;
    }

    public FigEdge makePresentation(Layer lay) {
        return new FigModuleEdge();
    }
    
    /**
     * 
     */
    public void initializeFlowrates(){
        getOperations();
        if (myOperations.getMaxFlowRates().length == 0){
            float[] maxFlowrates = {0f};
            float[] desiredFlowrates = {0f};
            float[] actualFlowrates = {0f};
            myOperations.setMaxFlowRates(maxFlowrates);
            myOperations.setDesiredFlowRates(desiredFlowrates);
            myOperations.setActualFlowRates(actualFlowrates);
        }
        else if (myOperations.getMaxFlowRates().length < myIndex + 1){
            myLogger.info("doesn't support more than 1 edge right now");
        }
    }
    
    /**
     * @return
     */
    public StoreImpl getStoreImpl(){
        if (myStoreImpl != null)
            return myStoreImpl;
        EditorPort sourcePort = (EditorPort)getSourcePort();
        EditorPort destPort = (EditorPort)getDestPort();
        if (sourcePort.getParent() instanceof PassiveNode){
            //we're consuming
            myStoreImpl = (StoreImpl)((PassiveNode)sourcePort.getParent()).getSimBioModuleImpl();
            return myStoreImpl;
        }
        else if (destPort.getParent() instanceof PassiveNode){
            //we're producing
            myStoreImpl = (StoreImpl)((PassiveNode)destPort.getParent()).getSimBioModuleImpl();
            return myStoreImpl;
        }
        else
            return null;
    }
    
    /**
     * @return
     */
    public SimEnvironmentImpl getSimEnvironmentImpl(){
        if (mySimEnvironmentImpl != null)
            return mySimEnvironmentImpl;
        EditorPort sourcePort = (EditorPort)getSourcePort();
        EditorPort destPort = (EditorPort)getDestPort();
        if (sourcePort.getParent() instanceof PassiveNode){
        	mySimEnvironmentImpl = (SimEnvironmentImpl)((PassiveNode)sourcePort.getParent()).getSimBioModuleImpl();
            return mySimEnvironmentImpl;
        }
        else if (destPort.getParent() instanceof PassiveNode){
        	mySimEnvironmentImpl = (SimEnvironmentImpl)((PassiveNode)destPort.getParent()).getSimBioModuleImpl();
            return mySimEnvironmentImpl;
        }
        else
            return null;
    }

    /**
     * @return
     */
    public SingleFlowRateControllable getOperations() {
        if (myOperations != null)
            return myOperations;
        EditorPort sourcePort = (EditorPort)getSourcePort();
        EditorPort destPort = (EditorPort)getDestPort();
        if (sourcePort.getParent() instanceof ActiveNode){
            //we're producing
            amProducerEdge = true;
            myActiveModule = ((ActiveNode)sourcePort.getParent()).getSimBioModuleImpl();
            PassiveNode thePassiveNode = (PassiveNode)(destPort.getParent());
            Class[] theProducersAllowed = thePassiveNode.getProducersAllowed();
            for (int i = 0; i < theProducersAllowed.length; i++){
                if (theProducersAllowed[i].isInstance(myActiveModule)){
                    //do tricky string manipulation to get correct definition
                    computeSenorAndActutorNames(theProducersAllowed[i]);
                    Method definitionMethod = getOperationsMethod(theProducersAllowed[i]);
                    myOperations = invokeMethod(myActiveModule, definitionMethod);
                    return myOperations;
                }
            }
        }
        else if (destPort.getParent() instanceof ActiveNode){
            //we're consuming
            amProducerEdge = false;
            myActiveModule = ((ActiveNode)destPort.getParent()).getSimBioModuleImpl();
            PassiveNode thePassiveNode = (PassiveNode)(sourcePort.getParent());
            Class[] theConsumersAllowed = thePassiveNode.getConsumersAllowed();
            for (int i = 0; i < theConsumersAllowed.length; i++){
                if (theConsumersAllowed[i].isInstance(myActiveModule)){
                    //do tricky string manipulation to get correct definition
                    computeSenorAndActutorNames(theConsumersAllowed[i]);
                    Method definitionMethod = getOperationsMethod(theConsumersAllowed[i]);
                    myOperations = invokeMethod(myActiveModule, definitionMethod);
                    return myOperations;
                }
            }
        }
        return null;
    }

    /**
     * @param class1
     */
    private void computeSenorAndActutorNames(Class producerOrConsumerClass) {
        String className = producerOrConsumerClass.getSimpleName();
        String producerOrConsumerType = className.substring(0, className.lastIndexOf("Operations"));
        String sensorClassName = "";
        String actuatorClassName = "";
        
        //construct package names
        String consumerOrProducerPackageName = producerOrConsumerClass.getPackage().getName();
        String packageResourceName = consumerOrProducerPackageName.substring("com.traclabs.biosim.idl.simulation.".length());
        String sensorPackageName = "com.traclabs.biosim.server.sensor." + packageResourceName;
        String actuatorPackageName = "com.traclabs.biosim.server.actuator." + packageResourceName;
        
        //contruct class names
        if (producerOrConsumerType.contains("Producer")){
            String classResourceName = producerOrConsumerType.substring(0, producerOrConsumerType.lastIndexOf("Producer"));
            sensorClassName = sensorPackageName + "." + classResourceName + "OutFlowRateSensorImpl";
            actuatorClassName = actuatorPackageName + "." + classResourceName + "OutFlowRateActuatorImpl";
        }
        else{
            String classResourceName = producerOrConsumerType.substring(0, producerOrConsumerType.lastIndexOf("Consumer"));
            sensorClassName = sensorPackageName + "." + classResourceName + "InFlowRateSensorImpl";
            actuatorClassName = actuatorPackageName + "." + classResourceName + "InFlowRateActuatorImpl";
        }
        try {
            mySensorClass =  Class.forName(sensorClassName);
            myActuatorClass = Class.forName(actuatorClassName);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param theSimBioModuleImpl
     * @param definitionMethod
     * @return
     */
    private SingleFlowRateControllable invokeMethod(SimBioModuleImpl theSimBioModuleImpl, Method definitionMethod) {
    	SingleFlowRateControllable theFlowRateControllableOperations = null;
        try{
            theFlowRateControllableOperations = (SingleFlowRateControllable)(definitionMethod.invoke(theSimBioModuleImpl, null));
        }
        catch (IllegalAccessException e){
            myLogger.error("This shouldn't of happened, problem invoking method");
            e.printStackTrace();
        }
        catch (InvocationTargetException e){
            myLogger.error("This shouldn't of happened, problem invoking method");
            e.printStackTrace();
        }
        return theFlowRateControllableOperations;
    }

    /**
     * @param class1
     * @return
     */
    private Method getOperationsMethod(Class producerOrConsumerClass) {
        String className = producerOrConsumerClass.getSimpleName();
        String producerOrConsumerType = className.substring(0, className.lastIndexOf("Operations"));
        myName = producerOrConsumerType + FLOWRATE_STRING;
        String methodName = GET_STRING + producerOrConsumerType + DEFINITION_STRING;
        Method definitionMethod = null;
        try{
            definitionMethod = producerOrConsumerClass.getMethod(methodName, null);
        }
        catch (NoSuchMethodException e){
            myLogger.error("This shouldn't of happened, problem getting method");
            e.printStackTrace();
        }
        return definitionMethod;
    }

    /**
     * @return Returns whether edge is a producer.
     */
    public boolean isProducerEdge() {
        return amProducerEdge;
    }

    /**
     * 
     */
    public void addSensor() {
        Class[] constructorParameterTypes = {int.class, String.class};
        Constructor sensorConstructor = null;
        try {
            sensorConstructor = mySensorClass.getConstructor(constructorParameterTypes);
        } catch (SecurityException e) {
            e.printStackTrace();
            return;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }
        String sensorClassString = mySensorClass.getName();
        String idlSensorName = sensorClassString.substring(0, sensorClassString.indexOf("Impl"));
        Object[] constructorParameters = {myActiveModule.getModuleName() + idlSensorName, new Integer(0)};
        try {
            mySensorImpl = (GenericSensorImpl)(sensorConstructor.newInstance(constructorParameters));
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 
     */
    public void removeSensor() {
        mySensorImpl = null;
        
    }

    /**
     * 
     */
    public void addActuator() {
        Class[] constructorParameterTypes = {int.class, String.class};
        Constructor actuatorConstructor = null;
        try {
            actuatorConstructor = myActuatorClass.getConstructor(constructorParameterTypes);
        } catch (SecurityException e) {
            e.printStackTrace();
            return;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return;
        }
        String actuatorClassString = myActuatorClass.getName();
        String idlActuatorName = actuatorClassString.substring(0, actuatorClassString.indexOf("Impl"));
        Object[] constructorParameters = {myActiveModule.getModuleName() + idlActuatorName, new Integer(0)};
        try {
            myActuatorImpl = (GenericActuatorImpl)(actuatorConstructor.newInstance(constructorParameters));
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 
     */
    public void removeActuator() {
        myActuatorImpl = null;
        
    }
} /* end class EditorEdge */
