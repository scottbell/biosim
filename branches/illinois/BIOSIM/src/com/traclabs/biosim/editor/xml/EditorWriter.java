package com.traclabs.biosim.editor.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import org.tigris.gef.presentation.Fig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.traclabs.biosim.editor.base.EditorDocument;
import com.traclabs.biosim.editor.graph.FigModuleNode;
import com.traclabs.biosim.editor.graph.ModuleEdge;
import com.traclabs.biosim.editor.graph.ModuleNode;
import com.traclabs.biosim.editor.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;
import com.traclabs.biosim.server.simulation.framework.PassiveModuleImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

/**
 * Writes a Editor Document to a file.
 * 
 * @author scott
 */
public class EditorWriter {
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
     * Initializes the XML output process. Sets the encoding methods, begins
     * root tags, opens file, etc.
     * 
     * @param document
     * @param file
     * @throws ParserConfigurationException
     */
    private void initializeXML()
            throws TransformerConfigurationException,
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
        Element biosimElement = myXMLDocument.createElement("biosim");
        biosimElement.setAttribute("xmlns:xsi",
                XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
        biosimElement.setAttribute("xsi:noNamespaceSchemaLocation",
                "BiosimInitSchema.xsd");
        myXMLDocument.appendChild(biosimElement);
        myBiosimNode = biosimElement;
        
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
        mySimBioModulesNode.appendChild(mySimFrameworkNode);
        mySimPowerNode = myXMLDocument.createElement("power");
        mySimBioModulesNode.appendChild(mySimPowerNode);
        mySimWasteNode = myXMLDocument.createElement("waste");
        mySimBioModulesNode.appendChild(mySimWasteNode);
        mySimWaterNode = myXMLDocument.createElement("water");
        mySimBioModulesNode.appendChild(mySimWaterNode);
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
        mySensorNode.appendChild(mySensorFrameworkNode);
        mySensorPowerNode = myXMLDocument.createElement("power");
        mySensorNode.appendChild(mySensorPowerNode);
        mySensorWasteNode = myXMLDocument.createElement("waste");
        mySensorNode.appendChild(mySensorWasteNode);
        mySensorWaterNode = myXMLDocument.createElement("water");
        mySensorNode.appendChild(mySensorWaterNode);
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
        myActuatorNode.appendChild(myActuatorFrameworkNode);
        myActuatorPowerNode = myXMLDocument.createElement("power");
        myActuatorNode.appendChild(myActuatorPowerNode);
        myActuatorWasteNode = myXMLDocument.createElement("waste");
        myActuatorNode.appendChild(myActuatorWasteNode);
        myActuatorWaterNode = myXMLDocument.createElement("water");
        myActuatorNode.appendChild(myActuatorWaterNode);
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
        saveFigs(myEditorDocument.getRoot().getContents());
        
        //do pruning
        pruneTopNodes(mySimBioModulesNode);
        pruneTopNodes(mySensorNode);
        pruneTopNodes(myActuatorNode);
        pruneTopNodes(myBiosimNode);
    }

    /* Save a list of figs. */
    private void saveFigs(java.util.List figs) {
        FigModuleNode vf;
        // For each Editor Fig, write the information
		Iterator i = figs.iterator();
		// In the first loop, print out all the EditorFigNodes
		while (i.hasNext()) {
		    Fig f = (Fig) i.next();
		    if (f instanceof FigModuleNode) {
		        vf = (FigModuleNode) f;
		        saveFigNode(vf);
		    } else {
		    }
		}
    }

