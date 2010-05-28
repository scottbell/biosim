package com.traclabs.biosim.idl.sensor.food;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "FoodInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class FoodInFlowRateSensorPOATie
	extends FoodInFlowRateSensorPOA
{
	private FoodInFlowRateSensorOperations _delegate;

	private POA _poa;
	public FoodInFlowRateSensorPOATie(FoodInFlowRateSensorOperations delegate)
	{
		_delegate = delegate;
	}
	public FoodInFlowRateSensorPOATie(FoodInFlowRateSensorOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensor _this()
	{
		return com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensor _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorHelper.narrow(_this_object(orb));
	}
	public FoodInFlowRateSensorOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(FoodInFlowRateSensorOperations delegate)
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

	public void setInput(com.traclabs.biosim.idl.simulation.food.FoodConsumer pConsumer, int pIndex)
	{
_delegate.setInput(pConsumer,pIndex);
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

	public com.traclabs.biosim.idl.simulation.food.FoodConsumer getInput()
	{
		return _delegate.getInput();
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

	public float getMin()
	{
		return _delegate.getMin();
	}

	public com.traclabs.biosim.idl.framework.BioModule getInputModule()
	{
		return _delegate.getInputModule();
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
