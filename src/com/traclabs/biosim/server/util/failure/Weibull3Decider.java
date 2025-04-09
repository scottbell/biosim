package com.traclabs.biosim.server.util.failure;

public class Weibull3Decider extends FailureDecider {
    private final double myLambda;

    private final double myBeta;

    private final double myHold;

    public Weibull3Decider(double lambda, double beta, double hold) {
        this.myLambda = lambda;
        this.myBeta = beta;
        this.myHold = hold;
    }

    /**
     * This method calculates the 3-parameter Weibull failure rate.
     *
     * @param lambda The 3-parameter Weibull scale parameter.
     * @param beta   The 3-parameter Weibull shape parameter.
     * @param hold   The 3-parameter Weibull Threshold parameter.
     * @param x      x must be greater than 0.
     */
    //
    public static double weibull3CumulativeDistribution(double lambda,
                                                        double beta, double hold, double x) {

        double p;

        p = 1.0 - Math.exp(-Math.pow(lambda * (x - hold), beta));

        return p;

    }

    public static double weibull3Reliability(double lambda, double beta,
                                             double hold, double x) {

        double R;

        R = Math.exp(-Math.pow(lambda * (x - hold), beta));

        return R;

    }

    public static double weibull3FailureRate(double lambda, double beta,
                                             double hold, double x) {

        double Z;

        Z = (beta * lambda) * Math.pow(lambda * (x - hold), (beta - 1));

        return Z;

    }

    @Override
    public void reset() {
    }

    @Override
    protected double getReliability(double timeElapsed) {
        return weibull3FailureRate(myLambda, myBeta, myHold, timeElapsed);
    }

}
