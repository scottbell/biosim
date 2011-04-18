package com.traclabs.biosim.idl.sensor.water;

/**
 *	Generated from IDL interface "DirtyWaterOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DirtyWaterOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public DirtyWaterOutFlowRateSensor value;
	public DirtyWaterOutFlowRateSensorHolder()
	{
	}
	public DirtyWaterOutFlowRateSensorHolder (final DirtyWaterOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DirtyWaterOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DirtyWaterOutFlowRateSensorHelper.write (_out,value);
	}
}
