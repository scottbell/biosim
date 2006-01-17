/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * Created on Feb 25, 2005
 */
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * A text field that only allows integer values.
 * 
 * @author kkusy
 */
public class IntegerTextField extends JTextField {
    public IntegerTextField() {
        setDocument(new IntegerDocument());
    }

    public IntegerTextField(int columns) {
        super(columns);
        setDocument(new IntegerDocument());
    }
}

class IntegerDocument extends PlainDocument {
    public void insertString(int offset, String s, AttributeSet attributeSet)
            throws BadLocationException {
        try {
            Integer.parseInt(s);
        } catch (Exception ex) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        super.insertString(offset, s, attributeSet);
    }
}