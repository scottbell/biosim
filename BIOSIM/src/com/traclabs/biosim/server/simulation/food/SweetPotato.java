package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * SweetPotato
 * @author    Scott Bell
 */

public class SweetPotato extends Planophile{
	public SweetPotato(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[0] = 1207000f;
		canopyClosureConstants[1] = 4948.4f;
		canopyClosureConstants[6] = 4.2978f;
		canopyClosureConstants[20] = .00000040109f;
		canopyClosureConstants[22] = .0000000000020193f;

		canopyQYConstants[6] = 0.039317f;
		canopyQYConstants[7] = 0.000056741f;
		canopyQYConstants[8] = -0.000000021797f;
		canopyQYConstants[11] = -0.000013836f;
		canopyQYConstants[12] = -0.0000000063397f;
		canopyQYConstants[17] = -0.000000000013464f;
		canopyQYConstants[18] = 0.0000000000000077362f;
	}

	public PlantType getPlantType(){
		return PlantType.DRY_BEAN;
	}

	public float getPPFNeeded(){
		float cropArea = myShelfImpl.getCropArea();
		float constantPPF = 28f; //in moles per meters squared days
		return (constantPPF * cropArea) / 24;
	}
	
	protected float getCarbonUseEfficiency24(){
		return 0.0f;
	}

	protected float getBCF(){
		return 0.44f;
	}
	
	protected float getCUEMax(){
		return 0.625f;
	}
	
	protected float getCUEMin(){
		return 0.50f;
	}

	protected float getPhotoperiod(){
		return 18f;
	}

	protected float getN(){
		return 1.0f;
	}

	protected float getCQYMin(){
		return .001f;
	}

	protected float getTimeAtCanopySenescence(){
		return getTimeAtCropMaturity();
	}

	protected float getTimeAtCropMaturity(){
		return 120f;
	}

	protected float getOPF(){
		return 1.02f;
	}

	protected float getFreshFactor(){
		return 7.3792f;
	}

	public float getFractionOfEdibleBiomass(){
		if (myAge > getTimeAtCanopySenescence())
			return 1f;
		else
			return 0f;
	}

	protected float getEdibleFreshBasisWaterContent(){
		return 71f;
	}

	protected float getInedibleFreshBasisWaterContent(){
		return 90f;
	}
}
