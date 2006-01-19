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
import org.tigris.gef.presentation.FigEdge;

import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.simulation.power.schematic.base.PowerSchematicEditor;
import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleNode;
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

	private FigModuleNode myLastNode;

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
		FigModuleNode firstFigNode = createNode();
		FigModuleNode secondFigNode = createNode();
		ModuleNode firstNode = (ModuleNode)firstFigNode.getOwner();
		ModuleNode secondNode = (ModuleNode)secondFigNode.getOwner();
		connectNodes(firstFigNode, secondFigNode);
		myLogger.info("Connected nodes");
	}

	public void refresh() {
	}

	public PowerSchematicEditor getEditor() {
		return myEditor;
	}

	private void connectNodes(FigModuleNode sourceNode, FigModuleNode destNode) {
		MutableGraphModel mutableGraphModel = (MutableGraphModel) myGraph
				.getGraphModel();
		NetPort sourcePort = (NetPort) sourceNode.getPort();
		NetPort destPort = (NetPort) destNode.getPort();
		Object newEdge = mutableGraphModel.connect(sourcePort, destPort);

		Layer theActiveLayer = myGraph.getEditor().getLayerManager()
				.getActiveLayer();
		FigEdge newFigEdge = (FigEdge) theActiveLayer.presentationFor(newEdge);
		
		newFigEdge.setSourcePortFig(sourceNode.getPortFig());
		newFigEdge.setSourceFigNode(sourceNode);
		newFigEdge.setDestPortFig(destNode.getPortFig());
		newFigEdge.setDestFigNode(destNode);
		
	}

	private FigModuleNode createNode() {
		PowerStoreNode node = new PowerStoreNode();
		Layer theActiveLayer = myGraph.getEditor().getLayerManager()
				.getActiveLayer();
		FigModuleNode figNode = (FigModuleNode) node.makePresentation(theActiveLayer);
		myEditor.add(figNode);
		myGraph.getGraphModel().getNodes().add(node);
		int x = myRandomGen.nextInt(300);
		int y = myRandomGen.nextInt(300);
		figNode.setCenter(new Point(x, y));
		return figNode;
	}
}