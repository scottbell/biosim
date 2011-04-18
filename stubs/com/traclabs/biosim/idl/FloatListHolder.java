package com.traclabs.biosim.idl;

/**
 *	Generated from IDL definition of alias "FloatList"
 *	@author JacORB IDL compiler 
 */

public final class FloatListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public float[] value;

	public FloatListHolder ()
	{
	}
	public FloatListHolder (final float[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return FloatListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FloatListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		FloatListHelper.write (out,value);
	}
}
