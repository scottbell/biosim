package com.traclabs.biosim.idl.actuator.water;

/**
 * Generated from IDL interface "GreyWaterOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class GreyWaterOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterOutFlowRateActuator value;
	public GreyWaterOutFlowRateActuatorHolder()
	{
	}
	public GreyWaterOutFlowRateActuatorHolder (final GreyWaterOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterOutFlowRateActuatorHelper.write (_out,value);
	}
}
