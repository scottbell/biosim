package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "H2InFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class H2InFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public H2InFlowRateSensor value;
	public H2InFlowRateSensorHolder()
	{
	}
	public H2InFlowRateSensorHolder (final H2InFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return H2InFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2InFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		H2InFlowRateSensorHelper.write (_out,value);
	}
}
