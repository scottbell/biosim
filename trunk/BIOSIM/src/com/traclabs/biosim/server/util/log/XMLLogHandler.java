package biosim.server.util;

import biosim.idl.util.*;
import java.io.*;
// SAX classes.
import org.xml.sax.*;
import org.xml.sax.helpers.*;
//JAXP 1.1
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.sax.*;

/**
 * The XMLLogHandler takes Logs and outputs them to an xml file
 * @author    Scott Bell
 */

public class XMLLogHandler extends LogHandler{
	private File myOutputFile;
	private static String DEFAULT_FILENAME = "biosim-output.xml";
	private TransformerHandler myHandler;

	public XMLLogHandler(){
		myOutputFile = new File(DEFAULT_FILENAME);
	}

	public XMLLogHandler(File pOutputFile){
		myOutputFile = pOutputFile;
	}

	public void writeLog(LogNode logToWrite){
		try{
			initializeXML();
			printXMLTree(logToWrite, 0);
		}
		catch(Exception e){
			System.err.println("Had problems writing XML log!");
			e.printStackTrace();
		}
	}

	private void initializeXML() throws IOException, TransformerConfigurationException{
		// PrintWriter from a Servlet
		FileWriter myFileWriter = new FileWriter(myOutputFile);
		StreamResult streamResult = new StreamResult(myFileWriter);
		SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		myHandler = tf.newTransformerHandler();
		Transformer serializer = myHandler.getTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
		serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"biosim.dtd");
		serializer.setOutputProperty(OutputKeys.INDENT,"yes");
		myHandler.setResult(streamResult);
	}

	private void printXMLTree(LogNode currentNode, int currentDepth) throws SAXException{
		myHandler.startDocument();
		AttributesImpl atts = new AttributesImpl();
		// BIOSIM tag.
		myHandler.startElement("","","biosim",atts);
		myHandler.startElement("","","USER",atts);
		//myHandler.characters(desc[i].toCharArray(),0,desc[i].length());
		myHandler.endElement("","","USER");
		myHandler.endDocument();
	}
}
