package com.traclabs.biosim.util;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jacorb.naming.NameServer;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.BioModuleHelper;

/**
 * The OrbUtils class provides basic CORBA utilities to server components
 * 
 * @author Scott Bell
 */

public class OrbUtils {
    //Flag to make sure OrbUtils only runs initialize once
    private static boolean initializeOrbRunOnce = false;

    //The root POA for transformation methods and other things
    private static POA myRootPOA = null;

    //The server ORB used resolving references
    private static ORB myOrb = null;

    //The root biosim naming context reference
    private static NamingContextExt myBiosimNamingContext = null;

    private static NamingContextExt myRootContext = null;

    private static Properties myCustomORBProperties;
    
    private static final int STANDALONE_NAMESERVER_PORT = 16315;
    
    private static final int STANDALONE_SERVER_OA_PORT = 16310;

    private static final int STANDALONE_CLIENT_OA_PORT = 16311;

    private static final String ORB_CLASS = "org.jacorb.orb.ORB";
    
    private static Thread myNamingServiceThread;
    
    private static Logger myLogger = Logger.getLogger(OrbUtils.class);

    /**
     * Shouldn't be called (everything static!)
     */
    private OrbUtils() {
    }

    /**
     * Returns the ORB
     * 
     * @return the ORB
     */
    public static ORB getORB() {
    	initializeOrb();
        return myOrb;
    }

    public static void setORB(ORB pORB) {
        myOrb = pORB;
    }

    public static void setRootContext(NamingContextExt pRootContext) {
        myRootContext = pRootContext;
    }

    public static void setRootPOA(POA pRootPOA) {
        myRootPOA = pRootPOA;
    }

    /**
     * Returns the root POA
     * 
     * @return the root POA
     */
    public static POA getRootPOA() {
        initialize();
        return myRootPOA;
    }

    public static BioModule getBioModule(int pID, String pModuleName) {
        BioModule module = null;
        try {
            module = BioModuleHelper.narrow(getNamingContext(pID).resolve_str(
                    pModuleName));
        } catch (Exception e) {
            Logger.getLogger(OrbUtils.class).warn(
                    "(id=" + pID + ") Had problems getting module:"
                            + pModuleName + " " + e);
        }
        return module;
    }

    /**
     * Returns the naming context associated with this ID
     * 
     * @return the naming context
     */
    public static NamingContextExt getNamingContext(int pID) {
        initializeIDNamingContexts(pID);
        NamingContextExt idContext = null;
        try {
            idContext = NamingContextExtHelper.narrow(myBiosimNamingContext
                    .resolve_str(Integer.toString(pID)));
        } catch (Exception e) {
            Logger.getLogger(OrbUtils.class).warn(
                    "(id=" + pID + ") Had problems getting naming context:"
                            + pID + " " + e);
        }
        return idContext;
    }

    /**
     * Forces OrbUtils to retrieve the RootPoa and Naming Service again on next
     * request.
     */
    public static void resetInit() {
        initializeOrbRunOnce = false;
    }

    private static void initializeIDNamingContexts(int pID) {
        initialize();
        try {
            //Attempt to create id context, if already there, don't bother
            NameComponent idComponent = new NameComponent(pID + "", "");
            NameComponent[] idComponents = { idComponent };
            myBiosimNamingContext.bind_new_context(idComponents);
        } catch (org.omg.CosNaming.NamingContextPackage.AlreadyBound e) {
        } catch (Exception e) {
            Logger.getLogger(OrbUtils.class).warn(
                    "Had problems getting naming context " + e);
        }
    }

    private static void initialize() {
        while (!initializeLoop()) {
            sleepAwhile();
        }
    }
    
    private static void initializeOrb(){
        String[] nullArgs = null;
        // create and initialize the ORB
        if (myOrb == null){
            Properties orbProperties = new Properties();
            if (myCustomORBProperties != null)
            	orbProperties.putAll(myCustomORBProperties);
            orbProperties.put("org.omg.CORBA.ORBClass", ORB_CLASS);
            myOrb = ORB.init(nullArgs, orbProperties);
        }
    }

