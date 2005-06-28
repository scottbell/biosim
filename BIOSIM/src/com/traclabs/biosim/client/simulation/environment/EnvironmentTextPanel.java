package com.traclabs.biosim.client.simulation.environment;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.traclabs.biosim.client.framework.gui.TimedPanel;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;

/**
 * This is the JPanel that displays information about the environment (gas
 * levels, current time, etc.)
 * 
 * @author Scott Bell
 */

public class EnvironmentTextPanel extends TimedPanel {
    //Various GUI componenets
    private JLabel tickLabel;

    private JPanel tickPanel;

    private JLabel O2Label;

    private JLabel CO2Label;

    private JLabel waterLabel;

    private JLabel nitrogenLabel;

    private JLabel otherLabel;

    private JPanel airPanel;

    //Server required for data polling
    private SimEnvironment mySimEnvironment;

    //For formatting floats
    private DecimalFormat numFormat;

    /**
     * Creates and registers this panel.
     */
    public EnvironmentTextPanel(SimEnvironment pSimEnvironment) {
        super();
        mySimEnvironment = pSimEnvironment;
        buildGui();
    }

    /**
     * Contructs GUI components, adds them to the panel.
     */
    private void buildGui() {
        numFormat = new DecimalFormat("#,##0.00;(#)");
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
        JLabel moduleLabel = new JLabel("Environment");
        if (mySimEnvironment.getModuleName().startsWith("Crew"))
            moduleLabel.setText("Crew Environment");
        else if (mySimEnvironment.getModuleName().startsWith("Plant"))
            moduleLabel.setText("Plant Environment");
        JPanel modulePanel = new JPanel();
        modulePanel.setLayout(new BorderLayout());
        modulePanel.setBorder(BorderFactory.createTitledBorder("Module"));
        modulePanel.add(moduleLabel, BorderLayout.CENTER);

        long ticksExpired = BioHolderInitializer.getBioHolder().theBioDriver
                .getTicks();
        tickLabel = new JLabel(ticksExpired + " hours (" + (ticksExpired / 24)
                + " days)");
        tickPanel = new JPanel();
        tickPanel.setLayout(new BorderLayout());
        tickPanel.setBorder(BorderFactory.createTitledBorder("Time"));
        tickPanel.add(tickLabel, BorderLayout.CENTER);

        airPanel = new JPanel();
        airPanel.setLayout(new GridLayout(5, 1));
        airPanel.setBorder(BorderFactory.createTitledBorder("Air"));
        O2Label = new JLabel("O2:     "
                + numFormat.format(mySimEnvironment.getO2Store().getCurrentLevel()) + " moles");
        CO2Label = new JLabel("CO2:   "
                + numFormat.format(mySimEnvironment.getCO2Store().getCurrentLevel()) + " moles");
        nitrogenLabel = new JLabel("N:   "
                + numFormat.format(mySimEnvironment.getNitrogenStore().getCurrentLevel())
                + " moles");
        waterLabel = new JLabel("water:   "
                + numFormat.format(mySimEnvironment.getWaterStore().getCurrentLevel()) + " moles");
        otherLabel = new JLabel("other:  "
                + numFormat.format(mySimEnvironment.getOtherStore().getCurrentLevel()) + " moles");
        airPanel.add(O2Label);
        airPanel.add(CO2Label);
        airPanel.add(nitrogenLabel);
        airPanel.add(waterLabel);
        airPanel.add(otherLabel);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        gridbag.setConstraints(modulePanel, c);
        add(modulePanel);

        gridbag.setConstraints(tickPanel, c);
        add(tickPanel);

        gridbag.setConstraints(airPanel, c);
        add(airPanel);
    }

    /**
     * Updates every label on the panel with new data pulled from the servers.
     */
    public void refresh() {
        long ticksExpired = BioHolderInitializer.getBioHolder().theBioDriver
                .getTicks();
        tickLabel.setText(ticksExpired + " hours (" + (ticksExpired / 24)
                + " days)");
        O2Label.setText("O2:     "
                + numFormat.format(mySimEnvironment.getO2Store().getCurrentLevel()) + " moles");
        CO2Label.setText("CO2:   "
                + numFormat.format(mySimEnvironment.getCO2Store().getCurrentLevel()) + " moles");
        nitrogenLabel.setText("N:   "
                + numFormat.format(mySimEnvironment.getNitrogenStore().getCurrentLevel())
                + " moles");
        waterLabel
                .setText("water:   "
                        + numFormat.format(mySimEnvironment.getWaterStore().getCurrentLevel())
                        + " moles");
        otherLabel
                .setText("other:  "
                        + numFormat.format(mySimEnvironment.getOtherStore().getCurrentLevel())
                        + " moles");
    }
}