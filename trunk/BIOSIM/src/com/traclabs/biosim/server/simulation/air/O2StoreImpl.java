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

	void setO2Capacity(float liters){
		O2Capacity = liters;
	}
	
	void setO2Level(float liters){
		O2Level = liters;
	}

	float addO2(float liters){
		if ((liters + O2Level) > O2Capacity){
			float returnValue = (O2Capacity - O2Level);
			O2Level = O2Capacity;
			return returnValue;
		}
		else{
			O2Level = O2Level + liters;
			return liters;
		}
	}

	float takeO2(float liters){
		if ((O2Level - liters) < 0){
			O2Level = 0;
			if (liters < 0)
				return 0;
			else
				return O2Level;
		}
		else{
			O2Level = O2Level - liters;
			return liters;
		}
	}
	float getO2Level(){
		return O2Level;
	}

	public void tick(){
		System.out.println("O2Store has been ticked!");
	}
	public String getModuleName(){
		return "O2Store";
	}
}
