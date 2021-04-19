package com.traclabs.biosim.idl.simulation.air;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "H2ProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public class H2ProducerDefinitionPOATie
	extends H2ProducerDefinitionPOA
{
	private H2ProducerDefinitionOperations _delegate;

	private POA _poa;
	public H2ProducerDefinitionPOATie(H2ProducerDefinitionOperations delegate)
	{
		_delegate = delegate;
	}
	public H2ProducerDefinitionPOATie(H2ProducerDefinitionOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition __r = com.traclabs.biosim.idl.simulation.air.H2ProducerDefinitionHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition __r = com.traclabs.biosim.idl.simulation.air.H2ProducerDefinitionHelper.narrow(__o);
		return __r;
	}
	public H2ProducerDefinitionOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(H2ProducerDefinitionOperations delegate)
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
	public float getPercentageFull(int index)
	{
		return _delegate.getPercentageFull(index);
	}

	public void setStore(com.traclabs.biosim.idl.simulation.framework.Store pStore, int index)
	{
_delegate.setStore(pStore,index);
	}

	public void setInitialStores(com.traclabs.biosim.idl.simulation.framework.Store[] pStores)
	{
_delegate.setInitialStores(pStores);
	}

	public float[] getActualFlowRates()
	{
		return _delegate.getActualFlowRates();
	}

	public com.traclabs.biosim.idl.simulation.framework.Store[] getStores()
	{
		return _delegate.getStores();
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

	public float[] getDesiredFlowRates()
	{
		return _delegate.getDesiredFlowRates();
	}

	public boolean connectsTo(com.traclabs.biosim.idl.simulation.framework.Store pStore)
	{
		return _delegate.connectsTo(pStore);
	}

	public float getTotalMaxFlowRate()
	{
		return _delegate.getTotalMaxFlowRate();
	}

	public void setH2Outputs(com.traclabs.biosim.idl.simulation.air.H2Store[] destinations, float[] maxFlowRates, float[] desiredFlowRates)
	{
_delegate.setH2Outputs(destinations,maxFlowRates,desiredFlowRates);
	}

	public void setMaxFlowRate(float value, int index)
	{
_delegate.setMaxFlowRate(value,index);
	}

	public float getDesiredFlowRate(int index)
	{
		return _delegate.getDesiredFlowRate(index);
	}

	public float getTotalDesiredFlowRate()
	{
		return _delegate.getTotalDesiredFlowRate();
	}

}
