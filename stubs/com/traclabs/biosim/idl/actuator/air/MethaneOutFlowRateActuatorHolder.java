package com.traclabs.biosim.idl.actuator.air;

/**
 * Generated from IDL interface "MethaneOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class MethaneOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public MethaneOutFlowRateActuator value;
	public MethaneOutFlowRateActuatorHolder()
	{
	}
	public MethaneOutFlowRateActuatorHolder (final MethaneOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MethaneOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MethaneOutFlowRateActuatorHelper.write (_out,value);
	}
}
