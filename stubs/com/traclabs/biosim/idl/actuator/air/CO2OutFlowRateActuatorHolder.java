package com.traclabs.biosim.idl.actuator.air;

/**
 * Generated from IDL interface "CO2OutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CO2OutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CO2OutFlowRateActuator value;
	public CO2OutFlowRateActuatorHolder()
	{
	}
	public CO2OutFlowRateActuatorHolder (final CO2OutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CO2OutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2OutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CO2OutFlowRateActuatorHelper.write (_out,value);
	}
}
