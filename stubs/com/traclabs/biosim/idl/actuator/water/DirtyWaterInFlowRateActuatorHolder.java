package com.traclabs.biosim.idl.actuator.water;

/**
 * Generated from IDL interface "DirtyWaterInFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DirtyWaterInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public DirtyWaterInFlowRateActuator value;
	public DirtyWaterInFlowRateActuatorHolder()
	{
	}
	public DirtyWaterInFlowRateActuatorHolder (final DirtyWaterInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DirtyWaterInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DirtyWaterInFlowRateActuatorHelper.write (_out,value);
	}
}
