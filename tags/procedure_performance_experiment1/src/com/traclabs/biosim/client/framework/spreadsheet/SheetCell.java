package com.traclabs.biosim.client.framework.spreadsheet;

import java.awt.Color;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * This class specifies the cell format.
 * 
 * @version 1.0 July-2002
 */
class SheetCell {

    static final int UNDEFINED = 0;

    static final int EDITED = 1;

    static final int UPDATED = 2;

    static final boolean USER_EDITION = true;

    static final boolean UPDATE_EVENT = false;

    Object value;

    String formula;

    int state;

    Vector<SheetCell> listeners;

    Vector<SheetCell> listenees;

    Color background;

    Color foreground;

    int row;

    int column;

    Logger myLogger;

    SheetCell(int r, int c) {
        myLogger = Logger.getLogger(this.getClass());
        row = r;
        column = c;
        value = null;
        formula = null;
        state = UNDEFINED;
        listeners = new Vector<SheetCell>();
        listenees = new Vector<SheetCell>();
        background = Color.white;
        foreground = Color.black;
    }

    SheetCell(int r, int c, String value, String formula) {
        this(r, c);
        this.value = value;
        this.formula = formula;
    }

    void userUpdate() {

        // The user has edited the cell. The dependencies
        // on other cells may have changed:
        // clear the links to the listeners. They will be
        // resseted during interpretation
        for (int ii = 0; ii < listenees.size(); ii++) {
            SheetCell c = listenees.get(ii);
            c.listeners.remove(this);
        }
        listenees.clear();

        SpreadSheetModel.interpreter.interpret(this, USER_EDITION);
        updateListeners();
        state = UPDATED;
    }

    void updateListeners() {
        for (int ii = 0; ii < listeners.size(); ii++) {
            SheetCell cell = listeners.get(ii);
            SpreadSheetModel.interpreter.interpret(cell, UPDATE_EVENT);
            myLogger.debug("Listener updated.");
            cell.updateListeners();
        }
    }

    public String toString() {
        if (state == EDITED && formula != null)
            return formula;
        else if (value != null)
            return value.toString();
        else
            return null;
    }

}
