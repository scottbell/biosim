package com.traclabs.biosim.idl.actuator.air;

/**
 * Generated from IDL interface "O2InFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class O2InFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public O2InFlowRateActuator value;
	public O2InFlowRateActuatorHolder()
	{
	}
	public O2InFlowRateActuatorHolder (final O2InFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return O2InFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2InFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		O2InFlowRateActuatorHelper.write (_out,value);
	}
}
