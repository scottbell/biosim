/*
 * Created on Feb 14, 2005
 */
package com.traclabs.biosim.client.simulation.power.schematic.graph;

import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.client.simulation.power.schematic.presentation.FigPanel;

/**
 * Adds a panel for nodes that required more complex layout strategies.
 * 
 * @author kkusy
 */
public abstract class FigPanelModuleNode extends FigModuleNode {
    protected Fig createBgFig() {
        return new FigPanel();
    }

    public FigPanel getPanel() {
        return (FigPanel) _bgFig;
    }
}