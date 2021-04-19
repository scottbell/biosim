package com.traclabs.biosim.idl.actuator.food;

/**
 * Generated from IDL interface "FoodProcessorActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class FoodProcessorActuatorHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodProcessorActuator value;
	public FoodProcessorActuatorHolder()
	{
	}
	public FoodProcessorActuatorHolder (final FoodProcessorActuator initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodProcessorActuatorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodProcessorActuatorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodProcessorActuatorHelper.write (_out,value);
	}
}
