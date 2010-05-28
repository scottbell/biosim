package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL definition of alias "GreyWaterStoreList"
 *	@author JacORB IDL compiler 
 */

public final class GreyWaterStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.water.GreyWaterStore[] value;

	public GreyWaterStoreListHolder ()
	{
	}
	public GreyWaterStoreListHolder (final com.traclabs.biosim.idl.simulation.water.GreyWaterStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return GreyWaterStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GreyWaterStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		GreyWaterStoreListHelper.write (out,value);
	}
}
