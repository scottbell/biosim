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

		canopyQYConstants[6] = .04191f;
		canopyQYConstants[7] = .000053852f;
		canopyQYConstants[8] = -.000000021275f;
		canopyQYConstants[11] = -.00001238f;
		canopyQYConstants[17] = -.00000000001544f;
		canopyQYConstants[18] = .000000000000006469f;
	}

	public PlantType getPlantType(){
		return PlantType.DRY_BEAN;
	}

	public float getPPFNeeded(){
		float cropArea = myShelfImpl.getCropArea();
		float constantPPF = 115f; //in moles per meters squared days
		return (constantPPF * cropArea) / 24;
	}
	
	protected float getCarbonUseEfficiency24(){
		return 0.0f;
	}

	protected float getBCF(){
		return 0.44f;
	}
	
	protected float getCUEMax(){
		return 0.65f;
	}
	
	protected float getCUEMin(){
		return 0.50f;
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

	protected float getOPF(){
		return 1.07f;
	}

	protected float getFreshFactor(){
		return 6.4546f;
	}

	public float getFractionOfEdibleBiomass(){
		if (myAge > 34)
			return 1f;
		else
			return 0f;
	}

	protected float getEdibleFreshBasisWaterContent(){
		return 12f;
	}

	protected float getInedibleFreshBasisWaterContent(){
		return 90f;
	}
}
