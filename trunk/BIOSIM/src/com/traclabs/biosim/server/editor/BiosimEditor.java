package com.traclabs.biosim.server.editor;

import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.tigris.gef.graph.presentation.JGraphFrame;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

/**
 * @author scott
 */
public class BiosimEditor {
    JGraphFrame myFrame;
    
    public BiosimEditor(){
        //init logger
        Properties logProps = new Properties();
        logProps.setProperty("log4j.appender.editorAppender",
                "org.apache.log4j.ConsoleAppender");
        logProps.setProperty("log4j.appender.editorAppender.layout",
                "org.apache.log4j.PatternLayout");
        logProps.setProperty(
                "log4j.appender.editorAppender.layout.ConversionPattern",
                "%5p [%c] - %m%n");
        logProps.setProperty("log4j.logger.org.tigris.gef","INFO, editorAppender");
        logProps.setProperty("log4j.logger.com.traclabs.biosim","INFO, editorAppender");
        PropertyConfigurator.configure(logProps);
        // init localizer and resourceloader
        Localizer.addResource("GefBase","org.tigris.gef.base.BaseResourceBundle");
        Localizer.addResource("GefPres","org.tigris.gef.presentation.PresentationResourceBundle");
        Localizer.addLocale(Locale.getDefault());
        Localizer.switchCurrentLocale(Locale.getDefault());
        ResourceLoader.addResourceExtension("gif");
        ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
        myFrame = new JGraphFrame();
        myFrame.setVisible(true);
    }
    
    public static void main(String args[]){
        BiosimEditor editor = new BiosimEditor();
    }
}
