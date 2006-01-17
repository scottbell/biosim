package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.ModeCreate;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleEdge;
import com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode;

/**
 * A Mode to interpret user input while creating an edge. Basically mouse down
 * starts creating an edge from a source port Fig, mouse motion paints a
 * rubberband line, mouse up finds the destination port and finishes creating
 * the edge and makes an FigEdge and sends it to the back of the Layer.
 * 
 * The argument "edgeClass" determines the type if edge to suggest that the
 * Editor's GraphModel construct. The GraphModel is responsible for acutally
 * making an edge in the underlying model and connecting it to other model
 * elements.
 */

public class EditorModeCreateEdge extends ModeCreate {
    ////////////////////////////////////////////////////////////////
    // instance variables

    /** The NetPort where the arc is paintn from */
    private Object startPort;

    /** The Fig that presents the starting NetPort */
    private Fig _startPortFig;

    /** The FigNode on the NetNode that owns the start port */
    private FigNode _sourceFigNode;

    /** The new NetEdge that is being created */
    private Object _newEdge;

    ////////////////////////////////////////////////////////////////
    // constructor

    public EditorModeCreateEdge() {
        super();
    }

    public EditorModeCreateEdge(Editor par) {
        super(par);
    }

    ////////////////////////////////////////////////////////////////
    // Mode API

    public String instructions() {
        return "Drag to define an edge to another port";
    }

    ////////////////////////////////////////////////////////////////
    // ModeCreate API

    /**
     * Create the new item that will be drawn. In this case I would rather
     * create the FigEdge when I am done. Here I just create a rubberband
     * FigLine to show during dragging.
     */
    public Fig createNewItem(MouseEvent me, int snapX, int snapY) {
        return new FigLine(snapX, snapY, 0, 0, Globals.getPrefs()
                .getRubberbandColor());
    }

    ////////////////////////////////////////////////////////////////
    // event handlers

    /**
     * On mousePressed determine what port the user is dragging from. The
     * mousePressed event is sent via ModeSelect.
     */
    public void mousePressed(MouseEvent me) {
        if (me.isConsumed()) {
            return;
        }
        int x = me.getX(), y = me.getY();
        Editor ce = Globals.curEditor();
        Fig underMouse = ce.hit(x, y);
        if (underMouse == null) {
            //System.out.println("bighit");
            underMouse = ce.hit(x - 16, y - 16, 32, 32);
        }
        if (underMouse == null) {
            done();
            me.consume();
            return;
        }
        if (!(underMouse instanceof FigNode)) {
            done();
            me.consume();
            return;
        }
        _sourceFigNode = (FigNode) underMouse;
        startPort = _sourceFigNode.deepHitPort(x, y);
        if (startPort == null) {
            done();
            me.consume();
            return;
        }
        _startPortFig = _sourceFigNode.getPortFig(startPort);
        super.mousePressed(me);
    }

