package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * DryBean
 * @author    Scott Bell
 */

public class DryBean extends Legume{
	public DryBean(ShelfImpl pShelfImpl){
		super(pShelfImpl);
		canopyClosureConstants[0] = 290410f;
		canopyClosureConstants[1] = 1559.4f;
		canopyClosureConstants[6] = 15.840f;
		canopyClosureConstants[10] = .0061120f;
		canopyClosureConstants[17] = -0.0000000037409f;
		canopyClosureConstants[24] = .00000000000000000096484f;

		canopyQYConstants[6] = 0.04191f;
		canopyQYConstants[7] = 0.000053852f;
		canopyQYConstants[8] = -0.000000021275f;
		canopyQYConstants[11] = -0.00001238f;
		canopyQYConstants[17] = -0.00000000001544f;
		canopyQYConstants[18] = 0.000000000000006469f;
	}

	public PlantType getPlantType(){
		return PlantType.DRY_BEAN;
	}

	public float getPPFNeeded(){
		float cropArea = myShelfImpl.getCropArea();
		float constantPPF = 24f; //in moles per meters squared days
		return (constantPPF * cropArea) / 24;
	}

	protected float getBCF(){
		return 0.45f;
	}
	
	protected float getCUEMax(){
		return 0.65f;
	}
	
	protected float getCUEMin(){
		return 0.50f;
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
		return 42f;
	}

	protected float getTimeAtCropMaturity(){
		return 63f;
	}

	protected float getOPF(){
		return 1.1f;
	}

	protected float getFreshFactor(){
		return 6.444f;
	}

	public float getFractionOfEdibleBiomass(){
		if (myAge > getTimeAtCanopySenescence())
			return 1f;
		else
			return 0f;
	}

	protected float getEdibleFreshBasisWaterContent(){
		return 10f;
	}

	protected float getInedibleFreshBasisWaterContent(){
		return 90f;
	}
}
