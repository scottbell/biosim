package biosim.server.framework;

import java.io.*;
import java.net.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Reads BioSim configuration from XML file.
 *
 * @author Scott Bell
 */
public class BioInitializer extends DefaultHandler {
	/** Namespaces feature id (http://xml.org/sax/features/namespaces). */
	private static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";

	/** Namespace prefixes feature id (http://xml.org/sax/features/namespace-prefixes). */
	private static final String NAMESPACE_PREFIXES_FEATURE_ID = "http://xml.org/sax/features/namespace-prefixes";

	/** Validation feature id (http://xml.org/sax/features/validation). */
	private static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";

	/** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
	private static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";

	/** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
	private static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";

	/** Dynamic validation feature id (http://apache.org/xml/features/validation/dynamic). */
	private static final String DYNAMIC_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/dynamic";

	// default settings
	/** Default parser name. */
	private static final String DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";

	/** Default namespaces support (true). */
	private static final boolean DEFAULT_NAMESPACES = true;

	/** Default namespace prefixes (false). */
	private static final boolean DEFAULT_NAMESPACE_PREFIXES = false;

	/** Default validation support (false). */
	private static final boolean DEFAULT_VALIDATION = true;

	/** Default Schema validation support (false). */
	private static final boolean DEFAULT_SCHEMA_VALIDATION = true;

	/** Default Schema full checking support (false). */
	private static final boolean DEFAULT_SCHEMA_FULL_CHECKING = true;

	/** Default dynamic validation support (false). */
	private static final boolean DEFAULT_DYNAMIC_VALIDATION = false;
	
	private static XMLReader myParser = null;
	private static BioInitializer myInitializer = null;
	
	/** Default constructor. */
	private BioInitializer(){
		try {
			myParser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
			myParser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, DEFAULT_SCHEMA_VALIDATION);
			myParser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, DEFAULT_SCHEMA_FULL_CHECKING);
			myParser.setFeature(NAMESPACE_PREFIXES_FEATURE_ID, DEFAULT_NAMESPACE_PREFIXES);
			myParser.setFeature(VALIDATION_FEATURE_ID, DEFAULT_VALIDATION);
			myParser.setFeature(NAMESPACES_FEATURE_ID, DEFAULT_NAMESPACES);
			myParser.setFeature(DYNAMIC_VALIDATION_FEATURE_ID, DEFAULT_DYNAMIC_VALIDATION);
			myParser.setContentHandler(myInitializer);
			myParser.setErrorHandler(myInitializer);
		}
		catch (SAXException e) {
			System.err.println("warning: Parser does not support feature ("+NAMESPACES_FEATURE_ID+")");
		}
		catch (Exception e) {
			System.err.println("error: Unable to instantiate parser ("+DEFAULT_PARSER_NAME+")");	
		}
	} 

	/** Start element. */
	public void startElement(String uri, String local, String raw, Attributes attributes) throws SAXException {
		if (attributes != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				System.out.println(attributes.getLocalName(i)+" = "+attributes.getValue(i));
			}
		}

	}

	/** Warning. */
	public void warning(SAXParseException ex) throws SAXException {
		printError("Warning", ex);
	}

	/** Error. */
	public void error(SAXParseException ex) throws SAXException {
		printError("Error", ex);
	}
	/** Fatal error. */
	public void fatalError(SAXParseException ex) throws SAXException {
		printError("Fatal Error", ex);
	}

	/** Prints the error message. */
	protected void printError(String type, SAXParseException ex) {

		System.err.print("[");
		System.err.print(type);
		System.err.print("] ");
		if (ex == null)
			System.out.println("!!!");
		String systemId = ex.getSystemId();
		if (systemId != null) {
			int index = systemId.lastIndexOf('/');
			if (index != -1)
				systemId = systemId.substring(index + 1);
			System.err.print(systemId);
		}
		System.err.print(':');
		System.err.print(ex.getLineNumber());
		System.err.print(':');
		System.err.print(ex.getColumnNumber());
		System.err.print(": ");
		System.err.print(ex.getMessage());
		System.err.println();
		System.err.flush();
	}
	
	private static void parseFile(String fileToParse){
		if (myInitializer == null)
			myInitializer = new BioInitializer();
		try{
			myParser.parse(fileToParse);
		}
		catch (Exception e){
			System.err.println("error: Parse error occurred - "+e.getMessage());
			Exception se = e;
			if (e instanceof SAXException)
				se = ((SAXException)e).getException();
			if (se != null)
				se.printStackTrace(System.err);
			else
				e.printStackTrace(System.err);
		}
	}

	/** Main program entry point. */
	public static void main(String argv[]) {
		URL documentUrl = ClassLoader.getSystemClassLoader().getResource("biosim/server/framework/DefaultInitialization.xml");
		String documentString = documentUrl.toString();
		if (documentString.length() > 0) 
			BioInitializer.parseFile(documentString);
	}
} 
