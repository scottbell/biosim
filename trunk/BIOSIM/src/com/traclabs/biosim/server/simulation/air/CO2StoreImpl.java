package biosim.server.air;

// The package containing our stubs.
import SIMULATION.*;

public class CO2StoreImpl extends CO2StorePOA {
	private float CO2level;
	private float CO2capacity;

	float addCO2(float liters){
		if ((liters + CO2level) > CO2capacity){
			CO2level = CO2capacity;
			if (liters >=  CO2capacity)
				return 0;
			else
				return (CO2capacity - CO2level);
		}
		else{
			CO2level = CO2level + liters;
			return liters;
		}
	}

	float takeCO2(float liters){
		if ((CO2level - liters) < 0){
			CO2level = 0;
			if (liters < 0)
				return 0;
			else
				return CO2level;
		}
		else{
			CO2level = CO2level - liters;
			return liters;
		}
	}
	float getCO2Level(){
		return CO2level;
	}

	public void tick(){
		System.out.println("CO2Store has been ticked!");
	}
	public String getModuleName(){
		return "CO2Store";
	}
}
