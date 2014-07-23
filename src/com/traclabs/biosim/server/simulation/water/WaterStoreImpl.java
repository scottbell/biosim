package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.WaterStoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

public class WaterStoreImpl extends StoreImpl implements WaterStoreOperations {
	private float intialTemperature = 17.5f;
	private float temperature = 17.5f;
	
	public WaterStoreImpl(int pID, String pName) {
		super (pID, pName);
	}

	@Override
	public float add(float amountRequested) {
		return addWaterWithTemperature(amountRequested, temperature);
	}

	public void setInitialWaterTemperature(float newTemperature) {
		this.intialTemperature = newTemperature;
		this.temperature = intialTemperature;
	}

	@Override
	public void reset() {
		super.reset();
		this.temperature = intialTemperature;
	}

	public float addWaterWithTemperature(float newWater, float newWaterTemperature) {
		float oldLevel = getCurrentLevel();
		float amountAdded = super.add(newWater);
		if (amountAdded > 0){
			float oldWeight = 0;
			if (oldLevel > 0)
				oldWeight = 1 / (amountAdded / oldLevel);
			float newWeight = 1 - oldWeight;
			float calculatedTemperature = (oldWeight * temperature) + (newWeight * newWaterTemperature);
			this.temperature = calculatedTemperature;
		}
		return amountAdded;
	}

	public float getCurrentTemperature() {
		return temperature;
	}

}
