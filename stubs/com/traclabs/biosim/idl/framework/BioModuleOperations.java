package com.traclabs.biosim.idl.framework;


/**
 * Generated from IDL interface "BioModule".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface BioModuleOperations
{
	/* constants */
	/* operations  */
	void tick();
	java.lang.String getModuleName();
	void reset();
	com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength);
	void scheduleMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength, int pTickToMalfunction);
	void fixMalfunction(long id);
	void fixAllMalfunctions();
	void clearAllMalfunctions();
	boolean isMalfunctioning();
	java.lang.String[] getMalfunctionNames();
	com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions();
	void log();
	void maintain();
	void doSomeRepairWork(long id);
	void clearMalfunction(long id);
	void setEnableFailure(boolean pValue);
	boolean isFailureEnabled();
	int getMyTicks();
	int getID();
	void setTickLength(float pInterval);
	float getTickLength();
	void setLogLevel(com.traclabs.biosim.idl.framework.LogLevel pLogLevel);
	float randomFilter(float preFilteredValue);
}
