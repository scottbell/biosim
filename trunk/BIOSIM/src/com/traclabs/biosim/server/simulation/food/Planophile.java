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
	protected abstract float getN();
	protected abstract float getTimeTillCanopySenescence();
	protected abstract float getCQYMin();
	protected abstract float getTimeTillCropMaturity();
	protected abstract float getOPF();
	protected abstract float getFreshFactor();
	protected abstract float getFractionOfEdibleBiomass();
	protected abstract float getEdibleFreshBasisWaterContent();
	protected abstract float getInedibleFreshBasisWaterContent();
	public abstract float getPPFNeeded();
	public abstract PlantType getPlantType();
	
	public Planophile(ShelfImpl pShelfImpl){
		super(pShelfImpl);
	}
	
	protected float calculateCanopyStomatalConductance(){
		float temperature = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0].getTemperature();
		float vaporPressureDeficit = calculateVaporPressureDeficit();
		float netCanopyPhotosynthesis = calculateNetCanopyPhotosynthesis();
		System.out.println("Planophile: temperature: "+temperature);
		System.out.println("Planophile: vaporPressureDeficit: "+vaporPressureDeficit);
		System.out.println("Planophile: CO2Concentration: "+getAverageCO2Concentration());
		System.out.println("Planophile: netCanopyPhotosynthesis: "+netCanopyPhotosynthesis);
		return (1.717f * temperature - 19.96f - 10.54f * vaporPressureDeficit) * (netCanopyPhotosynthesis / getAverageCO2Concentration());
	}
	
	protected float calculateAtmosphericAeroDynamicConductance(){
		return 2.5f;
	}
}
