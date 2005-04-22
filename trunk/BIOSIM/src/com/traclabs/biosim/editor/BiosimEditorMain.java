package com.traclabs.biosim.editor;

import java.util.Locale;
import java.util.Properties;

import javax.swing.ImageIcon;

import org.apache.log4j.PropertyConfigurator;
import org.jacorb.naming.NameServer;
import org.jacorb.util.Environment;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

import com.traclabs.biosim.editor.presentation.EditorFrame;

public class BiosimEditorMain {

    private static final int NAMESERVER_PORT = 16309;

    private static final int SERVER_OA_PORT = 16310;
    
    private Thread myNamingServiceThread;
    
    protected BiosimEditorMain() {
        myNamingServiceThread = new Thread(new NamingServiceThread());
        myNamingServiceThread.start();
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
                                "com/traclabs/biosim/client/framework/gui/biosim.png"));

        // Create and display the main window.
        Environment.setProperty("OAPort", Integer.toString(SERVER_OA_PORT));
        Environment.setProperty("ORBInitRef.NameService",
                "corbaloc::localhost:" + NAMESERVER_PORT + "/NameService");
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
            String[] portArgs = { "-p", Integer.toString(NAMESERVER_PORT) };
            NameServer.main(portArgs);
        }
    }
}
