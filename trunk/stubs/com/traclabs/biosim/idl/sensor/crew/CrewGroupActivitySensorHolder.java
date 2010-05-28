package com.traclabs.biosim.idl.sensor.crew;

/**
 *	Generated from IDL interface "CrewGroupActivitySensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CrewGroupActivitySensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CrewGroupActivitySensor value;
	public CrewGroupActivitySensorHolder()
	{
	}
	public CrewGroupActivitySensorHolder (final CrewGroupActivitySensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrewGroupActivitySensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewGroupActivitySensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrewGroupActivitySensorHelper.write (_out,value);
	}
}
