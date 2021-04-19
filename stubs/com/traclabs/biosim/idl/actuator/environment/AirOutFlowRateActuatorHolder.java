package com.traclabs.biosim.idl.actuator.environment;

/**
 * Generated from IDL interface "AirOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class AirOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public AirOutFlowRateActuator value;
	public AirOutFlowRateActuatorHolder()
	{
	}
	public AirOutFlowRateActuatorHolder (final AirOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AirOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AirOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AirOutFlowRateActuatorHelper.write (_out,value);
	}
}
