/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */

/*
 * Created on Mar 11, 2005
 */
package com.traclabs.biosim.server.editor.xml;

import java.io.Writer;

import com.traclabs.biosim.server.editor.base.VesprDocument;
import com.traclabs.biosim.server.editor.base.VesprEditor;

/**
 * @author kkusy
 */
public interface DocumentWriter {
    /** Writes the specified document to the Writer. */
    void saveDocument(Writer w, VesprDocument doc) throws Exception;

    /**
     * Writes the selected items in the editor to the Writer. Used for paste
     * command.
     */
    void copySelections(Writer w, VesprEditor ed) throws Exception;
}