package biosim.server.actuator.food;

import biosim.idl.actuator.food.BiomassRSActuatorOperations;
import biosim.idl.simulation.food.BiomassRS;
import biosim.server.actuator.framework.GenericActuatorImpl;

public abstract class BiomassRSActuatorImpl extends GenericActuatorImpl implements BiomassRSActuatorOperations{
	protected BiomassRS myBiomassRS;
	
	public BiomassRSActuatorImpl(int pID, String pName){
		super(pID, pName);
	}

	public void setOutput(BiomassRS source){
		myBiomassRS = source;
	}
	
	public BiomassRS getOutput(){
		return myBiomassRS;
	}
}
