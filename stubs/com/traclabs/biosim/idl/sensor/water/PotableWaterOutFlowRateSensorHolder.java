package com.traclabs.biosim.idl.sensor.water;

/**
 * Generated from IDL interface "PotableWaterOutFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class PotableWaterOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PotableWaterOutFlowRateSensor value;
	public PotableWaterOutFlowRateSensorHolder()
	{
	}
	public PotableWaterOutFlowRateSensorHolder (final PotableWaterOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PotableWaterOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PotableWaterOutFlowRateSensorHelper.write (_out,value);
	}
}
