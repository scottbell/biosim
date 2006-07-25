package com.traclabs.biosim.editor.base;

import java.awt.Cursor;
import java.util.Hashtable;

import javax.swing.Action;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.CmdSave;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.Mode;
import org.tigris.gef.base.ModePlace;
import org.tigris.gef.graph.GraphFactory;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.GraphNodeHooks;
import org.tigris.gef.graph.MutableGraphModel;

/**
 * Cmd to Load a previously saved document document. The loaded editor is
 * displayed in a new JGraphFrame.
 * 
 * @see CmdSave
 */

public class CmdCreateModuleNode extends Cmd implements GraphFactory {
    public static Class DEFAULT_NODE_CLASS = org.tigris.gef.graph.presentation.NetNode.class;

    private static Logger LOG = Logger.getLogger(CmdCreateModuleNode.class);

    /** Construct a new Cmd with the given arguments for node class. */
    public CmdCreateModuleNode(Hashtable args, String resource, String name) {
        super(args, resource, name);
    }

    /**
     * Construct a new Cmd with the given classes for the NetNode and its
     * FigNode.
     */
    public CmdCreateModuleNode(Class nodeClass, String resource, String name) {
        this(new Hashtable(), resource, name);
        setArg("className", nodeClass);
    }

    /**
     * Actually instanciate the NetNode and FigNode objects and set the global
     * next mode to ModePlace
     */
    public void doIt() {
        Editor ce = Globals.curEditor();
        BiosimEditor biosimEditor = (BiosimEditor) (ce);
        biosimEditor.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        GraphModel gm = ce.getGraphModel();
        if (!(gm instanceof MutableGraphModel))
            return;
        setArg("graphModel", gm);

        String instructions = null;
        Object actionName = getValue(javax.swing.Action.NAME);
        if (actionName != null) {
            instructions = "Click to place " + actionName.toString();
        }
        Mode placeMode = new ModePlace(this, instructions);

        Object shouldBeSticky = getArg("shouldBeSticky");
        Globals.mode(placeMode, shouldBeSticky == Boolean.TRUE);
        if (LOG.isDebugEnabled())
            LOG.debug("Mode set to ModePlace with sticky mode "
                    + shouldBeSticky);
        biosimEditor.getFrame().setCursor(Cursor.getDefaultCursor());
    }

    public void undoIt() {
        LOG.warn("undo is not implemented");
    }

    public GraphModel makeGraphModel() {
        return null;
    }

    public Object makeEdge() {
        return null;
    }

    /**
     * Factory method for creating a new NetNode from the className argument.
     */
    public Object makeNode() {
        Object newNode;
        Object nodeType = getArg("className", DEFAULT_NODE_CLASS);
        if (nodeType instanceof Action) {
            Action a = null;
            a.actionPerformed(null);
            newNode = a.getValue("node");
        } else {
            Class nodeClass = (Class) getArg("className", DEFAULT_NODE_CLASS);
            //assert _nodeClass != null
            try {
                newNode = nodeClass.newInstance();
            } catch (java.lang.IllegalAccessException ignore) {
                return null;
            } catch (java.lang.InstantiationException ignore) {
                return null;
            }
        }
        LOG.debug("New node created " + newNode);

        if (newNode instanceof GraphNodeHooks) {
            LOG.debug("Initializing GraphNodeHooks");
            ((GraphNodeHooks) newNode).initialize(_args);
        }
        return newNode;
    }
}
