package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL definition of alias "BioMatterList"
 *	@author JacORB IDL compiler 
 */

public final class BioMatterListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.food.BioMatter[] value;

	public BioMatterListHolder ()
	{
	}
	public BioMatterListHolder (final com.traclabs.biosim.idl.simulation.food.BioMatter[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return BioMatterListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BioMatterListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		BioMatterListHelper.write (out,value);
	}
}
