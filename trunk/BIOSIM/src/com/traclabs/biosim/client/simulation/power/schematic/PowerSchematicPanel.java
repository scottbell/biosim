package com.traclabs.biosim.client.simulation.power.schematic;

import java.awt.GridLayout;

import org.tigris.gef.base.Globals;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.simulation.power.schematic.base.PowerSchematicEditor;
import com.traclabs.biosim.client.simulation.power.schematic.graph.power.PowerStoreNode;

/**
 * This is the JPanel that displays a schematic
 * 
 * @author Scott Bell
 */
public class PowerSchematicPanel extends TimedPanel {
	private JGraph myGraph;
    private PowerSchematicEditor myEditor;
	
	public PowerSchematicPanel(){
        //need to add ScrollPane
        myEditor = new PowerSchematicEditor();
        //myEditor.setGridHidden(true);
        myGraph = new JGraph(myEditor);
        Globals.curEditor(myEditor);
        //myGraph.setDrawingSize(0, 0);
        setLayout(new GridLayout(1, 1));
        add(myGraph);
        createNode();
	}

    public void refresh() {
    }
    
    public PowerSchematicEditor getEditor(){
        return myEditor;
    }
    
    private void createNode(){
    	PowerStoreNode node = new PowerStoreNode();
        node.initialize(null); // Currently we are not using the args to
                               // initialize.

        // Get the FigNode used to present this Node in the new Diagram
        // Then add the Fig to the Layer and the Node to the GraphModel
        Layer theActiveLayer = myGraph.getEditor().getLayerManager().getActiveLayer();
        FigNode figNode = (FigNode) node.makePresentation(theActiveLayer);
        myEditor.add(figNode);
        myGraph.getGraphModel().getNodes().add(node);
    }
}