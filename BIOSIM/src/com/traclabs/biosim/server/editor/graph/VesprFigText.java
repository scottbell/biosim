/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.server.editor.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.StringTokenizer;

import org.tigris.gef.presentation.FigText;

/*
 * VesprFigText tries to correct a bug in the calcBounds() method of FigText.
 * FigText objects can be visually edited by double clicking them. The problem
 * was that the FigText was being shifted to the left when the text length was
 * decreased while editing. This occured when the backspace or delete keys were
 * pressed.
 * 
 * @author Kevin Kusy
 */
public class VesprFigText extends FigText {

    /**
     * Construct a new VesprFigText with the given position, size, color,
     * string, font, and font size. Text string is initially empty and centered.
     */
    public VesprFigText(int x, int y, int w, int h, Color textColor,
            String familyName, int fontSize) {
        super(x, y, w, h, textColor, familyName, fontSize);
        _expandOnly = false;
    }

    /**
     * Construct a new VesprFigText with the given position, size, and
     * attributes.
     */
    public VesprFigText(int x, int y, int w, int h) {
        super(x, y, w, h, Color.blue, "TimesRoman", 12);
        _expandOnly = false;
    }

    /**
     * Paint the FigText. Distingusih between linewidth=1 and >1 If <linewidth>
     * is equal 1, then paint a single rectangle Otherwise paint <linewidth>
     * nested rectangles, whereas every rectangle is painted of 4 connecting
     * lines.
     */
    public void paint(Graphics g) {
        if (!(isVisible()))
            return;
        //System.out.println("FigText.paint: x/y = " + _x + "/" + _y);
        //System.out.println("FigText.paint: top-/bottomMargin = " + _topMargin
        // + "/" + _botMargin);
        int chunkX = _x + _leftMargin;
        int chunkY = _y + _topMargin;
        StringTokenizer lines;

        if (_filled) {
            g.setColor(getFillColor());
            g.fillRect(_x, _y, _w, _h);
        }
        if (getLineWidth() > 0) {
            g.setColor(getLineColor());
            // test linewidth
            if (getLineWidth() == 1) {
                // paint single rectangle
                g.drawRect(_x, _y, _w - getLineWidth(), _h - getLineWidth());
            } else {
                // paint <linewidth rectangles
                for (int i = 0; i < getLineWidth(); i++) {
                    // a rectangle is painted as four connecting lines
                    g.drawLine(_x + i, _y + i, _x + _w - i - 1, _y + i);
                    g.drawLine(_x + _w - i - 1, _y + i, _x + _w - i - 1, _y
                            + _h - i - 1);
                    g.drawLine(_x + _w - i - 1, _y + _h - i - 1, _x + i, _y
                            + _h - i - 1);
                    g.drawLine(_x + i, _y + _h - i - 1, _x + i, _y + i);
                }
            }
        }
        if (_font != null)
            g.setFont(_font);
        _fm = g.getFontMetrics(_font);
        //System.out.println("FigText.paint: font height = " +
        // _fm.getHeight());
        int chunkH = _fm.getHeight() + _lineSpacing;
        //System.out.println("FigText.paint: chunkH = " + chunkH);
        chunkY = _y + _topMargin;
        if (_textFilled) {
            g.setColor(_textFillColor);
            lines = new StringTokenizer(_curText, "\n\r", true);
            while (lines.hasMoreTokens()) {
                String curLine = lines.nextToken();
                //if (curLine.equals("\r")) continue;
                int chunkW = _fm.stringWidth(curLine);
                switch (_justification) {
                case JUSTIFY_LEFT:
                    break;
                case JUSTIFY_CENTER:
                    chunkX = _x + (_w - chunkW) / 2;
                    break;
                case JUSTIFY_RIGHT:
                    chunkX = _x + _w - chunkW - _rightMargin;
                    break;
                }
                if (curLine.equals("\n") || curLine.equals("\r"))
                    chunkY += chunkH;
                else
                    g.fillRect(chunkX, chunkY, chunkW, _fm.getHeight());
            }
        }

        g.setColor(_textColor);
        chunkX = _x + _leftMargin;
        chunkY = _y + _topMargin + _fm.getHeight() - _fm.getDescent();
        //System.out.println("FigText.paint: chunkY = " + chunkY);
        lines = new StringTokenizer(_curText, "\n\r", true);
        while (lines.hasMoreTokens()) {
            String curLine = lines.nextToken();
            //if (curLine.equals("\r")) continue;
            int chunkW = _fm.stringWidth(curLine);
            switch (_justification) {
            case JUSTIFY_LEFT:
                break;
            case JUSTIFY_CENTER:
                chunkX = _x + (_w - chunkW) / 2;
                break;
            case JUSTIFY_RIGHT:
                chunkX = _x + _w - chunkW;
                break;
            }
            if (curLine.equals("\n") || curLine.equals("\r"))
                chunkY += chunkH;
            else {
                if (_underline)
                    g.drawLine(chunkX, chunkY + 1, chunkX + chunkW, chunkY + 1);
                drawString(g, curLine, chunkX, chunkY);
            }
        }
    }

    public void stuffMinimumSize(Dimension d) {
        if (_font == null)
            return;
        if (_fm == null)
            _fm = Toolkit.getDefaultToolkit().getFontMetrics(_font);

        // usage of getFontMetrics() is deprecated
        //if (_fm == null) _fm =
        // Toolkit.getDefaultToolkit().getFontMetrics(_font);
        int overallW = 0;
        int numLines = 1;
        StringTokenizer lines = new StringTokenizer(_curText, "\n\r", true);
        while (lines.hasMoreTokens()) {
            String curLine = lines.nextToken();
            int chunkW = _fm.stringWidth(curLine);
            if (curLine.equals("\n") || curLine.equals("\r"))
                numLines++;
            else
                overallW = Math.max(chunkW, overallW);
        }
        _lineHeight = _fm.getHeight();

        int overallH = _lineHeight * numLines + _lineSpacing * (numLines - 1)
                + _topMargin + _botMargin;
        overallH = Math.max(overallH, getMinimumHeight());
        overallW = Math.max(MIN_TEXT_WIDTH, overallW + _leftMargin
                + _rightMargin);
        d.width = overallW;
        d.height = overallH;
        //System.out.println("FigText.minimumSize: " + getText() + " = " +
        // overallW + " / " + overallH);
    }

    /**
     * Compute the overall width and height of the FigText object based on the
     * font, font size, and current text. Needs-More-Work: Right now text
     * objects can get larger when you type more, but they do not get smaller
     * when you backspace.
     */
    public void calcBounds() {
        Dimension dim = this.getMinimumSize();

        //		if(_editMode) {
        switch (_justification) {
        case JUSTIFY_LEFT:
            break;

        case JUSTIFY_CENTER:
            //if(_w < dim.width)
            _x -= (dim.width - _w) / 2;
            break;

        case JUSTIFY_RIGHT:
            //if(_w < dim.width)
            _x -= (dim.width - _w);
            break;
        }
        //		}

        _w = _expandOnly ? Math.max(_w, dim.width) : dim.width;
        _h = _expandOnly ? Math.max(_h, dim.height) : dim.height;
    }
}