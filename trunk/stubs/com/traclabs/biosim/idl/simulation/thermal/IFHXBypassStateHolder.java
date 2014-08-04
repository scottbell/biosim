package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IFHXBypassState"
 *	@author JacORB IDL compiler 
 */

public final class IFHXBypassStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public IFHXBypassState value;

	public IFHXBypassStateHolder ()
	{
	}
	public IFHXBypassStateHolder (final IFHXBypassState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return IFHXBypassStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = IFHXBypassStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		IFHXBypassStateHelper.write (out,value);
	}
}
