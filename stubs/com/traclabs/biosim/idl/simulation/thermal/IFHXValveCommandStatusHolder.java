package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IFHXValveCommandStatus"
 *	@author JacORB IDL compiler 
 */

public final class IFHXValveCommandStatusHolder
	implements org.omg.CORBA.portable.Streamable
{
	public IFHXValveCommandStatus value;

	public IFHXValveCommandStatusHolder ()
	{
	}
	public IFHXValveCommandStatusHolder (final IFHXValveCommandStatus initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return IFHXValveCommandStatusHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = IFHXValveCommandStatusHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		IFHXValveCommandStatusHelper.write (out,value);
	}
}
