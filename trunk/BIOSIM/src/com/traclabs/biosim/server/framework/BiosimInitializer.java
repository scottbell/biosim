package com.traclabs.biosim.server.framework;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Node;

import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.framework.BioDriverHelper;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.BioModuleHelper;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.framework.StochasticIntensity;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.server.actuator.framework.ActuatorInitializer;
import com.traclabs.biosim.server.actuator.framework.ActuatorParser;
import com.traclabs.biosim.server.sensor.framework.SensorInitializer;
import com.traclabs.biosim.server.sensor.framework.SensorParser;
import com.traclabs.biosim.server.simulation.framework.SimulationInitializer;
import com.traclabs.biosim.server.simulation.framework.SimulationParser;
import com.traclabs.biosim.util.OrbUtils;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class BiosimInitializer extends BiosimParser{
	private BioDriver myBioDriver;
	private Logger myLogger;

	private SimulationInitializer mySimulationInitializer;
	private SensorInitializer mySensorInitializer;
	private ActuatorInitializer myActuatorInitializer;

    private List<BioModule> myModules;

    /** Default constructor. */
    public BiosimInitializer(int pID) {
        super(pID);
        myLogger = Logger.getLogger(this.getClass());
        myModules = new Vector<BioModule>();
        
        mySimulationInitializer = new SimulationInitializer(pID);
        mySensorInitializer = new SensorInitializer(pID);
        myActuatorInitializer = new ActuatorInitializer(pID);
    }
    
    public void parseFile(String fileToParse) {
    	super.parseFile(fileToParse);
            
            //Fold Actuators, SimModules, and Sensors into modules
            myModules.addAll(mySensorInitializer.getSensors());
            myModules.addAll(mySimulationInitializer.getPassiveSimModules());
            myModules.addAll(mySimulationInitializer.getActiveSimModules());
            myModules.addAll(mySimulationInitializer.getPrioritySimModules());
            myModules.addAll(myActuatorInitializer.getActuators());

            //Give Modules, Sensors, Actuatos to BioDriver to tick
            BioModule[] moduleArray = convertList(myModules);
            BioModule[] sensorArray = convertList(mySensorInitializer
                    .getSensors());
            BioModule[] actuatorArray = convertList(myActuatorInitializer
                    .getActuators());
            BioModule[] passiveSimModulesArray = convertList(mySimulationInitializer
                    .getPassiveSimModules());
            BioModule[] activeSimModulesArray = convertList(mySimulationInitializer
                    .getActiveSimModules());
            BioModule[] prioritySimModulesArray = convertList(mySimulationInitializer
                    .getPrioritySimModules());
            handleModules(moduleArray, sensorArray, actuatorArray, activeSimModulesArray, passiveSimModulesArray, prioritySimModulesArray);
            myLogger.info("done");
            
        
    }
    
    protected void handleStochasticIntensity(StochasticIntensity stochasticIntensity) {
    	getBioDriver().setStochasticIntensity(stochasticIntensity);
    }

    protected void handleRunTillPlantDeath(boolean b) {
    	getBioDriver().setRunTillPlantDeath(b);
	}

    protected void handleRunTillCrewDeath(boolean b) {
    	getBioDriver().setRunTillCrewDeath(b);
	}

    protected void handleCrewsToWatch(String[] crewsToWatchArray) {
    	CrewGroup[] crewGroups = new CrewGroup[crewsToWatchArray.length];
    	for (int i = 0; i < crewGroups.length; i++) {
            try {
                crewGroups[i] = CrewGroupHelper.narrow(OrbUtils
                        .getNamingContext(getID()).resolve_str(
                                crewsToWatchArray[i]));
                myLogger.debug("Fetched "
                        + crewGroups[i].getModuleName());
            } catch (org.omg.CORBA.UserException e) {
                e.printStackTrace();
            }
        }
    	getBioDriver().setCrewsToWatch(crewGroups);
	}

    protected void handleLogProperty(Properties logProperties) {
    	PropertyConfigurator.configure(logProperties);
	}

    protected void handleIsLopping(boolean looping) {
    	getBioDriver().setLooping(looping);
    }

    protected void handlePauseSimulation(boolean pause) {
    	getBioDriver().setPauseSimulation(pause);
	}

    protected void handleRunTillN(int ticks) {
    	getBioDriver().setRunTillN(ticks);
	}

    protected void handleTickLength(float tickLength) {
    	getBioDriver().setTickLength(tickLength);
	}
    
    protected void handleDriverStutterLength(int stutterLength) {
    	getBioDriver().setDriverStutterLength(stutterLength);
	}

    protected void handleModules(BioModule[] moduleArray, BioModule[] sensorArray, BioModule[] actuatorArray, BioModule[] activeSimModulesArray, BioModule[] passiveSimModulesArray, BioModule[] prioritySimModulesArray) {
    	BioDriver driver = getBioDriver();
    	driver.setModules(moduleArray);
    	driver.setSensors(sensorArray);
    	driver.setActuators(actuatorArray);
    	driver.setActiveSimModules(activeSimModulesArray);
    	driver.setPassiveSimModules(passiveSimModulesArray);
    	driver.setPrioritySimModules(prioritySimModulesArray);
    }
    
    private BioDriver getBioDriver(){
    	if (myBioDriver == null){
            try {
            	myBioDriver = BioDriverHelper.narrow(OrbUtils.getNamingContext(
                        getID()).resolve_str("BioDriver"));
            } catch (Exception e) {
                myLogger.error(e.getMessage());
                e.printStackTrace();
            }
    	}
    	return myBioDriver;
    }

    public static boolean isCreatedLocally(Node node) {
        return node.getAttributes().getNamedItem("createLocally")
                .getNodeValue().equals("true");
    }

    public static String getModuleName(Node node) {
        return node.getAttributes().getNamedItem("moduleName").getNodeValue();
    }

    public static org.omg.CORBA.Object grabModule(int pID, String moduleName) {
        org.omg.CORBA.Object moduleToReturn = null;
        while (moduleToReturn == null) {
            try {
                moduleToReturn = OrbUtils.getNamingContext(pID).resolve_str(
                        moduleName);
            } catch (org.omg.CORBA.UserException e) {
                Logger.getLogger(BiosimInitializer.class).error(
                        "Couldn't find module " + moduleName
                                + ", polling again...");
                OrbUtils.sleepAwhile();
            } catch (Exception e) {
                Logger.getLogger(BiosimInitializer.class).error(
                        "Had problems contacting nameserver with module "
                                + moduleName + ", polling again...");
                OrbUtils.resetInit();
                OrbUtils.sleepAwhile();
            }
        }
        return moduleToReturn;
    }

    public static void printRemoteWarningMessage(String pName) {
        Logger.getLogger(BiosimInitializer.class).warn(
                "\nInstance of the module named " + pName
                        + " should be created remotely (if not already done)");
    }

    private static boolean getEnableBreakDown(Node pNode) {
        return pNode.getAttributes().getNamedItem("isLoggingEnabled")
                .getNodeValue().equals("true");
    }

    private static StochasticIntensity getStochasticIntensity(Node pNode) {
        String intensityString = pNode.getAttributes().getNamedItem(
                "setStochasticIntensity").getNodeValue();
        if (intensityString.equals("HIGH_STOCH"))
            return StochasticIntensity.HIGH_STOCH;
        else if (intensityString.equals("MEDIUM_STOCH"))
            return StochasticIntensity.MEDIUM_STOCH;
        else if (intensityString.equals("LOW_STOCH"))
            return StochasticIntensity.LOW_STOCH;
        else
            return StochasticIntensity.NONE_STOCH;
    }

    private static MalfunctionLength getMalfunctionLength(Node pNode) {
        String lengthString = pNode.getAttributes().getNamedItem("length")
                .getNodeValue();
        if (lengthString.equals("TEMPORARY_MALF"))
            return MalfunctionLength.TEMPORARY_MALF;
		return MalfunctionLength.PERMANENT_MALF;
    }

    private static int getMalfunctionTick(Node pNode) {
        int occursAtTick = 0;
        try {
            occursAtTick = Integer.parseInt(pNode.getAttributes().getNamedItem(
                    "occursAtTick").getNodeValue());
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        return occursAtTick;
    }

    private static MalfunctionIntensity getMalfunctionIntensity(Node pNode) {
        String intensityString = pNode.getAttributes()
                .getNamedItem("intensity").getNodeValue();
        if (intensityString.equals("SEVERE_MALF"))
            return MalfunctionIntensity.SEVERE_MALF;
        else if (intensityString.equals("MEDIUM_MALF"))
            return MalfunctionIntensity.MEDIUM_MALF;
        else
            return MalfunctionIntensity.LOW_MALF;
    }

    public static void setupBioModule(BioModuleImpl pModule, Node node) {
        pModule.setEnableBreakdown(getEnableBreakDown(node));
        pModule.setStochasticIntensity(getStochasticIntensity(node));
        Node child = node.getFirstChild();
        while (child != null) {
            if (child.getNodeName().equals("malfunction")) {
                pModule.scheduleMalfunction(getMalfunctionIntensity(child),
                        getMalfunctionLength(child), getMalfunctionTick(child));
            }
            child = child.getNextSibling();
        }
    }
    

	private static BioModule[] convertList(List pBioModules) {
        BioModule[] newArray = new BioModule[pBioModules.size()];
        int i = 0;
        for (Iterator iter = pBioModules.iterator(); iter.hasNext(); i++) {
            newArray[i] = BioModuleHelper.narrow((org.omg.CORBA.Object) (iter
                    .next()));
        }
        return newArray;
    }

	@Override
	protected SimulationParser getSimulationParser() {
		return mySimulationInitializer;
	}

	@Override
	protected SensorParser getSensorParser() {
		return mySensorInitializer;
	}

	@Override
	protected ActuatorParser getActuatorParser() {
		return myActuatorInitializer;
	}
}