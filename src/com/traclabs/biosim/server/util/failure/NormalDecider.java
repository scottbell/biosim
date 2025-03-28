package com.traclabs.biosim.server.util.failure;

public class NormalDecider extends FailureDecider {
    private final double myLogmean;

    private final double myLogsd;

    public NormalDecider(double logmean, double logsd) {
        this.myLogmean = logmean;
        this.myLogsd = logsd;
    }

    /**
     * This method calculates the normal cumulative distribution function. It is
     * based upon algorithm 5666 for the error function, from: Hart, J.F. et al,
     * 'Computer Approximations', Wiley 1968 accurate to 1.e-15
     *
     * @param x The method returns the value of the normal cumulative
     *          distribution function at x.
     */

    public static double normp(double x) {
        double zabs;
        double p;
        double expntl, pdf;

        final double p0 = 220.2068679123761;
        final double p1 = 221.2135961699311;
        final double p2 = 112.0792914978709;
        final double p3 = 33.91286607838300;
        final double p4 = 6.373962203531650;
        final double p5 = .7003830644436881;
        final double p6 = .3526249659989109E-01;

        final double q0 = 440.4137358247522;
        final double q1 = 793.8265125199484;
        final double q2 = 637.3336333788311;
        final double q3 = 296.5642487796737;
        final double q4 = 86.78073220294608;
        final double q5 = 16.06417757920695;
        final double q6 = 1.755667163182642;
        final double q7 = .8838834764831844E-1;

        final double cutoff = 7.071;
        final double root2pi = 2.506628274631001;

        zabs = Math.abs(x);

        // |x| > 37

        if (x > 37.0) {
            p = 1.0;
            return p;
        }

        if (x < -37.0) {
            p = 0.0;
            return p;
        }

        // |x| <= 37.
        expntl = Math.exp(-.5 * zabs * zabs);
        pdf = expntl / root2pi;

        // |x| < cutoff = 10/sqrt(2).
        if (zabs < cutoff) {
            p = expntl
                    * ((((((p6 * zabs + p5) * zabs + p4) * zabs + p3) * zabs + p2)
                    * zabs + p1)
                    * zabs + p0)
                    / (((((((q7 * zabs + q6) * zabs + q5) * zabs + q4) * zabs + q3)
                    * zabs + q2)
                    * zabs + q1)
                    * zabs + q0);
        } else {
            p = pdf
                    / (zabs + 1.0 / (zabs + 2.0 / (zabs + 3.0 / (zabs + 4.0 / (zabs + 0.65)))));
        }

        if (x < 0.0) {
            return 1 - p;
        } else {
            return p;
        }
    }

    public static double normalReliability(double logmean, double logsd,
                                           double x) {
        double R;
        double y;
        double pi = 3.1415926;
        y = (x - logmean) / logsd;
        R = normp(y);
        return R;
    }

    public static double normdensity(double logmean, double logsd, double x) {
        double f;
        double y;
        double pi = 3.1415926;
        y = (x - logmean) / logsd;
        f = 1 / Math.sqrt(2 * pi) * Math.exp(-y * y / 2);
        return f;
    }

    public static double normalFailureRate(double logmean, double logsd,
                                           double x) {
        double z;
        z = normdensity(logmean, logsd, x)
                / (logsd * normalReliability(logmean, logsd, x));
        return z;
    }

    @Override
    public void reset() {
    }

    @Override
    protected double getReliability(double timeElapsed) {
        return normalFailureRate(myLogmean, myLogsd, timeElapsed);
    }

}
