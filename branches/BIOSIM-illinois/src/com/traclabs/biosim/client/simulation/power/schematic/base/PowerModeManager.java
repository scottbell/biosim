package com.traclabs.biosim.client.simulation.power.schematic.base;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.ModeManager;

public class PowerModeManager extends ModeManager {

    Logger myLogger = Logger.getLogger(PowerModeManager.class);

    public PowerModeManager(Editor par) {
        super(par);
    }

    
}