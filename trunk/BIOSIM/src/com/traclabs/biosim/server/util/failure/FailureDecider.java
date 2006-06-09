package com.traclabs.biosim.server.util.failure;

import java.util.Random;

public abstract class FailureDecider {
    private Random myRandomGenerator;
    
	public FailureDecider(){
		myRandomGenerator = new Random();
	}
	
	public boolean hasFailed(double timeElapsed){
		double failureRate = getFailureRate(timeElapsed);
		double randomNumber = myRandomGenerator.nextDouble();
        return (failureRate <= randomNumber);
	}
	
	protected abstract double getFailureRate(double timeElapsed);
}
