package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.util.Vector;

import javax.swing.Action;
import javax.swing.ImageIcon;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleNode;

/**
 * Show the properties for the selected fig.
 */
public class EditorCmdShowProperties extends Cmd {

    public EditorCmdShowProperties() {
        super("EditorBase", "ShowProperties");
        putValue(Action.SHORT_DESCRIPTION,
                "Shows properties for the selected item");
    }

    public EditorCmdShowProperties(ImageIcon image) {
        super(null, "EditorBase", "ShowProperties", image);
    }

    public void doIt() {

        Editor editor = Globals.curEditor();

        Vector figs = editor.getSelectionManager().getFigs();
        if (figs.size() == 1) {
            Fig fig = (Fig) figs.firstElement();
            if (fig instanceof FigModuleNode) {
                //((ModuleFigNode) fig).showProperties(editor.findFrame());
            }
        }
    }

    public void undoIt() {
    }
}