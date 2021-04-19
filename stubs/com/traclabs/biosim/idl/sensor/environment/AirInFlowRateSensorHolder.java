package com.traclabs.biosim.idl.sensor.environment;

/**
 * Generated from IDL interface "AirInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class AirInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirInFlowRateSensor value;
	public AirInFlowRateSensorHolder()
	{
	}
	public AirInFlowRateSensorHolder (final AirInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirInFlowRateSensorHelper.write (_out,value);
	}
}
