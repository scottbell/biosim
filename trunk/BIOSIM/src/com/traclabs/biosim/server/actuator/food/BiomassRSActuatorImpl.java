package biosim.server.actuator.food;

import biosim.server.actuator.framework.*;
import biosim.idl.actuator.food.*;
import biosim.idl.framework.*;
import biosim.idl.simulation.food.*;

public abstract class BiomassRSActuatorImpl extends GenericActuatorImpl implements BiomassRSActuatorOperations{
	protected BiomassRS myBiomassRS;
	
	public BiomassRSActuatorImpl(int pID){
		super(pID);
	}

	public void setOutput(BiomassRS source){
		myBiomassRS = source;
	}
	
	public BiomassRS getOutput(){
		return myBiomassRS;
	}
	
	/**
	* Returns the name of this module (BiomassRSActuator)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassRSActuator"+getID();
	}
}
