package com.traclabs.biosim.idl.sensor.environment;

/**
 * Generated from IDL interface "GasConcentrationSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
