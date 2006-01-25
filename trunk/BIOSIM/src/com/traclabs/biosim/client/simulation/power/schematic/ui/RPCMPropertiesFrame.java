/*
 * Created on Apr 28, 2005
 *
 */
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.power.FigRPCMNode;
import com.traclabs.biosim.idl.simulation.power.RPCM;


public class RPCMPropertiesFrame extends JFrame {
    private RPCM myRPCM;
    private FigRPCMNode myFigRPCMNode;
    
    private JPanel mySwitchPanel = new JPanel();
    private EnumeratedCheckBox[] mySwitches;
    
    private JPanel myTripPanel = new JPanel();
    private JButton myOvertripButton;
    private JButton myUndertripButton;
    private JButton myClearButton;

    public RPCMPropertiesFrame(FigRPCMNode pNode) {
    	myFigRPCMNode = pNode;
        ModuleNode owner = (ModuleNode) pNode.getOwner();
        myRPCM = (RPCM)owner.getSimBioModule();
        myOvertripButton = new JButton(new OvertripAction());
        myUndertripButton = new JButton(new UndertripAction());
        myClearButton = new JButton(new ClearTripsAction());
        
        mySwitches = new EnumeratedCheckBox[myRPCM.getPowerProducerDefinition().getStores().length];
        mySwitchPanel.setLayout(new GridLayout(1, mySwitches.length));
        boolean[] switchStatuses = myRPCM.getSwitchStatuses();
        for (int i = 0; i < mySwitches.length; i++){
        	String label = "Effluent "+(i + 1);
        	mySwitches[i] = new EnumeratedCheckBox(new SwitchAction(label), i);
        	mySwitches[i].setSelected(switchStatuses[i]);
        	mySwitchPanel.add(mySwitches[i]);
		}
        mySwitchPanel.setBorder(BorderFactory.createTitledBorder("Switches"));
        
        myTripPanel.setLayout(new GridLayout(1, 3));
        myTripPanel.add(myClearButton);
        myTripPanel.add(myOvertripButton);
        myTripPanel.add(myUndertripButton);
        myTripPanel.setBorder(BorderFactory.createTitledBorder("Trips"));
        
        setTitle(myRPCM.getModuleName() + " Properties");
        setLayout(new GridLayout(2, 1));
        add(mySwitchPanel);
        add(myTripPanel);
    }
  
    private class SwitchAction extends AbstractAction {
    	public SwitchAction(String name){
    		super(name);
    	}
        public void actionPerformed(ActionEvent ae) {
        	EnumeratedCheckBox checkbox = (EnumeratedCheckBox)ae.getSource();
        	myRPCM.setSwitch(checkbox.getIndex(), checkbox.isSelected());
        	boolean[] switchStatuses = myRPCM.getSwitchStatuses();
        	myFigRPCMNode.update();
        }
    }
    
    private class OvertripAction extends AbstractAction {
        public OvertripAction() {
            super("Overtrip");
        }
        public void actionPerformed(ActionEvent ae) {
        	myRPCM.overtrip();
        }
    }
    
    private class UndertripAction extends AbstractAction {
        public UndertripAction() {
            super("Undertrip");
        }
        public void actionPerformed(ActionEvent ae) {
        	myRPCM.undertrip();
        }
    }
    
    private class ClearTripsAction extends AbstractAction {
        public ClearTripsAction() {
            super("Clear");
        }
        public void actionPerformed(ActionEvent ae) {
        	myRPCM.clearTrips();
        }
    }
    
    private class EnumeratedCheckBox extends JCheckBox{
    	private int myIndex = 0;
    	public EnumeratedCheckBox(AbstractAction action, int index){
    		super(action);
    		this.myIndex = index;
    	}
    	public int getIndex(){
    		return myIndex;
    	}
    }
}