package com.traclabs.biosim.editor;

import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

import com.traclabs.biosim.editor.presentation.EditorFrame;

public class BiosimEditorMain {
    
    protected BiosimEditorMain() {
        Localizer.addResource("GefBase",
                "org.tigris.gef.base.BaseResourceBundle");
        Localizer.addResource("GefPres",
                "org.tigris.gef.presentation.PresentationResourceBundle");
        Localizer.addResource("EditorBase","com.traclabs.biosim.editor.base.BaseResourceBundle");
        Localizer.addLocale(Locale.getDefault());
        Localizer.switchCurrentLocale(Locale.getDefault());
        ResourceLoader.addResourceExtension("gif");
        ResourceLoader.addResourceExtension("png");
        ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
        ResourceLoader
                .addResourceLocation("/com/traclabs/biosim/editor");

        // Create and display the main window.
        EditorFrame frame = new EditorFrame("Biosim Editor");
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
}
