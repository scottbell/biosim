package com.traclabs.biosim.idl.sensor.food;

/**
 * Generated from IDL interface "PlantDeathSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PlantDeathSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PlantDeathSensor value;
	public PlantDeathSensorHolder()
	{
	}
	public PlantDeathSensorHolder (final PlantDeathSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PlantDeathSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PlantDeathSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PlantDeathSensorHelper.write (_out,value);
	}
}
