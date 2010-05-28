package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "Shelf"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class ShelfHolder	implements org.omg.CORBA.portable.Streamable{
	 public Shelf value;
	public ShelfHolder()
	{
	}
	public ShelfHolder (final Shelf initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return ShelfHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = ShelfHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		ShelfHelper.write (_out,value);
	}
}
