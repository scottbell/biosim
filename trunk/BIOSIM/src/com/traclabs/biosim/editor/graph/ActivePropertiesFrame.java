/*
 * Created on Apr 28, 2005
 *
 */
package com.traclabs.biosim.editor.graph;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


class ActivePropertiesFrame extends JFrame {
    private Logger myLogger;
    
    private JTextField myNameField;
    
    private List consumerAndProducerLabels = new Vector();
    
    private List consumerAndProducerFields = new Vector();
    
    private JButton myOKButton;

    private SimBioModuleImpl mySimBioModuleImpl;
    
    private FigActiveNode myFigActiveNode;
    
    private static final String maximumLabel= new String(" maximum");
    private static final String desiredLabel= new String(" desired");

    public ActivePropertiesFrame(FigActiveNode pNode) {
        myLogger = Logger.getLogger(ActivePropertiesFrame.class);
        myFigActiveNode = pNode;
        ModuleNode owner = (ModuleNode) pNode.getOwner();
        mySimBioModuleImpl = (SimBioModuleImpl) owner.getSimBioModuleImpl();
        myNameField = new JTextField(mySimBioModuleImpl.getModuleName());
        myOKButton = new JButton(new OKAction());
        
        generateConsumerAndProducerFields();

        setLayout(new GridLayout(2, 2));
        add(new JLabel("Name"));
        add(myNameField);
        add(myOKButton);
        setTitle(pNode.getText() + " Properties");
    }
    
    /**
     * 
     */
    private void generateConsumerAndProducerFields() {
        if (mySimBioModuleImpl instanceof PowerConsumerOperations){
            generateLabelAndTextPair("Power Consumer", ((PowerConsumerOperations)mySimBioModuleImpl).getPowerConsumerDefinition());
        }
    }

    /**
     * @param string
     * @param powerConsumerDefinition
     */
    private void generateLabelAndTextPair(String labelName, StoreFlowRateControllableOperations powerConsumerDefinition) {
        consumerAndProducerLabels.add(new JLabel(labelName + maximumLabel));
        consumerAndProducerLabels.add(new JLabel(labelName + desiredLabel));
        
        JFormattedTextField newMaximumField = new JFormattedTextField(NumberFormat.getNumberInstance());
        newMaximumField.setValue(new Float(powerConsumerDefinition.getMaxFlowRate(0)));
        consumerAndProducerFields.add(newMaximumField);
    }

    public JPanel getModuleSpecificPanel(){
        return new JPanel();
    }
    
    /**
     * Action that auto-arranges internal menus
     */
    private class OKAction extends AbstractAction {
        public OKAction() {
            super("OK");
        }

        public void actionPerformed(ActionEvent ae) {
            mySimBioModuleImpl.setModuleName(myNameField.getText());
            myFigActiveNode.update();
            dispose();
        }
    }
}