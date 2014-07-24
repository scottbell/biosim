package com.traclabs.biosim.idl.simulation.thermal;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "IFHX"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class IFHXPOATie
	extends IFHXPOA
{
	private IFHXOperations _delegate;

	private POA _poa;
	public IFHXPOATie(IFHXOperations delegate)
	{
		_delegate = delegate;
	}
	public IFHXPOATie(IFHXOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.thermal.IFHX _this()
	{
		return com.traclabs.biosim.idl.simulation.thermal.IFHXHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.thermal.IFHX _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.thermal.IFHXHelper.narrow(_this_object(orb));
	}
	public IFHXOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(IFHXOperations delegate)
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
	public float getTickLength()
	{
		return _delegate.getTickLength();
	}

	public void registerBioCommandListener(com.traclabs.biosim.idl.simulation.framework.BioCommandListener listener)
	{
_delegate.registerBioCommandListener(listener);
	}

	public void setLogLevel(com.traclabs.biosim.idl.framework.LogLevel pLogLevel)
	{
_delegate.setLogLevel(pLogLevel);
	}

	public void clearMalfunction(long id)
	{
_delegate.clearMalfunction(id);
	}

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition getGreyWaterProducerDefinition()
	{
		return _delegate.getGreyWaterProducerDefinition();
	}

	public boolean isMalfunctioning()
	{
		return _delegate.isMalfunctioning();
	}

	public void clearAllMalfunctions()
	{
_delegate.clearAllMalfunctions();
	}

	public void setEnableFailure(boolean pValue)
	{
_delegate.setEnableFailure(pValue);
	}

	public float randomFilter(float preFilteredValue)
	{
		return _delegate.randomFilter(preFilteredValue);
	}

	public void reset()
	{
_delegate.reset();
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public void maintain()
	{
_delegate.maintain();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public java.lang.String getModuleName()
	{
		return _delegate.getModuleName();
	}

	public void scheduleMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength, int pTickToMalfunction)
	{
_delegate.scheduleMalfunction(pIntensity,pLength,pTickToMalfunction);
	}

	public void tick()
	{
_delegate.tick();
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public java.lang.String[] getMalfunctionNames()
	{
		return _delegate.getMalfunctionNames();
	}

	public void setTickLength(float pInterval)
	{
_delegate.setTickLength(pInterval);
	}

	public com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition getGreyWaterConsumerDefinition()
	{
		return _delegate.getGreyWaterConsumerDefinition();
	}

	public boolean isFailureEnabled()
	{
		return _delegate.isFailureEnabled();
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public void log()
	{
_delegate.log();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public void fixAllMalfunctions()
	{
_delegate.fixAllMalfunctions();
	}

}
