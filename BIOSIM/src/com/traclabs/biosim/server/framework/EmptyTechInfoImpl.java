package biosim.server.framework;

import biosim.idl.framework.*;

public class EmptyTechInfoImpl extends TechSpecificInfoPOA {
	
	public EmptyTechInfoImpl(){
	}
	
	public String print(){
		return "Nothing";
	}
}
