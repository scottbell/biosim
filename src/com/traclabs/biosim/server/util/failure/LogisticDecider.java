package com.traclabs.biosim.server.util.failure;

public class LogisticDecider extends FailureDecider {
    private final double mymu;

    private final double mysd;

    public LogisticDecider(double mu, double sd) {
        this.mymu = mu;
        this.mysd = sd;
    }

    /**
     * This method calculates the Cauchy distribution function.
     *
     * @param mu location parameter.
     * @param sd scale parameter.
     * @param x  x must be greater than 0.
     */

    public static double LogisticDensity(double mu, double sd, double x) {

        double f;
        double y = (x - mu) / sd;
        f = Math.exp(y) / (sd * Math.pow((1 + Math.exp(y)), 2));
        return f;

    }

    public static double LogisticReliability(double mu, double sd, double x) {

        double R;
        double y = (x - mu) / sd;
        R = 1 / (1 + Math.exp(y));
        return R;

    }

    public static double LogisticFailureRate(double mu, double sd, double x) {

        double Z;
        double y = (x - mu) / sd;
        Z = Math.exp(y) / (sd * (1 + Math.exp(y)));
        return Z;
    }

    @Override
    public void reset() {
    }

    @Override
    protected double getReliability(double timeElapsed) {
        return LogisticFailureRate(mymu, mysd, timeElapsed);
    }

}
