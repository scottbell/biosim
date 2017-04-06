package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.simulation.water.WaterStoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

public class WaterStoreImpl extends StoreImpl implements WaterStoreOperations {
	private float intialTemperature = 22f;
	private float temperature = intialTemperature;
	
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

	public float addWaterWithTemperature(float newWaterVolume, float newWaterTemperature) {
		float oldVolume = getCurrentLevel();
		float oldTemperature = getCurrentTemperature();
		float amountAdded = super.add(newWaterVolume);
		if (amountAdded > 0){
			float calculatedTemperature = ((oldVolume * oldTemperature) + (newWaterVolume * newWaterTemperature)) / (oldVolume + newWaterVolume);
			this.temperature = calculatedTemperature;
		}
		return amountAdded;
	}

	public float getCurrentTemperature() {
		return temperature;
	}

}
