package com.traclabs.biosim.framework;

import javax.swing.ImageIcon;

import com.traclabs.biosim.client.framework.apollo13.Apollo13Viewer;
import com.traclabs.biosim.client.util.BioHolderInitializer;


/**
 * A standalone Apollo13 instance (server, nameserver, client in one)
 * 
 * @author Scott Bell
 */

public class Apollo13Standalone extends BiosimStandalone{

    public Apollo13Standalone(ImageIcon splashIcon) {
        super(splashIcon, "Apollo 13 Simulation ", "com/traclabs/biosim/server/framework/apollo13/Apollo13Init.xml", 0);
    }
    
    public static void main(String args[]) {
        ImageIcon moonIcon = new ImageIcon(BiosimStandalone.class
                .getClassLoader().getResource(
                        "com/traclabs/biosim/framework/moon.png"));
        Apollo13Standalone myApollo13Standalone = new Apollo13Standalone(moonIcon);
        myApollo13Standalone.beginSimulation();
    }
    
    protected void runClient(){
        String[] emptyArgs = new String[0];
        BioHolderInitializer.setFile(getXmlFilename());
        Apollo13Viewer.main(emptyArgs);
    }
    
}
