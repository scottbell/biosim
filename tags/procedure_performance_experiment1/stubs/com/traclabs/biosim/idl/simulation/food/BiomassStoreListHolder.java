package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL definition of alias "BiomassStoreList"
 *	@author JacORB IDL compiler 
 */

public final class BiomassStoreListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.food.BiomassStore[] value;

	public BiomassStoreListHolder ()
	{
	}
	public BiomassStoreListHolder (final com.traclabs.biosim.idl.simulation.food.BiomassStore[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return BiomassStoreListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassStoreListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		BiomassStoreListHelper.write (out,value);
	}
}
