package biosim.server.water;

public class BWP{
	private float currentPower = 0;
	private final static float powerNeededNominally =100; 
	private final static float waterWantedNominally  = 0.3f;
	private float powerWantedNow = powerNeededNominally; 
	private float waterWantedNow = 0; 
	private float waterLevel = 0;
	private RO myRO;
	private AES myAES;
	private PPS myPPS;
	private WaterRSImpl myWaterRS;
	private boolean hasCollectedReferences = false;
	private String status = "off";
	private boolean hasEnoughPower = false;
	
	public BWP(WaterRSImpl pWaterRSImpl){
		myWaterRS = pWaterRSImpl;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void addPower(float pPower){
		currentPower = pPower;
		if (currentPower < powerWantedNow){
			hasEnoughPower = false;
		}
		else{
			hasEnoughPower = true;
		}
	}
	
	private void collectReferences(){
		if (!hasCollectedReferences){
			myRO = myWaterRS.getRO();
			myAES = myWaterRS.getAES();
			myPPS = myWaterRS.getPPS();
			hasCollectedReferences = true;
		}
	}
	
	public void addWater(float pWater){
		waterLevel = pWater;
	}
	
	public float getWaterWanted(){
		if (hasEnoughPower)
			waterWantedNow = waterWantedNominally;
		else{
			waterWantedNow = 0;
		}
		return waterWantedNow;
	}
	
	private void checkStatus(){
		status = ("nominal");
		if (!hasEnoughPower)
			status = ("needs power");
		else if (waterLevel == 0)
			status = ("needs water");
	}
	
	private void pushWater(){
		myWaterRS.getRO().addWater(waterLevel);
		waterLevel = 0;
	}
	
	public void tick(){
		collectReferences();
		checkStatus();
		pushWater();
	}
}
