package com.traclabs.biosim.client.framework.spreadsheet;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

public class SpreadSheet extends JTable {

    private JScrollPane myScrollPane;

    private SpreadSheetModel myModel;

    private int myRowNumber;

    private int myColumnNumber;

    private int myEditedRowNumber;

    private int myEditedColumnNumber;

    /*
     * GUI components used to tailored the SpreadSheet.
     */
    private CellRenderer _renderer;

    private Font _cellFont;

    //private FontMetrics _metrics;

    // Cells selected.
    private Object[] _selection;

    public SpreadSheet(String[] colNames, String[] rowNames) {
        this(null, colNames, rowNames);
    }

    /**
     * Build SpreadSheet of numCol columns and numRow rows.
     * 
     * @param cells
     *            [numRow][numColumn] If not null, the cells to be used in the
     *            spreadsheet. It must be a two dimensional rectangular array.
     *            If null, the cells are automatically created.
     * @param colNames
     *            The names of columns
     * @param rowNames
     *            The names of rows
     *  
     */
    private SpreadSheet(SheetCell[][] cells, String[] colNames,
            String[] rowNames) {

        super();

        SheetCell foo[][];

        if (cells != null)
            foo = cells;
        else {
            foo = new SheetCell[rowNames.length][colNames.length];
            for (int ii = 0; ii < rowNames.length; ii++) {
                for (int jj = 0; jj < colNames.length; jj++)
                    foo[ii][jj] = new SheetCell(ii, jj);
            }
        }

        myRowNumber = rowNames.length;
        myColumnNumber = colNames.length;

        _cellFont = new Font("Arial", Font.PLAIN, 14);

        // Create the JScrollPane that includes the Table
        myScrollPane = new JScrollPane(this);

        // Create the rendeder for the cells
        _renderer = new CellRenderer();
        try {
            setDefaultRenderer(Class.forName("java.lang.Object"), _renderer);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpreadSheet.class).debug(
                    "SpreadSheet() Can't modify renderer");
        }

        myModel = new SpreadSheetModel(foo, this, colNames);
        setModel(myModel);

        /*
         * Tune the selection mode
         */

