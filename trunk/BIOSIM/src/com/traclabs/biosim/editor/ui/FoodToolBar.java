/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

/**
 * @author scott
 * 
 */
public class FoodToolBar extends EditorToolBar {
    public FoodToolBar() {
        super("Food");
        add(new JButton("BiomassRS"));
        add(new JButton("FoodProcessor"));
        addSeparator();
        add(new JButton("BiomassStore"));
        add(new JButton("FoodStore"));
    }
}