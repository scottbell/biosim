package com.traclabs.biosim.idl.actuator.air;

/**
 * Generated from IDL interface "H2InFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class H2InFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public H2InFlowRateActuator value;
	public H2InFlowRateActuatorHolder()
	{
	}
	public H2InFlowRateActuatorHolder (final H2InFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return H2InFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2InFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		H2InFlowRateActuatorHelper.write (_out,value);
	}
}
