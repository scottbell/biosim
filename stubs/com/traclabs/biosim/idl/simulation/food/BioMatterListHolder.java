package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL alias "BioMatterList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
