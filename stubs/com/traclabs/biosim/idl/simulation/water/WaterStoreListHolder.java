package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL definition of alias "WaterStoreList"
 *	@author JacORB IDL compiler 
 */

public final class WaterStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.water.WaterStore[] value;

	public WaterStoreListHolder ()
	{
	}
	public WaterStoreListHolder (final com.traclabs.biosim.idl.simulation.water.WaterStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return WaterStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = WaterStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		WaterStoreListHelper.write (out,value);
	}
}
