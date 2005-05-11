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
package com.traclabs.biosim.editor.xml;

import java.io.Writer;

import com.traclabs.biosim.editor.base.BiosimEditor;
import com.traclabs.biosim.editor.base.EditorDocument;

/**
 * @author kkusy
 */
public interface DocumentWriter {
    /** Writes the specified document to the Writer. */
    void saveDocument(Writer w, EditorDocument doc) throws Exception;

    /**
     * Writes the selected items in the editor to the Writer. Used for paste
     * command.
     */
    void copySelections(Writer w, BiosimEditor ed) throws Exception;
}