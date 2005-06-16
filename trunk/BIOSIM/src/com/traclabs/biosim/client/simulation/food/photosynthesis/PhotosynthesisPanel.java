package com.traclabs.biosim.client.simulation.food.photosynthesis;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.PropertyConfigurator;

import com.traclabs.biosim.client.framework.gui.BioFrame;
import com.traclabs.biosim.client.util.Fnorder;
import com.traclabs.biosim.server.simulation.food.photosynthesis.Chloroplast;

public class PhotosynthesisPanel extends JPanel {

    private JPanel myButtonPanel = new JPanel();

    private JButton myTickButton;

    private JButton myPlayPauseButton;
    
    private Chloroplast myChloroplast = new Chloroplast();
    
    private StromaPanel myStromaPanel;
    
    private LumenPanel myLumenPanel;

    //Icons
    private ImageIcon myPlayIcon;

    private ImageIcon myPauseIcon;

    private ImageIcon myTickIcon;

    public PhotosynthesisPanel() {
        setLayout(new BorderLayout());
        buildGUI();
    }

    private void buildGUI() {
        loadIcons();
        //Actions
        myTickButton = new JButton(new TickButtonAction());
        myTickButton.setIcon(myTickIcon);
        myPlayPauseButton = new JButton(new PlayPauseButtonAction());
        myPlayPauseButton.setIcon(myPlayIcon);
        myButtonPanel.add(myPlayPauseButton);
        myButtonPanel.add(myTickButton);
        myButtonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        myStromaPanel = new StromaPanel(myChloroplast.getStroma());
        myStromaPanel.setBorder(BorderFactory.createTitledBorder("Stroma"));
        myLumenPanel = new LumenPanel(myChloroplast.getThylakoid().getLumen());
        myLumenPanel.setBorder(BorderFactory.createTitledBorder("Lumen"));
        add(myButtonPanel, BorderLayout.NORTH);
        add(myStromaPanel, BorderLayout.EAST);
        add(myLumenPanel, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        Properties logProps = new Properties();
        logProps.setProperty("log4j.rootLogger", "DEBUG, rootAppender");
        logProps.setProperty("log4j.appender.rootAppender",
                "org.apache.log4j.ConsoleAppender");
        logProps.setProperty("log4j.appender.rootAppender.layout",
                "org.apache.log4j.PatternLayout");
        logProps.setProperty(
                "log4j.appender.rootAppender.layout.ConversionPattern",
                "%5p [%c] - %m%n");
        PropertyConfigurator.configure(logProps);
        BioFrame myFrame = new BioFrame("Photosynthesis Model", false);
        myFrame.getContentPane().add(new PhotosynthesisPanel());
        myFrame.pack();
        myFrame.setVisible(true);
    }
    
    private void loadIcons(){
        myTickIcon = new ImageIcon(
                ClassLoader
                        .getSystemClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/forward.png"));
        myPlayIcon = new ImageIcon(
                ClassLoader
                        .getSystemClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/play.png"));
        
        myPauseIcon = new ImageIcon(
                ClassLoader
                        .getSystemClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/pause.png"));
    }

    private void refresh() {
        myStromaPanel.refresh();
        myLumenPanel.refresh();
    }
    
    private class PlayPauseButtonAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            /*
            if (){
                //Pause the sim
            }
            else{
                //Play the sim
                
            }
            */
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private class TickButtonAction extends AbstractAction {
        private TickAction myTickAction = new TickAction();
        
        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            myTickAction.actionPerformed(ae);
            if (ae.getModifiers() == (ActionEvent.CTRL_MASK + 16)) {
                Fnorder myFnord = new Fnorder();
                String message = myFnord.getFnord();
                ImageIcon fnordIcon = new ImageIcon(ClassLoader
                        .getSystemResource("ga/client/handlers/pyramid.gif"));
                JOptionPane fnordPane = new JOptionPane(message,
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION, fnordIcon);
                JDialog dialog = fnordPane.createDialog(null,
                        "Your message from the Illuminati");
                dialog.setVisible(true);
            }
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private class TickAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            myChloroplast.tick();
            refresh();
        }
    }
}