package com.traclabs.biosim.idl.actuator.air;

/**
 * Generated from IDL interface "CO2InFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class CO2InFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public CO2InFlowRateActuator value;
	public CO2InFlowRateActuatorHolder()
	{
	}
	public CO2InFlowRateActuatorHolder (final CO2InFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CO2InFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2InFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CO2InFlowRateActuatorHelper.write (_out,value);
	}
}
