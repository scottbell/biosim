package com.traclabs.biosim.editor.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.traclabs.biosim.editor.base.BiosimEditor;
import com.traclabs.biosim.editor.base.EditorDocument;

/**
 * Writes a Editor Document to a file.
 * 
 * @author scott
 */
public class EditorWriter {
    private static EditorWriter mySingleton;
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
        public EditorWriter(){
                String biosimPath = new String();
                biosimPath = System.getProperty("BIOSIM_HOME");
                if (biosimPath != null)
                        biosimPath = biosimPath + "/tmp/";
                if (biosimPath != null)
                        myOutputFile = new File(biosimPath + DEFAULT_FILENAME);
                else
                        myOutputFile = new File(DEFAULT_FILENAME);
        }
        
        /**
        * This constructor creates the xml log file in the file specified
        * @param pOutputFile where the xml will outputted to
        */
        public EditorWriter(File pOutputFile){
                myOutputFile = pOutputFile;
        }
        
        /**
        * Initializes the XML writing process (if not done already) and prints the LogNode to XML
        * @param logToWrite the LogNode to write
        */
        public void writeLog(){
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
        
        /**
        * Recursively prints out the LogNode.
        * @param currentNode The current LogNode being printed
        */
        private void printXMLTree() throws SAXException{
                
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
        public void saveDocument(File file, EditorDocument document) {
            // TODO Auto-generated method stub
            
        }

        /**
         * @param file
         * @param editor
         */
        public void copySelections(File file, BiosimEditor editor) {
            // TODO Auto-generated method stub
            
        }
}