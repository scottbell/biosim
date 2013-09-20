package com.traclabs.biosim.idl.actuator.water;

/**
 *	Generated from IDL interface "GreyWaterOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class GreyWaterOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public GreyWaterOutFlowRateActuator value;
	public GreyWaterOutFlowRateActuatorHolder()
	{
	}
	public GreyWaterOutFlowRateActuatorHolder (final GreyWaterOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GreyWaterOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GreyWaterOutFlowRateActuatorHelper.write (_out,value);
	}
}
