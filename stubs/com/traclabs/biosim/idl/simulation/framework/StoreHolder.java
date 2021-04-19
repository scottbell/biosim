package com.traclabs.biosim.idl.simulation.framework;

/**
 * Generated from IDL interface "Store".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
