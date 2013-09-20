package com.traclabs.biosim.idl.actuator.air;

/**
 *	Generated from IDL interface "H2OutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class H2OutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public H2OutFlowRateActuator value;
	public H2OutFlowRateActuatorHolder()
	{
	}
	public H2OutFlowRateActuatorHolder (final H2OutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return H2OutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2OutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		H2OutFlowRateActuatorHelper.write (_out,value);
	}
}
