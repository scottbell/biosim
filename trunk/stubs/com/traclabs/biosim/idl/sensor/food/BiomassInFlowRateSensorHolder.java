package com.traclabs.biosim.idl.sensor.food;

/**
 *	Generated from IDL interface "BiomassInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
