package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSCommandStatus"
 *	@author JacORB IDL compiler 
 */

public final class CDRSCommandStatusHolder
	implements org.omg.CORBA.portable.Streamable
{
	public CDRSCommandStatus value;

	public CDRSCommandStatusHolder ()
	{
	}
	public CDRSCommandStatusHolder (final CDRSCommandStatus initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return CDRSCommandStatusHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CDRSCommandStatusHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		CDRSCommandStatusHelper.write (out,value);
	}
}
