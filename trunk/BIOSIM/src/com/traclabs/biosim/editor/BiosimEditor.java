/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/**
 * Copyright (c) 2001 by S&amp;K Electronics. All rights reserved.
 */

package com.traclabs.biosim.editor;

import java.util.Locale;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

import com.traclabs.biosim.editor.presentation.EditorFrame;

public class BiosimEditor {
    ////////////////////////////////////////////////////////////////
    // constructors
    protected BiosimEditor() {
        // Set the images directory.
        // If this is not set properly, you get a 'Icon for xxx not found'
        // message
        Localizer.addResource("GefBase",
                "org.tigris.gef.base.BaseResourceBundle");
        Localizer.addResource("GefPres",
                "org.tigris.gef.presentation.PresentationResourceBundle");
        Localizer.addResource("VesprBase",
                "com.traclabs.biosim.editor.base.BaseResourceBundle");
        Localizer.addLocale(Locale.getDefault());
        Localizer.switchCurrentLocale(Locale.getDefault());
        ResourceLoader.addResourceExtension("gif");
        ResourceLoader.addResourceExtension("png");
        ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
        ResourceLoader
                .addResourceLocation("/com/traclabs/biosim/editor");

        // Create and display the main window.
        EditorFrame frame = new EditorFrame("Biosim Editor");
        frame.setBounds(10, 10, 640, 480);
        frame.setVisible(true);
    }

    ////////////////////////////////////////////////////////////////
    // main
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
        BiosimEditor app = new BiosimEditor();
    }
} /* end class VESPR */
