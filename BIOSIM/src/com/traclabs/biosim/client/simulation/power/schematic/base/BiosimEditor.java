package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.ModeDragScroll;
import org.tigris.gef.base.ModeSelect;
import org.tigris.gef.graph.presentation.DefaultGraphNodeRenderer;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleNode;

/**
 * BiosimEditor supports the nesting of graphs within nodes. BiosimEditor simply
 * keeps track of the layer at the root of the tree. Each ModuleFigNode has a
 * child layer (EditorLayer) associated with it that visually represents the
 * nested contents of the node.
 */
public class BiosimEditor extends Editor {

    private JFrame myFrame;

    public BiosimEditor() {
        this(new EditorLayer("Root"));
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
        setGraphNodeRenderer(new DefaultGraphNodeRenderer());
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
        FigModuleNode figNode = layer.getParent();
        // Check for root.
        if (figNode != null) {
            layer = (EditorLayer) figNode.getLayer();
            getLayerManager().replaceActiveLayer(layer);
            getSelectionManager().deselectAll();
            damageAll();
        }
    }

    public void expandNode(FigModuleNode figNode) {
        if (figNode != null && figNode.getNestedLayer() != null) {
            EditorLayer layer = figNode.getNestedLayer();
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