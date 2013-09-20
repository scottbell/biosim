package com.traclabs.biosim.client.framework;

import java.awt.Cursor;

import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * A frame that has a closing listener and (if wanted) a popup dialog asking "Do
 * you want to close"
 * 
 * @author Scott Bell
 */

public class BioFrame extends javax.swing.JFrame {
	//Flag that decides if the frame pops up a dialog box when closing
    private boolean showCloseDialog = false;

    /**
     * Default constructor makes the frame and registers the close listener
     */
    public BioFrame() {
        buildGui();
    }

    /**
     * Constructor that makes the frame with the title specified and registers
     * the close listener
     * 
     * @param newTitle
     *            The title of the frame
     */
    public BioFrame(String newTitle) {
        super(newTitle);
        buildGui();
    }

    /**
     * Constructor that makes the frame with the title specified and registers
     * the close listener. Also has option to have frame popup a dialog when
     * closing
     * 
     * @param newTitle
     *            The title of the frame
     * @param show_close_dialog
     *            if <code>true</code>, popup dialog comes up asking "Do you
     *            want to close?", on <code>true</code> it doesn't
     */
    public BioFrame(String newTitle, boolean show_close_dialog) {
        super(newTitle);
        showCloseDialog = show_close_dialog;
        buildGui();
    }

    /**
     * Creates close listener and adds it to the window
     */
    private void buildGui() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        WindowCloseListener myWCL = new WindowCloseListener();
        this.addWindowListener(myWCL);
    }

    /**
     * Invoked on window closing
     */
    public void frameClosing() {
        if (!showCloseDialog
                || JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
                        this, "Do you really want to quit?", "Quit Dialog",
                        JOptionPane.YES_NO_OPTION)) {
            setVisible(false); // hide the Frame
            dispose(); // free the system resources
            frameExiting();
            System.exit(0); // close the application
        }
    }

    /**
     * Invoked on window exiting
     */
    protected void frameExiting() {
    }

    /**
     * The Window Close listener for this Frame
     */
    private class WindowCloseListener extends java.awt.event.WindowAdapter {
        public void windowClosing(java.awt.event.WindowEvent event) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            frameClosing();
            setCursor(Cursor.getDefaultCursor());
        }
    }

}

