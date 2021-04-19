package com.traclabs.biosim.idl.simulation.framework;

/**
 * Generated from IDL alias "StoreList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class StoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.framework.Store[] value;

	public StoreListHolder ()
	{
	}
	public StoreListHolder (final com.traclabs.biosim.idl.simulation.framework.Store[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return StoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = StoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		StoreListHelper.write (out,value);
	}
}
