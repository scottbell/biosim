package com.traclabs.biosim.idl.sensor.framework;

/**
 *	Generated from IDL interface "EffluentValveSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
