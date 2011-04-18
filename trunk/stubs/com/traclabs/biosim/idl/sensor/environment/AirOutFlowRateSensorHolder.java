package com.traclabs.biosim.idl.sensor.environment;

/**
 *	Generated from IDL interface "AirOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class AirOutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirOutFlowRateSensor value;
	public AirOutFlowRateSensorHolder()
	{
	}
	public AirOutFlowRateSensorHolder (final AirOutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirOutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirOutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirOutFlowRateSensorHelper.write (_out,value);
	}
}
