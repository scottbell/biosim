/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.server.editor.base;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.server.editor.graph.VesprFigNode;
import com.traclabs.biosim.server.editor.graph.VesprGraphModel;
import com.traclabs.biosim.server.editor.xml.DocumentReader;
import com.traclabs.biosim.server.editor.xml.DocumentWriter;
import com.traclabs.biosim.server.editor.xml.VesprParser;
import com.traclabs.biosim.server.editor.xml.VesprWriter;

/**
 * VesprDocument represents a document which contains a hierachy of nested vespr
 * diagrams that can be saved to a file and opened to an editor for
 * modification. This class is used with the New, Open, and Save operations to
 * track modifications to the document. It is also used to store the filename
 * for the document (so that the user does not have to be prompted for a name at
 * each save).
 * 
 * @author Kevin Kusy
 */

public class VesprDocument {
    /** The layer at the root of the hierarchy */
    protected VesprLayer _root = null;

    /** Flag indicating whether the document has been modified */
    protected boolean _modifiedFlag = false;

    /** The filename for the document set when it is opened or saved */
    protected Vector _editors = new Vector();

    /** The url for the document set when it is opened or saved */
    File _file;

    /** Reader for this document. */
    DocumentReader _reader;

    /** Writer for this document. */
    DocumentWriter _writer;

    public VesprDocument() {
        this(new VesprLayer("Root"));
    }

    public VesprDocument(VesprLayer root, File file) {
        this(root);
        setFile(file);
    }

    public VesprDocument(VesprLayer root) {
        setRoot(root);
        _reader = createReader();
        _writer = createWriter();
    }

    public VesprDocument(VesprGraphModel model) {
        this(new VesprLayer(model));
    }

    public VesprDocument(VesprGraphModel model, File file) {
        this(new VesprLayer(model), file);
    }

    private void setRoot(VesprLayer root) {
        _root = root;
    }

    public VesprLayer getRoot() {
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
            VesprEditor ed = (VesprEditor) e.nextElement();
            Frame frame = ed.findFrame();
            if (frame != null) {
                frame.setTitle(getAppName() + " - " + title);
            }
        }
    }

    public Vector getEditors() {
        return _editors;
    }

    void addEditor(VesprEditor e) {
        _editors.remove(e);
        _editors.add(e);
    }

    void removeEditor(VesprEditor e) {
        _editors.remove(e);
    }

    /**
     * Any editor showing a child diagram for this Fig will be switched to the
     * root layer which cannot be deleted.
     */
    void deleted(Fig f) {
        if (f instanceof VesprFigNode) {
            VesprFigNode node = (VesprFigNode) f;
            Enumeration eds = _editors.elements();
            while (eds.hasMoreElements()) {
                VesprEditor ed = (VesprEditor) eds.nextElement();
                Layer layer = ed.getLayerManager().getActiveLayer();
                if (layer instanceof VesprLayer) {
                    if (((VesprLayer) layer).isDescendantDiagram(node)) {
                        ed.showRoot();
                    }
                }
            }
        }
    }

    public SimpleFileFilter getFileFilter() {
        String[] exts = { "vspr" };
        return new SimpleFileFilter(exts, "VESPR Files (*.vspr)");
    }

    public String getAppName() {
        return "VESPR";
    }

    public DocumentReader createReader() {
        return VesprParser.SINGLETON;
    }

    public DocumentWriter createWriter() {
        return VesprWriter.SINGLETON;
    }

    /**
     * Saves the document to the specified file starting at the root diagram.
     */
    void saveDocument(File file) throws Exception {
        onSaveDocument(file);
        setFile(file); // assign the filename
        setModified(false);
    }

    protected void onSaveDocument(File file) throws Exception {
        Writer out = new BufferedWriter(new FileWriter(file));
        _writer.saveDocument(out, this);
    }

    void copySelections(File file, VesprEditor editor) throws Exception {
        Writer out = new BufferedWriter(new FileWriter(file));
        _writer.copySelections(out, editor);
    }

    void openDocument(File file) throws Exception {
        onOpenDocument(file);
        setFile(file);
        setModified(false);
    }

    protected void onOpenDocument(File file) throws Exception {
        Reader in = new BufferedReader(new FileReader(file));
        _reader.openDocument(in, this);
    }

    void pasteSelections(File file, VesprEditor editor) throws Exception {
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