        // Allows row and collumn selections to exit at the same time
        setCellSelectionEnabled(true);

        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent ev) {

                        int selRow[] = getSelectedRows();
                        int selCol[] = getSelectedColumns();

                        _selection = new Object[selRow.length * selCol.length];

                        int indice = 0;
                        for (int r = 0; r < selRow.length; r++) {
                            for (int c = 0; c < selCol.length; c++) {
                                _selection[indice] = myModel.cells[selRow[r]][convertColumnIndexToModel(selCol[c])];
                                indice++;
                            }
                        }

                    }
                });

        // Create a row-header to display row numbers.
        // This row-header is made of labels whose Borders,
        // Foregrounds, Backgrounds, and Fonts must be
        // the one used for the table column headers.
        // Also ensure that the row-header labels and the table
        // rows have the same height.
        TableColumn aColumn = getColumnModel().getColumn(0);
        TableCellRenderer aRenderer = getTableHeader().getDefaultRenderer();
        if (aRenderer == null) {
            Logger.getLogger(SpreadSheet.class).warn(" Aouch !");
            aColumn = getColumnModel().getColumn(0);
            aRenderer = aColumn.getHeaderRenderer();
            if (aRenderer == null) {
                Logger.getLogger(SpreadSheet.class).error(" Aouch Aouch !");
                System.exit(3);
            }
        }
        Component aComponent = aRenderer.getTableCellRendererComponent(this,
                aColumn.getHeaderValue(), false, false, -1, 0);
        Font aFont = aComponent.getFont();
        Color aBackground = aComponent.getBackground();
        Color aForeground = aComponent.getForeground();

        Border border = (Border) UIManager.getDefaults().get(
                "TableHeader.cellBorder");
        Insets insets = border.getBorderInsets(tableHeader);
        FontMetrics metrics = getFontMetrics(_cellFont);
        rowHeight = insets.bottom + metrics.getHeight() + insets.top;

        /*
         * Creating a panel to be used as the row header.
         * 
         * Since I'm not using any LayoutManager, a call to setPreferredSize().
         */
        JPanel pnl = new JPanel((LayoutManager) null);
        Dimension dim = new Dimension(metrics.stringWidth(getLongestString(rowNames))
                + insets.right + insets.left, rowHeight * myRowNumber);
        pnl.setPreferredSize(dim);

        // Adding the row header labels
        dim.height = rowHeight;
        for (int ii = 0; ii < rowNames.length; ii++) {
            JLabel lbl = new JLabel(rowNames[ii], SwingConstants.CENTER);
            lbl.setFont(aFont);
            lbl.setBackground(aBackground);
            lbl.setForeground(aForeground);
            lbl.setBorder(border);
            lbl.setBounds(0, ii * dim.height, dim.width, dim.height);
            pnl.add(lbl);
        }

        JViewport vp = new JViewport();
        dim.height = rowHeight * myRowNumber;
        vp.setViewSize(dim);
        vp.setView(pnl);
        myScrollPane.setRowHeader(vp);

        // Set resize policy and make sure
        // the table's size is tailored
        // as soon as it gets drawn.
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Dimension dimScpViewport = getPreferredScrollableViewportSize();
        if (myRowNumber > 30)
            dimScpViewport.height = 30 * rowHeight;
        else
            dimScpViewport.height = myRowNumber * rowHeight;
        if (myColumnNumber > 15)
            dimScpViewport.width = 15 * getColumnModel().getTotalColumnWidth()
                    / myColumnNumber;
        else
            dimScpViewport.width = getColumnModel().getTotalColumnWidth();
        setPreferredScrollableViewportSize(dimScpViewport);
        resizeAndRepaint();
    }

    private static String getLongestString(String[] rowNames) {
    	String longestString = "";
		for (String currentString : rowNames) {
			if (currentString.length() > longestString.length())
				longestString = currentString;
		}
		return longestString;
	}

	/**
     * Invoked when a cell edition starts. This method overrides and calls that
     * of its super class.
     * 
     * @param row
     *            The row to be edited
     * @param column
     *            The column to be edited
     * @param ev
     *            The firing event
     * @return boolean false if for any reason the cell cannot be edited.
     */
    public boolean editCellAt(int row, int column, EventObject ev) {

        if (myEditedRowNumber != -1)
            myModel.setDisplayMode(myEditedRowNumber, myEditedColumnNumber);

        myEditedRowNumber = row;
        myEditedColumnNumber = convertColumnIndexToModel(column);

        myModel.setEditMode(row, convertColumnIndexToModel(column));
        return super.editCellAt(row, column, ev);

    }

    /**
     * Invoked by the cell editor when a cell edition stops. This method
     * override and calls that of its super class.
     *  
     */
    public void editingStopped(ChangeEvent ev) {
        myModel.setDisplayMode(myEditedRowNumber, myEditedColumnNumber);
        super.editingStopped(ev);
    }

    /**
     * Invoked by the cell editor when a cell edition is cancelled. This method
     * override and calls that of its super class.
     *  
     */
    public void editingCanceled(ChangeEvent ev) {
        myModel.setDisplayMode(myEditedRowNumber, myEditedColumnNumber);
        super.editingCanceled(ev);
    }

    public JScrollPane getScrollPane() {
        return myScrollPane;
    }

    public void processMouseEvent(MouseEvent ev) {

        int type = ev.getID();
        int modifiers = ev.getModifiers();

        if ((type == MouseEvent.MOUSE_RELEASED)
                && (modifiers == InputEvent.BUTTON3_MASK)) {
            /*
             * if (_selection!=null) { if (_popupMenu==null) _popupMenu = new
             * CellMenu(this);
             * 
             * if (_popupMenu.isVisible()) _popupMenu.setVisible(false); else {
             * _popupMenu.setTargetCells(_selection); Point p =
             * getLocationOnScreen(); _popupMenu.setLocation(p.x+ev.getX()+1,
             * p.y+ev.getY()+1); _popupMenu.setVisible(true); } }
             */
        }
        super.processMouseEvent(ev);
    }

    protected void release() {
        myModel = null;
    }

    public void setVisible(boolean flag) {
        myScrollPane.setVisible(flag);
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /*
     * This class is used to customize the cells rendering.
     */
    public class CellRenderer extends JLabel implements TableCellRenderer {

        private Dimension _dim;

        public CellRenderer() {
            super();
            this.setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            _dim = new Dimension();
            _dim.height = 22;
            _dim.width = 100;
            this.setSize(_dim);
        }

        /**
         * 
         * Method defining the renderer to be used when drawing the cells.
         *  
         */
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            SheetCell sc = (SheetCell) value;

            this.setFont(_cellFont);
            this.setText(sc.toString());
            this.setForeground(sc.foreground);
            this.setBackground(sc.background);
            /*
             * if (isSelected) { this.setBorder(_selectBorder);
             * this.setToolTipText("Right-click to change the cell's colors."); }
             * else { this.setBorder(_emptyBorder);
             * this.setToolTipText("Single-Click to select a cell, " +
             * "double-click to edit."); }
             */
            return this;

        }

    }

}

