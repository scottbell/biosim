package biosim.server.water;

public class AES{
	private float currentPower = 0f;
	private static final float powerNeeded =100; 
	private BWP myBWP;
	private RO myRO;
	private PPS myPPS;
	private WaterRSImpl myWaterRS;
	private boolean hasCollectedReferences = false;
	private String status = "off";
	private boolean hasEnoughPower = false;
	private float waterLevel = 0f;
	
	public AES(WaterRSImpl pWaterRSImpl){
		myWaterRS = pWaterRSImpl;
	}

	public boolean hasPower(){
		return hasEnoughPower;
	}
	
	public void addPower(float pPower){
		currentPower = pPower;
		if (currentPower < powerNeeded){
			hasEnoughPower = false;
		}
		else{
			hasEnoughPower = true;
		}
	}
	
	public void addWater(float pWater){
		waterLevel = pWater;
	}
	
	private void pushWater(){
		myWaterRS.getPPS().addWater(waterLevel);
		waterLevel = 0;
	}
	
	private void checkStatus(){
		status = ("nominal");
		if (!hasEnoughPower)
			status = ("needs power");
		else if (waterLevel == 0)
			status = ("needs water");
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
		checkStatus();
		pushWater();
	}
}
