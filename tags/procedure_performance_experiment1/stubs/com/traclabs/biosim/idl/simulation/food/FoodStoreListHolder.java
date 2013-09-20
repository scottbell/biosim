package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL definition of alias "FoodStoreList"
 *	@author JacORB IDL compiler 
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
