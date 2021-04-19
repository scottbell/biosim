package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL alias "PotableWaterStoreList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
