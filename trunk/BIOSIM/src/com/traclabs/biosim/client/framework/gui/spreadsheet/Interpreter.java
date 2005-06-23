package com.traclabs.biosim.client.framework.gui.spreadsheet;

import org.apache.log4j.Logger;

/*******************************************************************************
 * This class implements an interpreter that reads the content of a cell and
 * computes its associated value.
 * 
 * @version 1.0 July-2002
 * @author Thierry Manf�
 ******************************************************************************/
class Interpreter {

    static final String FORMULA = "FORMULA";

    static final String TERM = "TERM";

    static final String CELLID = "CELLID";

    static final String NUMBER = "NUMBER";

    static final String PARENTHESES = "PARENTHESES";

    static final String OPENPAR = "(";

    static final String CLOSEPAR = ")";

    static final String OPERATION = "OPERATION";

    static final String ADD = "+";

    static final String SUBSTRACT = "-";

    static final String MULTIPLY = "*";

    static final String DEVIDE = "/";

    static final char SEPARATORS[] = { '+', '-', '/', '*', '(', ')' };

    static final String SYNTAX_ERROR = "Error";

    static final String MULTIPLY_OR_DEVIDE = "MULTIDIVE";

    static final String ADD_OR_SUBSTRACT = "ADDSUB";

    class InterpreterEvent extends Throwable {
        public InterpreterEvent() {
            super();
        }

        public InterpreterEvent(String s) {
            super(s);
        }
    }

    /**
     * A syntax error terminates the evaluation of the formula immediatly
     */
    class SyntaxError extends InterpreterEvent {
        public SyntaxError() {
            super();
        }

        public SyntaxError(String s) {
            super(s);
        }
    }

    class SyntagmaMismatch extends InterpreterEvent {
        public SyntagmaMismatch() {
            super();
        }
    }

    class EndFormula extends InterpreterEvent {
        public EndFormula() {
            super();
        }
    }

    class EmptyReference extends InterpreterEvent {
        public EmptyReference() {
            super();
        }
    }

    /**
     * The associated TableModel.
     */
    private SpreadSheetModel _data;

    private SheetCell _cell;

    private String _formula;

    private int _depth;

    private boolean _noEmptyRef;

    private boolean _userEdition;

    private String _leaf;

    private StringBuffer _buffer;

    private Logger myLogger;

    Interpreter(SpreadSheetModel data) {
        myLogger = Logger.getLogger(this.getClass());
        _data = data;
    }

    /**
     * This method actually interprets the formula and returns the computed
     * value.
     * 
     * Following is the grammar description. Expressions between brackets are
     * optional. A list of characters between braces represents any single
     * character from that list.
     * 
     * FORMULA = TERM [ {*,/} TERM [ {+,-} FORMULA ] ] FORMULA = TERM {+,-}
     * FORMULA
     * 
     * TERM = ( FORMULA ) TERM = CELLID TERM = NUMBER
     * 
     * @param SheetCell
     *            The cell to update
     * @param boolean
     *            If true, the method has been called by an edition of the cell
     *            by the user
     * @return Object The computed value
     */
    void interpret(SheetCell cell, boolean edition) {
        _depth = 0;
        _noEmptyRef = true;
        _userEdition = edition;
        _formula = cell.formula.trim();

        if (_formula.length() == 0) {

            cell.value = null;
            cell.formula = null;

        } else if (_formula.charAt(0) == '=') {

            _cell = cell;

            if (_userEdition) {

                // Convert all characters to
                // uppercase characters.
                char[] upper = _formula.toCharArray();
                for (int ii = 0; ii < upper.length; ii++)
                    upper[ii] = Character.toUpperCase(upper[ii]);

                _formula = new String(upper);
                cell.formula = _formula;

            }

            _formula = cell.formula.substring(1, _formula.length());
            _buffer = new StringBuffer(_formula);

            Float value = null;
            try {
                value = (Float) accept(FORMULA);
            } catch (InterpreterEvent evt) {
                cell.value = SYNTAX_ERROR;
                return;
            }

            if (_noEmptyRef)
                cell.value = value.toString();
            else
                cell.value = null;

        } else {

            cell.value = cell.formula;
            cell.formula = null;

        }

        return;

    }

