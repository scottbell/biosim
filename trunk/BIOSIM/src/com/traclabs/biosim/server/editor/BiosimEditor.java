package com.traclabs.biosim.server.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.PropertyConfigurator;
import org.tigris.gef.graph.presentation.JGraph;

/**
 * @author scott
 */
public class BiosimEditor {
    JFrame myMainFrame;
    JGraph myGraph;
    JTabbedPane myTabbedPane;
    
    public BiosimEditor(){
        initLogger();
        buildGui();
    }
    
    
    /**
     * 
     */
    private void buildGui() {
        //init graph
        myGraph = new JGraph();
        myGraph.setDrawingSize(300,200);
        
        //init tabbed pane tool bar
        myTabbedPane = new JTabbedPane();
        JComponent panel1 = makeTextPanel("Panel #1");
        myTabbedPane.addTab("Tab 1", panel1);
        JComponent panel2 = makeTextPanel("Panel #2");
        myTabbedPane.addTab("Tab 2", panel2);
        JComponent panel3 = makeTextPanel("Panel #3");
        myTabbedPane.addTab("Tab 3", panel3);
        //myTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        myMainFrame = new JFrame();
        //do Gridbag
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        myMainFrame.getContentPane().setLayout(gridbag);
        
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        gridbag.setConstraints(myTabbedPane, constraints);
        myMainFrame.getContentPane().add(myTabbedPane);
        
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        gridbag.setConstraints(myGraph, constraints);
        myMainFrame.getContentPane().add(myGraph);
        
        //set size, pack, show
        myMainFrame.setSize(640, 480);
        myMainFrame.pack();
        myMainFrame.setVisible(true);
    }
    
    private JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
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
