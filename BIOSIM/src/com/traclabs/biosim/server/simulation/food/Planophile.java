package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
/**
 * Planophile
 * @author    Scott Bell
 */

public abstract class Planophile extends PlantImpl{
	protected abstract float getBCF();
	protected abstract float getCarbonUseEfficiency24();
	protected abstract float getPhotoperiod();
	protected abstract float getNominalPhotoperiod();
	protected abstract float getN();
	protected abstract float getTimeAtCanopySenescence();
	protected abstract float getCQYMin();
	protected abstract float getTimeAtCropMaturity();
	protected abstract float getOPF();
	protected abstract float getProtectedFractionOfEdibleBiomass();
	protected abstract float getProtectedEdibleFreshBasisWaterContent();
	protected abstract float getProtectedInedibleFreshBasisWaterContent();
	protected abstract float getConstantPPF();
	public abstract PlantType getPlantType();
	public abstract String getPlantTypeString();
	public Planophile(ShelfImpl pShelfImpl, int pStartDay){
		super(pShelfImpl, pStartDay);
	}
	
	protected float calculateCanopyStomatalConductance(){
		float temperature = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].getTemperature();
		float vaporPressureDeficit = calculateVaporPressureDeficit();
		float netCanopyPhotosynthesis = calculateNetCanopyPhotosynthesis();
		//System.out.println("Planophile: temperature: "+temperature);
		//System.out.println("Planophile: vaporPressureDeficit: "+vaporPressureDeficit);
		//System.out.println("Planophile: CO2Concentration: "+getAverageCO2Concentration());
		//System.out.println("Planophile: netCanopyPhotosynthesis: "+netCanopyPhotosynthesis);
		float canopyStomatalConductance = (1.717f * temperature - 19.96f - 10.54f * vaporPressureDeficit) * (netCanopyPhotosynthesis / getAverageCO2Concentration());
		if (canopyStomatalConductance <= 0)
			return Float.MIN_VALUE;
		else
			return canopyStomatalConductance;
	}
	
	protected float calculateAtmosphericAeroDynamicConductance(){
		return 2.5f;
	}
}
