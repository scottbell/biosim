package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSState"
 *	@author JacORB IDL compiler 
 */

public final class CDRSStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public CDRSState value;

	public CDRSStateHolder ()
	{
	}
	public CDRSStateHolder (final CDRSState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return CDRSStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CDRSStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		CDRSStateHelper.write (out,value);
	}
}
