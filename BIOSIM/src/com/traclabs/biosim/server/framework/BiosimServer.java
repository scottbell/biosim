package biosim.server.framework;

import biosim.idl.air.*;
import biosim.idl.crew.*;
import biosim.idl.food.*;
import biosim.idl.water.*;
import biosim.idl.power.*;
import biosim.idl.environment.*;
import biosim.server.air.*;
import biosim.server.crew.*;
import biosim.server.food.*;
import biosim.server.water.*;
import biosim.server.power.*;
import biosim.server.environment.*;
import biosim.server.util.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

public class BioModuleServer {

public static void main(String args[]) {
	try{
		// create and initialize the ORB
		ORB orb = BioSimUtilsImpl.getORB();
		NamingContextExt ncRef = BioSimUtilsImpl.getNCRef();
		POA rootpoa = BioSimUtilsImpl.getRootPOA();
		 
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
		 PowerPSImpl myPowerPSImpl = new PowerPSImpl();
		 PowerStoreImpl myPowerStoreImpl = new PowerStoreImpl();
		 WaterRSImpl myWaterRSImpl = new WaterRSImpl();
		 GreyWaterStoreImpl myGreyWaterStoreImpl = new GreyWaterStoreImpl();
		 PotableWaterStoreImpl myPotableWaterStoreImpl = new PotableWaterStoreImpl();
		 DirtyWaterStoreImpl myDirtyWaterStoreImpl = new DirtyWaterStoreImpl();
		
		// bind the Object References in Naming
		 org.omg.CORBA.Object ref = rootpoa.servant_to_reference(mySimEnvironmentImpl);
		 NameComponent path[] = ncRef.to_name(mySimEnvironmentImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myAirRSImpl);
		 path = ncRef.to_name(myAirRSImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myCO2StoreImpl);
		 path = ncRef.to_name(myCO2StoreImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myO2StoreImpl);
		 path = ncRef.to_name(myO2StoreImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myCrewImpl);
		 path = ncRef.to_name(myCrewImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myBiomassRSImpl);
		 path = ncRef.to_name(myBiomassRSImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myBiomassStoreImpl);
		 path = ncRef.to_name(myBiomassStoreImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myFoodProcessorImpl);
		 path = ncRef.to_name(myFoodProcessorImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myFoodStoreImpl);
		 path = ncRef.to_name(myFoodStoreImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myPowerPSImpl);
		 path = ncRef.to_name(myPowerPSImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myPowerStoreImpl);
		 path = ncRef.to_name(myPowerStoreImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myWaterRSImpl);
		 path = ncRef.to_name(myWaterRSImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myGreyWaterStoreImpl);
		 path = ncRef.to_name(myGreyWaterStoreImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myPotableWaterStoreImpl);
		 path = ncRef.to_name(myPotableWaterStoreImpl.getModuleName());
		 ncRef.rebind(path, ref);
		 ref = rootpoa.servant_to_reference(myDirtyWaterStoreImpl);
		 path = ncRef.to_name(myDirtyWaterStoreImpl.getModuleName());
		 ncRef.rebind(path, ref);

		 
		 System.out.println("BioModule Server ready and waiting ...");
		 // wait for invocations from clients
		 orb.run();
	}
	catch (Exception e) {
		System.err.println("ERROR: " + e);
		e.printStackTrace(System.out);
	}
	System.out.println("BioModule Server Exiting ...");
	}
}

