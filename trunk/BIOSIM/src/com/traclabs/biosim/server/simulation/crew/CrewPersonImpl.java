package biosim.server.crew;

import biosim.idl.crew.*;
import biosim.server.util.*;

public class CrewPersonImpl extends CrewPersonPOA {
	private String myName;
	private ActivityImpl myCurrentActivity;

	public CrewPersonImpl(String pName){
		myName = pName;
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
		return (myName + " performing activity " +myCurrentActivity.getName() + 
				" for " + myCurrentActivity .getTimeLength() + " hours");
	}
}
