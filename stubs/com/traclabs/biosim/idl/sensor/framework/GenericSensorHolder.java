package com.traclabs.biosim.idl.sensor.framework;

/**
 * Generated from IDL interface "GenericSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class GenericSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public GenericSensor value;
	public GenericSensorHolder()
	{
	}
	public GenericSensorHolder (final GenericSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GenericSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GenericSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GenericSensorHelper.write (_out,value);
	}
}
