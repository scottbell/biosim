package com.traclabs.biosim.editor.graph;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.log4j.Logger;
import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.FigEdgeLine;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigText;

import com.traclabs.biosim.editor.ui.FlowratePropertiesFrame;
import com.traclabs.biosim.editor.ui.SimEnvironmentFlowratePropertiesFrame;
import com.traclabs.biosim.editor.ui.StoreFlowratePropertiesFrame;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations;

public class FigModuleEdge extends FigEdgeLine implements MouseListener {
    private FlowratePropertiesFrame myFlowratePropertiesFrame;
    
    private Logger myLogger;
    
    private FigText myTextDescription;
    
    private static String SENSED = "sensed";
    private static String ACTUATED = "actuated";
    private static String SENSED_AND_ACTUATED = "sensed/actuated";

    private boolean myFlowSensed;

    private boolean myFlowActuated;
    
    private ModuleEdge myModuleEdge;

    public FigModuleEdge() {
        super();
        myLogger = Logger.getLogger(FigModuleEdge.class);
        setDestArrowHead(new ArrowHeadTriangle());
        _useNearest = true;
        myTextDescription = new FigText(10, 30, 90, 20);  	 
        myTextDescription.setText(""); 	 
        myTextDescription.setTextColor(Color.black); 	 
        myTextDescription.setTextFilled(false); 	 
        myTextDescription.setFilled(false); 	 
        myTextDescription.setLineWidth(0); 	 
        addPathItem(myTextDescription, new PathConvPercent(this, 50, 10));
        myModuleEdge = (ModuleEdge)getOwner();
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
    
    public void setIsSensed(boolean pFlowSensed) {
        if (myFlowSensed == pFlowSensed)
            return;
        myFlowSensed = pFlowSensed;
        if (myFlowSensed)
            myModuleEdge.addSensor();
        else
            myModuleEdge.removeSensor();
        computeDescription();
    } 	 

    public void setIsActuated(boolean pFlowActuated) { 	 
        if (myFlowActuated == pFlowActuated)
            return;
        myFlowActuated = pFlowActuated;
        if (myFlowActuated)
            myModuleEdge.addActuator();
        else
            myModuleEdge.removeActuator();
        computeDescription();
    }
 	 
    /**
     * 
     */
    private void computeDescription() {
        if (myFlowSensed && myFlowActuated)
            myTextDescription.setText(SENSED_AND_ACTUATED);
        else if (myFlowSensed)
            myTextDescription.setText(SENSED);
        else if (myFlowActuated)
            myTextDescription.setText(ACTUATED);
        else
            myTextDescription.setText("");
    }
    
    public void editProperties(int x, int y){
        if (myFlowratePropertiesFrame == null){
            if (myModuleEdge.getOperations() instanceof StoreFlowRateControllableOperations)
            	myFlowratePropertiesFrame = new StoreFlowratePropertiesFrame(this, myModuleEdge.getOperations(), myModuleEdge.getStoreImpl());
            else
            	myFlowratePropertiesFrame = new SimEnvironmentFlowratePropertiesFrame(this, myModuleEdge.getOperations(), myModuleEdge.getSimEnvironmentImpl());
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