package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.Store;
import com.traclabs.biosim.idl.simulation.framework.StoreEnvironmentFlowRateControllableOperations;

/**
 * @author Scott Bell
 */

public abstract class StoreEnvironmentFlowRateControllableImpl extends SingleFlowRateControllableImpl implements StoreEnvironmentFlowRateControllableOperations {
    private SimEnvironment[] mySimEnvironment;
    private Store[] myStores;
    
    private float[] myStoreMaxFlowRates;

    private float[] myStoreActualFlowRates;

    private float[] myStoreDesiredFlowRates;
    
    private float[] myEnvironmentMaxFlowRates;

    private float[] myEnvironmentActualFlowRates;

    private float[] myEnvironmentDesiredFlowRates;
    
    public StoreEnvironmentFlowRateControllableImpl(){
        myStoreMaxFlowRates = new float[0];
        myStoreMaxFlowRates = new float[0];
        myStoreDesiredFlowRates = new float[0];
        myEnvironmentMaxFlowRates = new float[0];
        myEnvironmentActualFlowRates = new float[0];
        myEnvironmentDesiredFlowRates = new float[0];
    }
    
    //Stores
    public Store[] getStores() {
       return myStores;
    }
    
    protected void setStores(Store[] pStores){
        myStores = pStores;
    }
    
    public void setStoreMaxFlowRate(float value, int index) {
        myStoreMaxFlowRates[index] = value;
    }
    
    public float getStoreMaxFlowRate(int index) {
        return myStoreMaxFlowRates[index];
    }

    public void setStoreDesiredFlowRate(float value, int index) {
        myStoreDesiredFlowRates[index] = value;
    }

    public float getStoreDesiredFlowRate(int index) {
        return myStoreDesiredFlowRates[index];
    }

    public float getStoreActualFlowRate(int index) {
        return myStoreActualFlowRates[index];
    }
    
    public float[] getStoreMaxFlowRates() {
        return myStoreMaxFlowRates;
    }
    
    public float[] getStoreDesiredFlowRates() {
        return myStoreDesiredFlowRates;
    }

    public float[] getStoreActualFlowRates() {
        return myStoreActualFlowRates;
    }
    
    protected void setStoreMaxFlowRates(float[] pStoreMaxFlowRates) {
        myStoreMaxFlowRates = pStoreMaxFlowRates;
    }
    
    protected void setStoreDesiredFlowRates(float[] pStoreDesiredFlowRates) {
        myStoreDesiredFlowRates = pStoreDesiredFlowRates;
    }

    protected void setStoreActualFlowRates(float[] pStoreActualFlowRates) {
        myStoreActualFlowRates = pStoreActualFlowRates;
    }
    
    //Environments
    public SimEnvironment[] getEnvironments() {
       return mySimEnvironment;
    }
    
    protected void setEnvironments(SimEnvironment[] pSimEnvironment){
        mySimEnvironment = pSimEnvironment;
    }
    
    public void setEnvironmentMaxFlowRate(float value, int index) {
        myEnvironmentMaxFlowRates[index] = value;
    }
    
    public float getEnvironmentMaxFlowRate(int index) {
        return myEnvironmentMaxFlowRates[index];
    }

    public void setEnvironmentDesiredFlowRate(float value, int index) {
        myEnvironmentDesiredFlowRates[index] = value;
    }

    public float getEnvironmentDesiredFlowRate(int index) {
        return myEnvironmentDesiredFlowRates[index];
    }

    public float getEnvironmentActualFlowRate(int index) {
        return myEnvironmentActualFlowRates[index];
    }
    
    public float[] getEnvironmentMaxFlowRates() {
        return myEnvironmentMaxFlowRates;
    }
    
    public float[] getEnvironmentDesiredFlowRates() {
        return myEnvironmentDesiredFlowRates;
    }

    public float[] getEnvironmentActualFlowRates() {
        return myEnvironmentActualFlowRates;
    }
    
    protected void setEnvironmentMaxFlowRates(float[] pEnvironmentMaxFlowRates) {
        myEnvironmentMaxFlowRates = pEnvironmentMaxFlowRates;
    }
    
    protected void setEnvironmentDesiredFlowRates(float[] pEnvironmentDesiredFlowRates) {
        myEnvironmentDesiredFlowRates = pEnvironmentDesiredFlowRates;
    }

    protected void setEnvironmentActualFlowRates(float[] pEnvironmentActualFlowRates) {
        myEnvironmentActualFlowRates = pEnvironmentActualFlowRates;
    }
}