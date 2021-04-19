package com.traclabs.biosim.idl.sensor.water;

/**
 * Generated from IDL interface "PotableWaterInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PotableWaterInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PotableWaterInFlowRateSensor value;
	public PotableWaterInFlowRateSensorHolder()
	{
	}
	public PotableWaterInFlowRateSensorHolder (final PotableWaterInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PotableWaterInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PotableWaterInFlowRateSensorHelper.write (_out,value);
	}
}
