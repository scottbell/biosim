/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * LinkedHandler.java
 *
 * Created on February 6, 2003, 10:59 AM
 */

package com.traclabs.biosim.client.simulation.power.schematic.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * LinkedHandler allows the control to be passed to different SAX handlers as an
 * XML file is be parsed.
 * 
 * @author kkusy
 */
public class LinkedHandler extends DefaultHandler {
    XMLReader _reader;

    LinkedHandler _previous;

    /** Creates a new instance of LinkedHandler */
    public LinkedHandler() {
    }

    public LinkedHandler(XMLReader reader) {
        _reader = reader;
    }

    public void setReader(XMLReader reader) {
        _reader = reader;
    }

    public XMLReader getReader() {
        return _reader;
    }

    public void setPrevious(LinkedHandler previous) {
        _previous = previous;
    }

    public LinkedHandler getPrevious() {
        return _previous;
    }

    public void gotoPrevious() {
        exit();
        if (_reader != null && _previous != null) {
            _reader.setContentHandler(_previous);
            _reader.setErrorHandler(_previous);
        }
    }

    // Transfers control to the next handler.
    public void gotoNext(LinkedHandler next) {
        if (_reader != null) {
            next.setPrevious(this);
            next.setReader(_reader);
            _reader.setContentHandler(next);
            _reader.setErrorHandler(next);
            next.enter();
        }
    }

    // Transfers control to the next handler calling calling its startElement
    // method.
    public void gotoNext(LinkedHandler next, String uri, String local,
            String qName, Attributes attrs) throws SAXException {
        gotoNext(next);
        next.startElement(uri, local, qName, attrs);
    }

    protected void enter() {
    }

    protected void exit() {
    }

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

    /** Prints the error message. */
    protected void printError(String type, SAXParseException ex) {

        System.err.print("[");
        System.err.print(type);
        System.err.print("] ");
        if (ex == null) {
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

    } // printError(String,SAXParseException)
}