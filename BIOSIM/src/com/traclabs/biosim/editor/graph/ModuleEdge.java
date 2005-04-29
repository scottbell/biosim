package com.traclabs.biosim.editor.graph;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.presentation.FigEdge;

import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.simulation.framework.Store;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations;
import com.traclabs.biosim.idl.simulation.framework.StoreHelper;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 */

public class ModuleEdge extends NetEdge {
    private final static String GET_STRING = "get";
    private final static String DEFINITION_STRING = "Definition";
    
    private GenericActuator actuator;
    private GenericSensor sensor;
    private int myIndex;
    private Logger myLogger;
    private StoreImpl myStoreImpl;
    private StoreFlowRateControllableOperations myOperations;
    
    /** Construct a new SampleEdge. */
    public ModuleEdge() {
        myLogger = Logger.getLogger(ModuleEdge.class);
    } 

    public String getId() {
        return toString();
    }
    
    public void setIndex(int pIndex){
        myIndex = pIndex;
    }
    
    public int getIndex(){
        return myIndex;
    }

    public FigEdge makePresentation(Layer lay) {
        return new ModuleFigEdge();
    }
    
    /**
     * 
     */
    public void initializeFlowrates(){
        getOperations();
        getStoreImpl();
        if (myOperations.getStores().length == 0){
            myStoreImpl = (StoreImpl)myStoreImpl;
            Object theObject = OrbUtils.poaToCorbaObj(myStoreImpl);
            Store theStore = (Store)myStoreImpl;
            Store[] storeArray = {(StoreHelper.narrow(OrbUtils.poaToCorbaObj(myStoreImpl)))};
            myOperations.setStores(storeArray);
        }
        else if (myOperations.getStores().length < myIndex + 1){
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
            myStoreImpl = (StoreImpl)((PassiveNode)sourcePort.getParent()).getSimBioModuleImpl();
            return myStoreImpl;
        }
        else if (destPort.getParent() instanceof PassiveNode){
            myStoreImpl = (StoreImpl)((PassiveNode)destPort.getParent()).getSimBioModuleImpl();
            return myStoreImpl;
        }
        else
            return null;
    }

    /**
     * @return
     */
    public StoreFlowRateControllableOperations getOperations() {
        if (myOperations != null)
            return myOperations;
        EditorPort sourcePort = (EditorPort)getSourcePort();
        EditorPort destPort = (EditorPort)getDestPort();
        if (sourcePort.getParent() instanceof ActiveNode){
            //we're producing
            SimBioModuleImpl theSimBioModuleImpl = ((ActiveNode)sourcePort.getParent()).getSimBioModuleImpl();
            PassiveNode thePassiveNode = (PassiveNode)(destPort.getParent());
            Class[] theProducersAllowed = thePassiveNode.getProducersAllowed();
            for (int i = 0; i < theProducersAllowed.length; i++){
                if (theProducersAllowed[i].isInstance(theSimBioModuleImpl)){
                    //do tricky string manipulation to get correct definition
                    Method definitionMethod = getOperationsMethod(theProducersAllowed[i]);
                    myOperations = invokeMethod(theSimBioModuleImpl, definitionMethod);
                    return myOperations;
                }
            }
        }
        else if (destPort.getParent() instanceof ActiveNode){
            //we're consuming
            SimBioModuleImpl theSimBioModuleImpl = ((ActiveNode)destPort.getParent()).getSimBioModuleImpl();
            PassiveNode thePassiveNode = (PassiveNode)(sourcePort.getParent());
            Class[] theConsumersAllowed = thePassiveNode.getConsumersAllowed();
            for (int i = 0; i < theConsumersAllowed.length; i++){
                if (theConsumersAllowed[i].isInstance(theSimBioModuleImpl)){
                    //do tricky string manipulation to get correct definition
                    Method definitionMethod = getOperationsMethod(theConsumersAllowed[i]);
                    myOperations = invokeMethod(theSimBioModuleImpl, definitionMethod);
                    return myOperations;
                }
            }
        }
        return null;
    }

    /**
     * @param theSimBioModuleImpl
     * @param definitionMethod
     * @return
     */
    private StoreFlowRateControllableOperations invokeMethod(SimBioModuleImpl theSimBioModuleImpl, Method definitionMethod) {
        StoreFlowRateControllableOperations theStoreFlowRateControllableOperations = null;
        try{
            theStoreFlowRateControllableOperations = (StoreFlowRateControllableOperations)(definitionMethod.invoke(theSimBioModuleImpl, null));
        }
        catch (IllegalAccessException e){
            myLogger.error("This shouldn't of happened, problem invoking method");
            e.printStackTrace();
        }
        catch (InvocationTargetException e){
            myLogger.error("This shouldn't of happened, problem invoking method");
            e.printStackTrace();
        }
        return theStoreFlowRateControllableOperations;
    }

    /**
     * @param class1
     * @return
     */
    private Method getOperationsMethod(Class producerOrConsumerClass) {
        String className = producerOrConsumerClass.getSimpleName();
        String producerType = className.substring(0, className.lastIndexOf("Operations"));
        String methodName = GET_STRING + producerType + DEFINITION_STRING;
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

} /* end class EditorEdge */
