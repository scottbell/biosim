
package com.traclabs.biosim.server.util.failure;
import java.lang.Math;
import java.lang.Double;


public class LognormalDecider extends FailureDecider {
	private double myLogmean;
	private double myLogsd;
	
	public LognormalDecider(double logmean, double logsd){
		this.myLogmean = logmean;
		this.myLogsd = logsd;
	}
	
	@Override
	protected double getFailureRate(double timeElapsed) {
		return lognormalFailureRate(myLogmean, myLogsd, timeElapsed);
	}
	

     /* This method calculates the density of the lognormal distribution.
     *@param   f         The method returns the value of the normal
	 *                   cumulative distribution function at x.
	 *@param   x         time       
	 *@param   logmean   mu.
	 *@param   logsd     sita
     */
    
    public static double  lognormaldensity(double x, double logmean, double logsd)
    {
        double y;
        double pi = 3.1415926;
        double f;
    
        if (Double.isNaN(x) || Double.isNaN(logmean) || Double.isNaN(logsd))
    	return x + logmean + logsd;

        if(logsd <= 0) {
            throw new java.lang.ArithmeticException("Math Error: DOMAIN");
	    
        }
        
        if(x == 0) return 0;
        y = (java.lang.Math.log(x) - logmean) / logsd;
        f = (1 / Math.sqrt(2 * pi))* java.lang.Math.exp(-0.5 * y * y) / (x * logsd);
        return f;
    }
    
    //    This method calculates the lognormal cumulative distribution function.
 
    public static double  lognormalcdf(double x, double logmean, double logsd) {
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
    
    public static double  lognormalFailureRate(double x, double logmean, double logsd){
    	double z;
    	double y;
    	y = (java.lang.Math.log(x) - logmean) / logsd;
    	z=lognormaldensity(x, logmean, logsd)/NormalDecider.normp(y);
    	return z;
    	}
    }
    
  
