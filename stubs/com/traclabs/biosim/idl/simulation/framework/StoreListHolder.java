package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL definition of alias "StoreList"
 *	@author JacORB IDL compiler 
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
