package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.framework.Store;

public class WaterStore extends Store  {
	private float intialTemperature = 22f;
	private float temperature = intialTemperature;
	
	public WaterStore(int pID, String pName) {
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
