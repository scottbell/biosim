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
	
	public AES(WaterRSImpl pWaterRSImpl){
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
	}
}
