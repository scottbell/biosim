package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.LayerDiagram;
import org.tigris.gef.base.LayerGrid;
import org.tigris.gef.base.LayerPerspective;
import org.tigris.gef.base.ModeDragScroll;
import org.tigris.gef.base.ModeSelect;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.presentation.DefaultGraphNodeRenderer;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleNode;

/**
 * BiosimEditor supports the nesting of graphs within nodes. BiosimEditor simply
 * keeps track of the layer at the root of the tree. Each ModuleFigNode has a
 * child layer (EditorLayer) associated with it that visually represents the
 * nested contents of the node.
 */
public class PowerSchematicEditor extends Editor {

    private JFrame myFrame;

    public PowerSchematicEditor() {
        this(new PowerSchematicLayer("Root"));
    }

    public PowerSchematicEditor(PowerSchematicLayer lay) {
        this(new PowerSchematicDocument(lay));
    }

    public PowerSchematicEditor(PowerSchematicDocument doc) {
        super(doc.getRoot().getGraphModel(), null, doc.getRoot());

        doc.addEditor(this);
        super.document(doc);

        _selectionManager = new PowerSelectionManager(this);
        _modeManager = new PowerModeManager(this);

        pushMode(new ModeSelect(this));
        pushMode(new ModeDragScroll(this));
        setGraphNodeRenderer(new DefaultGraphNodeRenderer());
    }
    
    protected void defineLayers(GraphModel gm, Layer lay) {
    	LayerGrid layerGrid = new LayerGrid(Color.WHITE, Color.WHITE, 16, false);
        _layerManager.addLayer(layerGrid);
        if(lay != null)
            _layerManager.addLayer(lay);
        else if(gm == null)
            _layerManager.addLayer(new LayerDiagram("Example"));
        else
            _layerManager.addLayer(new LayerPerspective("untitled", gm));
    }

    public void document(Object d) {
        PowerSchematicDocument oldDoc = (PowerSchematicDocument) document();

        if (oldDoc != null) {
            oldDoc.removeEditor(this);
        }

        if (d != null && d instanceof PowerSchematicDocument) {
            PowerSchematicDocument newDoc = (PowerSchematicDocument) d;
            newDoc.addEditor(this);
            super.document(newDoc);
            showRoot();
        } else {
            super.document(null);
        }
    }

    /**
     * Returns the root of the layer tree.
     */
    public PowerSchematicLayer getRoot() {
        PowerSchematicDocument vd = (PowerSchematicDocument) _document;
        return vd.getRoot();
    }

    public void showRoot() {
        // Switch out the layer that is being displayed.
        PowerSchematicDocument vd = (PowerSchematicDocument) _document;
        getLayerManager().replaceActiveLayer(vd.getRoot());
        getSelectionManager().deselectAll();
        damageAll();
    }

    public void showParent() {
        PowerSchematicLayer layer = (PowerSchematicLayer) getLayerManager().getActiveLayer();
        FigModuleNode figNode = layer.getParent();
        // Check for root.
        if (figNode != null) {
            layer = (PowerSchematicLayer) figNode.getLayer();
            getLayerManager().replaceActiveLayer(layer);
            getSelectionManager().deselectAll();
            damageAll();
        }
    }

    public void expandNode(FigModuleNode figNode) {
        if (figNode != null && figNode.getNestedLayer() != null) {
            PowerSchematicLayer layer = figNode.getNestedLayer();
            getLayerManager().replaceActiveLayer(layer);
            getSelectionManager().deselectAll();
            damageAll();
        }
    }
    /**
     * Clone the receiving editor. Called from EditorCmdSpawn. Subclasses of
     * Editor should override this method.
     */
    public Object clone() {
        // Create a new editor with its own layer manager, selection manager,
        // and mode manager.
        PowerSchematicEditor ed = new PowerSchematicEditor((PowerSchematicDocument) document());
        return ed;
    }

    /** Invoked when the mouse exits the Editor. */
    public void mouseExited(MouseEvent me) {
        super.mouseExited(me);
        if (_canSelectElements) {
            _modeManager.mouseExited(me);
        }
    }

    /**
     * @param pFrame
     */
    public void setFrame(JFrame pFrame) {
        myFrame = pFrame;
    }
    
    public JFrame getFrame() {
        return myFrame;
    }

}