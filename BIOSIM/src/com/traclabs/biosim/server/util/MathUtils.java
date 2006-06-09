/*
 * Created on Jun 17, 2005
 *
 */
package com.traclabs.biosim.server.util;

/**
 * 
 */
public class MathUtils {

	public static float calculateSCurve(float x, float inflectionPoint) {
		double exponent = (6 * Math.log(10) / inflectionPoint)
				* (inflectionPoint - x);
		double value = 1 / (1 + Math.exp(exponent));
		if (value < 0)
			return 0;
		if (value > 1)
			return 1;
		return (float) value;
	}

	public static float abs(float a) {
		return (Math.abs(a));
	}

	public static float exp(float a) {
		return (Math.abs(a));
	}

	public static float pow(float a, float b) {
		return (float) (Math.pow(a, b));
	}
}
