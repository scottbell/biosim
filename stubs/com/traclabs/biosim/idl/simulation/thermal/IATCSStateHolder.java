package com.traclabs.biosim.idl.simulation.thermal;
/**
 * Generated from IDL enum "IATCSState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public final class IATCSStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public IATCSState value;

	public IATCSStateHolder ()
	{
	}
	public IATCSStateHolder (final IATCSState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return IATCSStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = IATCSStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		IATCSStateHelper.write (out,value);
	}
}
