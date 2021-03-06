package com.traclabs.biosim.idl.simulation.air;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "AirRS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public class AirRSPOATie
	extends AirRSPOA
{
	private AirRSOperations _delegate;

	private POA _poa;
	public AirRSPOATie(AirRSOperations delegate)
	{
		_delegate = delegate;
	}
	public AirRSPOATie(AirRSOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.air.AirRS _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.air.AirRS __r = com.traclabs.biosim.idl.simulation.air.AirRSHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.air.AirRS _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.air.AirRS __r = com.traclabs.biosim.idl.simulation.air.AirRSHelper.narrow(__o);
		return __r;
	}
	public AirRSOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(AirRSOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		return super._default_POA();
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

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition getH2ProducerDefinition()
	{
		return _delegate.getH2ProducerDefinition();
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinition getPotableWaterProducerDefinition()
	{
		return _delegate.getPotableWaterProducerDefinition();
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public com.traclabs.biosim.idl.simulation.air.H2ConsumerDefinition getH2ConsumerDefinition()
	{
		return _delegate.getH2ConsumerDefinition();
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

	public float randomFilter(float preFilteredValue)
	{
		return _delegate.randomFilter(preFilteredValue);
	}

	public boolean isMalfunctioning()
	{
		return _delegate.isMalfunctioning();
	}

	public com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition getPotableWaterConsumerDefinition()
	{
		return _delegate.getPotableWaterConsumerDefinition();
	}

	public void log()
	{
_delegate.log();
	}

	public com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition getAirProducerDefinition()
	{
		return _delegate.getAirProducerDefinition();
	}

	public void maintain()
	{
_delegate.maintain();
	}

	public com.traclabs.biosim.idl.simulation.air.O2ProducerDefinition getO2ProducerDefinition()
	{
		return _delegate.getO2ProducerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition getCO2ProducerDefinition()
	{
		return _delegate.getCO2ProducerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.air.MethaneProducerDefinition getMethaneProducerDefinition()
	{
		return _delegate.getMethaneProducerDefinition();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public void setEnableFailure(boolean pValue)
	{
_delegate.setEnableFailure(pValue);
	}

	public com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition getAirConsumerDefinition()
	{
		return _delegate.getAirConsumerDefinition();
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

	public com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinition getCO2ConsumerDefinition()
	{
		return _delegate.getCO2ConsumerDefinition();
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
