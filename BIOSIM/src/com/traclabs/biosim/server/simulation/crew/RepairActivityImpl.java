package biosim.server.crew;

import biosim.idl.crew.*;
/**
 * Repair Activities.
 *
 * @author    Scott Bell
 */

public class RepairActivityImpl extends ActivityImpl implements RepairActivityOperations {
	private long myMalfunctionID = 0;
	private String myModuleNameToRepair = "unknown";
	
	public RepairActivityImpl(String pModuleNameToRepair, long pMalfunctionID, int pTimeLength){
		super("repair", pTimeLength, 3);
		myMalfunctionID = pMalfunctionID;
		myModuleNameToRepair = pModuleNameToRepair;
	}
	
	public String getModuleNameToRepair(){
		return myModuleNameToRepair;
	}
	
	public long getMalfunctionIDToRepair(){
		return myMalfunctionID;
	}
}
