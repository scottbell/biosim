package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSValveState"
 *	@author JacORB IDL compiler 
 */

public final class CDRSValveStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public CDRSValveState value;

	public CDRSValveStateHolder ()
	{
	}
	public CDRSValveStateHolder (final CDRSValveState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return CDRSValveStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CDRSValveStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		CDRSValveStateHelper.write (out,value);
	}
}
