package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "GasConcentrationSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class GasConcentrationSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public GasConcentrationSensor value;
	public GasConcentrationSensorHolder()
	{
	}
	public GasConcentrationSensorHolder (final GasConcentrationSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GasConcentrationSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GasConcentrationSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GasConcentrationSensorHelper.write (_out,value);
	}
}
