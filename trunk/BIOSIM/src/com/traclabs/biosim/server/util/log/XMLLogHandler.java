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
	
	public XMLLogHandler(){
		myOutputFile = new File(DEFAULT_FILENAME);
	}
	
	public XMLLogHandler(File pOutputFile){
		myOutputFile = pOutputFile;
	}

	public void writeLog(LogNode logToWrite){
		initializeXML();
		printTree(logToWrite, 0);
	}

	private void initializeXML(){
		try{
			// PrintWriter from a Servlet
			FileWriter myFileWriter = new FileWriter(myOutputFile);
			StreamResult streamResult = new StreamResult(myFileWriter);
			SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			TransformerHandler hd = tf.newTransformerHandler();
			Transformer serializer = hd.getTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
			serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"biosim.dtd");
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			hd.setResult(streamResult);
			hd.startDocument();
			AttributesImpl atts = new AttributesImpl();
			// BIOSIM tag.
			hd.startElement("","","biosim",atts);
			// USER tags.
			String[] id = {"PWD122","MX787","A4Q45"};
			String[] type = {"customer","manager","employee"};
			String[] desc = {"Tim@Home","Jack&Moud","John D'oé"};
			for (int i=0;i<id.length;i++)
			{
				atts.clear();
				atts.addAttribute("","","ID","CDATA",id[i]);
				atts.addAttribute("","","TYPE","CDATA",type[i]);
				hd.startElement("","","USER",atts);
				hd.characters(desc[i].toCharArray(),0,desc[i].length());
				hd.endElement("","","USER");
			}
			hd.endElement("","","USERS");
			hd.endDocument();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}


	private void printTree(LogNode currentNode, int currentDepth) {
		/*
		if (currentNode == null){
			return;  // There is nothing to print in an empty tree.
	}
		for (int i = 0; i < currentDepth; i ++){
			System.out.print("\t");
	}
		System.out.println(currentNode.getValue());
		LogNode[] childrenNodes =currentNode.getChildren();
		for (int i = 0; i < childrenNodes.length; i++){
			printTree(childrenNodes[i], currentDepth + 1);
	}
		*/
	}
}
