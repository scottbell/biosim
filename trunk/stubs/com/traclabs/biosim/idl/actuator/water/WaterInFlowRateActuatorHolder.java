package com.traclabs.biosim.idl.actuator.water;

/**
 *	Generated from IDL interface "WaterInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class WaterInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterInFlowRateActuator value;
	public WaterInFlowRateActuatorHolder()
	{
	}
	public WaterInFlowRateActuatorHolder (final WaterInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterInFlowRateActuatorHelper.write (_out,value);
	}
}
