package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * Rice
 * @author    Scott Bell
 */

public class Rice extends Erectophile{
	public Rice(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[0] = 6591400f;
		canopyClosureConstants[1] = 25776f;
		canopyClosureConstants[3] = .0064532f;
		canopyClosureConstants[5] = -3748f;
		canopyClosureConstants[7] = -0.043378f;
		canopyClosureConstants[12] = .00004562f;
		canopyClosureConstants[16] = .0000045207f;
		canopyClosureConstants[17] = -.000000014936f;

		canopyQYConstants[6] = 0.036186f;
		canopyQYConstants[7] = 0.000061457f;
		canopyQYConstants[8] = -0.000000024322f;
		canopyQYConstants[11] = -0.0000000091477f;
		canopyQYConstants[17] = 0.000000000003889f;
		canopyQYConstants[18] = -0.0000000026712f;
	}

	public PlantType getPlantType(){
		return PlantType.WHEAT;
	}

	public float getPPFNeeded(){
		float cropArea = myShelfImpl.getCropArea();
		float constantPPF = 115f; //in moles per meters squared days
		return (constantPPF * cropArea) / 24;
	}
	
	protected float getCarbonUseEfficiency24(){
		return 0.64f;
	}

	protected float getBCF(){
		return 0.44f;
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
