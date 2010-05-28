package com.traclabs.biosim.idl.simulation.environment;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "EnvironmentFlowRateControllable"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class EnvironmentFlowRateControllablePOATie
	extends EnvironmentFlowRateControllablePOA
{
	private EnvironmentFlowRateControllableOperations _delegate;

	private POA _poa;
	public EnvironmentFlowRateControllablePOATie(EnvironmentFlowRateControllableOperations delegate)
	{
		_delegate = delegate;
	}
	public EnvironmentFlowRateControllablePOATie(EnvironmentFlowRateControllableOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllable _this()
	{
		return com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllableHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllable _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllableHelper.narrow(_this_object(orb));
	}
	public EnvironmentFlowRateControllableOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(EnvironmentFlowRateControllableOperations delegate)
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
	public float getPercentageFull(int index)
	{
		return _delegate.getPercentageFull(index);
	}

	public float[] getActualFlowRates()
	{
		return _delegate.getActualFlowRates();
	}

	public float[] getMaxFlowRates()
	{
		return _delegate.getMaxFlowRates();
	}

	public void reset()
	{
_delegate.reset();
	}

	public void setInitialDesiredFlowRates(float[] flowrates)
	{
_delegate.setInitialDesiredFlowRates(flowrates);
	}

	public void setDesiredFlowRate(float value, int index)
	{
_delegate.setDesiredFlowRate(value,index);
	}

	public float getTotalActualFlowRate()
	{
		return _delegate.getTotalActualFlowRate();
	}

	public void setInitialActualFlowRates(float[] flowrates)
	{
_delegate.setInitialActualFlowRates(flowrates);
	}

	public float getActualFlowRate(int index)
	{
		return _delegate.getActualFlowRate(index);
	}

	public void setInitialMaxFlowRates(float[] flowrates)
	{
_delegate.setInitialMaxFlowRates(flowrates);
	}

	public float getMaxFlowRate(int index)
	{
		return _delegate.getMaxFlowRate(index);
	}

	public float getAveragePercentageFull()
	{
		return _delegate.getAveragePercentageFull();
	}

	public boolean connectsTo(com.traclabs.biosim.idl.simulation.environment.SimEnvironment pSimEnvironment)
	{
		return _delegate.connectsTo(pSimEnvironment);
	}

	public float getTotalMaxFlowRate()
	{
		return _delegate.getTotalMaxFlowRate();
	}

	public float[] getDesiredFlowRates()
	{
		return _delegate.getDesiredFlowRates();
	}

	public void setMaxFlowRate(float value, int index)
	{
_delegate.setMaxFlowRate(value,index);
	}

	public float getDesiredFlowRate(int index)
	{
		return _delegate.getDesiredFlowRate(index);
	}

	public com.traclabs.biosim.idl.simulation.environment.SimEnvironment[] getEnvironments()
	{
		return _delegate.getEnvironments();
	}

	public float getTotalDesiredFlowRate()
	{
		return _delegate.getTotalDesiredFlowRate();
	}

}
