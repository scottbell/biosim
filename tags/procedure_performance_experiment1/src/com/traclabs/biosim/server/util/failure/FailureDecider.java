package com.traclabs.biosim.server.util.failure;

import java.util.Random;

import org.apache.log4j.Logger;

import com.traclabs.biosim.util.MersenneTwister;

public abstract class FailureDecider {
	private Random myRandomGenerator = new MersenneTwister();

	private Logger myLogger = Logger.getLogger(FailureDecider.class);

	public boolean hasFailed(double timeElapsed) {
		double Reliability = getReliability(timeElapsed);
		double randomNumber = myRandomGenerator.nextDouble();
		boolean failed = randomNumber < Reliability;
		//myLogger.debug("Reliability = " + Reliability);
		//myLogger.debug("randomNumber = " + randomNumber);
		//myLogger.debug("failed = " + failed);
		return failed;
	}

	protected abstract double getReliability(double timeElapsed);

	public abstract void reset();
}
