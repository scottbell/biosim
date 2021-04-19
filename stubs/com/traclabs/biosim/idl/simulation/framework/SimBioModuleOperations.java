package com.traclabs.biosim.idl.simulation.framework;


/**
 * Generated from IDL interface "SimBioModule".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface SimBioModuleOperations
	extends com.traclabs.biosim.idl.framework.BioModuleOperations
{
	/* constants */
	/* operations  */
	void notifyCommandSent(java.lang.String commandName);
	void registerBioCommandListener(com.traclabs.biosim.idl.simulation.framework.BioCommandListener listener);
}
