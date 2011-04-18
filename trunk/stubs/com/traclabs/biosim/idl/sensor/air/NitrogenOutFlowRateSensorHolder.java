package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "NitrogenOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class NitrogenOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public NitrogenOutFlowRateSensor value;
	public NitrogenOutFlowRateSensorHolder()
	{
	}
	public NitrogenOutFlowRateSensorHolder (final NitrogenOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return NitrogenOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		NitrogenOutFlowRateSensorHelper.write (_out,value);
	}
}
