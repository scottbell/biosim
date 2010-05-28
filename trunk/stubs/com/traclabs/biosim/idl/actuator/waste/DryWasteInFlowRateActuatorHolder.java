package com.traclabs.biosim.idl.actuator.waste;

/**
 *	Generated from IDL interface "DryWasteInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DryWasteInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public DryWasteInFlowRateActuator value;
	public DryWasteInFlowRateActuatorHolder()
	{
	}
	public DryWasteInFlowRateActuatorHolder (final DryWasteInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DryWasteInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DryWasteInFlowRateActuatorHelper.write (_out,value);
	}
}
