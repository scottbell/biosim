package com.traclabs.biosim.idl.sensor.framework;

/**
 * Generated from IDL interface "StoreSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class StoreSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public StoreSensor value;
	public StoreSensorHolder()
	{
	}
	public StoreSensorHolder (final StoreSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return StoreSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StoreSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		StoreSensorHelper.write (_out,value);
	}
}
