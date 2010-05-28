package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL definition of struct "FoodMatter"
 *	@author JacORB IDL compiler 
 */

public final class FoodMatterHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.food.FoodMatter value;

	public FoodMatterHolder ()
	{
	}
	public FoodMatterHolder(final com.traclabs.biosim.idl.simulation.food.FoodMatter initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return com.traclabs.biosim.idl.simulation.food.FoodMatterHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = com.traclabs.biosim.idl.simulation.food.FoodMatterHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		com.traclabs.biosim.idl.simulation.food.FoodMatterHelper.write(_out, value);
	}
}
