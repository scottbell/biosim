package biosim.server.water;

public abstract class WaterRSSubSystem{
	protected float currentPower = 0;
	protected float powerNeeded =100;
	protected float waterNeeded = 0.25f;
	protected WaterRSImpl myWaterRS;
	protected boolean hasEnoughPower = false;
	protected boolean hasEnoughWater = false;
	protected float waterLevel = 0;
	
	public WaterRSSubSystem(WaterRSImpl pWaterRSImpl){
		myWaterRS = pWaterRSImpl;
	}
	
	public void reset(){
		currentPower = 0;
		hasEnoughPower = false;
		hasEnoughWater = false;
		waterLevel = 0;
	}
	
	public boolean hasPower(){
		return hasEnoughPower;
	}
	
	public boolean hasWater(){
		return hasEnoughWater;
	}
	
	public float getPowerNeeded(){
		return powerNeeded;
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
		if (waterLevel < waterNeeded){
			hasEnoughWater = false;
		}
		else{
			hasEnoughWater = true;
		}
	}

	public abstract void tick();
}
