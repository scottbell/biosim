package biosim.server.framework;

import biosim.idl.framework.*;

public abstract class StoreImpl extends BioModuleImpl implements StoreOperations{
	protected float level = 0.0f;
	protected float capacity = 0.0f;
	
	public StoreImpl(){
		level = 0.0f;
		capacity = 10.0f;
	}
	
	public StoreImpl (float initialLevel, float  initialCapacity){
		level = initialLevel;
		capacity = initialCapacity;
	}
	
	public void setCapacity(float metricAmount){
		capacity = metricAmount;
	}

	public void setLevel(float metricAmount){
		level = metricAmount;
	}

	public float add(float amountRequested){
		float acutallyAdded = 0f;
		if ((amountRequested + level) > capacity){
			//adding more than capacity
			acutallyAdded = (capacity - level);
			level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = amountRequested;
			level += amountRequested;
			return acutallyAdded;
		}
	}

	public float take(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float takenAmount;
		//asking for more stuff than exists
		if (amountRequested > level){
			takenAmount = level;
			level = 0;
		}
		//stuff exists for request
		else{
			takenAmount = amountRequested;
			level -= amountRequested; 
		}
		return takenAmount;
	}
	
	public float getLevel(){
		return level;
	}
	
	public void reset(){
		level = 0.0f;
	}

}
