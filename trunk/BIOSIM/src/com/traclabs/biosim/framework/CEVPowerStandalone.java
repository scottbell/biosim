package com.traclabs.biosim.framework;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.simulation.power.schematic.PowerSchematicFrame;
import com.traclabs.biosim.client.util.BioHolderInitializer;


/**
 * A standalone CEV Power instance (server, nameserver, client in one)
 * 
 * @author Scott Bell
 */

public class CEVPowerStandalone extends BiosimStandalone{
    public CEVPowerStandalone(ImageIcon splashIcon) {
        super(splashIcon, "CEV Power Simulation ", "com/traclabs/biosim/server/framework/cev/CEVPowerInit.xml", 0);
    }
    
    public static void main(String args[]) {
        ImageIcon moonIcon = new ImageIcon(CEVPowerStandalone.class
                .getClassLoader().getResource(
                        "com/traclabs/biosim/framework/moon.png"));
        CEVPowerStandalone myCEVPowerStandalone = new CEVPowerStandalone(moonIcon);
        myCEVPowerStandalone.beginSimulation();
    }
    
    protected void runClient(){
    	Logger.getLogger(CEVPowerStandalone.class).info("starting client");
        String[] emptyArgs = new String[0];
        BioHolderInitializer.setFile(getXmlFilename());
        PowerSchematicFrame newPowerSchematicFrame= new PowerSchematicFrame();
        newPowerSchematicFrame.setSize(1000, 500);
        newPowerSchematicFrame.setLocationRelativeTo(null); 
        newPowerSchematicFrame.setVisible(true);
    }
    
}
