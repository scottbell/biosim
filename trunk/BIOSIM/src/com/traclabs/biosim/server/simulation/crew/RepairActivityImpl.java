package biosim.server.crew;

import biosim.idl.crew.*;
/**
 * Repair Activities.
 *
 * @author    Scott Bell
 */

public class RepairActivityImpl extends ActivityImpl implements RepairActivityOperations {
	private long myMalfunctionID = 0;
	private String myModuleToRepair = "Unknown";
	
	public RepairActivityImpl(String pModuleToRepair, long pMalfunctionID, int pTimeLength){
		super("Repair", pTimeLength, 3);
		myMalfunctionID = pMalfunctionID;
		myModuleToRepair = pModuleToRepair;
	}
	
	public String getModuleToRepair(){
		return myModuleToRepair;
	}
	
	public long getMalfunctionIDToRepair(){
		return myMalfunctionID;
	}
}
