package biosim.server.framework;

import biosim.idl.framework.*;
/**
 * The Logger takes Logs and outputs them using the choosen handler
 * @author    Scott Bell
 */

public class MalfunctionImpl extends MalfunctionPOA  {
	private static int lastID = 0;
	private int myID;
	private MalfunctionIntensity myIntensity;
	private MalfunctionLength myLength;
	
	public MalfunctionImpl(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		if (lastID > java.lang.Integer.MAX_VALUE)
			lastID = 0;
		lastID++;
		myID = lastID;
		myIntensity = pIntensity;
		myLength = pLength;
	}
	
	public int getID(){
		return myID;
	}
	
	public MalfunctionIntensity getIntensity(){
		return myIntensity;
	}
	
	public MalfunctionLength getLength(){
		return myLength;
	}
}
