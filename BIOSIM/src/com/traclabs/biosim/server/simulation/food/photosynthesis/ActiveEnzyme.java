/*
 * Created on Jun 14, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 * TODO
 */
public abstract class ActiveEnzyme extends Enzyme {
	private float myRate = 1f;
	public void setRate(float pRate){
		myRate = pRate;
	}
	public float getRate(){
		return myRate;
	}

	protected float adjustForRateAndConcentration(float chemicalRatio) {
		return getRate() * getQuantity() * chemicalRatio;
	}
}
