package com.traclabs.biosim.idl.simulation.crew;
/**
 *	Generated from IDL definition of enum "Sex"
 *	@author JacORB IDL compiler 
 */

public final class SexHolder
	implements org.omg.CORBA.portable.Streamable
{
	public Sex value;

	public SexHolder ()
	{
	}
	public SexHolder (final Sex initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return SexHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = SexHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		SexHelper.write (out,value);
	}
}
