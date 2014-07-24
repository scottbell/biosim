package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "SoftwareStatus"
 *	@author JacORB IDL compiler 
 */

public final class SoftwareStatusHolder
	implements org.omg.CORBA.portable.Streamable
{
	public SoftwareStatus value;

	public SoftwareStatusHolder ()
	{
	}
	public SoftwareStatusHolder (final SoftwareStatus initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return SoftwareStatusHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = SoftwareStatusHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		SoftwareStatusHelper.write (out,value);
	}
}