    /** Saves a fig node. */
    private void saveFigNode(FigModuleNode vf) {
        ModuleNode currentModuleNode = (ModuleNode) vf.getOwner();
        SimBioModuleImpl currentModule = currentModuleNode.getSimBioModuleImpl();
        String corbaName = currentModuleNode.getModuleType();
        Element newModuleElement = (myXMLDocument.createElement(corbaName));
        newModuleElement.setAttribute("moduleName", currentModule.getModuleName());
        if (!(currentModule instanceof PassiveModuleImpl))
            configureModuleFlowRates(currentModuleNode, newModuleElement);
        else if (currentModule instanceof StoreImpl)
            configureStoreModule((StoreImpl)currentModule, newModuleElement, (StoreNode)currentModuleNode);
        //find where to put in SimBioModules Node
        appendSimBioModule(getResourcePackageName(currentModule), newModuleElement);
    }
    
    private static String getResourcePackageName(BioModuleImpl currentModule){
        Class currentClass = currentModule.getClass();
        String packageName = currentClass.getPackage().toString();
        String[] individualPackages = packageName.split("\\.");
        String resourcePackageName = individualPackages[individualPackages.length - 1];
        return resourcePackageName;
    }

    /**
     * @param impl
     * @param newElementNode
     * @param node
     */
    private void configureStoreModule(StoreImpl store, Element newElementNode, StoreNode storeNode) {
        newElementNode.setAttribute("capacity", Float.toString(store.getInitialCapacity()));
        newElementNode.setAttribute("level", Float.toString(store.getInitialLevel()));
        if (storeNode.isSensed())
            createSensorElement(storeNode.getSensorImpl(), store);
    }

    /**
     * @param currentNode
     * @param resourcePackageName
     */
    private void configureModuleFlowRates(ModuleNode currentNode, Node currentElementNode) {
        List consumerEdges = currentNode.getInBoundEdges();
        List producerEdges = currentNode.getOutBoundEdges();

        setConsumersOrProducerFlowRates(consumerEdges, currentElementNode, "inputs");
        setConsumersOrProducerFlowRates(producerEdges, currentElementNode, "outputs");
    }

    /**
     * @param edges
     * @param currentElementNode
     * @param resourcePackageName
     * @param string
     */
    private void setConsumersOrProducerFlowRates(List edges, Node currentElementNode, String storeFieldName) {
        for (Iterator iter = edges.iterator(); iter.hasNext();){
            ModuleEdge currentEdge = (ModuleEdge)iter.next();
            if (currentEdge.isSensed())
                createSensorElement(currentEdge.getSensorImpl(), currentEdge.getActiveModule());
            if (currentEdge.isActuated())
                createActuatorElement(currentEdge.getActuatorImpl(), currentEdge.getActiveModule());
            String currentFlowRateType = currentEdge.getFlowRateType();
            Element newFlowRateElement = (myXMLDocument.createElement(currentFlowRateType));
            createFlowRateAttributes(currentEdge.getOperations(), newFlowRateElement);
            newFlowRateElement.setAttribute(storeFieldName, currentEdge.getPassiveModuleImpl().getModuleName());
            currentElementNode.appendChild(newFlowRateElement);
        }
    }

    /**
     * @param currentEdge
     * @param resourcePackageName
     * 
     */
    private void createActuatorElement(GenericActuatorImpl actuatorToAdd, SimBioModuleImpl moduleToWatch) {
        Element newActuatorElement = (myXMLDocument.createElement(getCorbaName(actuatorToAdd)));
        newActuatorElement.setAttribute("name", actuatorToAdd.getModuleName());
        newActuatorElement.setAttribute("input", moduleToWatch.getModuleName());
        newActuatorElement.setAttribute("index", "0");
        appendActuator(getResourcePackageName(actuatorToAdd), newActuatorElement);
    }

    /**
     * @param currentEdge
     * @param resourcePackageName
     * 
     */
    private void createSensorElement(GenericSensorImpl sensorToAdd, SimBioModuleImpl moduleToWatch) {
        Element newSensorElement = (myXMLDocument.createElement(getCorbaName(sensorToAdd)));
        newSensorElement.setAttribute("name", sensorToAdd.getModuleName());
        newSensorElement.setAttribute("input", moduleToWatch.getModuleName());
        newSensorElement.setAttribute("index", "0");
        appendSensor(getResourcePackageName(sensorToAdd), newSensorElement);
    }
    
