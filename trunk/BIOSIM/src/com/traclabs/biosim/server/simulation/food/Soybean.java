package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * Soybean
 * @author    Scott Bell
 */

public class Soybean extends Legume{
	public Soybean(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[0] = 6797800f;
		canopyClosureConstants[1] = -4365.8f;
		canopyClosureConstants[2] = 1.5573f;
		canopyClosureConstants[5] = -43260f;
		canopyClosureConstants[6] = 33.959f;
		canopyClosureConstants[10] = 112.63f;
		canopyClosureConstants[13] = -.000000004911f;
		canopyClosureConstants[15] = -0.13637f;
		canopyClosureConstants[20] = .000066918f;
		canopyClosureConstants[21] = -.000000021367f;
		canopyClosureConstants[22] = 000000000015467f;

		canopyQYConstants[6] = 0.041513f;
		canopyQYConstants[7] = 0.000051157f;
		canopyQYConstants[8] = -0.000000020992f;
		canopyQYConstants[12] = 0.000000040864f;
		canopyQYConstants[16] = -0.000000021582f;
		canopyQYConstants[17] = -0.00000000010468f;
		canopyQYConstants[22] = 0.000000000000048541f;
		canopyQYConstants[24] = 0.0000000000000000000039259f;
	}

	public PlantType getPlantType(){
		return PlantType.DRY_BEAN;
	}

	public float getPPFNeeded(){
		float cropArea = myShelfImpl.getCropArea();
		float constantPPF = 115f; //in moles per meters squared days
		return (constantPPF * cropArea) / 24;
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
