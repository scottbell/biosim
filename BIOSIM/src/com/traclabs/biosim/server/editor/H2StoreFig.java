/*
 * Created on Feb 3, 2005
 *
 */
package com.traclabs.biosim.server.editor;

import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigRect;

/**
 * @author scott
 *
 */
public class H2StoreFig extends EditorFig {
    
    private H2StoreFig(){
        super();
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.server.editor.EditorFig#createFig()
     */
    public Fig createFig() {
        return new FigRect(0, 0, 50, 50);
    }

}
