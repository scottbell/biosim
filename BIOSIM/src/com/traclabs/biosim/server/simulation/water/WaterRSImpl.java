package biosim.server.water;

import biosim.idl.water.*;
import biosim.idl.power.*;
import biosim.server.util.*;

public class WaterRSImpl extends WaterRSPOA {
	private float currentPotableWaterProduced = 0f;
	private float currentGreyWaterProduced = 0f;
	private float currentPowerConsumed = 0f;
	private float currentDirtyWaterConsumed = 0f;
	private float currentGreyWaterConsumed = 0f;

	private boolean hasCollectedReferences = false;

	private BWP myBWP;
	private RO myRO;
	private AES myAES;
	private PPS myPPS;
	private PowerStore myPowerStore;
	private PotableWaterStore myPotableWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private GreyWaterStore myGreyWaterStore;

	public WaterRSImpl(){
		myBWP = new BWP(this);
		myRO = new RO(this);
		myAES = new AES(this);
		myPPS = new PPS(this);
	}

	public float getPotableWaterProduced(){
		return currentPotableWaterProduced;
	}
	
	public String getROStatus(){
		return myRO.getStatus();
	}
	
	public String getAESStatus(){
		return myAES.getStatus();
	}
	
	public String getPPSStatus(){
		return myPPS.getStatus();
	}
	
	public String getBWPStatus(){
		return myBWP.getStatus();
	}
	
	public RO getRO(){
		return myRO;
	}
	
	public AES getAES(){
		return myAES;
	}
	
	public PPS getPPS(){
		return myPPS;
	}
	
	public BWP getBWP(){
		return myBWP;
	}

	public float getGreyWaterProduced(){
		return currentGreyWaterProduced;
	}
	
	public float getGreyWaterConsumed(){
		return currentGreyWaterConsumed;
	}

	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	public float getDirtyWaterConsumed(){
		return currentDirtyWaterConsumed;
	}

	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("DirtyWaterStore"));
				myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("GreyWaterStore"));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
	}

	public void tick(){
		//collect references
		collectReferences();
		//gather power for each system
		gatherPower();
		//gather dirty & clean water for BWP
		gatherUnpotableWater();
		//get clean water from PPS and put in clean water store
		distributeCleanWater();
	}

	private void gatherPower(){
		float powerNeeded = 468;
		currentPowerConsumed = myPowerStore.takePower(powerNeeded);
		if (currentPowerConsumed < powerNeeded){
			myPPS.addPower(0);
			myRO.addPower(0);
			myBWP.addPower(0);
			myAES.addPower(0);
		}
		else{
			myPPS.addPower(168);
			myRO.addPower(100);
			myBWP.addPower(100);
			myAES.addPower(100);
		}
	}

	private void gatherUnpotableWater(){
		if (myDirtyWaterStore.getWaterLevel() != 0){
			currentDirtyWaterConsumed = myDirtyWaterStore.takeWater(myBWP.getWaterWanted());
			currentGreyWaterConsumed = 0;
		}
		else{
			currentDirtyWaterConsumed = 0;
			currentGreyWaterConsumed = myGreyWaterStore.takeWater(myBWP.getWaterWanted());
		}
		myBWP.addWater(currentDirtyWaterConsumed + currentGreyWaterConsumed);
	}

	private void distributeCleanWater(){
	}

	public String getModuleName(){
		return "WaterRS";
	}
}
