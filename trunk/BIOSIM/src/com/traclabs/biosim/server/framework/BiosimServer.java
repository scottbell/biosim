package biosim.server.framework;

import biosim.idl.air.*;
import biosim.idl.crew.*;
import biosim.idl.food.*;
import biosim.idl.water.*;
import biosim.idl.power.*;
import biosim.idl.util.log.*;
import biosim.idl.environment.*;
import biosim.server.air.*;
import biosim.server.crew.*;
import biosim.server.food.*;
import biosim.server.water.*;
import biosim.server.power.*;
import biosim.server.environment.*;
import biosim.server.util.*;
import biosim.server.util.log.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
/**
 * The Biosim Server.  Creates an instance of each module (AirRS, FoodProcessor, WaterRS, etc..) and binds them to the nameserver.
 *
 * @author    Scott Bell
 */

public class BiosimServer {
	/**
	* Instantiates the server and binds it to the name server.
	* @param args aren't used for anything
	*/
	public static void main(String args[]) {
		try{
			// create and initialize the ORB
			ORB orb = OrbUtils.getORB();
			NamingContextExt ncRef = OrbUtils.getNCRef();
			POA rootpoa = OrbUtils.getRootPOA();
			rootpoa.the_POAManager().activate();

			// create servants and register them with ORB
			SimEnvironmentImpl mySimEnvironmentImpl = new SimEnvironmentImpl();
			AirRSImpl myAirRSImpl = new AirRSImpl();
			CO2StoreImpl myCO2StoreImpl = new CO2StoreImpl();
			O2StoreImpl myO2StoreImpl = new O2StoreImpl();
			CrewGroupImpl myCrewImpl = new CrewGroupImpl();
			BiomassRSImpl myBiomassRSImpl = new BiomassRSImpl();
			BiomassStoreImpl myBiomassStoreImpl = new BiomassStoreImpl();
			FoodProcessorImpl myFoodProcessorImpl = new FoodProcessorImpl();
			FoodStoreImpl myFoodStoreImpl = new FoodStoreImpl();
			PowerPSImpl myPowerPSImpl = new SolarPowerPS();
			PowerStoreImpl myPowerStoreImpl = new PowerStoreImpl();
			WaterRSImpl myWaterRSImpl = new WaterRSImpl();
			GreyWaterStoreImpl myGreyWaterStoreImpl = new GreyWaterStoreImpl();
			PotableWaterStoreImpl myPotableWaterStoreImpl = new PotableWaterStoreImpl();
			DirtyWaterStoreImpl myDirtyWaterStoreImpl = new DirtyWaterStoreImpl();
			LoggerImpl myLoggerImpl = new LoggerImpl();
			BioDriverImpl myBioDriverImpl = new BioDriverImpl();

			// bind the Object References in Naming
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(new SimEnvironmentPOATie(mySimEnvironmentImpl));
			NameComponent path[] = ncRef.to_name(mySimEnvironmentImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new AirRSPOATie(myAirRSImpl));
			path = ncRef.to_name(myAirRSImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new CO2StorePOATie(myCO2StoreImpl));
			path = ncRef.to_name(myCO2StoreImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new O2StorePOATie(myO2StoreImpl));
			path = ncRef.to_name(myO2StoreImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new CrewGroupPOATie(myCrewImpl));
			path = ncRef.to_name(myCrewImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new BiomassRSPOATie(myBiomassRSImpl));
			path = ncRef.to_name(myBiomassRSImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new BiomassStorePOATie(myBiomassStoreImpl));
			path = ncRef.to_name(myBiomassStoreImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new FoodProcessorPOATie(myFoodProcessorImpl));
			path = ncRef.to_name(myFoodProcessorImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new FoodStorePOATie(myFoodStoreImpl));
			path = ncRef.to_name(myFoodStoreImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new PowerPSPOATie(myPowerPSImpl));
			path = ncRef.to_name(myPowerPSImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new PowerStorePOATie(myPowerStoreImpl));
			path = ncRef.to_name(myPowerStoreImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new WaterRSPOATie(myWaterRSImpl));
			path = ncRef.to_name(myWaterRSImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new PotableWaterStorePOATie(myPotableWaterStoreImpl));
			path = ncRef.to_name(myPotableWaterStoreImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new GreyWaterStorePOATie(myGreyWaterStoreImpl));
			path = ncRef.to_name(myGreyWaterStoreImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(new DirtyWaterStorePOATie(myDirtyWaterStoreImpl));
			path = ncRef.to_name(myDirtyWaterStoreImpl.getModuleName());
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(myLoggerImpl);
			path = ncRef.to_name("Logger");
			ncRef.rebind(path, ref);
			ref = rootpoa.servant_to_reference(myBioDriverImpl);
			path = ncRef.to_name("BioDriver");
			ncRef.rebind(path, ref);
			System.out.println("Biosim Server ready and waiting ...");
			// wait for invocations from clients
			orb.run();
		}
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("Biosim Server Exiting ...");
	}
}

