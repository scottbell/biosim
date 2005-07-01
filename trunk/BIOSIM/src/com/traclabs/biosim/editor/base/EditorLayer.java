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

import org.tigris.gef.base.Layer;
import org.tigris.gef.base.LayerPerspective;
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