package com.traclabs.biosim.idl.actuator.air;

/**
 * Generated from IDL interface "NitrogenOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class NitrogenOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public NitrogenOutFlowRateActuator value;
	public NitrogenOutFlowRateActuatorHolder()
	{
	}
	public NitrogenOutFlowRateActuatorHolder (final NitrogenOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return NitrogenOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		NitrogenOutFlowRateActuatorHelper.write (_out,value);
	}
}
