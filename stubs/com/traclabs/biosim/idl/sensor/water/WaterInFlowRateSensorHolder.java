package com.traclabs.biosim.idl.sensor.water;

/**
 * Generated from IDL interface "WaterInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class WaterInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterInFlowRateSensor value;
	public WaterInFlowRateSensorHolder()
	{
	}
	public WaterInFlowRateSensorHolder (final WaterInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterInFlowRateSensorHelper.write (_out,value);
	}
}
