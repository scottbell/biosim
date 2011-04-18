package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL definition of alias "ShelfList"
 *	@author JacORB IDL compiler 
 */

public final class ShelfListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.food.Shelf[] value;

	public ShelfListHolder ()
	{
	}
	public ShelfListHolder (final com.traclabs.biosim.idl.simulation.food.Shelf[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return ShelfListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ShelfListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		ShelfListHelper.write (out,value);
	}
}
