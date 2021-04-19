package com.traclabs.biosim.idl.sensor.crew;

/**
 * Generated from IDL interface "CrewGroupSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CrewGroupSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CrewGroupSensor value;
	public CrewGroupSensorHolder()
	{
	}
	public CrewGroupSensorHolder (final CrewGroupSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrewGroupSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewGroupSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrewGroupSensorHelper.write (_out,value);
	}
}
