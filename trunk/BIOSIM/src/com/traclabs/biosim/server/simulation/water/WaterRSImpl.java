package biosim.server.water;

// The package containing our stubs.
import ALSS.*;

public class WaterRSImpl extends WaterRSPOA {
	private float currentPotableWaterProduced = 0f;
	private float currentGreyWaterProduced = 0f;
	private float currentPowerConsumed = 0f;
	private float currentDirtyWaterConsumed = 0f;
	
	public float getPotableWaterProduced(){
		return currentPotableWaterProduced;
	}

	public float getGreyWaterProduced(){
		return currentGreyWaterProduced;
	}

	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	public float getDirtyWaterConsumed(){
		return currentDirtyWaterConsumed;
	}

	public void tick(){
		System.out.println("WaterRS has been ticked!");
	}
	
	public String getModuleName(){
		return "WaterRS";
	}
}
