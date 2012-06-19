package com.traclabs.biosim.client.framework.spreadsheet;

import javax.swing.table.AbstractTableModel;

/**
 * This class specifies the data format for the SpreadSheet JTable
 * 
 * @version 1.0 July-2002
 */
class SpreadSheetModel extends AbstractTableModel {

    final private SpreadSheet _dpyTable;

    static Interpreter interpreter;

    static final boolean DEBUG = false;

    private int _nbRow;

    private int _nbColumn;

    protected SheetCell[][] cells;

    private String[] myColumnNames;

    /**
     * Create a nbRow by nbColumn SpreadSheetModel.
     * 
     * @param cells[][]
     *            The cell array
     * @param table
     *            The associated SpreadSheet
     */
    SpreadSheetModel(SheetCell[][] cells, SpreadSheet table,
            String[] pColumnNames) {
        _dpyTable = table;
        _nbRow = cells.length;
        _nbColumn = cells[0].length;
        myColumnNames = pColumnNames;
        this.cells = cells;
        interpreter = new Interpreter(this);
    }

    public int getRowCount() {
        return _nbRow;
    }

    public int getColumnCount() {
        return _nbColumn;
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public String getColumnName(int index) {
        return myColumnNames[index];
    }

    public Object getValueAt(int row, int column) {
        return cells[row][column];
    }

    /**
     * Mark the corresponding cell as being edited. Its formula must be
     * displayed instead of its result
     */
    void setEditMode(int row, int column) {
        cells[row][column].state = SheetCell.EDITED;
    }

    void setDisplayMode(int row, int column) {
        cells[row][column].state = SheetCell.UPDATED;
    }

    public void setValueAt(Object value, int row, int column) {

        String input = (String) value;

        cells[row][column].formula = input;
        cells[row][column].userUpdate();
        _dpyTable.repaint();

    }

}
