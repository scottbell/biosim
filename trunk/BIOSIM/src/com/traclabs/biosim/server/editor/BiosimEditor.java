package com.traclabs.biosim.server.editor;

import java.util.Properties;

import javax.swing.JFrame;

import org.apache.log4j.PropertyConfigurator;
import org.tigris.gef.graph.presentation.JGraph;

/**
 * @author scott
 */
public class BiosimEditor {
    JFrame myMainFrame;
    JGraph myGraph;
    
    public BiosimEditor(){
        initLogger();
        buildGui();
    }
    
    
    /**
     * 
     */
    private void buildGui() {
        myGraph = new JGraph();
        myGraph.setDrawingSize(600,400);
        
        myMainFrame = new JFrame();
        myMainFrame.setSize(640, 480);
        myMainFrame.getContentPane().add(myGraph);
        myMainFrame.setVisible(true);
    }


    /**
     * 
     */
    private void initLogger() {
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
    }


    public static void main(String args[]){
        BiosimEditor editor = new BiosimEditor();
    }
}
