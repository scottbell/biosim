package biosim.server.water;

public class BWP extends WaterRSSubSystem{
	private final static float powerNeeded=100; 
	private final static float waterWantedNominally  = 0.3f;
	private float waterWantedNow = 0;
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
		if (hasEnoughPower)
			waterWantedNow = waterWantedNominally;
		else{
			waterWantedNow = 0;
		}
		return waterWantedNow;
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
