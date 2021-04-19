package com.traclabs.biosim.idl.simulation.thermal;
/**
 * Generated from IDL enum "IFHXValveState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
