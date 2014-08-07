package com.traclabs.biosim.idl.simulation.thermal;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "IATCS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class IATCSPOATie
	extends IATCSPOA
{
	private IATCSOperations _delegate;

	private POA _poa;
	public IATCSPOATie(IATCSOperations delegate)
	{
		_delegate = delegate;
	}
	public IATCSPOATie(IATCSOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.thermal.IATCS _this()
	{
		return com.traclabs.biosim.idl.simulation.thermal.IATCSHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.thermal.IATCS _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.thermal.IATCSHelper.narrow(_this_object(orb));
	}
	public IATCSOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(IATCSOperations delegate)
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
	public com.traclabs.biosim.idl.simulation.thermal.IATCSActivation getActivateState()
	{
		return _delegate.getActivateState();
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

	public com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition getGreyWaterProducerDefinition()
	{
		return _delegate.getGreyWaterProducerDefinition();
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public void setPumpSpeed(float pumpSpeed)
	{
_delegate.setPumpSpeed(pumpSpeed);
	}

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatus getPpaPumpSpeedCommandStatus()
	{
		return _delegate.getPpaPumpSpeedCommandStatus();
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus getIsolationValveCommandStatus()
	{
		return _delegate.getIsolationValveCommandStatus();
	}

	public com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition getGreyWaterConsumerDefinition()
	{
		return _delegate.getGreyWaterConsumerDefinition();
	}

	public void setPpaPumpSpeedCommandStatus(com.traclabs.biosim.idl.simulation.thermal.PPAPumpSpeedStatus ppaPumpSpeedCommandStatus)
	{
_delegate.setPpaPumpSpeedCommandStatus(ppaPumpSpeedCommandStatus);
	}

	public void setIsloationValveState(com.traclabs.biosim.idl.simulation.thermal.IFHXValveState isloationValveState)
	{
_delegate.setIsloationValveState(isloationValveState);
	}

	public void setIatcsState(com.traclabs.biosim.idl.simulation.thermal.IATCSState state)
	{
_delegate.setIatcsState(state);
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public void setBypassValveCommandStatus(com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus bypassValveCommandStatus)
	{
_delegate.setBypassValveCommandStatus(bypassValveCommandStatus);
	}

	public com.traclabs.biosim.idl.simulation.thermal.IFHXBypassState getBypassValveState()
	{
		return _delegate.getBypassValveState();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public com.traclabs.biosim.idl.simulation.thermal.SoftwareState getSfcaSoftwareState()
	{
		return _delegate.getSfcaSoftwareState();
	}

	public void reset()
	{
_delegate.reset();
	}

	public void registerBioCommandListener(com.traclabs.biosim.idl.simulation.framework.BioCommandListener listener)
	{
_delegate.registerBioCommandListener(listener);
	}

	public java.lang.String getModuleName()
	{
		return _delegate.getModuleName();
	}

	public void setBypassValveState(com.traclabs.biosim.idl.simulation.thermal.IFHXBypassState bypassValveState)
	{
_delegate.setBypassValveState(bypassValveState);
	}

	public void setHeaterSoftwareState(com.traclabs.biosim.idl.simulation.thermal.SoftwareState newHeaterSoftwareState)
	{
_delegate.setHeaterSoftwareState(newHeaterSoftwareState);
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

	public void setActivateState(com.traclabs.biosim.idl.simulation.thermal.IATCSActivation activateState)
	{
_delegate.setActivateState(activateState);
	}

	public void setTwvmSoftwareState(com.traclabs.biosim.idl.simulation.thermal.SoftwareState twvmSoftwareState)
	{
_delegate.setTwvmSoftwareState(twvmSoftwareState);
	}

	public float getPumpSpeed()
	{
		return _delegate.getPumpSpeed();
	}

	public void maintain()
	{
_delegate.maintain();
	}

	public com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus getBypassValveCommandStatus()
	{
		return _delegate.getBypassValveCommandStatus();
	}

	public void setIsolationValveCommandStatus(com.traclabs.biosim.idl.simulation.thermal.IFHXValveCommandStatus isolationValveCommandStatus)
	{
_delegate.setIsolationValveCommandStatus(isolationValveCommandStatus);
	}

	public void setSfcaSoftwareState(com.traclabs.biosim.idl.simulation.thermal.SoftwareState sfcaSoftwareState)
	{
_delegate.setSfcaSoftwareState(sfcaSoftwareState);
	}

	public com.traclabs.biosim.idl.simulation.thermal.SoftwareState getHeaterSoftwareState()
	{
		return _delegate.getHeaterSoftwareState();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public void setEnableFailure(boolean pValue)
	{
_delegate.setEnableFailure(pValue);
	}

	public void clearAllMalfunctions()
	{
_delegate.clearAllMalfunctions();
	}

	public com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition getPowerConsumerDefinition()
	{
		return _delegate.getPowerConsumerDefinition();
	}

	public void tick()
	{
_delegate.tick();
	}

	public void setTickLength(float pInterval)
	{
_delegate.setTickLength(pInterval);
	}

	public com.traclabs.biosim.idl.simulation.thermal.IATCSState getIatcsState()
	{
		return _delegate.getIatcsState();
	}

	public com.traclabs.biosim.idl.simulation.thermal.IFHXValveState getIsloationValveState()
	{
		return _delegate.getIsloationValveState();
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

	public com.traclabs.biosim.idl.simulation.thermal.SoftwareState getTwvmSoftwareState()
	{
		return _delegate.getTwvmSoftwareState();
	}

}
