/*
 * Created on Feb 14, 2005
 */
package com.traclabs.biosim.server.editor.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * LabelVesprFigNode adds a label to display the text from a VesprNode.
 * 
 * @author kkusy
 */
public abstract class FigLabelNode extends VesprFigNode {
    protected FigLabel _label;

    public FigLabelNode() {
        super();
    }

    public void setEditable(boolean editable) {
        _label.setEditable(editable);
    }

    public boolean getEditable() {
        return _label.getEditable();
    }

    public void setMultiLine(boolean b) {
        _label.setMultiLine(b);
    }

    public boolean getMultiLine() {
        return _label.getMultiLine();
    }

    protected void addFigs() {
        // Add the label on top.
        _label = new FigLabel(38, 25, 0, 0);
        _label.setText(getTag());
        _label.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getSource() == _label
                        && evt.getPropertyName().equals("editing")) {
                    Boolean newValue = (Boolean) evt.getNewValue();

                    if (newValue.booleanValue() == false) {
                        endEditing();
                    }
                }
            }
        });

        addFig(_label);
    }

    /**
     * Return the text string displayed for this node
     */
    public String getLabel() {
        return _label.getText();
    }

    public void mouseClicked(java.awt.event.MouseEvent me) {
        _label.mouseClicked(me);
    }

    /**
     * Resizes the node so that it is large enough to accomodate the size of the
     * label.
     */
    public void endEditing() {
        Dimension size = getPreferedSize(); // Minimum size of handle box.
        int w = (int) size.getWidth();
        int h = (int) size.getHeight();

        Point center = _label.center();
        int x = center.x - w / 2;
        int y = center.y - h / 2;

        setHandleBox(x, y, w, h);
        endTrans();

        VesprNode own = (VesprNode) getOwner();
        own.setText(_label.getText());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("text")) {
            update();
            return;
        }
        super.propertyChange(evt);
    }

    public Dimension getMinimumSize() {
        Dimension dim = _label.getSize();
        int w = (int) Math.max(dim.getWidth() + 10, 75);
        int h = (int) Math.max(dim.getHeight() + 10.0, 50.0);
        return new Dimension(w, h);
    }

    public void setLineColor(Color col) {
        super.setLineColor(col);
        setTextLineColor(_label, col);
    }

    public void setBounds(int x, int y, int w, int h) {
        Rectangle rect = new Rectangle(x, y, w - _shadowOffset, h
                - _shadowOffset);
        Point center = new Point((int) (rect.getX() + rect.getWidth() / 2),
                (int) (rect.getY() + rect.getHeight() / 2));

        // Translate the label to the center.
        Point textCenter = _label.center();
        _label.translate(center.x - textCenter.x, center.y - textCenter.y);

        super.setBounds(x, y, w, h);
    }

    public void update() {
        VesprNode node = (VesprNode) getOwner();
        if (!_label.getText().equals(node.getText())) {
            _label.setText(node.getText());
            doLayout();
        }
    }
}