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

	public float addBiomass(float kilosRequested){
		float acutallyAdded = 0f;
		if ((kilosRequested + biomassLevel) > biomassCapacity){
			//adding more biomass than capacity
			acutallyAdded = (biomassCapacity - biomassLevel);
			biomassLevel += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = kilosRequested;
			biomassLevel += kilosRequested;
			return acutallyAdded;
		}
	}

	public float takeBiomass(float kilosRequested){
		//idiot check
		if (kilosRequested < 0){
			return 0f;
		}
		//asking for more biomass than exists
		if (kilosRequested > biomassLevel){
			float takenBiomass = biomassLevel;
			biomassLevel = 0;
			return takenBiomass;
		}
		//biomass exists for request
		else{
			float takenBiomass = kilosRequested;
			biomassLevel -= kilosRequested; 
			return takenBiomass;
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
