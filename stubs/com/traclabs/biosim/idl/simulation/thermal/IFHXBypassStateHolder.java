package com.traclabs.biosim.idl.simulation.thermal;
/**
 * Generated from IDL enum "IFHXBypassState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
