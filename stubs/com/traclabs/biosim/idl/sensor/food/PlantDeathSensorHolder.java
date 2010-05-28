package com.traclabs.biosim.idl.sensor.food;

/**
 *	Generated from IDL interface "PlantDeathSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