    /**
     * Retreive words on the left of the expression and check if it matches a
     * given syntagma.
     * 
     * @param String
     *            The grammatical term looked for
     * @return Object The evaluated value of the grammatical term (if
     *         computable)
     */
    Object accept(String syntagma) throws InterpreterEvent {
        _depth++;
        myLogger.debug("********** Depth: " + _depth);

        Float value = null;

        if (syntagma.equals(FORMULA)) {

            myLogger.debug("Looking for a FORMULA");

            Float leftTerm = null;
            Float rightTerm = null;
            String op = null;
            float res = 0;

            // A formula must have a left term.
            // If not, this is a syntax error.
            try {
                leftTerm = (Float) accept(TERM);
            } catch (SyntagmaMismatch evt) {
                throw new SyntaxError();
            }

            try {
                op = (String) accept(MULTIPLY_OR_DEVIDE);
                try {
                    rightTerm = (Float) accept(TERM);
                    if (_noEmptyRef) {
                        res = computeOperation(op, leftTerm.floatValue(),
                                rightTerm.floatValue());
                        myLogger.debug("Result MULTIPLY/DEVIDE is: " + res);
                    }

                    try {
                        op = (String) accept(ADD_OR_SUBSTRACT);
                        try {
                            rightTerm = (Float) accept(FORMULA);
                        } catch (EndFormula err) {
                            throw new SyntaxError();
                        } catch (SyntagmaMismatch err) {
                            throw new SyntaxError();
                        }
                        if (_noEmptyRef) {
                            res = computeOperation(op, res, rightTerm
                                    .floatValue());
                            myLogger.debug("Result ADD/SUBSTRACT: " + res);
                        }
                    } catch (SyntagmaMismatch evt) {
                        _depth--;
                        try {
                            op = (String) accept(MULTIPLY_OR_DEVIDE);
                            try {
                                rightTerm = (Float) accept(FORMULA);
                            } catch (EndFormula err) {
                                throw new SyntaxError();
                            } catch (SyntagmaMismatch err) {
                                throw new SyntaxError();
                            }
                            if (_noEmptyRef) {
                                res = computeOperation(op, res, rightTerm
                                        .floatValue());
                                myLogger
                                        .debug("Result MULTIPLY/DEVIDE: " + res);
                            }
                        } catch (SyntagmaMismatch evt2) {
                            throw new SyntaxError();
                        }
                    } catch (EndFormula evt) {
                        _depth--;
                    }
                } catch (EndFormula err) {
                    throw new SyntaxError();
                } catch (SyntagmaMismatch evt) {
                    throw new SyntaxError();
                }
            } catch (SyntagmaMismatch evt1) {

                _depth--;
                try {
                    op = (String) accept(ADD_OR_SUBSTRACT);
                    try {
                        rightTerm = (Float) accept(FORMULA);
                        if (_noEmptyRef) {
                            res = computeOperation(op, leftTerm.floatValue(),
                                    rightTerm.floatValue());
                            myLogger.debug("Result ADD/SUBSTRACT: " + res);
                        }
                    } catch (SyntagmaMismatch evt2) {
                        throw new SyntaxError();
                    }
                } catch (SyntagmaMismatch evt3) {
                    throw new SyntaxError();
                } catch (EndFormula end) {
                    _depth--;
                    if (_noEmptyRef)
                        res = leftTerm.floatValue();
                }

            } catch (EndFormula end2) {
                _depth--;
                if (_noEmptyRef)
                    res = leftTerm.floatValue();
            }

            _depth--;
            if (_noEmptyRef)
                return new Float(res);
            return null;

        }

        if (syntagma.equals(TERM)) {

            myLogger.debug("Looking for a TERM");

            try {
                value = (Float) accept(FORMULA);
            } catch (SyntagmaMismatch evt) {
                _depth--;
                try {
                    value = (Float) accept(CELLID);
                } catch (SyntagmaMismatch ev) {
                    _depth--;
                    value = (Float) accept(NUMBER);
                } catch (EmptyReference ev) {
                    _noEmptyRef = false;
                    value = null;
                }
            }

            myLogger.debug("TERM is: " + value);
            _depth--;
            return value;

        }

        /*
         * Hereunder the simple words (or leaf words)
         */

        if (syntagma.equals(NUMBER)) {

            myLogger.debug("Looking for a NUMBER");

            readLeaf();
            try {
                value = new Float(_leaf);
            } catch (NumberFormatException ex) {
                throw new SyntagmaMismatch();
            }

            updateFormula();
            myLogger.debug("Number=" + value);
            _depth--;
            return value;
        }

        if (syntagma.equals(CELLID)) {

            myLogger.debug("Looking for a CELLID");

            readLeaf();

            char[] id = _leaf.toCharArray();

            int ii = 0;
            while (ii < id.length && Character.isLetter(id[ii])) {
                ii++;
            }
            if (ii == 0 || ii >= id.length)
                throw new SyntagmaMismatch();
            String column = _leaf.substring(0, ii);

            int jj = ii;
            while (jj < id.length && Character.isDigit(id[jj])) {
                jj++;
            }
            if (ii == jj || jj != id.length)
                throw new SyntagmaMismatch();
            String row = _leaf.substring(ii, jj);

            // Translate row and column string names into
            // integer indexes.
            int r = Integer.valueOf(row).intValue() - 1;
            char[] buf = column.toCharArray();
            int c = 0;
            for (int kk = 0; kk < buf.length; kk++) {
                c = 26 * c + (Character.digit(buf[kk], 36) - 9);
            }
            c = c - 1;

            // A cell can not reference itself
            if (_cell.row == r && _cell.column == c) {
                myLogger.debug("Self reference not allowed in cells.");
                throw new SyntaxError();
            }

            String cellVal = _data.getValueAt(r, c).toString();
            myLogger.debug("cellVal: " + cellVal);

            updateFormula();

            // If proceeding a user edition...
            if (_userEdition) {

                // ... the links to listeners have been cleaned.
                // Register the current cell has a listener of the pointed cell.
                // Make sure the current cell is not already registered.
                if (_data.cells[r][c].listeners.indexOf(_cell) == -1)
                    _data.cells[r][c].listeners.add(_cell);

                // Register the pointed cell has a listenee
                // of the current cell.
                // Make sure the pointed cell is not already registered
                if (_cell.listenees.indexOf(_data.cells[r][c]) == -1)
                    _cell.listenees.add(_data.cells[r][c]);

            }

            if (cellVal == null)
                throw new EmptyReference();

            try {
                value = Float.valueOf(cellVal);
            } catch (NumberFormatException ex) {
                myLogger.debug("Can't read value at: " + row + ", " + column);
                throw new SyntagmaMismatch();
            }

            _depth--;
            return value;
        }

        if (syntagma.equals(MULTIPLY_OR_DEVIDE)) {
            myLogger.debug("Looking for MULTIPLY or DEVIDE");

            String op = null;
            try {
                op = readCharLeaf(MULTIPLY);
            } catch (SyntagmaMismatch evt) {
                op = readCharLeaf(DEVIDE);
            }

            _depth--;
            return op;
        }

        if (syntagma.equals(ADD_OR_SUBSTRACT)) {
            myLogger.debug("Looking for ADD or SUBSTRACT");
            String op = null;
            try {
                op = readCharLeaf(ADD);
            } catch (SyntagmaMismatch evt) {
                op = readCharLeaf(SUBSTRACT);
            }
            _depth--;
            return op;
        }

        if (syntagma.equals(OPENPAR)) {
            myLogger.debug("Looking for an OPENPAR");
            readCharLeaf(OPENPAR);
            _depth--;
            return null;
        }

        if (syntagma.equals(CLOSEPAR)) {
            myLogger.debug("Looking for a CLOSEPAR");
            readCharLeaf(CLOSEPAR);
            _depth--;
            return null;
        }

        // Should never be reached
        throw new SyntaxError();

    }

