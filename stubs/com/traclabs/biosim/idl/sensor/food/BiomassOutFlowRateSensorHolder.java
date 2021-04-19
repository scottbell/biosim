package com.traclabs.biosim.idl.sensor.food;

/**
 * Generated from IDL interface "BiomassOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public BiomassOutFlowRateSensor value;
	public BiomassOutFlowRateSensorHolder()
	{
	}
	public BiomassOutFlowRateSensorHolder (final BiomassOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return BiomassOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		BiomassOutFlowRateSensorHelper.write (_out,value);
	}
}
