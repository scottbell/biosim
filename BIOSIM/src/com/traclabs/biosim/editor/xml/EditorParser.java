package com.traclabs.biosim.editor.xml;

import java.io.Reader;
import java.util.Hashtable;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.graph.presentation.DefaultGraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigEdge;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.traclabs.biosim.editor.base.BiosimEditor;
import com.traclabs.biosim.editor.base.EditorDocument;
import com.traclabs.biosim.editor.base.EditorLayer;
import com.traclabs.biosim.editor.graph.EditorFigEdge;
import com.traclabs.biosim.editor.graph.ModuleFigNode;
import com.traclabs.biosim.editor.graph.ModuleNode;
import com.traclabs.biosim.editor.graph.EditorPort;

public class EditorParser extends DefaultHandler implements DocumentReader {

    ////////////////////////////////////////////////////////////////
    // static variables
    public static EditorParser SINGLETON = new EditorParser();

    // The Layer contains the Figs, while the Graph Model contains the Nodes.
    // We will reuse the Layer of the existing Editor, and attach a new Graph
    // Model
    // to it.
    protected Stack _layerStack = new Stack();

    protected Stack _graphModelStack = new Stack();

    protected EditorLayer _layer;

    protected MutableGraphModel _graphModel;

    protected ModuleFigNode _figNode;

    protected FigEdge _figEdge;

    // Store each Node as it is created, for use in creating Edges later.
    protected Hashtable _netNodeList = null;

    ////////////////////////////////////////////////////////////////
    // constructors

    protected EditorParser() {
        // Note: we currently use a single instance to parse many files.
        // _netNodeList = new Hashtable();
    }
                                         
    protected void handleEditor(Attributes attrs) {
        //System.out.println("Handling EDITOR tag");
    }

    /*
     * Set the display attributes that are common to the Editor nodes.
     */
    protected void handleAttributes(Attributes attrs, ModuleFigNode node) {
        // Get the known attributes from the AttributeList
        String strX = attrs.getValue("x");
        int x = (strX == null) ? 0 : Integer.parseInt(strX);
        String strY = attrs.getValue("y");
        int y = (strY == null) ? 0 : Integer.parseInt(strY);
        String strWidth = attrs.getValue("width");
        int width = (strWidth == null) ? 0 : Integer.parseInt(strWidth);
        String strHeight = attrs.getValue("height");
        int height = (strHeight == null) ? 0 : Integer.parseInt(strHeight);

        node.setHandleBox(x, y, width, height);
    }

    protected FigEdge handleEdge(Attributes attrs) {
        String fromName = attrs.getValue("FromNode");
        String toName = attrs.getValue("ToNode");

        if (fromName == null || toName == null) {
            System.out.println("In handleEdge, Names are null");
            return null;
        }

        ModuleNode fromNode = (ModuleNode) _netNodeList.get(fromName);
        ModuleNode toNode = (ModuleNode) _netNodeList.get(toName);

        if (fromNode == null || toNode == null) {
            System.out.println("In handleEdge, Nodes are null");
            return null;
        }

        ModuleFigNode fromFigNode = (ModuleFigNode) _layer
                .presentationFor(fromNode);
        ModuleFigNode toFigNode = (ModuleFigNode) _layer.presentationFor(toNode);

        if (fromFigNode == null || toFigNode == null) {
            System.out.println("In handleEdge, FigNodes are null");
        }

        EditorPort fromPort = (EditorPort) fromNode.getPort();
        EditorPort toPort = (EditorPort) toNode.getPort();

        Fig fromPortFig = fromFigNode.getPortFig();
        Fig toPortFig = toFigNode.getPortFig();

        Object newEdge;

        newEdge = _graphModel.connect(fromPort, toPort);
        FigEdge fe = (FigEdge) _layer.presentationFor(newEdge);
        fe.setSourcePortFig(fromPortFig);
        fe.setSourceFigNode(fromFigNode);
        fe.setDestPortFig(toPortFig);
        fe.setDestFigNode(toFigNode);

        String text = attrs.getValue("text");
        if (text != null && fe instanceof EditorFigEdge) {
            ((EditorFigEdge) fe).setText(text);
        }
        return fe;
    }

    protected void handleGraph(Attributes attrs) {
        //Find the owner node.
        _layer = _figNode.getNestedLayer();
        ModuleNode netNode = (ModuleNode) _figNode.getOwner();
        _graphModel = netNode.getNestedModel();

        _layerStack.push(_layer);
        _graphModelStack.push(_graphModel);
    }

    ////////////////////////////////////////////////////////////////
    // SAX parse method

    public void startElement(String namespaceURI, String sName, String qName,
            Attributes attrs) throws SAXException {
        //System.out.println("Entering Start Element with " + qName);
        if (qName.equals("EDITOR")) {
            handleEditor(attrs);
        //else if (qName.equals("NitrogenNode"){}
        } else if (qName.equals("Edge")) {
            handleEdge(attrs);
        } else if (qName.equals("Graph")) {
            handleGraph(attrs);
        } else {
            /* super.startElement(elementName, attrList); */
            System.err.println("Unknown tag " + qName + " encountered");
        }
    }

    public void endElement(String namespaceURI, String sName, String qName)
            throws SAXException {
        //System.out.println("Entering End Element with " + qName);
        if (qName.equals("Graph")) {
            _graphModelStack.pop();
            _layerStack.pop();
            _layer.repaintParent();
            _graphModel = (DefaultGraphModel) _graphModelStack.peek();
            _layer = (EditorLayer) _layerStack.peek();
        }
    }

    /** Reads the items from a file and adds them to the specified document. */
    public synchronized void openDocument(Reader in, EditorDocument doc)
            throws Exception {
        parseXML(in, doc.getRoot());
    }

    /** Reads the items from a file and adds them to the specified layer. */
    public synchronized void pasteSelections(Reader in, BiosimEditor ed)
            throws Exception {
        parseXML(in, (EditorLayer) ed.getLayerManager().getActiveLayer());
    }

    protected void parseXML(Reader in, EditorLayer layer) throws Exception {
        _layer = layer;
        _graphModel = (MutableGraphModel) _layer.getGraphModel();
        _netNodeList = new Hashtable();
        _graphModelStack = new Stack();
        _graphModelStack.push(_graphModel);
        _layerStack = new Stack();
        _layerStack.push(_layer);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);
        SAXParser pc = factory.newSAXParser();

        InputSource is = new InputSource(in);
        pc.parse(is, this);
        in.close();
    }
} /* end class EditorParser */