    /**
     * Done only once, this method initializes the ORB, resolves the root POA,
     * and grabs the naming context.
     */
    private static boolean initializeLoop() {
        if (initializeOrbRunOnce)
            return true;
        try {
        	initializeOrb();
            // get reference to rootpoa & activate the POAManager

            if (myRootPOA == null) {
                myRootPOA = POAHelper.narrow(myOrb.resolve_initial_references("RootPOA"));
                myRootPOA.the_POAManager().activate();
            }

            if (myRootContext == null)
                myRootContext = NamingContextExtHelper.narrow(myOrb.resolve_initial_references("NameService"));

            //Attempt to create com.traclabs context, if already there, don't
            // bother
            NameComponent comComponent = new NameComponent("com", "");
            NameComponent[] comComponentArray = { comComponent };
            myRootContext.bind_new_context(comComponentArray);
            NamingContextExt comContext = NamingContextExtHelper
                    .narrow(myRootContext.resolve_str("com"));
            NameComponent traclabsComponent = new NameComponent("traclabs", "");
            NameComponent[] traclabsComponentArray = { traclabsComponent };
            comContext.bind_new_context(traclabsComponentArray);
        } catch (org.omg.CosNaming.NamingContextPackage.AlreadyBound e) {
        } catch (Exception e) {
            Logger.getLogger(OrbUtils.class).warn(
                    "nameserver not found, polling again: " + e.getLocalizedMessage());
            myOrb.shutdown(false);
            myOrb = null;
            myRootPOA = null;
            myRootContext = null;
            return false;
        }

        //Attempt to create biosim context, if already there, don't bother
        try {
            NamingContextExt comContext = NamingContextExtHelper
                    .narrow(myRootContext.resolve_str("com"));
            NamingContextExt traclabsContext = NamingContextExtHelper
                    .narrow(comContext.resolve_str("traclabs"));
            NameComponent biosimComponent = new NameComponent("biosim", "");
            NameComponent[] biosimComponentArray = { biosimComponent };
            traclabsContext.bind_new_context(biosimComponentArray);
        } catch (org.omg.CosNaming.NamingContextPackage.AlreadyBound e) {
        } catch (Exception e) {
            Logger.getLogger(OrbUtils.class).warn(
                    "had trouble creating biosim context: ", e);
        }

        try {
            NamingContextExt comContext = NamingContextExtHelper
                    .narrow(myRootContext.resolve_str("com"));
            NamingContextExt traclabsContext = NamingContextExtHelper
                    .narrow(comContext.resolve_str("traclabs"));
            myBiosimNamingContext = NamingContextExtHelper
                    .narrow(traclabsContext.resolve_str("biosim"));
            initializeOrbRunOnce = true;
        } catch (Exception e) {
            Logger.getLogger(OrbUtils.class).warn(
                    "had trouble retrieving biosim context: ", e);
        }
        return true;
    }

