/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */

/*
 * Created on Mar 10, 2005
 */
package com.traclabs.biosim.editor.presentation;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Vector;

import org.tigris.gef.presentation.Fig;

/**
 * GEFCenterLayout centers all figs in the center of the panel stacking them on
 * top of one another.
 * 
 * @author kkusy
 */
public class GEFCenterLayout implements GEFLayoutManager {

    public void layoutContainer(FigPanel parent) {
        Rectangle rect = parent.getBounds();
        Vector children = parent.getContents();
        int nfigs = children.size();
        for (int i = 0; i < nfigs; i++) {
            Fig fig = (Fig) children.get(i);
            Dimension dim = fig.getPreferredSize();
            int x = rect.x + (rect.width - dim.width) / 2;
            int y = rect.y + (rect.height - dim.height) / 2;
            fig.setBounds(x, y, dim.width, dim.height);
        }
    }

    public void addLayoutFig(Fig fig, Object constraints) {
    }

    public Dimension minimumLayoutSize(FigPanel parent) {
        return preferredLayoutSize(parent);
    }

    public Dimension preferredLayoutSize(FigPanel parent) {
        Insets insets = parent.getInsets();
        Vector children = parent.getContents();
        int nfigs = children.size();
        Dimension result = new Dimension(0, 0);
        for (int i = 0; i < nfigs; i++) {
            Fig fig = (Fig) children.get(i);
            Dimension dim = fig.getPreferredSize();
            result.width = Math.max(result.width, dim.width);
            result.height = Math.max(result.height, dim.height);
        }
        result.width += insets.left + insets.right;
        result.height += insets.top + insets.bottom;
        return result;
    }

    public void removeLayoutFig(Fig fig) {
    }
}