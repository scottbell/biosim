package com.traclabs.biosim.server.util.failure;

public class CauchyDecider extends FailureDecider {
	private double mymu;

	private double mysd;

	public CauchyDecider(double mu, double sd) {
		this.mymu = mu;
		this.mysd = sd;
	}
	
	@Override
	public void reset() {
	}

	@Override
	protected double getReliability(double timeElapsed) {
		return CauchyReliability(mymu, mysd, timeElapsed);
	}

	/**
	 * This method calculates the Cauchy distribution function.
	 * 
	 * @param mu
	 * @param sd
	 *            sd mush be greater than 0
	 * @param x
	 *            x must be greater than 0, but less than 1.
	 */

	public static double atan(double x) {
		double lo;
		double hi;
		double PI = 3.141592653589793;
		double TWO_66 = 7.378697629483821e19;
		double ATAN_0_5H = 0.4636476090008061;
		double ATAN_0_5L = 2.2698777452961687e-17;
		double ATAN_1_5H = 0.982793723247329;
		double ATAN_1_5L = 1.3903311031230998e-17;
		double PI_L = 1.2246467991473532e-16;
		double TWO_29 = 0x20000000;
		double AT0 = 0.3333333333333293;
		double AT1 = -0.19999999999876483;
		double AT2 = 0.14285714272503466;
		double AT3 = -0.11111110405462356;
		double AT4 = 0.09090887133436507;
		double AT5 = -0.0769187620504483;
		double AT6 = 0.06661073137387531;
		double AT7 = -0.058335701337905735;
		double AT8 = 0.049768779946159324;
		double AT9 = -0.036531572744216916;
		double AT10 = 0.016285820115365782;
		boolean negative = x < 0;
		if (negative)
			x = -x;
		if (x >= TWO_66)
			return negative ? -PI / 2 : PI / 2;
		if (!(x >= 0.4375)) // |x|<7/16, or NaN.
		{
			if (!(x >= 1 / TWO_29)) // Small, or NaN.
				return negative ? -x : x;
			lo = hi = 0;
		} else if (x < 1.1875) {
			if (x < 0.6875) // 7/16<=|x|<11/16.
			{
				x = (2 * x - 1) / (2 + x);
				hi = ATAN_0_5H;
				lo = ATAN_0_5L;
			} else // 11/16<=|x|<19/16.
			{
				x = (x - 1) / (x + 1);
				hi = PI / 4;
				lo = PI_L / 4;
			}
		} else if (x < 2.4375) // 19/16<=|x|<39/16.
		{
			x = (x - 1.5) / (1 + 1.5 * x);
			hi = ATAN_1_5H;
			lo = ATAN_1_5L;
		} else // 39/16<=|x|<2**66.
		{
			x = -1 / x;
			hi = PI / 2;
			lo = PI_L / 2;
		}
		// Break sum from i=0 to 10 ATi*z**(i+1) into odd and even poly.
		double z = x * x;
		double w = z * z;
		double s1 = z
				* (AT0 + w
						* (AT2 + w * (AT4 + w * (AT6 + w * (AT8 + w * AT10)))));
		double s2 = w * (AT1 + w * (AT3 + w * (AT5 + w * (AT7 + w * AT9))));
		if (hi == 0)
			return negative ? x * (s1 + s2) - x : x - x * (s1 + s2);
		z = hi - ((x * (s1 + s2) - lo) - x);
		return negative ? -z : z;
	}

	public static double CauchyDensity(double mu, double sd, double x) {

		double f;

		f = Math.pow(sd * 3.1415926 * (1 + Math.pow(((x - mu) / sd), 2)), -1);

		return f;

	}

	public static double CauchyReliability(double mu, double sd, double x) {

		double R;

		R = 0.5 - (atan((x - mu) / sd)) / 180;

		return R;

	}

}
