package com.traclabs.biosim.idl.actuator.food;

/**
 *	Generated from IDL interface "FoodInFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class FoodInFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodInFlowRateActuator value;
	public FoodInFlowRateActuatorHolder()
	{
	}
	public FoodInFlowRateActuatorHolder (final FoodInFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodInFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodInFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodInFlowRateActuatorHelper.write (_out,value);
	}
}
