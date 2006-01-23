package com.traclabs.biosim.framework;

import javax.swing.ImageIcon;

import com.traclabs.biosim.client.simulation.power.schematic.PowerSchematicFrame;
import com.traclabs.biosim.client.util.BioHolderInitializer;


/**
 * A standalone CEV Power instance (server, nameserver, client in one)
 * 
 * @author Scott Bell
 */

public class CEVPowerStandalone extends BiosimStandalone{

    public CEVPowerStandalone(ImageIcon splashIcon) {
        super(splashIcon, "CEV Power Simulation ", "cev/CEVPowerInit.xml", 0);
    }
    
    public static void main(String args[]) {
        ImageIcon moonIcon = new ImageIcon(BiosimStandalone.class
                .getClassLoader().getResource(
                        "com/traclabs/biosim/framework/moon.png"));
        CEVPowerStandalone myCEVPowerStandalone = new CEVPowerStandalone(moonIcon);
        myCEVPowerStandalone.beginSimulation();
    }
    
    protected void runClient(){
        String[] emptyArgs = new String[0];
        BioHolderInitializer.setFile(getXmlFilename());
        PowerSchematicFrame.main(emptyArgs);
    }
    
}
