/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.editor.base;

import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.Selection;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.graph.presentation.NetNode;
import org.tigris.gef.graph.presentation.NetPort;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.EditorFigNode;
import com.traclabs.biosim.editor.graph.EditorNode;
import com.traclabs.biosim.editor.graph.EditorPort;

/**
 * Rearrange the selected node and all nodes that it leads to. Arrange them into
 * a tree structure. The selected node will not be moved; it will be the root of
 * the tree. Any nodes that have edges leading TO the selected node will not be
 * affected.
 */
public class CmdTreeLayout extends Cmd {

    // Horizontal and Vertical Sep are the separation between nodes
    // in the tree.
    private static final int HorizontalSep = 40;

    private static final int VerticalSep = 30;

    public CmdTreeLayout() {
        super("VesprBase", "TreeLayout");
    }

    public CmdTreeLayout(ImageIcon icon) {
        super(null, "VesprBase", "TreeLayout", icon);
    }

    public void doIt() {
        VesprEditor ed = (VesprEditor) Globals.curEditor();

        Vector selection = ed.getSelectionManager().selections();
        Enumeration sel = selection.elements();
        while (sel.hasMoreElements()) {
            Selection s = (Selection) sel.nextElement();
            Fig f = s.getContent();
            if (f instanceof EditorFigNode) {
                EditorNode source = (EditorNode) f.getOwner();
                Rectangle rect = getBoundingBox((EditorFigNode) f);

                arrangeRoot((EditorFigNode) f, rect);
            }
        }
        // Must force a refresh here
        ed.damageAll();
    }

    /*
     * Given an edge, return the destination node of the edge which is on the
     * given layer.
     */
    private EditorFigNode getDestination(NetEdge edge, Layer layer) {
        NetPort destPort = edge.getDestPort();
        NetNode destNode = destPort.getParentNode();
        FigNode destFig = destNode.presentationFor(layer);

        return (EditorFigNode) destFig;
    }

    /*
     * Arrange this node and all of its children as a tree. When arranging the
     * tree, the root object is not moved. The given rect contains space for the
     * entire tree. The top of the rect should be at the same location as the
     * top of the root. The children will be placed starting at (Height of
     * parent) + VerticalSep below the root.
     */
    private void arrangeRoot(EditorFigNode fig, Rectangle rect) {

        // Child Rect is the portion of rect which will be used
        // to place the (remaining) children.
        // UsedChildRect is the portion of
        // the rect that has already be used to place children.
        Rectangle childRect = getChildRect(fig, rect);
        Rectangle usedChildRect = new Rectangle(childRect.x, childRect.y, 0,
                childRect.height);

        // For each outbound edge...
        Vector edges = getOutboundEdges(fig);
        Enumeration e = edges.elements();
        while (e.hasMoreElements()) {
            NetEdge edge = (NetEdge) e.nextElement();
            NetPort edgeSrc = (NetPort) edge.getSourcePort();
            EditorFigNode vesprDestFig = getDestination(edge, fig.getLayer());
            // Arrange the child.
            Rectangle thisChildRect = arrange(vesprDestFig, childRect);

            // Update UsedChildRect and ChildRect to reflect
            // the space taken by this child.
            usedChildRect.width = thisChildRect.x + thisChildRect.width;
            childRect.width -= thisChildRect.width + HorizontalSep;
            childRect.x += thisChildRect.width + HorizontalSep;
        }
    }

    /*
     * Arrange this node and all of its children into the given rectangle. The
     * arranged nodes should fit into the leftmost portion the rect. Return the
     * rectangle that the arranged nodes used.
     */
    private Rectangle arrange(EditorFigNode fig, Rectangle rect) {

        // The 'childRect' is the rectangle that the children must
        // be placed into
        // The 'usedChildRect' is that portion of the 'childRect'
        // that has already been used. Note the childRect is used
        // from left to right.
        // The 'thisChildRect' is the amount used by the currently
        // arranged child.
        Rectangle childRect = getChildRect(fig, rect);
        Rectangle usedChildRect = new Rectangle(childRect.x, childRect.y, 0,
                childRect.height);

        Vector edges = getOutboundEdges(fig);
        Enumeration e = edges.elements();
        while (e.hasMoreElements()) {
            NetEdge edge = (NetEdge) e.nextElement();
            NetPort edgeSrc = (NetPort) edge.getSourcePort();
            EditorFigNode vesprDestFig = getDestination(edge, fig.getLayer());
            Rectangle thisChildRect = arrange(vesprDestFig, childRect);

            // Update the child rect to have the remainder
            childRect.width -= thisChildRect.width + HorizontalSep;
            childRect.x += thisChildRect.width + HorizontalSep;
            usedChildRect.width = (thisChildRect.x - rect.x)
                    + thisChildRect.width;
        }

        // Move this node to its new location. If there are no
        // children, its left edge should match the left edge of
        // 'rect'. If it has children, its center should be in the
        // middle of 'rect', which means its left edge should be
        // (width of rectangle * 0.5) - (width of node * 0.5)
        int x; // X coordinate==coord of left edge
        if (usedChildRect.width == 0) {
            x = rect.x;
        } else {
            x = rect.x + (int) Math.round(0.5 * usedChildRect.width)
                    - (int) Math.round(0.5 * fig.getBounds().width);
        }

        fig.setLocation(x, rect.y);

        // Return a Rectangle representing the total space taken
        // by this object and its children.
        return new Rectangle(rect.x, rect.y, Math.max(fig.getBounds().width,
                usedChildRect.width), rect.height);
    }

