package com.traclabs.biosim.editor.graph;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.FigEdgeLine;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigText;

import com.traclabs.biosim.idl.simulation.framework.ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

public class ModuleFigEdge extends FigEdgeLine implements MouseListener {
    private FigText mid;
    
    private FlowratePropertiesFrame myFlowratePropertiesFrame;
    
    private final static String getString = "get";
    private final static String consumerDefinitonString = "ConsumerDefiniton";
    private final static String producerDefinitonString = "ProducerDefiniton";

    public ModuleFigEdge() {
        super();
        setDestArrowHead(new ArrowHeadTriangle());
        mid = new FigText(10, 30, 90, 20);
        mid.setText("");
        mid.setTextColor(Color.black);
        mid.setTextFilled(false);
        mid.setFilled(false);
        mid.setLineWidth(0);
        addPathItem(mid, new PathConvPercent(this, 50, 10));

        _useNearest = true;
    }

    public void setBetweenNearestPoints(boolean un) {
    }

    public String getText() {
        return mid.getText();
    }

    public void setText(String text) {
        mid.setText(text);
    }
    
    

    /** Compute the shape of the line that presents an Edge. */
    public void computeRoute() {
        Point srcPt = getSourcePortFig().center();
        Point dstPt = getDestPortFig().center();

        srcPt = _sourceFigNode.connectionPoint(dstPt);
        dstPt = _destFigNode.connectionPoint(srcPt);

        ((FigLine) _fig).setShape(srcPt, dstPt);
        calcBounds();
    }
    
    public void editProperties(int x, int y){
        if (myFlowratePropertiesFrame == null){
            myFlowratePropertiesFrame = new FlowratePropertiesFrame(this, getOperations());
            myFlowratePropertiesFrame.pack();
        }
        myFlowratePropertiesFrame.setLocation(x, y);
        myFlowratePropertiesFrame.setVisible(true);
    }

    /**
     * @return
     */
    private StoreFlowRateControllableOperations getOperations() {
        EditorPort sourcePort = (EditorPort)getSourcePortFig().getOwner();
        EditorPort destPort = (EditorPort)getDestPortFig().getOwner();
        
        if (sourcePort.getParent() instanceof ActiveNode){
            //we're producing
            SimBioModuleImpl theSimBioModuleImpl = ((ActiveNode)sourcePort.getParent()).getSimBioModuleImpl();
            PassiveNode thePassiveNode = (PassiveNode)(destPort.getParent());
            Class[] theProducersAllowed = thePassiveNode.getProducersAllowed();
            for (int i = 0; i < theProducersAllowed.length; i++){
                if (theProducersAllowed[i].isInstance(theSimBioModuleImpl)){
                    return (StoreFlowRateControllableOperations)(theProducersAllowed[i].cast(theSimBioModuleImpl));
                }
            }
        }
        else if (destPort.getParent() instanceof ActiveNode){
            //we're consuming
            SimBioModuleImpl theSimBioModuleImpl = ((ActiveNode)destPort.getParent()).getSimBioModuleImpl();
            PassiveNode thePassiveNode = (PassiveNode)(sourcePort.getParent());
            Class[] theConsumersAllowed = thePassiveNode.getConsumersAllowed();
            for (int i = 0; i < theConsumersAllowed.length; i++){
                if (theConsumersAllowed[i].isInstance(theSimBioModuleImpl)){
                    //do tricky string manipulation to get correct definition
                    ConsumerOperations theOperations = (ConsumerOperations)(theConsumersAllowed[i].cast(theSimBioModuleImpl));
                    //return (StoreFlowRateControllableOperations));
                    return null;
                }
            }
        }
        return null;
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