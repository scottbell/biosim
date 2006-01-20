/*
 * Created on May 12, 2005
 *
 */
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.traclabs.biosim.client.simulation.power.schematic.graph.FigStoreNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.ModuleNode;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 * @author scott
 *
 */
public class StorePropertiesFrame extends JFrame {
    private JTextField myNameField;

    private JFormattedTextField myCapacityField;

    private JFormattedTextField myLevelField;
    
    private JCheckBox mySensorCheckBox;
    
    private JButton myOKButton;

    private StoreImpl myStoreImpl;
    
    private FigStoreNode myFigStoreNode;

    public StorePropertiesFrame(FigStoreNode pNode) {
        myFigStoreNode = pNode;
        ModuleNode owner = (ModuleNode) pNode.getOwner();
        myStoreImpl = (StoreImpl) owner.getSimBioModuleImpl();
        myNameField = new JTextField(myStoreImpl.getModuleName());
        myCapacityField = new JFormattedTextField(NumberFormat.getNumberInstance());
        myCapacityField.setValue(new Float(myStoreImpl.getInitialCapacity()));
        myLevelField = new JFormattedTextField(NumberFormat.getNumberInstance());
        myLevelField.setValue(new Float(myStoreImpl.getInitialLevel()));
        mySensorCheckBox = new JCheckBox();
        myOKButton = new JButton(new OKAction());

        setLayout(new GridLayout(5, 2));
        add(new JLabel("Name"));
        add(myNameField);
        add(new JLabel("Capacity"));
        add(myCapacityField);
        add(new JLabel("Level"));
        add(myLevelField);
        add(new JLabel("Sensed"));
        add(mySensorCheckBox);
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
            myFigStoreNode.setIsSensed(mySensorCheckBox.isSelected());
            myFigStoreNode.damage();
            dispose();
        }
    }

}
