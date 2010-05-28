package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "EVAActivity"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class EVAActivityHolder	implements org.omg.CORBA.portable.Streamable{
	 public EVAActivity value;
	public EVAActivityHolder()
	{
	}
	public EVAActivityHolder (final EVAActivity initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return EVAActivityHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = EVAActivityHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		EVAActivityHelper.write (_out,value);
	}
}
