package biosim.server.crew;

import biosim.idl.crew.*;

public class ActivityImpl extends ActivityPOA {
	private String myName = "";
	private int myTimeLength = 0;
	private int myActivityIntensity = 0;
	
	public ActivityImpl(String pName, int pTimeLength, int pActivityIntensity){
		myName = pName;
		myTimeLength = pTimeLength;
		myActivityIntensity = pActivityIntensity;
	}
	
	public String getName(){
		return myName;
	}

	public int getTimeLength(){
		return myTimeLength;
	}
	
	public int getActivityIntensity (){
		return myActivityIntensity;
	}
}
