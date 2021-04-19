package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL alias "FoodStoreList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class FoodStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.food.FoodStore[] value;

	public FoodStoreListHolder ()
	{
	}
	public FoodStoreListHolder (final com.traclabs.biosim.idl.simulation.food.FoodStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return FoodStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		FoodStoreListHelper.write (out,value);
	}
}
