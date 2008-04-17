package com.traclabs.biosim.server.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tietronix.bionet.configuration.BionetConfig;
import com.tietronix.bionet.hab.JBionetHab;
import com.tietronix.bionet.util.Node;
import com.traclabs.biosim.server.framework.BioModuleImpl;

public class BionetUtils extends JBionetHab{
	private final static String CONFIG_FILE = "config.bionet";
	private final static String NODE_ID = "com.traclabs.biosim";
	private final static Node myNode = new Node(BionetConfig.HAB_TYPE, BionetConfig.HAB_ID, NODE_ID);
	private final static Map<String, BioModuleImpl> myCallbackMap = new HashMap<String, BioModuleImpl>();
	private final static Logger myLogger = Logger.getLogger(BionetUtils.class);
	
	protected BionetUtils() {
		super(CONFIG_FILE);
	}
	
	/**
	 * Callback for actuator commands.
	 * 
	 * @param nodeId
	 *            Id of Node that owns the updated actuator Respource
	 * @param resourceId
	 *            Id of updated actuator Resource
	 * @param value
	 *            New Value of updated actuator Resource
	 */
	public static void setResourceCallBack(final String nodeId, final String resourceId, final String value) {
		myLogger.info("Receive callback: " + nodeId + ":" + resourceId + " = " + value);
		BioModuleImpl module = myCallbackMap.get(nodeId);
		module.bionetCallBack(resourceId, value);
	}
	
}
