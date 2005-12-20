/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * Created on Jun 9, 2004
 */
package com.traclabs.biosim.editor.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays a dialog that allows the user to set the magnification of the JGraph
 * in the current editor.
 * 
 * @author kkusy
 */
public class ZoomDialog extends JDialog {
    private int _magnification = 100;

    private JComboBox _magnificationCombo;

    private static final String magnificationStrings[] = { "12%", "25%", "50%",
            "75%", "100%", "125%", "150%", "200%", "400%", "800%", "1600%" };

    public static final int OK = 1;

    public static final int CANCEL = 0;

    private int _result = CANCEL;

    public ZoomDialog(Frame frame) {
        super(frame, "Zoom To", true);
        JPanel contents = new JPanel(new BorderLayout(5, 5));
        contents.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contents);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton okButton = new JButton("OK");
        okButton.setMnemonic(KeyEvent.VK_O);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic(KeyEvent.VK_C);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeDialog();
            }
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        getRootPane().setDefaultButton(okButton);
        contents.add(buttonPanel, BorderLayout.SOUTH);

        JPanel northPanel = new JPanel(new BorderLayout(5, 5));
        northPanel.add(new JLabel("Magnification:"), BorderLayout.WEST);
        _magnificationCombo = new JComboBox(magnificationStrings);
        _magnificationCombo.setEditable(true);
        northPanel.add(_magnificationCombo);

        contents.add(northPanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(frame);
    }

    public int getMagnification() {
        return _magnification;
    }

    public void setMagnification(int magnification) {
        _magnification = magnification;
    }

    public int display() {
        _result = CANCEL;
        _magnificationCombo.setSelectedItem(_magnification + "%");

        this.setVisible(true);
        return _result;
    }

    private void onOK() {
        String percent = _magnificationCombo.getSelectedItem().toString();
        percent = percent.trim();
        if (percent.endsWith("%")) {
            percent = percent.substring(0, percent.length() - 1);
        }

        try {
            double d = Double.parseDouble(percent);
            _magnification = (int) d;
        } catch (NumberFormatException e) {
            return;
        }

        _result = OK;
        closeDialog();
    }

    private void closeDialog() {
        this.setVisible(false);
        this.dispose();
    }
}