package biosim.server.framework;

import biosim.idl.framework.*;
/**
 * The Logger takes Logs and outputs them using the choosen handler
 * @author    Scott Bell
 */

public class MalfunctionImpl extends MalfunctionPOA  {
	private static long lastID = 0;
	private int myID;
	private MalfunctionIntensity myIntensity;
	private MalfunctionLength myLength;
	private String myName;
	private boolean performed;
	private int repairTime = 0;
	private int maxRepairTime = 1;
	
	public MalfunctionImpl(String pName, MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		if (lastID > java.lang.Long.MAX_VALUE)
			lastID = 0;
		lastID++;
		myID = lastID;
		myName = pName;
		myIntensity = pIntensity;
		myLength = pLength;
		if (myLength == MalfunctionLength.TEMPORARY_MALF)
			if (myIntensity == MalfunctionIntensity.LOW_MALF)
				maxRepairTime = 1;
			else if (myIntensity == MalfunctionIntensity.MEDIUM_MALF)
				maxRepairTime = 2;
			if (myIntensity == MalfunctionIntensity.SEVERE_MALF)
				maxRepairTime = 4;	
	}
	
	public int getID(){
		return myID;
	}
	
	public void repair(){
		if (myLength == MalfunctionLength.TEMPORARY_MALF)
			repairTime++;
	}
	
	public boolean isRepaired(){
		return (repairTime >= maxRepairTime);
	}
	
	public boolean hasPerformed(){
		return performed;
	}
	
	public void setPerformed(boolean pPerformed){
		performed = pPerformed;
	}
	
	public String getName(){
		return myName;
	}
	
	public MalfunctionIntensity getIntensity(){
		return myIntensity;
	}
	
	public MalfunctionLength getLength(){
		return myLength;
	}
}
