package com.traclabs.biosim.idl.simulation.water;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "DirtyWaterStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class DirtyWaterStorePOATie
	extends DirtyWaterStorePOA
{
	private DirtyWaterStoreOperations _delegate;

	private POA _poa;
	public DirtyWaterStorePOATie(DirtyWaterStoreOperations delegate)
	{
		_delegate = delegate;
	}
	public DirtyWaterStorePOATie(DirtyWaterStoreOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.water.DirtyWaterStore _this()
	{
		return com.traclabs.biosim.idl.simulation.water.DirtyWaterStoreHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.water.DirtyWaterStore _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.water.DirtyWaterStoreHelper.narrow(_this_object(orb));
	}
	public DirtyWaterStoreOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(DirtyWaterStoreOperations delegate)
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
	public void setPipe(boolean pPipe)
	{
_delegate.setPipe(pPipe);
	}

	public void setCurrentLevel(float metricAmount)
	{
_delegate.setCurrentLevel(metricAmount);
	}

	public void clearMalfunction(long id)
	{
_delegate.clearMalfunction(id);
	}

	public float getTickLength()
	{
		return _delegate.getTickLength();
	}

	public void fixAllMalfunctions()
	{
_delegate.fixAllMalfunctions();
	}

	public void setLogLevel(com.traclabs.biosim.idl.framework.LogLevel pLogLevel)
	{
_delegate.setLogLevel(pLogLevel);
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public float getCurrentCapacity()
	{
		return _delegate.getCurrentCapacity();
	}

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public void setInitialCapacity(float metricAmount)
	{
_delegate.setInitialCapacity(metricAmount);
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public float getInitialCapacity()
	{
		return _delegate.getInitialCapacity();
	}

	public void reset()
	{
_delegate.reset();
	}

	public void registerBioCommandListener(com.traclabs.biosim.idl.simulation.framework.BioCommandListener listener)
	{
_delegate.registerBioCommandListener(listener);
	}

	public float getOverflow()
	{
		return _delegate.getOverflow();
	}

	public float take(float metricAmount)
	{
		return _delegate.take(metricAmount);
	}

	public float getPercentageFilled()
	{
		return _delegate.getPercentageFilled();
	}

	public java.lang.String getModuleName()
	{
		return _delegate.getModuleName();
	}

	public float randomFilter(float preFilteredValue)
	{
		return _delegate.randomFilter(preFilteredValue);
	}

	public boolean isMalfunctioning()
	{
		return _delegate.isMalfunctioning();
	}

	public void log()
	{
_delegate.log();
	}

	public void setInitialLevel(float metricAmount)
	{
_delegate.setInitialLevel(metricAmount);
	}

	public void maintain()
	{
_delegate.maintain();
	}

	public float getCurrentLevel()
	{
		return _delegate.getCurrentLevel();
	}

	public void setCurrentCapacity(float metricAmount)
	{
_delegate.setCurrentCapacity(metricAmount);
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public float getCurrentTemperature()
	{
		return _delegate.getCurrentTemperature();
	}

	public float add(float metricAmount)
	{
		return _delegate.add(metricAmount);
	}

	public float addWaterWithTemperature(float mass, float temperature)
	{
		return _delegate.addWaterWithTemperature(mass,temperature);
	}

	public void setInitialWaterTemperature(float temperature)
	{
_delegate.setInitialWaterTemperature(temperature);
	}

	public boolean isPipe()
	{
		return _delegate.isPipe();
	}

	public float getInitialLevel()
	{
		return _delegate.getInitialLevel();
	}

	public void setEnableFailure(boolean pValue)
	{
_delegate.setEnableFailure(pValue);
	}

	public void clearAllMalfunctions()
	{
_delegate.clearAllMalfunctions();
	}

	public void tick()
	{
_delegate.tick();
	}

	public void setTickLength(float pInterval)
	{
_delegate.setTickLength(pInterval);
	}

	public boolean isFailureEnabled()
	{
		return _delegate.isFailureEnabled();
	}

	public java.lang.String[] getMalfunctionNames()
	{
		return _delegate.getMalfunctionNames();
	}

	public void scheduleMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength, int pTickToMalfunction)
	{
_delegate.scheduleMalfunction(pIntensity,pLength,pTickToMalfunction);
	}

}
