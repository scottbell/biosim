package com.traclabs.biosim.idl.simulation.air;

/**
 * Generated from IDL alias "MethaneStoreList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class MethaneStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.air.MethaneStore[] value;

	public MethaneStoreListHolder ()
	{
	}
	public MethaneStoreListHolder (final com.traclabs.biosim.idl.simulation.air.MethaneStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return MethaneStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = MethaneStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		MethaneStoreListHelper.write (out,value);
	}
}
