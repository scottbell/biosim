package com.traclabs.biosim.idl.sensor.food;

/**
 * Generated from IDL interface "FoodOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class FoodOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodOutFlowRateSensor value;
	public FoodOutFlowRateSensorHolder()
	{
	}
	public FoodOutFlowRateSensorHolder (final FoodOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodOutFlowRateSensorHelper.write (_out,value);
	}
}
