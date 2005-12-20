package com.traclabs.biosim.editor.graph;

import java.awt.Color;
import java.awt.Rectangle;

import org.tigris.gef.presentation.FigText;

/**
 * FigLabel represents a label for EditorFigNode objects. The label is centered
 * both horizontally and vertically within its parent node. The label is also
 * editable.
 * 
 * @author Kevin Kusy
 */
public class FigLabel extends FigText {

    /**
     * Constructs a new FigLabel with the given position, size, color, string,
     * font, and font size. Text string is initially empty and centered.
     */
    public FigLabel(int x, int y, int w, int h, Color textColor,
            String familyName, int fontSize) {
        super(x, y, w, h, textColor, familyName, fontSize);

        setExpandOnly(false);
        setFilled(false);
        setTextFilled(false);
        setLineWidth(0);
        setJustification(FigText.JUSTIFY_CENTER);
        setEditable(true);
    }

    /**
     * Constructs a new FigText with the given position, size, and attributes.
     */
    public FigLabel(int x, int y, int w, int h) {
        this(x, y, w, h, Color.blue, "Helvetica", 12);
    }

    /**
     * Computes the overall width and height of the FigLabel object and then
     * adjusts the label position as to center it in the node.
     */
    public void calcBounds() {
        super.calcBounds();

        // calculate x, y to center in the parent node.
        if (getGroup() != null && getGroup() instanceof FigModuleLabelNode) {
            FigModuleNode node = (FigModuleNode) getGroup();
            Rectangle rect = node.getHandleBox();
            int x = (int) rect.getX();
            int y = (int) rect.getY();
            int w = (int) rect.getWidth();
            int h = (int) rect.getHeight();
            _x = x + w / 2 - _w / 2;
            _y = y + h / 2 - _h / 2;
        }
    }

    public void setText(String t) {
        super.setText(t);
    }
}