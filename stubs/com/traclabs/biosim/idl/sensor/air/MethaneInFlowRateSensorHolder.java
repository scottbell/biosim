package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "MethaneInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class MethaneInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public MethaneInFlowRateSensor value;
	public MethaneInFlowRateSensorHolder()
	{
	}
	public MethaneInFlowRateSensorHolder (final MethaneInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MethaneInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MethaneInFlowRateSensorHelper.write (_out,value);
	}
}
