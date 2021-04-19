package com.traclabs.biosim.idl.sensor.food;

/**
 * Generated from IDL interface "TimeTillCanopyClosureSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class TimeTillCanopyClosureSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public TimeTillCanopyClosureSensor value;
	public TimeTillCanopyClosureSensorHolder()
	{
	}
	public TimeTillCanopyClosureSensorHolder (final TimeTillCanopyClosureSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return TimeTillCanopyClosureSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TimeTillCanopyClosureSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		TimeTillCanopyClosureSensorHelper.write (_out,value);
	}
}
