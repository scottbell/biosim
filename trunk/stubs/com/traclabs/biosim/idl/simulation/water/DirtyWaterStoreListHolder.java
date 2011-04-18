package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL definition of alias "DirtyWaterStoreList"
 *	@author JacORB IDL compiler 
 */

public final class DirtyWaterStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.water.DirtyWaterStore[] value;

	public DirtyWaterStoreListHolder ()
	{
	}
	public DirtyWaterStoreListHolder (final com.traclabs.biosim.idl.simulation.water.DirtyWaterStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DirtyWaterStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DirtyWaterStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DirtyWaterStoreListHelper.write (out,value);
	}
}
