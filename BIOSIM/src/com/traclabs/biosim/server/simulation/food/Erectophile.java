package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * Erectophile
 * @author    Scott Bell
 */

public abstract class Erectophile extends PlantImpl{
	protected abstract float getBCF();
	protected abstract float getCarbonUseEfficiency24();
	protected abstract float getPhotoperiod();
	protected abstract float getN();
	protected abstract float getTimeAtCanopySenescence();
	protected abstract float getCQYMin();
	protected abstract float getTimeAtCropMaturity();
	protected abstract float getOPF();
	protected abstract float getFreshFactor();
	protected abstract float getCurrentFractionOfEdibleBiomass();
	protected abstract float getEdibleFreshBasisWaterContent();
	protected abstract float getInedibleFreshBasisWaterContent();
	protected abstract float getConstantPPF();
	public abstract PlantType getPlantType();
	public Erectophile(ShelfImpl pShelfImpl){
		super(pShelfImpl);
	}
	
	protected float calculateCanopyStomatalConductance(){
		float relativeHumdity = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].getRelativeHumidity();
		float netCanopyPhotosynthesis = calculateNetCanopyPhotosynthesis();
		float CO2Concentration = getAverageCO2Concentration();
		//System.out.println("Erectophile: relativeHumdity: "+relativeHumdity);
		//System.out.println("Erectophile: netCanopyPhotosynthesis: "+netCanopyPhotosynthesis);
		//System.out.println("Erectophile: CO2Concentration: "+CO2Concentration);
		return 0.1389f + 15.32f * relativeHumdity * (netCanopyPhotosynthesis / CO2Concentration);
	}
	
	protected float calculateAtmosphericAeroDynamicConductance(){
		return 5.5f;
	}
}
