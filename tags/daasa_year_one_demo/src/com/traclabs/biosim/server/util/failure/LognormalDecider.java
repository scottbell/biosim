package com.traclabs.biosim.server.util.failure;

public class LognormalDecider extends FailureDecider {
	private double myLogmean;

	private double myLogsd;

	public LognormalDecider(double logmean, double logsd) {
		this.myLogmean = logmean;
		this.myLogsd = logsd;
	}
	
	@Override
	public void reset() {
	}

	@Override
	protected double getReliability(double timeElapsed) {
		return lognormalFailureRate(myLogmean, myLogsd, timeElapsed);
	}

	/*
	 * This method calculates the density of the lognormal distribution. @param
	 * f The method returns the value of the normal cumulative distribution
	 * function and reliability function at x. @param x time @param logmean mu.
	 * @param logsd sita
	 */

	public static double lognormaldensity(double logmean, double logsd, double x) {
		double y;
		double pi = 3.1415926;
		double f;
		if (Double.isNaN(x) || Double.isNaN(logmean) || Double.isNaN(logsd))
			return x + logmean + logsd;
		if (logsd <= 0) {
			throw new java.lang.ArithmeticException("Math Error: DOMAIN");
		}
		if (x == 0)
			return 0;
		y = (java.lang.Math.log(x) - logmean) / logsd;
		f = (1 / Math.sqrt(2 * pi)) * java.lang.Math.exp(-0.5 * y * y)
				/ (x * logsd);
		return f;
	}

	public static double lognormalcdf(double logmean, double logsd, double x) {
		double y;
		y = (java.lang.Math.log(x) - logmean) / logsd;
		if (Double.isNaN(x) || Double.isNaN(logmean) || Double.isNaN(logsd))
			return x + logmean + logsd;
		if (logsd <= 0) {
			throw new java.lang.ArithmeticException("Math Error: DOMAIN");
		}
		if (x > 0)
			return NormalDecider.normp(y);
		return 0;
	}

	public static double lognormalReliability(double logmean, double logsd,
			double x) {
		double R;
		R = 1 - lognormalcdf(logmean, logsd, x);
		return R;
	}

	public static double lognormalFailureRate(double logmean, double logsd,
			double x) {
		double z;
		double y;
		y = (java.lang.Math.log(x) - logmean) / logsd;
		z = lognormaldensity(logmean, logsd, x) / NormalDecider.normp(y);
		return z;
	}
}
