package com.traclabs.biosim.idl.sensor.environment;

/**
 * Generated from IDL interface "AirOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class AirOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirOutFlowRateSensor value;
	public AirOutFlowRateSensorHolder()
	{
	}
	public AirOutFlowRateSensorHolder (final AirOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirOutFlowRateSensorHelper.write (_out,value);
	}
}
