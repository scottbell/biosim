package com.traclabs.biosim.idl.actuator.environment;

/**
 * Generated from IDL interface "AirInFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class AirInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirInFlowRateActuator value;
	public AirInFlowRateActuatorHolder()
	{
	}
	public AirInFlowRateActuatorHolder (final AirInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirInFlowRateActuatorHelper.write (_out,value);
	}
}
