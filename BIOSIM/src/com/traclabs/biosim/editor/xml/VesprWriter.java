/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */

/*
 * Created on Mar 11, 2005
 */
package com.traclabs.biosim.editor.xml;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.tigris.gef.base.Selection;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigEdge;

import com.traclabs.biosim.editor.base.VesprDocument;
import com.traclabs.biosim.editor.base.VesprEditor;
import com.traclabs.biosim.editor.base.VesprLayer;
import com.traclabs.biosim.editor.graph.VesprFigEdge;
import com.traclabs.biosim.editor.graph.VesprFigNode;

/**
 * Writes a VesprDocument to a file.
 * 
 * @author kkusy
 */
public class VesprWriter implements DocumentWriter {
    public static final VesprWriter SINGLETON = new VesprWriter();

    protected VesprWriter() {
    }

    synchronized public void saveDocument(Writer out, VesprDocument doc)
            throws Exception {
        try {
            // Write the beginning tag
            out.write("<" + doc.getDocumentTag() + ">\n");
            saveFigs(doc.getRoot().getContents(), out, 1);
            out.write("</" + doc.getDocumentTag() + ">");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void saveGraph(VesprLayer lay, Writer out, int indent) {
        if (lay == null) {
            return;
        }
        try {
            // Do not save empty leaf layers.
            if (lay.getContents().size() == 0) {
                return;
            }

            out.write(tab(indent) + "<Graph>\n");
            saveFigs(lay.getContents(), out, indent + 1);
            out.write(tab(indent) + "</Graph>\n");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /* Save a list of figs. */
    protected void saveFigs(java.util.List figs, Writer out, int indent) {
        VesprFigNode vf;
        try {
            // For each VESPR Fig, write the information
            Iterator i = figs.iterator();
            /* In the first loop, print out all the VesprFigNodes */
            while (i.hasNext()) {
                Fig f = (Fig) i.next();
                if (f instanceof VesprFigNode) {
                    //System.out.println("VESPR Fig found");
                    vf = (VesprFigNode) f;
                    saveFigNode(vf, out, indent);
                } else {
                    //System.out.println("Non-VESPR Fig found");
                }
            }

            /**
             * In the second loop, print out all the Edges. Note that an Edge
             * must appear after the nodes that it connects; this is the
             * simplest way to guarantee that.
             */
            i = figs.iterator();
            while (i.hasNext()) {
                Fig f = (Fig) i.next();
                if (f instanceof VesprFigEdge) {
                    //System.out.println("Edge Fig found");
                    VesprFigEdge fe = (VesprFigEdge) f;
                    saveFigEdge(fe, out, indent);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /** Saves a fig node. */
    protected void saveFigNode(VesprFigNode vf, Writer out, int indent)
            throws IOException {
        out.write(tab(indent) + "<" + vf.getTag() + "\n");
        out.write(tab(indent + 1) + "Id=\""
                + Integer.toString(vf.hashCode(), 16) + "\"\n");
        //out.write(t.tab(indent+1) + "text=\"" + vf.getText() + "\"\n");
        saveTextAttr(vf.getText(), out, indent + 1);
        Rectangle rect = vf.getHandleBox();
        out.write(tab(indent + 1) + "x=\"" + (int) rect.getX() + "\"\n");
        out.write(tab(indent + 1) + "y=\"" + (int) rect.getY() + "\"\n");
        out
                .write(tab(indent + 1) + "width=\"" + (int) rect.getWidth()
                        + "\"\n");
        out.write(tab(indent + 1) + "height=\"" + (int) rect.getHeight()
                + "\"\n");
        out.write(tab(indent) + ">\n");
        saveGraph(vf.getNestedLayer(), out, indent + 1);
        out.write(tab(indent) + "</" + vf.getTag() + ">\n");
    }

    /* Saves a fig edge. */
    protected void saveFigEdge(VesprFigEdge fe, Writer out, int indent)
            throws IOException {
        out.write(tab(indent) + "<Edge\n");
        if (fe.getText().length() != 0) {
            saveTextAttr(fe.getText(), out, indent + 1);
        }
        out.write(tab(indent + 1) + "FromNode=\""
                + Integer.toString(fe.getSourceFigNode().hashCode(), 16)
                + "\"\n");
        out
                .write(tab(indent + 1) + "ToNode=\""
                        + Integer.toString(fe.getDestFigNode().hashCode(), 16)
                        + "\"\n");
        out.write(tab(indent) + "/>\n");
    }

    /**
     * Saves the text attribute of a node or an edge. Newlines were being lost
     * in multiline labels. Newline characters written directly to the XML file
     * were converted to whitespace by the XML parser when the file was read
     * back in. The XML parser also interpreted the escape sequence \n as two
     * separate characters. Substituting the special HTML character codes seem
     * to work.
     */
    protected static void saveTextAttr(String label, Writer out, int indent)
            throws IOException {
        out.write(tab(indent) + "text=\"");
        StringTokenizer lines = new StringTokenizer(label, "\n\r", true);
        while (lines.hasMoreTokens()) {
            String curLine = lines.nextToken();
            if (curLine.equals("\n")) {
                out.write("&#10;");
            } else if (curLine.equals("\r")) {
                out.write("&#13;");
            } else {
                out.write(curLine);
            }
        }
        out.write("\"\n");
    }

    static protected String tab(int num) {
        String result = "";
        for (int i = 0; i < num; i++) {
            result += "\t";
        }
        return result;
    }

    /**
     * Saves all Figs that have been selected in the specified editor to a
     * temporary file for the Copy operation.
     */
    public synchronized void copySelections(Writer out, VesprEditor ed)
            throws Exception {
        VesprDocument doc = (VesprDocument) ed.document();
        // Write the beginning tag
        out.write("<" + doc.getDocumentTag() + ">\n");
        saveFigs(getSelectedFigs(ed), out, 1);
        out.write("</" + doc.getDocumentTag() + ">");
        out.close();
    }

    /**
     * Find the selected figs in the editor filtering out the edges that do not
     * have both source and destination nodes selected.
     */
    protected Vector getSelectedFigs(VesprEditor ed) {
        Vector selections = ed.getSelectionManager().selections();

        // Create a list of figs to be copied.
        Vector figs = new Vector();
        Enumeration theElements = selections.elements();
        while (theElements.hasMoreElements()) {
            Selection sel = (Selection) theElements.nextElement();
            Fig fig = sel.getContent();

            if (fig instanceof VesprFigEdge) {
                // Only include edges where both source and destination
                // nodes are selected.
                FigEdge edge = (FigEdge) fig;
                Fig src = edge.getSourceFigNode();
                Fig dest = edge.getDestFigNode();

                if (ed.getSelectionManager().containsFig(src)
                        && ed.getSelectionManager().containsFig(dest)) {
                    figs.add(fig);
                }
            } else {
                // Add all nodes
                figs.add(fig);
            }
        }
        return figs;
    }
}