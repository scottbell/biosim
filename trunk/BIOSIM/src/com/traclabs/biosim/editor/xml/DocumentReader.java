/*
 * Created on Feb 23, 2005
 */
package com.traclabs.biosim.server.editor.xml;

import java.io.Reader;

import com.traclabs.biosim.server.editor.base.VesprDocument;
import com.traclabs.biosim.server.editor.base.VesprEditor;

/**
 * @author kkusy
 */
public interface DocumentReader {
    /** Reads items from the Reader and adds them to the specified document. */
    void openDocument(Reader in, VesprDocument doc) throws Exception;

    /**
     * Reads items from the Reader and adds them to the active layer in
     * specified the editor. Used for copy command.
     */
    void pasteSelections(Reader in, VesprEditor ed) throws Exception;
}