    /*
     * When laying out a tree into a rectangle, the children of the object will
     * go into the lower part of the rectangle (below the parent and the
     * Vertical Separation).
     */
    private Rectangle getChildRect(EditorFigNode fig, Rectangle rect) {
        int verticalSpace = fig.getBounds().height + VerticalSep;

        return new Rectangle(rect.x, rect.y + verticalSpace, rect.width,
                rect.height - verticalSpace);
    }

    /*
     * Given a node, find all of the edges that lead away from the node. Return
     * a vector of NetEdge objects.
     */
    private Vector getOutboundEdges(EditorFigNode fig) {
        EditorNode source = (EditorNode) fig.getOwner();
        Vector outboundEdges = new Vector();
        EditorPort port = (EditorPort) source.getPort();
        Vector edges = port.getEdges();
        Enumeration e = edges.elements();
        while (e.hasMoreElements()) {
            NetEdge edge = (NetEdge) e.nextElement();
            NetPort edgeSrc = (NetPort) edge.getSourcePort();
            if (port == edgeSrc) {
                /*
                 * This edge must lead from the selected node away
                 */
                outboundEdges.add(edge);
            }
        }

        return outboundEdges;
    }

    /*
     * Given a node, return a rectangle that would fit that node and all of its
     * children if they were laid out as a tree. Note that the (x, y) value of
     * the rectangle is irrelevant; all that we are concerned with is (width,
     * height); the size of the necessary rectangle.
     */
    private Rectangle getBoundingBox(EditorFigNode fig) {

        // Child Boxes will be a Vector of Rectangles, each large
        // enough to hold one of the children of Fig.
        Vector childBoxes = new Vector();

        Rectangle rect = fig.getBounds();
        Vector edges = getOutboundEdges(fig);
        Enumeration e = edges.elements();
        while (e.hasMoreElements()) {
            NetEdge edge = (NetEdge) e.nextElement();
            NetPort edgeSrc = (NetPort) edge.getSourcePort();
            EditorFigNode vesprDestFig = getDestination(edge, fig.getLayer());
            childBoxes.add(getBoundingBox(vesprDestFig));
        }

        /*
         * Now, rect is the bounding box for this Fig, and all the children
         * bounding boxes are in childBoxes. The total width is the maximum of
         * the Figs width
         */
        int width = getTotalWidth(rect, childBoxes);
        int height = getTotalHeight(rect, childBoxes);
        int x = getTotalX(rect, width);
        int y = getTotalY(rect);

        return new Rectangle(x, y, width, height);
    }

    /*
     * Parent is the bounding box of the parent fig, and children is a Vector of
     * Rectangles of the child bounding boxes. The total width is the maximum of
     * the Parent's width or the sum of the child widths, with HorizontalSep
     * added between each pair of children.
     */
    private int getTotalWidth(Rectangle parent, Vector children) {
        int childWidth = 0;

        Enumeration e = children.elements();
        while (e.hasMoreElements()) {
            Rectangle childRect = (Rectangle) e.nextElement();
            if (childWidth == 0) {
                childWidth = (int) childRect.width;
            } else {
                childWidth += childRect.width + HorizontalSep;
            }
        }

        return Math.max(parent.width, childWidth);
    }

    /*
     * Parent is the bounding box of the parent fig, and children is a Vector of
     * Rectangles of the child bounding boxes. The total height is the height of
     * the parent, plus the VerticalSep, plus the maximum of the heights of the
     * children.
     */
    private int getTotalHeight(Rectangle parent, Vector children) {
        int childHeight = 0;

        /* Find the greatest height of all children */
        Enumeration e = children.elements();
        while (e.hasMoreElements()) {
            Rectangle childRect = (Rectangle) e.nextElement();
            if (childHeight < childRect.height) {
                childHeight = childRect.height;
            }
        }

        return parent.height + VerticalSep + childHeight;
    }

    /*
     * Parent is the bounding box of the parent fig, and width is the width of
     * the total bounding box for parent and children. The X dimension is the X
     * dimension of the parent, plus 1/2 the width of the parent, minus 1/2 the
     * width of the total box.
     */
    private int getTotalX(Rectangle parent, int totalWidth) {
        int width = (int) Math.round(parent.x + 0.5 * parent.width - 0.5
                * totalWidth);
        return width;
    }

    /*
     * Parent is the bounding box of the parent fig. The Y dimension is the Y
     * dimension of the parent.
     */
    private int getTotalY(Rectangle parent) {
        return parent.y;
    }

    public void undoIt() {
    }
}