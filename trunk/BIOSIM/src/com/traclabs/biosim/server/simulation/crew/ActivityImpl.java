package biosim.server.crew;

// The package containing our stubs.
import SIMULATION.*;

public class ActivityImpl extends ActivityPOA {
	private String myName;
	private int myTimeLength;

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
