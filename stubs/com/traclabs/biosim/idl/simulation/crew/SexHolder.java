package com.traclabs.biosim.idl.simulation.crew;
/**
 * Generated from IDL enum "Sex".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
