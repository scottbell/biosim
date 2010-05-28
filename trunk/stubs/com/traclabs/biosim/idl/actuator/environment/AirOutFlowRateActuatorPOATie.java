package com.traclabs.biosim.idl.actuator.environment;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "AirOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class AirOutFlowRateActuatorPOATie
	extends AirOutFlowRateActuatorPOA
{
	private AirOutFlowRateActuatorOperations _delegate;

	private POA _poa;
	public AirOutFlowRateActuatorPOATie(AirOutFlowRateActuatorOperations delegate)
	{
		_delegate = delegate;
	}
	public AirOutFlowRateActuatorPOATie(AirOutFlowRateActuatorOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuator _this()
	{
		return com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuator _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorHelper.narrow(_this_object(orb));
	}
	public AirOutFlowRateActuatorOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(AirOutFlowRateActuatorOperations delegate)
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

	public void setLogLevel(com.traclabs.biosim.idl.framework.LogLevel pLogLevel)
	{
_delegate.setLogLevel(pLogLevel);
	}

	public void clearMalfunction(long id)
	{
_delegate.clearMalfunction(id);
	}

	public boolean isMalfunctioning()
	{
		return _delegate.isMalfunctioning();
	}

	public float getMax()
	{
		return _delegate.getMax();
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

	public com.traclabs.biosim.idl.simulation.environment.AirProducer getOutput()
	{
		return _delegate.getOutput();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public void setOutput(com.traclabs.biosim.idl.simulation.environment.AirProducer pProducer, int pIndex)
	{
_delegate.setOutput(pProducer,pIndex);
	}

	public float getValue()
	{
		return _delegate.getValue();
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

	public int getIndex()
	{
		return _delegate.getIndex();
	}

	public void setValue(float pValue)
	{
_delegate.setValue(pValue);
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

	public void log()
	{
_delegate.log();
	}

	public com.traclabs.biosim.idl.framework.BioModule getOutputModule()
	{
		return _delegate.getOutputModule();
	}

	public float getMin()
	{
		return _delegate.getMin();
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
