package com.traclabs.biosim.idl.framework;

/**
 *	Generated from IDL interface "BioDriver"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface BioDriverOperations
{
	/* constants */
	/* operations  */
	boolean isPaused();
	boolean isStarted();
	void startSimulation();
	void endSimulation();
	void setRunTillCrewDeath(boolean pRunTillDead);
	void setRunTillPlantDeath(boolean pRunTillDead);
	void setCrewsToWatch(com.traclabs.biosim.idl.simulation.crew.CrewGroup[] pCrews);
	void setPlantsToWatch(com.traclabs.biosim.idl.simulation.food.BiomassPS[] pBiomass);
	void setRunTillN(int pTicks);
	void setLoopSimulation(boolean pLoop);
	void setPauseSimulation(boolean pPause);
	void advanceOneTick();
	void setDriverStutterLength(int pDriverPauseLength);
	int getDriverStutterLength();
	void setModules(com.traclabs.biosim.idl.framework.BioModule[] pModules);
	void setSensors(com.traclabs.biosim.idl.framework.BioModule[] pSensors);
	void setActuators(com.traclabs.biosim.idl.framework.BioModule[] pActuators);
	void setPrioritySimModules(com.traclabs.biosim.idl.framework.BioModule[] pSimModules);
	void setActiveSimModules(com.traclabs.biosim.idl.framework.BioModule[] pSimModules);
	void setPassiveSimModules(com.traclabs.biosim.idl.framework.BioModule[] pSimModules);
	com.traclabs.biosim.idl.framework.BioModule getModule(java.lang.String moduleName);
	com.traclabs.biosim.idl.framework.BioModule[] getModules();
	com.traclabs.biosim.idl.framework.BioModule[] getSensors();
	com.traclabs.biosim.idl.framework.BioModule[] getActuators();
	com.traclabs.biosim.idl.framework.BioModule[] getSimModules();
	com.traclabs.biosim.idl.framework.BioModule[] getActiveSimModules();
	com.traclabs.biosim.idl.framework.BioModule[] getPrioritySimModules();
	com.traclabs.biosim.idl.framework.BioModule[] getPassiveSimModules();
	java.lang.String[] getModuleNames();
	java.lang.String[] getSensorNames();
	java.lang.String[] getActuatorNames();
	java.lang.String[] getSimModuleNames();
	java.lang.String[] getActiveSimModuleNames();
	java.lang.String[] getPrioritySimModuleNames();
	java.lang.String[] getPassiveSimModuleNames();
	java.lang.String getName();
	boolean isDone();
	int getTicks();
	void reset();
	boolean isLooping();
	void setLooping(boolean pLoop);
	void startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength);
	void setTickLength(float tickLength);
	float getTickLength();
}
