package com.traclabs.biosim.idl.sensor.water;

/**
 *	Generated from IDL interface "GreyWaterOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class GreyWaterOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterOutFlowRateSensor value;
	public GreyWaterOutFlowRateSensorHolder()
	{
	}
	public GreyWaterOutFlowRateSensorHolder (final GreyWaterOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterOutFlowRateSensorHelper.write (_out,value);
	}
}
