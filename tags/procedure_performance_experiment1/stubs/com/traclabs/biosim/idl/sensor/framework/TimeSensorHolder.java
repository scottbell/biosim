package com.traclabs.biosim.idl.sensor.framework;

/**
 *	Generated from IDL interface "TimeSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
