package biosim.server.crew;

public class ActivityImpl extends ALSS.ActivityPOA {
	private String myName = "";
	private int myTimeLength = 0;
	
	//Shouldn't be called.  Use Schedule's createActivity
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
