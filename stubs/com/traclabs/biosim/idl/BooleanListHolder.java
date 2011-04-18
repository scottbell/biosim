package com.traclabs.biosim.idl;

/**
 *	Generated from IDL definition of alias "BooleanList"
 *	@author JacORB IDL compiler 
 */

public final class BooleanListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public boolean[] value;

	public BooleanListHolder ()
	{
	}
	public BooleanListHolder (final boolean[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return BooleanListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BooleanListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		BooleanListHelper.write (out,value);
	}
}
