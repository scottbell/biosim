package biosim.server.water;

public class BWP{
	private float currentPower = 0;
	private final static float powerNeededNominally =100; 
	private final static float waterWantedNominally  = 1;
	private float powerWantedNow =0; 
	private float waterWantedNow =0; 
	private float waterLevel = 0;
	private RO myRO;
	private AES myAES;
	private PPS myPPS;
	private WaterRSImpl myWaterRS;
	private boolean hasCollectedReferences = false;
	private String status = "needs power\nneeds water";
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
		else
			waterWantedNow = 0;
		return waterWantedNow;
	}
	
	public void checkStatus(){
		StringBuffer statusBuffer = new StringBuffer();
		if (!hasEnoughPower)
			statusBuffer.append("   needs power");
		if (waterLevel < waterWantedNow)
			statusBuffer.append("   needs water");
	}
	
	public void pushWater(){
	}
	
	public void tick(){
		collectReferences();
		checkStatus();
		pushWater();
	}
}
