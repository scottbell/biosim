package com.traclabs.biosim.idl.sensor.water;

/**
 *	Generated from IDL interface "WaterOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class WaterOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterOutFlowRateSensor value;
	public WaterOutFlowRateSensorHolder()
	{
	}
	public WaterOutFlowRateSensorHolder (final WaterOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterOutFlowRateSensorHelper.write (_out,value);
	}
}
