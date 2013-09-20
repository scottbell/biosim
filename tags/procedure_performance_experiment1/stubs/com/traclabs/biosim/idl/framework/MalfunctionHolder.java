package com.traclabs.biosim.idl.framework;

/**
 *	Generated from IDL interface "Malfunction"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class MalfunctionHolder	implements org.omg.CORBA.portable.Streamable{
	 public Malfunction value;
	public MalfunctionHolder()
	{
	}
	public MalfunctionHolder (final Malfunction initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return MalfunctionHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MalfunctionHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		MalfunctionHelper.write (_out,value);
	}
}
