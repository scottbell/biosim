package com.traclabs.biosim.client.simulation.power.schematic;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.graph.presentation.NetPort;

import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.simulation.power.schematic.base.CmdTreeLayout;
import com.traclabs.biosim.client.simulation.power.schematic.base.PowerSchematicEditor;
import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleEdge;
import com.traclabs.biosim.client.simulation.power.schematic.graph.FigModuleNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.power.GenericPowerConsumerNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.power.PowerStoreNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.power.RPCMNode;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.framework.Store;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.idl.simulation.power.GenericPowerConsumer;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.power.RPCM;

/**
 * This is the JPanel that displays a schematic
 * 
 * @author Scott Bell
 */
public class PowerSchematicPanel extends TimedPanel {
	private static final int REFRESH_RATE = 250;
	private static final Dimension DRAWING_SIZE = new Dimension(950, 350);
	
	private JGraph myGraph;

	private PowerSchematicEditor myEditor;

	private Logger myLogger;
	
	private BioHolder myBioHolder;
	
	public CmdTreeLayout myCmdTreeLayout;
	
	public List<FigModuleEdge> myFigEdges = new Vector<FigModuleEdge>();
	
	public List<FigModuleNode> myFigModules = new Vector<FigModuleNode>();

	public PowerSchematicPanel() {
		myLogger = Logger.getLogger(PowerSchematicPanel.class);
		myEditor = new PowerSchematicEditor();
		myGraph = new JGraph(myEditor);
		myGraph.setDrawingSize(DRAWING_SIZE);
		myBioHolder = BioHolderInitializer.getBioHolder();
		myCmdTreeLayout = new CmdTreeLayout();
		createPowerNodes();
		Globals.curEditor(myEditor);
		setLayout(new GridLayout(1, 1));
		add(myGraph);
		setDelay(REFRESH_RATE);
	}
	
	public Dimension getDrawingSize(){
		return DRAWING_SIZE;
	}

	public void refresh() {
		//change color of lines
		for (FigModuleEdge edge : myFigEdges)
			edge.refresh();
		//change color of nodes
		for (FigModuleNode module : myFigModules)
			module.refresh();
		myGraph.repaint();
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
		FigModuleEdge newFigEdge = (FigModuleEdge) theActiveLayer.presentationFor(newEdge);
		
		newFigEdge.setSourcePortFig(sourceNode.getPortFig());
		newFigEdge.setSourceFigNode(sourceNode);
		newFigEdge.setDestPortFig(destNode.getPortFig());
		newFigEdge.setDestFigNode(destNode);
		myFigEdges.add(newFigEdge);
	}

	private FigModuleNode addNode(ModuleNode node) {
		Layer theActiveLayer = myGraph.getEditor().getLayerManager().getActiveLayer();
		FigModuleNode figNode = (FigModuleNode) node.makePresentation(theActiveLayer);
		myEditor.add(figNode);
		myGraph.getGraphModel().getNodes().add(node);
		myFigModules.add(figNode);
		return figNode;
	}
	
	private void createPowerNodes(){
		PowerStore rootPowerStore = myBioHolder.thePowerStores.get(0);
		FigModuleNode rootPowerStoreNode = addNode(new PowerStoreNode(rootPowerStore));
		rootPowerStoreNode.setCenter((new Point(450, 50)));
		for (RPCM rpcm : myBioHolder.theRPCMs) {
			//find the RPCMs connected to the battery and connect them to the battery
			if (rpcm.getPowerConsumerDefinition().connectsTo(rootPowerStore)){
				FigModuleNode rpcmNode = addNode(new RPCMNode(rpcm));
				connectNodes(rootPowerStoreNode, rpcmNode);
				//find the generic power consumers connected to the RPCMs and connect them to the RPCMs
				for (GenericPowerConsumer powerConsumer : myBioHolder.theGenericPowerConsumers) {
					if (connected(powerConsumer.getPowerConsumerDefinition(), rpcm.getPowerProducerDefinition())){
						FigModuleNode consumerNode = findModule(powerConsumer.getModuleName());
						//if it already exists, connect to the existing one
						if (consumerNode == null)
							consumerNode = addNode(new GenericPowerConsumerNode(powerConsumer));
						connectNodes(rpcmNode, consumerNode);
					}
				}
			}
		}
		myCmdTreeLayout.arrangeRoot(rootPowerStoreNode);
	}
	
	private FigModuleNode findModule(String moduleName){
		for (Object figObject : myEditor.getLayerManager().getActiveLayer().getContents()) {
			if (figObject instanceof FigModuleNode){
				FigModuleNode currentFigModuleNode = (FigModuleNode)figObject;
				ModuleNode moduleNode = (ModuleNode)currentFigModuleNode.getOwner();
			if (moduleNode.getSimBioModule().getModuleName().equals(moduleName))
				return currentFigModuleNode;
			}
		}
		return null;
	}
	
	private boolean connected(StoreFlowRateControllable consumerA, StoreFlowRateControllable consumerB){
		for (Store storeFromA : consumerA.getStores()) {
			if (consumerB.connectsTo(storeFromA))
				return true;
		}
		return false;
	}
}