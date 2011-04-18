package com.traclabs.biosim.idl.sensor.food;

/**
 *	Generated from IDL interface "HarvestSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class HarvestSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public HarvestSensor value;
	public HarvestSensorHolder()
	{
	}
	public HarvestSensorHolder (final HarvestSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return HarvestSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = HarvestSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		HarvestSensorHelper.write (_out,value);
	}
}
