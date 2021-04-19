package com.traclabs.biosim.idl.sensor.framework;

/**
 * Generated from IDL interface "EffluentValveStateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class EffluentValveStateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public EffluentValveStateSensor value;
	public EffluentValveStateSensorHolder()
	{
	}
	public EffluentValveStateSensorHolder (final EffluentValveStateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EffluentValveStateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EffluentValveStateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EffluentValveStateSensorHelper.write (_out,value);
	}
}
