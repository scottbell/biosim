/*
 * Created on Feb 23, 2005
 */
package com.traclabs.biosim.editor.xml;

import java.io.Reader;

import com.traclabs.biosim.editor.base.BiosimEditor;
import com.traclabs.biosim.editor.base.EditorDocument;

/**
 * @author kkusy
 */
public interface DocumentReader {
    /** Reads items from the Reader and adds them to the specified document. */
    void openDocument(Reader in, EditorDocument doc) throws Exception;

    /**
     * Reads items from the Reader and adds them to the active layer in
     * specified the editor. Used for copy command.
     */
    void pasteSelections(Reader in, BiosimEditor ed) throws Exception;
}