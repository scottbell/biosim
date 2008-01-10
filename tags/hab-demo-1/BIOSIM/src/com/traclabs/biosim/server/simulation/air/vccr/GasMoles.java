package com.traclabs.biosim.server.simulation.air.vccr;

public class GasMoles {
	private float myO2Moles;
	private float myCo2Moles;
	private float myVaporMoles;
	private float myNitrogenMoles;
	private float myOtherMoles;
	
	/**
	 * Assume sea level concentration
	 */
	public GasMoles(float molesOfAir){
		myO2Moles = molesOfAir * 0.20f;
		myCo2Moles = molesOfAir * 0.00111f;
		myVaporMoles = molesOfAir * 0.01f;
		myNitrogenMoles = molesOfAir * 0.7896f;
		myOtherMoles = molesOfAir * 0.01f;
	}

}
