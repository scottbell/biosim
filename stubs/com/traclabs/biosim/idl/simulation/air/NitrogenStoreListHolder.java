package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL alias "NitrogenStoreList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
