package biosim.server.util.log;

import biosim.idl.util.log.*;
import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*;

/**
 * The XMLLogHandler takes Logs and outputs them to an xml file
 * @author    Scott Bell
 */

public class XMLLogHandler implements LogHandler{
	//The File handle of where we're going to put the XML output
	private File myOutputFile;
	//The default name of the XML file to output
	private static String DEFAULT_FILENAME = "biosim-log.xml";
	//Used to transform SAX into a file output stream
	private TransformerHandler myHandler;
	//Empty attributes used to tack onto LogNodes (none of them have attributes)
	private AttributesImpl emptyAtts;
	//Whether XML file has been created, tags initialized, etc
	private boolean initialized = false;
	//The writer that takes the XML stream and outputs it to a file
	private FileWriter myFileWriter;

	/**
	* The default constructor creates a file in $BIOSIM_HOME/generated/biosim-log.xml
	*/
	public XMLLogHandler(){
		String biosimPath = new String();biosimPath = System.getProperty("BIOSIM_HOME");
		if (biosimPath != null)
			biosimPath = biosimPath + "/generated/";
		if (biosimPath != null)
			myOutputFile = new File(biosimPath + DEFAULT_FILENAME);
		else
			myOutputFile = new File(DEFAULT_FILENAME);
	}
	
	/**
	* This constructor creates the xml log file in the file specified
	* @param pOutputFile where the xml will outputted to
	*/
	public XMLLogHandler(File pOutputFile){
		myOutputFile = pOutputFile;
	}
	
	/**
	* Initializes the XML writing process (if not done already) and prints the LogNode to XML
	* @param logToWrite the LogNode to write
	*/
	public void writeLog(LogNode logToWrite){
		try{
			if (!initialized)
				initializeXML();
			printXMLTree(logToWrite);
		}
		catch(Exception e){
			System.err.println("Had problems writing XML log!");
			e.printStackTrace();
		}
	}
	
	/**
	* Ends the root tags of the XML file and flushes the output
	*/
	public void endLog(){
		if (!initialized)
			return;
		try{
			endXML();
			initialized = false;
		}
		catch (SAXException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	* Initializes the XML output process.  Sets the encoding methods, begins root tags, opens file, etc.
	*/
	private void initializeXML() throws IOException, TransformerConfigurationException, SAXException{
		// PrintWriter from a Servlet
		emptyAtts = new AttributesImpl();
		myFileWriter = new FileWriter(myOutputFile);
		StreamResult streamResult = new StreamResult(myFileWriter);
		SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		myHandler = tf.newTransformerHandler();
		Transformer serializer = myHandler.getTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
		serializer.setOutputProperty(OutputKeys.METHOD,"xml");
		serializer.setOutputProperty(OutputKeys.INDENT,"yes");
		myHandler.setResult(streamResult);
		myHandler.startDocument();
		myHandler.startElement("","","biosim",emptyAtts);
		initialized = true;
	}
	
	/**
	* Ends root tag and flushs output
	*/
	private void endXML() throws SAXException, IOException{
		myHandler.endElement("","","biosim");
		myHandler.endDocument();
		myFileWriter.flush();
	}
	
	
	public LogHandlerType getType(){
		return LogHandlerType.XML;
	}
	
	/**
	* Recursively prints out the LogNode.
	* @param currentNode The current LogNode being printed
	*/
	private void printXMLTree(LogNode currentNode) throws SAXException{
		if (currentNode == null){
			return;  // There is nothing to print in an empty tree.
		}
		String tagName = currentNode.getValue();
		if (currentNode.getChildren().length < 1){
			myHandler.characters(tagName.toCharArray(),0,tagName.length());
		}
		else{
			if (tagName.startsWith("tick")){
				StringTokenizer st = new StringTokenizer(tagName);
				String tickTagName = st.nextToken();
				AttributesImpl tickAtts = new AttributesImpl();
				tickAtts.addAttribute("","","number","CDATA", st.nextToken());
				myHandler.startElement("","",tickTagName,tickAtts);
				LogNode[] childrenNodes = currentNode.getChildren();
				for (int i = 0; i < childrenNodes.length; i++)
					printXMLTree(childrenNodes[i]);
				myHandler.endElement("","",tickTagName);
			}
			else{
				myHandler.startElement("","",tagName,emptyAtts);
				LogNode[] childrenNodes = currentNode.getChildren();
				for (int i = 0; i < childrenNodes.length; i++)
					printXMLTree(childrenNodes[i]);
				myHandler.endElement("","",tagName);
			}
		}
	}
}
