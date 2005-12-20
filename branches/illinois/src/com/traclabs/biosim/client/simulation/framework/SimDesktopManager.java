package com.traclabs.biosim.client.simulation.framework;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.DefaultDesktopManager;
import javax.swing.JInternalFrame;

public class SimDesktopManager extends DefaultDesktopManager {
    private SimDesktop desktop;

    /** Current x offset for new child windows */
    private int newInternalX = 0;

    /** Current y offset for new child windows */
    private int newInternalY = 0;

    /** Rollover counter for y offsets */
    private int rollover = 0;

    /** Relative x offset of a new child window */
    private static int NEW_INTERNAL_X_OFFSET = 22;

    /** Relative y offset of a new child window */
    private static int NEW_INTERNAL_Y_OFFSET = 22;

    /** Default width of a new child window */
    private static int NEW_INTERNAL_WIDTH = 600;

    /** Default height of a new child window */
    private static int NEW_INTERNAL_HEIGHT = 400;

    public SimDesktopManager(SimDesktop pSimDesktop) {
        desktop = pSimDesktop;
    }

    private LinkedList getOpenFrames() {
        LinkedList<JInternalFrame> openFrames = new LinkedList<JInternalFrame>();
        Map framesMap = desktop.getInternalFrames();
        if (framesMap.size() == 0) {
            return openFrames;
        }
        for (Iterator iter = framesMap.values().iterator(); iter.hasNext();) {
            JInternalFrame currentFrame = (JInternalFrame) (iter.next());
            if (currentFrame.isVisible() && (!currentFrame.isClosed()))
                openFrames.add(currentFrame);
        }
        return openFrames;
    }

    /**
     * Tile all child windows.
     */
    public void tileWindows() {
        LinkedList openFrames = getOpenFrames();
        int framesCount = openFrames.size();
        if (framesCount == 0)
            return;
        int sqrt = (int) Math.sqrt(framesCount);
        int rows = sqrt;
        int cols = sqrt;
        if (rows * cols < framesCount) {
            cols++;
            if (rows * cols < framesCount) {
                rows++;
            }
        }

        Dimension size = desktop.getDesktopPane().getSize();

        int width = size.width / cols;
        int height = size.height / rows;
        int offsetX = 0;
        int offsetY = 0;

        JInternalFrame currentFrame;
        Iterator iter = openFrames.iterator();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols && (i * cols + j < framesCount); j++) {
                currentFrame = (JInternalFrame) (iter.next());
                normalizeFrame(currentFrame);
                resizeFrame(currentFrame, offsetX, offsetY, width, height);
                offsetX += width;
            }
            offsetX = 0;
            offsetY += height;
        }
    }

    public Rectangle getNextInternalFrameBounds() {
        if (newInternalY + NEW_INTERNAL_HEIGHT > desktop.getDesktopPane()
                .getHeight()) {
            rollover++;
            newInternalY = 0;
            newInternalX = rollover * NEW_INTERNAL_X_OFFSET;
        }
        Rectangle nextBounds = new Rectangle(newInternalX, newInternalY,
                NEW_INTERNAL_WIDTH, NEW_INTERNAL_HEIGHT);
        newInternalX += NEW_INTERNAL_X_OFFSET;
        newInternalY += NEW_INTERNAL_Y_OFFSET;
        return nextBounds;
    }

    public void stackWindows() {
        newInternalX = newInternalY = rollover = 0;
        Rectangle currentBounds;
        JInternalFrame currentFrame;
        LinkedList openFrames = getOpenFrames();
        if (openFrames.size() == 0)
            return;
        Iterator iter = openFrames.iterator();
        for (iter = openFrames.iterator(); iter.hasNext();) {
            currentFrame = (JInternalFrame) (iter.next());
            normalizeFrame(currentFrame);
            currentBounds = getNextInternalFrameBounds();
            resizeFrame(currentFrame, currentBounds.x, currentBounds.y,
                    currentBounds.width, currentBounds.height);
            try {
                currentFrame.setSelected(true);
            } catch (PropertyVetoException e) {
            }
        }
    }

    private void normalizeFrame(JInternalFrame frame) {
        try {
            if (frame.isIcon())
                frame.setIcon(false);
            if (frame.isMaximum())
                frame.setMaximum(false);
        } catch (PropertyVetoException e) {
        }
    }
}