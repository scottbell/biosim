package com.traclabs.biosim.server.framework;

import ch.qos.logback.classic.Level;
import com.traclabs.biosim.server.util.failure.FailureDecider;
import com.traclabs.biosim.server.util.stochastic.StochasticFilter;

public interface IBioModule {

    void tick();

    int getMyTicks();

    String[] getMalfunctionNames();

    Malfunction[] getMalfunctions();

    void doSomeRepairWork(long malfunctionID);

    Malfunction startMalfunction(MalfunctionIntensity pIntensity, MalfunctionLength pLength);

    void scheduleMalfunction(MalfunctionIntensity pIntensity, MalfunctionLength pLength, int tickToOccur);

    void fixMalfunction(long pID);

    void clearMalfunction(long malfunctionID);

    boolean isMalfunctioning();

    void fixAllMalfunctions();

    void clearAllMalfunctions();

    void log();

    int getID();

    void setEnableFailure(boolean pValue);

    boolean isFailureEnabled();

    void maintain();

    void setLogLevel(Level level);

    float randomFilter(float value);

    StochasticFilter getStochasticFilter();

    void setStochasticFilter(StochasticFilter filter);

    String getModuleName();

    void setModuleName(String pName);

    float getTickLength();

    void setTickLength(float pInterval);

    void setFailureDecider(FailureDecider decider);

    void reset();
}