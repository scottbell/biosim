package com.traclabs.biosim.idl.sensor.food;

/**
 * Generated from IDL interface "BiomassInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassInFlowRateSensor value;
	public BiomassInFlowRateSensorHolder()
	{
	}
	public BiomassInFlowRateSensorHolder (final BiomassInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassInFlowRateSensorHelper.write (_out,value);
	}
}
