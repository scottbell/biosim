package biosim.server.crew;

import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.server.util.*;

public class CrewPersonImpl extends CrewPersonPOA {
	private String myName;
	private ActivityImpl myCurrentActivity;
	private SimEnvironment myCurrentEnvironment;
	private CrewGroupImpl myCrewGroup;
	private boolean hasCollectedReferences = false;
	private int currentOrder = 0;
	private int timeActivityPerformed = 0;

	public CrewPersonImpl(String pName, CrewGroupImpl pCrewGroup){
		myCrewGroup = pCrewGroup;
		myName = pName;
		myCurrentActivity = myCrewGroup.getRawActivityByOrder(currentOrder);
	}

	public String getName(){
		return myName;
	}

	public org.omg.CORBA.Object getCurrentActivity(){
		return (BioSimUtilsImpl.poaToCorbaObj(myCurrentActivity));
	}

	public void setCurrentActivity(org.omg.CORBA.Object pActivity){
		myCurrentActivity = (ActivityImpl)(BioSimUtilsImpl.corbaObjToPoa(pActivity));
	}

	public String toString(){
		return (myName + " now performing activity " +myCurrentActivity.getName() +
		        " for " + myCurrentActivity .getTimeLength() + " hours");
	}

	private void collectReferences(){
		try{
			if (!hasCollectedReferences)
				myCurrentEnvironment = SimEnvironmentHelper.narrow(BioSimUtilsImpl.getNCRef().resolve_str("SimEnvironment"));
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't find SimEnvironment!!");
			e.printStackTrace(System.out);
		}
	}
	
	private void advanceCurrentOrder(){
		currentOrder++;
		if (currentOrder >= (myCrewGroup.getNumberOfActivities()))
			currentOrder = 1;
	}

	public void processTick(){
		collectReferences();
		//done with this timestep, advance 1 timestep
		if (myCurrentActivity.getName().equals("Dead"))
			return;
		timeActivityPerformed++;
		if (timeActivityPerformed >= myCurrentActivity.getTimeLength()){
			advanceCurrentOrder();
			myCurrentActivity = myCrewGroup.getRawActivityByOrder(currentOrder);
			timeActivityPerformed = 0;
		}
		consumeResources();
		System.out.println("CrewPerson: "+toString());
	}
	
	private void consumeResources(){
	}
}
