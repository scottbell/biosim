package biosim.server.water;

public abstract class WaterRSSubSystem{
	protected float currentPower = 0;
	protected static final float powerNeeded =168;
	protected WaterRSImpl myWaterRS;
	protected boolean hasEnoughPower = false;
	protected float waterLevel = 0;
	
	public WaterRSSubSystem(WaterRSImpl pWaterRSImpl){
		myWaterRS = pWaterRSImpl;
	}
	
	public boolean hasPower(){
		return hasEnoughPower;
	}
	
	public boolean hasWater(){
		return (waterLevel > 0);
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

	public abstract void tick();
}
