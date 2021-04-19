package com.traclabs.biosim.idl.actuator.air;

/**
 * Generated from IDL interface "MethaneInFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class MethaneInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public MethaneInFlowRateActuator value;
	public MethaneInFlowRateActuatorHolder()
	{
	}
	public MethaneInFlowRateActuatorHolder (final MethaneInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MethaneInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MethaneInFlowRateActuatorHelper.write (_out,value);
	}
}
