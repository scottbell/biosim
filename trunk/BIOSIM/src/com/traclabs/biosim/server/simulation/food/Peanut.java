package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * Peanut
 * @author    Scott Bell
 */

public class Peanut extends Legume{
	public Peanut(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[0] = 3748700f;
		canopyClosureConstants[1] = 2920f;
		canopyClosureConstants[4] = .000000094008f;
		canopyClosureConstants[5] = -18840f;
		canopyClosureConstants[6] = 23.912f;
		canopyClosureConstants[10] = 51.256f;
		canopyClosureConstants[15] = -0.05963f;
		canopyClosureConstants[16] = .0000055180f;
		canopyClosureConstants[20] = .000025969f;

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
		float constantPPF = 27f; //in moles per meters squared days
		return (constantPPF * cropArea) / 24;
	}

	protected float getBCF(){
		return 0.50f;
	}

	protected float getTimeAtOrganFormation(){
		return 49f;
	}

	protected float getCUEMax(){
		return 0.65f;
	}

	protected float getCUEMin(){
		return 0.30f;
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
		return 65f;
	}

	protected float getTimeAtCropMaturity(){
		return 110;
	}

	protected float getOPF(){
		return 1.19f;
	}

	protected float getFreshFactor(){
		return 7.7614f;
	}

	public static float getFractionOfEdibleBiomass(){
		return 0.49f;
	}
	
	protected float getCurrentFractionOfEdibleBiomass(){
		if (myAge > getTimeAtOrganFormation())
			return getFractionOfEdibleBiomass();
		else
			return 0f;
	}

	protected float getEdibleFreshBasisWaterContent(){
		return 5.6f;
	}

	protected float getInedibleFreshBasisWaterContent(){
		return 90f;
	}
}
