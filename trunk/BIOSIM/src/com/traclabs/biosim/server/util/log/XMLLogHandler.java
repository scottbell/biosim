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
	private File myOutputFile;
	private static String DEFAULT_FILENAME = "biosim-log.xml";
	private TransformerHandler myHandler;
	private AttributesImpl emptyAtts;
	private boolean initialized = false;
	private FileWriter myFileWriter;

	public XMLLogHandler(){
		String biosimPath = new String();biosimPath = System.getProperty("BIOSIM_HOME");
		if (biosimPath != null)
			biosimPath = biosimPath + "/generated/";
		if (biosimPath != null)
			myOutputFile = new File(biosimPath + DEFAULT_FILENAME);
		else
			myOutputFile = new File(DEFAULT_FILENAME);
	}

	public XMLLogHandler(File pOutputFile){
		myOutputFile = pOutputFile;
	}

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

	private void endXML() throws SAXException, IOException{
		myHandler.endElement("","","biosim");
		myHandler.endDocument();
		myFileWriter.flush();
	}

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
