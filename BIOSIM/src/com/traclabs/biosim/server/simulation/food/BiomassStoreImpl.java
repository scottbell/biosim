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
	
	public void setBiomassCapacity(float kilograms){
		biomassCapacity = kilograms;
	}

	public void setBiomassLevel(float kilograms){
		biomassLevel = kilograms;
	}

	public float addBiomass(float kilograms){
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

	public float takeBiomass(float kilograms){
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
	public float getBiomassLevel(){
		return biomassLevel;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "BiomassStore";
	}
}
