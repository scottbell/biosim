/*
 * Created on Apr 28, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.traclabs.biosim.editor.graph.FigModuleEdge;
import com.traclabs.biosim.editor.graph.ModuleEdge;
import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable;


public abstract class FlowratePropertiesFrame extends JFrame {
    private Logger myLogger;
    
    private int myIndex = 0;
    
    private JButton myOKButton;

    private SingleFlowRateControllable myOperations;
    
    private FigModuleEdge myModuleFigEdge;

    private JFormattedTextField myMaxField;

    private JFormattedTextField myDesiredField;
    
    private JCheckBox mySensorCheckBox;
    
    private JCheckBox myActuatorCheckBox;

    public FlowratePropertiesFrame(FigModuleEdge pEdge, SingleFlowRateControllable pOpertations) {
        myIndex = ((ModuleEdge)pEdge.getOwner()).getIndex();
        myLogger = Logger.getLogger(FlowratePropertiesFrame.class);
        myModuleFigEdge = pEdge;
        myOperations = pOpertations;
        myMaxField = new JFormattedTextField(NumberFormat.getNumberInstance());
        myMaxField.setValue(new Float(myOperations.getMaxFlowRate(myIndex)));
        myDesiredField = new JFormattedTextField(NumberFormat.getNumberInstance());
        myDesiredField.setValue(new Float(myOperations.getDesiredFlowRate(myIndex)));
        
        mySensorCheckBox = new JCheckBox();
        myActuatorCheckBox = new JCheckBox();
        
        myOKButton = new JButton(new OKAction());

        setLayout(new GridLayout(5, 2));
        add(new JLabel("Maximum"));
        add(myMaxField);
        add(new JLabel("Desired"));
        add(myDesiredField);
        add(new JLabel("Sensed"));
        add(mySensorCheckBox);
        add(new JLabel("Actuated"));
        add(myActuatorCheckBox);
        add(myOKButton);
        ModuleEdge theEdge = (ModuleEdge)myModuleFigEdge.getOwner();
        setTitle(theEdge.getName() + " Properties");
    }


    /**
     * Action that auto-arranges internal menus
     */
    private class OKAction extends AbstractAction {
        public OKAction() {
            super("OK");
        }

        public void actionPerformed(ActionEvent ae) {
            float max = ((Number)myMaxField.getValue()).floatValue();
            float desired = ((Number)myDesiredField.getValue()).floatValue();
            myOperations.setMaxFlowRate(max, myIndex);
            myOperations.setDesiredFlowRate(desired, myIndex);
            myModuleFigEdge.setIsSensed(mySensorCheckBox.isSelected());
            myModuleFigEdge.setIsActuated(myActuatorCheckBox.isSelected());
            
            dispose();
        }
    }
}