package com.traclabs.biosim.editor.graph;

import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.presentation.FigEdge;

import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;

/**
 * A sample NetEdge subclass for use in the demos. This edge is drawn with an
 * arrowhead.
 */

public class ModuleEdge extends NetEdge {
    private GenericActuator actuator;
    private GenericSensor sensor;
    
    /** Construct a new SampleEdge. */
    public ModuleEdge() {
    } /* needs-more-work */

    public String getId() {
        return toString();
    }

    public FigEdge makePresentation(Layer lay) {
        return new ModuleFigEdge();
    }

} /* end class EditorEdge */
