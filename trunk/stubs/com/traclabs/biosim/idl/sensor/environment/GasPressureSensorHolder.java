package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "GasPressureSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class GasPressureSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public GasPressureSensor value;
	public GasPressureSensorHolder()
	{
	}
	public GasPressureSensorHolder (final GasPressureSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GasPressureSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GasPressureSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GasPressureSensorHelper.write (_out,value);
	}
}
