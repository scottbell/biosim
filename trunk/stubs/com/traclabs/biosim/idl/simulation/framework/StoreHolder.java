package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "Store"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class StoreHolder	implements org.omg.CORBA.portable.Streamable{
	 public Store value;
	public StoreHolder()
	{
	}
	public StoreHolder (final Store initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return StoreHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StoreHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		StoreHelper.write (_out,value);
	}
}
