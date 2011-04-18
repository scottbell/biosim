package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL definition of alias "CO2StoreList"
 *	@author JacORB IDL compiler 
 */

public final class CO2StoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.air.CO2Store[] value;

	public CO2StoreListHolder ()
	{
	}
	public CO2StoreListHolder (final com.traclabs.biosim.idl.simulation.air.CO2Store[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return CO2StoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CO2StoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		CO2StoreListHelper.write (out,value);
	}
}
