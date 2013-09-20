package com.traclabs.biosim.idl.actuator.power;

/**
 *	Generated from IDL interface "PowerInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PowerInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerInFlowRateActuator value;
	public PowerInFlowRateActuatorHolder()
	{
	}
	public PowerInFlowRateActuatorHolder (final PowerInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerInFlowRateActuatorHelper.write (_out,value);
	}
}
