package com.traclabs.biosim.server.simulation.framework;

public interface IStore {
    
    boolean isPipe();
    
    void setPipe(boolean pPipe);
    
    void setResupply(int pResupplyFrequency, float pResupplyAmount);
    
    void setInitialCapacity(float metricAmount);
    
    void setCurrentCapacity(float metricAmount);
    
    void setCurrentLevel(float metricAmount);
    
    void setInitialLevel(float metricAmount);
    
    void tick();
    
    float add(float amountRequested);
    
    float take(float amountRequested);
    
    float getCurrentLevel();
    
    float getOverflow();
    
    float getCurrentCapacity();
    
    void reset();
    
    float getInitialCapacity();
    
    float getInitialLevel();
    
    float getPercentageFilled();
    
    void log();
}