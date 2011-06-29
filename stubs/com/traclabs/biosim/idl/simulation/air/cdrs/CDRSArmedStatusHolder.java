package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSArmedStatus"
 *	@author JacORB IDL compiler 
 */

public final class CDRSArmedStatusHolder
	implements org.omg.CORBA.portable.Streamable
{
	public CDRSArmedStatus value;

	public CDRSArmedStatusHolder ()
	{
	}
	public CDRSArmedStatusHolder (final CDRSArmedStatus initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return CDRSArmedStatusHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CDRSArmedStatusHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		CDRSArmedStatusHelper.write (out,value);
	}
}
