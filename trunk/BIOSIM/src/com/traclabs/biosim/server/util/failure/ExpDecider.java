package com.traclabs.biosim.server.util.failure;

public class ExpDecider extends FailureDecider {
	private double myLambda;

	public ExpDecider(double lambda) {
		this.myLambda = lambda;
	}

	@Override
	protected double getFailureRate(double timeElapsed) {
		return expFailureRate(myLambda, timeElapsed);
	}

	public static double expdensity(double lambda, double x) {

		double f;

<<<<<<< .mine
	      f = lambda * Math.exp(-lambda*x);
=======
		f = lambda * Math.exp(lambda * x);
>>>>>>> .r1914

		return f;

	}

	public static double exprel(double lambda, double x) {

		double R;

<<<<<<< .mine
		R=Math.exp(-lambda*x);
=======
		R = Math.exp(lambda * x);
>>>>>>> .r1914

		return R;

	}

	public static double expFailureRate(double lambda, double x) {

		double Z;

		Z = lambda;

		return Z;

	}

}
