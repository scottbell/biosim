package com.traclabs.biosim.idl.sensor.crew;

/**
 *	Generated from IDL interface "CrewGroupAnyDeadSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CrewGroupAnyDeadSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CrewGroupAnyDeadSensor value;
	public CrewGroupAnyDeadSensorHolder()
	{
	}
	public CrewGroupAnyDeadSensorHolder (final CrewGroupAnyDeadSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrewGroupAnyDeadSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewGroupAnyDeadSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrewGroupAnyDeadSensorHelper.write (_out,value);
	}
}
