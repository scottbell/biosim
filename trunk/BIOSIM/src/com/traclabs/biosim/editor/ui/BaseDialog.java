/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * Created on Jun 23, 2004
 *
 */
package com.traclabs.biosim.server.editor.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A JDialog that has OK and Cancel buttons.
 * 
 * @author kkusy
 */
public class BaseDialog extends JDialog {
    public JPanel _formPanel;

    public static final int OK = 0;

    public static final int CANCEL = 1;

    public int _result = OK;

    public BaseDialog() {
        initComponents();
    }

    public BaseDialog(Frame frame) {
        super(frame);
        initComponents();
    }

    public BaseDialog(Frame frame, String title) {
        super(frame, title);
        initComponents();
    }

    public BaseDialog(Frame frame, boolean modal) {
        super(frame, modal);
        initComponents();
    }

    public BaseDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        initComponents();
    }

    public BaseDialog(Dialog owner) {
        super(owner);
        initComponents();
    }

    public BaseDialog(Dialog owner, String title) {
        super(owner, title);
        initComponents();
    }

    public BaseDialog(Dialog owner, boolean modal) {
        super(owner, modal);
        initComponents();
    }

    public BaseDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        initComponents();
    }

    protected JPanel getForm() {
        return _formPanel;
    }

    protected void setForm(JPanel formPanel) {
        _formPanel = formPanel;
        JPanel contents = (JPanel) this.getContentPane();
        contents.add(_formPanel, BorderLayout.CENTER);
        pack();
    }

    private void initComponents() {
        JPanel contents = new JPanel(new BorderLayout(5, 5));
        contents.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
        setContentPane(contents);

        ButtonPanel buttons = new ButtonPanel();
        JButton okButton = new JButton("OK");
        okButton.setMnemonic(KeyEvent.VK_O);
        buttons.add(okButton);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic(KeyEvent.VK_C);
        buttons.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        contents.add(buttons, BorderLayout.SOUTH);
        _formPanel = new JPanel();
        contents.add(_formPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                onCancel();
            }
        });
    }

    protected void onOK() {
        _result = OK;
        closeDialog();
    }

    protected void onCancel() {
        _result = CANCEL;
        closeDialog();
    }

    protected void closeDialog() {
        this.setVisible(false);
        this.dispose();
    }

    public int getResult() {
        return _result;
    }

    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void addGridRow(Container container, JLabel label,
            Component component) {
        GridBagLayout gridbag = null;
        try {
            gridbag = (GridBagLayout) (container.getLayout());
        } catch (Exception e) {
            System.err.println("Hey!  You called addRow with"
                    + " a container that doesn't " + " use GridBagLayout!");
            return;
        }

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.weighty = 1.0;
        c.insets = new Insets(0, 5, 2, 5);

        gridbag.setConstraints(label, c);
        container.add(label);
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        //c.fill = GridBagConstraints.NONE;
        //c.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(component, c);
        container.add(component);
    }
}