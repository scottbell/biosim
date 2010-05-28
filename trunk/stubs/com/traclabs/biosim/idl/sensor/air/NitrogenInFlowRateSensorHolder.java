package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "NitrogenInFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class NitrogenInFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public NitrogenInFlowRateSensor value;
	public NitrogenInFlowRateSensorHolder()
	{
	}
	public NitrogenInFlowRateSensorHolder (final NitrogenInFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return NitrogenInFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenInFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		NitrogenInFlowRateSensorHelper.write (_out,value);
	}
}
