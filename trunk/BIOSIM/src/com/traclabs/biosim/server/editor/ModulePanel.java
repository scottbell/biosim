/*
 * Created on Jan 26, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.editor;

import javax.swing.JPanel;

import org.tigris.gef.presentation.Fig;

/**
 * @author scott
 *
 * TODO
 */
public abstract class ModulePanel extends JPanel {
    private BiosimEditor myEditor;
    
    public void registerEditor(BiosimEditor pEditor){
        myEditor = pEditor;
    }
    
    protected void notfifyEditor(Fig pFigure){
        myEditor.setCurrentFigure(pFigure);
    }
}
