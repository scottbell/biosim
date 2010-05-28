package com.traclabs.biosim.idl.sensor.food;

/**
 *	Generated from IDL interface "FoodInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class FoodInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodInFlowRateSensor value;
	public FoodInFlowRateSensorHolder()
	{
	}
	public FoodInFlowRateSensorHolder (final FoodInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodInFlowRateSensorHelper.write (_out,value);
	}
}
