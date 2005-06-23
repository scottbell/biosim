package com.traclabs.biosim.editor.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;

import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.GraphNodeHooks;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.gef.presentation.FigNode;
import org.tigris.gef.presentation.FigPoly;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;

import com.traclabs.biosim.editor.base.EditorLayer;

/**
 * Fig level representation of a EditorNode. Visually, it has
 * a drop shadow fig which can be hidden or shown for decoration, and a
 * background fig for subclasses to draw on. This fig has a single port for both
 * input and output connections. It also has a nested layer which displays its
 * child nodes.
 * 
 * @author kkusy
 */
public abstract class FigModuleNode extends FigNode {
    protected Fig _port;

    protected Fig _bgFig;

    protected Fig _shadowFig;

    protected boolean _showShadow = true;

    protected int _shadowOffset = 2;

    protected Color _shadowColor = Color.GRAY;

    protected EditorLayer _nestedLayer;

    public FigModuleNode() {
        super();
        // Add the port on bottom.
        _port = new FigCircle(37, 25, 0, 0, Color.cyan, Color.cyan);
        addFig(_port);

        _shadowFig = createShadowFig();
        addFig(_shadowFig);

        _bgFig = createBgFig();
        addFig(_bgFig);

        addFigs();
        doLayout();
    }

    protected Fig createBgFig() {
        Fig fig = new FigRect(0, 0, 1, 1, getLineColor(), getFillColor());
        return fig;
    }

    protected Fig createShadowFig() {
        Fig fig = new FigRect(2, 2, 1, 1, Color.GRAY, Color.GRAY);
        return fig;
    }

    protected void addFigs() {
    }

    public void doLayout() {
        //		startTrans();
        Dimension dim = getPreferedSize();
        setHandleBox(_x, _y, dim.width, dim.height);
        endTrans();
    }

    public Rectangle getHandleBox() {
        return _bgFig.getBounds();
    }

    public void setHandleBox(int x, int y, int w, int h) {
        setBounds(x, y, w + _shadowOffset, h + _shadowOffset);
    }

    public Dimension getMinimumSize() {
        return _bgFig.getMinimumSize();
    }

    public Dimension getPreferedSize() {
        return getMinimumSize();
    }

    public void setBounds(int x, int y, int w, int h) {
        _bgFig.setBounds(x, y, w - _shadowOffset, h - _shadowOffset);
        _shadowFig.setBounds(x + _shadowOffset, y + _shadowOffset, w
                - _shadowOffset, h - _shadowOffset);

        Rectangle rect = _bgFig.getBounds();
        Point center = new Point((int) (rect.getX() + rect.getWidth() / 2),
                (int) (rect.getY() + rect.getHeight() / 2));

        // Move the port to the center.
        _port.setBounds(center.x, center.y, 0, 0);

        Rectangle oldBounds = getBounds();
        calcBounds();
        firePropChange("bounds", oldBounds, getBounds());

        updateEdges();
    }

    public void setShowShadow(boolean showShadow) {
        if (showShadow != _showShadow) {
            _shadowFig.setVisible(showShadow);
            _showShadow = showShadow;
        }
    }

    public boolean getShowShadow() {
        return _showShadow;
    }

    public int getShadowOffset() {
        return _shadowOffset;
    }

