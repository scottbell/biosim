package biosim.server.framework;

import java.net.*;
import java.util.*;
import org.w3c.dom.*;
import org.apache.xerces.parsers.*;
import org.xml.sax.*;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
import biosim.server.simulation.air.*;
import biosim.server.simulation.crew.*;
import biosim.server.simulation.food.*;
import biosim.server.simulation.water.*;
import biosim.server.simulation.power.*;
import biosim.server.simulation.environment.*;
import biosim.server.simulation.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.water.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.framework.*;
import biosim.server.sensor.air.*;
import biosim.server.sensor.food.*;
import biosim.server.sensor.water.*;
import biosim.server.sensor.power.*;
import biosim.server.sensor.crew.*;
import biosim.server.sensor.environment.*;
import biosim.server.sensor.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.water.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.crew.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.framework.*;
import biosim.server.actuator.air.*;
import biosim.server.actuator.food.*;
import biosim.server.actuator.water.*;
import biosim.server.actuator.power.*;
import biosim.server.actuator.environment.*;
import biosim.server.actuator.framework.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import biosim.server.util.log.*;

/**
 * Reads BioSim configuration from XML file.
 *
 * @author Scott Bell
 */
public class BioInitializer{
	/** Namespaces feature id (http://xml.org/sax/features/moduleNamespaces). */
	private static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";
	/** Validation feature id (http://xml.org/sax/features/validation). */
	private static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";
	/** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
	private static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";
	/** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
	private static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";
	// default settings
	/** Default moduleNamespaces support (true). */
	private static final boolean DEFAULT_NAMESPACES = true;
	/** Default validation support (false). */
	private static final boolean DEFAULT_VALIDATION = true;
	/** Default Schema validation support (false). */
	private static final boolean DEFAULT_SCHEMA_VALIDATION = true;
	/** Default Schema full checking support (false). */
	private static final boolean DEFAULT_SCHEMA_FULL_CHECKING = true;

	private DOMParser myParser = null;
	private int myID = 0;
	private List myModules;
	private List mySimModules;
	private List mySensors;
	private List myActuators;

