/*
 * Created on Feb 14, 2005
 */
package com.traclabs.biosim.editor.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Adds a label to display the text from a FigModuleNode.
 * 
 * @author kkusy
 */
public abstract class FigModuleLabelNode extends FigModuleNode {
    protected FigLabel myNameLabel;
    protected FigLabel myDescriptionLabel;
    
    private static final int X_DESCRIPTION_OFFSET = 17;
    private static final int Y_DESCRIPTION_OFFSET = 15;

    public FigModuleLabelNode() {
        super();
    }

    protected void addFigs() {
        // Add the label on top.
        myNameLabel = new FigLabel(38, 0, 0, 0);
        myNameLabel.setText("Unnamed");
        myNameLabel.setFontSize(14);
        myNameLabel.setFont(myNameLabel.getFont().deriveFont(Font.BOLD));
        myNameLabel.addPropertyChangeListener(new LabelEditingListener());

        addFig(myNameLabel);
        
        myDescriptionLabel = new FigLabel(myNameLabel.getX() + X_DESCRIPTION_OFFSET, myNameLabel.getY() + Y_DESCRIPTION_OFFSET, 0, 0);
        myDescriptionLabel.setText("");
        myDescriptionLabel.setFontSize(10);
        myDescriptionLabel.addPropertyChangeListener(new LabelEditingListener());

        addFig(myNameLabel);
        addFig(myDescriptionLabel);
    }

    /**
     * Return the text string displayed for this node
     */
    public String getNameText() {
        return myNameLabel.getText();
    }
    
    public void setDescriptionText(String text){
        myDescriptionLabel.setText(text);
        myDescriptionLabel.setX(myNameLabel.getX() + X_DESCRIPTION_OFFSET);
        myDescriptionLabel.setY(myNameLabel.getY() + Y_DESCRIPTION_OFFSET);
        damage();
    }

    

    /**
     * Resizes the node so that it is large enough to accomodate the size of the
     * label.
     */
    public void endEditing() {
        Dimension size = getPreferedSize(); // Minimum size of handle box.
        int w = (int) size.getWidth() + 400;
        int h = (int) size.getHeight() + 400;

        Point center = myNameLabel.center();
        int x = center.x - w / 2;
        int y = center.y - h / 2;

        setHandleBox(x, y, w, h);
        endTrans();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("text")) {
            update();
            return;
        }
        super.propertyChange(evt);
    }

    public Dimension getMinimumSize() {
        Dimension dimName = myNameLabel.getSize();
        Dimension dimDescription = myDescriptionLabel.getSize();
        int w = (int) Math.max(dimName.getWidth() + dimDescription.getWidth() + 30, 30);
        int h = (int) Math.max(dimName.getHeight() +  dimDescription.getHeight() + 30, 30);
        return new Dimension(w, h);
    }

    public void setLineColor(Color col) {
        super.setLineColor(col);
        setTextLineColor(myNameLabel, col);
        setTextLineColor(myDescriptionLabel, col);
    }

    public void setBounds(int x, int y, int w, int h) {
        Rectangle rect = new Rectangle(x, y, w - _shadowOffset, h
                - _shadowOffset);
        Point center = new Point((int) (rect.getX() + rect.getWidth() / 2),
                (int) (rect.getY() + rect.getHeight() / 2));

        // Translate the label to the center.
        Point textCenter = myNameLabel.center();
        myNameLabel.translate(center.x - textCenter.x, center.y - textCenter.y);
        myDescriptionLabel.translate(center.x - textCenter.x, center.y - textCenter.y);
        super.setBounds(x, y, w, h);
    }

    public void update() {
        ModuleNode node = (ModuleNode) getOwner();
        if (!myNameLabel.getText().equals(node.getSimBioModuleImpl().getModuleName())) {
            myNameLabel.setText(node.getSimBioModuleImpl().getModuleName());
            doLayout();
        }
        
    }
    
    private class LabelEditingListener implements PropertyChangeListener{
        public void propertyChange(PropertyChangeEvent evt) {
            if ((evt.getSource() == myNameLabel) || (evt.getSource() == myDescriptionLabel))
                if (evt.getPropertyName().equals("editing")) {
                Boolean newValue = (Boolean) evt.getNewValue();
                if (newValue.booleanValue() == false) {
                    endEditing();
                }
            }
        }
    }
}