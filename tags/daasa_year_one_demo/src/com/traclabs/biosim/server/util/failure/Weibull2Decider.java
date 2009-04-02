package com.traclabs.biosim.server.util.failure;

public class Weibull2Decider extends FailureDecider {
	private double myLambda;

	private double myBeta;

	public Weibull2Decider(double lambda, double beta) {
		this.myLambda = lambda;
		this.myBeta = beta;
	}
	
	@Override
	public void reset() {
	}

	@Override
	protected double getReliability(double timeElapsed) {
		return weibullFailureRate(myLambda, myBeta, timeElapsed);
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
	public static double weibullCumulativeDistribution(double lambda,
			double beta, double x) {
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
	public static double weibullReliability(double lambda, double beta, double x) {
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
