package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL alias "DirtyWaterStoreList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
