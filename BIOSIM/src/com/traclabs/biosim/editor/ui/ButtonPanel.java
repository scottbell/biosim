/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.server.editor.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class ButtonPanel extends JPanel {
    JPanel buttonPanel = new JPanel();

    JSeparator separator = new JSeparator();

    public ButtonPanel() {
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        setLayout(new BorderLayout(0, 3));
        add(separator, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public void add(JButton button) {
        buttonPanel.add(button);
    }

    public void setAlignment(int align) {
        FlowLayout layout = (FlowLayout) buttonPanel.getLayout();
        layout.setAlignment(align);
    }
}

