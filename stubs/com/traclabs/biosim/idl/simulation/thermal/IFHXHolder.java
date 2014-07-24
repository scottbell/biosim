package com.traclabs.biosim.idl.simulation.thermal;

/**
 *	Generated from IDL interface "IFHX"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class IFHXHolder	implements org.omg.CORBA.portable.Streamable{
	 public IFHX value;
	public IFHXHolder()
	{
	}
	public IFHXHolder (final IFHX initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return IFHXHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = IFHXHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		IFHXHelper.write (_out,value);
	}
}
