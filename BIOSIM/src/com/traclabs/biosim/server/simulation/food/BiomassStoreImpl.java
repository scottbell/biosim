package biosim.server.food;

import biosim.idl.food.*;

public class BiomassStoreImpl extends BiomassStorePOA {
	private float biomassLevel;
	private float biomassCapacity;

	public BiomassStoreImpl(){
		biomassLevel = 0.0f;
		biomassCapacity = 10.0f;
	}

	public BiomassStoreImpl (float initialBiomassLevel, float  initialBiomassCapacity){
		biomassLevel = initialBiomassLevel;
		biomassCapacity = initialBiomassCapacity;
	}
	
	void setBiomassCapacity(float kilograms){
		biomassCapacity = kilograms;
	}

	void setBiomassLevel(float kilograms){
		biomassLevel = kilograms;
	}

	float addBiomass(float kilograms){
		if ((kilograms +biomassLevel) > biomassCapacity){
			float returnValue = (biomassCapacity - biomassLevel);
			biomassLevel = biomassCapacity;
			return returnValue;
		}
		else{
			biomassLevel = biomassLevel + kilograms;
			return kilograms;
		}
	}

	float takeBiomass(float kilograms){
		if ((biomassLevel - kilograms) < 0){
			biomassLevel = 0;
			if (kilograms < 0)
				return 0;
			else
				return biomassLevel;
		}
		else{
			biomassLevel = biomassLevel - kilograms;
			return kilograms;
		}
	}
	float getBiomassLevel(){
		return biomassLevel;
	}

	public void tick(){
		System.out.println("Biomass Store has been ticked!");
	}
	public String getModuleName(){
		return "BiomassStore";
	}
}
