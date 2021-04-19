package com.traclabs.biosim.idl.sensor.framework;

/**
 * Generated from IDL interface "EffluentValveSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class EffluentValveSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public EffluentValveSensor value;
	public EffluentValveSensorHolder()
	{
	}
	public EffluentValveSensorHolder (final EffluentValveSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EffluentValveSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EffluentValveSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EffluentValveSensorHelper.write (_out,value);
	}
}
