package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 * Generated from IDL enum "CDRSArmedStatus".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
