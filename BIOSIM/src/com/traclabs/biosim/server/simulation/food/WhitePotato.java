package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * WhitePotato
 * @author    Scott Bell
 */

public class WhitePotato extends Planophile{
	public WhitePotato(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[0] = 657730f;
		canopyClosureConstants[1] = 8562.6f;
		canopyClosureConstants[6] = 0.042749f;
		canopyClosureConstants[10] = 0.00000088437f;
		canopyClosureConstants[17] = -0.000017905f;

		canopyQYConstants[6] = 0.046929f;
		canopyQYConstants[7] = 0.000050910f;
		canopyQYConstants[8] = -0.000000021878f;
		canopyQYConstants[14] = 0.0000000000000043976f;
		canopyQYConstants[17] = -0.000000000015272f;
		canopyQYConstants[21] = -0.000000000019602f;
	}

	public PlantType getPlantType(){
		return PlantType.DRY_BEAN;
	}
	
	protected float getConstantPPF(){
		return 28000000f;
	}
	
	protected float getCarbonUseEfficiency24(){
		return 0.0f;
	}

	protected float getBCF(){
		return 0.41f;
	}
	
	protected float getCUEMax(){
		return 0.625f;
	}
	
	protected float getTimeAtOrganFormation(){
		return 45f;
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
		return 0.02f;
	}

	protected float getTimeAtCanopySenescence(){
		return 75f;
	}

	protected float getTimeAtCropMaturity(){
		return 138f;
	}

	protected float getOPF(){
		return 1.02f;
	}

	protected float getFreshFactor(){
		return 6.4988f;
	}

	public static float getFractionOfEdibleBiomass(){
		return 1.00f;
	}
	
	/**
	 * Returns calories per kilogram
	*/
	public static float getCaloriesPerKilogram(){
		return 760f;
	}
	
	protected float getCurrentFractionOfEdibleBiomass(){
		if (myAge > getTimeAtOrganFormation())
			return getFractionOfEdibleBiomass();
		else
			return 0f;
	}

	protected float getEdibleFreshBasisWaterContent(){
		return 80f;
	}

	protected float getInedibleFreshBasisWaterContent(){
		return 90f;
	}
}
