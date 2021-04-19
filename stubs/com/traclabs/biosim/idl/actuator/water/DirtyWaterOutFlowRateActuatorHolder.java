package com.traclabs.biosim.idl.actuator.water;

/**
 * Generated from IDL interface "DirtyWaterOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DirtyWaterOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public DirtyWaterOutFlowRateActuator value;
	public DirtyWaterOutFlowRateActuatorHolder()
	{
	}
	public DirtyWaterOutFlowRateActuatorHolder (final DirtyWaterOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return DirtyWaterOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		DirtyWaterOutFlowRateActuatorHelper.write (_out,value);
	}
}
