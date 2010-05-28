package com.traclabs.biosim.idl.sensor.food;

/**
 *	Generated from IDL interface "ShelfSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ShelfSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public ShelfSensor value;
	public ShelfSensorHolder()
	{
	}
	public ShelfSensorHolder (final ShelfSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ShelfSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ShelfSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ShelfSensorHelper.write (_out,value);
	}
}
