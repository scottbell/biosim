/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.server.editor.base;

import java.awt.event.MouseEvent;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.ModeDragScroll;
import org.tigris.gef.base.ModeSelect;

import com.traclabs.biosim.server.editor.graph.VesprFigNode;
import com.traclabs.biosim.server.editor.graph.VesprGraphModel;

/**
 * VesprEditor supports the nesting of graphs within nodes. VesprEditor simply
 * keeps track of the layer at the root of the tree. Each VesprFigNode has a
 * child layer (VesprLayer) associated with it that visually represents the
 * nested contents of the node.
 */
public class VesprEditor extends Editor {

    public VesprEditor() {
        this(new VesprLayer("Root"));
    }

    public VesprEditor(VesprGraphModel gm) {
        this(new VesprLayer("Root", gm));
    }

    public VesprEditor(VesprLayer lay) {
        this(new VesprDocument(lay));
    }

    public VesprEditor(VesprDocument doc) {
        super(doc.getRoot().getGraphModel(), null, doc.getRoot());

        doc.addEditor(this);
        super.document(doc);

        _selectionManager = new VesprSelectionManager(this);
        _modeManager = new VesprModeManager(this);

        pushMode(new ModeSelect(this));
        pushMode(new ModeDragScroll(this));
    }

    protected VesprEditor(VesprEditor ed) {
        this((VesprDocument) ed.document());

        getLayerManager().replaceActiveLayer(
                ed.getLayerManager().getActiveLayer());
    }

    public void document(Object d) {
        VesprDocument oldDoc = (VesprDocument) document();

        if (oldDoc != null) {
            oldDoc.removeEditor(this);
        }

        if (d != null && d instanceof VesprDocument) {
            VesprDocument newDoc = (VesprDocument) d;
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
    public VesprLayer getRoot() {
        VesprDocument vd = (VesprDocument) _document;
        return vd.getRoot();
    }

    public void showRoot() {
        // Switch out the layer that is being displayed.
        VesprDocument vd = (VesprDocument) _document;
        getLayerManager().replaceActiveLayer(vd.getRoot());
        getSelectionManager().deselectAll();
        damageAll();
    }

    public void showParent() {
        VesprLayer layer = (VesprLayer) getLayerManager().getActiveLayer();
        VesprFigNode figNode = layer.getParent();
        // Check for root.
        if (figNode != null) {
            layer = (VesprLayer) figNode.getLayer();
            getLayerManager().replaceActiveLayer(layer);
            getSelectionManager().deselectAll();
            damageAll();
        }
    }

    public void expandNode(VesprFigNode figNode) {
        if (figNode != null && figNode.getNestedLayer() != null) {
            VesprLayer layer = (VesprLayer) figNode.getNestedLayer();
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
        VesprEditor ed = new VesprEditor((VesprDocument) document());
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