package com.traclabs.biosim.idl.simulation.power;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "RPCM"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class RPCMPOATie
	extends RPCMPOA
{
	private RPCMOperations _delegate;

	private POA _poa;
	public RPCMPOATie(RPCMOperations delegate)
	{
		_delegate = delegate;
	}
	public RPCMPOATie(RPCMOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.power.RPCM _this()
	{
		return com.traclabs.biosim.idl.simulation.power.RPCMHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.power.RPCM _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.power.RPCMHelper.narrow(_this_object(orb));
	}
	public RPCMOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(RPCMOperations delegate)
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

	public com.traclabs.biosim.idl.simulation.power.RPCMSwitchState getSwitchState()
	{
		return _delegate.getSwitchState();
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

	public com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition getPowerConsumerDefinition()
	{
		return _delegate.getPowerConsumerDefinition();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public void scheduleMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength, int pTickToMalfunction)
	{
_delegate.scheduleMalfunction(pIntensity,pLength,pTickToMalfunction);
	}

	public java.lang.String getModuleName()
	{
		return _delegate.getModuleName();
	}

	public com.traclabs.biosim.idl.simulation.power.RPCMArmedStatus getArmedStatus()
	{
		return _delegate.getArmedStatus();
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

	public boolean isFailureEnabled()
	{
		return _delegate.isFailureEnabled();
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public com.traclabs.biosim.idl.simulation.power.PowerProducerDefinition getPowerProducerDefinition()
	{
		return _delegate.getPowerProducerDefinition();
	}

	public void log()
	{
_delegate.log();
	}

	public void setSwitchState(com.traclabs.biosim.idl.simulation.power.RPCMSwitchState state)
	{
_delegate.setSwitchState(state);
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public void fixAllMalfunctions()
	{
_delegate.fixAllMalfunctions();
	}

	public void setArmedStatus(com.traclabs.biosim.idl.simulation.power.RPCMArmedStatus state)
	{
_delegate.setArmedStatus(state);
	}

}
