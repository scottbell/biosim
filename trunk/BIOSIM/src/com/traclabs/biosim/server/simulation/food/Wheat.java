package biosim.server.simulation.food;

/**
 * Wheat
 * @author    Scott Bell
 */

public class Wheat extends PlantImpl{
	public Wheat(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[0] = 9.5488f * pow(10, 4);
		canopyClosureConstants[1] = 0f;
		canopyClosureConstants[2] = 0.3419f;
		canopyClosureConstants[3] = -1.9076f * pow(10, -4);
		canopyClosureConstants[4] = 0f;
		canopyClosureConstants[5] = 1.0686f * pow(10, 3);
		canopyClosureConstants[6] = 15.977f;
		canopyClosureConstants[7] = 1.9733f * pow(10, -4);
		canopyClosureConstants[8] = 0f;
		canopyClosureConstants[9] = 0f;
		canopyClosureConstants[10] = 0f;
		canopyClosureConstants[11] = 0f;
		canopyClosureConstants[12] = 0f;
		canopyClosureConstants[13] = 0f;
		canopyClosureConstants[14] = 0f;
		canopyClosureConstants[15] = 0f;
		canopyClosureConstants[16] = 0f;
		canopyClosureConstants[17] = 0f;
		canopyClosureConstants[18] = 0f;
		canopyClosureConstants[19] = 0f;
		canopyClosureConstants[20] = 0f;
		canopyClosureConstants[21] = 0f;
		canopyClosureConstants[22] = 0f;
		canopyClosureConstants[23] = 0f;
		canopyClosureConstants[24] = 0f;
		
	}

	public String getPlantType(){
		return "wheat";
	}
	
	protected float getBCF(){
		return 0.44f;
	}
	
	protected float getCarbonUseEfficiency24(){
		return 0.64f;
	}
	
	protected float getPhotoperiod(){
		return 20f;
	}
	
	protected float getN(){
		return 1.0f;
	}
}
