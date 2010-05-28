package com.traclabs.biosim.idl.sensor.air;

/**
 *	Generated from IDL interface "CO2OutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CO2OutFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CO2OutFlowRateSensor value;
	public CO2OutFlowRateSensorHolder()
	{
	}
	public CO2OutFlowRateSensorHolder (final CO2OutFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CO2OutFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2OutFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CO2OutFlowRateSensorHelper.write (_out,value);
	}
}
