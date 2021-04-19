package com.traclabs.biosim.idl.sensor.framework;

/**
 * Generated from IDL interface "TimeSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class TimeSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public TimeSensor value;
	public TimeSensorHolder()
	{
	}
	public TimeSensorHolder (final TimeSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return TimeSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TimeSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		TimeSensorHelper.write (_out,value);
	}
}
