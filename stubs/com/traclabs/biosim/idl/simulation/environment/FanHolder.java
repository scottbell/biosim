package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "Fan"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class FanHolder	implements org.omg.CORBA.portable.Streamable{
	 public Fan value;
	public FanHolder()
	{
	}
	public FanHolder (final Fan initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return FanHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = FanHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		FanHelper.write (_out,value);
	}
}
