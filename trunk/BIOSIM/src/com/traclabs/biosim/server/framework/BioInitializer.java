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
	protected static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";

	/** Namespace prefixes feature id (http://xml.org/sax/features/namespace-prefixes). */
	protected static final String NAMESPACE_PREFIXES_FEATURE_ID = "http://xml.org/sax/features/namespace-prefixes";

	/** Validation feature id (http://xml.org/sax/features/validation). */
	protected static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";

	/** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
	protected static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";

	/** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
	protected static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";

	/** Dynamic validation feature id (http://apache.org/xml/features/validation/dynamic). */
	protected static final String DYNAMIC_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/dynamic";

	// default settings

	/** Default parser name. */
	protected static final String DEFAULT_PARSER_NAME = "org.apache.xerces.parsers.SAXParser";

	/** Default repetition (1). */
	protected static final int DEFAULT_REPETITION = 1;

	/** Default namespaces support (true). */
	protected static final boolean DEFAULT_NAMESPACES = true;

	/** Default namespace prefixes (false). */
	protected static final boolean DEFAULT_NAMESPACE_PREFIXES = false;

	/** Default validation support (false). */
	protected static final boolean DEFAULT_VALIDATION = true;

	/** Default Schema validation support (false). */
	protected static final boolean DEFAULT_SCHEMA_VALIDATION = true;

	/** Default Schema full checking support (false). */
	protected static final boolean DEFAULT_SCHEMA_FULL_CHECKING = true;

	/** Default dynamic validation support (false). */
	protected static final boolean DEFAULT_DYNAMIC_VALIDATION = false;

	/** Default memory usage report (false). */
	protected static final boolean DEFAULT_MEMORY_USAGE = false;

	/** Default "tagginess" report (false). */
	protected static final boolean DEFAULT_TAGGINESS = false;

	//
	// Data
	//

	/** Number of elements. */
	protected long fElements;

	/** Number of attributes. */
	protected long fAttributes;

	/** Number of characters. */
	protected long fCharacters;

	/** Number of ignorable whitespace characters. */
	protected long fIgnorableWhitespace;

	/** Number of characters of tags. */
	protected long fTagCharacters;

	/** Number of other content characters for the "tagginess" calculation. */
	protected long fOtherCharacters;

	//
	// Constructors
	//

	/** Default constructor. */
	public BioInitializer() {
	} // <init>()

	//
	// Public methods
	//

	/** Prints the results. */
	public void printResults(PrintWriter out, String uri, long time,
	                         long memory, boolean tagginess,
	                         int repetition) {

		// filename.xml: 631 ms (4 elems, 0 attrs, 78 spaces, 0 chars)
		out.print(uri);
		out.print(": ");
		if (repetition == 1) {
			out.print(time);
		}
		else {
			out.print(time);
			out.print('/');
			out.print(repetition);
			out.print('=');
			out.print(time/repetition);
		}
		out.print(" ms");
		if (memory != Long.MIN_VALUE) {
			out.print(", ");
			out.print(memory);
			out.print(" bytes");
		}
		out.print(" (");
		out.print(fElements);
		out.print(" elems, ");
		out.print(fAttributes);
		out.print(" attrs, ");
		out.print(fIgnorableWhitespace);
		out.print(" spaces, ");
		out.print(fCharacters);
		out.print(" chars)");
		if (tagginess) {
			out.print(' ');
			long totalCharacters = fTagCharacters + fOtherCharacters
			                       + fCharacters + fIgnorableWhitespace;
			long tagValue = fTagCharacters * 100 / totalCharacters;
			out.print(tagValue);
			out.print("% tagginess");
		}
		out.println();
		out.flush();

	} // printResults(PrintWriter,String,long)

	//
	// ContentHandler methods
	//

	/** Start document. */
	public void startDocument() throws SAXException {

		fElements            = 0;
		fAttributes          = 0;
		fCharacters          = 0;
		fIgnorableWhitespace = 0;
		fTagCharacters       = 0;

	} // startDocument()

	/** Start element. */
	public void startElement(String uri, String local, String raw,
	                         Attributes attrs) throws SAXException {

		fElements++;
		System.out.println("raw is :"+raw);
		System.out.println("local is :"+local);
		System.out.println("uri is :"+uri);
		fTagCharacters++; // open angle bracket
		fTagCharacters += raw.length();
		if (attrs != null) {
			int attrCount = attrs.getLength();
			fAttributes += attrCount;
			for (int i = 0; i < attrCount; i++) {
				fTagCharacters++; // space
				fTagCharacters += attrs.getQName(i).length();
				fTagCharacters++; // '='
				fTagCharacters++; // open quote
				fOtherCharacters += attrs.getValue(i).length();
				fTagCharacters++; // close quote
			}
		}
		fTagCharacters++; // close angle bracket

	} // startElement(String,String,StringAttributes)

	/** Characters. */
	public void characters(char ch[], int start, int length)
	throws SAXException {
		fCharacters += length;

	} // characters(char[],int,int);

	/** Ignorable whitespace. */
	public void ignorableWhitespace(char ch[], int start, int length)
	throws SAXException {

		fIgnorableWhitespace += length;

	} // ignorableWhitespace(char[],int,int);

	/** Processing instruction. */
	public void processingInstruction(String target, String data)
	throws SAXException {
		fTagCharacters += 2; // "<?"
		fTagCharacters += target.length();
		if (data != null && data.length() > 0) {
			fTagCharacters++; // space
			fOtherCharacters += data.length();
		}
		fTagCharacters += 2; // "?>"
	} // processingInstruction(String,String)

	//
	// ErrorHandler methods
	//

	/** Warning. */
	public void warning(SAXParseException ex) throws SAXException {
		printError("Warning", ex);
	} // warning(SAXParseException)

	/** Error. */
	public void error(SAXParseException ex) throws SAXException {
		printError("Error", ex);
	} // error(SAXParseException)

	/** Fatal error. */
	public void fatalError(SAXParseException ex) throws SAXException {
		printError("Fatal Error", ex);
		//throw ex;
	} // fatalError(SAXParseException)

	//
	// Protected methods
	//

	/** Prints the error message. */
	protected void printError(String type, SAXParseException ex) {

		System.err.print("[");
		System.err.print(type);
		System.err.print("] ");
		if (ex== null) {
			System.out.println("!!!");
		}
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

	/** Main program entry point. */
	public static void main(String argv[]) {
		URL docUrl = ClassLoader.getSystemClassLoader().getResource("biosim/server/framework/DefaultInitialization.xml");
		argv = new String[1];
		argv[0] = docUrl.toString();
		// is there anything to do?
		if (argv.length == 0) {
			printUsage();
			System.exit(1);
		}

		// variables
		BioInitializer counter = new BioInitializer();
		PrintWriter out = new PrintWriter(System.out);
		XMLReader parser = null;
		int repetition = DEFAULT_REPETITION;
		boolean namespaces = DEFAULT_NAMESPACES;
		boolean namespacePrefixes = DEFAULT_NAMESPACE_PREFIXES;
		boolean validation = DEFAULT_VALIDATION;
		boolean schemaValidation = DEFAULT_SCHEMA_VALIDATION;
		boolean schemaFullChecking = DEFAULT_SCHEMA_FULL_CHECKING;
		boolean dynamicValidation = DEFAULT_DYNAMIC_VALIDATION;
		boolean memoryUsage = DEFAULT_MEMORY_USAGE;
		boolean tagginess = DEFAULT_TAGGINESS;

		// process arguments
		for (int i = 0; i < argv.length; i++) {
			String arg = argv[i];
			if (arg.startsWith("-")) {
				String option = arg.substring(1);
				if (option.equals("p")) {
					// get parser name
					if (++i == argv.length) {
						System.err.println("error: Missing argument to -p option.");
						continue;
					}
					String parserName = argv[i];

					// create parser
					try {
						parser = XMLReaderFactory.createXMLReader(parserName);
					}
					catch (Exception e) {
						try {
							Parser sax1Parser = ParserFactory.makeParser(parserName);
							parser = new ParserAdapter(sax1Parser);
							System.err.println("warning: Features and properties not supported on SAX1 parsers.");
						}
						catch (Exception ex) {
							parser = null;
							System.err.println("error: Unable to instantiate parser ("+parserName+")");
						}
					}
					continue;
				}
				if (option.equals("x")) {
					if (++i == argv.length) {
						System.err.println("error: Missing argument to -x option.");
						continue;
					}
					String number = argv[i];
					try {
						int value = Integer.parseInt(number);
						if (value < 1) {
							System.err.println("error: Repetition must be at least 1.");
							continue;
						}
						repetition = value;
					}
					catch (NumberFormatException e) {
						System.err.println("error: invalid number ("+number+").");
					}
					continue;
				}
				if (option.equalsIgnoreCase("n")) {
					namespaces = option.equals("n");
					continue;
				}
				if (option.equalsIgnoreCase("np")) {
					namespacePrefixes = option.equals("np");
					continue;
				}
				if (option.equalsIgnoreCase("v")) {
					validation = option.equals("v");
					continue;
				}
				if (option.equalsIgnoreCase("s")) {
					schemaValidation = option.equals("s");
					continue;
				}
				if (option.equalsIgnoreCase("f")) {
					schemaFullChecking = option.equals("f");
					continue;
				}
				if (option.equalsIgnoreCase("dv")) {
					dynamicValidation = option.equals("dv");
					continue;
				}
				if (option.equalsIgnoreCase("m")) {
					memoryUsage = option.equals("m");
					continue;
				}
				if (option.equalsIgnoreCase("t")) {
					tagginess = option.equals("t");
					continue;
				}
				if (option.equals("-rem")) {
					if (++i == argv.length) {
						System.err.println("error: Missing argument to -# option.");
						continue;
					}
					System.out.print("# ");
					System.out.println(argv[i]);
					continue;
				}
				if (option.equals("h")) {
					printUsage();
					continue;
				}
				System.err.println("error: unknown option ("+option+").");
				continue;
			}

			// use default parser?
			if (parser == null) {

				// create parser
				try {
					parser = XMLReaderFactory.createXMLReader(DEFAULT_PARSER_NAME);
				}
				catch (Exception e) {
					System.err.println("error: Unable to instantiate parser ("+DEFAULT_PARSER_NAME+")");
					continue;
				}
			}

			// set parser features
			try {
				parser.setFeature(NAMESPACES_FEATURE_ID, namespaces);
			}
			catch (SAXException e) {
				System.err.println("warning: Parser does not support feature ("+NAMESPACES_FEATURE_ID+")");
			}
			try {
				parser.setFeature(NAMESPACE_PREFIXES_FEATURE_ID, namespacePrefixes);
			}
			catch (SAXException e) {
				System.err.println("warning: Parser does not support feature ("+NAMESPACE_PREFIXES_FEATURE_ID+")");
			}
			try {
				parser.setFeature(VALIDATION_FEATURE_ID, validation);
			}
			catch (SAXException e) {
				System.err.println("warning: Parser does not support feature ("+VALIDATION_FEATURE_ID+")");
			}
			try {
				parser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, schemaValidation);
			}
			catch (SAXNotRecognizedException e) {
				System.err.println("warning: Parser does not support feature ("+SCHEMA_VALIDATION_FEATURE_ID+")");

			}
			catch (SAXNotSupportedException e) {
				System.err.println("warning: Parser does not support feature ("+SCHEMA_VALIDATION_FEATURE_ID+")");
			}
			try {
				parser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, schemaFullChecking);
			}
			catch (SAXNotRecognizedException e) {
				System.err.println("warning: Parser does not support feature ("+SCHEMA_FULL_CHECKING_FEATURE_ID+")");

			}
			catch (SAXNotSupportedException e) {
				System.err.println("warning: Parser does not support feature ("+SCHEMA_FULL_CHECKING_FEATURE_ID+")");
			}
			try {
				parser.setFeature(DYNAMIC_VALIDATION_FEATURE_ID, dynamicValidation);
			}
			catch (SAXNotRecognizedException e) {
				System.err.println("warning: Parser does not support feature ("+DYNAMIC_VALIDATION_FEATURE_ID+")");

			}
			catch (SAXNotSupportedException e) {
				System.err.println("warning: Parser does not support feature ("+DYNAMIC_VALIDATION_FEATURE_ID+")");
			}

			// parse file
			parser.setContentHandler(counter);
			parser.setErrorHandler(counter);
			try {
				long timeBefore = System.currentTimeMillis();
				long memoryBefore = Runtime.getRuntime().freeMemory();
				for (int j = 0; j < repetition; j++) {
					parser.parse(arg);
				}
				long memoryAfter = Runtime.getRuntime().freeMemory();
				long timeAfter = System.currentTimeMillis();

				long time = timeAfter - timeBefore;
				long memory = memoryUsage
				              ? memoryBefore - memoryAfter : Long.MIN_VALUE;
				counter.printResults(out, arg, time, memory, tagginess,
				                     repetition);
			}
			catch (SAXParseException e) {
				// ignore
			}
			catch (Exception e) {
				System.err.println("error: Parse error occurred - "+e.getMessage());
				Exception se = e;
				if (e instanceof SAXException) {
					se = ((SAXException)e).getException();
				}
				if (se != null)
					se.printStackTrace(System.err);
				else
					e.printStackTrace(System.err);

			}
		}

	} // main(String[])

	//
	// Private static methods
	//

	/** Prints the usage. */
	private static void printUsage() {

		System.err.println("usage: java biosim.server.framework.BioInitializer (options) uri ...");
		System.err.println();

		System.err.println("options:");
		System.err.println("  -p name     Select parser by name.");
		System.err.println("  -x number   Select number of repetitions.");
		System.err.println("  -n  | -N    Turn on/off namespace processing.");
		System.err.println("  -np | -NP   Turn on/off namespace prefixes.");
		System.err.println("              NOTE: Requires use of -n.");
		System.err.println("  -v  | -V    Turn on/off validation.");
		System.err.println("  -s  | -S    Turn on/off Schema validation support.");
		System.err.println("              NOTE: Not supported by all parsers.");
		System.err.println("  -f  | -F    Turn on/off Schema full checking.");
		System.err.println("              NOTE: Requires use of -s and not supported by all parsers.");
		System.err.println("  -dv | -DV   Turn on/off dynamic validation.");
		System.err.println("              NOTE: Requires use of -v and not supported by all parsers.");
		System.err.println("  -m  | -M    Turn on/off memory usage report");
		System.err.println("  -t  | -T    Turn on/off \"tagginess\" report.");
		System.err.println("  --rem text  Output user defined comment before next parse.");
		System.err.println("  -h          This help screen.");

		System.err.println();
		System.err.println("defaults:");
		System.err.println("  Parser:     "+DEFAULT_PARSER_NAME);
		System.err.println("  Repetition: "+DEFAULT_REPETITION);
		System.err.print("  Namespaces: ");
		System.err.println(DEFAULT_NAMESPACES ? "on" : "off");
		System.err.print("  Prefixes:   ");
		System.err.println(DEFAULT_NAMESPACE_PREFIXES ? "on" : "off");
		System.err.print("  Validation: ");
		System.err.println(DEFAULT_VALIDATION ? "on" : "off");
		System.err.print("  Schema:     ");
		System.err.println(DEFAULT_SCHEMA_VALIDATION ? "on" : "off");
		System.err.print("  Schema full checking:     ");
		System.err.println(DEFAULT_SCHEMA_FULL_CHECKING ? "on" : "off");
		System.err.print("  Dynamic:    ");
		System.err.println(DEFAULT_DYNAMIC_VALIDATION ? "on" : "off");
		System.err.print("  Memory:     ");
		System.err.println(DEFAULT_MEMORY_USAGE ? "on" : "off");
		System.err.print("  Tagginess:  ");
		System.err.println(DEFAULT_TAGGINESS ? "on" : "off");

		System.err.println();
		System.err.println("notes:");
		System.err.println("  The speed and memory results from this program should NOT be used as the");
		System.err.println("  basis of parser performance comparison! Real analytical methods should be");
		System.err.println("  used. For better results, perform multiple document parses within the same");
		System.err.println("  virtual machine to remove class loading from parse time and memory usage.");
		System.err.println();
		System.err.println("  The \"tagginess\" measurement gives a rough estimate of the percentage of");
		System.err.println("  markup versus content in the XML document. The percent tagginess of a ");
		System.err.println("  document is equal to the minimum amount of tag characters required for ");
		System.err.println("  elements, attributes, and processing instructions divided by the total");
		System.err.println("  amount of characters (characters, ignorable whitespace, and tag characters)");
		System.err.println("  in the document.");
		System.err.println();
		System.err.println("  Not all features are supported by different parsers.");

	} // printUsage()

} // class Counter
