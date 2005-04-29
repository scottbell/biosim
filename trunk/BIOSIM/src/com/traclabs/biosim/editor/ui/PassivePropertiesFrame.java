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
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.traclabs.biosim.editor.graph.FigPassiveNode;
import com.traclabs.biosim.editor.graph.ModuleNode;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;


public class PassivePropertiesFrame extends JFrame {
    private JTextField myNameField;

    private JFormattedTextField myCapacityField;

    private JFormattedTextField myLevelField;
    
    private JButton myOKButton;

    private StoreImpl myStoreImpl;
    
    private FigPassiveNode myFigStoreNode;

    public PassivePropertiesFrame(FigPassiveNode pNode) {
        myFigStoreNode = pNode;
        ModuleNode owner = (ModuleNode) pNode.getOwner();
        myStoreImpl = (StoreImpl) owner.getSimBioModuleImpl();
        myNameField = new JTextField(myStoreImpl.getModuleName());
        myCapacityField = new JFormattedTextField(NumberFormat.getNumberInstance());
        myCapacityField.setValue(new Float(myStoreImpl.getInitialCapacity()));
        myLevelField = new JFormattedTextField(NumberFormat.getNumberInstance());
        myLevelField.setValue(new Float(myStoreImpl.getInitialLevel()));
        myOKButton = new JButton(new OKAction());

        setLayout(new GridLayout(4, 3));
        add(new JLabel("Name"));
        add(myNameField);
        add(new JLabel("Capacity"));
        add(myCapacityField);
        add(new JLabel("Level"));
        add(myLevelField);
        add(myOKButton);
        setTitle(pNode.getText() + " Properties");
    }
    
    /**
     * Action that auto-arranges internal menus
     */
    private class OKAction extends AbstractAction {
        public OKAction() {
            super("OK");
        }

        public void actionPerformed(ActionEvent ae) {
            myStoreImpl.setModuleName(myNameField.getText());
            float capacity = ((Number)myCapacityField.getValue()).floatValue();
            float level = ((Number)myCapacityField.getValue()).floatValue();
            myStoreImpl.setInitialCapacity(capacity);
            myStoreImpl.setInitialLevel(level);
            myFigStoreNode.update();
            dispose();
        }
    }
}