package com.traclabs.biosim.idl.actuator.air;

/**
 * Generated from IDL interface "O2OutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class O2OutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public O2OutFlowRateActuator value;
	public O2OutFlowRateActuatorHolder()
	{
	}
	public O2OutFlowRateActuatorHolder (final O2OutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return O2OutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2OutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		O2OutFlowRateActuatorHelper.write (_out,value);
	}
}
