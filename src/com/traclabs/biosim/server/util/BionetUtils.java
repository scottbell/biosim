package com.traclabs.biosim.server.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tietronix.bionet.configuration.BionetConfig;
import com.tietronix.bionet.hab.JBionetHab;
import com.tietronix.bionet.util.Node;
import com.tietronix.bionet.util.Resource;
import com.tietronix.bionet.util.Resource.resourceFlavor;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.sensor.framework.GenericSensorImpl;

public class BionetUtils extends JBionetHab{
	public final static String RESOURCE_ID = "value";
	private final static String CONFIG_FILE = "config.bionet";
	private final static Map<String, BioModuleImpl> myCallbackMap = new HashMap<String, BioModuleImpl>();
	private final static Logger myLogger = Logger.getLogger(BionetUtils.class);
	private static BionetUtils mySingleton;
	
	private BionetUtils() {
		super(CONFIG_FILE);
	}
	
	public static BionetUtils getBionetUtils(){
		if (mySingleton == null){
			initialize();
		}
		return mySingleton;
	}
	
	private static void initialize(){
		mySingleton = new BionetUtils();
		// Register the set resource callback function
		mySingleton.registerCallbackSetResource("setResourceCallBack");

		// Set the NAG hostname
		mySingleton.setNagHostname(BionetConfig.NAG_HOST_NAME);

		// Set the HAB type
		int result = mySingleton.setType(BionetConfig.HAB_TYPE);
		if (result == 0) {
			System.out.println("Set HAB type successful");
		} else {
			System.out.println("Set HAB type error");
		}

		// Set the HAB id
		result = mySingleton.setId(BionetConfig.HAB_ID);
		if (result != -1) {
			System.out.println("Set HAB id successful");
		} else {
			System.out.println("Set HAB id error");
		}

		// connect to the NAG
		result = mySingleton.connectToNag();
		if (result == 0) {
			System.out.println("NAG connection successful");
		} else {
			System.out.println("NAG connection error " + result);
		}

		// spawns polling thread and moves to next statement
		mySingleton.poll();

		// create local node
		final String nodeId = "Node1";
		final Node node = new Node(BionetConfig.HAB_TYPE, BionetConfig.HAB_ID,
				nodeId);
	}
	
	/**
	 * Start the polling thread to handle NAG messages.
	 */
	private void poll() {
		this.startPolling(this);
	}
	
	private void reportNode(Node node){
		int result = mySingleton.reportNewNode(node);
		if (result == 0) {
			myLogger.info("Reported new node named "+node.getNodeId());
		} else {
			myLogger.error("Failed to report new node named "+node.getNodeId());
		}
	}
	
	public Node registerSensor(GenericSensorImpl sensor){
		Node node = registerModule(sensor, Resource.resourceFlavor.BIONET_RESOURCE_FLAVOR_SENSOR);
		return node;
	}
	
	public Node registerActuator(GenericActuatorImpl actuator){
		Node node = registerModule(actuator, Resource.resourceFlavor.BIONET_RESOURCE_FLAVOR_ACTUATOR);
		myCallbackMap.put(node.getNodeId(), actuator);
		return node;
		
	}
	
	private Node registerModule(BioModuleImpl module, resourceFlavor resourceFlavor) {
		if (mySingleton == null)
			initialize();
		// create local node
		final String nodeId = module.getModuleName();
		final Node node = new Node(BionetConfig.HAB_TYPE, BionetConfig.HAB_ID,
				nodeId);

		// create actuator resource and add to local node
		final Resource newResource = new Resource(node.getHabType(), node
				.getHabId(), node.getNodeId(),
				Resource.resourceDataType.BIONET_RESOURCE_DATA_TYPE_FLOAT,
				resourceFlavor,
				RESOURCE_ID);
		newResource.setResourceValue(1);
		node.addResource(newResource);
		reportNode(node);
		return node;
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
		if (resourceId.equals(RESOURCE_ID)){
			BioModuleImpl module = myCallbackMap.get(nodeId);
			module.bionetCallBack(value);
		}
		else
			myLogger.warn("Received the wrong resource ID. Expecting: " + RESOURCE_ID + " and got: "+ resourceId);
	}
	
}
