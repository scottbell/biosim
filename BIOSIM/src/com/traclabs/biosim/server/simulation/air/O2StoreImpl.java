package biosim.server.air;

// The package containing our stubs.
import biosim.idl.air.*;

public class O2StoreImpl extends O2StorePOA {
	private float O2Level;
	private float O2Capacity;
 
	public O2StoreImpl(){
		O2Level = 0.0f;
		O2Capacity = 10.0f;
	}

	public O2StoreImpl (float initialO2Level, float  initialO2Capacity){
		O2Level = initialO2Level;
		O2Capacity = initialO2Capacity;
	}

	public void setO2Capacity(float liters){
		O2Capacity = liters;
	}
	
	public void setO2Level(float liters){
		O2Level = liters;
	}

	public float addO2(float litersRequested){
		float acutallyAdded = 0f;
		if ((litersRequested + O2Level) > O2Capacity){
			//adding more O2 than capacity
			acutallyAdded = (O2Capacity - O2Level);
			O2Level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = litersRequested;
			O2Level += litersRequested;
			return acutallyAdded;
		}
	}

	public float takeO2(float litersRequested){
		//idiot check
		if (litersRequested < 0){
			return 0f;
		}
		//asking for more O2 than exists
		if (litersRequested > O2Level){
			float takenO2 = O2Level;
			O2Level = 0;
			return takenO2;
		}
		//O2 exists for request
		else{
			float takenO2 = litersRequested;
			O2Level -= litersRequested; 
			return takenO2;
		}
	}
	
	public float getO2Level(){
		return O2Level;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "O2Store";
	}
}
