package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "FoodProcessor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class FoodProcessorHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodProcessor value;
	public FoodProcessorHolder()
	{
	}
	public FoodProcessorHolder (final FoodProcessor initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodProcessorHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodProcessorHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodProcessorHelper.write (_out,value);
	}
}
