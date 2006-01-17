/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * Created on Dec 1, 2003
 */
package com.traclabs.biosim.client.simulation.power.schematic.presentation;

import java.awt.Dimension;

import org.tigris.gef.presentation.Fig;

/**
 * GefLayoutManager manages layout of figs in a FigContainer.
 * 
 * @author kkusy
 */
public interface GEFLayoutManager {
    void layoutContainer(FigPanel parent);

    void addLayoutFig(Fig fig, Object constraints);

    Dimension minimumLayoutSize(FigPanel parent);

    Dimension preferredLayoutSize(FigPanel parent);

    void removeLayoutFig(Fig fig);
}