package com.traclabs.biosim.client.simulation.power.schematic;

import java.awt.GridLayout;

import org.tigris.gef.base.Globals;
import org.tigris.gef.graph.presentation.JGraph;

import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.simulation.power.schematic.base.PowerSchematicEditor;

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
        myEditor.setGridHidden(true);
        myGraph = new JGraph(myEditor);
        Globals.curEditor(myEditor);
        //myGraph.setDrawingSize(0, 0);
        setLayout(new GridLayout(1, 1));
        add(myGraph);
	}

    public void refresh() {
    }
    
    public PowerSchematicEditor getEditor(){
        return myEditor;
    }
}