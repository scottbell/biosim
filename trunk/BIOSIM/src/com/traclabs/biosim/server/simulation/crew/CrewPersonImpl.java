package biosim.server.crew;

import biosim.idl.crew.*;

public class CrewPersonImpl extends CrewPersonPOA {
	private String myName;
	private Activity myCurrentActivity;

	public CrewPersonImpl(String pName){
		myName = pName;
	}

	public String getName(){
		return myName;
	}

	public Activity getCurrentActivity(){
		return myCurrentActivity;
	}

	public void setCurrentActivity(Activity pActivity){
		myCurrentActivity = pActivity;
	}

	public String toString(){
		return (myName + " performing activity " +myCurrentActivity.getName() + 
				" for " + myCurrentActivity .getTimeLength() + " hours");
	}
}
