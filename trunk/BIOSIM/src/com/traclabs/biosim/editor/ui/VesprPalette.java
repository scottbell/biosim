/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.editor.ui;

import org.tigris.gef.base.CmdCreateNode;
import org.tigris.gef.base.CmdSetMode;
import org.tigris.gef.base.ModeBroom;
import org.tigris.gef.base.ModeSelect;
import org.tigris.gef.ui.PaletteFig;

import com.traclabs.biosim.editor.base.CmdExpandNode;
import com.traclabs.biosim.editor.base.CmdNewVespr;
import com.traclabs.biosim.editor.base.CmdOpenVespr;
import com.traclabs.biosim.editor.base.CmdSaveVespr;
import com.traclabs.biosim.editor.base.CmdShowParent;
import com.traclabs.biosim.editor.base.CmdShowRoot;
import com.traclabs.biosim.editor.base.ModeZoom;
import com.traclabs.biosim.editor.graph.DecisionNode;
import com.traclabs.biosim.editor.graph.GoToNode;
import com.traclabs.biosim.editor.graph.OptionalNode;
import com.traclabs.biosim.editor.graph.RequiredNode;
import com.traclabs.biosim.editor.graph.TerminatorNode;

/**
 * A class to define a custom palette for use in some demos.
 * 
 * @see uci.gef.demo.FlexibleApplet
 * @see uci.gef.demo.BasicApplication
 */

public class VesprPalette extends PaletteFig {

    /** Construct a new palette of example nodes for the Example application */
    public VesprPalette() {
        super();
    }

    /** Define a button to make for the Example application */
    public void defineButtons() {
        //Our version of defineButtons
        add(new CmdSetMode(ModeSelect.class, "Select"));
        add(new CmdSetMode(ModeBroom.class, "Broom"));
        add(new CmdSetMode(ModeZoom.class, "Zoom"));
        addSeparator();
        add(new CmdNewVespr());
        add(new CmdSaveVespr());
        add(new CmdOpenVespr());

        addSeparator();

        add(new CmdCreateNode(RequiredNode.class, "VesprBase", "RequiredNode"));
        add(new CmdCreateNode(OptionalNode.class, "VesprBase", "OptionalNode"));
        add(new CmdCreateNode(DecisionNode.class, "VesprBase", "DecisionNode"));
        add(new CmdCreateNode(TerminatorNode.class, "VesprBase",
                "TerminatorNode"));
        add(new CmdCreateNode(GoToNode.class, "VesprBase", "GoToNode"));

        addSeparator();

        add(new CmdShowRoot());
        add(new CmdExpandNode());
        add(new CmdShowParent());
    }
}