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

	/** Default constructor. */
	public BioInitializer(int pID){
		myID = pID;
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

	//Globals
	private void crawlGlobals(Node node, boolean firstPass){
		if (firstPass){
			try{
				BioDriver myDriver = BioDriverHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str("BioDriver"));
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
			Node crewsToWatchNode = node.getAttributes().getNamedItem("crewsToWatch");
			if (crewsToWatchNode != null){
				String crewsToWatchString = crewsToWatchNode.getNodeValue();
				StringTokenizer tokenizer = new StringTokenizer(crewsToWatchString);
				CrewGroup[] crewGroups = new CrewGroup[tokenizer.countTokens()];
				for (int i = 0; tokenizer.hasMoreTokens(); i++){
					try{
						crewGroups[i] = CrewGroupHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(tokenizer.nextToken()));
						System.out.println("Fetched "+crewGroups[i].getModuleName());
					}
					catch(org.omg.CORBA.UserException e){
						e.printStackTrace();
					}
				}
			}
		}
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

	private static float[] getMaxFlowRates(Node node){
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
				Node environmentNode = child.getFirstChild();
				Node storeNode = environmentNode.getNextSibling();
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
				Node environmentNode = child.getFirstChild();
				Node storeNode = environmentNode.getNextSibling();
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
				Node environmentNode = child.getFirstChild();
				Node storeNode = environmentNode.getNextSibling();
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
				Node environmentNode = child.getFirstChild();
				Node storeNode = environmentNode.getNextSibling();
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
			child = child.getNextSibling();
		}
	}

	private BioModule[] getInputs(Node node){
		String arrayString = node.getAttributes().getNamedItem("inputs").getNodeValue();
		StringTokenizer tokenizer = new StringTokenizer(arrayString);
		BioModule[] inputs = new BioModule[tokenizer.countTokens()];
		for (int i = 0; tokenizer.hasMoreTokens(); i++){
			try{
				inputs[i] = BioModuleHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(tokenizer.nextToken()));
				System.out.println("Fetched "+inputs[i].getModuleName());
			}
			catch(org.omg.CORBA.UserException e){
				e.printStackTrace();
			}
		}
		return inputs;
	}

	private BioModule[] getOutputs(Node node){
		String arrayString = node.getAttributes().getNamedItem("outputs").getNodeValue();
		StringTokenizer tokenizer = new StringTokenizer(arrayString);
		BioModule[] outputs = new BioModule[tokenizer.countTokens()];
		for (int i = 0; tokenizer.hasMoreTokens(); i++){
			try{
				outputs[i] = BioModuleHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(tokenizer.nextToken()));
				System.out.println("Fetched "+outputs[i].getModuleName());
			}
			catch(org.omg.CORBA.UserException e){
				e.printStackTrace();
			}
		}
		return outputs;
	}

	//Modules
	private void createAirRS(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating AirRS with moduleName: "+moduleName);
			AirRSImpl myAirRSImpl = new AirRSImpl(myID, moduleName);
			BiosimServer.registerServer(new AirRSPOATie(myAirRSImpl), myAirRSImpl.getModuleName(), myAirRSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAirRS(Node node){
		System.out.println("Configuring AirRS");
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
			System.out.println("Creating O2Store with moduleName: "+moduleName);
			O2StoreImpl myO2StoreImpl = new O2StoreImpl(myID, moduleName);
			myO2StoreImpl.setLevel(getStoreLevel(node));
			myO2StoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new O2StorePOATie(myO2StoreImpl), myO2StoreImpl.getModuleName(), myO2StoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createCO2Store(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating CO2Store with moduleName: "+moduleName);
			CO2StoreImpl myCO2StoreImpl = new CO2StoreImpl(myID, moduleName);
			myCO2StoreImpl.setLevel(getStoreLevel(node));
			myCO2StoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new CO2StorePOATie(myCO2StoreImpl), myCO2StoreImpl.getModuleName(), myCO2StoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createH2Store(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating H2Store with moduleName: "+moduleName);
			H2StoreImpl myH2StoreImpl = new H2StoreImpl(myID, moduleName);
			myH2StoreImpl.setLevel(getStoreLevel(node));
			myH2StoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new H2StorePOATie(myH2StoreImpl), myH2StoreImpl.getModuleName(), myH2StoreImpl.getID());
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
		int i = 0;
		while(child != null){
			if (child.getNodeName().equals("activity")){
				Activity newActivity = createActivity(child);
				newSchedule.insertActivityInSchedule(newActivity, i);
				i++;
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
			System.out.println("Creating CrewGroup with moduleName: "+moduleName);
			CrewGroupImpl myCrewGroupImpl = new CrewGroupImpl(myID, moduleName);
			BiosimServer.registerServer(new CrewGroupPOATie(myCrewGroupImpl), myCrewGroupImpl.getModuleName(), myCrewGroupImpl.getID());
			Node child = node.getFirstChild();
			while (child != null) {
				if (child.getNodeName().equals("crewPerson"))
					createCrewPerson(child, myCrewGroupImpl);
				child = child.getNextSibling();
			}
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureCrewGroup(Node node){
		System.out.println("Configuring CrewGroup");
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
			System.out.println("Creating SimEnvironment with moduleName: "+moduleName);
			SimEnvironmentImpl mySimEnvironmentImpl = null;
			float CO2Moles = 0f;
			float O2Moles = 0f;
			float waterMoles = 0f;
			float otherMoles = 0f;
			float volume = 0f;
			Node CO2MolesNode = null;
			Node O2MolesNode = null;
			Node waterMolesNode = null;
			Node otherMolesNode = null;
			try{
				volume = Float.parseFloat(node.getAttributes().getNamedItem("initialVolume").getNodeValue());
				CO2MolesNode = node.getAttributes().getNamedItem("initialCO2Moles");
				O2MolesNode = node.getAttributes().getNamedItem("initialO2Moles");
				waterMolesNode = node.getAttributes().getNamedItem("initialWaterMoles");
				otherMolesNode = node.getAttributes().getNamedItem("initialOtherMoles");
				if (CO2MolesNode != null)
					CO2Moles = Float.parseFloat(CO2MolesNode.getNodeValue());
				if (O2MolesNode != null)
					O2Moles = Float.parseFloat(O2MolesNode.getNodeValue());
				if (waterMolesNode != null)
					waterMoles = Float.parseFloat(waterMolesNode.getNodeValue());
				if (otherMolesNode != null)
					otherMoles = Float.parseFloat(otherMolesNode.getNodeValue());
			}
			catch (NumberFormatException e){

				e.printStackTrace();
			}
			if ((CO2MolesNode != null) || (O2MolesNode != null) || (waterMolesNode != null) || (otherMolesNode != null))
				mySimEnvironmentImpl = new SimEnvironmentImpl(CO2Moles, O2Moles, otherMoles, waterMoles, volume, moduleName, myID);
			else
				mySimEnvironmentImpl = new SimEnvironmentImpl(myID, volume, moduleName);
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
			System.out.println("Creating Accumulator with moduleName: "+moduleName);
			AccumulatorImpl myAccumulatorImpl = new AccumulatorImpl(myID, moduleName);
			BiosimServer.registerServer(new AccumulatorPOATie(myAccumulatorImpl), myAccumulatorImpl.getModuleName(), myAccumulatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAccumulator(Node node){
		System.out.println("Configuring Accumulator");
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
			System.out.println("Creating Injector with moduleName: "+moduleName);
			InjectorImpl myInjectorImpl = new InjectorImpl(myID, moduleName);
			BiosimServer.registerServer(new InjectorPOATie(myInjectorImpl), myInjectorImpl.getModuleName(), myInjectorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureInjector(Node node){
		System.out.println("Configuring Injector");
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
			if (childName.equals("Actuator")){
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
			System.out.println("Creating BiomassRS with moduleName: "+moduleName);
			BiomassRSImpl myBiomassRSImpl = new BiomassRSImpl(myID, moduleName);
			Node child = node.getFirstChild();
			while (child != null) {
				if (child.getNodeName().equals("shelf"))
					myBiomassRSImpl.createNewShelf(getCropType(child), getCropArea(child));
				child = child.getNextSibling();
			}
			BiosimServer.registerServer(new BiomassRSPOATie(myBiomassRSImpl), myBiomassRSImpl.getModuleName(), myBiomassRSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassRS(Node node){
		System.out.println("Configuring BiomassRS");
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
			System.out.println("Creating FoodProcessor with moduleName: "+moduleName);
			FoodProcessorImpl myFoodProcessorImpl = new FoodProcessorImpl(myID, moduleName);
			BiosimServer.registerServer(new FoodProcessorPOATie(myFoodProcessorImpl), myFoodProcessorImpl.getModuleName(), myFoodProcessorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureFoodProcessor(Node node){
		System.out.println("Configuring FoodProcessor");
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
			System.out.println("Creating BiomassStore with moduleName: "+moduleName);
			BiomassStoreImpl myBiomassStoreImpl = new BiomassStoreImpl(myID, moduleName);
			myBiomassStoreImpl.setLevel(getStoreLevel(node));
			myBiomassStoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new BiomassStorePOATie(myBiomassStoreImpl), myBiomassStoreImpl.getModuleName(), myBiomassStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createFoodStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating FoodStore with moduleName: "+moduleName);
			FoodStoreImpl myFoodStoreImpl = new FoodStoreImpl(myID, moduleName);
			myFoodStoreImpl.setLevel(getStoreLevel(node));
			myFoodStoreImpl.setCapacity(getStoreCapacity(node));
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
			System.out.println("Creating PowerPS with moduleName: "+moduleName);
			PowerPSImpl myPowerPSImpl = null;
			if (node.getAttributes().getNamedItem("generation").getNodeValue().equals("SOLAR"))
				myPowerPSImpl = new SolarPowerPS(myID, moduleName);
			else
				myPowerPSImpl = new NuclearPowerPS(myID, moduleName);
			BiosimServer.registerServer(new PowerPSPOATie(myPowerPSImpl), myPowerPSImpl.getModuleName(), myPowerPSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePowerPS(Node node){
		System.out.println("Configuring PowerPS");
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
			System.out.println("Creating PowerStore with moduleName: "+moduleName);
			PowerStoreImpl myPowerStoreImpl = new PowerStoreImpl(myID, moduleName);
			myPowerStoreImpl.setLevel(getStoreLevel(node));
			myPowerStoreImpl.setCapacity(getStoreCapacity(node));
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
			System.out.println("Creating WaterRS with moduleName: "+moduleName);
			WaterRSImpl myWaterRSImpl = new WaterRSImpl(myID, moduleName);
			BiosimServer.registerServer(new WaterRSPOATie(myWaterRSImpl), myWaterRSImpl.getModuleName(), myWaterRSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureWaterRS(Node node){
		System.out.println("Configuring WaterRS");
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
			System.out.println("Creating PotableWaterStore with moduleName: "+moduleName);
			PotableWaterStoreImpl myPotableWaterStoreImpl = new PotableWaterStoreImpl(myID, moduleName);
			myPotableWaterStoreImpl.setLevel(getStoreLevel(node));
			myPotableWaterStoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new PotableWaterStorePOATie(myPotableWaterStoreImpl), myPotableWaterStoreImpl.getModuleName(), myPotableWaterStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createDirtyWaterStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating DirtyWaterStore with moduleName: "+moduleName);
			DirtyWaterStoreImpl myDirtyWaterStoreImpl = new DirtyWaterStoreImpl(myID, moduleName);
			myDirtyWaterStoreImpl.setLevel(getStoreLevel(node));
			myDirtyWaterStoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new DirtyWaterStorePOATie(myDirtyWaterStoreImpl), myDirtyWaterStoreImpl.getModuleName(), myDirtyWaterStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createGreyWaterStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating GreyWaterStore with moduleName: "+moduleName);
			GreyWaterStoreImpl myGreyWaterStoreImpl = new GreyWaterStoreImpl(myID, moduleName);
			myGreyWaterStoreImpl.setLevel(getStoreLevel(node));
			myGreyWaterStoreImpl.setCapacity(getStoreCapacity(node));
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
	private void crawlAirSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlCrewSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlEnvironmentSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlFrameworkSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlFoodSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlPowerSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlWaterSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	//Actuators
	private void crawlAirActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlCrewActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlEnvironmentActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlFrameworkActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlFoodActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlPowerActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlWaterActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

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
			else if (childName.equals("framework")){
				crawlFrameworkActuators(child, firstPass);

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
			System.out.println("Starting to parse file: "+fileToParse);
			myParser.parse(fileToParse);
			Document document = myParser.getDocument();
			crawlBiosim(document, true);
			crawlBiosim(document, false);
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
}
