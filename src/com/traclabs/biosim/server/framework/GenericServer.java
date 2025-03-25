package com.traclabs.biosim.server.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.traclabs.biosim.util.RestUtils;

/**
 * The Generic Server. Provides basic functionality for BioSim servers
 * 
 * @author Scott Bell
 */

public class GenericServer {
	private List<ActionListener> readyListeners;

	protected Logger myLogger;

	public GenericServer() {
		myLogger = Logger.getLogger(this.getClass());
	}

	/**
	 * Registers this server with the REST API
	 * 
	 * @param pModule
	 *            the object to register
	 * @param pServerName
	 *            the name that will be associated with this server
	 * @param pID
	 *            the ID of the module
	 */
	public static void registerServer(Object pModule, String pServerName, int pID) {
		// Try to register the module
		try {
			// Register the module with RestUtils
			RestUtils.registerModule(pID, pServerName, pModule);
		} catch (Exception e) {
			Logger
					.getLogger(GenericServer.class)
					.error(
							pServerName
									+ " had problems registering with REST API, trying again..");
			e.printStackTrace();
			RestUtils.sleepAwhile();
			registerServer(pModule, pServerName, pID);
		}
	}

	/**
	 * Registers this server with the REST API and starts the server
	 * 
	 * @param pModule
	 *            the object to register
	 * @param pServerName
	 *            the name that will be associated with this server
	 * @param pID
	 *            the id of the server
	 */
	public void registerServerAndRun(Object pModule, String pServerName, int pID) {
		registerServer(pModule, pServerName, pID);
		runServer(pServerName);
	}

	public void addReadyListener(ActionListener newListener) {
		if (readyListeners == null)
			readyListeners = new Vector<ActionListener>();
		readyListeners.add(newListener);
	}

	private void notfiyListeners() {
		if (readyListeners == null)
			return;
		for (Iterator iter = readyListeners.iterator(); iter.hasNext();) {
			ActionListener currentListener = (ActionListener) (iter.next());
			currentListener.actionPerformed(new ActionEvent(this, 0,
					" server ready"));
		}
	}

	/**
	 * Starts the server
	 * 
	 * @param serverName
	 *            the name associated with this server (for debug purposes only)
	 */
	public void runServer(String serverName) {
		try {
			notfiyListeners();
			myLogger.info(serverName + " ready and waiting");
			
			// Keep the server running
			// This is a blocking call in the original code with OrbUtils.getORB().run()
			// In the REST version, we'll just keep the thread alive
			Thread.currentThread().join();
		} catch (Exception e) {
			myLogger.error(serverName + " ERROR: " + e);
			e.printStackTrace();
		}
		myLogger.error(serverName + " exiting");
	}
}
