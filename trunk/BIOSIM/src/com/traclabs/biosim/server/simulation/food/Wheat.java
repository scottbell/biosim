package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * Wheat
 * @author    Scott Bell
 */

public class Wheat extends PlantImpl{
	public Wheat(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[0] = 95488f;;
		canopyClosureConstants[1] = 1068.6f;
		canopyClosureConstants[6] = 15.977f;
		canopyClosureConstants[10] = 0.3419f;
		canopyClosureConstants[11] = .00019733f;
		canopyClosureConstants[15] = -.00019076f;
		
	}

	public PlantType getPlantType(){
		return PlantType.WHEAT;
	}
	
	public float getPPFNeeded(){
		return 4791667f;
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
	
	protected float getCQYMin(){
		return .001f;
	}
	
	protected float getTimeTillCanopySenescence(){
		return 33f;
	}
	
	protected float getTimeTillCropMaturity(){
		return 62f;
	}
}
