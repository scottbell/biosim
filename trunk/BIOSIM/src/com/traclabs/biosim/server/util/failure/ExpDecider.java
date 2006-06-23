package com.traclabs.biosim.server.util.failure;

public class ExpDecider extends FailureDecider {
	private double myLambda;

	public ExpDecider(double lambda) {
		this.myLambda = lambda;
	}

	@Override
	protected double getReliability(double timeElapsed) {
		return expReliability(myLambda, timeElapsed);
	}

	public static double expdensity(double lambda, double x) {

		double f;

	      f = lambda * Math.exp(-lambda*x);

		return f;

	}

	public static double expReliability(double lambda, double x) {

		double R;

		R=Math.exp(-lambda*x);

		return R;

	}

	public static double expFailureRate(double lambda, double x) {

		double Z;

		Z = lambda;

		return Z;

	}

}
