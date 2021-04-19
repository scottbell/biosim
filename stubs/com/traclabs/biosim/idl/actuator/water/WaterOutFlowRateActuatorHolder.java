package com.traclabs.biosim.idl.actuator.water;

/**
 * Generated from IDL interface "WaterOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class WaterOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public WaterOutFlowRateActuator value;
	public WaterOutFlowRateActuatorHolder()
	{
	}
	public WaterOutFlowRateActuatorHolder (final WaterOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return WaterOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		WaterOutFlowRateActuatorHelper.write (_out,value);
	}
}
