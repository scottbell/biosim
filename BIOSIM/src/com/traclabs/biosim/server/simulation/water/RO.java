package biosim.server.water;

public class RO{
	private float currentPower = 0f;
	private static final float powerNeeded =100; 
	private BWP myBWP;
	private AES myAES;
	private PPS myPPS;
	private WaterRSImpl myWaterRS;
	private boolean hasCollectedReferences = false;
	private String status = "off";
	private boolean hasEnoughPower = false;
	private float waterLevel = 0;
	
	public RO(WaterRSImpl pWaterRSImpl){
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
	
	private void checkStatus(){
		status = ("nominal");
		if (!hasEnoughPower)
			status = ("needs power");
		else if (waterLevel == 0)
			status = ("needs water");
	}
	
	public void addWater(float pWater){
		waterLevel = pWater;
	}
	
	private void pushWater(){
		myWaterRS.getAES().addWater(waterLevel);
		waterLevel = 0;
	}
	
	public void tick(){
		collectReferences();
		checkStatus();
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
