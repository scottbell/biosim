package com.traclabs.biosim.idl.sensor.water;

/**
 * Generated from IDL interface "DirtyWaterInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DirtyWaterInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public DirtyWaterInFlowRateSensor value;
	public DirtyWaterInFlowRateSensorHolder()
	{
	}
	public DirtyWaterInFlowRateSensorHolder (final DirtyWaterInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DirtyWaterInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DirtyWaterInFlowRateSensorHelper.write (_out,value);
	}
}
