package biosim.client.framework.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
/**
 * A  panel that displays the log
 *
 * @author    Scott Bell
 */

public class LogViewer extends javax.swing.JPanel
{
	private JTree myLogTree;
	private DefaultMutableTreeNode myTopNode;
	
	/**
	* Default constructor
	*/
	public LogViewer(){
		buildGui();
	}
	
	/**
	* Creates close listener and adds it to the window
	*/
	private void buildGui(){
		myTopNode = new DefaultMutableTreeNode("Log");
		myLogTree = new JTree(myTopNode);
	}

}



