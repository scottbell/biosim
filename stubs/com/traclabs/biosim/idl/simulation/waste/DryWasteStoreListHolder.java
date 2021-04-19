package com.traclabs.biosim.idl.simulation.waste;

/**
 * Generated from IDL alias "DryWasteStoreList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class DryWasteStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] value;

	public DryWasteStoreListHolder ()
	{
	}
	public DryWasteStoreListHolder (final com.traclabs.biosim.idl.simulation.waste.DryWasteStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return DryWasteStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = DryWasteStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		DryWasteStoreListHelper.write (out,value);
	}
}
