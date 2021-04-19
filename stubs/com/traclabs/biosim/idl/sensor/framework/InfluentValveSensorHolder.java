package com.traclabs.biosim.idl.sensor.framework;

/**
 * Generated from IDL interface "InfluentValveSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class InfluentValveSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public InfluentValveSensor value;
	public InfluentValveSensorHolder()
	{
	}
	public InfluentValveSensorHolder (final InfluentValveSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return InfluentValveSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = InfluentValveSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		InfluentValveSensorHelper.write (_out,value);
	}
}
