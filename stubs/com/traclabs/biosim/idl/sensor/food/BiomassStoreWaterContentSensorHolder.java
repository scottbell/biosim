package com.traclabs.biosim.idl.sensor.food;

/**
 * Generated from IDL interface "BiomassStoreWaterContentSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassStoreWaterContentSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassStoreWaterContentSensor value;
	public BiomassStoreWaterContentSensorHolder()
	{
	}
	public BiomassStoreWaterContentSensorHolder (final BiomassStoreWaterContentSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassStoreWaterContentSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassStoreWaterContentSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassStoreWaterContentSensorHelper.write (_out,value);
	}
}
