package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL interface "FoodConsumer".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class FoodConsumerHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodConsumer value;
	public FoodConsumerHolder()
	{
	}
	public FoodConsumerHolder (final FoodConsumer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodConsumerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodConsumerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodConsumerHelper.write (_out,value);
	}
}
