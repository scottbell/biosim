package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL definition of alias "H2StoreList"
 *	@author JacORB IDL compiler 
 */

public final class H2StoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.air.H2Store[] value;

	public H2StoreListHolder ()
	{
	}
	public H2StoreListHolder (final com.traclabs.biosim.idl.simulation.air.H2Store[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return H2StoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = H2StoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		H2StoreListHelper.write (out,value);
	}
}
