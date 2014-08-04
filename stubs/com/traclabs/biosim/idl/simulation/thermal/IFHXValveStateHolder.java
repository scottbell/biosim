package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IFHXValveState"
 *	@author JacORB IDL compiler 
 */

public final class IFHXValveStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public IFHXValveState value;

	public IFHXValveStateHolder ()
	{
	}
	public IFHXValveStateHolder (final IFHXValveState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return IFHXValveStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = IFHXValveStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		IFHXValveStateHelper.write (out,value);
	}
}
