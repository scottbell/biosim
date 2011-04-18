package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "EnvironmentSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EnvironmentSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public EnvironmentSensor value;
	public EnvironmentSensorHolder()
	{
	}
	public EnvironmentSensorHolder (final EnvironmentSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EnvironmentSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EnvironmentSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EnvironmentSensorHelper.write (_out,value);
	}
}
