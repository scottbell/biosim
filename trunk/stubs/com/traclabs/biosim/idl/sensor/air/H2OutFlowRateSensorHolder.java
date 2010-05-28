package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "H2OutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class H2OutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public H2OutFlowRateSensor value;
	public H2OutFlowRateSensorHolder()
	{
	}
	public H2OutFlowRateSensorHolder (final H2OutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return H2OutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2OutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		H2OutFlowRateSensorHelper.write (_out,value);
	}
}
