package com.traclabs.biosim.idl.framework;
/**
 *	Generated from IDL definition of enum "MalfunctionLength"
 *	@author JacORB IDL compiler 
 */

public final class MalfunctionLengthHolder
	implements org.omg.CORBA.portable.Streamable
{
	public MalfunctionLength value;

	public MalfunctionLengthHolder ()
	{
	}
	public MalfunctionLengthHolder (final MalfunctionLength initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return MalfunctionLengthHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MalfunctionLengthHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		MalfunctionLengthHelper.write (out,value);
	}
}
