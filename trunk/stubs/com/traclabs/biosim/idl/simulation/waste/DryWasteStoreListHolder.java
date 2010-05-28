package com.traclabs.biosim.idl.simulation.waste;

/**
 *	Generated from IDL definition of alias "DryWasteStoreList"
 *	@author JacORB IDL compiler 
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
