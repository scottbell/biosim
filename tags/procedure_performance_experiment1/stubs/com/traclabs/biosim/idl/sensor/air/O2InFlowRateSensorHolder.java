package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "O2InFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class O2InFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public O2InFlowRateSensor value;
	public O2InFlowRateSensorHolder()
	{
	}
	public O2InFlowRateSensorHolder (final O2InFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return O2InFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2InFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		O2InFlowRateSensorHelper.write (_out,value);
	}
}
