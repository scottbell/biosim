package com.traclabs.biosim.server.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
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

    private static Properties myORBProperties;

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
        initialize();
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
        try{
            module = BioModuleHelper.narrow(getNamingContext(pID).resolve_str(
                pModuleName));
        }
        catch (Exception e){
            Logger.getLogger(OrbUtils.class).info(
                    "(id="+pID+") Had problems getting module:"+pModuleName+" "+ e);
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
                    .resolve_str("" + pID));
        } catch (Exception e) {
            e.printStackTrace();
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
            NamingContext IDContext = myBiosimNamingContext
                    .bind_new_context(idComponents);
        } catch (org.omg.CosNaming.NamingContextPackage.AlreadyBound e) {
        } catch (Exception e) {
            Logger.getLogger(OrbUtils.class).info(
                    "Had problems getting naming context " + e);
            e.printStackTrace();
        }
    }
    
    private static void initialize(){
        while (!initializeLoop()){
            sleepAwhile();
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
            //Done
            
            String[] nullArgs = null;
            // create and initialize the ORB
            if (myOrb == null)
                myOrb = ORB.init(nullArgs, myORBProperties);
            
            // get reference to rootpoa & activate the POAManager

            if (myRootPOA == null){
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
            Logger.getLogger(OrbUtils.class).info(
                    "OrbUtils: nameserver not found, polling again: " + e);
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Sleeps for a few seconds. Used when we can't find the naming service and
     * need to poll again after a few seconds.
     */
    public static void sleepAwhile() {
        try {
            Thread.sleep(2000);
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
        myORBProperties = pORBProperties;
    }
}