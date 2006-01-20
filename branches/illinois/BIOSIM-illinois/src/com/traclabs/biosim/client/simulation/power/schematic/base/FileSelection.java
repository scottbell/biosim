/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/**
 * FileSelection is used by the Copy and Paste operations to save a File object
 * onto the clipboard. The File specifies a temporary file that contains the
 * figs to be pasted.
 * 
 * @author Kevin Kusy
 */
package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;

public class FileSelection implements Transferable, ClipboardOwner {
    static public DataFlavor fileFlavor;

    private DataFlavor[] supportedFlavors = new DataFlavor[1];

    private File file;

    public FileSelection(File parFile) {
        if (parFile == null) {
            System.out.println("parFile=null");
        }
        file = parFile;
        try {
            fileFlavor = new DataFlavor(Class.forName("java.io.File"), "File");
            supportedFlavors[0] = fileFlavor;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized DataFlavor[] getTransferDataFlavors() {
        return (supportedFlavors);
    }

    public boolean isDataFlavorSupported(DataFlavor parFlavor) {
        return (parFlavor.equals(fileFlavor));
    }

    public void lostOwnership(Clipboard parClipboard,
            Transferable parTransferable) {
        System.out.println("Lost ownership");
    }

    public synchronized Object getTransferData(DataFlavor parFlavor)
            throws UnsupportedFlavorException {
        if (parFlavor.equals(fileFlavor))
            return (file);
        throw new UnsupportedFlavorException(fileFlavor);
    }
}