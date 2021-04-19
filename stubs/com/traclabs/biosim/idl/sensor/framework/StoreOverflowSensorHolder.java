package com.traclabs.biosim.idl.sensor.framework;

/**
 * Generated from IDL interface "StoreOverflowSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class StoreOverflowSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public StoreOverflowSensor value;
	public StoreOverflowSensorHolder()
	{
	}
	public StoreOverflowSensorHolder (final StoreOverflowSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return StoreOverflowSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StoreOverflowSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		StoreOverflowSensorHelper.write (_out,value);
	}
}
