package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * Tomato
 * @author    Scott Bell
 */

public class Tomato extends Planophile{
	public Tomato(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[0] = 627740f;
		canopyClosureConstants[1] = 3172.4f;
		canopyClosureConstants[6] = 24.281f;
		canopyClosureConstants[10] = 0.44686f;
		canopyClosureConstants[11] = .0056276f;
		canopyClosureConstants[16] = -.0000030690f;

		canopyQYConstants[6] = 0.040061f;
		canopyQYConstants[7] = 0.00005688f;
		canopyQYConstants[8] = -0.000000022598f;
		canopyQYConstants[12] = -0.00000001182f;
		canopyQYConstants[13] = 0.00000000000550264f;
		canopyQYConstants[16] = -0.0000000071241f;
	}

	public PlantType getPlantType(){
		return PlantType.DRY_BEAN;
	}
	
	protected float getConstantPPF(){
		return 312.5f;
	}
	
	protected float getCarbonUseEfficiency24(){
		return 0.0f;
	}

	protected float getBCF(){
		return 0.42f;
	}
	
	protected float getCUEMax(){
		return 0.65f;
	}
	
	protected float getTimeAtOrganFormation(){
		return 41f;
	}
	
	protected float getCUEMin(){
		return 0f;
	}

	protected float getPhotoperiod(){
		return 12f;
	}

	protected float getN(){
		return 1.0f;
	}

	protected float getCQYMin(){
		return .001f;
	}

	protected float getTimeAtCanopySenescence(){
		return 56f;
	}

	protected float getTimeAtCropMaturity(){
		return 80f;
	}

	protected float getOPF(){
		return 1.09f;
	}

	protected float getFreshFactor(){
		return 12.999f;
	}

	public static float getFractionOfEdibleBiomass(){
		return 0.70f;
	}
	
	/**
	 * Returns calories per kilogram
	*/
	public static float getCaloriesPerKilogram(){
		return 220f;
	}
	
	protected float getCurrentFractionOfEdibleBiomass(){
		if (myAge > getTimeAtOrganFormation())
			return getFractionOfEdibleBiomass();
		else
			return 0f;
	}

	protected float getEdibleFreshBasisWaterContent(){
		return 94f;
	}

	protected float getInedibleFreshBasisWaterContent(){
		return 90f;
	}
}
