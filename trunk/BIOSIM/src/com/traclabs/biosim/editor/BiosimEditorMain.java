package com.traclabs.biosim.editor;

import java.util.Locale;
import java.util.Properties;

import javax.swing.ImageIcon;

import org.apache.log4j.PropertyConfigurator;
import org.jacorb.config.Configuration;
import org.jacorb.naming.NameServer;
import org.jacorb.orb.ORB;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

import com.traclabs.biosim.editor.presentation.EditorFrame;
import com.traclabs.biosim.server.util.OrbUtils;

public class BiosimEditorMain {

    private int nameserverPort = 16309;

    private int serverOAPort = 16310;
    
    private Thread myNamingServiceThread;
    
    protected BiosimEditorMain() {
        myNamingServiceThread = new Thread(new NamingServiceThread());
        myNamingServiceThread.start();
        OrbUtils.sleepAwhile();
        Localizer.addResource("GefBase",
                "org.tigris.gef.base.BaseResourceBundle");
        Localizer.addResource("GefPres",
                "org.tigris.gef.presentation.PresentationResourceBundle");
        //Localizer.addResource("EditorBase","com.traclabs.biosim.editor.base.BaseResourceBundle");
        Localizer.addLocale(Locale.getDefault());
        Localizer.switchCurrentLocale(Locale.getDefault());
        ResourceLoader.addResourceExtension("gif");
        ResourceLoader.addResourceExtension("png");
        ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
        //ResourceLoader.addResourceLocation("/com/traclabs/biosim/editor");

        ImageIcon biosimIcon = new ImageIcon(
                ClassLoader
                        .getSystemClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/biosim.png"));

        // Create and display the main window.
        /*
        Environment.setProperty("OAPort", Integer.toString(serverOAPort));
        Environment.setProperty("ORBInitRef.NameService",
                "corbaloc::localhost:" + nameserverPort + "/NameService");
            */    
        ORB jacorbOrb = (ORB)OrbUtils.getORB();
        Configuration theConfiguration = jacorbOrb.getConfiguration();
        theConfiguration.setAttribute("OAPort", Integer.toString(serverOAPort));
        theConfiguration.setAttribute("ORBInitRef.NameService", "corbaloc::localhost:" + nameserverPort + "/NameService");
        //How do we get this OAPort to the ORB??
        
        EditorFrame frame = new EditorFrame("Biosim Editor");
        frame.setIconImage(biosimIcon.getImage());
        frame.setSize(830, 600);
        frame.setVisible(true);
        
    }
    
    public static void main(String args[]) {
        Properties logProps = new Properties();
        logProps.setProperty("log4j.rootLogger", "INFO, rootAppender");
        logProps.setProperty("log4j.appender.rootAppender",
                "org.apache.log4j.ConsoleAppender");
        logProps.setProperty("log4j.appender.rootAppender.layout",
                "org.apache.log4j.PatternLayout");
        logProps.setProperty(
                "log4j.appender.rootAppender.layout.ConversionPattern",
                "%5p [%c] - %m%n");
        PropertyConfigurator.configure(logProps);
        BiosimEditorMain app = new BiosimEditorMain();
    }
    
    private class NamingServiceThread implements Runnable {
        public void run() {
            String[] portArgs = {"-DOAPort="+Integer.toString(nameserverPort)};
            NameServer.main(portArgs);
        }
    }
}
