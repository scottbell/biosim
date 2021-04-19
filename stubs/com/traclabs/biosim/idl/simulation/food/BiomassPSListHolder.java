package com.traclabs.biosim.idl.simulation.food;

/**
 * Generated from IDL alias "BiomassPSList".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class BiomassPSListHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.food.BiomassPS[] value;

	public BiomassPSListHolder ()
	{
	}
	public BiomassPSListHolder (final com.traclabs.biosim.idl.simulation.food.BiomassPS[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return BiomassPSListHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = BiomassPSListHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		BiomassPSListHelper.write (out,value);
	}
}
