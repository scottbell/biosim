package com.traclabs.biosim.server.editor;

import org.tigris.gef.base.CmdSetMode;
import org.tigris.gef.base.ModeCreateFigCircle;
import org.tigris.gef.base.ModeSelect;
import org.tigris.gef.ui.ToolBar;

public class EditorToolBar extends ToolBar {

    public EditorToolBar(String pName) {
        defineButtons();
    }

    private void defineButtons() {
        add(new CmdSetMode(ModeSelect.class, "Select"));
        addSeparator();
        add(new CmdSetMode(ModeCreateFigCircle.class, "Circle"));
    }
}