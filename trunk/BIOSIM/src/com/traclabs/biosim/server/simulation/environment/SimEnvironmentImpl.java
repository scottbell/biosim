package biosim.server.environment;

import biosim.idl.environment.*;
import biosim.idl.air.*;

public class SimEnvironmentImpl extends SimEnvironmentPOA {
	private float O2level;
	private float O2capacity;
	private float CO2level;
	private float CO2capacity;
	
	private int ticks	= 0;

	public SimEnvironmentImpl(){
		CO2level = 0.0f;
		CO2capacity = 10.0f;
		O2level = 0.0f;
		O2capacity = 10.0f;
	}
	
	public SimEnvironmentImpl (float initialCO2level, float initialO2level, float  initialCO2capacity, float initialO2capacity){
		CO2level = initialCO2level;
		O2level = initialO2level;
		CO2capacity = initialCO2capacity;
		O2capacity = initialO2capacity;
	}
	
	public int getTicks(){
		return ticks;
	}

	public void setO2Capacity(float liters){
		O2capacity = liters;
	}
	
	public void setCO2Capacity(float liters){
		CO2capacity = liters;
	}
	
	public void setCO2Level(float liters){
		CO2level = liters;
	}
	
	public void setO2Level(float liters){
		O2level = liters;
	}

	public float addO2(float liters){
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

	public Breath takeO2(float liters){
		if ((O2level - liters) < 0){
			O2level = 0;
			if (liters < 0){
				return new Breath(0, 0);
			}
			else{
				return new Breath(O2level, O2level/getRatio());
			}
		}
		else{
			O2level = O2level - liters;
			return new Breath(liters, liters/getRatio());
		}
	}

	public float getO2Level(){
		return O2level;
	}

	public float addCO2(float liters){
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

	public Breath takeCO2(float liters){
		if ((CO2level - liters) < 0){
			CO2level = 0;
			if (liters < 0){
				return new Breath(0,0);
			}
			else{
				return new Breath(getRatio()*CO2level , CO2level);
			}
		}
		else{
			CO2level = CO2level - liters;
			return new Breath(getRatio() * liters, liters);
		}
	}

	public float getCO2Level(){
		return CO2level;
	}
	
	public float getRatio(){
		return O2level/CO2level;
	}

	public void tick(){
		ticks++;
		System.out.println(getModuleName() + ": advanced to timestep @ "+ticks);
	}

	public String getModuleName(){
		return "SimEnvironment";
	}
}
