package com.traclabs.biosim.idl.actuator.food;

/**
 *	Generated from IDL interface "FoodOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class FoodOutFlowRateActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodOutFlowRateActuator value;
	public FoodOutFlowRateActuatorHolder()
	{
	}
	public FoodOutFlowRateActuatorHolder (final FoodOutFlowRateActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodOutFlowRateActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodOutFlowRateActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodOutFlowRateActuatorHelper.write (_out,value);
	}
}
