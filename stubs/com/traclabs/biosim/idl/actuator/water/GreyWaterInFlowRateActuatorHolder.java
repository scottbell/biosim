package com.traclabs.biosim.idl.actuator.water;

/**
 * Generated from IDL interface "GreyWaterInFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class GreyWaterInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterInFlowRateActuator value;
	public GreyWaterInFlowRateActuatorHolder()
	{
	}
	public GreyWaterInFlowRateActuatorHolder (final GreyWaterInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterInFlowRateActuatorHelper.write (_out,value);
	}
}
