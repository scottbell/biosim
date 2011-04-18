package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL definition of alias "NitrogenStoreList"
 *	@author JacORB IDL compiler 
 */

public final class NitrogenStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.air.NitrogenStore[] value;

	public NitrogenStoreListHolder ()
	{
	}
	public NitrogenStoreListHolder (final com.traclabs.biosim.idl.simulation.air.NitrogenStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return NitrogenStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = NitrogenStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		NitrogenStoreListHelper.write (out,value);
	}
}
