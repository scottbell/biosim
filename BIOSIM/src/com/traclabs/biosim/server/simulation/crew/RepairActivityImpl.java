package biosim.server.crew;

import biosim.idl.crew.*;
/**
 * Repair Activities.
 *
 * @author    Scott Bell
 */

public class RepairActivityImpl extends ActivityImpl implements RepairActivityOperations {
	private int myMalfunctionID = 0;
	private String myModuleToRepair = "Unknown";
	
	public RepairActivityImpl(String pName, int pTimeLength, int pActivityIntensity, String pModuleToRepair, int pMalfunctionID){
		super(pName, pTimeLength, pActivityIntensity);
		myMalfunctionID = pMalfunctionID;
		myModuleToRepair = pModuleToRepair;
	}
	
	public String getModuleToRepair(){
		return myModuleToRepair;
	}
	
	public int getMalfunctionIDToRepair(){
		return myMalfunctionID;
	}
}
