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
	private JToolBar myToolBar;
	private ImageIcon folderIcon;
	private ImageIcon refreshIcon;
	private ImageIcon lastIcon;
	private ImageIcon backIcon;
	private ImageIcon nextIcon;
	private ImageIcon firstIcon;
	
	/**
	* Default constructor
	*/
	public LogViewer(){
		buildGui();
	}
	
	private void loadIcons(){
		folderIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/folder.jpg"));
		refreshIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/refresh.jpg"));
		lastIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/last.jpg"));
		backIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/back.jpg"));
		nextIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/next.jpg"));
		firstIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/first.jpg"));
	}
	
	/**
	* Creates close listener and adds it to the window
	*/
	private void buildGui(){
		myTopNode = new DefaultMutableTreeNode("Log");
		myLogTree = new JTree(myTopNode);
		myToolBar = new JToolBar();
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



