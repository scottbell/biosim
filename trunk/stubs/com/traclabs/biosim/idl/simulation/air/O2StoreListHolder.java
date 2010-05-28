package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL definition of alias "O2StoreList"
 *	@author JacORB IDL compiler 
 */

public final class O2StoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.air.O2Store[] value;

	public O2StoreListHolder ()
	{
	}
	public O2StoreListHolder (final com.traclabs.biosim.idl.simulation.air.O2Store[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return O2StoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = O2StoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		O2StoreListHelper.write (out,value);
	}
}
