package com.traclabs.biosim.idl.framework;
/**
 * Generated from IDL enum "MalfunctionIntensity".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class MalfunctionIntensityHolder
	implements org.omg.CORBA.portable.Streamable
{
	public MalfunctionIntensity value;

	public MalfunctionIntensityHolder ()
	{
	}
	public MalfunctionIntensityHolder (final MalfunctionIntensity initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return MalfunctionIntensityHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MalfunctionIntensityHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		MalfunctionIntensityHelper.write (out,value);
	}
}
