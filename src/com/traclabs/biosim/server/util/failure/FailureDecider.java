package com.traclabs.biosim.server.util.failure;

import com.traclabs.biosim.util.MersenneTwister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public abstract class FailureDecider {
	private Random myRandomGenerator = new MersenneTwister();

	Logger myLogger = LoggerFactory.getLogger(FailureDecider.class);

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
