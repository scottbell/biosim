package com.traclabs.biosim.idl.actuator.waste;

/**
 * Generated from IDL interface "DryWasteOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
