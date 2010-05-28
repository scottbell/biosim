package com.traclabs.biosim.idl.sensor.framework;

/**
 *	Generated from IDL interface "InfluentValveStateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class InfluentValveStateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public InfluentValveStateSensor value;
	public InfluentValveStateSensorHolder()
	{
	}
	public InfluentValveStateSensorHolder (final InfluentValveStateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return InfluentValveStateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = InfluentValveStateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		InfluentValveStateSensorHelper.write (_out,value);
	}
}
