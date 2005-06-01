/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.editor.base;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Vector;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.editor.graph.EditorGraphModel;
import com.traclabs.biosim.editor.graph.FigModuleNode;
import com.traclabs.biosim.editor.xml.EditorParser;
import com.traclabs.biosim.editor.xml.EditorWriter;

/**
 * EditorDocument represents a document which contains a hierachy of nested editor
 * diagrams that can be saved to a file and opened to an editor for
 * modification. This class is used with the New, Open, and Save operations to
 * track modifications to the document. It is also used to store the filename
 * for the document (so that the user does not have to be prompted for a name at
 * each save).
 * 
 * @author Kevin Kusy
 */

public class EditorDocument {
    /** The layer at the root of the hierarchy */
    protected EditorLayer _root = null;

    /** Flag indicating whether the document has been modified */
    protected boolean _modifiedFlag = false;

    /** The filename for the document set when it is opened or saved */
    protected Vector _editors = new Vector();
    
    private EditorWriter myEditorWriter;

    /** The url for the document set when it is opened or saved */
    File _file;

    /** Reader for this document. */
    EditorParser _reader;

    /** Writer for this document. */
    EditorWriter _writer;

    public EditorDocument() {
        this(new EditorLayer("Root"));
    }

    public EditorDocument(EditorLayer root, File file) {
        this(root);
        setFile(file);
    }

    public EditorDocument(EditorLayer root) {
        setRoot(root);
        _reader = createReader();
        myEditorWriter = new EditorWriter(this);
    }

    public EditorDocument(EditorGraphModel model) {
        this(new EditorLayer(model));
    }

    public EditorDocument(EditorGraphModel model, File file) {
        this(new EditorLayer(model), file);
    }

    private void setRoot(EditorLayer root) {
        _root = root;
    }

    public EditorLayer getRoot() {
        return _root;
    }

    public boolean getModified() {
        return _modifiedFlag;
    }

    public void setModified(boolean modified) {
        _modifiedFlag = modified;
    }

    public File getFile() {
        return _file;
    }

    public void setFile(File file) {
        _file = file;
        updateTitles();
    }

    public void updateTitles() {
        String title = "";
        if (_file != null) {
            title = _file.getPath();
        }
        for (Enumeration e = _editors.elements(); e.hasMoreElements();) {
            BiosimEditor ed = (BiosimEditor) e.nextElement();
            Frame frame = ed.findFrame();
            if (frame != null) {
                frame.setTitle(getAppName() + " - " + title);
            }
        }
    }

    public Vector getEditors() {
        return _editors;
    }

    public void addEditor(BiosimEditor e) {
        _editors.remove(e);
        _editors.add(e);
    }

    public void removeEditor(BiosimEditor e) {
        _editors.remove(e);
    }

    /**
     * Any editor showing a child diagram for this Fig will be switched to the
     * root layer which cannot be deleted.
     */
    public void deleted(Fig f) {
        if (f instanceof FigModuleNode) {
            FigModuleNode node = (FigModuleNode) f;
            Enumeration eds = _editors.elements();
            while (eds.hasMoreElements()) {
                BiosimEditor ed = (BiosimEditor) eds.nextElement();
                Layer layer = ed.getLayerManager().getActiveLayer();
                if (layer instanceof EditorLayer) {
                    if (((EditorLayer) layer).isDescendantDiagram(node)) {
                        ed.showRoot();
                    }
                }
            }
        }
    }

    public SimpleFileFilter getFileFilter() {
        String[] exts = { "xml" };
        return new SimpleFileFilter(exts, "Biosim Configuration Files (*.xml)");
    }

    public String getAppName() {
        return "Biosim Editor";
    }

    public EditorParser createReader() {
        return EditorParser.SINGLETON;
    }

    /**
     * Saves the document to the specified file starting at the root diagram.
     */
    public void saveDocument(File file) throws Exception {
        onSaveDocument(file);
        setFile(file); // assign the filename
        setModified(false);
    }

    protected void onSaveDocument(File file){
        myEditorWriter.saveDocument(file);
    }

    public void copySelections(File file, BiosimEditor editor){
        _writer.copySelections(file, editor);
    }

    public void openDocument(File file) throws Exception {
        onOpenDocument(file);
        setFile(file);
        setModified(false);
    }

    protected void onOpenDocument(File file) throws Exception {
        Reader in = new BufferedReader(new FileReader(file));
        _reader.openDocument(in, this);
    }

    public void pasteSelections(File file, BiosimEditor editor) throws Exception {
        Reader in = new BufferedReader(new FileReader(file));
        _reader.pasteSelections(in, editor);
    }

    /** Generate the document tag bases on application name. */
    public String getDocumentTag() {
        String appName = getAppName();
        StringBuffer buf = new StringBuffer(appName.length());
        for (int i = 0; i < appName.length(); i++) {
            char ch = appName.charAt(i);
            if (Character.isLetterOrDigit(ch) || ch == '-') {
                buf.append(ch);
            } else {
                buf.append('_');
            }
        }
        return buf.toString();
    }
}