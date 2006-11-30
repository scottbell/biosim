package com.traclabs.biosim.server.util.failure;

public class UniformDecider extends FailureDecider {
	private double myAlpha;

	private double myBeta;

	public UniformDecider(double alpha, double beta) {
		this.myAlpha = alpha;
		this.myBeta = beta;
	}

	@Override
	protected double getReliability(double timeElapsed) {
		return UniformReliability(myAlpha, myBeta, timeElapsed);
	}

	/**
	 * This method calculates the uniform distribution function.
	 * 
	 * @param alpha
	 *            alpha<beta
	 * @param beta
	 *            beta>alpha
	 * @param x
	 *            x must be greater than 0, but less than 1.
	 */

	public static double UniformDensity(double alpha, double beta, double x) {

		double f;

		f = 1 / (beta - alpha);

		return f;

	}

	public static double UniformReliability(double alpha, double beta, double x) {

		double R;

		R = 1 - (x - alpha) / (beta - alpha);

		return R;

	}

	public static double UniformFailureRate(double alpha, double beta, double x) {

		double Z;

		Z = 1 / (beta - x);

		return Z;

	}

}
