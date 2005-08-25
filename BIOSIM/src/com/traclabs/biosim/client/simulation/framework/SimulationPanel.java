package com.traclabs.biosim.client.simulation.framework;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;

import com.traclabs.biosim.client.util.Fnorder;

public abstract class SimulationPanel extends JPanel {

    private JToolBar myButtonBar = new JToolBar();

    private JButton myResetButton;
    
    private final static String myResetToolTipText = "Reset the simulation.";

    private JButton myTickButton;
    
    private final static String myTickToolTipText = "Advance the simulation one tick";

    private JButton myPlayPauseButton;
    private final static String myPlayToolTipText = "Run the simulation continuously";
    private final static String myPauseToolTipText = "Pause the simulation";
 
    private Timer myTickTimer;

    //Icons
    private ImageIcon myResetIcon;
    
    private ImageIcon myPlayIcon;

    private ImageIcon myPauseIcon;

    private ImageIcon myTickIcon;
    
    private final static int TICK_DELAY = 200;

    public SimulationPanel() {
        buildGUI();
    }

	protected abstract void reset();

	protected abstract void refresh();

    private void buildGUI() {
        loadIcons();
        myResetButton = new JButton(new ResetButtonAction());
        myResetButton.setIcon(myResetIcon);
        myResetButton.setToolTipText(myPlayToolTipText);
        myTickTimer = new Timer(TICK_DELAY, new TickAction());
        myTickButton = new JButton(new TickButtonAction());
        myTickButton.setIcon(myTickIcon);
        myTickButton.setToolTipText(myTickToolTipText);
        myPlayPauseButton = new JButton(new PlayPauseButtonAction());
        myPlayPauseButton.setIcon(myPlayIcon);
        myPlayPauseButton.setToolTipText(myPlayToolTipText);
        myButtonBar.add(myResetButton);
        myButtonBar.add(myPlayPauseButton);
        myButtonBar.add(myTickButton);
    }
    
    private void loadIcons(){
    	myResetIcon = new ImageIcon(
    			SimulationPanel.class.getClassLoader()
            .getResource(
                    "com/traclabs/biosim/client/framework/start.png"));

        myTickIcon = new ImageIcon(
        		SimulationPanel.class.getClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/forward.png"));
        myPlayIcon = new ImageIcon(
        		SimulationPanel.class.getClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/play.png"));
        
        myPauseIcon = new ImageIcon(
        		SimulationPanel.class.getClassLoader()
                        .getResource(
                                "com/traclabs/biosim/client/framework/pause.png"));
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
                ImageIcon fnordIcon = new ImageIcon(SimulationPanel.class.getClassLoader().getResource("com/traclabs/biosim/client/framework/pyramid.png"));
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
    
    private class ResetButtonAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            getTopLevelAncestor().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            reset();
            getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
        }
    }

    private class TickAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            refresh();
        }
    }
}