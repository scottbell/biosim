/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.editor.base;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.LayerPerspective;
import org.tigris.gef.graph.GraphEvent;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.editor.graph.EditorGraphModel;
import com.traclabs.biosim.editor.graph.FigModuleNode;

public class EditorLayer extends LayerPerspective {
    FigModuleNode _parent = null;

    public EditorLayer(String name, EditorGraphModel gm, FigModuleNode parent) {
        super(name, gm);
        _parent = parent;
    }

    public EditorLayer(String name, EditorGraphModel gm) {
        this(name, gm, null);
    }

    public EditorLayer(EditorGraphModel gm) {
        this("Root", gm, null);
    }

    public EditorLayer(String name) {
        this(name, new EditorGraphModel());
    }

    public FigModuleNode getParent() {
        return _parent;
    }

    void setParentLayer(FigModuleNode parent) {
        _parent = parent;
    }

    public void nodeAdded(GraphEvent e) {
        super.nodeAdded(e);
        modifyDocument();
    }

    public void edgeAdded(GraphEvent e) {
        super.edgeAdded(e);
        modifyDocument();
    }

    public void nodeRemoved(GraphEvent e) {
        super.nodeRemoved(e);
        modifyDocument();
    }

    public void edgeRemoved(GraphEvent e) {
        super.edgeRemoved(e);
        modifyDocument();
    }

    public void graphChanged(GraphEvent e) {
        super.graphChanged(e);
        modifyDocument();
    }

    /*
     * deleted was overridden to fix the bug in the "Delete from Diagram"
     * command. The Figs were properly deleted from the layer but the Net
     * objects were being left in the GraphModel.
     */
    public void deleted(Fig f) {
        // Notify the associated EDITOR document that a fig has been
        // deleted. Any editor showing a child diagram for this fig
        // should switch to the root.
        // Assumes one layer can be displayed in several editors but
        // all of these editors share the same document.
        if (_editors != null) {
            Editor ed = (Editor) _editors.get(0);
            if (ed != null) {
                Object doc = ed.document();
                if (doc != null && doc instanceof EditorDocument) {
                    ((EditorDocument) doc).deleted(f);
                }
            }
        }

        super.deleted(f);
    }

    /**
     * Modify all documents associated with this layer.
     */
    public void modifyDocument() {
        Iterator i = _editors.iterator();
        while (i.hasNext()) {
            Editor ed = (Editor) i.next();
            if (ed instanceof BiosimEditor) {
                BiosimEditor ved = (BiosimEditor) ed;
                EditorDocument doc = (EditorDocument) ved.document();
                doc.setModified(true);
            }
        }
    }

    /**
     * Determines if this diagram is a descendant of the specified fig node
     */
    public boolean isDescendantDiagram(FigModuleNode node) {
        FigModuleNode parent = _parent;
        while (parent != null) {
            if (parent == node) {
                return true;
            }
            Layer layer = parent.getLayer();
            if (layer != null && layer instanceof EditorLayer) {
                parent = ((EditorLayer) layer).getParent();
            } else {
                return false;
            }
        }
        return false;
    }

    public void add(Fig f) {
        super.add(f);
        repaintParent();
    }

    public void remove(Fig f) {
        super.remove(f);
        repaintParent();
    }

    /**
     * Shows or hides the background rectangle that indicates whether the parent
     * node has content.
     */
    public void repaintParent() {
        if (_parent != null) {
            Rectangle rect = _parent.getHandleBox();
            int x = (int) rect.getX();
            int y = (int) rect.getY();
            int w = (int) rect.getWidth();
            int h = (int) rect.getHeight();
            //			_parent.startTrans();
            _parent.setHandleBox(x, y, w, h);
            _parent.endTrans();
        }
    }

    /**
     * Returns all fig nodes that do not have input connections.
     */
    public List findInputs() {
        List<FigModuleNode> result = new ArrayList<FigModuleNode>();
        Collection contents = getContents(null);

        Iterator i = contents.iterator();
        while (i.hasNext()) {
            Fig f = (Fig) i.next();
            if (f instanceof FigModuleNode) {
                FigModuleNode node = (FigModuleNode) f;
                if (node.getInputCount() == 0) {
                    result.add(node);
                }
            }
        }

        return result;
    }
}