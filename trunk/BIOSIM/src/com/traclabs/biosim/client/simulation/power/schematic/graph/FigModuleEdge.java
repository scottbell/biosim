package com.traclabs.biosim.client.simulation.power.schematic.graph;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.FigEdgeRectiline;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigText;

import com.traclabs.biosim.client.simulation.power.schematic.ui.FlowratePropertiesFrame;

public class FigModuleEdge extends FigEdgeRectiline implements MouseListener {
    private FlowratePropertiesFrame myFlowratePropertiesFrame;
    
    private FigText myTextDescription;

    public FigModuleEdge() {
        super();
        setDestArrowHead(new ArrowHeadTriangle());
        _useNearest = true;
        myTextDescription = new FigText(10, 30, 90, 20);  	 
        myTextDescription.setText(""); 	 
        myTextDescription.setTextColor(Color.black); 	 
        myTextDescription.setFontFamily("Helvetica");
        myTextDescription.setTextFilled(false); 	 
        myTextDescription.setFilled(false); 	 
        myTextDescription.setLineWidth(0); 	 
        addPathItem(myTextDescription, new PathConvPercent(this, 50, 10));
    }

    public void setBetweenNearestPoints(boolean un) {
    }
    
	public void computeRoute() {
		Point srcPt = getSourcePortFig().getCenter();
        Point dstPt = getDestPortFig().getCenter();

        srcPt = _sourceFigNode.connectionPoint(dstPt);
        dstPt = _destFigNode.connectionPoint(srcPt);

        ((FigLine) _fig).setShape(srcPt, dstPt);
        calcBounds();
    } 
    
    public void editProperties(int x, int y){
        if (myFlowratePropertiesFrame == null){
            ModuleEdge theModuleEdge = (ModuleEdge)getOwner();
            myFlowratePropertiesFrame.pack();
        }
        myFlowratePropertiesFrame.setLocation(x, y);
        myFlowratePropertiesFrame.setVisible(true);
    }
    

    public void mouseClicked(MouseEvent me) {
        if (me.getClickCount() == 2){
            me.consume();
            editProperties(me.getX(), me.getY());
        }
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }
    
    public String toString(){
        ModuleEdge owner = (ModuleEdge)getOwner();
        return owner.getName();
    }
}