package biosim.server.water;

public class BWP extends WaterRSSubSystem{
	private final static float powerNeeded=100; 
	private final static float waterNeeded = 0.2f;
	private float currentPower = 0;
	private RO myRO;
	private AES myAES;
	private PPS myPPS;
	private boolean hasCollectedReferences = false;
	
	public BWP(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
	}
	
	private void collectReferences(){
		if (!hasCollectedReferences){
			myRO = myWaterRS.getRO();
			myAES = myWaterRS.getAES();
			myPPS = myWaterRS.getPPS();
			hasCollectedReferences = true;
		}
	}
	
	public float getWaterWanted(){
		if (!hasEnoughPower){
			hasEnoughWater = true;
			return 0;
		}
		else
			return waterNeeded;
	}
	
	private void pushWater(){
		myWaterRS.getRO().addWater(waterLevel);
		waterLevel = 0;
	}
	
	public void tick(){
		collectReferences();
		pushWater();
	}
}
