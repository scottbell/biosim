package biosim.client.control;

import SIMULATION.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.*;

public class BioSimulator
{
 //Module Names
	private final static String crewName = "Crew";
	private final static  String energyPSName = "EnergyPS";
	private final static  String energyStoreName = "EnergyStore";
	private final static  String airRSName = "AirRS";
	private final static  String airStoreName = "AirStore";
	private final static  String biomassRSName = "BiomassRS";
	private final static  String biomassStoreName = "BiomassStore";
	private final static  String waterRSName = "WaterRS";
	private final static  String waterStoreName = "WaterStore";

	private Hashtable modules;
	private NamingContextExt ncRef;

	public BioSimulator(){
		initializeORB();
		collectReferences();
	}

	public static void main(String args[])
	{
		BioSimulator biosim = new BioSimulator();
		biosim.runSimulation();
	}
	
	public void runSimulation(){
		tick();
		//Play with crew object
		Crew myCrew = (Crew)(biosim.getBioModule("Crew"));
		if (myCrew == null){
			System.out.println("Couldn't find crew to test....");
		}
		else{
			myCrew.createCrewPerson("Bob Roberts");
			Activity newActivity = myCrew.getScheduledActivityByName("sleeping");
			org.omg.CORBA.Object myCrewPerson = myCrew.getCrewPerson("Bob Roberts");
			/*myCrewPerson.setCurrentActivity(newActivity);
			Activity currentActivity = myCrewPerson.getCurrentActivity();
			System.out.println("Current state for  "+myCrewPerson.getName()+"  is " +currentActivity.name+ " for "+currentActivity.timeLength+" hours");
			newActivity = myCrew.getScheduledActivityByName("eating");
			myCrewPerson.setCurrentActivity(newActivity);
			currentActivity = myCrewPerson.getCurrentActivity();
			System.out.println("Current state for "+myCrewPerson.getName()+" is "+currentActivity.name+ " for "+currentActivity.timeLength+" hours");
			*/
		}
		for (int i = 0; i < 10; i ++){
			tick();
		}
	}
	
	public BioModule getBioModule(String type){
		return (BioModule)(modules.get(type));
	}
	
	private void initializeORB(){
		//create null array
		String nullArgs[] = null;
		// create and initialize the ORB
		ORB orb = ORB.init(nullArgs, null);
		// get the root naming context
		org.omg.CORBA.Object objRef = null;
		try{
			objRef = orb.resolve_initial_references("NameService");
		}
		catch (org.omg.CORBA.ORBPackage.InvalidName e){
			System.out.println("Couldn't find nameserver! "+e);
		}
		// Use NamingContextExt instead of NamingContext. This is part of the Interoperable naming Service.
		ncRef = NamingContextExtHelper.narrow(objRef);
	}

	private void collectReferences(){
		// resolve the Objects Reference in Naming		
		modules = new Hashtable();
		System.out.println("Collecting references to modules...");
		try{
			Crew myCrew = CrewHelper.narrow(ncRef.resolve_str(crewName));
			modules.put(crewName , myCrew);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate Crew, skipping...");
		}
		try{
			EnergyPS myEnergyPS = EnergyPSHelper.narrow(ncRef.resolve_str(energyPSName));
			modules.put(energyPSName , myEnergyPS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate EnergyPS, skipping...");
		}
		try{
			EnergyStore myEnergyStore = EnergyStoreHelper.narrow(ncRef.resolve_str(energyStoreName));
			modules.put(energyStoreName , myEnergyStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate EnergyStore, skipping...");
		}
		try{
			AirRS myAirRS = AirRSHelper.narrow(ncRef.resolve_str(airRSName));
			modules.put(airRSName , myAirRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate AirRS, skipping...");
		}
		try{
			AirStore myAirStore = AirStoreHelper.narrow(ncRef.resolve_str(airStoreName));
			modules.put(airStoreName , myAirStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate AirStore, skipping...");
		}
		try{
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(ncRef.resolve_str(biomassRSName));
			modules.put(biomassRSName , myBiomassRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate BiomassRS, skipping...");
		}
		try{
			BiomassStore myBiomassStore = BiomassStoreHelper.narrow(ncRef.resolve_str(biomassStoreName));
			modules.put(biomassStoreName, myBiomassStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate BiomassStore, skipping...");
		}
		try{
			WaterRS myWaterRS = WaterRSHelper.narrow(ncRef.resolve_str(waterRSName));
			modules.put(waterRSName , myWaterRS);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate WaterRS, skipping...");
		}
		try{
			WaterStore myWaterStore = WaterStoreHelper.narrow(ncRef.resolve_str(waterStoreName));
			modules.put(waterStoreName , myWaterStore);
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't locate WaterStore, skipping...");
		}
	}

	public void tick(){
		//Iterate through modules and tick them
		System.out.println("Ticking modules...");
		for (Enumeration e = modules.elements(); e.hasMoreElements();){
			BioModule currentBioModule = (BioModule)(e.nextElement());
			currentBioModule.tick();
		}
	}
}

