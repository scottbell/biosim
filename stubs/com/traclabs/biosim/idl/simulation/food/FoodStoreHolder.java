package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL interface "FoodStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class FoodStoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public FoodStore value;
	public FoodStoreHolder()
	{
	}
	public FoodStoreHolder (final FoodStore initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FoodStoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodStoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FoodStoreHelper.write (_out,value);
	}
}
