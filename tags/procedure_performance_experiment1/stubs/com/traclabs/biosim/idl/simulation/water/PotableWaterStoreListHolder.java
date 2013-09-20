package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL definition of alias "PotableWaterStoreList"
 *	@author JacORB IDL compiler 
 */

public final class PotableWaterStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.water.PotableWaterStore[] value;

	public PotableWaterStoreListHolder ()
	{
	}
	public PotableWaterStoreListHolder (final com.traclabs.biosim.idl.simulation.water.PotableWaterStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return PotableWaterStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PotableWaterStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		PotableWaterStoreListHelper.write (out,value);
	}
}
