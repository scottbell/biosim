package com.traclabs.biosim.idl.simulation.framework;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "SingleFlowRateControllable"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class SingleFlowRateControllablePOATie
	extends SingleFlowRateControllablePOA
{
	private SingleFlowRateControllableOperations _delegate;

	private POA _poa;
	public SingleFlowRateControllablePOATie(SingleFlowRateControllableOperations delegate)
	{
		_delegate = delegate;
	}
	public SingleFlowRateControllablePOATie(SingleFlowRateControllableOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable _this()
	{
		return com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllableHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllable _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllableHelper.narrow(_this_object(orb));
	}
	public SingleFlowRateControllableOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(SingleFlowRateControllableOperations delegate)
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
	public void setInitialActualFlowRates(float[] flowrates)
	{
_delegate.setInitialActualFlowRates(flowrates);
	}

	public float getMaxFlowRate(int index)
	{
		return _delegate.getMaxFlowRate(index);
	}

	public float[] getDesiredFlowRates()
	{
		return _delegate.getDesiredFlowRates();
	}

	public float[] getActualFlowRates()
	{
		return _delegate.getActualFlowRates();
	}

	public void setDesiredFlowRate(float value, int index)
	{
_delegate.setDesiredFlowRate(value,index);
	}

	public float getTotalDesiredFlowRate()
	{
		return _delegate.getTotalDesiredFlowRate();
	}

	public float[] getMaxFlowRates()
	{
		return _delegate.getMaxFlowRates();
	}

	public float getTotalMaxFlowRate()
	{
		return _delegate.getTotalMaxFlowRate();
	}

	public float getActualFlowRate(int index)
	{
		return _delegate.getActualFlowRate(index);
	}

	public float getTotalActualFlowRate()
	{
		return _delegate.getTotalActualFlowRate();
	}

	public void setMaxFlowRate(float value, int index)
	{
_delegate.setMaxFlowRate(value,index);
	}

	public void setInitialDesiredFlowRates(float[] flowrates)
	{
_delegate.setInitialDesiredFlowRates(flowrates);
	}

	public void setInitialMaxFlowRates(float[] flowrates)
	{
_delegate.setInitialMaxFlowRates(flowrates);
	}

	public float getAveragePercentageFull()
	{
		return _delegate.getAveragePercentageFull();
	}

	public float getPercentageFull(int index)
	{
		return _delegate.getPercentageFull(index);
	}

	public void reset()
	{
_delegate.reset();
	}

	public float getDesiredFlowRate(int index)
	{
		return _delegate.getDesiredFlowRate(index);
	}

}
