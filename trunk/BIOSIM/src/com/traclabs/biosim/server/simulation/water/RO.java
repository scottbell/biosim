package biosim.server.water;

public class RO extends WaterRSSubSystem{
	private BWP myBWP;
	private AES myAES;
	private PPS myPPS;
	private boolean hasCollectedReferences = false;
	
	public RO(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
	}
	
	private void pushWater(){
		myWaterRS.getAES().addWater(waterLevel);
		waterLevel = 0;
	}
	
	public void tick(){
		collectReferences();
		pushWater();
	}
	
	private void collectReferences(){
		if (!hasCollectedReferences){
			myBWP = myWaterRS.getBWP();
			myAES = myWaterRS.getAES();
			myPPS = myWaterRS.getPPS();
			hasCollectedReferences = true;
		}
	}
}
