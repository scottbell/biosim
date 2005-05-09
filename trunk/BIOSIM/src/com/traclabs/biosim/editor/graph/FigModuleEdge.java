package com.traclabs.biosim.editor.graph;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.log4j.Logger;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.FigEdgeLine;
import org.tigris.gef.presentation.FigLine;

import com.traclabs.biosim.editor.ui.FlowratePropertiesFrame;
import com.traclabs.biosim.editor.ui.SimEnvironmentFlowratePropertiesFrame;
import com.traclabs.biosim.editor.ui.StoreFlowratePropertiesFrame;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations;

public class FigModuleEdge extends FigEdgeLine implements MouseListener {
    private FlowratePropertiesFrame myFlowratePropertiesFrame;
    
    private Logger myLogger;

    public FigModuleEdge() {
        super();
        myLogger = Logger.getLogger(FigModuleEdge.class);
        setDestArrowHead(new ArrowHeadTriangle());
        _useNearest = true;
    }

    public void setBetweenNearestPoints(boolean un) {
    }
    
    /** Compute the shape of the line that presents an Edge. */
    public void computeRoute() {
        ModuleEdge theEdge = (ModuleEdge)getOwner();
        Point sourcePoint = getSourcePortFig().center();
        Point destPoint = getDestPortFig().center();
        if (!theEdge.isProducerEdge()){
            sourcePoint.x += 100;
            sourcePoint.y += 100;
            destPoint.x -= 100;
            destPoint.y -= 100;
        }
        else{
            sourcePoint.x -= 100;
            sourcePoint.y -= 100;
            destPoint.x += 100;
            destPoint.y += 100;
        }

        sourcePoint = _sourceFigNode.connectionPoint(destPoint);
        destPoint = _destFigNode.connectionPoint(sourcePoint);

        ((FigLine) _fig).setShape(sourcePoint, destPoint);
        calcBounds();
    }
    
    public void editProperties(int x, int y){
        if (myFlowratePropertiesFrame == null){
            ModuleEdge theEdge = (ModuleEdge)getOwner();
            if (theEdge.getOperations() instanceof StoreFlowRateControllableOperations)
            	myFlowratePropertiesFrame = new StoreFlowratePropertiesFrame(this, theEdge.getOperations(), theEdge.getStoreImpl());
            else
            	myFlowratePropertiesFrame = new SimEnvironmentFlowratePropertiesFrame(this, theEdge.getOperations(), theEdge.getSimEnvironmentImpl());
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
}