package com.traclabs.biosim.server.simulation.framework;

public interface IStore {

    boolean isPipe();

    void setPipe(boolean pPipe);

    void setResupply(int pResupplyFrequency, float pResupplyAmount);

    void tick();

    float add(float amountRequested);

    float take(float amountRequested);

    float getCurrentLevel();

    void setCurrentLevel(float metricAmount);

    float getOverflow();

    float getCurrentCapacity();

    void setCurrentCapacity(float metricAmount);

    void reset();

    float getInitialCapacity();

    void setInitialCapacity(float metricAmount);

    float getInitialLevel();

    void setInitialLevel(float metricAmount);

    float getPercentageFilled();

    void log();

    String getModuleName();
}