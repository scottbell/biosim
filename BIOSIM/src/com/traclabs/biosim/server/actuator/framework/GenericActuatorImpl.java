package biosim.server.actuator.framework;

import biosim.server.framework.*;
import biosim.idl.actuator.framework.*;
import java.util.*;

public abstract class GenericActuator extends BioModuleImpl implements GenericActuatorOperations{
	private List myListeners;
	public GenericActuator(int pID){
		super(pID);
	}
}
