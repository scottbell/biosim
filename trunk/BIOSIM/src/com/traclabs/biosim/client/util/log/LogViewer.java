package biosim.client.util.log;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import biosim.idl.util.*;
import biosim.client.framework.gui.*;
import javax.imageio.*;
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
	private OpenAction myOpenAction;
	private RefreshAction myRefreshAction;
	private LastAction myLastAction;
	private BackAction myBackAction;
	private NextAction myNextAction;
	private FirstAction myFirstAction;
	private JButton openButton;
	private JButton refreshButton;
	private JButton lastButton;
	private JButton backButton;
	private JButton nextButton;
	private JButton firstButton;

	/**
	* Default constructor
	*/
	public LogViewer(){
		buildGui();
	}

	private void loadIcons(){
		try{
			folderIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/folder.gif"));
			refreshIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/refresh.gif"));
			backIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/back.gif"));
			nextIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/next.gif"));
			lastIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/last.gif"));
			firstIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/util/log/first.gif"));
		}
		catch(Exception e){
			System.err.println("Couldn't load icons!");
			e.printStackTrace();
		}
	}

	/**
	* Creates close listener and adds it to the window
	*/
	private void buildGui(){
		loadIcons();
		setLayout(new BorderLayout());
		myTopNode = new DefaultMutableTreeNode("Log");
		myLogTree = new JTree(myTopNode);
		myToolBar = new JToolBar();
		myToolBar.setFloatable(false);
		myOpenAction = new OpenAction("Open", folderIcon);
		myRefreshAction = new RefreshAction("Refresh", refreshIcon);
		myLastAction = new LastAction("Last", lastIcon);
		myBackAction = new BackAction("Back", backIcon);
		myNextAction = new NextAction("Next", nextIcon);
		myFirstAction = new FirstAction("First", firstIcon);
		openButton = myToolBar.add(myOpenAction);
		openButton.setText("");
		refreshButton = myToolBar.add(myRefreshAction);
		refreshButton.setText("");
		lastButton = myToolBar.add(myLastAction);
		lastButton.setText("");
		backButton = myToolBar.add(myBackAction);
		backButton.setText("");
		nextButton = myToolBar.add(myNextAction);
		nextButton.setText("");
		firstButton = myToolBar.add(myFirstAction);
		firstButton.setText(""); 
		add(myToolBar, BorderLayout.NORTH);
		add(myLogTree, BorderLayout.CENTER);
	}
	
	private void createMenuItems(BioFrame myFrame){
		JMenuBar myMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem openMenuItem = fileMenu.add(myOpenAction);
		openMenuItem.setIcon(null);
		JMenuItem refreshMenuItem = fileMenu.add(myRefreshAction);
		refreshMenuItem.setIcon(null);
		myMenuBar.add(fileMenu);
		JMenu controlMenu = new JMenu("Control");
		JMenuItem lastMenuItem = controlMenu.add(myLastAction);
		lastMenuItem.setIcon(null);
		JMenuItem backMenuItem = controlMenu.add(myBackAction);
		backMenuItem.setIcon(null);
		JMenuItem nextMenuItem = controlMenu.add(myNextAction);
		nextMenuItem.setIcon(null);
		JMenuItem firstMenuItem = controlMenu.add(myFirstAction);
		firstMenuItem.setIcon(null);
		myMenuBar.add(controlMenu);
		JMenu myHelpMenu = new JMenu("Help");
		AboutAction myAboutAction = new AboutAction("About");
		JMenuItem myAboutItem = myHelpMenu.add(myAboutAction);
		myHelpMenu.add(myAboutItem);
		myMenuBar.add(myHelpMenu);
		myFrame.setJMenuBar(myMenuBar);
	}

	public static void main(String args[]){
		LogViewer myLogViewer = new LogViewer();
		BioFrame myFrame = new BioFrame("Log Viewer", false);
		myLogViewer.createMenuItems(myFrame);
		myFrame.getContentPane().add(myLogViewer);
		myFrame.setSize(640,480);
		myFrame.pack();
		myFrame.setVisible(true);
	}

	/**
	* Action that opens a file for input
	*/
	private class OpenAction extends AbstractAction{
		public OpenAction(String name, Icon icon){
			super(name, icon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			System.out.println("Open!");
			setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	* Action that refresh a file for input
	*/
	private class RefreshAction extends AbstractAction{
		public RefreshAction(String name, Icon icon){
			super(name, icon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			System.out.println("Refresh!");
			setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	* Action that goes back to the last timestep
	*/
	private class LastAction extends AbstractAction{
		public LastAction(String name, Icon icon){
			super(name, icon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			System.out.println("Last!");
			setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	* Action that goes back one timestep
	*/
	private class BackAction extends AbstractAction{
		public BackAction(String name, Icon icon){
			super(name, icon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			System.out.println("Back!");
			setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	* Action that goes up one timestep
	*/
	private class NextAction extends AbstractAction{
		public NextAction(String name, Icon icon){
			super(name, icon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			System.out.println("Next!");
			setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	* Action that goes to the first timestep
	*/
	private class FirstAction extends AbstractAction{
		public FirstAction(String name, Icon icon){
			super(name, icon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			System.out.println("First!");
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	*  Action that brings up a dialog box about authors, company, etc.
	*/
	private class AboutAction extends AbstractAction{
		public AboutAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			JOptionPane.showMessageDialog(null,"Advanced Life Support Simulation Log Viewer\nCopyright "+ new Character( '\u00A9' ) + " 2002, TRACLabs\nby Scott Bell and David Kortenkamp");
		}
	}
}



