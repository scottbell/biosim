package com.traclabs.biosim.idl.sensor.crew;

/**
 * Generated from IDL interface "CrewGroupProductivitySensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CrewGroupProductivitySensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CrewGroupProductivitySensor value;
	public CrewGroupProductivitySensorHolder()
	{
	}
	public CrewGroupProductivitySensorHolder (final CrewGroupProductivitySensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrewGroupProductivitySensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewGroupProductivitySensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrewGroupProductivitySensorHelper.write (_out,value);
	}
}
