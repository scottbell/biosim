package com.traclabs.biosim.idl;

/**
 *	Generated from IDL definition of alias "StringList"
 *	@author JacORB IDL compiler 
 */

public final class StringListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public java.lang.String[] value;

	public StringListHolder ()
	{
	}
	public StringListHolder (final java.lang.String[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return StringListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StringListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		StringListHelper.write (out,value);
	}
}
