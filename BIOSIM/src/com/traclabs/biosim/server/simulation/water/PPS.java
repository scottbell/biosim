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
	}
}
