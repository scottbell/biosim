package com.traclabs.biosim.server.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.omg.PortableServer.Servant;

import com.traclabs.biosim.util.OrbUtils;

/**
 * The Generic Server. Provides basic functionality for BioSim servers
 * 
 * @author Scott Bell
 */

public class GenericServer {
	private List<ActionListener> readyListeners;

	protected Logger myLogger;

	public GenericServer() {
		OrbUtils.initializeLog();
		myLogger = Logger.getLogger(this.getClass());
	}

	/**
	 * Registers this server with the CORBA naming service
	 * 
	 * @param pPOA
	 *            the object to register
	 * @param pServerName
	 *            the name that will be associated with this server in the
	 *            naming service
	 * @param pID
	 *            the subcontext which to bind the name
	 */
	public static void registerServer(Servant pPOA, String pServerName, int pID) {
		// Try to register the ID name context
		try {
			// bind the Object Reference in Naming
			OrbUtils.getNamingContext(pID).rebind(
					OrbUtils.getNamingContext(pID).to_name(pServerName),
					OrbUtils.poaToCorbaObj(pPOA));
		} catch (org.omg.CORBA.UserException e) {
			Logger
					.getLogger(GenericServer.class)
					.error(
							pServerName
									+ " had problems registering with nameservice, trying again..");
			e.printStackTrace();
			OrbUtils.sleepAwhile();
			registerServer(pPOA, pServerName, pID);
		}
	}

	/**
	 * Registers this server with the CORBA naming service and starts the server
	 * 
	 * @param pPOA
	 *            the object to register
	 * @param pServerName
	 *            the name that will be associated with this server in the
	 *            naming service
	 * @param pID
	 *            the id of the server
	 */
	public void registerServerAndRun(Servant pPOA, String pServerName, int pID) {
		registerServer(pPOA, pServerName, pID);
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
	 * Starts the server by calling ORB.run()
	 * 
	 * @param serverName
	 *            the name associated with this server (for debug purposes only)
	 */
	public void runServer(String serverName) {
		try {
			notfiyListeners();
			myLogger.info(serverName + " ready and waiting");
			// wait for invocations from clients
			OrbUtils.getORB().run();
		} catch (Exception e) {
			myLogger.error(serverName + " ERROR: " + e);
			e.printStackTrace();
		}
		myLogger.error(serverName + " exiting");
	}
}
