package com.traclabs.biosim.idl.sensor.crew;

/**
 * Generated from IDL interface "CrewGroupDeathSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CrewGroupDeathSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CrewGroupDeathSensor value;
	public CrewGroupDeathSensorHolder()
	{
	}
	public CrewGroupDeathSensorHolder (final CrewGroupDeathSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CrewGroupDeathSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CrewGroupDeathSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CrewGroupDeathSensorHelper.write (_out,value);
	}
}
