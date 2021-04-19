package com.traclabs.biosim.idl.simulation.water;

/**
 * Generated from IDL alias "GreyWaterStoreList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
