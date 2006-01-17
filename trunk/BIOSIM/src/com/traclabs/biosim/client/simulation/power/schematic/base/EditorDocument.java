/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.util.Enumeration;
import java.util.Vector;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.client.simulation.power.schematic.graph.EditorGraphModel;
import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleNode;

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
    protected Vector<BiosimEditor> _editors = new Vector<BiosimEditor>();
 
    public EditorDocument() {
        this(new EditorLayer("Root"));
    }

    public EditorDocument(EditorLayer root) {
        setRoot(root);
    }

    public EditorDocument(EditorGraphModel model) {
        this(new EditorLayer(model));
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