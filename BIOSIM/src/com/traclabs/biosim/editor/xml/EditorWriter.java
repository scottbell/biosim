package com.traclabs.biosim.editor.xml;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.tigris.gef.presentation.Fig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.helpers.AttributesImpl;

import com.traclabs.biosim.editor.base.BiosimEditor;
import com.traclabs.biosim.editor.base.EditorDocument;
import com.traclabs.biosim.editor.base.EditorLayer;
import com.traclabs.biosim.editor.graph.FigModuleEdge;
import com.traclabs.biosim.editor.graph.FigModuleNode;
import com.traclabs.biosim.editor.graph.ModuleEdge;
import com.traclabs.biosim.editor.graph.ModuleNode;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * Writes a Editor Document to a file.
 * 
 * @author scott
 */
public class EditorWriter {
    //Empty attributes
    private  final AttributesImpl emptyAtts = new AttributesImpl();

    //The writer that takes the XML stream and outputs it to a file
    private FileWriter myFileWriter;

    private Transformer myTransformer;

    private Document myXMLDocument;
    
    private EditorDocument myEditorDocument;

    private Node myBiosimNode;
    
    //Simulation Nodes
    private Node mySimAirNode;
    private Node mySimCrewNode;
    private Node mySimEnvironmentNode;
    private Node mySimFoodNode;
    private Node mySimFrameworkNode;
    private Node mySimPowerNode;
    private Node mySimWasteNode;
    private Node mySimWaterNode;
    
    //Sensor Nodes
    private Node mySensorAirNode;
    private Node mySensorCrewNode;
    private Node mySensorEnvironmentNode;
    private Node mySensorFoodNode;
    private Node mySensorFrameworkNode;
    private Node mySensorPowerNode;
    private Node mySensorWasteNode;
    private Node mySensorWaterNode;
    
    //Actuator Nodes
    private Node myActuatorAirNode;
    private Node myActuatorCrewNode;
    private Node myActuatorEnvironmentNode;
    private Node myActuatorFoodNode;
    private Node myActuatorFrameworkNode;
    private Node myActuatorPowerNode;
    private Node myActuatorWasteNode;
    private Node myActuatorWaterNode;

    private Node mySimBioModulesNode;

    private Node mySensorNode;

    private Node myActuatorNode;
    

    public EditorWriter(EditorDocument pEditorDocument) {
        myEditorDocument = pEditorDocument;
    }