    private void appendSensor(String resourcePackageName, Node sensorNode){
        if (resourcePackageName.equals("air")){
            mySensorAirNode.appendChild(sensorNode);
        }
        else if (resourcePackageName.equals("crew")){
            mySensorCrewNode.appendChild(sensorNode);
        }
        else if (resourcePackageName.equals("environment")){
            mySensorEnvironmentNode.appendChild(sensorNode);
        }
        else if (resourcePackageName.equals("food")){
            mySensorFoodNode.appendChild(sensorNode);
        }
        else if (resourcePackageName.equals("framework")){
            mySensorFrameworkNode.appendChild(sensorNode);
        }
        else if (resourcePackageName.equals("power")){
            mySensorPowerNode.appendChild(sensorNode);
        }
        else if (resourcePackageName.equals("waste")){
            mySensorWasteNode.appendChild(sensorNode);
        }
        else if (resourcePackageName.equals("water")){
            mySensorWaterNode.appendChild(sensorNode);
        }
    }
    
    private void appendActuator(String resourcePackageName, Node actuatorNode){
        if (resourcePackageName.equals("air")){
            myActuatorAirNode.appendChild(actuatorNode);
        }
        else if (resourcePackageName.equals("crew")){
            myActuatorCrewNode.appendChild(actuatorNode);
        }
        else if (resourcePackageName.equals("environment")){
            myActuatorEnvironmentNode.appendChild(actuatorNode);
        }
        else if (resourcePackageName.equals("food")){
            myActuatorFoodNode.appendChild(actuatorNode);
        }
        else if (resourcePackageName.equals("framework")){
            myActuatorFrameworkNode.appendChild(actuatorNode);
        }
        else if (resourcePackageName.equals("power")){
            myActuatorPowerNode.appendChild(actuatorNode);
        }
        else if (resourcePackageName.equals("waste")){
            myActuatorWasteNode.appendChild(actuatorNode);
        }
        else if (resourcePackageName.equals("water")){
            myActuatorWaterNode.appendChild(actuatorNode);
        }
    }
    
    private void appendSimBioModule(String resourcePackageName, Node moduleNode){
        if (resourcePackageName.equals("air")){
            mySimAirNode.appendChild(moduleNode);
        }
        else if (resourcePackageName.equals("crew")){
            mySimCrewNode.appendChild(moduleNode);
        }
        else if (resourcePackageName.equals("environment")){
            mySimEnvironmentNode.appendChild(moduleNode);
        }
        else if (resourcePackageName.equals("food")){
            mySimFoodNode.appendChild(moduleNode);
        }
        else if (resourcePackageName.equals("framework")){
            mySimFrameworkNode.appendChild(moduleNode);
        }
        else if (resourcePackageName.equals("power")){
            mySimPowerNode.appendChild(moduleNode);
        }
        else if (resourcePackageName.equals("waste")){
            mySimWasteNode.appendChild(moduleNode);
        }
        else if (resourcePackageName.equals("water")){
            mySimWaterNode.appendChild(moduleNode);
        }
    }
    
    private static String getCorbaName(BioModuleImpl pModule){
        String implName = pModule.getClass().getSimpleName();
        String corbaName = implName.substring(0, implName.indexOf("Impl"));
        return corbaName;
    }

    /**
     * @param operations
     * @param newConsumerElement
     */
    private void createFlowRateAttributes(SingleFlowRateControllable operations, Element newConsumerElement) {
        newConsumerElement.setAttribute("maxFlowRates", Float.toString(operations.getMaxFlowRate(0)));
        newConsumerElement.setAttribute("desiredFlowRates", Float.toString(operations.getDesiredFlowRate(0)));   
    }
}