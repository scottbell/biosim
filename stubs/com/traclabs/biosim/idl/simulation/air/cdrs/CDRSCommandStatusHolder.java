package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 * Generated from IDL enum "CDRSCommandStatus".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
