package biosim.server.water;

public class AES extends WaterRSSubSystem{
	private static final float powerNeeded =100;
	private BWP myBWP;
	private RO myRO;
	private PPS myPPS;
	private boolean hasCollectedReferences = false;

	public AES(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
	}

	private void pushWater(){
		myWaterRS.getPPS().addWater(waterLevel);
		waterLevel = 0;
	}

	private void collectReferences(){
		if (!hasCollectedReferences){
			myBWP = myWaterRS.getBWP();
			myRO = myWaterRS.getRO();
			myPPS = myWaterRS.getPPS();
			hasCollectedReferences = true;
		}
	}

	public void tick(){
		collectReferences();
		pushWater();
	}
}
