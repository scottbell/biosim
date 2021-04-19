package com.traclabs.biosim.idl.sensor.power;

/**
 * Generated from IDL interface "PowerInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PowerInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerInFlowRateSensor value;
	public PowerInFlowRateSensorHolder()
	{
	}
	public PowerInFlowRateSensorHolder (final PowerInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerInFlowRateSensorHelper.write (_out,value);
	}
}
