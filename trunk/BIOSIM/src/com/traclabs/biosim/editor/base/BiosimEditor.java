/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.editor.base;

import java.awt.event.MouseEvent;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.ModeDragScroll;
import org.tigris.gef.base.ModeSelect;

import com.traclabs.biosim.editor.graph.EditorFigNode;
import com.traclabs.biosim.editor.graph.EditorGraphModel;

/**
 * VesprEditor supports the nesting of graphs within nodes. VesprEditor simply
 * keeps track of the layer at the root of the tree. Each VesprFigNode has a
 * child layer (VesprLayer) associated with it that visually represents the
 * nested contents of the node.
 */
public class BiosimEditor extends Editor {

    public BiosimEditor() {
        this(new EditorLayer("Root"));
    }

    public BiosimEditor(EditorGraphModel gm) {
        this(new EditorLayer("Root", gm));
    }

    public BiosimEditor(EditorLayer lay) {
        this(new EditorDocument(lay));
    }

    public BiosimEditor(EditorDocument doc) {
        super(doc.getRoot().getGraphModel(), null, doc.getRoot());

        doc.addEditor(this);
        super.document(doc);

        _selectionManager = new EditorSelectionManager(this);
        _modeManager = new EditorModeManager(this);

        pushMode(new ModeSelect(this));
        pushMode(new ModeDragScroll(this));
    }

    protected BiosimEditor(BiosimEditor ed) {
        this((EditorDocument) ed.document());

        getLayerManager().replaceActiveLayer(
                ed.getLayerManager().getActiveLayer());
    }

    public void document(Object d) {
        EditorDocument oldDoc = (EditorDocument) document();

        if (oldDoc != null) {
            oldDoc.removeEditor(this);
        }

        if (d != null && d instanceof EditorDocument) {
            EditorDocument newDoc = (EditorDocument) d;
            newDoc.addEditor(this);
            newDoc.updateTitles();
            super.document(newDoc);
            showRoot();
        } else {
            super.document(null);
        }
    }

    /**
     * Returns the root of the layer tree.
     */
    public EditorLayer getRoot() {
        EditorDocument vd = (EditorDocument) _document;
        return vd.getRoot();
    }

    public void showRoot() {
        // Switch out the layer that is being displayed.
        EditorDocument vd = (EditorDocument) _document;
        getLayerManager().replaceActiveLayer(vd.getRoot());
        getSelectionManager().deselectAll();
        damageAll();
    }

    public void showParent() {
        EditorLayer layer = (EditorLayer) getLayerManager().getActiveLayer();
        EditorFigNode figNode = layer.getParent();
        // Check for root.
        if (figNode != null) {
            layer = (EditorLayer) figNode.getLayer();
            getLayerManager().replaceActiveLayer(layer);
            getSelectionManager().deselectAll();
            damageAll();
        }
    }

    public void expandNode(EditorFigNode figNode) {
        if (figNode != null && figNode.getNestedLayer() != null) {
            EditorLayer layer = (EditorLayer) figNode.getNestedLayer();
            getLayerManager().replaceActiveLayer(layer);
            getSelectionManager().deselectAll();
            damageAll();
        }
    }

    // public VesprEditor(GraphModel gm, Component awt_comp, Layer lay) {
    // 	super(gm, awt_comp, lay);
    // 	System.out.println("Creating Vespr Editor");
    // }

    /**
     * Clone the receiving editor. Called from VesprCmdSpawn. Subclasses of
     * Editor should override this method.
     */
    public Object clone() {
        // Create a new editor with its own layer manager, selection manager,
        // and mode manager.
        BiosimEditor ed = new BiosimEditor((EditorDocument) document());
        return ed;
    }

    /** Invoked when the mouse exits the Editor. */
    public void mouseExited(MouseEvent me) {
        super.mouseExited(me);
        if (_canSelectElements) {
            _modeManager.mouseExited(me);
        }
    }
}