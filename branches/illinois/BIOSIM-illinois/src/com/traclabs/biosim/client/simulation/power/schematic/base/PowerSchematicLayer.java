package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.tigris.gef.base.Layer;
import org.tigris.gef.base.LayerPerspective;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.client.simulation.power.schematic.graph.EditorGraphModel;
import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleNode;

public class PowerSchematicLayer extends LayerPerspective {
    FigModuleNode _parent = null;

    public PowerSchematicLayer(String name, EditorGraphModel gm, FigModuleNode parent) {
        super(name, gm);
        _parent = parent;
    }

    public PowerSchematicLayer(String name, EditorGraphModel gm) {
        this(name, gm, null);
    }

    public PowerSchematicLayer(EditorGraphModel gm) {
        this("Root", gm, null);
    }

    public PowerSchematicLayer(String name) {
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
            if (layer != null && layer instanceof PowerSchematicLayer) {
                parent = ((PowerSchematicLayer) layer).getParent();
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