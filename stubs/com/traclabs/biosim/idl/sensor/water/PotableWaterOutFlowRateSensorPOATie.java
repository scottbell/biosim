package com.traclabs.biosim.idl.sensor.water;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "PotableWaterOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public class PotableWaterOutFlowRateSensorPOATie
	extends PotableWaterOutFlowRateSensorPOA
{
	private PotableWaterOutFlowRateSensorOperations _delegate;

	private POA _poa;
	public PotableWaterOutFlowRateSensorPOATie(PotableWaterOutFlowRateSensorOperations delegate)
	{
		_delegate = delegate;
	}
	public PotableWaterOutFlowRateSensorPOATie(PotableWaterOutFlowRateSensorOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensor _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensor __r = com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensor _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensor __r = com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorHelper.narrow(__o);
		return __r;
	}
	public PotableWaterOutFlowRateSensorOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(PotableWaterOutFlowRateSensorOperations delegate)
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

	public void setInput(com.traclabs.biosim.idl.simulation.water.PotableWaterProducer pProducer, int pIndex)
	{
_delegate.setInput(pProducer,pIndex);
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

	public com.traclabs.biosim.idl.simulation.water.PotableWaterProducer getInput()
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
