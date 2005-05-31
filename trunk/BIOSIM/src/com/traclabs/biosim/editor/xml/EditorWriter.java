package com.traclabs.biosim.editor.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.tigris.gef.base.Selection;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigEdge;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.traclabs.biosim.editor.base.BiosimEditor;
import com.traclabs.biosim.editor.base.EditorDocument;
import com.traclabs.biosim.editor.base.EditorLayer;
import com.traclabs.biosim.editor.graph.FigModuleEdge;
import com.traclabs.biosim.editor.graph.FigModuleNode;

/**
 * Writes a Editor Document to a file.
 * 
 * @author scott
 */
public class EditorWriter {
    private static EditorWriter mySingleton;

    //The File handle of where we're going to put the XML output
    private File myOutputFile;

    //Used to transform SAX into a file output stream
    private TransformerHandler myHandler;

    //Empty attributes used to tack onto LogNodes (none of them have
    // attributes)
    private AttributesImpl emptyAtts;

    //Whether XML file has been created, tags initialized, etc
    private boolean initialized = false;

    //The writer that takes the XML stream and outputs it to a file
    private FileWriter myFileWriter;

    private EditorWriter() {
    }

    /**
     * Initializes the XML output process. Sets the encoding methods, begins
     * root tags, opens file, etc.
     * 
     * @param document
     * @param file
     */
    private void initializeXML(File file, EditorDocument document)
            throws IOException, TransformerConfigurationException, SAXException {
        // PrintWriter from a Servlet
        emptyAtts = new AttributesImpl();
        myFileWriter = new FileWriter(myOutputFile);
        StreamResult streamResult = new StreamResult(myFileWriter);
        SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory
                .newInstance();
        myHandler = tf.newTransformerHandler();
        Transformer serializer = myHandler.getTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        serializer.setOutputProperty(OutputKeys.METHOD, "xml");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        myHandler.setResult(streamResult);
        myHandler.startDocument();
        myHandler.startElement("", "", "biosim", emptyAtts);
        initialized = true;
    }

    /**
     * Ends root tag and flushs output
     * 
     * @param document
     * @param file
     */
    private void endXML(File file, EditorDocument document)
            throws SAXException, IOException {
        myHandler.endElement("", "", "biosim");
        myHandler.endDocument();
        myFileWriter.flush();
    }

    private void writeXML(File file, EditorDocument document)
            throws SAXException {

    }

    /**
     * @return
     */
    public static EditorWriter getWriter() {
        if (mySingleton != null)
            return mySingleton;
        mySingleton = new EditorWriter();
        return mySingleton;

    }

    /**
     * @param file
     * @param document
     */
    public synchronized void saveDocument(File file, EditorDocument document) {
        try {
            initializeXML(file, document);
            writeXML(file, document);
            endXML(file, document);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param file
     * @param editor
     */
    public void copySelections(File file, BiosimEditor editor) {
    }

    private static void saveGraph(EditorLayer lay, Writer out)
            throws IOException {
        if (lay == null) {
            return;
        }
        // Do not save empty leaf layers.
        if (lay.getContents().size() == 0) {
            return;
        }

        //out.write(tab(indent) + "<Graph>\n");
        //saveFigs(lay.getContents(), out, indent + 1);
        //out.write(tab(indent) + "</Graph>\n");
    }

    /* Save a list of figs. */
    private static void saveFigs(java.util.List figs, Writer out) {
        FigModuleNode vf;
        try {
            // For each Editor Fig, write the information
            Iterator i = figs.iterator();
            /* In the first loop, print out all the EditorFigNodes */
            while (i.hasNext()) {
                Fig f = (Fig) i.next();
                if (f instanceof FigModuleNode) {
                    vf = (FigModuleNode) f;
                    saveFigNode(vf, out);
                } else {
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
                if (f instanceof FigModuleEdge) {
                    //System.out.println("Edge Fig found");
                    FigModuleEdge fe = (FigModuleEdge) f;
                    saveFigEdge(fe, out);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /** Saves a fig node. */
    private static void saveFigNode(FigModuleNode vf, Writer out) throws IOException {
        /*
         * ModuleNode currentNode = (ModuleNode) vf.getOwner(); SimBioModuleImpl
         * currentModule = currentNode.getSimBioModuleImpl();
         * out.write(tab(indent) + " <" + currentModule.getModuleName() + "\n");
         * saveTextAttr(vf.getText(), out, indent + 1); Rectangle rect =
         * vf.getHandleBox(); out.write(tab(indent + 1) + "x=\"" + (int)
         * rect.getX() + "\"\n"); out.write(tab(indent + 1) + "y=\"" + (int)
         * rect.getY() + "\"\n"); out .write(tab(indent + 1) + "width=\"" +
         * (int) rect.getWidth() + "\"\n"); out.write(tab(indent + 1) +
         * "height=\"" + (int) rect.getHeight() + "\"\n"); out.write(tab(indent) +
         * ">\n"); saveGraph(vf.getNestedLayer(), out, indent + 1);
         * out.write(tab(indent) + " </" + currentModule.getModuleName() +
         * ">\n");
         */
    }

    /* Saves a fig edge. */
    private static void saveFigEdge(FigModuleEdge fe, Writer out) throws IOException {
        /*
         * ModuleEdge myEdge = (ModuleEdge) fe.getOwner(); ModuleNode sourceNode =
         * (ModuleNode) myEdge.getSourcePort().getParent(); ModuleNode destNode =
         * (ModuleNode) myEdge.getDestPort().getParent(); out.write(tab(indent) + "
         * <Edge\n"); saveTextAttr(myEdge.getName(), out, indent + 1);
         * out.write(tab(indent + 1) + "FromNode=\"" +
         * sourceNode.getSimBioModuleImpl().getModuleName() + "\"\n");
         * out.write(tab(indent + 1) + "ToNode=\"" +
         * destNode.getSimBioModuleImpl().getModuleName() + "\"\n");
         * out.write(tab(indent) + "/>\n");
         */
    }

    /**
     * Saves the text attribute of a node or an edge. Newlines were being lost
     * in multiline labels. Newline characters written directly to the XML file
     * were converted to whitespace by the XML parser when the file was read
     * back in. The XML parser also interpreted the escape sequence \n as two
     * separate characters. Substituting the special HTML character codes seem
     * to work.
     */
    private static void saveTextAttr(String label, Writer out)
            throws IOException {
        /*
         * out.write(tab(indent) + "text=\""); StringTokenizer lines = new
         * StringTokenizer(label, "\n\r", true); while (lines.hasMoreTokens()) {
         * String curLine = lines.nextToken(); if (curLine.equals("\n")) {
         * out.write("&#10;"); } else if (curLine.equals("\r")) {
         * out.write("&#13;"); } else { out.write(curLine); } }
         * out.write("\"\n");
         */
    }

    /**
     * Find the selected figs in the editor filtering out the edges that do not
     * have both source and destination nodes selected.
     */
    private static Vector getSelectedFigs(BiosimEditor ed) {
        Vector selections = ed.getSelectionManager().selections();

        // Create a list of figs to be copied.
        Vector figs = new Vector();
        Enumeration theElements = selections.elements();
        while (theElements.hasMoreElements()) {
            Selection sel = (Selection) theElements.nextElement();
            Fig fig = sel.getContent();

            if (fig instanceof FigModuleEdge) {
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