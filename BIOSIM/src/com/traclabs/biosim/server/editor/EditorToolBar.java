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

    public EditorToolBar() {
        this("No Name");
    }

    public EditorToolBar(String pName) {
        super(pName);
        setFloatable(false);
    }

    public void registerEditor(BiosimEditor pEditor) {
        myEditor = pEditor;
    }

    protected void notfifyEditor(EditorFig pFigFactory) {
        myEditor.setCurrentFig(pFigFactory);
    }
}