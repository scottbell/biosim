package biosim.server.environment;

// The package containing our stubs.
import SIMULATION.*;

public class SimEnvironmentImpl extends SimEnvironmentPOA {
	private float O2level;
	private float O2capacity;
	private float CO2level;
	private float CO2capacity;

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
		System.out.println("SimEnvironment has been ticked!");
	}

	public String getModuleName(){
		return "SimEnvironment";
	}
}
