package com.traclabs.biosim.idl.sensor.power;

/**
 *	Generated from IDL interface "PowerOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PowerOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerOutFlowRateSensor value;
	public PowerOutFlowRateSensorHolder()
	{
	}
	public PowerOutFlowRateSensorHolder (final PowerOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerOutFlowRateSensorHelper.write (_out,value);
	}
}
