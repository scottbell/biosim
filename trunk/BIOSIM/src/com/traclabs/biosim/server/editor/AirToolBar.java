/*
 * Created on Jan 26, 2005
 */
package com.traclabs.biosim.server.editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * @author scott
 */
public class AirToolBar extends EditorToolBar {

    public AirToolBar() {
        super("Air");

        add(new H2StoreAction());
        add(new NitrogenStoreAction());
        add(new CH4StoreAction());
        add(new O2StoreAction());
        add(new CO2StoreAction());
        addSeparator();
        add(new CRSAction());
        add(new OGSAction());
        add(new VCCRAction());
        add(new AirRSAction());
        addSeparator();
        add(new ConduitAction());
    }

    private class H2StoreAction extends AbstractAction {
        public H2StoreAction() {
            super("H2 Store");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }

    private class NitrogenStoreAction extends AbstractAction {
        public NitrogenStoreAction() {
            super("Nitrogen Store");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }

    private class CH4StoreAction extends AbstractAction {
        public CH4StoreAction() {
            super("Methane Store");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }

    private class O2StoreAction extends AbstractAction {
        public O2StoreAction() {
            super("O2 Store");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }

    private class CO2StoreAction extends AbstractAction {
        public CO2StoreAction() {
            super("CO2 Store");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }

    private class CRSAction extends AbstractAction {
        public CRSAction() {
            super("CRS");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }

    private class OGSAction extends AbstractAction {
        public OGSAction() {
            super("OGS");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }

    private class VCCRAction extends AbstractAction {
        public VCCRAction() {
            super("VCCR");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }

    private class AirRSAction extends AbstractAction {
        public AirRSAction() {
            super("Air RS");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }

    private class ConduitAction extends AbstractAction {
        public ConduitAction() {
            super("Conduit");
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Action for first button/menu item" + e);
        }
    }
}