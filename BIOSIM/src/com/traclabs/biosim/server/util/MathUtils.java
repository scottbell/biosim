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

	/**
	 * This method calculates the 2-parameter Weibull cumulative distribution
	 * function.
	 * 
	 * @param lambda
	 *            The 2-parameter Weibull scale parameter.
	 * 
	 * @param beta
	 *            The 2-parameter Weibull shape parameter.
	 * 
	 * @param x
	 *            x must be greater than 0.
	 * 
	 */
	public static double weibullCumulativeDistribution(double lambda, double beta, double x) {
		double p;
		p = 1.0 - Math.exp(-Math.pow(lambda * x, beta));
		return p;
	}

	/**
	 * This method calculates the reliability function of the 2-parameter
	 * Weibull model.
	 * 
	 * @param lambda
	 *            The 2-parameter Weibull scale parameter.
	 * 
	 * @param beta
	 *            The 2-parameter Weibull shape parameter.
	 * 
	 * @param x
	 *            x must be greater than 0.
	 * 
	 */
	public static double weibullReliabilityFunction(double lambda, double beta, double x) {
		double R;
		R = Math.exp(-Math.pow(lambda * x, beta));
		return R;
	}

	/**
	 * This method calculates the failure rate of the 2-parameter Weibull model.
	 * 
	 * @param lambda
	 *            The 2-parameter Weibull scale parameter.
	 * 
	 * @param beta
	 *            The 2-parameter Weibull shape parameter.
	 * 
	 * @param x
	 *            x must be greater than 0.
	 * 
	 */ 
	public static double weibullFailureRate(double lambda, double beta, double x) {
		double Z;
		Z = (beta * lambda) * Math.pow(lambda * x, (beta - 1));
		return Z;
	}

}
