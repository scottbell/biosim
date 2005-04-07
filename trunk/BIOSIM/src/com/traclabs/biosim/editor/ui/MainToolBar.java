
package com.traclabs.biosim.editor.ui;

import org.tigris.gef.base.CmdSetMode;
import org.tigris.gef.base.ModeBroom;
import org.tigris.gef.base.ModeSelect;

import com.traclabs.biosim.editor.base.ModeZoom;

/**
 * @author scott
 *
 */
public class MainToolBar extends EditorToolBar {

    public MainToolBar() {
        super("Main");
        add(new CmdSetMode(ModeSelect.class, "Select"));
        add(new CmdSetMode(ModeBroom.class, "Broom"));
        add(new CmdSetMode(ModeZoom.class, "Zoom"));
    }
}
