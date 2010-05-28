package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL definition of struct "BioMatter"
 *	@author JacORB IDL compiler 
 */

public final class BioMatterHolder
	implements org.omg.CORBA.portable.Streamable
{
	public com.traclabs.biosim.idl.simulation.food.BioMatter value;

	public BioMatterHolder ()
	{
	}
	public BioMatterHolder(final com.traclabs.biosim.idl.simulation.food.BioMatter initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return com.traclabs.biosim.idl.simulation.food.BioMatterHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = com.traclabs.biosim.idl.simulation.food.BioMatterHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		com.traclabs.biosim.idl.simulation.food.BioMatterHelper.write(_out, value);
	}
}
