package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "TotalPressureSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class TotalPressureSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public TotalPressureSensor value;
	public TotalPressureSensorHolder()
	{
	}
	public TotalPressureSensorHolder (final TotalPressureSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return TotalPressureSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = TotalPressureSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		TotalPressureSensorHelper.write (_out,value);
	}
}
