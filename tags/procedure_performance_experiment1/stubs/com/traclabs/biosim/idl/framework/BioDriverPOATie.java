package com.traclabs.biosim.idl.framework;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "BioDriver"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class BioDriverPOATie
	extends BioDriverPOA
{
	private BioDriverOperations _delegate;

	private POA _poa;
	public BioDriverPOATie(BioDriverOperations delegate)
	{
		_delegate = delegate;
	}
	public BioDriverPOATie(BioDriverOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.framework.BioDriver _this()
	{
		return com.traclabs.biosim.idl.framework.BioDriverHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.framework.BioDriver _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.framework.BioDriverHelper.narrow(_this_object(orb));
	}
	public BioDriverOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(BioDriverOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		else
		{
			return super._default_POA();
		}
	}
	public void setLooping(boolean pLoop)
	{
_delegate.setLooping(pLoop);
	}

	public java.lang.String[] getActiveSimModuleNames()
	{
		return _delegate.getActiveSimModuleNames();
	}

	public float getTickLength()
	{
		return _delegate.getTickLength();
	}

	public java.lang.String[] getModuleNames()
	{
		return _delegate.getModuleNames();
	}

	public void startSimulation()
	{
_delegate.startSimulation();
	}

	public java.lang.String[] getSensorNames()
	{
		return _delegate.getSensorNames();
	}

	public void startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
_delegate.startMalfunction(pIntensity,pLength);
	}

	public boolean isDone()
	{
		return _delegate.isDone();
	}

	public com.traclabs.biosim.idl.framework.BioModule[] getPrioritySimModules()
	{
		return _delegate.getPrioritySimModules();
	}

	public com.traclabs.biosim.idl.framework.BioModule[] getSensors()
	{
		return _delegate.getSensors();
	}

	public void setActuators(com.traclabs.biosim.idl.framework.BioModule[] pActuators)
	{
_delegate.setActuators(pActuators);
	}

	public java.lang.String[] getPassiveSimModuleNames()
	{
		return _delegate.getPassiveSimModuleNames();
	}

	public void setActiveSimModules(com.traclabs.biosim.idl.framework.BioModule[] pSimModules)
	{
_delegate.setActiveSimModules(pSimModules);
	}

	public java.lang.String[] getActuatorNames()
	{
		return _delegate.getActuatorNames();
	}

	public int getDriverStutterLength()
	{
		return _delegate.getDriverStutterLength();
	}

	public void endSimulation()
	{
_delegate.endSimulation();
	}

	public java.lang.String[] getSimModuleNames()
	{
		return _delegate.getSimModuleNames();
	}

	public java.lang.String getName()
	{
		return _delegate.getName();
	}

	public void setPauseSimulation(boolean pPause)
	{
_delegate.setPauseSimulation(pPause);
	}

	public void setRunTillPlantDeath(boolean pRunTillDead)
	{
_delegate.setRunTillPlantDeath(pRunTillDead);
	}

	public void setSensors(com.traclabs.biosim.idl.framework.BioModule[] pSensors)
	{
_delegate.setSensors(pSensors);
	}

	public com.traclabs.biosim.idl.framework.BioModule[] getSimModules()
	{
		return _delegate.getSimModules();
	}

	public void reset()
	{
_delegate.reset();
	}

	public com.traclabs.biosim.idl.framework.BioModule getModule(java.lang.String moduleName)
	{
		return _delegate.getModule(moduleName);
	}

	public void setLoopSimulation(boolean pLoop)
	{
_delegate.setLoopSimulation(pLoop);
	}

	public void setCrewsToWatch(com.traclabs.biosim.idl.simulation.crew.CrewGroup[] pCrews)
	{
_delegate.setCrewsToWatch(pCrews);
	}

	public void setPrioritySimModules(com.traclabs.biosim.idl.framework.BioModule[] pSimModules)
	{
_delegate.setPrioritySimModules(pSimModules);
	}

	public void setDriverStutterLength(int pDriverPauseLength)
	{
_delegate.setDriverStutterLength(pDriverPauseLength);
	}

	public boolean isPaused()
	{
		return _delegate.isPaused();
	}

	public void setModules(com.traclabs.biosim.idl.framework.BioModule[] pModules)
	{
_delegate.setModules(pModules);
	}

	public void setRunTillCrewDeath(boolean pRunTillDead)
	{
_delegate.setRunTillCrewDeath(pRunTillDead);
	}

	public com.traclabs.biosim.idl.framework.BioModule[] getActuators()
	{
		return _delegate.getActuators();
	}

	public com.traclabs.biosim.idl.framework.BioModule[] getPassiveSimModules()
	{
		return _delegate.getPassiveSimModules();
	}

	public void setRunTillN(int pTicks)
	{
_delegate.setRunTillN(pTicks);
	}

	public java.lang.String[] getPrioritySimModuleNames()
	{
		return _delegate.getPrioritySimModuleNames();
	}

	public void advanceOneTick()
	{
_delegate.advanceOneTick();
	}

	public void setPassiveSimModules(com.traclabs.biosim.idl.framework.BioModule[] pSimModules)
	{
_delegate.setPassiveSimModules(pSimModules);
	}

	public void setPlantsToWatch(com.traclabs.biosim.idl.simulation.food.BiomassPS[] pBiomass)
	{
_delegate.setPlantsToWatch(pBiomass);
	}

	public void setTickLength(float tickLength)
	{
_delegate.setTickLength(tickLength);
	}

	public boolean isLooping()
	{
		return _delegate.isLooping();
	}

	public boolean isStarted()
	{
		return _delegate.isStarted();
	}

	public com.traclabs.biosim.idl.framework.BioModule[] getActiveSimModules()
	{
		return _delegate.getActiveSimModules();
	}

	public int getTicks()
	{
		return _delegate.getTicks();
	}

	public com.traclabs.biosim.idl.framework.BioModule[] getModules()
	{
		return _delegate.getModules();
	}

}
