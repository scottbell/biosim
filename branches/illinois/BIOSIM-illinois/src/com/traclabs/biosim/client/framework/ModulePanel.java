package com.traclabs.biosim.client.framework;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioModule;

/**
 * @author scott
 *  
 */
public class ModulePanel extends JPanel {
    private JTabbedPane myTabbedPane;

    private JList myAllModulesList;

    private JList mySimModulesList;

    private JList mySensorModulesList;

    private JList myActuatorModulesList;

    private Logger myLogger;

    private List<ModuleSelectionListener> myListeners;

    public ModulePanel() {
        myListeners = new Vector<ModuleSelectionListener>();
        myLogger = Logger.getLogger(ModulePanel.class);

        String[] myAllModuleNames = BioHolderInitializer.getBioHolder().theBioDriver
                .getModuleNames();
        Arrays.sort(myAllModuleNames);
        myAllModulesList = new JList(myAllModuleNames);
        myAllModulesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myAllModulesList.setSelectedIndex(0);
        myAllModulesList
                .addListSelectionListener(new ModuleListSelectionListener());

        String[] mySimModuleNames = BioHolderInitializer.getBioHolder().theBioDriver
                .getSimModuleNames();
        Arrays.sort(mySimModuleNames);
        mySimModulesList = new JList(mySimModuleNames);
        mySimModulesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mySimModulesList.setSelectedIndex(0);
        mySimModulesList
                .addListSelectionListener(new ModuleListSelectionListener());

        String[] mySensorModuleNames = BioHolderInitializer.getBioHolder().theBioDriver
                .getSensorNames();
        Arrays.sort(mySensorModuleNames);
        mySensorModulesList = new JList(mySensorModuleNames);
        mySensorModulesList
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mySensorModulesList.setSelectedIndex(0);
        mySensorModulesList
                .addListSelectionListener(new ModuleListSelectionListener());

        String[] myActuatorModuleNames = BioHolderInitializer.getBioHolder().theBioDriver
                .getActuatorNames();
        Arrays.sort(myActuatorModuleNames);
        myActuatorModulesList = new JList(myActuatorModuleNames);
        myActuatorModulesList
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myActuatorModulesList.setSelectedIndex(0);
        myActuatorModulesList
                .addListSelectionListener(new ModuleListSelectionListener());

        myTabbedPane = new JTabbedPane();
        myTabbedPane.addTab("SimModules", new JScrollPane(mySimModulesList));
        myTabbedPane.addTab("Sensors", new JScrollPane(mySensorModulesList));
        myTabbedPane
                .addTab("Actuators", new JScrollPane(myActuatorModulesList));
        myTabbedPane.addTab("All Modules", new JScrollPane(myAllModulesList));
        myTabbedPane.addChangeListener(new TabChangeListener());
        setLayout(new GridLayout(1, 1));
        add(myTabbedPane);
    }

    /**
     * @return
     */
    public JList getModuleList() {
        return myAllModulesList;
    }

    public BioModule getSelectedModule() {
        String currentName = new String();
        if (myTabbedPane.getSelectedIndex() == 0)
            currentName = (String) (mySimModulesList.getSelectedValue());
        if (myTabbedPane.getSelectedIndex() == 1)
            currentName = (String) (mySensorModulesList.getSelectedValue());
        if (myTabbedPane.getSelectedIndex() == 2)
            currentName = (String) (myActuatorModulesList.getSelectedValue());
        if (myTabbedPane.getSelectedIndex() == 3)
            currentName = (String) (myAllModulesList.getSelectedValue());
        myLogger.debug("module selected = " + currentName);
        return (BioHolderInitializer.getBioHolder().theModulesMapped
                .get(currentName));
    }

    private void notifyListeners() {
        for (Iterator iter = myListeners.iterator(); iter.hasNext();) {
            ((ModuleSelectionListener) (iter.next()))
                    .stateChanged(new ChangeEvent(this));
        }
    }

    /**
     * @param listener
     */
    public void addModuleSelectionListener(ModuleSelectionListener listener) {
        myListeners.add(listener);

    }

    private class ModuleListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            notifyListeners();
        }
    }

    private class TabChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            notifyListeners();
        }
    }
}