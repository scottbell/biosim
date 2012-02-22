package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "SimBioModule"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface SimBioModuleOperations
	extends com.traclabs.biosim.idl.framework.BioModuleOperations
{
	/* constants */
	/* operations  */
	void notifyCommandSent(java.lang.String commandName);
	void registerBioCommandListener(com.traclabs.biosim.idl.simulation.framework.BioCommandListener listener);
}
