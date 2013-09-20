package com.traclabs.biosim.idl.actuator.air;

/**
 *	Generated from IDL interface "MethaneOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
