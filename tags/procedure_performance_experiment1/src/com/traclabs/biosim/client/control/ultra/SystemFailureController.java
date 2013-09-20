package com.traclabs.biosim.client.control.ultra;

import java.io.IOException;
import java.util.List;

import com.traclabs.biosim.idl.framework.BioModule;

public abstract class SystemFailureController extends ScenarioController {

	public SystemFailureController(int numberOfRuns, String outputDirectoryPath)
			throws IOException {
		super(numberOfRuns, outputDirectoryPath);

	}

	protected boolean endConditionMet(int systemsAllowedToFail, List modules) {
		int waterStoresFailed = 0;

		for (Object moduleObject : modules) {
			BioModule module = (BioModule) moduleObject;
			if (module.isMalfunctioning())
				waterStoresFailed++;

		}

		boolean tooManyWaterSystemsFailed = waterStoresFailed >= systemsAllowedToFail;

		return tooManyWaterSystemsFailed;
	}

}
