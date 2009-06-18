package com.traclabs.biosim.client.control.ultra;

import java.io.IOException;
import java.util.List;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.idl.framework.BioModule;

public abstract class CrewDeathController extends ScenarioController {

	public CrewDeathController(int numberOfRuns, String outputDirectoryPath)
			throws IOException {
		super(numberOfRuns, outputDirectoryPath);

	}

	protected boolean endConditionMet(int systemsAllowedToFail, List modules) 
	{
	
		return true;
	}

}
