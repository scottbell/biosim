/*
 * Created on Feb 2, 2005
 */
package com.traclabs.biosim.server.editor;

import org.tigris.gef.presentation.Fig;

/**
 * @author scott
 */
public abstract class EditorFig {
    private static EditorFig myFactoryInstance;
    
    private EditorFig(){
        super();
        myFactoryInstance = this;
    }
    
    public abstract Fig createFig();
}