    /**
     * @param file
     * @param document
     */
    public void saveDocument(File file) {
        try {
            initializeXML();
            writeXML();
            endXML(file);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file
     * @param editor
     */
    public void copySelections(File file, BiosimEditor editor) {
    }

    /**
     * Initializes the XML output process. Sets the encoding methods, begins
     * root tags, opens file, etc.
     * 
     * @param document
     * @param file
     * @throws ParserConfigurationException
     */
    private void initializeXML()
            throws IOException, TransformerConfigurationException,
            ParserConfigurationException {
        //initialize factory
        TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        transformerFactory.setAttribute("indent-number", new Integer(4));
        myTransformer = transformerFactory.newTransformer();
        myTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        myTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
        myTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        myXMLDocument = builder.newDocument();

        //create root element
        Element biosimElement = (Element) myXMLDocument.createElement("biosim");
        biosimElement.setAttribute("xmlns:xsi",
                XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
        biosimElement.setAttribute("xsi:noNamespaceSchemaLocation",
                "BiosimInitSchema.xsd");
        myXMLDocument.appendChild(biosimElement);
        myBiosimNode = (Node) biosimElement;
        
        //create globals
        Node globalElement = myXMLDocument.createElement("Globals");
        myBiosimNode.appendChild(globalElement);

        //create comment
        Node authorElement = myXMLDocument.createElement("author");
        globalElement.appendChild(authorElement);
        String date = DateFormat.getDateTimeInstance().format(new Date());
        Node authorText = myXMLDocument
                .createTextNode("Generated by BioSim Editor on " + date);
        authorElement.appendChild(authorText);
        
        mySimBioModulesNode = myXMLDocument.createElement("SimBioModules");
        mySimAirNode = myXMLDocument.createElement("air");
        mySimBioModulesNode.appendChild(mySimAirNode);
        mySimCrewNode = myXMLDocument.createElement("crew");
        mySimBioModulesNode.appendChild(mySimCrewNode);
        mySimEnvironmentNode = myXMLDocument.createElement("environment");
        mySimBioModulesNode.appendChild(mySimEnvironmentNode);
        mySimFoodNode = myXMLDocument.createElement("food");
        mySimBioModulesNode.appendChild(mySimFoodNode);
        mySimFrameworkNode = myXMLDocument.createElement("framework");
        mySimBioModulesNode.appendChild(mySimFoodNode);
        mySimPowerNode = myXMLDocument.createElement("power");
        mySimBioModulesNode.appendChild(mySimPowerNode);
        mySimWasteNode = myXMLDocument.createElement("waste");
        mySimBioModulesNode.appendChild(mySimWasteNode);
        mySimWaterNode = myXMLDocument.createElement("water");
        mySimBioModulesNode.appendChild(mySimWasteNode);
        myBiosimNode.appendChild(mySimBioModulesNode);
        
        mySensorNode = myXMLDocument.createElement("Sensors");
        mySensorAirNode = myXMLDocument.createElement("air");
        mySensorNode.appendChild(mySensorAirNode);
        mySensorCrewNode = myXMLDocument.createElement("crew");
        mySensorNode.appendChild(mySensorCrewNode);
        mySensorEnvironmentNode = myXMLDocument.createElement("environment");
        mySensorNode.appendChild(mySensorEnvironmentNode);
        mySensorFoodNode = myXMLDocument.createElement("food");
        mySensorNode.appendChild(mySensorFoodNode);
        mySensorFrameworkNode = myXMLDocument.createElement("framework");
        mySensorNode.appendChild(mySensorFoodNode);
        mySensorPowerNode = myXMLDocument.createElement("power");
        mySensorNode.appendChild(mySensorPowerNode);
        mySensorWasteNode = myXMLDocument.createElement("waste");
        mySensorNode.appendChild(mySensorWasteNode);
        mySensorWaterNode = myXMLDocument.createElement("water");
        mySensorNode.appendChild(mySensorWasteNode);
        myBiosimNode.appendChild(mySensorNode);
        
        myActuatorNode = myXMLDocument.createElement("Actuators");
        myActuatorAirNode = myXMLDocument.createElement("air");
        myActuatorNode.appendChild(myActuatorAirNode);
        myActuatorCrewNode = myXMLDocument.createElement("crew");
        myActuatorNode.appendChild(myActuatorCrewNode);
        myActuatorEnvironmentNode = myXMLDocument.createElement("environment");
        myActuatorNode.appendChild(myActuatorEnvironmentNode);
        myActuatorFoodNode = myXMLDocument.createElement("food");
        myActuatorNode.appendChild(myActuatorFoodNode);
        myActuatorFrameworkNode = myXMLDocument.createElement("framework");
        myActuatorNode.appendChild(myActuatorFoodNode);
        myActuatorPowerNode = myXMLDocument.createElement("power");
        myActuatorNode.appendChild(myActuatorPowerNode);
        myActuatorWasteNode = myXMLDocument.createElement("waste");
        myActuatorNode.appendChild(myActuatorWasteNode);
        myActuatorWaterNode = myXMLDocument.createElement("water");
        myActuatorNode.appendChild(myActuatorWasteNode);
        myBiosimNode.appendChild(myActuatorNode);
    }

    /**
     * Ends root tag and flushs output
     * 
     * @param document
     * @param file
     * @throws TransformerException
     */
    private void endXML(File file)
            throws IOException, TransformerException {
        myFileWriter = new FileWriter(file);
        myTransformer.transform(new DOMSource(myXMLDocument), new StreamResult(
                myFileWriter));
        myFileWriter.flush();
        myFileWriter.close();
    }

    /**
     * 
     */
    private static void pruneTopNodes(Node topLevelNode) {
        //Simulation Nodes
        NodeList childNodes = topLevelNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++){
            Node currentNode = childNodes.item(i);
            if (!currentNode.hasChildNodes()){
                topLevelNode.removeChild(currentNode);
                i--;
            }
        }
    }

    private void writeXML() {
        pruneTopNodes(mySimBioModulesNode);
        pruneTopNodes(mySensorNode);
        pruneTopNodes(myActuatorNode);
        pruneTopNodes(myBiosimNode);
    }

    private void saveGraph(EditorLayer lay, Writer out)
            throws IOException {
        if (lay == null) {
            return;
        }
        // Do not save empty leaf layers.
        if (lay.getContents().size() == 0) {
            return;
        }

        out.write("<Graph>\n");
        saveFigs(lay.getContents(), out);
        out.write("</Graph>\n");
    }

    /* Save a list of figs. */
    private void saveFigs(java.util.List figs, Writer out) {
        FigModuleNode vf;
        try {
            // For each Editor Fig, write the information
            Iterator i = figs.iterator();
            // In the first loop, print out all the EditorFigNodes
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
                    Logger.getLogger(EditorWriter.class)
                            .debug("Edge Fig found");
                    FigModuleEdge fe = (FigModuleEdge) f;
                    saveFigEdge(fe, out);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /** Saves a fig node. */
    private void saveFigNode(FigModuleNode vf, Writer out)
            throws IOException {
        ModuleNode currentNode = (ModuleNode) vf.getOwner();
        SimBioModuleImpl currentModule = currentNode.getSimBioModuleImpl();
        out.write(" <" + currentModule.getModuleName() + "\n");
        Rectangle rect = vf.getHandleBox();
        out.write("x=\"" + (int) rect.getX() + "\"\n");
        out.write("y=\"" + (int) rect.getY() + "\"\n");
        out.write("width=\"" + (int) rect.getWidth() + "\"\n");
        out.write("height=\"" + (int) rect.getHeight() + "\"\n");
        out.write(">\n");
        saveGraph(vf.getNestedLayer(), out);
        out.write(" </" + currentModule.getModuleName() + ">\n");
    }

    /* Saves a fig edge. */
    private void saveFigEdge(FigModuleEdge fe, Writer out)
            throws IOException {
        ModuleEdge myEdge = (ModuleEdge) fe.getOwner();
        ModuleNode sourceNode = (ModuleNode) myEdge.getSourcePort().getParent();
        ModuleNode destNode = (ModuleNode) myEdge.getDestPort().getParent();
        out.write("<Edge\n");
        out.write("FromNode=\""
                + sourceNode.getSimBioModuleImpl().getModuleName() + "\"\n");
        out.write("ToNode=\"" + destNode.getSimBioModuleImpl().getModuleName()
                + "\"\n");
        out.write("/>\n");
    }
}