    /**
     * Sleeps for a few seconds. Used when we can't find the naming service and
     * need to poll again after a few seconds.
     */
    public static void sleepAwhile() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
    }
    
    /**
     * Sleeps for a few seconds. Used when we can't find the naming service and
     * need to poll again after a few seconds.
     */
    public static void sleepAwhile(int pMilliseconds) {
        try {
            Thread.sleep(pMilliseconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     * This method take a POA servant object and transforms it into a CORBA
     * object
     * 
     * @param poa
     *            the POA object to transform
     * @return the transformed CORBA object
     */
    public static org.omg.CORBA.Object poaToCorbaObj(
            org.omg.PortableServer.Servant poa) {
        org.omg.CORBA.Object newObject = null;
        try {
            initialize();
            newObject = myRootPOA.servant_to_reference(poa);
        } catch (org.omg.PortableServer.POAPackage.ServantNotActive e) {
            e.printStackTrace();
        } catch (org.omg.PortableServer.POAPackage.WrongPolicy e) {
            e.printStackTrace();
        }
        return newObject;
    }

    /**
     * This method take a CORBA object and transforms it into a POA servant
     * object
     * 
     * @param pObject
     *            the CORBA object to transform
     * @return the transformed POA servant object
     */
    public static org.omg.PortableServer.Servant corbaObjToPoa(
            org.omg.CORBA.Object pObject) {
        org.omg.PortableServer.Servant newPoa = null;
        try {
            initialize();
            newPoa = myRootPOA.reference_to_servant(pObject);
        } catch (org.omg.PortableServer.POAPackage.ObjectNotActive e) {
            e.printStackTrace();
        } catch (org.omg.PortableServer.POAPackage.WrongPolicy e) {
            e.printStackTrace();
        } catch (org.omg.PortableServer.POAPackage.WrongAdapter e) {
            e.printStackTrace();
        }
        return newPoa;
    }

    /**
     * @param pORBProperties
     *            The myORBProperties to set.
     */
    public static void setMyORBProperties(Properties pORBProperties) {
        myCustomORBProperties = pORBProperties;
    }
    
    public static void initializeServerForStandalone(){
        initializeNamingServer(STANDALONE_NAMESERVER_PORT);
    }
    
    public static void initializeClientForStandalone(){
        initializeNamingServer(STANDALONE_NAMESERVER_PORT);
    }
    
    public static void initializeNamingServer(int OAPort){
    	initializeNamingServer("localhost", OAPort);
    }
    
    public static void initializeNamingServerWithFile(File namingServiceIor){
        Properties newORBProperties = new Properties();
        newORBProperties.setProperty("ORBInitRef.NameService", namingServiceIor.toURI().toString());
        setMyORBProperties(newORBProperties);
        resetInit();
    }
    
    public static void initializeNamingServer(String hostname, int port){
        Properties newORBProperties = new Properties();
        newORBProperties.setProperty("ORBInitRef.NameService", "corbaloc::"+hostname+":" + port + "/NameService");
        setMyORBProperties(newORBProperties);
        resetInit();
    }
    
    public static void startStandaloneNameServer(){
    	if (myNamingServiceThread != null)
    		return;
        myNamingServiceThread = new Thread(new NamingServiceThread());
        myNamingServiceThread.start();
    }
    
    public static void initializeLog(boolean pDebug){
    	//load mysql driver
    	try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			//ignore
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        Properties logProps = new Properties();
        if (pDebug)
            logProps.setProperty("log4j.rootLogger", "DEBUG, rootAppender");
        else
            logProps.setProperty("log4j.rootLogger", "INFO, rootAppender");
        logProps.setProperty("log4j.appender.rootAppender",
                "org.apache.log4j.ConsoleAppender");
        logProps.setProperty("log4j.appender.rootAppender.layout",
                "org.apache.log4j.PatternLayout");
        logProps.setProperty(
                "log4j.appender.rootAppender.layout.ConversionPattern",
                "%5p [%c] - %m%n");
        PropertyConfigurator.configure(logProps);
    }
    
    public static void initializeLog(){
        initializeLog(false);
    }
    
    private static class NamingServiceThread implements Runnable {
        public void run() {
            String portArg = "-DOAPort=" + Integer.toString(STANDALONE_NAMESERVER_PORT);
            String ORBArg = "-Dorg.omg.CORBA.ORBClass=" + ORB_CLASS;
            String[] nameServerArgs = {portArg, ORBArg};
            NameServer.main(nameServerArgs);
        }
    }
    
    public static String resolveXMLLocation(String xmlLocation) {
    	if (xmlLocation == null)
    		return null;
    	//first see if we can find it in the classpath
    	String fullFileLocation = findXmlInClasspath(xmlLocation);
    	if (fullFileLocation != null)
    		return fullFileLocation;
        //next look for it as a raw file
        File xmlFile = new File(xmlLocation);
        if (xmlFile.exists())
        	return xmlFile.toString();
        //maybe it's in the classpath, but user forgot default location
    	fullFileLocation = findXmlInClasspath("com/traclabs/biosim/server/framework/configuration/" + xmlLocation);
    	if (fullFileLocation != null)
    		return fullFileLocation;
        //give up
        return null;
	}
    
    private static String findXmlInClasspath(String xmlLocation){
    	Logger.getLogger(OrbUtils.class).debug("Looking for "+xmlLocation+" in classpath");
        URL foundURL = Thread.currentThread().getContextClassLoader().getResource(xmlLocation);
        if (foundURL != null){
        	String urlString = foundURL.toString();
        	if (urlString.length() > 0)
        		return urlString;
        }
        return null;
    }
}