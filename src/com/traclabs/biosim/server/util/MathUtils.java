/*
 * Created on Jun 17, 2005
 *
 */
package com.traclabs.biosim.server.util;

import com.traclabs.biosim.util.MersenneTwister;

import java.util.Random;

/**
 * 
 */
public class MathUtils {
	private static Random myRandom = new MersenneTwister();

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
     * A basic Gaussian function
     * 
     * @param mean
     *            where the gaussian is "centered". e.g., a value of 3 would
     *            yield numbers around 3
     * @param deviation
     *            how far the gaussian deviates from the mean
     * @return the randomized value
     */
    public static double gaussian(double mean, double deviation) {
        double t = 0.0;
        double x, v1, v2, r;
        if (t == 0) {
            do {
                v1 = 2.0 * myRandom.nextDouble() - 1.0;
                v2 = 2.0 * myRandom.nextDouble() - 1.0;
                r = v1 * v1 + v2 * v2;
            } while (r >= 1.0);
            r = Math.sqrt((-2.0 * Math.log(r)) / r);
            t = v2 * r;
            return (mean + v1 * r * deviation);
        }
		x = t;
		t = 0.0;
		return (mean + x * deviation);
    }
}
