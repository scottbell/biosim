package biosim.client.util.log;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import biosim.idl.util.*;
import biosim.client.framework.gui.*;
/**
 * A  panel that displays the log
 *
 * @author    Scott Bell
 */

public class LogViewer extends JPanel
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
		
		add(myLogTree);
	}
	
	public static void main(String args[]){
		LogViewer myLogViewer = new LogViewer();
		BioFrame myFrame = new BioFrame("Log Viewer", false);
		myFrame.getContentPane().add(myLogViewer);
		myFrame.setSize(640,480);
		myFrame.pack();
		myFrame.setVisible(true);
	}

}



