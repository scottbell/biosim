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
		return PlantType.PEANUT;
	}
	
	protected float getConstantPPF(){
		return 312.5f;
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
		return 2.0f;
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

	protected float getFreshBasisWaterContent(){
		return 7.7614f;
	}

	public static float getFractionOfEdibleBiomass(){
		return 0.49f;
	}
	
	protected float getProtectedFractionOfEdibleBiomass(){
		return getFractionOfEdibleBiomass();
	}
	
	/**
	 * Returns calories per kilogram
	*/
	public static float getCaloriesPerKilogram(){
		return 5680f;
	}

	protected float getEdibleFreshBasisWaterContent(){
		return 0.056f;
	}

	protected float getInedibleFreshBasisWaterContent(){
		return 0.90f;
	}
}
