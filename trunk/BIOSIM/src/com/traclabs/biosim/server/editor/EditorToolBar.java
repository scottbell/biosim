/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.server.editor;

import javax.swing.JToolBar;

/**
 * @author scott
 *  
 */
public abstract class EditorToolBar extends JToolBar {
    private BiosimEditor myEditor;

    public EditorToolBar(BiosimEditor pEditor) {
        this("No Name", pEditor);
    }

    public EditorToolBar(String pName, BiosimEditor pEditor) {
        super(pName);
        setFloatable(false);
        registerEditor(pEditor);
    }

    public void registerEditor(BiosimEditor pEditor) {
        myEditor = pEditor;
    }

    protected void notfifyEditor(EditorFig pFig) {
        myEditor.setCurrentFig(pFig);
    }
}