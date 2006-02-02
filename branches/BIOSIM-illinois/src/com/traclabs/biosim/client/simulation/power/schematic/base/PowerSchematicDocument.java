package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.util.Enumeration;
import java.util.Vector;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.client.simulation.power.schematic.graph.EditorGraphModel;
import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleNode;


public class PowerSchematicDocument {
    /** The layer at the root of the hierarchy */
    protected PowerSchematicLayer _root = null;

    /** Flag indicating whether the document has been modified */
    protected boolean _modifiedFlag = false;

    /** The filename for the document set when it is opened or saved */
    protected Vector<PowerSchematicEditor> _editors = new Vector<PowerSchematicEditor>();
 
    public PowerSchematicDocument() {
        this(new PowerSchematicLayer("Root"));
    }

    public PowerSchematicDocument(PowerSchematicLayer root) {
        setRoot(root);
    }

    public PowerSchematicDocument(EditorGraphModel model) {
        this(new PowerSchematicLayer(model));
    }

    private void setRoot(PowerSchematicLayer root) {
        _root = root;
    }

    public PowerSchematicLayer getRoot() {
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

    public void addEditor(PowerSchematicEditor e) {
        _editors.remove(e);
        _editors.add(e);
    }

    public void removeEditor(PowerSchematicEditor e) {
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
                PowerSchematicEditor ed = (PowerSchematicEditor) eds.nextElement();
                Layer layer = ed.getLayerManager().getActiveLayer();
                if (layer instanceof PowerSchematicLayer) {
                    if (((PowerSchematicLayer) layer).isDescendantDiagram(node)) {
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