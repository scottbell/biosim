package com.traclabs.biosim.editor.ui;

import org.tigris.gef.base.CmdCreateNode;

import com.traclabs.biosim.editor.graph.air.CO2StoreNode;
import com.traclabs.biosim.editor.graph.air.CRSNode;
import com.traclabs.biosim.editor.graph.air.H2StoreNode;
import com.traclabs.biosim.editor.graph.air.NitrogenStoreNode;
import com.traclabs.biosim.editor.graph.air.O2StoreNode;
import com.traclabs.biosim.editor.graph.air.OGSNode;
import com.traclabs.biosim.editor.graph.air.VCCRNode;

/**
 * @author scott
 */
public class AirToolBar extends EditorToolBar {

    public AirToolBar() {
        super("Air");
        add(new CmdCreateNode(OGSNode.class, "EditorBase", "OGS"));
        add(new CmdCreateNode(VCCRNode.class, "EditorBase", "VCCR"));
        add(new CmdCreateNode(CRSNode.class, "EditorBase", "CRS"));
        addSeparator();
        add(new CmdCreateNode(O2StoreNode.class, "EditorBase", "O2Store"));
        add(new CmdCreateNode(CO2StoreNode.class, "EditorBase", "CO2Store"));
        add(new CmdCreateNode(H2StoreNode.class, "EditorBase", "H2Store"));
        add(new CmdCreateNode(NitrogenStoreNode.class, "EditorBase", "NitrogenStore"));
        addSeparator();
        //add(new OGSAction());
        /*add(new CH4StoreAction());
        add(new O2StoreAction());
        add(new CO2StoreAction());
        addSeparator();
        add(new CRSAction());
        add(new OGSAction());
        add(new VCCRAction());
        add(new AirRSAction());
        */
    }
}