    /**
     * On mouseReleased, find the destination port, ask the GraphModel to
     * connect the two ports. If that connection is allowed, then construct a
     * new FigEdge and add it to the Layer and send it to the back.
     */
    public void mouseReleased(MouseEvent me) {
        if (me.isConsumed()) {
            return;
        }
        if (_sourceFigNode == null) {
            done();
            me.consume();
            return;
        }

        int x = me.getX(), y = me.getY();
        Editor ce = Globals.curEditor();
        Fig destFig = ce.hit(x, y);
        if (destFig == null) {
            destFig = ce.hit(x - 16, y - 16, 32, 32);
        }
        GraphModel gm = ce.getGraphModel();
        if (!(gm instanceof MutableGraphModel)) {
            destFig = null;
        }

        if (destFig instanceof FigNode) {
            MutableGraphModel mgm = (MutableGraphModel) gm;
            FigNode destFigNode = (FigNode) destFig;

            // If its a FigNode, then check within the
            // FigNode to see if a port exists
            Object foundPort = destFigNode.deepHitPort(x, y);
            Fig destPortFig = destFigNode.getPortFig(foundPort);

            if (foundPort != null && mgm.canConnect(startPort, foundPort)) {
                ModuleNode sourceNode = (ModuleNode) _sourceFigNode.getOwner();
                ModuleNode destNode = (ModuleNode) destFig.getOwner();
                Class[] classesMeetingContraints = ModuleNode.getClassesMeetingConstraintsForEdge(sourceNode, destNode);
                if (classesMeetingContraints.length > 0) {
                    Class selectedClass;
                    if (classesMeetingContraints.length > 1){
                        selectedClass = classesMeetingContraints[0];
                        selectedClass = getUserClassChoice(classesMeetingContraints);
                    }
                    else
                        selectedClass = classesMeetingContraints[0];
                    if (selectedClass == null){
                        _sourceFigNode.damage();
                        ce.damageAll();
                        _newItem = null;
                        done();
                        me.consume();
                        return;
                    }
                    _newEdge = mgm.connect(startPort, foundPort);
                    ModuleEdge newModuleEdge = (ModuleEdge) _newEdge;
                    //number of edges including the one just made
                    int numberOfEdges = sourceNode.countEdges(destNode);
                    newModuleEdge.setIndex(numberOfEdges - 1);
                    newModuleEdge.initializeFlowrates(selectedClass);
                }
            }

            // Calling connect() will add the edge to the GraphModel and
            // any LayerPersectives on that GraphModel will get a
            // edgeAdded event and will add an appropriate FigEdge
            // (determined by the GraphEdgeRenderer).

            if (_newEdge != null) {
                ce.damageAll();
                _sourceFigNode.damage();
                destFigNode.damage();
                _newItem = null;

                Layer layer = ce.getLayerManager().getActiveLayer();
                FigEdge fe = (FigEdge) layer.presentationFor(_newEdge);
                fe.setSourcePortFig(_startPortFig);
                fe.setSourceFigNode(_sourceFigNode);
                fe.setDestPortFig(destPortFig);
                fe.setDestFigNode(destFigNode);
                if (fe != null)
                    ce.getSelectionManager().select(fe);
                done();
                me.consume();
                return;
            }
        }
        _sourceFigNode.damage();
        ce.damageAll();
        _newItem = null;
        done();
        me.consume();
    }
    
    /**
     * @return
     */
    private Class getUserClassChoice(Class[] classesMeetingContraints) {
    	String[] userFriendlyResourceStrings = generateUserFriendlyResources(classesMeetingContraints);
    	String resourceStringSelected = (String)JOptionPane.showInputDialog(
                            new JFrame(),
                            "Select a flowrate to use",
                            "Flowrate Selection",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            userFriendlyResourceStrings,
                            userFriendlyResourceStrings[0]);
    	
    	int selectedIndex = getIndexOfString(userFriendlyResourceStrings, resourceStringSelected);
    	Class classSelected = classesMeetingContraints[selectedIndex];
        return classSelected;
    }

    private int getIndexOfString(String[] userFriendlyResourceStrings, String resourceStringSelected) {
		for (int i = 0; i < userFriendlyResourceStrings.length; i++){
			if (userFriendlyResourceStrings[i].equals(resourceStringSelected))
					return i;
		}
		return -1;
	}

	private String[] generateUserFriendlyResources(Class[] classesMeetingContraints) {
		String[] userFriendlyArray = new String[classesMeetingContraints.length];
    	for (int i = 0; i < userFriendlyArray.length; i++){
    		String classString = classesMeetingContraints[i].getSimpleName();
    		String withoutOperations = classString.substring(0, classString.indexOf("Operations"));
    		int indexToAddSpace =  withoutOperations.indexOf("Consumer");
    		if (indexToAddSpace == -1)
    			indexToAddSpace = withoutOperations.indexOf("Producer");
    		String nicelySpaced = withoutOperations.substring(0, indexToAddSpace) + " " + withoutOperations.substring(indexToAddSpace);
    		userFriendlyArray[i] = nicelySpaced;
    	}
		return userFriendlyArray;
	}

	/**
     * Get the NetPort where the arc is painted from
     * 
     * @return Returns the startPort.
     */
    public Object getStartPort() {
        return startPort;
    }
} /* end class ModeCreateEdge */
