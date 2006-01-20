package com.traclabs.biosim.client.simulation.power.schematic;

import java.awt.GridLayout;
import java.awt.Point;

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
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.power.PowerStore;

/**
 * This is the JPanel that displays a schematic
 * 
 * @author Scott Bell
 */
public class PowerSchematicPanel extends TimedPanel {
	private JGraph myGraph;

	private PowerSchematicEditor myEditor;

	private Logger myLogger;
	
	private BioHolder myBioHolder;

	public PowerSchematicPanel() {
		myLogger = Logger.getLogger(PowerSchematicPanel.class);
		myEditor = new PowerSchematicEditor();
		myGraph = new JGraph(myEditor);
		myGraph.setDrawingSize(600, 400);
		myBioHolder = BioHolderInitializer.getBioHolder();
		Globals.curEditor(myEditor);
		setLayout(new GridLayout(1, 1));
		add(myGraph);
	}

	public void refresh() {
		//change color of lines
		//change color of nodes
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

	private void addNode(ModuleNode node, int x, int y) {
		Layer theActiveLayer = myGraph.getEditor().getLayerManager()
				.getActiveLayer();
		FigModuleNode figNode = (FigModuleNode) node.makePresentation(theActiveLayer);
		myEditor.add(figNode);
		myGraph.getGraphModel().getNodes().add(node);
		figNode.setCenter(new Point(x, y));
	}
	
	private void createPowerNodes(){
		int x = 10;
		int y = 10;
		for (PowerStore powerStore : myBioHolder.thePowerStores) {
			myLogger.info("Adding node");
			addNode(new PowerStoreNode(powerStore), x, y);
			x += 10;
		}
	}
}