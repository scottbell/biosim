package com.traclabs.biosim.server.util.failure;

import java.util.Random;

import org.apache.log4j.Logger;

public abstract class FailureDecider {
    private Random myRandomGenerator;
    private Logger myLogger;
    
	public FailureDecider(){
		myRandomGenerator = new Random();
		myLogger = Logger.getLogger(FailureDecider.class);
	}
	
	public boolean hasFailed(double timeElapsed){
		double failureRate = getFailureRate(timeElapsed);
		double randomNumber = myRandomGenerator.nextDouble();
		boolean failed = randomNumber <= failureRate;
		myLogger.debug("failureRate = "+failureRate);
		myLogger.debug("randomNumber = "+randomNumber);
		myLogger.debug("failed = "+failed);
        return failed;
	}
	
	protected abstract double getFailureRate(double timeElapsed);
}
