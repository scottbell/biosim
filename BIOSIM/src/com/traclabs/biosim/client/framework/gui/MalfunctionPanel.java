package biosim.client.framework.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * 
 * @author    Scott Bell
 */
public class MalfunctionPanel extends TimedPanel
{
	JTable myTable;
	/**
	 * Default constructor.
	 */
	public MalfunctionPanel() {
		super();
		buildGui();
	}
	
	public void refresh(){
	}

	protected void buildGui(){
		setLayout(new BorderLayout());
		DefaultTableModel myTableModel = new DefaultTableModel();
		myTableModel.addColumn("Module");
		myTableModel.addColumn("Status");
		myTableModel.addColumn("Malfunction");
		myTable = new JTable(myTableModel);
		add(myTable, BorderLayout.CENTER);
		String[] rowValues = {"one", "two", "three"};
		myTableModel.addRow(rowValues);
	}
	
	public static void main(String[] args){
		BioFrame myFrame = new BioFrame("Malfunctions", false);
		MalfunctionPanel myMalfPanel = new MalfunctionPanel();
		myFrame.getContentPane().add(myMalfPanel);
		myFrame.pack();
		myFrame.setVisible(true);
	}
}
