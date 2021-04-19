package com.traclabs.biosim.idl.sensor.air;

/**
 * Generated from IDL interface "CO2InFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CO2InFlowRateSensorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CO2InFlowRateSensor value;
	public CO2InFlowRateSensorHolder()
	{
	}
	public CO2InFlowRateSensorHolder (final CO2InFlowRateSensor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CO2InFlowRateSensorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2InFlowRateSensorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CO2InFlowRateSensorHelper.write (_out,value);
	}
}
