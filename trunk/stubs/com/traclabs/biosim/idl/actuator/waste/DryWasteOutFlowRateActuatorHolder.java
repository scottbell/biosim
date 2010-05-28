package com.traclabs.biosim.idl.actuator.waste;

/**
 *	Generated from IDL interface "DryWasteOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class DryWasteOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public DryWasteOutFlowRateActuator value;
	public DryWasteOutFlowRateActuatorHolder()
	{
	}
	public DryWasteOutFlowRateActuatorHolder (final DryWasteOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DryWasteOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DryWasteOutFlowRateActuatorHelper.write (_out,value);
	}
}
