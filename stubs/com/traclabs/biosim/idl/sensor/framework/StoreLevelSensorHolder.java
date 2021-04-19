package com.traclabs.biosim.idl.sensor.framework;

/**
 * Generated from IDL interface "StoreLevelSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class StoreLevelSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public StoreLevelSensor value;
	public StoreLevelSensorHolder()
	{
	}
	public StoreLevelSensorHolder (final StoreLevelSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return StoreLevelSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StoreLevelSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		StoreLevelSensorHelper.write (_out,value);
	}
}
