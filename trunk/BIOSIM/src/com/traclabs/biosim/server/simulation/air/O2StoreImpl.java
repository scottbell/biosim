package biosim.server.air;

// The package containing our stubs.
import ALSS.*;

public class O2StoreImpl extends O2StorePOA {
	private float O2level;
	private float O2capacity;
 
	public O2StoreImpl(){
		O2level = 0.0f;
		O2capacity = 10.0f;
	}

	public O2StoreImpl (float initialO2level, float  initialO2capacity){
		O2level = initialO2level;
		O2capacity = initialO2capacity;
	}

	void setO2Capacity(float liters){
		O2capacity = liters;
	}
	
	void setO2Level(float liters){
		O2level = liters;
	}

	float addO2(float liters){
		if ((liters + O2level) > O2capacity){
			O2level = O2capacity;
			if (liters >=  O2capacity)
				return 0;
			else
				return (O2capacity - O2level);
		}
		else{
			O2level = O2level + liters;
			return liters;
		}
	}

	float takeO2(float liters){
		if ((O2level - liters) < 0){
			O2level = 0;
			if (liters < 0)
				return 0;
			else
				return O2level;
		}
		else{
			O2level = O2level - liters;
			return liters;
		}
	}
	float getO2Level(){
		return O2level;
	}

	public void tick(){
		System.out.println("O2Store has been ticked!");
	}
	public String getModuleName(){
		return "O2Store";
	}
}
