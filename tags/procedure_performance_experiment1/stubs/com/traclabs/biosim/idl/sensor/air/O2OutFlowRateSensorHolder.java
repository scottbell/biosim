package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "O2OutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class O2OutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public O2OutFlowRateSensor value;
	public O2OutFlowRateSensorHolder()
	{
	}
	public O2OutFlowRateSensorHolder (final O2OutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return O2OutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2OutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		O2OutFlowRateSensorHelper.write (_out,value);
	}
}
