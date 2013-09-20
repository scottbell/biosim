package com.traclabs.biosim.idl.framework;

/**
 *	Generated from IDL definition of alias "MalfunctionList"
 *	@author JacORB IDL compiler 
 */

public final class MalfunctionListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.framework.Malfunction[] value;

	public MalfunctionListHolder ()
	{
	}
	public MalfunctionListHolder (final com.traclabs.biosim.idl.framework.Malfunction[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return MalfunctionListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MalfunctionListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		MalfunctionListHelper.write (out,value);
	}
}
