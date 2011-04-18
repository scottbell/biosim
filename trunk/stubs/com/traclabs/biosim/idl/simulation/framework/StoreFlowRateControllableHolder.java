package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "StoreFlowRateControllable"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class StoreFlowRateControllableHolder	implements org.omg.CORBA.portable.Streamable{
	 public StoreFlowRateControllable value;
	public StoreFlowRateControllableHolder()
	{
	}
	public StoreFlowRateControllableHolder (final StoreFlowRateControllable initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return StoreFlowRateControllableHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StoreFlowRateControllableHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		StoreFlowRateControllableHelper.write (_out,value);
	}
}
