package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL alias "FoodMatterList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class FoodMatterListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.food.FoodMatter[] value;

	public FoodMatterListHolder ()
	{
	}
	public FoodMatterListHolder (final com.traclabs.biosim.idl.simulation.food.FoodMatter[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return FoodMatterListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FoodMatterListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		FoodMatterListHelper.write (out,value);
	}
}
