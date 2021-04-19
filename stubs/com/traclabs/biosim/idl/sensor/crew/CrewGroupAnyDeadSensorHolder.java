package com.traclabs.biosim.idl.sensor.crew;

/**
 * Generated from IDL interface "CrewGroupAnyDeadSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
