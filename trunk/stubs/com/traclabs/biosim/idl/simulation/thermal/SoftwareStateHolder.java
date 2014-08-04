package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "SoftwareState"
 *	@author JacORB IDL compiler 
 */

public final class SoftwareStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public SoftwareState value;

	public SoftwareStateHolder ()
	{
	}
	public SoftwareStateHolder (final SoftwareState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return SoftwareStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = SoftwareStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		SoftwareStateHelper.write (out,value);
	}
}
