package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * Lettuce
 * @author    Scott Bell
 */

public class Lettuce extends Planophile{
	public Lettuce(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[1] = 10289f;
		canopyClosureConstants[2] = -3.7018f;
		canopyClosureConstants[4] = .00000036648f;
		canopyClosureConstants[6] = 1.751f;
		canopyClosureConstants[8] = .0000023127f;
		canopyClosureConstants[10] = 1.8760f;

		canopyQYConstants[6] = 0.044763f;
		canopyQYConstants[7] = 0.00005163f;
		canopyQYConstants[8] = -0.00000002075f;
		canopyQYConstants[11] = -0.000011701f;
		canopyQYConstants[17] = -0.000000000019731f;
		canopyQYConstants[18] = 0.0000000000000089265f;
	}

	public PlantType getPlantType(){
		return PlantType.DRY_BEAN;
	}
	
	protected float getConstantPPF(){
		return 196.76f;
	}
	
	protected float getCarbonUseEfficiency24(){
		return 0.625f;
	}

	protected float getBCF(){
		return 0.40f;
	}
	
	protected float getTimeAtOrganFormation(){
		return 1f;
	}
	
	protected float getCUEMax(){
		return 0.625f;
	}

	protected float getPhotoperiod(){
		return 16f;
	}

	protected float getN(){
		return 1.0f;
	}

	protected float getCQYMin(){
		return 0f;
	}

	protected float getTimeAtCanopySenescence(){
		return getTimeAtCropMaturity();
	}

	protected float getTimeAtCropMaturity(){
		return 60f;
	}

	protected float getOPF(){
		return 1.08f;
	}

	protected float getFreshFactor(){
		return 18.993f;
	}

	public static float getFractionOfEdibleBiomass(){
		return 0.95f;
	}
	
	/**
	 * Returns calories per kilogram
	*/
	public static float getCaloriesPerKilogram(){
		return 180f;
	}
	
	protected float getCurrentFractionOfEdibleBiomass(){
		if (myAge > getTimeAtOrganFormation())
			return getFractionOfEdibleBiomass();
		else
			return 0f;
	}

	protected float getEdibleFreshBasisWaterContent(){
		return 95f;
	}

	protected float getInedibleFreshBasisWaterContent(){
		return 90f;
	}
}