	/** Default constructor. */
	public BioInitializer(int pID){
		myID = pID;
		myModules = new Vector();
		mySensors = new Vector();
		myActuators = new Vector();
		mySimModules = new Vector();
		try {
			myParser = new DOMParser();
			myParser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, DEFAULT_SCHEMA_VALIDATION);
			myParser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, DEFAULT_SCHEMA_FULL_CHECKING);
			myParser.setFeature(VALIDATION_FEATURE_ID, DEFAULT_VALIDATION);
			myParser.setFeature(NAMESPACES_FEATURE_ID, DEFAULT_NAMESPACES);
		}
		catch (SAXException e) {
			System.err.println("warning: Parser does not support feature ("+NAMESPACES_FEATURE_ID+")");
		}
	}

	/** Traverses the specified node, recursively. */
	private void crawlBiosim(Node node, boolean firstPass) {
		// is there anything to do?
		if (node == null)
			return;
		String nodeName = node.getNodeName();
		if (nodeName.equals("Globals")){
			crawlGlobals(node, firstPass);
			return;
		}
		else if (nodeName.equals("SimBioModules")){
			crawlModules(node, firstPass);
			return;
		}
		else if (nodeName.equals("Sensors")){
			crawlSensors(node, firstPass);
			return;
		}
		else if (nodeName.equals("Actuators")){
			crawlActuators(node, firstPass);
			return;
		}
		else{
			Node child = node.getFirstChild();
			while (child != null) {
				crawlBiosim(child, firstPass);
				child = child.getNextSibling();
			}
		}

	}

	public void parseFile(String fileToParse){
		try{
			System.out.print("Initializing...");
			myParser.parse(fileToParse);
			Document document = myParser.getDocument();
			crawlBiosim(document, true);
			crawlBiosim(document, false);
			System.out.println("done");
			System.out.flush();
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

	//Globals
	private void crawlGlobals(Node node, boolean firstPass){
		BioDriver myDriver = null;
		try{
			myDriver = BioDriverHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str("BioDriver"));
		}
		catch (Exception e){
			e.printStackTrace();
		}
		if (firstPass){
			try{
				Logger myLogger = LoggerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str("Logger"));
				myDriver.setLogger(myLogger);
				myDriver.setRunTillN(Integer.parseInt(node.getAttributes().getNamedItem("runTillN").getNodeValue()));
				myDriver.setPauseSimulation(node.getAttributes().getNamedItem("startPaused").getNodeValue().equals("true"));
				myDriver.setRunTillDead(node.getAttributes().getNamedItem("runTillDead").getNodeValue().equals("true"));
				myDriver.setFullLogging(node.getAttributes().getNamedItem("isFullLoggingEnabled").getNodeValue().equals("true"));
				myDriver.setSensorLogging(node.getAttributes().getNamedItem("isSensorLoggingEnabled").getNodeValue().equals("true"));
				myDriver.setActuatorLogging(node.getAttributes().getNamedItem("isActuatorLoggingEnabled").getNodeValue().equals("true"));
				myDriver.setDriverStutterLength(Integer.parseInt(node.getAttributes().getNamedItem("driverStutterLength").getNodeValue()));
				myDriver.setLooping(node.getAttributes().getNamedItem("isLooping").getNodeValue().equals("true"));

				String stochasticString = node.getAttributes().getNamedItem("stochasticIntensity").getNodeValue();
				if (stochasticString.equals("HIGH_STOCH"))
					myDriver.setStochasticIntensity(StochasticIntensity.HIGH_STOCH);
				else if (stochasticString.equals("MEDIUM_STOCH"))
					myDriver.setStochasticIntensity(StochasticIntensity.MEDIUM_STOCH);
				else if (stochasticString.equals("LOW_STOCH"))
					myDriver.setStochasticIntensity(StochasticIntensity.LOW_STOCH);
				else
					myDriver.setStochasticIntensity(StochasticIntensity.NONE_STOCH);
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		else{
			//Give BioDriver crew to watch for (if we're doing run till dead)
			Node crewsToWatchNode = node.getAttributes().getNamedItem("crewsToWatch");
			if (crewsToWatchNode != null){
				String crewsToWatchString = crewsToWatchNode.getNodeValue();
				StringTokenizer tokenizer = new StringTokenizer(crewsToWatchString);
				CrewGroup[] crewGroups = new CrewGroup[tokenizer.countTokens()];
				for (int i = 0; tokenizer.hasMoreTokens(); i++){
					try{
						crewGroups[i] = CrewGroupHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(tokenizer.nextToken()));
						//System.out.println("Fetched "+crewGroups[i].getModuleName());
					}
					catch(org.omg.CORBA.UserException e){
						e.printStackTrace();
					}
				}
				myDriver.setCrewsToWatch(crewGroups);
			}
			//Fold Actuators, SimModules, and Sensors into  modules
			myModules.addAll(mySensors);
			myModules.addAll(mySimModules);
			myModules.addAll(myActuators);
			
			//Give Modules, Sensors, Actuatos to BioDriver to tick
			BioModule[] moduleArray = convertList(myModules);
			BioModule[] sensorArray = convertList(mySensors);
			BioModule[] actuatorArray = convertList(myActuators);
			BioModule[] simModulesArray = convertList(mySimModules);
			myDriver.setModules(moduleArray);
			myDriver.setSensors(sensorArray);
			myDriver.setActuators(actuatorArray);
			myDriver.setSimModules(simModulesArray);
		}
	}

	private static BioModule[] convertList(List pBioModules){
		BioModule[] newArray = new BioModule[pBioModules.size()];
		int i = 0;
		for (Iterator iter = pBioModules.iterator(); iter.hasNext(); i++){
			newArray[i] = BioModuleHelper.narrow((org.omg.CORBA.Object)(iter.next()));
		}
		return newArray;
	}

	private static boolean isCreatedLocally(Node node){
		return node.getAttributes().getNamedItem("createLocally").getNodeValue().equals("true");
	}

	private static String getModuleName(Node node){
		return node.getAttributes().getNamedItem("name").getNodeValue();
	}

	private static void printRemoteWarningMessage(String pName){
		System.out.println("Instance of the module moduleNamed "+pName+" should be created remotely (if not already done)");
	}

	private static float getStoreLevel(Node node){
		float level = 0f;
		try{
			level = Float.parseFloat(node.getAttributes().getNamedItem("level").getNodeValue());
		}
		catch (NumberFormatException e){

			e.printStackTrace();
		}
		return level;
	}

	private static float getStoreCapacity(Node node){
		float capacity = 0f;
		try{
			capacity = Float.parseFloat(node.getAttributes().getNamedItem("capacity").getNodeValue());
		}
		catch (NumberFormatException e){

			e.printStackTrace();
		}
		return capacity;
	}
	
	private static int getStoreResupplyFrequency(Node node){
		int frequency = 0;
		try{
			frequency = Integer.parseInt(node.getAttributes().getNamedItem("resupplyFrequency").getNodeValue());
		}
		catch (NumberFormatException e){

			e.printStackTrace();
		}
		return frequency;
	}
	
	private static float getStoreResupplyAmount(Node node){
		float amount = 0f;
		try{
			amount = Float.parseFloat(node.getAttributes().getNamedItem("resupplyAmount").getNodeValue());
		}
		catch (NumberFormatException e){

			e.printStackTrace();
		}
		return amount;
	}

	private static float[] getMaxFlowRates(Node node){
		if (node == null)
			return new float[0];
		String arrayString = node.getAttributes().getNamedItem("maxFlowRates").getNodeValue();
		StringTokenizer tokenizer = new StringTokenizer(arrayString);
		float[] maxFlowRates = new float[tokenizer.countTokens()];

		for (int i = 0; tokenizer.hasMoreTokens(); i++){
			try{
				maxFlowRates[i] = Float.parseFloat(tokenizer.nextToken());
			}
			catch (NumberFormatException e){

				e.printStackTrace();
			}
		}
		return maxFlowRates;
	}

	private static float[] getDesiredFlowRates(Node node){
		if (node == null)
			return new float[0];
		String arrayString = node.getAttributes().getNamedItem("desiredFlowRates").getNodeValue();
		StringTokenizer tokenizer = new StringTokenizer(arrayString);
		float[] desiredFlowRates = new float[tokenizer.countTokens()];

		for (int i = 0; tokenizer.hasMoreTokens(); i++){
			try{
				desiredFlowRates[i] = Float.parseFloat(tokenizer.nextToken());
			}
			catch (NumberFormatException e){

				e.printStackTrace();
			}
		}
		return desiredFlowRates;
	}


	private void configureSimBioModule(SimBioModule pModule, Node node){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("powerConsumer")){
				PowerConsumer myPowerConsumer = (PowerConsumer)(pModule);
				BioModule[] modules = getInputs(child);
				PowerStore[] inputs = new PowerStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = PowerStoreHelper.narrow(modules[0]);
				myPowerConsumer.setPowerInputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("potableWaterConsumer")){
				PotableWaterConsumer myPotableWaterConsumer = (PotableWaterConsumer)(pModule);
				BioModule[] modules = getInputs(child);
				PotableWaterStore[] inputs = new PotableWaterStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = PotableWaterStoreHelper.narrow(modules[0]);
				myPotableWaterConsumer.setPotableWaterInputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("greyWaterConsumer")){
				GreyWaterConsumer myGreyWaterConsumer = (GreyWaterConsumer)(pModule);
				BioModule[] modules = getInputs(child);
				GreyWaterStore[] inputs = new GreyWaterStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = GreyWaterStoreHelper.narrow(modules[0]);
				myGreyWaterConsumer.setGreyWaterInputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("dirtyWaterConsumer")){
				DirtyWaterConsumer myDirtyWaterConsumer = (DirtyWaterConsumer)(pModule);
				BioModule[] modules = getInputs(child);
				DirtyWaterStore[] inputs = new DirtyWaterStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = DirtyWaterStoreHelper.narrow(modules[0]);
				myDirtyWaterConsumer.setDirtyWaterInputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("airConsumer")){
				AirConsumer myAirConsumer = (AirConsumer)(pModule);
				BioModule[] modules = getInputs(child);
				SimEnvironment[] inputs = new SimEnvironment[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = SimEnvironmentHelper.narrow(modules[0]);
				myAirConsumer.setAirInputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("H2Consumer")){
				H2Consumer myH2Consumer = (H2Consumer)(pModule);
				BioModule[] modules = getInputs(child);
				H2Store[] inputs = new H2Store[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = H2StoreHelper.narrow(modules[0]);
				myH2Consumer.setH2Inputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("nitrogenConsumer")){
				NitrogenConsumer myNitrogenConsumer = (NitrogenConsumer)(pModule);
				BioModule[] modules = getInputs(child);
				NitrogenStore[] inputs = new NitrogenStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = NitrogenStoreHelper.narrow(modules[0]);
				myNitrogenConsumer.setNitrogenInputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("O2Consumer")){
				O2Consumer myO2Consumer = (O2Consumer)(pModule);
				BioModule[] modules = getInputs(child);
				O2Store[] inputs = new O2Store[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = O2StoreHelper.narrow(modules[0]);
				myO2Consumer.setO2Inputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("CO2Consumer")){
				CO2Consumer myCO2Consumer = (CO2Consumer)(pModule);
				BioModule[] modules = getInputs(child);
				CO2Store[] inputs = new CO2Store[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = CO2StoreHelper.narrow(modules[0]);
				myCO2Consumer.setCO2Inputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("lightConsumer")){
				LightConsumer myLightConsumer = (LightConsumer)(pModule);
				BioModule[] modules = getInputs(child);
				SimEnvironment[] inputs = new SimEnvironment[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = SimEnvironmentHelper.narrow(modules[0]);
				myLightConsumer.setLightInput(inputs[0]);
			}
			else if (childName.equals("biomassConsumer")){
				BiomassConsumer myBiomassConsumer = (BiomassConsumer)(pModule);
				BioModule[] modules = getInputs(child);
				BiomassStore[] inputs = new BiomassStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = BiomassStoreHelper.narrow(modules[0]);
				myBiomassConsumer.setBiomassInputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("foodConsumer")){
				FoodConsumer myFoodConsumer = (FoodConsumer)(pModule);
				BioModule[] modules = getInputs(child);
				FoodStore[] inputs = new FoodStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					inputs[i] = FoodStoreHelper.narrow(modules[0]);
				myFoodConsumer.setFoodInputs(inputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("O2AirConsumer")){
				O2AirConsumer myO2AirConsumer = (O2AirConsumer)(pModule);
				Node environmentNode = getEnvironmentNode(child);
				Node storeNode = getStoreNode(child);
				BioModule[] environmentModules = getInputs(environmentNode);
				BioModule[] storeModules = getInputs(storeNode);
				SimEnvironment[] environmentInputs = new SimEnvironment[environmentModules.length];
				O2Store[] storeInputs = new O2Store[storeModules.length];
				for (int i = 0; i < environmentModules.length; i++)
					environmentInputs[i] = SimEnvironmentHelper.narrow(environmentModules[0]);
				for (int i = 0; i < storeModules.length; i++)
					storeInputs[i] = O2StoreHelper.narrow(storeModules[0]);
				myO2AirConsumer.setO2AirEnvironmentInputs(environmentInputs, getMaxFlowRates(environmentNode), getDesiredFlowRates(environmentNode));
				myO2AirConsumer.setO2AirStoreInputs(storeInputs, getMaxFlowRates(storeNode), getDesiredFlowRates(storeNode));
			}
			else if (childName.equals("CO2AirConsumer")){
				CO2AirConsumer myCO2AirConsumer = (CO2AirConsumer)(pModule);
				Node environmentNode = getEnvironmentNode(child);
				Node storeNode = getStoreNode(child);
				BioModule[] environmentModules = getInputs(environmentNode);
				BioModule[] storeModules = getInputs(storeNode);
				SimEnvironment[] environmentInputs = new SimEnvironment[environmentModules.length];
				CO2Store[] storeInputs = new CO2Store[storeModules.length];
				for (int i = 0; i < environmentModules.length; i++)
					environmentInputs[i] = SimEnvironmentHelper.narrow(environmentModules[0]);
				for (int i = 0; i < storeModules.length; i++)
					storeInputs[i] = CO2StoreHelper.narrow(storeModules[0]);
				myCO2AirConsumer.setCO2AirEnvironmentInputs(environmentInputs, getMaxFlowRates(environmentNode), getDesiredFlowRates(environmentNode));
				myCO2AirConsumer.setCO2AirStoreInputs(storeInputs, getMaxFlowRates(storeNode), getDesiredFlowRates(storeNode));
			}
			else if (childName.equals("nitrogenAirConsumer")){
				NitrogenAirConsumer myNitrogenAirConsumer = (NitrogenAirConsumer)(pModule);
				Node environmentNode = getEnvironmentNode(child);
				Node storeNode = getStoreNode(child);
				BioModule[] environmentModules = getInputs(environmentNode);
				BioModule[] storeModules = getInputs(storeNode);
				SimEnvironment[] environmentInputs = new SimEnvironment[environmentModules.length];
				NitrogenStore[] storeInputs = new NitrogenStore[storeModules.length];
				for (int i = 0; i < environmentModules.length; i++)
					environmentInputs[i] = SimEnvironmentHelper.narrow(environmentModules[0]);
				for (int i = 0; i < storeModules.length; i++)
					storeInputs[i] = NitrogenStoreHelper.narrow(storeModules[0]);
				myNitrogenAirConsumer.setNitrogenAirEnvironmentInputs(environmentInputs, getMaxFlowRates(environmentNode), getDesiredFlowRates(environmentNode));
				myNitrogenAirConsumer.setNitrogenAirStoreInputs(storeInputs, getMaxFlowRates(storeNode), getDesiredFlowRates(storeNode));
			}
			else if (childName.equals("waterAirConsumer")){
				WaterAirConsumer myWaterAirConsumer = (WaterAirConsumer)(pModule);
				Node environmentNode = getEnvironmentNode(child);
				Node storeNode = getStoreNode(child);
				BioModule[] environmentModules = getInputs(environmentNode);
				BioModule[] storeModules = getInputs(storeNode);
				SimEnvironment[] environmentInputs = new SimEnvironment[environmentModules.length];
				PotableWaterStore[] storeInputs = new PotableWaterStore[storeModules.length];
				for (int i = 0; i < environmentModules.length; i++)
					environmentInputs[i] = SimEnvironmentHelper.narrow(environmentModules[0]);
				for (int i = 0; i < storeModules.length; i++)
					storeInputs[i] = PotableWaterStoreHelper.narrow(storeModules[0]);
				myWaterAirConsumer.setWaterAirEnvironmentInputs(environmentInputs, getMaxFlowRates(environmentNode), getDesiredFlowRates(environmentNode));
				myWaterAirConsumer.setWaterAirStoreInputs(storeInputs, getMaxFlowRates(storeNode), getDesiredFlowRates(storeNode));
			}
			else if (childName.equals("powerProducer")){
				PowerProducer myPowerProducer = (PowerProducer)(pModule);
				BioModule[] modules = getOutputs(child);
				PowerStore[] outputs = new PowerStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = PowerStoreHelper.narrow(modules[0]);
				myPowerProducer.setPowerOutputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("potableWaterProducer")){
				PotableWaterProducer myPotableWaterProducer = (PotableWaterProducer)(pModule);
				BioModule[] modules = getOutputs(child);
				PotableWaterStore[] outputs = new PotableWaterStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = PotableWaterStoreHelper.narrow(modules[0]);
				myPotableWaterProducer.setPotableWaterOutputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("greyWaterProducer")){
				GreyWaterProducer myGreyWaterProducer = (GreyWaterProducer)(pModule);
				BioModule[] modules = getOutputs(child);
				GreyWaterStore[] outputs = new GreyWaterStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = GreyWaterStoreHelper.narrow(modules[0]);
				myGreyWaterProducer.setGreyWaterOutputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("dirtyWaterProducer")){
				DirtyWaterProducer myDirtyWaterProducer = (DirtyWaterProducer)(pModule);
				BioModule[] modules = getOutputs(child);
				DirtyWaterStore[] outputs = new DirtyWaterStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = DirtyWaterStoreHelper.narrow(modules[0]);
				myDirtyWaterProducer.setDirtyWaterOutputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("airProducer")){
				AirProducer myAirProducer = (AirProducer)(pModule);
				BioModule[] modules = getOutputs(child);
				SimEnvironment[] outputs = new SimEnvironment[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = SimEnvironmentHelper.narrow(modules[0]);
				myAirProducer.setAirOutputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("H2Producer")){
				H2Producer myH2Producer = (H2Producer)(pModule);
				BioModule[] modules = getOutputs(child);
				H2Store[] outputs = new H2Store[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = H2StoreHelper.narrow(modules[0]);
				myH2Producer.setH2Outputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("nitrogenProducer")){
				NitrogenProducer myNitrogenProducer = (NitrogenProducer)(pModule);
				BioModule[] modules = getOutputs(child);
				NitrogenStore[] outputs = new NitrogenStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = NitrogenStoreHelper.narrow(modules[0]);
				myNitrogenProducer.setNitrogenOutputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("O2Producer")){
				O2Producer myO2Producer = (O2Producer)(pModule);
				BioModule[] modules = getOutputs(child);
				O2Store[] outputs = new O2Store[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = O2StoreHelper.narrow(modules[0]);
				myO2Producer.setO2Outputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("CO2Producer")){
				CO2Producer myCO2Producer = (CO2Producer)(pModule);
				BioModule[] modules = getOutputs(child);
				CO2Store[] outputs = new CO2Store[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = CO2StoreHelper.narrow(modules[0]);
				myCO2Producer.setCO2Outputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("biomassProducer")){
				BiomassProducer myBiomassProducer = (BiomassProducer)(pModule);
				BioModule[] modules = getOutputs(child);
				BiomassStore[] outputs = new BiomassStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = BiomassStoreHelper.narrow(modules[0]);
				myBiomassProducer.setBiomassOutputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("foodProducer")){
				FoodProducer myFoodProducer = (FoodProducer)(pModule);
				BioModule[] modules = getOutputs(child);
				FoodStore[] outputs = new FoodStore[modules.length];
				for (int i = 0; i < modules.length; i++)
					outputs[i] = FoodStoreHelper.narrow(modules[0]);
				myFoodProducer.setFoodOutputs(outputs, getMaxFlowRates(child), getDesiredFlowRates(child));
			}
			else if (childName.equals("O2AirProducer")){
				O2AirProducer myO2AirProducer = (O2AirProducer)(pModule);
				Node environmentNode = getEnvironmentNode(child);
				Node storeNode = getStoreNode(child);
				BioModule[] environmentModules = getOutputs(environmentNode);
				BioModule[] storeModules = getOutputs(storeNode);
				SimEnvironment[] environmentOutputs = new SimEnvironment[environmentModules.length];
				O2Store[] storeOutputs = new O2Store[storeModules.length];
				for (int i = 0; i < environmentModules.length; i++)
					environmentOutputs[i] = SimEnvironmentHelper.narrow(environmentModules[0]);
				for (int i = 0; i < storeModules.length; i++)
					storeOutputs[i] = O2StoreHelper.narrow(storeModules[0]);
				myO2AirProducer.setO2AirEnvironmentOutputs(environmentOutputs, getMaxFlowRates(environmentNode), getDesiredFlowRates(environmentNode));
				myO2AirProducer.setO2AirStoreOutputs(storeOutputs, getMaxFlowRates(storeNode), getDesiredFlowRates(storeNode));
			}
			else if (childName.equals("CO2AirProducer")){
				CO2AirProducer myCO2AirProducer = (CO2AirProducer)(pModule);
				Node environmentNode = getEnvironmentNode(child);
				Node storeNode = getStoreNode(child);
				BioModule[] environmentModules = getOutputs(environmentNode);
				BioModule[] storeModules = getOutputs(storeNode);
				SimEnvironment[] environmentOutputs = new SimEnvironment[environmentModules.length];
				CO2Store[] storeOutputs = new CO2Store[storeModules.length];
				for (int i = 0; i < environmentModules.length; i++)
					environmentOutputs[i] = SimEnvironmentHelper.narrow(environmentModules[0]);
				for (int i = 0; i < storeModules.length; i++)
					storeOutputs[i] = CO2StoreHelper.narrow(storeModules[0]);
				myCO2AirProducer.setCO2AirEnvironmentOutputs(environmentOutputs, getMaxFlowRates(environmentNode), getDesiredFlowRates(environmentNode));
				myCO2AirProducer.setCO2AirStoreOutputs(storeOutputs, getMaxFlowRates(storeNode), getDesiredFlowRates(storeNode));
			}
			else if (childName.equals("nitrogenAirProducer")){
				NitrogenAirProducer myNitrogenAirProducer = (NitrogenAirProducer)(pModule);
				Node environmentNode = getEnvironmentNode(child);
				Node storeNode = getStoreNode(child);
				BioModule[] environmentModules = getOutputs(environmentNode);
				BioModule[] storeModules = getOutputs(storeNode);
				SimEnvironment[] environmentOutputs = new SimEnvironment[environmentModules.length];
				NitrogenStore[] storeOutputs = new NitrogenStore[storeModules.length];
				for (int i = 0; i < environmentModules.length; i++)
					environmentOutputs[i] = SimEnvironmentHelper.narrow(environmentModules[0]);
				for (int i = 0; i < storeModules.length; i++)
					storeOutputs[i] = NitrogenStoreHelper.narrow(storeModules[0]);
				myNitrogenAirProducer.setNitrogenAirEnvironmentOutputs(environmentOutputs, getMaxFlowRates(environmentNode), getDesiredFlowRates(environmentNode));
				myNitrogenAirProducer.setNitrogenAirStoreOutputs(storeOutputs, getMaxFlowRates(storeNode), getDesiredFlowRates(storeNode));
			}
			else if (childName.equals("waterAirProducer")){
				WaterAirProducer myWaterAirProducer = (WaterAirProducer)(pModule);
				Node environmentNode = getEnvironmentNode(child);
				Node storeNode = getStoreNode(child);
				BioModule[] environmentModules = getOutputs(environmentNode);
				BioModule[] storeModules = getOutputs(storeNode);
				SimEnvironment[] environmentOutputs = new SimEnvironment[environmentModules.length];
				PotableWaterStore[] storeOutputs = new PotableWaterStore[storeModules.length];
				for (int i = 0; i < environmentModules.length; i++)
					environmentOutputs[i] = SimEnvironmentHelper.narrow(environmentModules[0]);
				for (int i = 0; i < storeModules.length; i++)
					storeOutputs[i] = PotableWaterStoreHelper.narrow(storeModules[0]);
				myWaterAirProducer.setWaterAirEnvironmentOutputs(environmentOutputs, getMaxFlowRates(environmentNode), getDesiredFlowRates(environmentNode));
				myWaterAirProducer.setWaterAirStoreOutputs(storeOutputs, getMaxFlowRates(storeNode), getDesiredFlowRates(storeNode));
			}
			child = child.getNextSibling();
		}
	}

	private static Node getEnvironmentNode(Node node){
		Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeName().startsWith("environment"))
				return child;
			child = child.getNextSibling();
		}
		return null;
	}

	private static Node getStoreNode(Node node){
		Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeName().startsWith("store"))
				return child;
			child = child.getNextSibling();
		}
		return null;
	}

	private BioModule[] getInputs(Node node){
		if (node == null)
			return new BioModule[0];
		String arrayString = node.getAttributes().getNamedItem("inputs").getNodeValue();
		StringTokenizer tokenizer = new StringTokenizer(arrayString);
		BioModule[] inputs = new BioModule[tokenizer.countTokens()];
		for (int i = 0; tokenizer.hasMoreTokens(); i++){
			try{
				inputs[i] = BioModuleHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(tokenizer.nextToken()));
				//System.out.println("Fetched "+inputs[i].getModuleName());
			}
			catch(org.omg.CORBA.UserException e){
				e.printStackTrace();
			}
		}
		return inputs;
	}

	private BioModule[] getOutputs(Node node){
		if (node == null)
			return new BioModule[0];
		String arrayString = node.getAttributes().getNamedItem("outputs").getNodeValue();
		StringTokenizer tokenizer = new StringTokenizer(arrayString);
		BioModule[] outputs = new BioModule[tokenizer.countTokens()];
		for (int i = 0; tokenizer.hasMoreTokens(); i++){
			try{
				outputs[i] = BioModuleHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(tokenizer.nextToken()));
				//System.out.println("Fetched "+outputs[i].getModuleName());
			}
			catch(org.omg.CORBA.UserException e){
				e.printStackTrace();
			}
		}
		return outputs;
	}

	//Modules
	private void crawlModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("air")){
				crawlAirModules(child, firstPass);

			}
			else if (childName.equals("crew")){
				crawlCrewModules(child, firstPass);

			}
			else if (childName.equals("environment")){
				crawlEnvironmentModules(child, firstPass);

			}
			else if (childName.equals("food")){
				crawlFoodModules(child, firstPass);

			}
			else if (childName.equals("framework")){
				crawlFrameworkModules(child, firstPass);

			}
			else if (childName.equals("power")){
				crawlPowerModules(child, firstPass);

			}
			else if (childName.equals("water")){
				crawlWaterModules(child, firstPass);

			}
			child = child.getNextSibling();
		}
	}
	
	private boolean getEnableBreakDown(Node pNode){
		return pNode.getAttributes().getNamedItem("isLoggingEnabled").getNodeValue().equals("true");
	}
	
	private boolean getLogging(Node pNode){
		return pNode.getAttributes().getNamedItem("isBreakdownEnabled").getNodeValue().equals("true");
	}
	
	private StochasticIntensity getStochasticIntensity(Node pNode){
		String intensityString = pNode.getAttributes().getNamedItem("setStochasticIntensity").getNodeValue();
		if (intensityString.equals("HIGH_STOCH"))
			return StochasticIntensity.HIGH_STOCH;
		else if (intensityString.equals("MEDIUM_STOCH"))
			return StochasticIntensity.MEDIUM_STOCH;
		else if (intensityString.equals("LOW_STOCH"))
			return StochasticIntensity.LOW_STOCH;
		else
			return StochasticIntensity.NONE_STOCH;
	}
	
	private MalfunctionIntensity getMalfunctionIntensity(Node pNode){
		String intensityString = pNode.getAttributes().getNamedItem("intensity").getNodeValue();
		if (intensityString.equals("SEVERE_MALF"))
			return MalfunctionIntensity.SEVERE_MALF;
		else if (intensityString.equals("MEDIUM_MALF"))
			return MalfunctionIntensity.MEDIUM_MALF;
		else
			return MalfunctionIntensity.LOW_MALF;
	}
	
	private MalfunctionLength getMalfunctionLength(Node pNode){
		String lengthString = pNode.getAttributes().getNamedItem("length").getNodeValue();
		if (lengthString.equals("TEMPORARY_MALF"))
			return MalfunctionLength.TEMPORARY_MALF;
		else
			return MalfunctionLength.PERMANENT_MALF;
	}
	
	private int getMalfunctionTick(Node pNode){
		int occursAtTick = 0;
		try{
			occursAtTick = Integer.parseInt(pNode.getAttributes().getNamedItem("occursAtTick").getNodeValue());
		}
		catch (NumberFormatException e){

			e.printStackTrace();
		}
		return occursAtTick;
	}
	
	private void setupBioModule(BioModuleImpl pModule, Node node){
		pModule.setEnableBreakdown(getEnableBreakDown(node));
		pModule.setLogging(getLogging(node));
		pModule.setStochasticIntensity(getStochasticIntensity(node));
		Node child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeName().equals("malfunction")){
				pModule.scheduleMalfunction(getMalfunctionIntensity(child), getMalfunctionLength(child), getMalfunctionTick(child));
			}
			child = child.getNextSibling();
		}
	}

	private void createAirRS(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating AirRS with moduleName: "+moduleName);
			AirRSImpl myAirRSImpl = new AirRSImpl(myID, moduleName);
			setupBioModule(myAirRSImpl, node);
			BiosimServer.registerServer(new AirRSPOATie(myAirRSImpl), myAirRSImpl.getModuleName(), myAirRSImpl.getID());
			mySimModules.add(OrbUtils.poaToCorbaObj(myAirRSImpl));
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAirRS(Node node){
		//System.out.println("Configuring AirRS");
		String moduleName = getModuleName(node);
		try{
			AirRS myAirRS = AirRSHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			configureSimBioModule(myAirRS, node);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
	}

	private void createO2Store(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2Store with moduleName: "+moduleName);
			O2StoreImpl myO2StoreImpl = new O2StoreImpl(myID, moduleName);
			setupBioModule(myO2StoreImpl, node);
			myO2StoreImpl.setCapacity(getStoreCapacity(node));
			myO2StoreImpl.setLevel(getStoreLevel(node));
			myO2StoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myO2StoreImpl));
			BiosimServer.registerServer(new O2StorePOATie(myO2StoreImpl), myO2StoreImpl.getModuleName(), myO2StoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createCO2Store(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2Store with moduleName: "+moduleName);
			CO2StoreImpl myCO2StoreImpl = new CO2StoreImpl(myID, moduleName);
			setupBioModule(myCO2StoreImpl, node);
			myCO2StoreImpl.setCapacity(getStoreCapacity(node));
			myCO2StoreImpl.setLevel(getStoreLevel(node));
			myCO2StoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myCO2StoreImpl));
			BiosimServer.registerServer(new CO2StorePOATie(myCO2StoreImpl), myCO2StoreImpl.getModuleName(), myCO2StoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createH2Store(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating H2Store with moduleName: "+moduleName);
			H2StoreImpl myH2StoreImpl = new H2StoreImpl(myID, moduleName);
			setupBioModule(myH2StoreImpl, node);
			myH2StoreImpl.setCapacity(getStoreCapacity(node));
			myH2StoreImpl.setLevel(getStoreLevel(node));
			myH2StoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myH2StoreImpl));
			BiosimServer.registerServer(new H2StorePOATie(myH2StoreImpl), myH2StoreImpl.getModuleName(), myH2StoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}
	
	private void createNitrogenStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating NitrogenStore with moduleName: "+moduleName);
			NitrogenStoreImpl myNitrogenStoreImpl = new NitrogenStoreImpl(myID, moduleName);
			setupBioModule(myNitrogenStoreImpl, node);
			myNitrogenStoreImpl.setCapacity(getStoreCapacity(node));
			myNitrogenStoreImpl.setLevel(getStoreLevel(node));
			myNitrogenStoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myNitrogenStoreImpl));
			BiosimServer.registerServer(new NitrogenStorePOATie(myNitrogenStoreImpl), myNitrogenStoreImpl.getModuleName(), myNitrogenStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void crawlAirModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("AirRS")){
				if (firstPass)
					createAirRS(child);
				else
					configureAirRS(child);

			}
			else if (childName.equals("O2Store")){
				if (firstPass)
					createO2Store(child);

			}
			else if (childName.equals("CO2Store")){
				if (firstPass)
					createCO2Store(child);

			}
			else if (childName.equals("H2Store")){
				if (firstPass)
					createH2Store(child);

			}
			else if (childName.equals("NitrogenStore")){
				if (firstPass)
					createNitrogenStore(child);

			}
			child = child.getNextSibling();
		}
	}

	private Activity createActivity(Node node){
		String name = node.getAttributes().getNamedItem("name").getNodeValue();
		int length = 0;
		int intensity = 0;
		try{
			length = Integer.parseInt(node.getAttributes().getNamedItem("length").getNodeValue());
			intensity = Integer.parseInt(node.getAttributes().getNamedItem("intensity").getNodeValue());
		}
		catch (NumberFormatException e){

			e.printStackTrace();
		}
		ActivityImpl newActivityImpl = new ActivityImpl(name, length, intensity);
		return ActivityHelper.narrow(OrbUtils.poaToCorbaObj(newActivityImpl));
	}

	private Schedule createSchedule(Node node){
		Schedule newSchedule = new Schedule();
		Node child = node.getFirstChild();
		while(child != null){
			if (child.getNodeName().equals("activity")){
				Activity newActivity = createActivity(child);
				newSchedule.insertActivityInSchedule(newActivity);
			}
			child = child.getNextSibling();
		}
		return newSchedule;
	}

	private void createCrewPerson(Node node, CrewGroupImpl crew){
		Node child = node.getFirstChild();
		Schedule schedule = null;
		while (child != null){
			if (child.getNodeName().equals("schedule"))
				schedule = createSchedule(node.getFirstChild().getNextSibling());
			child = child.getNextSibling();
		}
		String name = node.getAttributes().getNamedItem("name").getNodeValue();
		Sex sex;
		if (node.getAttributes().getNamedItem("sex").getNodeValue().equals("FEMALE"))
			sex = Sex.female;
		else
			sex = Sex.male;
		float age = 0f;
		float weight = 0f;
		try{
			age = Float.parseFloat(node.getAttributes().getNamedItem("age").getNodeValue());
			weight = Float.parseFloat(node.getAttributes().getNamedItem("weight").getNodeValue());
		}
		catch (NumberFormatException e){

			e.printStackTrace();
		}
		crew.createCrewPerson(name, age, weight, sex, schedule);
	}

	private void createCrewGroup(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CrewGroup with moduleName: "+moduleName);
			CrewGroupImpl myCrewGroupImpl = new CrewGroupImpl(myID, moduleName);
			setupBioModule(myCrewGroupImpl, node);
			Node child = node.getFirstChild();
			while (child != null) {
				if (child.getNodeName().equals("crewPerson"))
					createCrewPerson(child, myCrewGroupImpl);
				child = child.getNextSibling();
			}
			mySimModules.add(OrbUtils.poaToCorbaObj(myCrewGroupImpl));
			BiosimServer.registerServer(new CrewGroupPOATie(myCrewGroupImpl), myCrewGroupImpl.getModuleName(), myCrewGroupImpl.getID());

		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCrewGroup(Node node){
		//System.out.println("Configuring CrewGroup");
		String moduleName = getModuleName(node);
		try{
			CrewGroup myCrewGroup = CrewGroupHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			configureSimBioModule(myCrewGroup, node);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
	}

	private void crawlCrewModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("CrewGroup")){
				if (firstPass)
					createCrewGroup(child);
				else
					configureCrewGroup(child);

			}
			child = child.getNextSibling();
		}
	}

	private void createSimEnvironment(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating SimEnvironment with moduleName: "+moduleName);
			SimEnvironmentImpl mySimEnvironmentImpl = null;
			float CO2Moles = 0f;
			float O2Moles = 0f;
			float waterMoles = 0f;
			float otherMoles = 0f;
			float nitrogenMoles = 0f;
			float volume = 0f;
			Node CO2MolesNode = null;
			Node O2MolesNode = null;
			Node waterMolesNode = null;
			Node otherMolesNode = null;
			Node nitrogenMolesNode = null;
			try{
				volume = Float.parseFloat(node.getAttributes().getNamedItem("initialVolume").getNodeValue());
				CO2MolesNode = node.getAttributes().getNamedItem("initialCO2Moles");
				O2MolesNode = node.getAttributes().getNamedItem("initialO2Moles");
				waterMolesNode = node.getAttributes().getNamedItem("initialWaterMoles");
				otherMolesNode = node.getAttributes().getNamedItem("initialOtherMoles");
				otherMolesNode = node.getAttributes().getNamedItem("initialNitrogenMoles");
				if (CO2MolesNode != null)
					CO2Moles = Float.parseFloat(CO2MolesNode.getNodeValue());
				if (O2MolesNode != null)
					O2Moles = Float.parseFloat(O2MolesNode.getNodeValue());
				if (waterMolesNode != null)
					waterMoles = Float.parseFloat(waterMolesNode.getNodeValue());
				if (otherMolesNode != null)
					otherMoles = Float.parseFloat(otherMolesNode.getNodeValue());
				if (nitrogenMolesNode != null)
					nitrogenMoles = Float.parseFloat(nitrogenMolesNode.getNodeValue());
			}
			catch (NumberFormatException e){

				e.printStackTrace();
			}
			if ((CO2MolesNode != null) || (O2MolesNode != null) || (waterMolesNode != null) || (otherMolesNode != null) || (nitrogenMolesNode != null))
				mySimEnvironmentImpl = new SimEnvironmentImpl(CO2Moles, O2Moles, otherMoles, waterMoles, nitrogenMoles, volume, moduleName, myID);
			else
				mySimEnvironmentImpl = new SimEnvironmentImpl(myID, volume, moduleName);
			setupBioModule(mySimEnvironmentImpl, node);
			mySimModules.add(OrbUtils.poaToCorbaObj(mySimEnvironmentImpl));
			BiosimServer.registerServer(new SimEnvironmentPOATie(mySimEnvironmentImpl), mySimEnvironmentImpl.getModuleName(), mySimEnvironmentImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void crawlEnvironmentModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("SimEnvironment")){
				if (firstPass)
					createSimEnvironment(child);
			}
			child = child.getNextSibling();
		}
	}

	private void createAccumulator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating Accumulator with moduleName: "+moduleName);
			AccumulatorImpl myAccumulatorImpl = new AccumulatorImpl(myID, moduleName);
			setupBioModule(myAccumulatorImpl, node);
			mySimModules.add(OrbUtils.poaToCorbaObj(myAccumulatorImpl));
			BiosimServer.registerServer(new AccumulatorPOATie(myAccumulatorImpl), myAccumulatorImpl.getModuleName(), myAccumulatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAccumulator(Node node){
		//System.out.println("Configuring Accumulator");
		String moduleName = getModuleName(node);
		try{
			Accumulator myAccumulator = AccumulatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			configureSimBioModule(myAccumulator, node);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
	}

	private void createInjector(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating Injector with moduleName: "+moduleName);
			InjectorImpl myInjectorImpl = new InjectorImpl(myID, moduleName);
			setupBioModule(myInjectorImpl, node);
			mySimModules.add(OrbUtils.poaToCorbaObj(myInjectorImpl));
			BiosimServer.registerServer(new InjectorPOATie(myInjectorImpl), myInjectorImpl.getModuleName(), myInjectorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureInjector(Node node){
		//System.out.println("Configuring Injector");
		String moduleName = getModuleName(node);
		try{
			Injector myInjector = InjectorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			configureSimBioModule(myInjector, node);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
	}

	private void crawlFrameworkModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("Accumulator")){
				if (firstPass)
					createAccumulator(child);
				else
					configureAccumulator(child);
			}
			else if (childName.equals("Injector")){
				if (firstPass)
					createInjector(child);
				else
					configureInjector(child);
			}
			child = child.getNextSibling();
		}
	}

	private static PlantType getCropType(Node node){
		String cropString = node.getAttributes().getNamedItem("cropType").getNodeValue();
		if (cropString.equals("DRY_BEAN"))
			return PlantType.DRY_BEAN;
		else if (cropString.equals("LETTUCE"))
			return PlantType.LETTUCE;
		else if (cropString.equals("PEANUT"))
			return PlantType.PEANUT;
		else if (cropString.equals("RICE"))
			return PlantType.RICE;
		else if (cropString.equals("SOYBEAN"))
			return PlantType.SOYBEAN;
		else if (cropString.equals("SWEET_POTATO"))
			return PlantType.SWEET_POTATO;
		else if (cropString.equals("TOMATO"))
			return PlantType.TOMATO;
		else if (cropString.equals("WHEAT"))
			return PlantType.WHEAT;
		else if (cropString.equals("WHITE_POTATO"))
			return PlantType.WHITE_POTATO;
		else
			return PlantType.UNKNOWN_PLANT;
	}

	private static float getCropArea(Node node){
		float area = 0f;
		try{
			area = Float.parseFloat(node.getAttributes().getNamedItem("cropArea").getNodeValue());
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
		return area;
	}

	private void createBiomassRS(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating BiomassRS with moduleName: "+moduleName);
			BiomassRSImpl myBiomassRSImpl = new BiomassRSImpl(myID, moduleName);
			setupBioModule(myBiomassRSImpl, node);
			Node child = node.getFirstChild();
			while (child != null) {
				if (child.getNodeName().equals("shelf"))
					myBiomassRSImpl.createNewShelf(getCropType(child), getCropArea(child));
				child = child.getNextSibling();
			}
			mySimModules.add(OrbUtils.poaToCorbaObj(myBiomassRSImpl));
			BiosimServer.registerServer(new BiomassRSPOATie(myBiomassRSImpl), myBiomassRSImpl.getModuleName(), myBiomassRSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassRS(Node node){
		//System.out.println("Configuring BiomassRS");
		String moduleName = getModuleName(node);
		try{
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			configureSimBioModule(myBiomassRS, node);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
	}

	private void createFoodProcessor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating FoodProcessor with moduleName: "+moduleName);
			FoodProcessorImpl myFoodProcessorImpl = new FoodProcessorImpl(myID, moduleName);
			setupBioModule(myFoodProcessorImpl, node);
			mySimModules.add(OrbUtils.poaToCorbaObj(myFoodProcessorImpl));
			BiosimServer.registerServer(new FoodProcessorPOATie(myFoodProcessorImpl), myFoodProcessorImpl.getModuleName(), myFoodProcessorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureFoodProcessor(Node node){
		//System.out.println("Configuring FoodProcessor");
		String moduleName = getModuleName(node);
		try{
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			configureSimBioModule(myFoodProcessor, node);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
	}

	private void createBiomassStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating BiomassStore with moduleName: "+moduleName);
			BiomassStoreImpl myBiomassStoreImpl = new BiomassStoreImpl(myID, moduleName);
			setupBioModule(myBiomassStoreImpl, node);
			myBiomassStoreImpl.setCapacity(getStoreCapacity(node));
			myBiomassStoreImpl.setLevel(getStoreLevel(node));
			myBiomassStoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myBiomassStoreImpl));
			BiosimServer.registerServer(new BiomassStorePOATie(myBiomassStoreImpl), myBiomassStoreImpl.getModuleName(), myBiomassStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createFoodStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating FoodStore with moduleName: "+moduleName);
			FoodStoreImpl myFoodStoreImpl = new FoodStoreImpl(myID, moduleName);
			setupBioModule(myFoodStoreImpl, node);
			myFoodStoreImpl.setCapacity(getStoreCapacity(node));
			myFoodStoreImpl.setLevel(getStoreLevel(node));
			myFoodStoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myFoodStoreImpl));
			BiosimServer.registerServer(new FoodStorePOATie(myFoodStoreImpl), myFoodStoreImpl.getModuleName(), myFoodStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void crawlFoodModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("BiomassRS")){
				if (firstPass)
					createBiomassRS(child);
				else
					configureBiomassRS(child);
			}
			else if (childName.equals("FoodProcessor")){
				if (firstPass)
					createFoodProcessor(child);
				else
					configureFoodProcessor(child);
			}
			else if (childName.equals("BiomassStore")){
				if (firstPass)
					createBiomassStore(child);
			}
			else if (childName.equals("FoodStore")){
				if (firstPass)
					createFoodStore(child);
			}
			child = child.getNextSibling();
		}
	}

	private void createPowerPS(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PowerPS with moduleName: "+moduleName);
			PowerPSImpl myPowerPSImpl = null;
			if (node.getAttributes().getNamedItem("generation").getNodeValue().equals("SOLAR"))
				myPowerPSImpl = new SolarPowerPS(myID, moduleName);
			else
				myPowerPSImpl = new NuclearPowerPS(myID, moduleName);
			setupBioModule(myPowerPSImpl, node);
			mySimModules.add(OrbUtils.poaToCorbaObj(myPowerPSImpl));
			BiosimServer.registerServer(new PowerPSPOATie(myPowerPSImpl), myPowerPSImpl.getModuleName(), myPowerPSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePowerPS(Node node){
		//System.out.println("Configuring PowerPS");
		String moduleName = getModuleName(node);
		try{
			PowerPS myPowerPS = PowerPSHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			configureSimBioModule(myPowerPS, node);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
	}

	private void createPowerStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PowerStore with moduleName: "+moduleName);
			PowerStoreImpl myPowerStoreImpl = new PowerStoreImpl(myID, moduleName);
			setupBioModule(myPowerStoreImpl, node);
			myPowerStoreImpl.setLevel(getStoreLevel(node));
			myPowerStoreImpl.setCapacity(getStoreCapacity(node));
			myPowerStoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myPowerStoreImpl));
			BiosimServer.registerServer(new PowerStorePOATie(myPowerStoreImpl), myPowerStoreImpl.getModuleName(), myPowerStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void crawlPowerModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("PowerPS")){
				if (firstPass)
					createPowerPS(child);
				else
					configurePowerPS(child);
			}
			else if (childName.equals("PowerStore")){
				if (firstPass)
					createPowerStore(child);
			}
			child = child.getNextSibling();
		}
	}

	private void createWaterRS(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating WaterRS with moduleName: "+moduleName);
			WaterRSImpl myWaterRSImpl = new WaterRSImpl(myID, moduleName);
			setupBioModule(myWaterRSImpl, node);
			mySimModules.add(OrbUtils.poaToCorbaObj(myWaterRSImpl));
			BiosimServer.registerServer(new WaterRSPOATie(myWaterRSImpl), myWaterRSImpl.getModuleName(), myWaterRSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureWaterRS(Node node){
		//System.out.println("Configuring WaterRS");
		String moduleName = getModuleName(node);
		try{
			WaterRS myWaterRS = WaterRSHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			configureSimBioModule(myWaterRS, node);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
	}

	private void createPotableWaterStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PotableWaterStore with moduleName: "+moduleName);
			PotableWaterStoreImpl myPotableWaterStoreImpl = new PotableWaterStoreImpl(myID, moduleName);
			setupBioModule(myPotableWaterStoreImpl, node);
			myPotableWaterStoreImpl.setLevel(getStoreLevel(node));
			myPotableWaterStoreImpl.setCapacity(getStoreCapacity(node));
			myPotableWaterStoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myPotableWaterStoreImpl));
			BiosimServer.registerServer(new PotableWaterStorePOATie(myPotableWaterStoreImpl), myPotableWaterStoreImpl.getModuleName(), myPotableWaterStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createDirtyWaterStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating DirtyWaterStore with moduleName: "+moduleName);
			DirtyWaterStoreImpl myDirtyWaterStoreImpl = new DirtyWaterStoreImpl(myID, moduleName);
			setupBioModule(myDirtyWaterStoreImpl, node);
			myDirtyWaterStoreImpl.setLevel(getStoreLevel(node));
			myDirtyWaterStoreImpl.setCapacity(getStoreCapacity(node));
			myDirtyWaterStoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myDirtyWaterStoreImpl));
			BiosimServer.registerServer(new DirtyWaterStorePOATie(myDirtyWaterStoreImpl), myDirtyWaterStoreImpl.getModuleName(), myDirtyWaterStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createGreyWaterStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating GreyWaterStore with moduleName: "+moduleName);
			GreyWaterStoreImpl myGreyWaterStoreImpl = new GreyWaterStoreImpl(myID, moduleName);
			setupBioModule(myGreyWaterStoreImpl, node);
			myGreyWaterStoreImpl.setLevel(getStoreLevel(node));
			myGreyWaterStoreImpl.setCapacity(getStoreCapacity(node));
			myGreyWaterStoreImpl.setResupply(getStoreResupplyFrequency(node), getStoreResupplyAmount(node));
			mySimModules.add(OrbUtils.poaToCorbaObj(myGreyWaterStoreImpl));
			BiosimServer.registerServer(new GreyWaterStorePOATie(myGreyWaterStoreImpl), myGreyWaterStoreImpl.getModuleName(), myGreyWaterStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void crawlWaterModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("WaterRS")){
				if (firstPass)
					createWaterRS(child);
				else
					configureWaterRS(child);
			}
			else if (childName.equals("PotableWaterStore")){
				if (firstPass)
					createPotableWaterStore(child);
			}
			else if (childName.equals("GreyWaterStore")){
				if (firstPass)
					createGreyWaterStore(child);
			}
			else if (childName.equals("DirtyWaterStore")){
				if (firstPass)
					createDirtyWaterStore(child);
			}
			child = child.getNextSibling();
		}
	}

	//Sensors
	private void crawlSensors(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("air")){
				crawlAirSensors(child, firstPass);

			}
			else if (childName.equals("crew")){
				crawlCrewSensors(child, firstPass);

			}
			else if (childName.equals("environment")){
				crawlEnvironmentSensors(child, firstPass);

			}
			else if (childName.equals("food")){
				crawlFoodSensors(child, firstPass);

			}
			else if (childName.equals("framework")){
				crawlFrameworkSensors(child, firstPass);

			}
			else if (childName.equals("power")){
				crawlPowerSensors(child, firstPass);

			}
			else if (childName.equals("water")){
				crawlWaterSensors(child, firstPass);

			}
			child = child.getNextSibling();
		}
	}

	//Air
	private void createCO2InFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2InFlowRateSensor with moduleName: "+moduleName);
			CO2InFlowRateSensorImpl myCO2InFlowRateSensorImpl = new CO2InFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myCO2InFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCO2InFlowRateSensorImpl));
			BiosimServer.registerServer(new CO2InFlowRateSensorPOATie(myCO2InFlowRateSensorImpl), myCO2InFlowRateSensorImpl.getModuleName(), myCO2InFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2InFlowRateSensor(Node node){
		//System.out.println("Configuring CO2InFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			CO2InFlowRateSensor myCO2InFlowRateSensor = CO2InFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2InFlowRateSensor.setInput(CO2ConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2OutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2OutFlowRateSensor with moduleName: "+moduleName);
			CO2OutFlowRateSensorImpl myCO2OutFlowRateSensorImpl = new CO2OutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myCO2OutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCO2OutFlowRateSensorImpl));
			BiosimServer.registerServer(new CO2OutFlowRateSensorPOATie(myCO2OutFlowRateSensorImpl), myCO2OutFlowRateSensorImpl.getModuleName(), myCO2OutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2OutFlowRateSensor(Node node){
		//System.out.println("Configuring CO2OutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			CO2OutFlowRateSensor myCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2OutFlowRateSensor.setInput(CO2ProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2StoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2StoreLevelSensor with moduleName: "+moduleName);
			CO2StoreLevelSensorImpl myCO2StoreLevelSensorImpl = new CO2StoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myCO2StoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCO2StoreLevelSensorImpl));
			BiosimServer.registerServer(new CO2StoreLevelSensorPOATie(myCO2StoreLevelSensorImpl), myCO2StoreLevelSensorImpl.getModuleName(), myCO2StoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2StoreLevelSensor(Node node){
		//System.out.println("Configuring CO2StoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			CO2StoreLevelSensor myCO2StoreLevelSensor = CO2StoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myCO2StoreLevelSensor.setInput(CO2StoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2InFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2InFlowRateSensor with moduleName: "+moduleName);
			O2InFlowRateSensorImpl myO2InFlowRateSensorImpl = new O2InFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myO2InFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myO2InFlowRateSensorImpl));
			BiosimServer.registerServer(new O2InFlowRateSensorPOATie(myO2InFlowRateSensorImpl), myO2InFlowRateSensorImpl.getModuleName(), myO2InFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2InFlowRateSensor(Node node){
		//System.out.println("Configuring O2InFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			O2InFlowRateSensor myO2InFlowRateSensor = O2InFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2InFlowRateSensor.setInput(O2ConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2OutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2OutFlowRateSensor with moduleName: "+moduleName);
			O2OutFlowRateSensorImpl myO2OutFlowRateSensorImpl = new O2OutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myO2OutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myO2OutFlowRateSensorImpl));
			BiosimServer.registerServer(new O2OutFlowRateSensorPOATie(myO2OutFlowRateSensorImpl), myO2OutFlowRateSensorImpl.getModuleName(), myO2OutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2OutFlowRateSensor(Node node){
		//System.out.println("Configuring O2OutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			O2OutFlowRateSensor myO2OutFlowRateSensor = O2OutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2OutFlowRateSensor.setInput(O2ProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2StoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2StoreLevelSensor with moduleName: "+moduleName);
			O2StoreLevelSensorImpl myO2StoreLevelSensorImpl = new O2StoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myO2StoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myO2StoreLevelSensorImpl));
			BiosimServer.registerServer(new O2StoreLevelSensorPOATie(myO2StoreLevelSensorImpl), myO2StoreLevelSensorImpl.getModuleName(), myO2StoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2StoreLevelSensor(Node node){
		//System.out.println("Configuring O2StoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			O2StoreLevelSensor myO2StoreLevelSensor = O2StoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myO2StoreLevelSensor.setInput(O2StoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createH2InFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating H2InFlowRateSensor with moduleName: "+moduleName);
			H2InFlowRateSensorImpl myH2InFlowRateSensorImpl = new H2InFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myH2InFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myH2InFlowRateSensorImpl));
			BiosimServer.registerServer(new H2InFlowRateSensorPOATie(myH2InFlowRateSensorImpl), myH2InFlowRateSensorImpl.getModuleName(), myH2InFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureH2InFlowRateSensor(Node node){
		//System.out.println("Configuring H2InFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			H2InFlowRateSensor myH2InFlowRateSensor = H2InFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myH2InFlowRateSensor.setInput(H2ConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createH2OutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating H2OutFlowRateSensor with moduleName: "+moduleName);
			H2OutFlowRateSensorImpl myH2OutFlowRateSensorImpl = new H2OutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myH2OutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myH2OutFlowRateSensorImpl));
			BiosimServer.registerServer(new H2OutFlowRateSensorPOATie(myH2OutFlowRateSensorImpl), myH2OutFlowRateSensorImpl.getModuleName(), myH2OutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureH2OutFlowRateSensor(Node node){
		//System.out.println("Configuring H2OutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			H2OutFlowRateSensor myH2OutFlowRateSensor = H2OutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myH2OutFlowRateSensor.setInput(H2ProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createH2StoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating H2StoreLevelSensor with moduleName: "+moduleName);
			H2StoreLevelSensorImpl myH2StoreLevelSensorImpl = new H2StoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myH2StoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myH2StoreLevelSensorImpl));
			BiosimServer.registerServer(new H2StoreLevelSensorPOATie(myH2StoreLevelSensorImpl), myH2StoreLevelSensorImpl.getModuleName(), myH2StoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureH2StoreLevelSensor(Node node){
		//System.out.println("Configuring H2StoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			H2StoreLevelSensor myH2StoreLevelSensor = H2StoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myH2StoreLevelSensor.setInput(H2StoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlAirSensors(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("CO2InFlowRateSensor")){
				if (firstPass)
					createCO2InFlowRateSensor(child);
				else
					configureCO2InFlowRateSensor(child);
			}
			else if (childName.equals("CO2OutFlowRateSensor")){
				if (firstPass)
					createCO2OutFlowRateSensor(child);
				else
					configureCO2OutFlowRateSensor(child);
			}
			else if (childName.equals("CO2StoreLevelSensor")){
				if (firstPass)
					createCO2StoreLevelSensor(child);
				else
					configureCO2StoreLevelSensor(child);
			}
			else if (childName.equals("O2InFlowRateSensor")){
				if (firstPass)
					createO2InFlowRateSensor(child);
				else
					configureO2InFlowRateSensor(child);
			}
			else if (childName.equals("O2OutFlowRateSensor")){
				if (firstPass)
					createO2OutFlowRateSensor(child);
				else
					configureO2OutFlowRateSensor(child);
			}
			else if (childName.equals("O2StoreLevelSensor")){
				if (firstPass)
					createO2StoreLevelSensor(child);
				else
					configureO2StoreLevelSensor(child);
			}
			else if (childName.equals("H2InFlowRateSensor")){
				if (firstPass)
					createH2InFlowRateSensor(child);
				else
					configureH2InFlowRateSensor(child);
			}
			else if (childName.equals("H2OutFlowRateSensor")){
				if (firstPass)
					createH2OutFlowRateSensor(child);
				else
					configureH2OutFlowRateSensor(child);
			}
			else if (childName.equals("H2StoreLevelSensor")){
				if (firstPass)
					createH2StoreLevelSensor(child);
				else
					configureH2StoreLevelSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	//Crew
	private void createCrewGroupDeathSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CrewGroupDeathSensor with moduleName: "+moduleName);
			CrewGroupDeathSensorImpl myCrewGroupDeathSensorImpl = new CrewGroupDeathSensorImpl(myID, moduleName);
			setupBioModule(myCrewGroupDeathSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCrewGroupDeathSensorImpl));
			BiosimServer.registerServer(new CrewGroupDeathSensorPOATie(myCrewGroupDeathSensorImpl), myCrewGroupDeathSensorImpl.getModuleName(), myCrewGroupDeathSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCrewGroupDeathSensor(Node node){
		//System.out.println("Configuring CrewGroupDeathSensor");
		String moduleName = getModuleName(node);
		try{
			CrewGroupDeathSensor myCrewGroupDeathSensor = CrewGroupDeathSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myCrewGroupDeathSensor.setInput(CrewGroupHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlCrewSensors(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("CrewGroupDeathSensor")){
				if (firstPass)
					createCrewGroupDeathSensor(child);
				else
					configureCrewGroupDeathSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	//Environment
	private void createAirInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating AirInFlowRateSensor with moduleName: "+moduleName);
			AirInFlowRateSensorImpl myAirInFlowRateSensorImpl = new AirInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myAirInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myAirInFlowRateSensorImpl));
			BiosimServer.registerServer(new AirInFlowRateSensorPOATie(myAirInFlowRateSensorImpl), myAirInFlowRateSensorImpl.getModuleName(), myAirInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAirInFlowRateSensor(Node node){
		//System.out.println("Configuring AirInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			AirInFlowRateSensor myAirInFlowRateSensor = AirInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myAirInFlowRateSensor.setInput(AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createAirOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating AirOutFlowRateSensor with moduleName: "+moduleName);
			AirOutFlowRateSensorImpl myAirOutFlowRateSensorImpl = new AirOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myAirOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myAirOutFlowRateSensorImpl));
			BiosimServer.registerServer(new AirOutFlowRateSensorPOATie(myAirOutFlowRateSensorImpl), myAirOutFlowRateSensorImpl.getModuleName(), myAirOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAirOutFlowRateSensor(Node node){
		//System.out.println("Configuring AirOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			AirOutFlowRateSensor myAirOutFlowRateSensor = AirOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myAirOutFlowRateSensor.setInput(AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirConcentrationSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirConcentrationSensor with moduleName: "+moduleName);
			CO2AirConcentrationSensorImpl myCO2AirConcentrationSensorImpl = new CO2AirConcentrationSensorImpl(myID, moduleName);
			setupBioModule(myCO2AirConcentrationSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCO2AirConcentrationSensorImpl));
			BiosimServer.registerServer(new CO2AirConcentrationSensorPOATie(myCO2AirConcentrationSensorImpl), myCO2AirConcentrationSensorImpl.getModuleName(), myCO2AirConcentrationSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirConcentrationSensor(Node node){
		//System.out.println("Configuring CO2AirConcentrationSensor");
		String moduleName = getModuleName(node);
		try{
			CO2AirConcentrationSensor myCO2AirConcentrationSensor = CO2AirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myCO2AirConcentrationSensor.setInput(SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirPressureSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirPressureSensor with moduleName: "+moduleName);
			CO2AirPressureSensorImpl myCO2AirPressureSensorImpl = new CO2AirPressureSensorImpl(myID, moduleName);
			setupBioModule(myCO2AirPressureSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCO2AirPressureSensorImpl));
			BiosimServer.registerServer(new CO2AirPressureSensorPOATie(myCO2AirPressureSensorImpl), myCO2AirPressureSensorImpl.getModuleName(), myCO2AirPressureSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirPressureSensor(Node node){
		//System.out.println("Configuring CO2AirPressureSensor");
		String moduleName = getModuleName(node);
		try{
			CO2AirPressureSensor myCO2AirPressureSensor = CO2AirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myCO2AirPressureSensor.setInput(SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirEnvironmentInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirEnvironmentInFlowRateSensor with moduleName: "+moduleName);
			CO2AirEnvironmentInFlowRateSensorImpl myCO2AirEnvironmentInFlowRateSensorImpl = new CO2AirEnvironmentInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myCO2AirEnvironmentInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCO2AirEnvironmentInFlowRateSensorImpl));
			BiosimServer.registerServer(new CO2AirEnvironmentInFlowRateSensorPOATie(myCO2AirEnvironmentInFlowRateSensorImpl), myCO2AirEnvironmentInFlowRateSensorImpl.getModuleName(), myCO2AirEnvironmentInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirEnvironmentInFlowRateSensor(Node node){
		//System.out.println("Configuring CO2AirEnvironmentInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			CO2AirEnvironmentInFlowRateSensor myCO2AirEnvironmentInFlowRateSensor = CO2AirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2AirEnvironmentInFlowRateSensor.setInput(CO2AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirEnvironmentOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirEnvironmentOutFlowRateSensor with moduleName: "+moduleName);
			CO2AirEnvironmentOutFlowRateSensorImpl myCO2AirEnvironmentOutFlowRateSensorImpl = new CO2AirEnvironmentOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myCO2AirEnvironmentOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCO2AirEnvironmentOutFlowRateSensorImpl));
			BiosimServer.registerServer(new CO2AirEnvironmentOutFlowRateSensorPOATie(myCO2AirEnvironmentOutFlowRateSensorImpl), myCO2AirEnvironmentOutFlowRateSensorImpl.getModuleName(), myCO2AirEnvironmentOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirEnvironmentOutFlowRateSensor(Node node){
		//System.out.println("Configuring CO2AirEnvironmentOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			CO2AirEnvironmentOutFlowRateSensor myCO2AirEnvironmentOutFlowRateSensor = CO2AirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2AirEnvironmentOutFlowRateSensor.setInput(CO2AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirStoreInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirStoreInFlowRateSensor with moduleName: "+moduleName);
			CO2AirStoreInFlowRateSensorImpl myCO2AirStoreInFlowRateSensorImpl = new CO2AirStoreInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myCO2AirStoreInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCO2AirStoreInFlowRateSensorImpl));
			BiosimServer.registerServer(new CO2AirStoreInFlowRateSensorPOATie(myCO2AirStoreInFlowRateSensorImpl), myCO2AirStoreInFlowRateSensorImpl.getModuleName(), myCO2AirStoreInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirStoreInFlowRateSensor(Node node){
		//System.out.println("Configuring CO2AirStoreInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			CO2AirStoreInFlowRateSensor myCO2AirStoreInFlowRateSensor = CO2AirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2AirStoreInFlowRateSensor.setInput(CO2AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirStoreOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirStoreOutFlowRateSensor with moduleName: "+moduleName);
			CO2AirStoreOutFlowRateSensorImpl myCO2AirStoreOutFlowRateSensorImpl = new CO2AirStoreOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myCO2AirStoreOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myCO2AirStoreOutFlowRateSensorImpl));
			BiosimServer.registerServer(new CO2AirStoreOutFlowRateSensorPOATie(myCO2AirStoreOutFlowRateSensorImpl), myCO2AirStoreOutFlowRateSensorImpl.getModuleName(), myCO2AirStoreOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirStoreOutFlowRateSensor(Node node){
		//System.out.println("Configuring CO2AirStoreOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			CO2AirStoreOutFlowRateSensor myCO2AirStoreOutFlowRateSensor = CO2AirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2AirStoreOutFlowRateSensor.setInput(CO2AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirConcentrationSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirConcentrationSensor with moduleName: "+moduleName);
			O2AirConcentrationSensorImpl myO2AirConcentrationSensorImpl = new O2AirConcentrationSensorImpl(myID, moduleName);
			setupBioModule(myO2AirConcentrationSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myO2AirConcentrationSensorImpl));
			BiosimServer.registerServer(new O2AirConcentrationSensorPOATie(myO2AirConcentrationSensorImpl), myO2AirConcentrationSensorImpl.getModuleName(), myO2AirConcentrationSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirConcentrationSensor(Node node){
		//System.out.println("Configuring O2AirConcentrationSensor");
		String moduleName = getModuleName(node);
		try{
			O2AirConcentrationSensor myO2AirConcentrationSensor = O2AirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myO2AirConcentrationSensor.setInput(SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirPressureSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirPressureSensor with moduleName: "+moduleName);
			O2AirPressureSensorImpl myO2AirPressureSensorImpl = new O2AirPressureSensorImpl(myID, moduleName);
			setupBioModule(myO2AirPressureSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myO2AirPressureSensorImpl));
			BiosimServer.registerServer(new O2AirPressureSensorPOATie(myO2AirPressureSensorImpl), myO2AirPressureSensorImpl.getModuleName(), myO2AirPressureSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirPressureSensor(Node node){
		//System.out.println("Configuring O2AirPressureSensor");
		String moduleName = getModuleName(node);
		try{
			O2AirPressureSensor myO2AirPressureSensor = O2AirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myO2AirPressureSensor.setInput(SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirEnvironmentInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirEnvironmentInFlowRateSensor with moduleName: "+moduleName);
			O2AirEnvironmentInFlowRateSensorImpl myO2AirEnvironmentInFlowRateSensorImpl = new O2AirEnvironmentInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myO2AirEnvironmentInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myO2AirEnvironmentInFlowRateSensorImpl));
			BiosimServer.registerServer(new O2AirEnvironmentInFlowRateSensorPOATie(myO2AirEnvironmentInFlowRateSensorImpl), myO2AirEnvironmentInFlowRateSensorImpl.getModuleName(), myO2AirEnvironmentInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirEnvironmentInFlowRateSensor(Node node){
		//System.out.println("Configuring O2AirEnvironmentInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			O2AirEnvironmentInFlowRateSensor myO2AirEnvironmentInFlowRateSensor = O2AirEnvironmentInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2AirEnvironmentInFlowRateSensor.setInput(O2AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirEnvironmentOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirEnvironmentOutFlowRateSensor with moduleName: "+moduleName);
			O2AirEnvironmentOutFlowRateSensorImpl myO2AirEnvironmentOutFlowRateSensorImpl = new O2AirEnvironmentOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myO2AirEnvironmentOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myO2AirEnvironmentOutFlowRateSensorImpl));
			BiosimServer.registerServer(new O2AirEnvironmentOutFlowRateSensorPOATie(myO2AirEnvironmentOutFlowRateSensorImpl), myO2AirEnvironmentOutFlowRateSensorImpl.getModuleName(), myO2AirEnvironmentOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirEnvironmentOutFlowRateSensor(Node node){
		//System.out.println("Configuring O2AirEnvironmentOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			O2AirEnvironmentOutFlowRateSensor myO2AirEnvironmentOutFlowRateSensor = O2AirEnvironmentOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2AirEnvironmentOutFlowRateSensor.setInput(O2AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirStoreInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirStoreInFlowRateSensor with moduleName: "+moduleName);
			O2AirStoreInFlowRateSensorImpl myO2AirStoreInFlowRateSensorImpl = new O2AirStoreInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myO2AirStoreInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myO2AirStoreInFlowRateSensorImpl));
			BiosimServer.registerServer(new O2AirStoreInFlowRateSensorPOATie(myO2AirStoreInFlowRateSensorImpl), myO2AirStoreInFlowRateSensorImpl.getModuleName(), myO2AirStoreInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirStoreInFlowRateSensor(Node node){
		//System.out.println("Configuring O2AirStoreInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			O2AirStoreInFlowRateSensor myO2AirStoreInFlowRateSensor = O2AirStoreInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2AirStoreInFlowRateSensor.setInput(O2AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirStoreOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirStoreOutFlowRateSensor with moduleName: "+moduleName);
			O2AirStoreOutFlowRateSensorImpl myO2AirStoreOutFlowRateSensorImpl = new O2AirStoreOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myO2AirStoreOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myO2AirStoreOutFlowRateSensorImpl));
			BiosimServer.registerServer(new O2AirStoreOutFlowRateSensorPOATie(myO2AirStoreOutFlowRateSensorImpl), myO2AirStoreOutFlowRateSensorImpl.getModuleName(), myO2AirStoreOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirStoreOutFlowRateSensor(Node node){
		//System.out.println("Configuring O2AirStoreOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			O2AirStoreOutFlowRateSensor myO2AirStoreOutFlowRateSensor = O2AirStoreOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2AirStoreOutFlowRateSensor.setInput(O2AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createOtherAirConcentrationSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating OtherAirConcentrationSensor with moduleName: "+moduleName);
			OtherAirConcentrationSensorImpl myOtherAirConcentrationSensorImpl = new OtherAirConcentrationSensorImpl(myID, moduleName);
			setupBioModule(myOtherAirConcentrationSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myOtherAirConcentrationSensorImpl));
			BiosimServer.registerServer(new OtherAirConcentrationSensorPOATie(myOtherAirConcentrationSensorImpl), myOtherAirConcentrationSensorImpl.getModuleName(), myOtherAirConcentrationSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureOtherAirConcentrationSensor(Node node){
		//System.out.println("Configuring OtherAirConcentrationSensor");
		String moduleName = getModuleName(node);
		try{
			OtherAirConcentrationSensor myOtherAirConcentrationSensor = OtherAirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myOtherAirConcentrationSensor.setInput(SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createOtherAirPressureSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating OtherAirPressureSensor with moduleName: "+moduleName);
			OtherAirPressureSensorImpl myOtherAirPressureSensorImpl = new OtherAirPressureSensorImpl(myID, moduleName);
			setupBioModule(myOtherAirPressureSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myOtherAirPressureSensorImpl));
			BiosimServer.registerServer(new OtherAirPressureSensorPOATie(myOtherAirPressureSensorImpl), myOtherAirPressureSensorImpl.getModuleName(), myOtherAirPressureSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureOtherAirPressureSensor(Node node){
		//System.out.println("Configuring OtherAirPressureSensor");
		String moduleName = getModuleName(node);
		try{
			OtherAirPressureSensor myOtherAirPressureSensor = OtherAirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myOtherAirPressureSensor.setInput(SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createWaterAirConcentrationSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating WaterAirConcentrationSensor with moduleName: "+moduleName);
			WaterAirConcentrationSensorImpl myWaterAirConcentrationSensorImpl = new WaterAirConcentrationSensorImpl(myID, moduleName);
			setupBioModule(myWaterAirConcentrationSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myWaterAirConcentrationSensorImpl));
			BiosimServer.registerServer(new WaterAirConcentrationSensorPOATie(myWaterAirConcentrationSensorImpl), myWaterAirConcentrationSensorImpl.getModuleName(), myWaterAirConcentrationSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureWaterAirConcentrationSensor(Node node){
		//System.out.println("Configuring WaterAirConcentrationSensor");
		String moduleName = getModuleName(node);
		try{
			WaterAirConcentrationSensor myWaterAirConcentrationSensor = WaterAirConcentrationSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myWaterAirConcentrationSensor.setInput(SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createWaterAirPressureSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating WaterAirPressureSensor with moduleName: "+moduleName);
			WaterAirPressureSensorImpl myWaterAirPressureSensorImpl = new WaterAirPressureSensorImpl(myID, moduleName);
			setupBioModule(myWaterAirPressureSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myWaterAirPressureSensorImpl));
			BiosimServer.registerServer(new WaterAirPressureSensorPOATie(myWaterAirPressureSensorImpl), myWaterAirPressureSensorImpl.getModuleName(), myWaterAirPressureSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureWaterAirPressureSensor(Node node){
		//System.out.println("Configuring WaterAirPressureSensor");
		String moduleName = getModuleName(node);
		try{
			WaterAirPressureSensor myWaterAirPressureSensor = WaterAirPressureSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myWaterAirPressureSensor.setInput(SimEnvironmentHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlEnvironmentSensors(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("AirInFlowRateSensor")){
				if (firstPass)
					createAirInFlowRateSensor(child);
				else
					configureAirInFlowRateSensor(child);
			}
			else if (childName.equals("AirOutFlowRateSensor")){
				if (firstPass)
					createAirOutFlowRateSensor(child);
				else
					configureAirOutFlowRateSensor(child);
			}
			else if (childName.equals("CO2AirConcentrationSensor")){
				if (firstPass)
					createCO2AirConcentrationSensor(child);
				else
					configureCO2AirConcentrationSensor(child);
			}
			else if (childName.equals("CO2AirEnvironmentInFlowRateSensor")){
				if (firstPass)
					createCO2AirEnvironmentInFlowRateSensor(child);
				else
					configureCO2AirEnvironmentInFlowRateSensor(child);
			}
			else if (childName.equals("CO2AirEnvironmentOutFlowRateSensor")){
				if (firstPass)
					createCO2AirEnvironmentOutFlowRateSensor(child);
				else
					configureCO2AirEnvironmentOutFlowRateSensor(child);
			}
			else if (childName.equals("CO2AirPressureSensor")){
				if (firstPass)
					createCO2AirPressureSensor(child);
				else
					configureCO2AirPressureSensor(child);
			}
			else if (childName.equals("CO2AirStoreInFlowRateSensor")){
				if (firstPass)
					createCO2AirStoreInFlowRateSensor(child);
				else
					configureCO2AirStoreInFlowRateSensor(child);
			}
			else if (childName.equals("CO2AirStoreOutFlowRateSensor")){
				if (firstPass)
					createCO2AirStoreOutFlowRateSensor(child);
				else
					configureCO2AirStoreOutFlowRateSensor(child);
			}
			else if (childName.equals("O2AirConcentrationSensor")){
				if (firstPass)
					createO2AirConcentrationSensor(child);
				else
					configureO2AirConcentrationSensor(child);
			}
			else if (childName.equals("O2AirEnvironmentInFlowRateSensor")){
				if (firstPass)
					createO2AirEnvironmentInFlowRateSensor(child);
				else
					configureO2AirEnvironmentInFlowRateSensor(child);
			}
			else if (childName.equals("O2AirEnvironmentOutFlowRateSensor")){
				if (firstPass)
					createO2AirEnvironmentOutFlowRateSensor(child);
				else
					configureO2AirEnvironmentOutFlowRateSensor(child);
			}
			else if (childName.equals("O2AirPressureSensor")){
				if (firstPass)
					createO2AirPressureSensor(child);
				else
					configureO2AirPressureSensor(child);
			}
			else if (childName.equals("O2AirStoreInFlowRateSensor")){
				if (firstPass)
					createO2AirStoreInFlowRateSensor(child);
				else
					configureO2AirStoreInFlowRateSensor(child);
			}
			else if (childName.equals("O2AirStoreOutFlowRateSensor")){
				if (firstPass)
					createO2AirStoreOutFlowRateSensor(child);
				else
					configureO2AirStoreOutFlowRateSensor(child);
			}
			else if (childName.equals("OtherAirConcentrationSensor")){
				if (firstPass)
					createOtherAirConcentrationSensor(child);
				else
					configureOtherAirConcentrationSensor(child);
			}
			else if (childName.equals("OtherAirPressureSensor")){
				if (firstPass)
					createOtherAirPressureSensor(child);
				else
					configureOtherAirPressureSensor(child);
			}
			else if (childName.equals("WaterAirConcentrationSensor")){
				if (firstPass)
					createWaterAirConcentrationSensor(child);
				else
					configureWaterAirConcentrationSensor(child);
			}
			else if (childName.equals("WaterAirPressureSensor")){
				if (firstPass)
					createWaterAirPressureSensor(child);
				else
					configureWaterAirPressureSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	//Food
	private void createBiomassInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating BiomassInFlowRateSensor with moduleName: "+moduleName);
			BiomassInFlowRateSensorImpl myBiomassInFlowRateSensorImpl = new BiomassInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myBiomassInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myBiomassInFlowRateSensorImpl));
			BiosimServer.registerServer(new BiomassInFlowRateSensorPOATie(myBiomassInFlowRateSensorImpl), myBiomassInFlowRateSensorImpl.getModuleName(), myBiomassInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassInFlowRateSensor(Node node){
		//System.out.println("Configuring BiomassInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			BiomassInFlowRateSensor myBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myBiomassInFlowRateSensor.setInput(BiomassConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createBiomassOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating BiomassOutFlowRateSensor with moduleName: "+moduleName);
			BiomassOutFlowRateSensorImpl myBiomassOutFlowRateSensorImpl = new BiomassOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myBiomassOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myBiomassOutFlowRateSensorImpl));
			BiosimServer.registerServer(new BiomassOutFlowRateSensorPOATie(myBiomassOutFlowRateSensorImpl), myBiomassOutFlowRateSensorImpl.getModuleName(), myBiomassOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassOutFlowRateSensor(Node node){
		//System.out.println("Configuring BiomassOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			BiomassOutFlowRateSensor myBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myBiomassOutFlowRateSensor.setInput(BiomassProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createBiomassStoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating BiomassStoreLevelSensor with moduleName: "+moduleName);
			BiomassStoreLevelSensorImpl myBiomassStoreLevelSensorImpl = new BiomassStoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myBiomassStoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myBiomassStoreLevelSensorImpl));
			BiosimServer.registerServer(new BiomassStoreLevelSensorPOATie(myBiomassStoreLevelSensorImpl), myBiomassStoreLevelSensorImpl.getModuleName(), myBiomassStoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassStoreLevelSensor(Node node){
		//System.out.println("Configuring BiomassStoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			BiomassStoreLevelSensor myBiomassStoreLevelSensor = BiomassStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myBiomassStoreLevelSensor.setInput(BiomassStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createFoodInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating FoodInFlowRateSensor with moduleName: "+moduleName);
			FoodInFlowRateSensorImpl myFoodInFlowRateSensorImpl = new FoodInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myFoodInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myFoodInFlowRateSensorImpl));
			BiosimServer.registerServer(new FoodInFlowRateSensorPOATie(myFoodInFlowRateSensorImpl), myFoodInFlowRateSensorImpl.getModuleName(), myFoodInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureFoodInFlowRateSensor(Node node){
		//System.out.println("Configuring FoodInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			FoodInFlowRateSensor myFoodInFlowRateSensor = FoodInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myFoodInFlowRateSensor.setInput(FoodConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createFoodOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating FoodOutFlowRateSensor with moduleName: "+moduleName);
			FoodOutFlowRateSensorImpl myFoodOutFlowRateSensorImpl = new FoodOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myFoodOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myFoodOutFlowRateSensorImpl));
			BiosimServer.registerServer(new FoodOutFlowRateSensorPOATie(myFoodOutFlowRateSensorImpl), myFoodOutFlowRateSensorImpl.getModuleName(), myFoodOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureFoodOutFlowRateSensor(Node node){
		//System.out.println("Configuring FoodOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			FoodOutFlowRateSensor myFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myFoodOutFlowRateSensor.setInput(FoodProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createFoodStoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating FoodStoreLevelSensor with moduleName: "+moduleName);
			FoodStoreLevelSensorImpl myFoodStoreLevelSensorImpl = new FoodStoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myFoodStoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myFoodStoreLevelSensorImpl));
			BiosimServer.registerServer(new FoodStoreLevelSensorPOATie(myFoodStoreLevelSensorImpl), myFoodStoreLevelSensorImpl.getModuleName(), myFoodStoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureFoodStoreLevelSensor(Node node){
		//System.out.println("Configuring FoodStoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			FoodStoreLevelSensor myFoodStoreLevelSensor = FoodStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myFoodStoreLevelSensor.setInput(FoodStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlFoodSensors(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("BiomassInFlowRateSensor")){
				if (firstPass)
					createBiomassInFlowRateSensor(child);
				else
					configureBiomassInFlowRateSensor(child);
			}
			else if (childName.equals("BiomassOutFlowRateSensor")){
				if (firstPass)
					createBiomassOutFlowRateSensor(child);
				else
					configureBiomassOutFlowRateSensor(child);
			}
			else if (childName.equals("BiomassStoreLevelSensor")){
				if (firstPass)
					createBiomassStoreLevelSensor(child);
				else
					configureBiomassStoreLevelSensor(child);
			}
			else if (childName.equals("FoodInFlowRateSensor")){
				if (firstPass)
					createFoodInFlowRateSensor(child);
				else
					configureFoodInFlowRateSensor(child);
			}
			else if (childName.equals("FoodOutFlowRateSensor")){
				if (firstPass)
					createFoodOutFlowRateSensor(child);
				else
					configureFoodOutFlowRateSensor(child);
			}
			else if (childName.equals("FoodStoreLevelSensor")){
				if (firstPass)
					createFoodStoreLevelSensor(child);
				else
					configureFoodStoreLevelSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	//Framework
	private void createStoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating StoreLevelSensor with moduleName: "+moduleName);
			StoreLevelSensorImpl myStoreLevelSensorImpl = new StoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myStoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myStoreLevelSensorImpl));
			BiosimServer.registerServer(new StoreLevelSensorPOATie(myStoreLevelSensorImpl), myStoreLevelSensorImpl.getModuleName(), myStoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureStoreLevelSensor(Node node){
		//System.out.println("Configuring StoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			StoreLevelSensor myStoreLevelSensor = StoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myStoreLevelSensor.setInput(StoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createStoreOverflowSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating StoreOverflowSensor with moduleName: "+moduleName);
			StoreOverflowSensorImpl myStoreOverflowSensorImpl = new StoreOverflowSensorImpl(myID, moduleName);
			setupBioModule(myStoreOverflowSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myStoreOverflowSensorImpl));
			BiosimServer.registerServer(new StoreOverflowSensorPOATie(myStoreOverflowSensorImpl), myStoreOverflowSensorImpl.getModuleName(), myStoreOverflowSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureStoreOverflowSensor(Node node){
		//System.out.println("Configuring StoreOverflowSensor");
		String moduleName = getModuleName(node);
		try{
			StoreOverflowSensor myStoreOverflowSensor = StoreOverflowSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myStoreOverflowSensor.setInput(StoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlFrameworkSensors(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("StoreLevelSensor")){
				if (firstPass)
					createStoreLevelSensor(child);
				else
					configureStoreLevelSensor(child);
			}
			else if (childName.equals("StoreOverflowSensor")){
				if (firstPass)
					createStoreOverflowSensor(child);
				else
					configureStoreOverflowSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	//Power
	private void createPowerInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PowerInFlowRateSensor with moduleName: "+moduleName);
			PowerInFlowRateSensorImpl myPowerInFlowRateSensorImpl = new PowerInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myPowerInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myPowerInFlowRateSensorImpl));
			BiosimServer.registerServer(new PowerInFlowRateSensorPOATie(myPowerInFlowRateSensorImpl), myPowerInFlowRateSensorImpl.getModuleName(), myPowerInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePowerInFlowRateSensor(Node node){
		//System.out.println("Configuring PowerInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			PowerInFlowRateSensor myPowerInFlowRateSensor = PowerInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myPowerInFlowRateSensor.setInput(PowerConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createPowerOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PowerOutFlowRateSensor with moduleName: "+moduleName);
			PowerOutFlowRateSensorImpl myPowerOutFlowRateSensorImpl = new PowerOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myPowerOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myPowerOutFlowRateSensorImpl));
			BiosimServer.registerServer(new PowerOutFlowRateSensorPOATie(myPowerOutFlowRateSensorImpl), myPowerOutFlowRateSensorImpl.getModuleName(), myPowerOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePowerOutFlowRateSensor(Node node){
		//System.out.println("Configuring PowerOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			PowerOutFlowRateSensor myPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myPowerOutFlowRateSensor.setInput(PowerProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createPowerStoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PowerStoreLevelSensor with moduleName: "+moduleName);
			PowerStoreLevelSensorImpl myPowerStoreLevelSensorImpl = new PowerStoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myPowerStoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myPowerStoreLevelSensorImpl));
			BiosimServer.registerServer(new PowerStoreLevelSensorPOATie(myPowerStoreLevelSensorImpl), myPowerStoreLevelSensorImpl.getModuleName(), myPowerStoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePowerStoreLevelSensor(Node node){
		//System.out.println("Configuring PowerStoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			PowerStoreLevelSensor myPowerStoreLevelSensor = PowerStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myPowerStoreLevelSensor.setInput(PowerStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlPowerSensors(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("PowerInFlowRateSensor")){
				if (firstPass)
					createPowerInFlowRateSensor(child);
				else
					configurePowerInFlowRateSensor(child);
			}
			else if (childName.equals("PowerOutFlowRateSensor")){
				if (firstPass)
					createPowerOutFlowRateSensor(child);
				else
					configurePowerOutFlowRateSensor(child);
			}
			else if (childName.equals("PowerStoreLevelSensor")){
				if (firstPass)
					createPowerStoreLevelSensor(child);
				else
					configurePowerStoreLevelSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	//Water
	private void createPotableWaterInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PotableWaterInFlowRateSensor with moduleName: "+moduleName);
			PotableWaterInFlowRateSensorImpl myPotableWaterInFlowRateSensorImpl = new PotableWaterInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myPotableWaterInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myPotableWaterInFlowRateSensorImpl));
			BiosimServer.registerServer(new PotableWaterInFlowRateSensorPOATie(myPotableWaterInFlowRateSensorImpl), myPotableWaterInFlowRateSensorImpl.getModuleName(), myPotableWaterInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePotableWaterInFlowRateSensor(Node node){
		//System.out.println("Configuring PotableWaterInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			PotableWaterInFlowRateSensor myPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myPotableWaterInFlowRateSensor.setInput(PotableWaterConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createPotableWaterOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PotableWaterOutFlowRateSensor with moduleName: "+moduleName);
			PotableWaterOutFlowRateSensorImpl myPotableWaterOutFlowRateSensorImpl = new PotableWaterOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myPotableWaterOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myPotableWaterOutFlowRateSensorImpl));
			BiosimServer.registerServer(new PotableWaterOutFlowRateSensorPOATie(myPotableWaterOutFlowRateSensorImpl), myPotableWaterOutFlowRateSensorImpl.getModuleName(), myPotableWaterOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePotableWaterOutFlowRateSensor(Node node){
		//System.out.println("Configuring PotableWaterOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			PotableWaterOutFlowRateSensor myPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myPotableWaterOutFlowRateSensor.setInput(PotableWaterProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createPotableWaterStoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PotableWaterStoreLevelSensor with moduleName: "+moduleName);
			PotableWaterStoreLevelSensorImpl myPotableWaterStoreLevelSensorImpl = new PotableWaterStoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myPotableWaterStoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myPotableWaterStoreLevelSensorImpl));
			BiosimServer.registerServer(new PotableWaterStoreLevelSensorPOATie(myPotableWaterStoreLevelSensorImpl), myPotableWaterStoreLevelSensorImpl.getModuleName(), myPotableWaterStoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePotableWaterStoreLevelSensor(Node node){
		//System.out.println("Configuring PotableWaterStoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			PotableWaterStoreLevelSensor myPotableWaterStoreLevelSensor = PotableWaterStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myPotableWaterStoreLevelSensor.setInput(PotableWaterStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createGreyWaterInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating GreyWaterInFlowRateSensor with moduleName: "+moduleName);
			GreyWaterInFlowRateSensorImpl myGreyWaterInFlowRateSensorImpl = new GreyWaterInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myGreyWaterInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myGreyWaterInFlowRateSensorImpl));
			BiosimServer.registerServer(new GreyWaterInFlowRateSensorPOATie(myGreyWaterInFlowRateSensorImpl), myGreyWaterInFlowRateSensorImpl.getModuleName(), myGreyWaterInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureGreyWaterInFlowRateSensor(Node node){
		//System.out.println("Configuring GreyWaterInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			GreyWaterInFlowRateSensor myGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myGreyWaterInFlowRateSensor.setInput(GreyWaterConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createGreyWaterOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating GreyWaterOutFlowRateSensor with moduleName: "+moduleName);
			GreyWaterOutFlowRateSensorImpl myGreyWaterOutFlowRateSensorImpl = new GreyWaterOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myGreyWaterOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myGreyWaterOutFlowRateSensorImpl));
			BiosimServer.registerServer(new GreyWaterOutFlowRateSensorPOATie(myGreyWaterOutFlowRateSensorImpl), myGreyWaterOutFlowRateSensorImpl.getModuleName(), myGreyWaterOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureGreyWaterOutFlowRateSensor(Node node){
		//System.out.println("Configuring GreyWaterOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			GreyWaterOutFlowRateSensor myGreyWaterOutFlowRateSensor = GreyWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myGreyWaterOutFlowRateSensor.setInput(GreyWaterProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createGreyWaterStoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating GreyWaterStoreLevelSensor with moduleName: "+moduleName);
			GreyWaterStoreLevelSensorImpl myGreyWaterStoreLevelSensorImpl = new GreyWaterStoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myGreyWaterStoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myGreyWaterStoreLevelSensorImpl));
			BiosimServer.registerServer(new GreyWaterStoreLevelSensorPOATie(myGreyWaterStoreLevelSensorImpl), myGreyWaterStoreLevelSensorImpl.getModuleName(), myGreyWaterStoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureGreyWaterStoreLevelSensor(Node node){
		//System.out.println("Configuring GreyWaterStoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			GreyWaterStoreLevelSensor myGreyWaterStoreLevelSensor = GreyWaterStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myGreyWaterStoreLevelSensor.setInput(GreyWaterStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createDirtyWaterInFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating DirtyWaterInFlowRateSensor with moduleName: "+moduleName);
			DirtyWaterInFlowRateSensorImpl myDirtyWaterInFlowRateSensorImpl = new DirtyWaterInFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myDirtyWaterInFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myDirtyWaterInFlowRateSensorImpl));
			BiosimServer.registerServer(new DirtyWaterInFlowRateSensorPOATie(myDirtyWaterInFlowRateSensorImpl), myDirtyWaterInFlowRateSensorImpl.getModuleName(), myDirtyWaterInFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureDirtyWaterInFlowRateSensor(Node node){
		//System.out.println("Configuring DirtyWaterInFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			DirtyWaterInFlowRateSensor myDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myDirtyWaterInFlowRateSensor.setInput(DirtyWaterConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createDirtyWaterOutFlowRateSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating DirtyWaterOutFlowRateSensor with moduleName: "+moduleName);
			DirtyWaterOutFlowRateSensorImpl myDirtyWaterOutFlowRateSensorImpl = new DirtyWaterOutFlowRateSensorImpl(myID, moduleName);
			setupBioModule(myDirtyWaterOutFlowRateSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myDirtyWaterOutFlowRateSensorImpl));
			BiosimServer.registerServer(new DirtyWaterOutFlowRateSensorPOATie(myDirtyWaterOutFlowRateSensorImpl), myDirtyWaterOutFlowRateSensorImpl.getModuleName(), myDirtyWaterOutFlowRateSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureDirtyWaterOutFlowRateSensor(Node node){
		//System.out.println("Configuring DirtyWaterOutFlowRateSensor");
		String moduleName = getModuleName(node);
		try{
			DirtyWaterOutFlowRateSensor myDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myDirtyWaterOutFlowRateSensor.setInput(DirtyWaterProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createDirtyWaterStoreLevelSensor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating DirtyWaterStoreLevelSensor with moduleName: "+moduleName);
			DirtyWaterStoreLevelSensorImpl myDirtyWaterStoreLevelSensorImpl = new DirtyWaterStoreLevelSensorImpl(myID, moduleName);
			setupBioModule(myDirtyWaterStoreLevelSensorImpl, node);
			mySensors.add(OrbUtils.poaToCorbaObj(myDirtyWaterStoreLevelSensorImpl));
			BiosimServer.registerServer(new DirtyWaterStoreLevelSensorPOATie(myDirtyWaterStoreLevelSensorImpl), myDirtyWaterStoreLevelSensorImpl.getModuleName(), myDirtyWaterStoreLevelSensorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureDirtyWaterStoreLevelSensor(Node node){
		//System.out.println("Configuring DirtyWaterStoreLevelSensor");
		String moduleName = getModuleName(node);
		try{
			DirtyWaterStoreLevelSensor myDirtyWaterStoreLevelSensor = DirtyWaterStoreLevelSensorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("input").getNodeValue();
			myDirtyWaterStoreLevelSensor.setInput(DirtyWaterStoreHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}
	private void crawlWaterSensors(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("PotableWaterInFlowRateSensor")){
				if (firstPass)
					createPotableWaterInFlowRateSensor(child);
				else
					configurePotableWaterInFlowRateSensor(child);
			}
			else if (childName.equals("PotableWaterOutFlowRateSensor")){
				if (firstPass)
					createPotableWaterOutFlowRateSensor(child);
				else
					configurePotableWaterOutFlowRateSensor(child);
			}
			else if (childName.equals("PotableWaterStoreLevelSensor")){
				if (firstPass)
					createPotableWaterStoreLevelSensor(child);
				else
					configurePotableWaterStoreLevelSensor(child);
			}
			else if (childName.equals("GreyWaterInFlowRateSensor")){
				if (firstPass)
					createGreyWaterInFlowRateSensor(child);
				else
					configureGreyWaterInFlowRateSensor(child);
			}
			else if (childName.equals("GreyWaterOutFlowRateSensor")){
				if (firstPass)
					createGreyWaterOutFlowRateSensor(child);
				else
					configureGreyWaterOutFlowRateSensor(child);
			}
			else if (childName.equals("GreyWaterStoreLevelSensor")){
				if (firstPass)
					createGreyWaterStoreLevelSensor(child);
				else
					configureGreyWaterStoreLevelSensor(child);
			}
			else if (childName.equals("DirtyWaterInFlowRateSensor")){
				if (firstPass)
					createDirtyWaterInFlowRateSensor(child);
				else
					configureDirtyWaterInFlowRateSensor(child);
			}
			else if (childName.equals("DirtyWaterOutFlowRateSensor")){
				if (firstPass)
					createDirtyWaterOutFlowRateSensor(child);
				else
					configureDirtyWaterOutFlowRateSensor(child);
			}
			else if (childName.equals("DirtyWaterStoreLevelSensor")){
				if (firstPass)
					createDirtyWaterStoreLevelSensor(child);
				else
					configureDirtyWaterStoreLevelSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	//Actuators

	//Air
	private void createCO2InFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2InFlowRateActuator with moduleName: "+moduleName);
			CO2InFlowRateActuatorImpl myCO2InFlowRateActuatorImpl = new CO2InFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myCO2InFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myCO2InFlowRateActuatorImpl));
			BiosimServer.registerServer(new CO2InFlowRateActuatorPOATie(myCO2InFlowRateActuatorImpl), myCO2InFlowRateActuatorImpl.getModuleName(), myCO2InFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2InFlowRateActuator(Node node){
		//System.out.println("Configuring CO2InFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			CO2InFlowRateActuator myCO2InFlowRateActuator = CO2InFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2InFlowRateActuator.setOutput(CO2ConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2OutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2OutFlowRateActuator with moduleName: "+moduleName);
			CO2OutFlowRateActuatorImpl myCO2OutFlowRateActuatorImpl = new CO2OutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myCO2OutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myCO2OutFlowRateActuatorImpl));
			BiosimServer.registerServer(new CO2OutFlowRateActuatorPOATie(myCO2OutFlowRateActuatorImpl), myCO2OutFlowRateActuatorImpl.getModuleName(), myCO2OutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2OutFlowRateActuator(Node node){
		//System.out.println("Configuring CO2OutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			CO2OutFlowRateActuator myCO2OutFlowRateActuator = CO2OutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2OutFlowRateActuator.setOutput(CO2ProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2InFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2InFlowRateActuator with moduleName: "+moduleName);
			O2InFlowRateActuatorImpl myO2InFlowRateActuatorImpl = new O2InFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myO2InFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myO2InFlowRateActuatorImpl));
			BiosimServer.registerServer(new O2InFlowRateActuatorPOATie(myO2InFlowRateActuatorImpl), myO2InFlowRateActuatorImpl.getModuleName(), myO2InFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2InFlowRateActuator(Node node){
		//System.out.println("Configuring O2InFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			O2InFlowRateActuator myO2InFlowRateActuator = O2InFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2InFlowRateActuator.setOutput(O2ConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2OutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2OutFlowRateActuator with moduleName: "+moduleName);
			O2OutFlowRateActuatorImpl myO2OutFlowRateActuatorImpl = new O2OutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myO2OutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myO2OutFlowRateActuatorImpl));
			BiosimServer.registerServer(new O2OutFlowRateActuatorPOATie(myO2OutFlowRateActuatorImpl), myO2OutFlowRateActuatorImpl.getModuleName(), myO2OutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2OutFlowRateActuator(Node node){
		//System.out.println("Configuring O2OutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			O2OutFlowRateActuator myO2OutFlowRateActuator = O2OutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2OutFlowRateActuator.setOutput(O2ProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createH2InFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating H2InFlowRateActuator with moduleName: "+moduleName);
			H2InFlowRateActuatorImpl myH2InFlowRateActuatorImpl = new H2InFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myH2InFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myH2InFlowRateActuatorImpl));
			BiosimServer.registerServer(new H2InFlowRateActuatorPOATie(myH2InFlowRateActuatorImpl), myH2InFlowRateActuatorImpl.getModuleName(), myH2InFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureH2InFlowRateActuator(Node node){
		//System.out.println("Configuring H2InFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			H2InFlowRateActuator myH2InFlowRateActuator = H2InFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myH2InFlowRateActuator.setOutput(H2ConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createH2OutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating H2OutFlowRateActuator with moduleName: "+moduleName);
			H2OutFlowRateActuatorImpl myH2OutFlowRateActuatorImpl = new H2OutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myH2OutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myH2OutFlowRateActuatorImpl));
			BiosimServer.registerServer(new H2OutFlowRateActuatorPOATie(myH2OutFlowRateActuatorImpl), myH2OutFlowRateActuatorImpl.getModuleName(), myH2OutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureH2OutFlowRateActuator(Node node){
		//System.out.println("Configuring H2OutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			H2OutFlowRateActuator myH2OutFlowRateActuator = H2OutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myH2OutFlowRateActuator.setOutput(H2ProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlAirActuators(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("CO2InFlowRateActuator")){
				if (firstPass)
					createCO2InFlowRateActuator(child);
				else
					configureCO2InFlowRateActuator(child);
			}
			else if (childName.equals("CO2OutFlowRateActuator")){
				if (firstPass)
					createCO2OutFlowRateActuator(child);
				else
					configureCO2OutFlowRateActuator(child);
			}
			else if (childName.equals("O2InFlowRateActuator")){
				if (firstPass)
					createO2InFlowRateActuator(child);
				else
					configureO2InFlowRateActuator(child);
			}
			else if (childName.equals("O2OutFlowRateActuator")){
				if (firstPass)
					createO2OutFlowRateActuator(child);
				else
					configureO2OutFlowRateActuator(child);
			}
			else if (childName.equals("H2InFlowRateActuator")){
				if (firstPass)
					createH2InFlowRateActuator(child);
				else
					configureH2InFlowRateActuator(child);
			}
			else if (childName.equals("H2OutFlowRateActuator")){
				if (firstPass)
					createH2OutFlowRateActuator(child);
				else
					configureH2OutFlowRateActuator(child);
			}
			child = child.getNextSibling();
		}
	}

	private void crawlCrewActuators(Node node, boolean firstPass){
		//None implemented Yet
	}

	//Environment
	private void createAirInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating AirInFlowRateActuator with moduleName: "+moduleName);
			AirInFlowRateActuatorImpl myAirInFlowRateActuatorImpl = new AirInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myAirInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myAirInFlowRateActuatorImpl));
			BiosimServer.registerServer(new AirInFlowRateActuatorPOATie(myAirInFlowRateActuatorImpl), myAirInFlowRateActuatorImpl.getModuleName(), myAirInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAirInFlowRateActuator(Node node){
		//System.out.println("Configuring AirInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			AirInFlowRateActuator myAirInFlowRateActuator = AirInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myAirInFlowRateActuator.setOutput(AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createAirOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating AirOutFlowRateActuator with moduleName: "+moduleName);
			AirOutFlowRateActuatorImpl myAirOutFlowRateActuatorImpl = new AirOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myAirOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myAirOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new AirOutFlowRateActuatorPOATie(myAirOutFlowRateActuatorImpl), myAirOutFlowRateActuatorImpl.getModuleName(), myAirOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAirOutFlowRateActuator(Node node){
		//System.out.println("Configuring AirOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			AirOutFlowRateActuator myAirOutFlowRateActuator = AirOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myAirOutFlowRateActuator.setOutput(AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirEnvironmentInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirEnvironmentInFlowRateActuator with moduleName: "+moduleName);
			CO2AirEnvironmentInFlowRateActuatorImpl myCO2AirEnvironmentInFlowRateActuatorImpl = new CO2AirEnvironmentInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myCO2AirEnvironmentInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myCO2AirEnvironmentInFlowRateActuatorImpl));
			BiosimServer.registerServer(new CO2AirEnvironmentInFlowRateActuatorPOATie(myCO2AirEnvironmentInFlowRateActuatorImpl), myCO2AirEnvironmentInFlowRateActuatorImpl.getModuleName(), myCO2AirEnvironmentInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirEnvironmentInFlowRateActuator(Node node){
		//System.out.println("Configuring CO2AirEnvironmentInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			CO2AirEnvironmentInFlowRateActuator myCO2AirEnvironmentInFlowRateActuator = CO2AirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2AirEnvironmentInFlowRateActuator.setOutput(CO2AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirEnvironmentOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirEnvironmentOutFlowRateActuator with moduleName: "+moduleName);
			CO2AirEnvironmentOutFlowRateActuatorImpl myCO2AirEnvironmentOutFlowRateActuatorImpl = new CO2AirEnvironmentOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myCO2AirEnvironmentOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myCO2AirEnvironmentOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new CO2AirEnvironmentOutFlowRateActuatorPOATie(myCO2AirEnvironmentOutFlowRateActuatorImpl), myCO2AirEnvironmentOutFlowRateActuatorImpl.getModuleName(), myCO2AirEnvironmentOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirEnvironmentOutFlowRateActuator(Node node){
		//System.out.println("Configuring CO2AirEnvironmentOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			CO2AirEnvironmentOutFlowRateActuator myCO2AirEnvironmentOutFlowRateActuator = CO2AirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2AirEnvironmentOutFlowRateActuator.setOutput(CO2AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirStoreInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirStoreInFlowRateActuator with moduleName: "+moduleName);
			CO2AirStoreInFlowRateActuatorImpl myCO2AirStoreInFlowRateActuatorImpl = new CO2AirStoreInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myCO2AirStoreInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myCO2AirStoreInFlowRateActuatorImpl));
			BiosimServer.registerServer(new CO2AirStoreInFlowRateActuatorPOATie(myCO2AirStoreInFlowRateActuatorImpl), myCO2AirStoreInFlowRateActuatorImpl.getModuleName(), myCO2AirStoreInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirStoreInFlowRateActuator(Node node){
		//System.out.println("Configuring CO2AirStoreInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			CO2AirStoreInFlowRateActuator myCO2AirStoreInFlowRateActuator = CO2AirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2AirStoreInFlowRateActuator.setOutput(CO2AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createCO2AirStoreOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating CO2AirStoreOutFlowRateActuator with moduleName: "+moduleName);
			CO2AirStoreOutFlowRateActuatorImpl myCO2AirStoreOutFlowRateActuatorImpl = new CO2AirStoreOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myCO2AirStoreOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myCO2AirStoreOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new CO2AirStoreOutFlowRateActuatorPOATie(myCO2AirStoreOutFlowRateActuatorImpl), myCO2AirStoreOutFlowRateActuatorImpl.getModuleName(), myCO2AirStoreOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCO2AirStoreOutFlowRateActuator(Node node){
		//System.out.println("Configuring CO2AirStoreOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			CO2AirStoreOutFlowRateActuator myCO2AirStoreOutFlowRateActuator = CO2AirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myCO2AirStoreOutFlowRateActuator.setOutput(CO2AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirEnvironmentInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirEnvironmentInFlowRateActuator with moduleName: "+moduleName);
			O2AirEnvironmentInFlowRateActuatorImpl myO2AirEnvironmentInFlowRateActuatorImpl = new O2AirEnvironmentInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myO2AirEnvironmentInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myO2AirEnvironmentInFlowRateActuatorImpl));
			BiosimServer.registerServer(new O2AirEnvironmentInFlowRateActuatorPOATie(myO2AirEnvironmentInFlowRateActuatorImpl), myO2AirEnvironmentInFlowRateActuatorImpl.getModuleName(), myO2AirEnvironmentInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirEnvironmentInFlowRateActuator(Node node){
		//System.out.println("Configuring O2AirEnvironmentInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			O2AirEnvironmentInFlowRateActuator myO2AirEnvironmentInFlowRateActuator = O2AirEnvironmentInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2AirEnvironmentInFlowRateActuator.setOutput(O2AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirEnvironmentOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirEnvironmentOutFlowRateActuator with moduleName: "+moduleName);
			O2AirEnvironmentOutFlowRateActuatorImpl myO2AirEnvironmentOutFlowRateActuatorImpl = new O2AirEnvironmentOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myO2AirEnvironmentOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myO2AirEnvironmentOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new O2AirEnvironmentOutFlowRateActuatorPOATie(myO2AirEnvironmentOutFlowRateActuatorImpl), myO2AirEnvironmentOutFlowRateActuatorImpl.getModuleName(), myO2AirEnvironmentOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirEnvironmentOutFlowRateActuator(Node node){
		//System.out.println("Configuring O2AirEnvironmentOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			O2AirEnvironmentOutFlowRateActuator myO2AirEnvironmentOutFlowRateActuator = O2AirEnvironmentOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2AirEnvironmentOutFlowRateActuator.setOutput(O2AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirStoreInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirStoreInFlowRateActuator with moduleName: "+moduleName);
			O2AirStoreInFlowRateActuatorImpl myO2AirStoreInFlowRateActuatorImpl = new O2AirStoreInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myO2AirStoreInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myO2AirStoreInFlowRateActuatorImpl));
			BiosimServer.registerServer(new O2AirStoreInFlowRateActuatorPOATie(myO2AirStoreInFlowRateActuatorImpl), myO2AirStoreInFlowRateActuatorImpl.getModuleName(), myO2AirStoreInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirStoreInFlowRateActuator(Node node){
		//System.out.println("Configuring O2AirStoreInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			O2AirStoreInFlowRateActuator myO2AirStoreInFlowRateActuator = O2AirStoreInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2AirStoreInFlowRateActuator.setOutput(O2AirConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createO2AirStoreOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating O2AirStoreOutFlowRateActuator with moduleName: "+moduleName);
			O2AirStoreOutFlowRateActuatorImpl myO2AirStoreOutFlowRateActuatorImpl = new O2AirStoreOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myO2AirStoreOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myO2AirStoreOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new O2AirStoreOutFlowRateActuatorPOATie(myO2AirStoreOutFlowRateActuatorImpl), myO2AirStoreOutFlowRateActuatorImpl.getModuleName(), myO2AirStoreOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureO2AirStoreOutFlowRateActuator(Node node){
		//System.out.println("Configuring O2AirStoreOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			O2AirStoreOutFlowRateActuator myO2AirStoreOutFlowRateActuator = O2AirStoreOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myO2AirStoreOutFlowRateActuator.setOutput(O2AirProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlEnvironmentActuators(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("AirInFlowRateActuator")){
				if (firstPass)
					createAirInFlowRateActuator(child);
				else
					configureAirInFlowRateActuator(child);
			}
			else if (childName.equals("AirOutFlowRateActuator")){
				if (firstPass)
					createAirOutFlowRateActuator(child);
				else
					configureAirOutFlowRateActuator(child);
			}
			else if (childName.equals("CO2AirEnvironmentInFlowRateActuator")){
				if (firstPass)
					createCO2AirEnvironmentInFlowRateActuator(child);
				else
					configureCO2AirEnvironmentInFlowRateActuator(child);
			}
			else if (childName.equals("CO2AirEnvironmentOutFlowRateActuator")){
				if (firstPass)
					createCO2AirEnvironmentOutFlowRateActuator(child);
				else
					configureCO2AirEnvironmentOutFlowRateActuator(child);
			}
			else if (childName.equals("CO2AirStoreInFlowRateActuator")){
				if (firstPass)
					createCO2AirStoreInFlowRateActuator(child);
				else
					configureCO2AirStoreInFlowRateActuator(child);
			}
			else if (childName.equals("CO2AirStoreOutFlowRateActuator")){
				if (firstPass)
					createCO2AirStoreOutFlowRateActuator(child);
				else
					configureCO2AirStoreOutFlowRateActuator(child);
			}
			else if (childName.equals("O2AirEnvironmentInFlowRateActuator")){
				if (firstPass)
					createO2AirEnvironmentInFlowRateActuator(child);
				else
					configureO2AirEnvironmentInFlowRateActuator(child);
			}
			else if (childName.equals("O2AirEnvironmentOutFlowRateActuator")){
				if (firstPass)
					createO2AirEnvironmentOutFlowRateActuator(child);
				else
					configureO2AirEnvironmentOutFlowRateActuator(child);
			}
			else if (childName.equals("O2AirStoreInFlowRateActuator")){
				if (firstPass)
					createO2AirStoreInFlowRateActuator(child);
				else
					configureO2AirStoreInFlowRateActuator(child);
			}
			else if (childName.equals("O2AirStoreOutFlowRateActuator")){
				if (firstPass)
					createO2AirStoreOutFlowRateActuator(child);
				else
					configureO2AirStoreOutFlowRateActuator(child);
			}
			child = child.getNextSibling();
		}
	}

	//Food
	private void createBiomassInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating BiomassInFlowRateActuator with moduleName: "+moduleName);
			BiomassInFlowRateActuatorImpl myBiomassInFlowRateActuatorImpl = new BiomassInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myBiomassInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myBiomassInFlowRateActuatorImpl));
			BiosimServer.registerServer(new BiomassInFlowRateActuatorPOATie(myBiomassInFlowRateActuatorImpl), myBiomassInFlowRateActuatorImpl.getModuleName(), myBiomassInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassInFlowRateActuator(Node node){
		//System.out.println("Configuring BiomassInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			BiomassInFlowRateActuator myBiomassInFlowRateActuator = BiomassInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myBiomassInFlowRateActuator.setOutput(BiomassConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createBiomassOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating BiomassOutFlowRateActuator with moduleName: "+moduleName);
			BiomassOutFlowRateActuatorImpl myBiomassOutFlowRateActuatorImpl = new BiomassOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myBiomassOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myBiomassOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new BiomassOutFlowRateActuatorPOATie(myBiomassOutFlowRateActuatorImpl), myBiomassOutFlowRateActuatorImpl.getModuleName(), myBiomassOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassOutFlowRateActuator(Node node){
		//System.out.println("Configuring BiomassOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			BiomassOutFlowRateActuator myBiomassOutFlowRateActuator = BiomassOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myBiomassOutFlowRateActuator.setOutput(BiomassProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createFoodInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating FoodInFlowRateActuator with moduleName: "+moduleName);
			FoodInFlowRateActuatorImpl myFoodInFlowRateActuatorImpl = new FoodInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myFoodInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myFoodInFlowRateActuatorImpl));
			BiosimServer.registerServer(new FoodInFlowRateActuatorPOATie(myFoodInFlowRateActuatorImpl), myFoodInFlowRateActuatorImpl.getModuleName(), myFoodInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureFoodInFlowRateActuator(Node node){
		//System.out.println("Configuring FoodInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			FoodInFlowRateActuator myFoodInFlowRateActuator = FoodInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myFoodInFlowRateActuator.setOutput(FoodConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createFoodOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating FoodOutFlowRateActuator with moduleName: "+moduleName);
			FoodOutFlowRateActuatorImpl myFoodOutFlowRateActuatorImpl = new FoodOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myFoodOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myFoodOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new FoodOutFlowRateActuatorPOATie(myFoodOutFlowRateActuatorImpl), myFoodOutFlowRateActuatorImpl.getModuleName(), myFoodOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureFoodOutFlowRateActuator(Node node){
		//System.out.println("Configuring FoodOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			FoodOutFlowRateActuator myFoodOutFlowRateActuator = FoodOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myFoodOutFlowRateActuator.setOutput(FoodProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlFoodActuators(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("BiomassInFlowRateActuator")){
				if (firstPass)
					createBiomassInFlowRateActuator(child);
				else
					configureBiomassInFlowRateActuator(child);
			}
			else if (childName.equals("BiomassOutFlowRateActuator")){
				if (firstPass)
					createBiomassOutFlowRateActuator(child);
				else
					configureBiomassOutFlowRateActuator(child);
			}
			else if (childName.equals("FoodInFlowRateActuator")){
				if (firstPass)
					createFoodInFlowRateActuator(child);
				else
					configureFoodInFlowRateActuator(child);
			}
			else if (childName.equals("FoodOutFlowRateActuator")){
				if (firstPass)
					createFoodOutFlowRateActuator(child);
				else
					configureFoodOutFlowRateActuator(child);
			}
			child = child.getNextSibling();
		}
	}

	//Power
	private void createPowerInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PowerInFlowRateActuator with moduleName: "+moduleName);
			PowerInFlowRateActuatorImpl myPowerInFlowRateActuatorImpl = new PowerInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myPowerInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myPowerInFlowRateActuatorImpl));
			BiosimServer.registerServer(new PowerInFlowRateActuatorPOATie(myPowerInFlowRateActuatorImpl), myPowerInFlowRateActuatorImpl.getModuleName(), myPowerInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePowerInFlowRateActuator(Node node){
		//System.out.println("Configuring PowerInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			PowerInFlowRateActuator myPowerInFlowRateActuator = PowerInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myPowerInFlowRateActuator.setOutput(PowerConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createPowerOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PowerOutFlowRateActuator with moduleName: "+moduleName);
			PowerOutFlowRateActuatorImpl myPowerOutFlowRateActuatorImpl = new PowerOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myPowerOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myPowerOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new PowerOutFlowRateActuatorPOATie(myPowerOutFlowRateActuatorImpl), myPowerOutFlowRateActuatorImpl.getModuleName(), myPowerOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePowerOutFlowRateActuator(Node node){
		//System.out.println("Configuring PowerOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			PowerOutFlowRateActuator myPowerOutFlowRateActuator = PowerOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myPowerOutFlowRateActuator.setOutput(PowerProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlPowerActuators(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("PowerInFlowRateActuator")){
				if (firstPass)
					createPowerInFlowRateActuator(child);
				else
					configurePowerInFlowRateActuator(child);
			}
			else if (childName.equals("PowerOutFlowRateActuator")){
				if (firstPass)
					createPowerOutFlowRateActuator(child);
				else
					configurePowerOutFlowRateActuator(child);
			}
			child = child.getNextSibling();
		}
	}

	//Water
	private void createPotableWaterInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PotableWaterInFlowRateActuator with moduleName: "+moduleName);
			PotableWaterInFlowRateActuatorImpl myPotableWaterInFlowRateActuatorImpl = new PotableWaterInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myPotableWaterInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myPotableWaterInFlowRateActuatorImpl));
			BiosimServer.registerServer(new PotableWaterInFlowRateActuatorPOATie(myPotableWaterInFlowRateActuatorImpl), myPotableWaterInFlowRateActuatorImpl.getModuleName(), myPotableWaterInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePotableWaterInFlowRateActuator(Node node){
		//System.out.println("Configuring PotableWaterInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			PotableWaterInFlowRateActuator myPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myPotableWaterInFlowRateActuator.setOutput(PotableWaterConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createPotableWaterOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating PotableWaterOutFlowRateActuator with moduleName: "+moduleName);
			PotableWaterOutFlowRateActuatorImpl myPotableWaterOutFlowRateActuatorImpl = new PotableWaterOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myPotableWaterOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myPotableWaterOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new PotableWaterOutFlowRateActuatorPOATie(myPotableWaterOutFlowRateActuatorImpl), myPotableWaterOutFlowRateActuatorImpl.getModuleName(), myPotableWaterOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePotableWaterOutFlowRateActuator(Node node){
		//System.out.println("Configuring PotableWaterOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			PotableWaterOutFlowRateActuator myPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myPotableWaterOutFlowRateActuator.setOutput(PotableWaterProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createGreyWaterInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating GreyWaterInFlowRateActuator with moduleName: "+moduleName);
			GreyWaterInFlowRateActuatorImpl myGreyWaterInFlowRateActuatorImpl = new GreyWaterInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myGreyWaterInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myGreyWaterInFlowRateActuatorImpl));
			BiosimServer.registerServer(new GreyWaterInFlowRateActuatorPOATie(myGreyWaterInFlowRateActuatorImpl), myGreyWaterInFlowRateActuatorImpl.getModuleName(), myGreyWaterInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureGreyWaterInFlowRateActuator(Node node){
		//System.out.println("Configuring GreyWaterInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			GreyWaterInFlowRateActuator myGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myGreyWaterInFlowRateActuator.setOutput(GreyWaterConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createGreyWaterOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating GreyWaterOutFlowRateActuator with moduleName: "+moduleName);
			GreyWaterOutFlowRateActuatorImpl myGreyWaterOutFlowRateActuatorImpl = new GreyWaterOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myGreyWaterOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myGreyWaterOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new GreyWaterOutFlowRateActuatorPOATie(myGreyWaterOutFlowRateActuatorImpl), myGreyWaterOutFlowRateActuatorImpl.getModuleName(), myGreyWaterOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureGreyWaterOutFlowRateActuator(Node node){
		//System.out.println("Configuring GreyWaterOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			GreyWaterOutFlowRateActuator myGreyWaterOutFlowRateActuator = GreyWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myGreyWaterOutFlowRateActuator.setOutput(GreyWaterProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createDirtyWaterInFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating DirtyWaterInFlowRateActuator with moduleName: "+moduleName);
			DirtyWaterInFlowRateActuatorImpl myDirtyWaterInFlowRateActuatorImpl = new DirtyWaterInFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myDirtyWaterInFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myDirtyWaterInFlowRateActuatorImpl));
			BiosimServer.registerServer(new DirtyWaterInFlowRateActuatorPOATie(myDirtyWaterInFlowRateActuatorImpl), myDirtyWaterInFlowRateActuatorImpl.getModuleName(), myDirtyWaterInFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureDirtyWaterInFlowRateActuator(Node node){
		//System.out.println("Configuring DirtyWaterInFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			DirtyWaterInFlowRateActuator myDirtyWaterInFlowRateActuator = DirtyWaterInFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myDirtyWaterInFlowRateActuator.setOutput(DirtyWaterConsumerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void createDirtyWaterOutFlowRateActuator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			//System.out.println("Creating DirtyWaterOutFlowRateActuator with moduleName: "+moduleName);
			DirtyWaterOutFlowRateActuatorImpl myDirtyWaterOutFlowRateActuatorImpl = new DirtyWaterOutFlowRateActuatorImpl(myID, moduleName);
			setupBioModule(myDirtyWaterOutFlowRateActuatorImpl, node);
			myActuators.add(OrbUtils.poaToCorbaObj(myDirtyWaterOutFlowRateActuatorImpl));
			BiosimServer.registerServer(new DirtyWaterOutFlowRateActuatorPOATie(myDirtyWaterOutFlowRateActuatorImpl), myDirtyWaterOutFlowRateActuatorImpl.getModuleName(), myDirtyWaterOutFlowRateActuatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureDirtyWaterOutFlowRateActuator(Node node){
		//System.out.println("Configuring DirtyWaterOutFlowRateActuator");
		String moduleName = getModuleName(node);
		try{
			DirtyWaterOutFlowRateActuator myDirtyWaterOutFlowRateActuator = DirtyWaterOutFlowRateActuatorHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
			String inputName = node.getAttributes().getNamedItem("output").getNodeValue();
			int index = Integer.parseInt(node.getAttributes().getNamedItem("index").getNodeValue());
			myDirtyWaterOutFlowRateActuator.setOutput(DirtyWaterProducerHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(inputName)), index);
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
		catch (NumberFormatException e){
			e.printStackTrace();
		}
	}

	private void crawlWaterActuators(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("PotableWaterInFlowRateActuator")){
				if (firstPass)
					createPotableWaterInFlowRateActuator(child);
				else
					configurePotableWaterInFlowRateActuator(child);
			}
			else if (childName.equals("PotableWaterOutFlowRateActuator")){
				if (firstPass)
					createPotableWaterOutFlowRateActuator(child);
				else
					configurePotableWaterOutFlowRateActuator(child);
			}
			else if (childName.equals("GreyWaterInFlowRateActuator")){
				if (firstPass)
					createGreyWaterInFlowRateActuator(child);
				else
					configureGreyWaterInFlowRateActuator(child);
			}
			else if (childName.equals("GreyWaterOutFlowRateActuator")){
				if (firstPass)
					createGreyWaterOutFlowRateActuator(child);
				else
					configureGreyWaterOutFlowRateActuator(child);
			}
			else if (childName.equals("DirtyWaterInFlowRateActuator")){
				if (firstPass)
					createDirtyWaterInFlowRateActuator(child);
				else
					configureDirtyWaterInFlowRateActuator(child);
			}
			else if (childName.equals("DirtyWaterOutFlowRateActuator")){
				if (firstPass)
					createDirtyWaterOutFlowRateActuator(child);
				else
					configureDirtyWaterOutFlowRateActuator(child);
			}
			child = child.getNextSibling();
		}
	}

	private void crawlActuators(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("air")){
				crawlAirActuators(child, firstPass);
			}
			else if (childName.equals("crew")){
				crawlCrewActuators(child, firstPass);

			}
			else if (childName.equals("environment")){
				crawlEnvironmentActuators(child, firstPass);

			}
			else if (childName.equals("food")){
				crawlFoodActuators(child, firstPass);

			}
			else if (childName.equals("power")){
				crawlPowerActuators(child, firstPass);

			}
			else if (childName.equals("water")){
				crawlWaterActuators(child, firstPass);

			}
			child = child.getNextSibling();
		}
	}
}
