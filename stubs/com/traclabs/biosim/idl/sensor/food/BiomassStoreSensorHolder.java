package com.traclabs.biosim.idl.sensor.food;

/**
 * Generated from IDL interface "BiomassStoreSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassStoreSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassStoreSensor value;
	public BiomassStoreSensorHolder()
	{
	}
	public BiomassStoreSensorHolder (final BiomassStoreSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassStoreSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassStoreSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassStoreSensorHelper.write (_out,value);
	}
}
