package com.traclabs.biosim.client.simulation.power.schematic;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.Random;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.graph.presentation.NetPort;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.simulation.power.schematic.base.PowerSchematicEditor;
import com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.power.PowerStoreNode;

/**
 * This is the JPanel that displays a schematic
 * 
 * @author Scott Bell
 */
public class PowerSchematicPanel extends TimedPanel {
	private JGraph myGraph;

	private PowerSchematicEditor myEditor;

	private Random myRandomGen;

	private Logger myLogger;

	private ModuleNode myLastNode;

	public PowerSchematicPanel() {
		myLogger = Logger.getLogger(PowerSchematicPanel.class);
		// need to add ScrollPane
		myEditor = new PowerSchematicEditor();
		// myEditor.setGridHidden(true);
		myGraph = new JGraph(myEditor);
		myGraph.setDrawingSize(300, 300);
		Globals.curEditor(myEditor);
		// myGraph.setDrawingSize(0, 0);
		setLayout(new GridLayout(1, 1));
		add(myGraph);
		myRandomGen = new Random();
	}

	public void refresh() {
		ModuleNode newNode = createNode();
		// connect them
		if (myLastNode != null) {
			MutableGraphModel mutableGraphModel = (MutableGraphModel) myGraph
					.getGraphModel();
			NetPort fromPort = (NetPort) myLastNode.getPort();
			NetPort destinationPort = (NetPort) newNode.getPort();
			Object newEdge = mutableGraphModel.connect(fromPort,
					destinationPort);
		}
		myLastNode = newNode;
	}

	public PowerSchematicEditor getEditor() {
		return myEditor;
	}

	private ModuleNode createNode() {
		PowerStoreNode node = new PowerStoreNode();
		node.initialize(null); // Currently we are not using the args to
		// initialize.

		// Get the FigNode used to present this Node in the new Diagram
		// Then add the Fig to the Layer and the Node to the GraphModel
		Layer theActiveLayer = myGraph.getEditor().getLayerManager()
				.getActiveLayer();
		FigNode figNode = (FigNode) node.makePresentation(theActiveLayer);
		myEditor.add(figNode);
		myGraph.getGraphModel().getNodes().add(node);
		int x = myRandomGen.nextInt(300);
		int y = myRandomGen.nextInt(300);
		figNode.setCenter(new Point(x, y));
		myLogger.info("created node");
		return node;
	}
}