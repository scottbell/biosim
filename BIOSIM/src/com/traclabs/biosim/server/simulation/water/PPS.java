package biosim.server.water;

public class PPS{
	private float currentPower = 0;
	private static final float powerNeeded =168; 
	private BWP myBWP;
	private RO myRO;
	private AES myAES;
	private WaterRSImpl myWaterRS;
	private boolean hasCollectedReferences = false;
	private String status = "off";
	private boolean hasEnoughPower = false;
	private float waterLevel = 0;
	
	public PPS(WaterRSImpl pWaterRSImpl){
		myWaterRS = pWaterRSImpl;
	}
	
	public String getStatus(){
		return status;
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
	
	private void checkStatus(){
		status = ("nominal");
		if (!hasEnoughPower)
			status = ("needs power");
		else if (waterLevel == 0)
			status = ("needs water");
	}
	
	public float takePotableWater(){
		float potableWaterProduced = waterLevel;
		waterLevel = 0;
		return potableWaterProduced;
	}
	
	private void collectReferences(){
		if (!hasCollectedReferences){
			myBWP = myWaterRS.getBWP();
			myRO = myWaterRS.getRO();
			myAES = myWaterRS.getAES();
			hasCollectedReferences = true;
		}
	}

	public void tick(){
		collectReferences();
		checkStatus();
	}
}
