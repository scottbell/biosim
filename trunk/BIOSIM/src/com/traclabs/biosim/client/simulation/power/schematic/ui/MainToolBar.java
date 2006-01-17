
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import org.tigris.gef.base.CmdSetMode;
import org.tigris.gef.base.ModeBroom;
import org.tigris.gef.base.ModeSelect;

/**
 * @author scott
 *
 */
public class MainToolBar extends EditorToolBar {

    public MainToolBar() {
        super("Main");
        add(new CmdSetMode(ModeSelect.class, "Select"));
        add(new CmdSetMode(ModeBroom.class, "Broom"));
    }
}
