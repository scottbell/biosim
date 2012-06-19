package com.traclabs.biosim.client.framework.spreadsheet;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

import org.apache.log4j.Logger;

/**
 * This class implements a popup-menu used to customize cell appearance.
 * 
 * @version 1.0 July-2002
 */
class CellMenu extends JPopupMenu implements ActionListener {

    static private final String _FOREGROUND = "Foreground";

    static private final String _BACKGROUND = "Background";

    //private SheetCell _targetCells[];
    private Object _targetCells[];

    private JWindow _colorWindow;

    private SpreadSheet _sp;

    private Logger myLogger;

    CellMenu(SpreadSheet parent) {
        myLogger = Logger.getLogger(this.getClass());

        _sp = parent;

        setDefaultLightWeightPopupEnabled(true);

        JMenuItem item = new JMenuItem(_FOREGROUND);
        item.addActionListener(this);
        add(item);
        item = new JMenuItem(_BACKGROUND);
        item.addActionListener(this);
        add(item);
        pack();
    }

    void setTargetCells(Object c[]) {
        _targetCells = c;
    }

    public void actionPerformed(ActionEvent ev) {

        myLogger.debug("Size of selection: " + _targetCells.length);

        if (ev.getActionCommand().equals(_FOREGROUND)) {
            setVisible(false);
            if (_colorWindow == null)
                new JWindow();
            Color col = JColorChooser.showDialog(_colorWindow,
                    "Foreground Color", null);
            for (int ii = 0; ii < _targetCells.length; ii++) {
                SheetCell sc = (SheetCell) _targetCells[ii];
                sc.foreground = col;
            }
            _sp.repaint();
        } else if (ev.getActionCommand().equals(_BACKGROUND)) {
            setVisible(false);
            if (_colorWindow == null)
                new JWindow();
            Color col = JColorChooser.showDialog(_colorWindow,
                    "Background Color", null);
            for (int ii = 0; ii < _targetCells.length; ii++) {
                SheetCell sc = (SheetCell) _targetCells[ii];
                sc.background = col;
            }
            _sp.repaint();
        }

    }

}
