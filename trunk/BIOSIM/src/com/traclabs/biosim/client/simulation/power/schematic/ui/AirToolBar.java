package com.traclabs.biosim.client.simulation.power.schematic.ui;

import com.traclabs.biosim.client.simulation.power.schematic.base.CmdCreateModuleNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.air.CO2StoreNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.air.CRSNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.air.H2StoreNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.air.MethaneStoreNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.air.NitrogenStoreNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.air.O2StoreNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.air.OGSNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.air.VCCRNode;

/**
 * @author scott
 */
public class AirToolBar extends EditorToolBar {

    public AirToolBar() {
        super("Air");
        add(new CmdCreateModuleNode(OGSNode.class, "EditorBase", "OGS"));
        add(new CmdCreateModuleNode(VCCRNode.class, "EditorBase", "VCCR"));
        add(new CmdCreateModuleNode(CRSNode.class, "EditorBase", "CRS"));
        addSeparator();
        add(new CmdCreateModuleNode(O2StoreNode.class, "EditorBase", "O2Store"));
        add(new CmdCreateModuleNode(CO2StoreNode.class, "EditorBase", "CO2Store"));
        add(new CmdCreateModuleNode(H2StoreNode.class, "EditorBase", "H2Store"));
        add(new CmdCreateModuleNode(NitrogenStoreNode.class, "EditorBase", "NitrogenStore"));
        add(new CmdCreateModuleNode(MethaneStoreNode.class, "EditorBase", "MethaneStore"));
        addSeparator();
    }
}