    public void setShadowOffset(int offset) {
        if (offset >= 0) {
            _shadowOffset = offset;

            if (_showShadow) {
                // Updates the bounds of the node.
                Rectangle rect = getHandleBox();
                setHandleBox(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    public Object getPort() {
        ModuleNode node = (ModuleNode) getOwner();
        return node.getPort();
    }

    public Fig getPortFig() {
        return _port;
    }

    public Object deepHitPort(int x, int y) {
        return getPort();
    }

    public String getPrivateData() {
        return null; //"text=\"" + obj7.getText() + "\"";
    }

    public void setOwner(Object own) {
        if (!(own instanceof ModuleNode))
            return;

        super.setOwner(own);

        ModuleNode node = (ModuleNode) own;
        bindPort(node.getPort(), _port);

        // Tie the nested layer to its GraphModel.
        GraphModel gm = node.getNestedModel();
        if (gm != null) {
            _nestedLayer = new EditorLayer(getText(), (EditorGraphModel) gm, this);
        }

        // Update the fig using net node properties.
        update();
    }

    public Point connectionPoint(Point anotherPt) {
        Rectangle rect = getHandleBox();

        int x = (int) rect.getX();
        int y = (int) rect.getY();
        int w = (int) rect.getWidth();
        int h = (int) rect.getHeight();

        Point corners[] = { new Point(x, y), new Point(x + w, y),
                new Point(x + w, y + h), new Point(x, y + h) };

        Point center = new Point(x + w / 2, y + h / 2);

        // Move the point outside of the rectangle.
        Point tmp = new Point();
        int dx = anotherPt.x - center.x;
        int dy = anotherPt.y - center.y;
        double len = Math.sqrt(dx * dx + dy * dy);
        double radius = Math.max(_w, _h);
        tmp.x = (int) (center.x + radius * dx / len);
        tmp.y = (int) (center.y + radius * dy / len);

        Point intersect = null;

        if (tmp.y < y) {
            // Test top.
            intersect = lineSegmentIntersect(center, tmp, corners[0],
                    corners[1]);

        } else if (tmp.y > y + h) {
            // Test bottom.
            intersect = lineSegmentIntersect(center, tmp, corners[2],
                    corners[3]);
        }

        if (intersect != null) {
            return intersect;
        }

        if (tmp.x < x) {
            // Test left.
            intersect = lineSegmentIntersect(center, tmp, corners[3],
                    corners[0]);
        } else if (tmp.x > x + w) {
            // Test right.
            intersect = lineSegmentIntersect(center, tmp, corners[1],
                    corners[2]);
        }

        if (intersect != null) {
            return intersect;
        }
        return center;
    }

    public EditorLayer getNestedLayer() {
        return _nestedLayer;
    }

    /** When a EditorFigNode is deleted, all of its edges are deleted. */
    public void dispose() {
        List edgeClone = getFigEdges();
        int edgeCount = edgeClone.size();
        for (int edgeIndex = 0; edgeIndex < edgeCount; ++edgeIndex) {
            Fig f = (Fig) edgeClone.get(edgeIndex);
            f.deleteFromModel();
        }

        Object own = getOwner();
        if (own instanceof GraphNodeHooks) {
            ((GraphNodeHooks) own).deleteFromModel();
        } else {
            removeFromDiagram();
        }
    }

    public void setLineColor(Color col) {
        if (col != null) {
            firePropChange("lineColor", super.getLineColor(), col);
            super.setLineColor(col);
            _bgFig.setLineColor(col);
        }
    }

    public void setFillColor(Color col) {
        if (col != null) {
            firePropChange("fillColor", super.getFillColor(), col);
            super.setFillColor(col);
            _bgFig.setFillColor(col);
        }
    }

    public void setShadowColor(Color col) {
        if (col != null) {
            firePropChange("shadowColor", _shadowColor, col);
            _shadowColor = col;
            _shadowFig.setLineColor(col);
            _shadowFig.setFillColor(col);
        }
    }

    public Color getShadowColor() {
        return _shadowColor;
    }

    public String getTag() {
        ModuleNode node = (ModuleNode)getOwner();
        return node.getSimBioModuleImpl().getModuleName();
    }

    protected void setTextFillColor(FigText text, Color col) {
        text.setTextFilled(true);
        text.setTextFillColor(col);
        text.setLineColor(col);
        text.setFillColor(col);
    }

    protected void setTextLineColor(FigText text, Color col) {
        text.setTextColor(col);
    }

    public void update() {
    }

    /**
     * Return the text string displayed for this node
     */
    public String getText() {
        ModuleNode own = (ModuleNode) getOwner();
        if (own != null) {
            return own.getSimBioModuleImpl().getModuleName();
        }
        return getTag();
    }

    /** Returns all source nodes for this node. */
    public List getSourceFigNodes() {
    	List edges = getInBoundEdges();
    	List<Fig> nodes = new Vector<Fig>(edges.size());

        Iterator i = edges.iterator();
        while (i.hasNext()) {
            FigEdge edge = (FigEdge) i.next();
            nodes.add(edge.getSourceFigNode());
        }

        return nodes;
    }

    /** Returns all destination nodes for this node. */
    public List getDestFigNodes() {
        List edges = getOutBoundEdges();
        List<Fig> nodes = new Vector<Fig>(edges.size());

        Iterator i = edges.iterator();
        while (i.hasNext()) {
            FigEdge edge = (FigEdge) i.next();
            nodes.add(edge.getDestFigNode());
        }

        return nodes;
    }

    /** Returns the inbound edges. */
    public List getInBoundEdges() {
        List<FigEdge> in = new ArrayList<FigEdge>();
        Collection edges = getFigEdges(null);
        Iterator i = edges.iterator();

        while (i.hasNext()) {
            FigEdge edge = (FigEdge) i.next();
            if (edge.getDestFigNode() == this) {
                in.add(edge);
            }
        }

        return in;
    }

    /** Returns the inbound edges. */
    public List getOutBoundEdges() {
        ArrayList<FigEdge> out = new ArrayList<FigEdge>();
        Collection edges = getFigEdges(null);
        Iterator i = edges.iterator();

        while (i.hasNext()) {
            FigEdge edge = (FigEdge) i.next();
            if (edge.getSourceFigNode() == this) {
                out.add(edge);
            }
        }

        return out;
    }

    public int getInputCount() {
        return getInBoundEdges().size();
    }

    public int getOutputCount() {
        return getOutBoundEdges().size();
    }

    /**
     * Finds the point where the line extending out from the center of a circle
     * through another point will intersect the circle.
     */
    protected static Point connectionPoint(FigCircle circle, Point anotherPt) {
        Point center = circle.center();
        double a = 0.5 * circle.getWidth();
        double b = 0.5 * circle.getHeight();
        int dx = anotherPt.x - center.x;
        int dy = anotherPt.y - center.y;

        if (dx == 0) {

            if (dy < 0) {
                return new Point((int) (circle.getX() + a), circle.getY());
            } else if (dy > 0) {
                return new Point((int) (circle.getX() + a), circle.getY()
                        + circle.getHeight());
            } else {
                return center;
            }

        }
		double m = (double) dy / dx;
		double a2 = a * a;
		double b2 = b * b;
		double x = Math.sqrt((a2 * b2) / (b2 + a2 * m * m));
		if (dx >= 0) {
		    return new Point((int) (center.x + x), (int) (center.y + m * x));
		}
		return new Point((int) (center.x - x), (int) (center.y - m * x));
    }

    /**
     * Finds the point where the line extending out from the center of a polygon
     * through another point will intersect the polygon.
     */
    public static Point connectionPoint(FigPoly poly, Point anotherPt) {
        List corners = poly.getPointsList();
        Point center = poly.center();

        // Move the point outside of the node.
        Point tmp = new Point();
        int dx = anotherPt.x - center.x;
        int dy = anotherPt.y - center.y;
        double len = Math.sqrt(dx * dx + dy * dy);
        tmp.x = (int) (center.x + Math.max(poly.getWidth(), poly.getHeight())
                * dx / len);
        tmp.y = (int) (center.y + Math.max(poly.getWidth(), poly.getHeight())
                * dy / len);

        Point intersect = null;
        for (int i = 0; i < corners.size(); i++) {
            intersect = lineSegmentIntersect(center, tmp, (Point) corners
                    .get(i), (Point) corners.get((i + 1)
                    % corners.size()));

            if (intersect != null) {
                return intersect;
            }
        }

        return center;
    }

    protected static Point lineSegmentIntersect(Point p1, Point p2, Point p3,
            Point p4) {
        int dx43 = p4.x - p3.x;
        int dy13 = p1.y - p3.y;
        int dy43 = p4.y - p3.y;
        int dx13 = p1.x - p3.x;
        int dx21 = p2.x - p1.x;
        int dy21 = p2.y - p1.y;
        int d = dy43 * dx21 - dx43 * dy21;

        if (d == 0) {
            return null;
        } // parallel

        double ua = (dx43 * dy13 - dy43 * dx13) / (double) d;
        double ub = (dx21 * dy13 - dy21 * dx13) / (double) d;

        if (ua >= 0.0 && ua <= 1.0 && ub >= 0.0 && ub <= 1.0) {
            Point pt = new Point();
            pt.x = (int) (p1.x + ua * (p2.x - p1.x));
            pt.y = (int) (p1.y + ua * (p2.y - p1.y));
            return pt;
        }

        return null;
    }
    
    public void editProperties(int x, int y){
        JFrame myPropertyEditor = getPropertiesEditor();
        myPropertyEditor.setLocation(x, y);
        myPropertyEditor.setVisible(true);
    }

    /**
     * @return
     */
    protected abstract JFrame getPropertiesEditor();

    public void mouseClicked(java.awt.event.MouseEvent me) {
        if (me.getClickCount() == 2){
            me.consume();
            editProperties(me.getX(), me.getY());
        }
    }
    
    public String toString(){
        ModuleNode owner = (ModuleNode)getOwner();
        return owner.getSimBioModuleImpl().getClass().getSimpleName();
    }
    
}
