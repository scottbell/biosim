package biosim.server.framework;

import biosim.idl.framework.*;

public abstract class BioModuleImpl extends BioModulePOA{
	public void tick(){
		System.out.println(getModuleName() + " has been ticked!");
	}
	public String getModuleName(){
		return "Unamed";
	}
}

