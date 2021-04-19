package com.traclabs.biosim.idl.actuator.air;

/**
 * Generated from IDL interface "NitrogenInFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class NitrogenInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public NitrogenInFlowRateActuator value;
	public NitrogenInFlowRateActuatorHolder()
	{
	}
	public NitrogenInFlowRateActuatorHolder (final NitrogenInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return NitrogenInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		NitrogenInFlowRateActuatorHelper.write (_out,value);
	}
}
