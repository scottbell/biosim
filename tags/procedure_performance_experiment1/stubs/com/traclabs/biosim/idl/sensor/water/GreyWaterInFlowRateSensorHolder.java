package com.traclabs.biosim.idl.sensor.water;

/**
 *	Generated from IDL interface "GreyWaterInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