    /**
     * Read a single word on the left of the unevaluated part of the formula and
     * stores it into _leaf
     */
    private void readLeaf() throws EndFormula {

        if (_formula.length() == 0)
            throw new EndFormula();

        boolean searching = true;
        char[] buf = _formula.toCharArray();

        int ii = 0;
        search: while (searching && ii < buf.length) {

            for (int jj = 0; jj < SEPARATORS.length; jj++) {
                if (buf[ii] == SEPARATORS[jj]) {
                    searching = false;
                    _leaf = _formula.substring(0, ii);
                    continue search;
                }
            }
            ii++;
        }

        if (searching)
            _leaf = _formula;

    }

    private String readCharLeaf(String c) throws SyntagmaMismatch, EndFormula {
        if (_formula.length() == 0) {
            myLogger.debug("readLeaf(): End Of Formula");
            throw new EndFormula();
        }
        if (_formula.substring(0, 1).equals(c)) {
            _leaf = _formula.substring(0, 1);
            updateFormula();
        } else {
            if (_formula.substring(0, 1).equals(CLOSEPAR)) {
                myLogger.debug("readLeaf(): End Of Formula");
                throw new EndFormula();
            }
            throw new SyntagmaMismatch();
        }
        return c;
    }

    /**
     * Remove _leaf from _formula
     */
    private void updateFormula() {
        _buffer = _buffer.delete(0, _leaf.length());
        _formula = _buffer.toString().trim();
        myLogger.debug("_formula: " + _formula);
    }

    private float computeOperation(String op, float left, float right) {
        if (op.equals(MULTIPLY))
            return (left * right);
        if (op.equals(DEVIDE))
            return (left / right);
        if (op.equals(ADD))
            return (left + right);
        if (op.equals(SUBSTRACT))
            return (left - right);
        return Float.NaN;
    }

}