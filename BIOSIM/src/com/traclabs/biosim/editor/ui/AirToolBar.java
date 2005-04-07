package com.traclabs.biosim.editor.ui;

import org.tigris.gef.base.CmdCreateNode;

import com.traclabs.biosim.editor.graph.air.NitrogenStoreNode;
import com.traclabs.biosim.editor.graph.air.O2StoreNode;
import com.traclabs.biosim.editor.graph.air.OGSNode;

/**
 * @author scott
 */
public class AirToolBar extends EditorToolBar {

    public AirToolBar() {
        super("Air");
        add(new CmdCreateNode(NitrogenStoreNode.class, "EditorBase", "NitrogenStore"));
        add(new CmdCreateNode(O2StoreNode.class, "EditorBase", "O2Store"));
        add(new CmdCreateNode(OGSNode.class, "EditorBase", "OGS"));
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