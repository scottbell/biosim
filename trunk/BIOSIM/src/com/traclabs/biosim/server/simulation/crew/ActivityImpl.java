package biosim.server.crew;

import biosim.idl.crew.*;

public class ActivityImpl extends ActivityPOA {
	private String myName = "";
	private int myTimeLength = 0;
	
	public ActivityImpl(String pName, int pTimeLength){
		myName = pName;
		myTimeLength = pTimeLength;
	}
	
	public String getName(){
		return myName;
	}

	public int getTimeLength(){
		return myTimeLength;
	}
}
