package com.traclabs.biosim.idl.sensor.water;

/**
 * Generated from IDL interface "GreyWaterInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class GreyWaterInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterInFlowRateSensor value;
	public GreyWaterInFlowRateSensorHolder()
	{
	}
	public GreyWaterInFlowRateSensorHolder (final GreyWaterInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterInFlowRateSensorHelper.write (_out,value);
	}
}
