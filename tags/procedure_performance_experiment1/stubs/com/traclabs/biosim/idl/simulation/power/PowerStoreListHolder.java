package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL definition of alias "PowerStoreList"
 *	@author JacORB IDL compiler 
 */

public final class PowerStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.power.PowerStore[] value;

	public PowerStoreListHolder ()
	{
	}
	public PowerStoreListHolder (final com.traclabs.biosim.idl.simulation.power.PowerStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return PowerStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = PowerStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		PowerStoreListHelper.write (out,value);
	}
}
