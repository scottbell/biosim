package com.traclabs.biosim.client.framework.gui.spreadsheet;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventObject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;

/*******************************************************************************
 * 
 * This class implements a basic spreadsheet using a JTable. It also provides a
 * main() method to be run as an application.
 * 
 * @version 1.0 July-2002
 * @author Thierry Manfé
 *  
 ******************************************************************************/
public class SpreadSheet extends JTable {

    private JScrollPane _scp;

    private CellMenu _popupMenu;

    private SpreadSheetModel _model;

    private int _numRow;

    private int _numCol;

    private int _editedModelRow;

    private int _editedModelCol;

    /*
     * GUI components used to tailored the SpreadSheet.
     */
    private CellRenderer _renderer;

    private Font _cellFont;

    //private FontMetrics _metrics;

    // Cells selected.
    private Object[] _selection;
    
    private Logger myLogger;

    /**
     * Start the spreadsheet into a JFrame.
     * 
     * @param args
     *            The options passed on the command line. Not used.
     */
    public static void main(String[] args) {

        String vers = System.getProperty("java.version");
        if (vers.compareTo("1.3") < 0) {
            Logger.getLogger(SpreadSheet.class).warn("Please use Java 1.3 or above.");
            //System.exit(1);
        }

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ex) {
            Logger.getLogger(SpreadSheet.class).error("Can't set look and feel MetalLookAndFeel");
            System.exit(2);
        }

        JFrame frame = new JFrame("A Simple Spreadsheet");

        /*
         * Allows the user to exit the application from the window manager's
         * dressing.
         */
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        String[] colNames1 = { "bob", "steve", "toby" };
        String[] rowNames2 = { "red", "green" };
        SpreadSheet sp = new SpreadSheet(colNames1, rowNames2);

        /**
         * SheetCell[][] cells = new SheetCell[3][2]; cells[0][0] = new
         * SheetCell(0 , 0, "1", null); cells[1][0] = new SheetCell(0 , 1, "2",
         * null); cells[2][0] = new SheetCell(0 , 2, "3", null); cells[0][1] =
         * new SheetCell(1 , 0, "1", "=A1"); cells[1][1] = new SheetCell(1 , 1,
         * "3", "=A1+A2"); cells[2][1] = new SheetCell(1 , 2, "6", "=A1+A2+A3");
         * SpreadSheet sp = new SpreadSheet(cells);
         */

        frame.getContentPane().add(sp.getScrollPane());
        frame.pack();
        frame.setVisible(true);

    }

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

        _numRow = rowNames.length;
        _numCol = colNames.length;

        _cellFont = new Font("Arial", Font.PLAIN, 14);

        // Create the JScrollPane that includes the Table
        _scp = new JScrollPane(this);

        // Create the rendeder for the cells
        _renderer = new CellRenderer();
        try {
            setDefaultRenderer(Class.forName("java.lang.Object"), _renderer);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SpreadSheet.class).debug("SpreadSheet() Can't modify renderer");
        }

        _model = new SpreadSheetModel(foo, this, colNames);
        setModel(_model);

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
                                _selection[indice] = _model.cells[selRow[r]][convertColumnIndexToModel(selCol[c])];
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
        Dimension dim = new Dimension(metrics.stringWidth("9999999999999")
                + insets.right + insets.left, rowHeight * _numRow);
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
        dim.height = rowHeight * _numRow;
        vp.setViewSize(dim);
        vp.setView(pnl);
        _scp.setRowHeader(vp);

        // Set resize policy and make sure
        // the table's size is tailored
        // as soon as it gets drawn.
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Dimension dimScpViewport = getPreferredScrollableViewportSize();
        if (_numRow > 30)
            dimScpViewport.height = 30 * rowHeight;
        else
            dimScpViewport.height = _numRow * rowHeight;
        if (_numCol > 15)
            dimScpViewport.width = 15 * getColumnModel().getTotalColumnWidth()
                    / _numCol;
        else
            dimScpViewport.width = getColumnModel().getTotalColumnWidth();
        setPreferredScrollableViewportSize(dimScpViewport);
        resizeAndRepaint();
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

        if (_editedModelRow != -1)
            _model.setDisplayMode(_editedModelRow, _editedModelCol);

        _editedModelRow = row;
        _editedModelCol = convertColumnIndexToModel(column);

        _model.setEditMode(row, convertColumnIndexToModel(column));
        return super.editCellAt(row, column, ev);

    }

    /**
     * Invoked by the cell editor when a cell edition stops. This method
     * override and calls that of its super class.
     *  
     */
    public void editingStopped(ChangeEvent ev) {
        _model.setDisplayMode(_editedModelRow, _editedModelCol);
        super.editingStopped(ev);
    }

    /**
     * Invoked by the cell editor when a cell edition is cancelled. This method
     * override and calls that of its super class.
     *  
     */
    public void editingCanceled(ChangeEvent ev) {
        _model.setDisplayMode(_editedModelRow, _editedModelCol);
        super.editingCanceled(ev);
    }

    public JScrollPane getScrollPane() {
        return _scp;
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
        _model = null;
    }

    public void setVisible(boolean flag) {
        _scp.setVisible(flag);
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /*
     * This class is used to customize the cells rendering.
     */
    public class CellRenderer extends JLabel implements TableCellRenderer {

        private LineBorder _selectBorder;

        private EmptyBorder _emptyBorder;

        private Dimension _dim;

        public CellRenderer() {
            super();
            _emptyBorder = new EmptyBorder(1, 2, 1, 2);
            _selectBorder = new LineBorder(Color.red);
            this.setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            _dim = new Dimension();
            _dim.height = 22;
            _dim.width = 100;
            this.setSize(_dim);
        };

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
             * this.setToolTipText("Right-click to change the cell's colors.");
             *  } else { this.setBorder(_emptyBorder);
             * this.setToolTipText("Single-Click to select a cell, " +
             * "double-click to edit."); }
             */
            return this;

        }

    }

}

