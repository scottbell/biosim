package com.traclabs.biosim.client.simulation.power.schematic.graph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.presentation.FigEdge;

import com.traclabs.biosim.editor.graph.environment.SimEnvironmentNode;
import com.traclabs.biosim.idl.simulation.environment.StoreEnvironmentProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentImpl;
import com.traclabs.biosim.server.simulation.framework.PassiveModuleImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 */

public class ModuleEdge extends NetEdge {
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

    private String myFlowRateType;
    
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
    
    public PassiveModuleImpl getPassiveModuleImpl() {
        if (getStoreImpl() != null)
            return getStoreImpl();
		return getSimEnvironmentImpl();
    }
    
    /**
     * 
     */
    public void initializeFlowrates(Class selectedConsumerOrProducerClass){
        myOperations = initializeOperations(selectedConsumerOrProducerClass);
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
        if (sourcePort.getParent() instanceof StoreNode){
            //we're consuming
            myStoreImpl = (StoreImpl)((PassiveNode)sourcePort.getParent()).getSimBioModuleImpl();
            return myStoreImpl;
        }
        else if (destPort.getParent() instanceof StoreNode){
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
        if (sourcePort.getParent() instanceof SimEnvironmentNode){
        	mySimEnvironmentImpl = (SimEnvironmentImpl)((PassiveNode)sourcePort.getParent()).getSimBioModuleImpl();
            return mySimEnvironmentImpl;
        }
        else if (destPort.getParent() instanceof SimEnvironmentNode){
        	mySimEnvironmentImpl = (SimEnvironmentImpl)((PassiveNode)destPort.getParent()).getSimBioModuleImpl();
            return mySimEnvironmentImpl;
        }
        else
            return null;
    }
    
    public SingleFlowRateControllable getOperations(){
        return myOperations;
    }
    
    public String getFlowRateType(){
        return myFlowRateType;
    }

    /**
     * @return
     */
    private SingleFlowRateControllable initializeOperations(Class selectedConsumerOrProducerClass) {
        SingleFlowRateControllable initializedOperations = null;
        myFlowRateType = calculateFlowRateType(selectedConsumerOrProducerClass);
        EditorPort sourcePort = (EditorPort)getSourcePort();
        EditorPort destPort = (EditorPort)getDestPort();
        if (sourcePort.getParent() instanceof ActiveNode){
            //we're producing
            amProducerEdge = true;
            myActiveModule = ((ActiveNode)sourcePort.getParent()).getSimBioModuleImpl();
            //do tricky string manipulation to get correct definition
            computeSenorAndActutorNames(selectedConsumerOrProducerClass);
            Method definitionMethod = getOperationsMethod(selectedConsumerOrProducerClass);
            initializedOperations = invokeMethod(myActiveModule, definitionMethod);
            return initializedOperations;
        }
        else if (destPort.getParent() instanceof ActiveNode){
            //we're consuming
            amProducerEdge = false;
            myActiveModule = ((ActiveNode)destPort.getParent()).getSimBioModuleImpl();
            computeSenorAndActutorNames(selectedConsumerOrProducerClass);
            Method definitionMethod = getOperationsMethod(selectedConsumerOrProducerClass);
            initializedOperations = invokeMethod(myActiveModule, definitionMethod);
            return initializedOperations;
        }
        return initializedOperations;
    }

    /**
     * @param selectedConsumerOrProducerClass
     */
    private String calculateFlowRateType(Class selectedConsumerOrProducerClass) {
        String className = selectedConsumerOrProducerClass.getSimpleName();
        String producerOrConsumerType = className.substring(0, className.lastIndexOf("Operations"));
        if (producerOrConsumerType.startsWith("O2") || producerOrConsumerType.startsWith("CO2")
            || producerOrConsumerType.startsWith("H2"))
            return producerOrConsumerType;
        char firstLetterLowered = Character.toLowerCase(producerOrConsumerType.charAt(0));
        return firstLetterLowered + producerOrConsumerType.substring(1);
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
        
        String modifier = "";
        if (StoreEnvironmentProducerOperations.class.isAssignableFrom(producerOrConsumerClass)){
            if (getSimEnvironmentImpl() != null)
                modifier = "Environment";
            else
                modifier = "Store";
        }
        
        //contruct class names
        if (producerOrConsumerType.contains("Producer")){
            String classResourceName = producerOrConsumerType.substring(0, producerOrConsumerType.lastIndexOf("Producer"));
            
            sensorClassName = sensorPackageName + "." + classResourceName + modifier + "OutFlowRateSensorImpl";
            actuatorClassName = actuatorPackageName + "." + classResourceName + modifier + "OutFlowRateActuatorImpl";
        }
        else{
            String classResourceName = producerOrConsumerType.substring(0, producerOrConsumerType.lastIndexOf("Consumer"));
            sensorClassName = sensorPackageName + "." + classResourceName + modifier + "InFlowRateSensorImpl";
            actuatorClassName = actuatorPackageName + "." + classResourceName + modifier + "InFlowRateActuatorImpl";
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
            theFlowRateControllableOperations = (SingleFlowRateControllable)(definitionMethod.invoke(theSimBioModuleImpl, new Object[0]));
        }
        catch (IllegalAccessException e){
            myLogger.error("This shouldn't of happened, problem invoking method");
            e.printStackTrace();
        }
        catch (InvocationTargetException e){
            myLogger.error("This shouldn't of happened, problem invoking method");
            e.printStackTrace();
        }
        catch (java.lang.IllegalArgumentException e){
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
        myName = generateUserFriendlyResource(producerOrConsumerType) + " Flowrate";
        String methodName = "get" + producerOrConsumerType + "Definition";
        Method definitionMethod = null;
        try{
            definitionMethod = producerOrConsumerClass.getMethod(methodName, new Class[0]);
        }
        catch (NoSuchMethodException e){
            myLogger.error("This shouldn't of happened, problem getting method");
            e.printStackTrace();
        }
        return definitionMethod;
    }
    
	private String generateUserFriendlyResource(String resourceName) {
		int indexToAddSpace = resourceName.indexOf("Consumer");
		if (indexToAddSpace == -1)
			indexToAddSpace = resourceName.indexOf("Producer");
		String nicelySpaced = resourceName.substring(0, indexToAddSpace) + " "
				+ resourceName.substring(indexToAddSpace);
		return nicelySpaced;
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
        String sensorClassString = mySensorClass.getSimpleName();
        String idlSensorName = sensorClassString.substring(0, sensorClassString.indexOf("Impl"));
        Object[] constructorParameters = {new Integer(0), myActiveModule.getModuleName() + idlSensorName};
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
    
    public boolean isSensed(){
        return (mySensorImpl != null);
    }
    
    public GenericSensorImpl getSensorImpl(){
        return mySensorImpl;
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
        String actuatorClassString = myActuatorClass.getSimpleName();
        String idlActuatorName = actuatorClassString.substring(0, actuatorClassString.indexOf("Impl"));
        Object[] constructorParameters = {new Integer(0), myActiveModule.getModuleName() + idlActuatorName};
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
    
    public boolean isActuated(){
        return (myActuatorImpl != null);
    }
    
    public GenericActuatorImpl getActuatorImpl(){
        return myActuatorImpl;
    }

    /**
     * @return
     */
    public SimBioModuleImpl getActiveModule() {
        return myActiveModule;
    }
} /* end class EditorEdge */
