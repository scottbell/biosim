package com.traclabs.biosim.client.simulation.food.photosynthesis;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.traclabs.biosim.client.framework.gui.BioFrame;
import com.traclabs.biosim.client.util.Fnorder;
import com.traclabs.biosim.server.simulation.food.photosynthesis.Chloroplast;
import com.traclabs.biosim.util.OrbUtils;

public class PhotosynthesisPanel extends JPanel {

    private JPanel myButtonPanel = new JPanel();

    private JButton myTickButton;
    
    private final static String myTickToolTipText = "Advance the simulation one tick";

    private JButton myPlayPauseButton;
    private final static String myPlayToolTipText = "Run the simulation continuously";
    private final static String myPauseToolTipText = "Pause the simulation";
    
    private Chloroplast myChloroplast = new Chloroplast();
    
    private StromaPanel myStromaPanel;
    
    private LumenPanel myLumenPanel;
    
    private EnergyLevelPanel myActivityPanel;
    
    private JPanel myGraphPanel;

    private Timer myTickTimer;

    //Icons
    private ImageIcon myPlayIcon;

    private ImageIcon myPauseIcon;

    private ImageIcon myTickIcon;
    
    private final static int TICK_DELAY = 200;

    public PhotosynthesisPanel() {
        buildGUI();
    }

    private void buildGUI() {
        loadIcons();
        myTickTimer = new Timer(TICK_DELAY, new TickAction());
        myTickButton = new JButton(new TickButtonAction());
        myTickButton.setIcon(myTickIcon);
        myTickButton.setToolTipText(myTickToolTipText);
        myPlayPauseButton = new JButton(new PlayPauseButtonAction());
        myPlayPauseButton.setIcon(myPlayIcon);
        myPlayPauseButton.setToolTipText(myPlayToolTipText);
        myButtonPanel.add(myPlayPauseButton);
        myButtonPanel.add(myTickButton);
        myButtonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        myStromaPanel = new StromaPanel(myChloroplast.getStroma());
        myStromaPanel.setBorder(BorderFactory.createTitledBorder("Stroma"));
        myLumenPanel = new LumenPanel(myChloroplast.getThylakoid().getLumen());
        myLumenPanel.setBorder(BorderFactory.createTitledBorder("Lumen"));
        myActivityPanel = new EnergyLevelPanel(myChloroplast.getThylakoid().getMembrane());
        myActivityPanel.setBorder(BorderFactory.createTitledBorder("Activity"));
        
        myGraphPanel = new JPanel();
        myGraphPanel.setLayout(new GridLayout(1,3));
        myGraphPanel.add(myStromaPanel);
        myGraphPanel.add(myLumenPanel);
        myGraphPanel.add(myActivityPanel);
        
        setLayout(new BorderLayout());
        add(myButtonPanel, BorderLayout.NORTH);
        add(myGraphPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        OrbUtils.initializeLog();
        BioFrame myFrame = new BioFrame("Photosynthesis Model", false);
        myFrame.getContentPane().add(new PhotosynthesisPanel());
        myFrame.pack();
        myFrame.setSize(800, 600);
        ImageIcon biosimIcon = new ImageIcon(PhotosynthesisPanel.class.getClassLoader().getResource("com/traclabs/biosim/client/food/food.png"));
        myFrame.setIconImage(biosimIcon.getImage());
        myFrame.setVisible(true);
    }
    
    private void loadIcons(){
        myTickIcon = new ImageIcon(
                PhotosynthesisPanel.class.getClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/forward.png"));
        myPlayIcon = new ImageIcon(
                PhotosynthesisPanel.class.getClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/play.png"));
        
        myPauseIcon = new ImageIcon(
                PhotosynthesisPanel.class.getClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/pause.png"));
    }

    private void refresh() {
        myStromaPanel.refresh();
        myLumenPanel.refresh();
        myActivityPanel.refresh();
    }
    
    private class PlayPauseButtonAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            getTopLevelAncestor().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (myTickTimer.isRunning()){
                //pause the sim
                myTickTimer.stop();
                myPlayPauseButton.setIcon(myPlayIcon);
                myPlayPauseButton.setToolTipText(myPlayToolTipText);
                myTickButton.setEnabled(true);
            }
            else{
                //start sim
                myTickTimer.start();
                myPlayPauseButton.setIcon(myPauseIcon);
                myPlayPauseButton.setToolTipText(myPauseToolTipText);
                myTickButton.setEnabled(false);
            }
            getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
        }
    }

    private class TickButtonAction extends AbstractAction {
        private TickAction myTickAction = new TickAction();
        
        public void actionPerformed(ActionEvent ae) {
            getTopLevelAncestor().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            myTickAction.actionPerformed(ae);
            if (ae.getModifiers() == (ActionEvent.CTRL_MASK + 16)) {
                Fnorder myFnord = new Fnorder();
                String message = myFnord.getFnord();
                ImageIcon fnordIcon = new ImageIcon(PhotosynthesisPanel.class.getClassLoader().getResource("com/traclabs/biosim/client/framework/pyramid.png"));
                JOptionPane fnordPane = new JOptionPane(message,
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION, fnordIcon);
                JDialog dialog = fnordPane.createDialog(null,
                        "Your message from the Illuminati");
                dialog.setVisible(true);
            }
            getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
        }
    }

    private class TickAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            myChloroplast.tick();
            refresh();
        }
    }
}