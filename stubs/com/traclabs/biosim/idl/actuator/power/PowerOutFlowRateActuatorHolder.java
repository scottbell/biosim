package com.traclabs.biosim.idl.actuator.power;

/**
 *	Generated from IDL interface "PowerOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class PowerOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public PowerOutFlowRateActuator value;
	public PowerOutFlowRateActuatorHolder()
	{
	}
	public PowerOutFlowRateActuatorHolder (final PowerOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return PowerOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		PowerOutFlowRateActuatorHelper.write (_out,value);
	}
}
